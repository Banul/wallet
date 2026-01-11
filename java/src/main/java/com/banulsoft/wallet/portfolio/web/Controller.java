package com.banulsoft.wallet.portfolio.web;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.shared.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliodraft.application.PortfolioDraftFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/portfolio")
@RequiredArgsConstructor
class Controller {
    private final PortfolioDraftFacade portfolioDraftFacade;

    @PostMapping
    UUID create(@RequestBody PortfolioCreateDto portfolioCreateDto) {
        PortfolioCreateCommand createCommand = CommandAdapter.createCommand(portfolioCreateDto);
        return portfolioDraftFacade.create(createCommand);
    }
}