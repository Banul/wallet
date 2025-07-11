package com.banulsoft.wallet.assetdetails.domain;

/*
    Tracking current price of tickers our users are interested in
 */

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AssetDetails {
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
