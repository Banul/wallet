package com.banulsoft.wallet.assetdetails.infrastructure;

import com.banulsoft.wallet.assetdetails.domain.AssetDetails;
import com.banulsoft.wallet.assetdetails.domain.PersistancePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AssetDetailsPersistanceAdapter implements PersistancePort {
    private final AssetDetailsJpaRepository assetDetailsJpaRepository;

    @Override
    public AssetDetails save(AssetDetails assetDetails) {
        AssetDetailsEntity assetDetailsEntity = AssetDetailsEntity.from(assetDetails);
        assetDetailsJpaRepository.save(assetDetailsEntity);
        return assetDetails;
    }
}
