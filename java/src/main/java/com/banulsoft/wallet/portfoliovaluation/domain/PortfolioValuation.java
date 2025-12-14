package com.banulsoft.wallet.portfoliovaluation.domain;

import com.banulsoft.wallet.stockvaluation.domain.Valuation;

public record PortfolioValuation(Valuation valuation, PortfolioValuationBaseInformation baseInformation) {
}
