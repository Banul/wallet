package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "portfolio_request")
class PortfolioOutboxEntity extends BaseEntity {
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "assets_creation_requests")
    private Set<PersistedCreationRequest> persistedCreationRequests;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OutboxProcessingStatus outboxProcessingStatus;

    @Column(name = "name")
    private String name;

    private UUID draftId;

    protected PortfolioOutboxEntity() {}

    public PortfolioOutboxEntity(PortfolioOutbox portfolioOutbox) {
        this.persistedCreationRequests = portfolioOutbox.getAssetsCreationRequests()
                .stream()
                .map(x -> new PersistedCreationRequest(x.ticker(), x.amount()))
                .collect(Collectors.toSet());

        this.name = portfolioOutbox.getName();
        this.outboxProcessingStatus = OutboxProcessingStatus.UNPROCESSED;
    }

    public UUID getId() {
        return super.getId();
    }

    public Set<PersistedCreationRequest> getAssets() {
        return persistedCreationRequests;
    }

    public OutboxProcessingStatus getStatus() {
        return outboxProcessingStatus;
    }

    public String getName() {
        return this.name;
    }
}
