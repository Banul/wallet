package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfoliooutbox.application.AssetCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioFacade;
import com.banulsoft.wallet.portfoliooutbox.domain.Asset;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/portfolio")
class Controller {
    private final PortfolioFacade portfolioFacade;

    public Controller(PortfolioFacade portfolioFacade) {
        this.portfolioFacade = portfolioFacade;
    }

    @PostMapping()
    PortfolioResponseDto create(PortfolioCreateDto portfolioCreateDto) {
        PortfolioCreateCommand createCommand = CommandAdapter.createCommand(portfolioCreateDto);
        PortfolioOutbox portfolio = portfolioFacade.create(createCommand);
        return PortfolioResponseDto.of(portfolio.getAssets());
    }
}
