from dataclasses import asdict
from kafka import KafkaProducer
import json

import StockTrackingStatus
from StockInformation import StockInformation


producer = KafkaProducer(
    bootstrap_servers=['host.docker.internal:29092'],
    value_serializer=lambda v: json.dumps(v).encode('utf-8'),
    request_timeout_ms=120000
)

TOPIC = 'stock_tracking_response'

def send_stock_info(status: StockTrackingStatus):
    data = asdict(status)
    future = producer.send(TOPIC, value=data)
    record_meta = future.get(timeout=10)
    return record_meta