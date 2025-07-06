from dataclasses import dataclass, asdict

@dataclass
class StockTrackingStatus:
    ticker: str
    status: str