package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table
class PortfolioOutboxEntity extends BaseEntity {
    private UUID id;
}
