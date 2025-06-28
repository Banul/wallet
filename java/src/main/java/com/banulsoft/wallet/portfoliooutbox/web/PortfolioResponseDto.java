package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfoliooutbox.application.AssetCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.domain.Asset;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

record PortfolioResponseDto(Set<AssetResponseDto> assets) {
    static PortfolioResponseDto of(Set<Asset> assets) {
        Set<AssetResponseDto> assetResponseDtos = assets.stream()
                .map(asset -> new AssetResponseDto(asset.ticker(), asset.amount()))
                .collect(Collectors.toSet());

        return new PortfolioResponseDto(assetResponseDtos);
    }
}
