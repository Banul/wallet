package com.banulsoft.wallet.portfoliooutbox.domain;

import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;
import com.banulsoft.wallet.portfolio.shared.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliodraft.infrastructure.PortfolioSendToQueueCommand;
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
    private String name;
    private UUID id;
    private final Set<AssetCreateCommand> assetsCreationRequests;
    private UUID draftId;

    public PortfolioOutbox(PortfolioSendToQueueCommand command) {
        this.assetsCreationRequests = command.assets().stream()
                .map(x -> new AssetCreateCommand(x.ticker(), x.amount()))
                .collect(Collectors.toSet());
        this.name = command.name();
        this.draftId = command.draftId();
    }


    public PortfolioOutbox(UUID id, String name, Set<AssetCreateCommand> assetsCreationRequests) {
        this.id = id;
        this.name = name;
        this.assetsCreationRequests = assetsCreationRequests;
    }


    public Set<AssetCreateCommand> getRequests() {
        return assetsCreationRequests;
    }

    public UUID getId() {
        return id;
    }
}
