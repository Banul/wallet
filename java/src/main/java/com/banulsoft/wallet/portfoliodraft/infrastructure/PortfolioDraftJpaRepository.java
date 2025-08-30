package com.banulsoft.wallet.portfoliodraft.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

interface PortfolioDraftJpaRepository extends JpaRepository<PortfolioDraftEntity, UUID> {
    @Query(value = "select * from portfolio_request pr where pr.status = 'PENDING'", nativeQuery = true)
    Set<PortfolioDraftEntity> findSent();
}
