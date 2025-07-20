package com.banulsoft.wallet.portfolio.application;

import com.banulsoft.wallet.stockvaluation.domain.Currency;

import java.math.BigDecimal;
import java.util.UUID;

public class PortfolioBaseInformation {
    private UUID id;
    private String name;
    private BigDecimal value;
    private Currency currency;

    public PortfolioBaseInformation(UUID id, String name, BigDecimal value, Currency currency) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }
}
