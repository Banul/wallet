package com.banulsoft.wallet.portfoliovalue.domain;

import java.math.BigDecimal;

public class PortfolioValue {
    private BigDecimal price;

    public PortfolioValue(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
