from dataclasses import asdict
from kafka import KafkaProducer
import json
from StockInformation import StockInformation


# 2. Inicjalizacja producenta Kafka
producer = KafkaProducer(
    bootstrap_servers=['host.docker.internal:29092'],
    value_serializer=lambda v: json.dumps(v).encode('utf-8'),
    request_timeout_ms=120000
)


TOPIC = 'portfolio_response_topic'


def send_stock_info(stock: StockInformation):
    """
    Wysyła instancję StockInformation do Kafka.
    Zwraca metadane (topic, partition, offset).
    """
    data = asdict(stock)
    future = producer.send(TOPIC, value=data)
    record_meta = future.get(timeout=10)
    return record_meta