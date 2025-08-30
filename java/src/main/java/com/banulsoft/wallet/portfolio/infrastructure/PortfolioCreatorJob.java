package com.banulsoft.wallet.portfolio.infrastructure;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfoliodraft.application.PortfolioDraftFacade;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraft;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.portfolio.domain.Position;
import com.banulsoft.wallet.shared.Ticker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
class PortfolioCreatorJob {
    private final PortfolioDraftFacade portfolioDraftFacade;
    private final TransactionTemplate transactionTemplate;
    private final PortfolioFacade portfolioFacade;

    @Scheduled(fixedRate = 10000)
    public void createPortfolios() {
        Set<PortfolioDraft> validPortfolios = portfolioDraftFacade.findValidPortfolios();

        validPortfolios.forEach(draft -> {
            Portfolio portfolio = draft.toPortfolio();
            try {
                transactionTemplate.executeWithoutResult(status -> {
                    portfolioFacade.create(portfolio);
                    portfolioDraftFacade.markAsCreated(draft.getId());
                });
            } catch (Exception e) {
                log.error("Error when creating portfolio from outbox", e);
            }
        });

    }
}
