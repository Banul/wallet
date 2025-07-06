import sqlite3
import time

import StockTrackingResponseSender
from StockFetcher import StockFetcher


def query_database():
    conn = sqlite3.connect('tracked_companies.db')
    cursor = conn.cursor()
    cursor.execute("SELECT ticker FROM companies")
    rows = cursor.fetchall()
    tickers = [row[0] for row in rows]
    conn.close()
    return tickers

interval = 10

stockFetcher = StockFetcher()
while True:
    try:
      tickers = query_database()
      print(tickers)
      for ticker in tickers:
        stockInfo = stockFetcher.fetch_stock_information(ticker)
        StockTrackingResponseSender.send_stock_info(stockInfo)
      time.sleep(interval)
    except Exception as e:
        print(e)