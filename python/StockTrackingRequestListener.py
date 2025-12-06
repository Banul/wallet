## todo -> this should just answer question, whether we started succesfully tracking company

from confluent_kafka import Consumer, KafkaException

import CompaniesTracker
import StockTrackingResponseSender
from StockFetcher import StockFetcher
from StockTrackingResponseSender import *
from confluent_kafka import Consumer, KafkaError

from exception.TickerNotExistsException import TickerNotExistsException

conf = {
    'bootstrap.servers': 'kafka:9092',
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

                for ticker in tickers:
                    try:
                        details = stockFetcher.fetch_ticker_details(ticker)
                        StockTrackingResponseSender.send_ticker_details(StockTrackingStatus.StockTrackingStatus(ticker, 'OK', details.country, details.industry, details.sector))

                    except TickerNotExistsException:
                        StockTrackingResponseSender.send_ticker_details(StockTrackingStatus.StockTrackingStatus(ticker, 'NOT EXISTS'))

                ## todo - what if saving here failed
                CompaniesTracker.save_tracked(tickers)

            except Exception as e:
                print(f"Odebrano surowy komunikat: {msg.value()}")

            consumer.commit(asynchronous=False)

except KeyboardInterrupt:
    print("Przerwano przez użytkownika")
finally:
    consumer.close()
