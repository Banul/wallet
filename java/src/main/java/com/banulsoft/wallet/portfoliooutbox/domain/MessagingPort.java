package com.banulsoft.wallet.portfoliooutbox.domain;

import com.banulsoft.wallet.portfoliooutbox.infrastructure.PortfolioCreationRequestedEvent;

public interface MessagingPort {
    void sendPortfolioRequest(PortfolioCreationRequestedEvent portfolioCreationRequestedEvent);

    void markAsSent(PortfolioCreationRequestedEvent portfolioCreationRequestedEvent);
}
