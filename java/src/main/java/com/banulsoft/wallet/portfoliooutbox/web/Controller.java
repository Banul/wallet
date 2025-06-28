package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfoliooutbox.application.AssetCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioFacade;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/portfolio")
@RequiredArgsConstructor
class Controller {
    private final PortfolioFacade portfolioFacade;

    @PostMapping()
    PortfolioResponseDto create(PortfolioCreateDto portfolioCreateDto) {
        PortfolioCreateCommand createCommand = CommandAdapter.createCommand(portfolioCreateDto);
        PortfolioOutbox portfolio = portfolioFacade.create(createCommand);
        List<AssetCreateCommand> assetCreateCommands = portfolio.getAssetCreateCommands();
        return PortfolioResponseDto.of(assetCreateCommands);
    }
}
