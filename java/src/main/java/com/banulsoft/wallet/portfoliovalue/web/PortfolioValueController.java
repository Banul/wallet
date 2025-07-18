package com.banulsoft.wallet.portfoliovalue.web;

import com.banulsoft.wallet.portfoliovalue.application.PortfolioValueFacade;
import com.banulsoft.wallet.portfoliovalue.domain.PortfolioValue;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping(path = "/portfolio-value")
@RequiredArgsConstructor
@RestController
class PortfolioValueController {
    private final PortfolioValueFacade portfolioValueFacade;

    @GetMapping("/{portfolioId}")
    public PortfolioValue calculateValue(@PathVariable UUID portfolioId) {
        return portfolioValueFacade.calculate(portfolioId);
    }
}
