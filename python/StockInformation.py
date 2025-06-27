from dataclasses import dataclass, asdict

@dataclass
class StockInformation:
    ticker: str
    price: float