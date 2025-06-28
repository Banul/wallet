package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfoliooutbox.application.AssetCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;

import java.util.List;

public class CommandAdapter {
    static PortfolioCreateCommand createCommand(PortfolioCreateDto dto) {
        List<AssetCreateCommand> assetCreateCommands = dto.assets().stream().map(asset -> new AssetCreateCommand(asset.ticker(), asset.amount())).toList();
        return new PortfolioCreateCommand(assetCreateCommands);
    }
}
