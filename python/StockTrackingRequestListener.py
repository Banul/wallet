## todo -> this should just answer question, whether we started succesfully tracking company

from confluent_kafka import Consumer, KafkaException

from kafka.errors import KafkaError

import CompaniesTracker
import StockTrackingResponseSender
import StockTrackingStatus
from StockFetcher import StockFetcher
from StockTrackingResponseSender import *
from confluent_kafka import Consumer, KafkaError

conf = {
    'bootstrap.servers': 'localhost:29092',
    'group.id': 'my_consumer_group',
    'auto.offset.reset': 'earliest',
    'enable.auto.commit': False
}

consumer = Consumer(conf)
stockFetcher = StockFetcher()

consumer.subscribe(['stock_tracking_request'])

try:
    while True:
        msg = consumer.poll(timeout=1.0)
        if msg is None:
            continue

        if msg.error():
            if msg.error().code() == KafkaError._PARTITION_EOF:
                print(f"Koniec partii {msg.topic()} [{msg.partition()}]")
            else:
                raise KafkaException(msg.error())
        else:
            try:
                data = json.loads(msg.value().decode('utf-8'))
                print(f"Odebrano: {data}")
                assets = data['tickers']
                tickers = []
                for asset in assets:
                    tickers.append(asset['name'])

                not_existing = []
                existing = []

                for ticker in tickers:
                    if stockFetcher.ticker_exists(ticker):
                        existing.append(ticker)
                    else:
                        not_existing.append(ticker)

                for ticker in not_existing:
                    StockTrackingResponseSender.send_tracking_status(StockTrackingStatus.StockTrackingStatus(ticker, 'NOT EXISTS'))

                for ticker in existing:
                    StockTrackingResponseSender.send_tracking_status(StockTrackingStatus.StockTrackingStatus(ticker, 'OK'))

                if len(not_existing) > 0:
                    continue

                ## todo - what if saving here failed
                CompaniesTracker.save_tracked(tickers)

            except Exception as e:
                print(f"Odebrano surowy komunikat: {msg.value()}")

            consumer.commit(asynchronous=False)

except KeyboardInterrupt:
    print("Przerwano przez użytkownika")
finally:
    consumer.close()
