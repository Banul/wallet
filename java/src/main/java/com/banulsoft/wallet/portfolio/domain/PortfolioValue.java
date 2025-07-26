package com.banulsoft.wallet.portfolio.domain;

import java.math.BigDecimal;

public class PortfolioValue {
    private final BigDecimal price;

    public PortfolioValue(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
