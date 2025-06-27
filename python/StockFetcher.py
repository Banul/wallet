from StockInformation import StockInformation
import yfinance as yf

class StockFetcher:
    def fetch_stock_information(self, ticker: str) -> StockInformation:
        xtb = yf.Ticker(ticker)
        current_price = xtb.info.get("currentPrice")
        print("Aktualna cena XTB (info):", current_price, "PLN")

        # 3b. Alternatywnie, pobranie dziennej ceny zamknięcia z ostatniej sesji
        hist = xtb.history(period="1d")  # 1 dzień
        close_price = hist["Close"].iloc[0]
        print("Cena zamknięcia XTB z ostatniej sesji:", close_price, "PLN")
        return StockInformation(ticker, close_price)

