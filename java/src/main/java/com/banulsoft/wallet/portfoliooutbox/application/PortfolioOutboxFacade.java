package com.banulsoft.wallet.portfoliooutbox.application;

import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import org.springframework.stereotype.Service;

@Service
public class PortfolioOutboxFacade {
    private final PersistancePort persistancePort;

    public PortfolioOutboxFacade(PersistancePort persistancePort) {
        this.persistancePort = persistancePort;
    }

    public PortfolioOutbox create(PortfolioCreateCommand portfolioCreateCommand) {
        PortfolioOutbox portfolioOutbox = new PortfolioOutbox(portfolioCreateCommand);
        return persistancePort.save(portfolioOutbox);
    }
}
