package com.banulsoft.wallet.portfolio.web;

import com.banulsoft.wallet.portfolio.shared.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliodraft.application.PortfolioDraftFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/portfolio")
@RequiredArgsConstructor
class Controller {
    private final PortfolioDraftFacade portfolioDraftFacade;

    @PostMapping
    void create(@RequestBody PortfolioCreateDto portfolioCreateDto) {
        PortfolioCreateCommand createCommand = CommandAdapter.createCommand(portfolioCreateDto);
        portfolioDraftFacade.create(createCommand);
    }
}