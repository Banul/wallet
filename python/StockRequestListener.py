from confluent_kafka import Consumer, KafkaException
import json

from kafka.errors import KafkaError

import KafkaSender
from StockFetcher import StockFetcher
from KafkaSender import *

# Konfiguracja konsumenta
conf = {
    'bootstrap.servers': 'localhost:29092',  # Adres twojego brokera
    'group.id': 'my_consumer_group',  # ID grupy konsumentów
    'auto.offset.reset': 'earliest',  # Czytaj od początku tematu
    'enable.auto.commit': False  # Wyłącz automatyczne commitowanie offsetów
}

consumer = Consumer(conf)
stockFetcher = StockFetcher()

consumer.subscribe(['stock_topic'])

try:
    while True:
        msg = consumer.poll(timeout=1.0)

        if msg is None:
            continue  # Brak wiadomości, kontynuuj
        if msg.error():
            if msg.error().code() == KafkaError._PARTITION_EOF:
                # Koniec partii
                print(f"Koniec partii {msg.topic()} [{msg.partition()}]")
            else:
                raise KafkaException(msg.error())
        else:
            try:
                data = json.loads(msg.value().decode('utf-8'))
                print(f"Odebrano: {data}")
                assets = data['assets']
                for asset in assets:
                  stockInfo = stockFetcher.fetch_stock_information(asset['ticker'])
                  KafkaSender.send_stock_info(stockInfo)

                # Tutaj dodaj logikę przetwarzania
                # np. zapis do bazy, obliczenia itp.

            except json.JSONDecodeError:
                print(f"Odebrano surowy komunikat: {msg.value()}")

            # Ręczne commitowanie offsetu
            consumer.commit(asynchronous=False)

except KeyboardInterrupt:
    print("Przerwano przez użytkownika")
finally:
    # Zamknij konsumenta
    consumer.close()
