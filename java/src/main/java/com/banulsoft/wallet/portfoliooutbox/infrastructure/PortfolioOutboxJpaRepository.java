package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PortfolioOutboxJpaRepository extends JpaRepository<PortfolioOutboxEntity, UUID> {
}
