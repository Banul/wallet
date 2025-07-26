package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioOutboxFacade;
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
    void create(@RequestBody PortfolioCreateDto portfolioCreateDto) {
        PortfolioCreateCommand createCommand = CommandAdapter.createCommand(portfolioCreateDto);
        portfolioOutboxFacade.create(createCommand);
    }

    @GetMapping(path = "/all")
    Set<PortfolioResponseDto> getAll() {
        return portfolioFacade.getBaseInformation()
                .stream()
                .map(x -> new PortfolioResponseDto(x.getName(), x.getValue()))
                .collect(Collectors.toSet());
    }
}