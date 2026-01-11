package com.banulsoft.wallet.portfolio.infrastructure;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfoliodraft.application.PortfolioDraftFacade;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraft;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
class PortfolioCreatorJob {
    private final PortfolioDraftFacade portfolioDraftFacade;
    private final TransactionTemplate transactionTemplate;
    private final PortfolioFacade portfolioFacade;

    @Scheduled(fixedRate = 10000)
    public void createPortfolios() {
        Set<PortfolioDraft> validDrafts = portfolioDraftFacade.findPendingDrafts();

        validDrafts.forEach(draft -> {
            Portfolio portfolio = draft.toPortfolio();
            try {
                transactionTemplate.executeWithoutResult(status -> {
                    log.info("Creating portfolio: {}", portfolio.getName());
                    portfolioFacade.create(portfolio);
                    portfolioDraftFacade.markAsCreated(draft.getId());
                });
            } catch (Exception e) {
                log.error("Error when creating portfolio from outbox", e);
            }
        });
    }
}
