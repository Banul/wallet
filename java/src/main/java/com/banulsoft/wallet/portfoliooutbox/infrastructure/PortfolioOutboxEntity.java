package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.Asset;
import com.banulsoft.wallet.position.Position;
import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
class PortfolioOutboxEntity extends BaseEntity {
    private UUID id;

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<Asset> assets;

    public PortfolioOutboxEntity(Set<Asset> assets) {
        this.assets = assets;
    }
}
