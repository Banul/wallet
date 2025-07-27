from StockValuation import StockValuation
import yfinance as yf

from TickerDetails import TickerDetails
from exception import TickerNotExistsException


class StockFetcher:
    def fetch_stock_information(self, ticker: str) -> StockValuation:
        stock = yf.Ticker(ticker)
        current_price = stock.info.get("currentPrice")
        currency = stock.info.get("currency")
        print("Aktualna cena" + ticker + "(info):", current_price, currency)

        hist = stock.history(period="1d")
        return StockValuation(ticker, current_price, currency)

    def fetch_ticker_details(self, ticker: str):
        try:
            data = yf.Ticker(ticker).history(period='7d', interval='1d')
            exists = not data.empty
            if not exists:
                raise TickerNotExistsException

            info = yf.Ticker(ticker).info
            country = info.get('country')
            industry = info.get('industry')
            sector = info.get('sector')
            return TickerDetails(ticker, country, industry, sector)
        except Exception:
            return False
