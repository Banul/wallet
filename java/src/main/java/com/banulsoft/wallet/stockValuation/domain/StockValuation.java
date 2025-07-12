package com.banulsoft.wallet.stockValuation.domain;

/*
    Tracking current price of tickers our users are interested in
 */

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class StockValuation {
    private Ticker ticker;
    private Price price;

    public String tickerName() {
        return ticker.ticker();
    }

    public BigDecimal price() {
        return price.price();
    }

    public String currencyName() {
        return price.currency().name();
    }
}
