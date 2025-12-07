package com.banulsoft.wallet.portfoliostatistics.web;

import com.banulsoft.wallet.portfolio.domain.PortfolioId;
import com.banulsoft.wallet.portfoliostatistics.application.PortfolioStatisticsFacade;
import com.banulsoft.wallet.portfoliostatistics.domain.PortfolioStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/portfolio-statistics")
@RequiredArgsConstructor
class StatisticsController {
    private final PortfolioStatisticsFacade portfolioStatisticsFacade;
    private final PortfolioStatisticsMapper mapper;

    @GetMapping(path = "/{id}")
    public PortfolioStatisticsDto getStatistics(@PathVariable UUID id) {
        PortfolioStatistics portfolioStatistics = portfolioStatisticsFacade.calculateStatisticsForPortfolio(new PortfolioId(id));
        return mapper.toResponse(portfolioStatistics);
    }
}
