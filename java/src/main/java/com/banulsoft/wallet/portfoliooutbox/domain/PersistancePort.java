package com.banulsoft.wallet.portfoliooutbox.domain;

import com.banulsoft.wallet.shared.Ticker;

import java.util.Set;
import java.util.UUID;

public interface PersistancePort {
    PortfolioOutbox save(PortfolioOutbox portfolio);

    void markAsSuccess(UUID requestId);

    void markAsFailure(UUID requestId);

    Set<PortfolioOutbox> findReadyForProcessing();

    void addTrackedCompanies(Set<Ticker> tickers);
}
