package com.banulsoft.wallet.portfoliodetails.domain;

import com.banulsoft.wallet.stockinformation.domain.StockInformation;

import java.util.Set;

public class PortfolioDetails {
    private final Set<StockInformation> stockInfo;

    public PortfolioDetails(Set<StockInformation> stockInfo) {
        this.stockInfo = stockInfo;
    }

    public Set<StockInformation> getStockInfo() {
        return stockInfo;
    }
}
