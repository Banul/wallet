package com.banulsoft.wallet.assetdetails.infrastructure;

import com.banulsoft.wallet.assetdetails.domain.PersistancePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AssetDetailsAdapter implements PersistancePort {
    private final AssetDetailsJpaRepository assetDetailsJpaRepository;


}
