package com.banulsoft.wallet.stockinformation.domain;

public enum Sector {
    TECHNOLOGY("technology"),
    HEALTHCARE("healthcare"),
    FINANCIAL_SERVICES("financial services"),
    INDUSTRIALS("industrials"),
    CONSUMER_DEFENSIVE("consumer defensive"),
    CONSUMER_CYCLICAL("consumer cyclical"),
    ENERGY("energy"),
    COMMUNICATION_SERVICES("communication services"),
    UTILITIES("utilities"),
    BASIC_MATERIALS("basic materials"),
    REAL_ESTATE("real estate"),
    OTHER("other");
    private final String name;

    Sector(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
