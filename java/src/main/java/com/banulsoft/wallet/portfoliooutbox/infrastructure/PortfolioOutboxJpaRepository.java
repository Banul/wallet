package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

interface PortfolioOutboxJpaRepository extends JpaRepository<PortfolioOutboxEntity, UUID> {

    @Query(value = "update portfolio_request set status = 'SENT' where id = :requestId", nativeQuery = true)
    @Modifying
    void markAsSent(UUID requestId);

    @Query(value = "update portfolio_request set status = 'FAILED' where id = :requestId", nativeQuery = true)
    @Modifying
    void markAsFailure(UUID requestId);

    @Query(value = "select * from portfolio_request pr where pr.status = 'UNPROCESSED'", nativeQuery = true)
    Set<PortfolioOutboxEntity> findReadyForProcessing();

    @Query(value = "select * from portfolio_request pr where pr.status = 'SENT'", nativeQuery = true)
    Set<PortfolioOutboxEntity> findSent();

    @Query(value = "update portfolio_request set status = 'CONSUMED' where draft_id = :draftId", nativeQuery = true)
    @Modifying
    void markAsConsumed(UUID draftId);
}
