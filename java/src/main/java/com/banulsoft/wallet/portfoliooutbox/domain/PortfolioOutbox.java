package com.banulsoft.wallet.portfoliooutbox.domain;

import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/*
    If ticker was not found in asset details module, then we are adding it to outbox table
    to check whether such ticker exists in stock market
 */

@Getter
public class PortfolioOutbox {
    private UUID id;
    private final Set<AssetsCreationRequest> assetsCreationRequests;

    public PortfolioOutbox(UUID id, PortfolioCreateCommand command) {
        this.id = id;
        this.assetsCreationRequests = command.assets().stream().map(x -> new AssetsCreationRequest(x.ticker(), x.amount())).collect(Collectors.toSet());
    }

    public PortfolioOutbox(PortfolioCreateCommand command) {
        this.assetsCreationRequests = command.assets().stream().map(x -> new AssetsCreationRequest(x.ticker(), x.amount())).collect(Collectors.toSet());
    }

    public PortfolioOutbox(Set<AssetsCreationRequest> assetsCreationRequests) {
        this.assetsCreationRequests = assetsCreationRequests;
    }

    public PortfolioOutbox(UUID id, Set<AssetsCreationRequest> assetsCreationRequests) {
        this.id = id;
        this.assetsCreationRequests = assetsCreationRequests;
    }


    public Set<AssetsCreationRequest> getRequests() {
        return assetsCreationRequests;
    }

    public UUID getId() {
        return id;
    }
}
