package com.banulsoft.wallet.stockValuation.infrastructure;

import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "stock_valuation")
class StockValuation extends BaseEntity {
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    public static StockValuation from(com.banulsoft.wallet.stockValuation.domain.StockValuation stockValuation) {
        StockValuation assetDetailsEntity = new StockValuation();
        assetDetailsEntity.ticker = stockValuation.tickerName();
        assetDetailsEntity.amount = stockValuation.price();
        assetDetailsEntity.currency = stockValuation.currencyName();
        return assetDetailsEntity;
    }

}
