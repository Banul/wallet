package com.banulsoft.wallet.stockvaluation.infrastructure;

import com.banulsoft.wallet.shared.BaseEntity;
import com.banulsoft.wallet.stockvaluation.domain.StockValuation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "stock_valuation_history")
class StockValuationHistoryEntity extends BaseEntity {
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "price")
    private BigDecimal price;

    // todo - move currency to some dedicated module (stock information)
    @Column(name = "currency")
    private String currency;

    public static StockValuationHistoryEntity from(StockValuation stockValuation) {
        StockValuationHistoryEntity assetDetailsEntity = new StockValuationHistoryEntity();
        assetDetailsEntity.ticker = stockValuation.tickerName();
        assetDetailsEntity.price = stockValuation.price();
        assetDetailsEntity.currency = stockValuation.currencyName();
        return assetDetailsEntity;
    }

}
