package com.banulsoft.wallet.portfoliooutbox.application;

import com.banulsoft.wallet.portfoliodraft.infrastructure.PortfolioSendToQueueCommand;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PortfolioOutboxFacade {
    private final PersistancePort persistancePort;

    public PortfolioOutboxFacade(PersistancePort persistancePort) {
        this.persistancePort = persistancePort;
    }

    public PortfolioOutbox send(PortfolioSendToQueueCommand portfolioSendToQueueCommand) {
        PortfolioOutbox portfolioOutbox = new PortfolioOutbox(portfolioSendToQueueCommand);
        return persistancePort.save(portfolioOutbox);
    }
}
