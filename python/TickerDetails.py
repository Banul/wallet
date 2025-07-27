from dataclasses import dataclass

@dataclass
class TickerDetails:
    ticker: str
    country: str
    industry: str
    sector: str