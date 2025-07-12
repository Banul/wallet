package com.banulsoft.wallet.stockValuation.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AssetDetailsJpaRepository extends JpaRepository<StockValuation, UUID> {
}
