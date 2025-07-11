package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.shared.Ticker;

import java.util.Set;
import java.util.UUID;

public record PortfolioCreationRequestedEvent(UUID requestId, Set<Ticker> tickers) {

}
