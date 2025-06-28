package com.banulsoft.wallet.portfoliooutbox.application;

import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.portfoliooutbox.infrastructure.PortfolioOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioFacade {
    private final PortfolioOutboxRepository portfolioOutboxRepository;
    public PortfolioOutbox create(PortfolioCreateCommand portfolioCreateCommand) {
        PortfolioOutbox portfolioOutbox = new PortfolioOutbox(portfolioCreateCommand);
        portfolioOutboxRepository.save()
    }

//    private Portfolio create(PortfolioCreateCommand createCommand) {
//        createCommand.assets().stream().map(asset -> {
//
//        })
//        return new Portfolio();
//    }
}
