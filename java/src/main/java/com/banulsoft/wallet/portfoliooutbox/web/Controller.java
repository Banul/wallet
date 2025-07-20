package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioOutboxFacade;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/portfolio")
@RequiredArgsConstructor
class Controller {
    private final PortfolioOutboxFacade portfolioOutboxFacade;
    private final PortfolioFacade portfolioFacade;


    @PostMapping
    PortfolioResponseDto create(@RequestBody PortfolioCreateDto portfolioCreateDto) {
        PortfolioCreateCommand createCommand = CommandAdapter.createCommand(portfolioCreateDto);
        PortfolioOutbox portfolio = portfolioOutboxFacade.create(createCommand);
        return PortfolioResponseDto.of(portfolio.getRequests());
    }

    @GetMapping(path = "/all")
    Set<PortfolioResponseDto> getAll() {
        return portfolioFacade.findAll().stream()
                .map(PortfolioResponseDto::of).collect(Collectors.toSet());
    }
}