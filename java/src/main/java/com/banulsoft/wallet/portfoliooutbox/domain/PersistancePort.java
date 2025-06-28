package com.banulsoft.wallet.portfoliooutbox.domain;

import java.util.Set;
import java.util.UUID;

public interface PersistancePort {
    PortfolioOutbox save(PortfolioOutbox portfolio);

    void markAsSuccess(UUID requestId);

    void markAsFailure(UUID requestId);

    Set<PortfolioOutbox> findReadyForProcessing();
}
