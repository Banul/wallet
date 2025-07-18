package com.banulsoft.wallet.stockvaluation.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface StockValuationHistoryJpaRepository extends JpaRepository<StockValuationHistoryEntity, UUID> {
}
