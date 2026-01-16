package com.banulsoft.wallet.portfoliovaluationsnapshot.infrastructure;

import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "portfolio_valuation_snapshot")
@Getter
@Setter
@NoArgsConstructor
class PortfolioValuationSnapshotEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "portfolio_id", nullable = false)
    private UUID portfolioId;

    @Column(name = "valuation", nullable = false)
    private BigDecimal valuation;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;
}