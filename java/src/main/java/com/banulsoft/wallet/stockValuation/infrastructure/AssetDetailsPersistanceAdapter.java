package com.banulsoft.wallet.stockValuation.infrastructure;

import com.banulsoft.wallet.stockValuation.domain.PersistancePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AssetDetailsPersistanceAdapter implements PersistancePort {
    private final AssetDetailsJpaRepository assetDetailsJpaRepository;

    @Override
    public com.banulsoft.wallet.stockValuation.domain.StockValuation save(com.banulsoft.wallet.stockValuation.domain.StockValuation stockValuation) {
        StockValuation assetDetailsEntity = StockValuation.from(stockValuation);
        assetDetailsJpaRepository.save(assetDetailsEntity);
        return stockValuation;
    }
}
