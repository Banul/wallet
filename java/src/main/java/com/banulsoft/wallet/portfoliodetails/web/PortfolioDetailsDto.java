package com.banulsoft.wallet.portfoliodetails.web;

import com.banulsoft.wallet.portfoliodetails.domain.PortfolioDetails;

import java.util.List;

class PortfolioDetailsDto {
    private final List<StockDetailDto> stockDetails;
    PortfolioDetailsDto(PortfolioDetails portfolioDetails) {
        this.stockDetails = portfolioDetails.stockInfo().stream().map(x -> new StockDetailDto(
                x.getTicker().name(),
                x.getSector().getName(),
                x.getIndustry(),
                x.getCountry()
        )).toList();
    }

    public List<StockDetailDto> getStockDetails() {
        return stockDetails;
    }
}
