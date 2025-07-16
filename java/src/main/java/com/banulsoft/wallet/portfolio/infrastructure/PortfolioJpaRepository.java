package com.banulsoft.wallet.portfolio.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioJpaRepository extends JpaRepository<PortfolioEntity, UUID> {
}
