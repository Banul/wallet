package com.banulsoft.wallet.shared.kafka;

import lombok.Getter;

@Getter
public class TickerDetailsResponse {
    private String ticker;
    private String status;
    private String country;
    private String industry;
    private String sector;

    public TickerDetailsResponse() {
    }

    public TickerDetailsResponse(String ticker, String status) {
        this.ticker = ticker;
        this.status = status;
    }
}