package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfoliooutbox.domain.AssetsCreationRequest;
import com.banulsoft.wallet.position.Position;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

record PortfolioResponseDto(Set<AssetResponseDto> assets) {
    static PortfolioResponseDto of(Set<AssetsCreationRequest> assetsCreationRequests) {
        Set<AssetResponseDto> assetResponseDtos = assetsCreationRequests.stream()
                .map(asset -> new AssetResponseDto(asset.ticker(), asset.amount()))
                .collect(Collectors.toSet());

        return new PortfolioResponseDto(assetResponseDtos);
    }

    static PortfolioResponseDto of(Portfolio portfolio) {
        Set<AssetResponseDto> assetResponseDtos = portfolio.getPositions()
                .stream().map(p -> new AssetResponseDto(p.getTicker().name(), p.getAmount()))
                .collect(Collectors.toSet());

        return new PortfolioResponseDto(assetResponseDtos);
    }
}
