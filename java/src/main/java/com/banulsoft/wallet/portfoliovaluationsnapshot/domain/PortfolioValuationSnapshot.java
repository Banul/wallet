package com.banulsoft.wallet.portfoliovaluationsnapshot.domain;

import com.banulsoft.wallet.portfolio.domain.PortfolioId;
import com.banulsoft.wallet.stockvaluation.domain.Valuation;

public record PortfolioValuationSnapshot(ValuationSnapshot valuationSnapshot, PortfolioId portfolioId) {

}
