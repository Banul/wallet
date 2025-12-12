package com.banulsoft.wallet.portfoliodraft.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

interface PortfolioDraftJpaRepository extends JpaRepository<PortfolioDraftEntity, UUID> {
    @Query(value = "select * from portfolio_draft pr where pr.status = 'READY_FOR_PROCESSING' FOR UPDATE", nativeQuery = true)
    Set<PortfolioDraftEntity> findPending();
}
