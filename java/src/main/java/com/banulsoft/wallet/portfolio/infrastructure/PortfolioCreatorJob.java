package com.banulsoft.wallet.portfolio.infrastructure;

import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfolio.domain.PortfolioPersistancePort;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioOutboxFacade;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.position.Position;
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
public class PortfolioCreatorJob {
    private final PortfolioOutboxFacade portfolioOutboxFacade;
    private final TransactionTemplate transactionTemplate;
    private final PortfolioPersistancePort portfolioPersistancePort;

    @Scheduled(fixedRate = 10000)
    public void createPortfolios() {
        Set<PortfolioOutbox> validPortfolios = portfolioOutboxFacade.findValidPortfolios();

        validPortfolios.forEach(portfolioOutbox -> {
            Portfolio portfolio = toPortfolio(portfolioOutbox);
            try {
                transactionTemplate.executeWithoutResult(status -> {
                    portfolioPersistancePort.save(portfolio);
                    portfolioOutboxFacade.markAsCreated(portfolioOutbox.getId());
                });
            } catch (Exception e) {
                log.error("Error when creating portfolio from outbox", e);
            }
        });

    }

    private static Portfolio toPortfolio(PortfolioOutbox validPortfolio) {
        Set<Position> positions = validPortfolio.getRequests().stream()
                .map(x -> new Position(new Ticker(x.ticker()), x.amount()))
                .collect(Collectors.toSet());

        return new Portfolio(positions);
    }
}
