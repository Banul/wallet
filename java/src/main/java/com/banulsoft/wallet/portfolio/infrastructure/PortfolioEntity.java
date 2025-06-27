package com.banulsoft.wallet.portfolio.infrastructure;

import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table
public class PortfolioEntity extends BaseEntity {
    private UUID id;
}
