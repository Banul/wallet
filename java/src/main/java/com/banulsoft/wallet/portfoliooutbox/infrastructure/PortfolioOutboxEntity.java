package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.Asset;
import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "portfolio_request")
class PortfolioOutboxEntity extends BaseEntity {
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "assets")
    private Set<Asset> assets;

    @Column(name = "status")
    private Status status;

    protected PortfolioOutboxEntity() {}

    public PortfolioOutboxEntity(Set<Asset> assets) {
        this.assets = assets;
    }

    public UUID getId() {
        return super.getId();
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public Status getStatus() {
        return status;
    }
}
