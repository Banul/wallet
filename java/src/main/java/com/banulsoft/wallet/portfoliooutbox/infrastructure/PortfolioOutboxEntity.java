package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.AssetsCreationRequest;
import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "portfolio_request")
class PortfolioOutboxEntity extends BaseEntity {
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "assets_creation_requests")
    private Set<AssetsCreationRequest> assetsCreationRequests;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    protected PortfolioOutboxEntity() {}

    public PortfolioOutboxEntity(Set<AssetsCreationRequest> assetsCreationRequests) {
        this.assetsCreationRequests = assetsCreationRequests;
        this.status = Status.UNPROCESSED;
    }

    public UUID getId() {
        return super.getId();
    }

    public Set<AssetsCreationRequest> getAssets() {
        return assetsCreationRequests;
    }

    public Status getStatus() {
        return status;
    }
}
