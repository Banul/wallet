package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioOutboxFacade;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/portfolio")
class Controller {
    private final PortfolioOutboxFacade portfolioOutboxFacade;

    public Controller(PortfolioOutboxFacade portfolioOutboxFacade) {
        this.portfolioOutboxFacade = portfolioOutboxFacade;
    }

    @PostMapping
    PortfolioResponseDto create(PortfolioCreateDto portfolioCreateDto) {
        PortfolioCreateCommand createCommand = CommandAdapter.createCommand(portfolioCreateDto);
        PortfolioOutbox portfolio = portfolioOutboxFacade.create(createCommand);
        return PortfolioResponseDto.of(portfolio.getAssets());
    }
}
