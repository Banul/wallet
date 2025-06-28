package com.banulsoft.wallet.portfoliooutbox.domain;

import com.banulsoft.wallet.portfoliooutbox.application.AssetCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;
import lombok.Getter;

import java.util.List;

@Getter
public class PortfolioOutbox {
    private List<AssetCreateCommand> assetCreateCommands;

    public PortfolioOutbox(PortfolioCreateCommand command) {
        this.assetCreateCommands = command.assets();
    }
}
