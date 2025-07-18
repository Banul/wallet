package com.banulsoft.wallet.stockvaluation.infrastructure;

import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "stock_valuation_current")
class StockValuationCurrentEntity extends BaseEntity {
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "price")
    private BigDecimal price;

    // todo - move currency to some dedicated module (stock information)
    @Column(name = "currency")
    private String currency;

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}