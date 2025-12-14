package com.banulsoft.wallet.portfoliovaluationsnapshot.application;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfoliovaluation.application.PortfolioValuationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class ValuationSnapshotJob {
    private final PortfolioValuationFacade portfolioValuationFacade;
    private final PortfolioFacade portfolioFacade;

    @Scheduled(cron = "0 0,30 * * * *")
    public void savePortfolioSnapshot() {

        // todo - adding snapshots

    }
}
