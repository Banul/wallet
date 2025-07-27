package com.banulsoft.wallet.portfoliodetails.web;

import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.stockinformation.domain.Sector;

class StockDetailDto {
    private final String ticker;
    private final String sector;
    private final String industry;
    private final String country;

    public StockDetailDto(String ticker, String sector, String industry, String country) {
        this.ticker = ticker;
        this.sector = sector;
        this.industry = industry;
        this.country = country;
    }

    public String getTicker() {
        return ticker;
    }

    public String getSector() {
        return sector;
    }

    public String getIndustry() {
        return industry;
    }

    public String getCountry() {
        return country;
    }
}
