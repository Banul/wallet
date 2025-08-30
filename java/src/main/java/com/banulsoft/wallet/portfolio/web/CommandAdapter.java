package com.banulsoft.wallet.portfolio.web;

import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;
import com.banulsoft.wallet.portfolio.shared.PortfolioCreateCommand;

import java.util.List;

public class CommandAdapter {
    static PortfolioCreateCommand createCommand(PortfolioCreateDto dto) {
        List<AssetCreateCommand> assetCreateCommands = dto.assets().stream().map(asset -> new AssetCreateCommand(asset.ticker(), asset.amount())).toList();
        return new PortfolioCreateCommand(assetCreateCommands, dto.name());
    }
}
