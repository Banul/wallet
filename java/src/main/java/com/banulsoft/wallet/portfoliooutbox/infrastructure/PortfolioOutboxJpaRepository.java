package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

interface PortfolioOutboxJpaRepository extends JpaRepository<PortfolioOutboxEntity, UUID> {

    @Query(value = "update portfolio_request pr set pr.status = 'SUCCEEDED'", nativeQuery = true)
    @Modifying
    void markAsSuccess(UUID requestId);

    @Query(value = "update portfolio_request pr set pr.status = 'FAILED'", nativeQuery = true)
    @Modifying
    void markAsFailure(UUID requestId);

    @Query(value = "select * from portfolio_request pr", nativeQuery = true)
    Set<PortfolioOutboxEntity> findReadyForProcessing();
}
