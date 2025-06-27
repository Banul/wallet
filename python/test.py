from kafka import KafkaProducer

from KafkaSender import send_stock_info
from StockFetcher import StockFetcher

stockFetcher = StockFetcher()
stockInformation = stockFetcher.fetch_stock_information("XTB.WA")

# Przykład użycia:
print("Wysyłam na kafkę")
meta = send_stock_info(stockInformation)
print(f"Wysłano do {meta.topic} [partition {meta.partition}] @ offset {meta.offset}")
