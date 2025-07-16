package com.banulsoft.wallet.stockValuation.infrastructure;

import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "stock_valuation")
class StockValuationEntity extends BaseEntity {
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "price")
    private BigDecimal price;

    // todo - move currency to some dedicated module (stock information)
    @Column(name = "currency")
    private String currency;

    public static StockValuationEntity from(com.banulsoft.wallet.stockValuation.domain.StockValuation stockValuation) {
        StockValuationEntity assetDetailsEntity = new StockValuationEntity();
        assetDetailsEntity.ticker = stockValuation.tickerName();
        assetDetailsEntity.price = stockValuation.price();
        assetDetailsEntity.currency = stockValuation.currencyName();
        return assetDetailsEntity;
    }

}
