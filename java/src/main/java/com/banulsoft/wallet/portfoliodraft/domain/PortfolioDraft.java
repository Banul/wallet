package com.banulsoft.wallet.portfoliodraft.domain;

import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfolio.domain.Position;
import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;
import com.banulsoft.wallet.shared.Ticker;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PortfolioDraft {
    private UUID id;
    private String name;
    private final List<AssetCreateCommand> assetsCreationRequests;

    private DraftStatus draftStatus;

    public PortfolioDraft(UUID id, String name, List<AssetCreateCommand> assetsCreationRequests) {
        this.id = id;
        this.name = name;
        this.assetsCreationRequests = assetsCreationRequests;
        this.draftStatus = DraftStatus.CREATED;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<AssetCreateCommand> getAssetsCreationRequests() {
        return assetsCreationRequests;
    }

    public void markAsCreated() {
        this.draftStatus = DraftStatus.CREATED;
    }

    public Portfolio toPortfolio() {
        Set<Position> positions = this.getAssetsCreationRequests().stream()
                .map(x -> new Position(new Ticker(x.ticker()), x.amount()))
                .collect(Collectors.toSet());

        return new Portfolio(positions, this.getName());
    }
}
