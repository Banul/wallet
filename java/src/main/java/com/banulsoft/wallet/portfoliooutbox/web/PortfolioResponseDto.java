package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfoliooutbox.application.AssetCreateCommand;

import java.util.List;

record PortfolioResponseDto(List<AssetResponseDto> assets) {
    static PortfolioResponseDto of(List<AssetCreateCommand> commands) {
        List<AssetResponseDto> assets = commands.stream().map(command -> new AssetResponseDto(
                command.ticker(),
                command.amount()
        )).toList();

        return new PortfolioResponseDto(assets);
    }
}
