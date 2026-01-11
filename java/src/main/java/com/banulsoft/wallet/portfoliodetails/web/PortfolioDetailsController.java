package com.banulsoft.wallet.portfoliodetails.web;

import com.banulsoft.wallet.portfoliodetails.application.PortfolioDetailsFacade;
import com.banulsoft.wallet.portfoliodetails.domain.PortfolioDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/portfolio-details")
@RequiredArgsConstructor
class PortfolioDetailsController {
    private final PortfolioDetailsFacade portfolioDetailsFacade;

    @GetMapping(path = "/{id}")
    public PortfolioDetailsDto findById(@PathVariable UUID id) {
        PortfolioDetails details = portfolioDetailsFacade.findDetails(id);
        return new PortfolioDetailsDto(details);
    }
}