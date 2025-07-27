package com.banulsoft.wallet.stockinformation.domain;

import com.banulsoft.wallet.shared.Ticker;

import java.util.Arrays;

public class StockInformation {
    private final Ticker ticker;
    private final Sector sector;
    private final String industry;
    private final String country;

    public StockInformation(Ticker ticker, String sector, String industry, String country) {
        this.ticker = ticker;
        this.sector = Arrays.stream(Sector.values()).filter(x -> x.getName()
                        .equalsIgnoreCase(sector))
                .findFirst()
                .orElse(Sector.OTHER);
        this.industry = industry;
        this.country = country;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public Sector getSector() {
        return sector;
    }

    public String getIndustry() {
        return industry;
    }

    public String getCountry() {
        return country;
    }
}
