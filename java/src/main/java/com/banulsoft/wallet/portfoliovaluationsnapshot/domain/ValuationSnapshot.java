package com.banulsoft.wallet.portfoliovaluationsnapshot.domain;

import com.banulsoft.wallet.portfolio.domain.PortfolioId;
import com.banulsoft.wallet.stockvaluation.domain.Currency;

import java.math.BigDecimal;
import java.time.Instant;

public record ValuationSnapshot(BigDecimal price, Currency currency, PortfolioId portfolioId, Instant timestamp) {
}
