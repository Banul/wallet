package com.banulsoft.wallet.stockvaluation.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

interface StockValuationCurrentJpaRepository extends JpaRepository<StockValuationCurrentEntity, UUID> {
    @Query(value = "select * from stock_valuation_current svc where svc.ticker in :tickers", nativeQuery = true)
    Set<StockValuationCurrentEntity> findByTickers(Set<String> tickers);
}
