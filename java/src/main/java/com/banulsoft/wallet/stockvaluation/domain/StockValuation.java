package com.banulsoft.wallet.stockvaluation.domain;

/*
    Tracking current price of tickers our users are interested in
 */

import com.banulsoft.wallet.shared.Ticker;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class StockValuation {
    private Ticker ticker;
    private Valuation price;

    public String tickerName() {
        return ticker.name();
    }

    public BigDecimal price() {
        return price.price();
    }

    public String currencyName() {
        return price.currency().name();
    }

    public StockValuation(Ticker ticker, Valuation price) {
        this.ticker = ticker;
        this.price = price;
    }
}
