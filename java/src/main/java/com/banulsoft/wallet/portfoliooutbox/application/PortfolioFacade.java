package com.banulsoft.wallet.portfoliooutbox.application;

import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.portfoliooutbox.infrastructure.PortfolioOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioFacade {
    private PortfolioOutboxRepository portfolioOutboxRepository;
    public PortfolioOutbox create(PortfolioCreateCommand portfolioCreateCommand) {
        PortfolioOutbox portfolioOutbox = new PortfolioOutbox(portfolioCreateCommand);
        return portfolioOutboxRepository.save(portfolioOutbox);
    }
}
