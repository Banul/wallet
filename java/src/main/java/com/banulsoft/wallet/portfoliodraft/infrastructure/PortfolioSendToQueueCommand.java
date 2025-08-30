package com.banulsoft.wallet.portfoliodraft.infrastructure;

import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;

import java.util.List;
import java.util.UUID;

public record PortfolioSendToQueueCommand(List<AssetCreateCommand> assets, String name, UUID draftId) {
}
