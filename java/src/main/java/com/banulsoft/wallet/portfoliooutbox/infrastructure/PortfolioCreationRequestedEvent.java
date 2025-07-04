package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import java.util.Set;
import java.util.UUID;

public record PortfolioCreationRequestedEvent(UUID requestId, Set<Ticker> assets) {

}
