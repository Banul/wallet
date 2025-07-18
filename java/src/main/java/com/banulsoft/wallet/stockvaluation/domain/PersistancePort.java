package com.banulsoft.wallet.stockvaluation.domain;

import com.banulsoft.wallet.shared.Ticker;

import java.util.Set;

public interface PersistancePort {
    StockValuation save(StockValuation stockValuation);
    Set<StockValuation> findByTickers(Set<Ticker> tickers);
}
