from StockInformation import StockInformation
import yfinance as yf

class StockFetcher:
    def fetch_stock_information(self, ticker: str) -> StockInformation:
        stock = yf.Ticker(ticker)
        current_price = stock.info.get("currentPrice")
        currency = stock.info.get("currency")
        print("Aktualna cena" + ticker + "(info):", current_price, currency)

        hist = stock.history(period="1d")
        return StockInformation(ticker, current_price, currency)

    def ticker_exists(self, ticker: str):
        try:
            data = yf.Ticker(ticker).history(period='7d', interval='1d')
            return not data.empty
        except Exception:
            return False
