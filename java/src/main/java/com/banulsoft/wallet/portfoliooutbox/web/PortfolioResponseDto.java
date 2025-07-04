package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfoliooutbox.domain.AssetsCreationRequest;

import java.util.Set;
import java.util.stream.Collectors;

record PortfolioResponseDto(Set<AssetResponseDto> assets) {
    static PortfolioResponseDto of(Set<AssetsCreationRequest> assetsCreationRequests) {
        Set<AssetResponseDto> assetResponseDtos = assetsCreationRequests.stream()
                .map(asset -> new AssetResponseDto(asset.ticker(), asset.amount()))
                .collect(Collectors.toSet());

        return new PortfolioResponseDto(assetResponseDtos);
    }
}
