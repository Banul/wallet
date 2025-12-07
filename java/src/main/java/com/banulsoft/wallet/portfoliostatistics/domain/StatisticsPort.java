package com.banulsoft.wallet.portfoliostatistics.domain;

import com.banulsoft.wallet.portfolio.domain.PortfolioId;

public interface StatisticsPort {
    PortfolioStatistics getStatistics(PortfolioId id);
}
