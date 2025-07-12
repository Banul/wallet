package com.banulsoft.wallet.shared.kafka;

import java.math.BigDecimal;

public class StockValuationResponse {
    private String ticker;
    private BigDecimal price;
    private String currency;

    public StockValuationResponse(String ticker, BigDecimal price, String currency) {
        this.ticker = ticker;
        this.price = price;
        this.currency = currency;
    }

    public StockValuationResponse() {}

    @Override
    public String toString() {
        return "Ticker: " + ticker + " price: " + price + " " + currency;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
