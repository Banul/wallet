package com.banulsoft.wallet.stockValuation.infrastructure;

import com.banulsoft.wallet.stockValuation.domain.PersistancePort;
import com.banulsoft.wallet.stockValuation.domain.StockValuation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AssetDetailsPersistanceAdapter implements PersistancePort {
    private final AssetDetailsJpaRepository assetDetailsJpaRepository;

    @Override
    public StockValuation save(StockValuation stockValuation) {
        StockValuationEntity assetDetailsEntity = StockValuationEntity.from(stockValuation);
        assetDetailsJpaRepository.save(assetDetailsEntity);
        return stockValuation;
    }
}
