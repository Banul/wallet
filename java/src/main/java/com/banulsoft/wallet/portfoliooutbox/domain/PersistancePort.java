package com.banulsoft.wallet.portfoliooutbox.domain;

import java.util.Set;
import java.util.UUID;

public interface PersistancePort {
    PortfolioOutbox save(PortfolioOutbox portfolio);

    void markAsFailure(UUID requestId);

    void markAsSent(UUID requestId);

    Set<PortfolioOutbox> findNotSendToKafka();

    Set<PortfolioOutbox> findSentToKafka();

    void markAsConsumed(UUID draftId);
}
