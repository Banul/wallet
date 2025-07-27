package com.banulsoft.wallet.stockinformation.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

interface StockInformationJpaRepository extends JpaRepository<StockInformationEntity, UUID> {
    Set<StockInformationEntity> findByTicker(Set<String> ticker);
}
