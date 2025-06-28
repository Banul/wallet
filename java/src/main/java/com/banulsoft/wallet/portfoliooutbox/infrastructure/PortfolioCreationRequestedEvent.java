package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.Asset;

import java.util.Set;
import java.util.UUID;

public record PortfolioCreationRequestedEvent(UUID requestId, Set<Asset> assets) {

}
