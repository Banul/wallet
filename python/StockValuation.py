from dataclasses import dataclass, asdict

@dataclass
class StockValuation:
    ticker: str
    price: float
    currency: str