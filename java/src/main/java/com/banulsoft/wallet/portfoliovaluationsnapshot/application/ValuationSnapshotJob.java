package com.banulsoft.wallet.portfoliovaluationsnapshot.application;

import com.banulsoft.wallet.portfoliovaluation.application.PortfolioValuationFacade;
import com.banulsoft.wallet.portfoliovaluationsnapshot.domain.ValuationSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
class ValuationSnapshotJob {
    private final PortfolioValuationFacade portfolioValuationFacade;
    private final Clock clock;
    private final TransactionTemplate transactionTemplate;

    @Scheduled(cron = "0 0,30 * * * *")
    public void savePortfolioSnapshot() {
        // todo - introduce user module so this functionality will
        // be availabe for premium users only
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            List<ValuationSnapshot> valuationSnapshots = getValuationSnapshots();

        });
    }

    private List<ValuationSnapshot> getValuationSnapshots() {
        return portfolioValuationFacade.getValuationForAllPortfolios()
                .stream()
                .map(portfolioValuation ->
                        new ValuationSnapshot(portfolioValuation.valuation().price(),
                                portfolioValuation.valuation().currency(), portfolioValuation.baseInformation().portfolioId(), Instant.now(clock))).toList();
    }
}