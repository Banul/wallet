package com.banulsoft.wallet.portfolio.infrastructure;

import lombok.Getter;

@Getter
public class StockPriceEvent {
    private String ticker;
    private double price;
    private String currency;

    public StockPriceEvent() {
    }

    public StockPriceEvent(String ticker, double price, String currency) {
        this.ticker = ticker;
        this.price = price;
        this.currency = currency;
    }
}
