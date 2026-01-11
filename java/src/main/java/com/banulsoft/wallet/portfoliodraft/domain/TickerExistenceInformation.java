package com.banulsoft.wallet.portfoliodraft.domain;

import com.banulsoft.wallet.shared.kafka.TickerDetailsResponse;
import lombok.Getter;

@Getter
public class TickerExistenceInformation {
    private final String ticker;
    private final String status;
    private final String country;
    private final String industry;
    private final String sector;


    public TickerExistenceInformation(String ticker, String status, String country, String industry, String sector) {
        this.ticker = ticker;
        this.status = status;
        this.country = country;
        this.industry = industry;
        this.sector = sector;
    }

    public TickerExistenceInformation(TickerDetailsResponse tickerDetailsResponse) {
        this.ticker = tickerDetailsResponse.getTicker();
        this.status = tickerDetailsResponse.getStatus();
        this.country = tickerDetailsResponse.getCountry();
        this.industry = tickerDetailsResponse.getIndustry();
        this.sector = tickerDetailsResponse.getSector();
    }
}