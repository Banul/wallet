package com.banulsoft.wallet.stockValuation.domain;

public interface PersistancePort {
    StockValuation save(StockValuation stockValuation);
}
