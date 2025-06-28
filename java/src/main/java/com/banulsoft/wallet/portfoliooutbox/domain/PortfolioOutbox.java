package com.banulsoft.wallet.portfoliooutbox.domain;

import com.banulsoft.wallet.portfoliooutbox.application.AssetCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class PortfolioOutbox {
    private final Set<Asset> assets;

    public PortfolioOutbox(PortfolioCreateCommand command) {
        this.assets = command.assets().stream().map(x -> new Asset(x.ticker(), x.amount())).collect(Collectors.toSet());
    }

    public Set<Asset> getAssets() {
        return assets;
    }
}
