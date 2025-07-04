package com.banulsoft.wallet.assetdetails.infrastructure;

import com.banulsoft.wallet.assetdetails.domain.AssetDetails;
import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "asset_details")
class AssetDetailsEntity extends BaseEntity {
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    public static AssetDetailsEntity from(AssetDetails assetDetails) {
        AssetDetailsEntity assetDetailsEntity = new AssetDetailsEntity();
        assetDetailsEntity.ticker = assetDetails.tickerName();
        assetDetailsEntity.amount = assetDetails.price();
        assetDetailsEntity.currency = assetDetails.currencyName();
        return assetDetailsEntity;
    }

}
