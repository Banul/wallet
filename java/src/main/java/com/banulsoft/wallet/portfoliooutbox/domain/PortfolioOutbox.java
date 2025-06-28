package com.banulsoft.wallet.portfoliooutbox.domain;

import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class PortfolioOutbox {
    private UUID id;
    private final Set<Asset> assets;

    public PortfolioOutbox(UUID id, PortfolioCreateCommand command) {
        this.id = id;
        this.assets = command.assets().stream().map(x -> new Asset(x.ticker(), x.amount())).collect(Collectors.toSet());
    }

    public PortfolioOutbox(PortfolioCreateCommand command) {
        this.assets = command.assets().stream().map(x -> new Asset(x.ticker(), x.amount())).collect(Collectors.toSet());
    }

    public PortfolioOutbox(Set<Asset> assets) {
        this.assets = assets;
    }

    public PortfolioOutbox(UUID id, Set<Asset> assets) {
        this.id = id;
        this.assets = assets;
    }


    public Set<Asset> getAssets() {
        return assets;
    }

    public UUID getId() {
        return id;
    }
}
