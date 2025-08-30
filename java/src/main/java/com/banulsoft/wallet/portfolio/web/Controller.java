package com.banulsoft.wallet.portfolio.web;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.shared.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliodraft.application.PortfolioDraftFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/portfolio")
@RequiredArgsConstructor
class Controller {
    private final PortfolioDraftFacade portfolioDraftFacade;
    private final PortfolioFacade portfolioFacade;

    @PostMapping
    void create(@RequestBody PortfolioCreateDto portfolioCreateDto) {
        PortfolioCreateCommand createCommand = CommandAdapter.createCommand(portfolioCreateDto);
        portfolioDraftFacade.create(createCommand);
    }

    @GetMapping(path = "/all")
    Set<PortfolioResponseDto> getAll() {
        return portfolioFacade.getBaseInformation()
                .stream()
                .map(x -> new PortfolioResponseDto(x.getId(), x.getName(), x.getValue()))
                .collect(Collectors.toSet());
    }
}