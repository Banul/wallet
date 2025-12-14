package com.banulsoft.wallet.portfoliovaluation.web;

import com.banulsoft.wallet.portfolio.application.PortfolioBaseInformation;
import com.banulsoft.wallet.portfoliovaluation.application.PortfolioValuationFacade;
import com.banulsoft.wallet.portfoliovaluation.domain.PortfolioValuation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/portfolio-valuation")
@RequiredArgsConstructor
class PortfolioValuationController {
    private final PortfolioValuationFacade portfolioValuationFacade;
    @GetMapping(path = "/all")
    Set<PortfolioValuationDto> getAll() {
        return portfolioValuationFacade.getValuationForAllPortfolios()
                .stream()
                .map(x -> new PortfolioValuationDto(
                        x.baseInformation().portfolioId().id(),
                        x.baseInformation().portfolioName(),
                        x.valuation().price()))
                .collect(Collectors.toSet());
    }

    @GetMapping(path = "/{id}")
    public PortfolioValuationDto findById(@PathVariable UUID id) {
        PortfolioValuation valuationForPortfolio = portfolioValuationFacade.getValuationForPortfolio(id);
        return new PortfolioValuationDto(
                valuationForPortfolio.baseInformation().portfolioId().id(),
                valuationForPortfolio.baseInformation().portfolioName(),
                valuationForPortfolio.valuation().price());
    }
}
