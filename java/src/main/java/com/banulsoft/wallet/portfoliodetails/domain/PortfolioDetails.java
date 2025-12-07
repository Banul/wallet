package com.banulsoft.wallet.portfoliodetails.domain;

import com.banulsoft.wallet.stockinformation.domain.StockInformation;

import java.util.Set;

public record PortfolioDetails(Set<StockInformation> stockInfo) {
}
