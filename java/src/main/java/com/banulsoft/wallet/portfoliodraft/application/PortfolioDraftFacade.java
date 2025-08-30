package com.banulsoft.wallet.portfoliodraft.application;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;
import com.banulsoft.wallet.portfolio.shared.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraft;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraftPersistancePort;
import com.banulsoft.wallet.portfoliodraft.infrastructure.ExistingCompaniesService;
import com.banulsoft.wallet.portfoliodraft.infrastructure.PortfolioSendToQueueCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioOutboxFacade;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioDraftFacade {
    private final PortfolioDraftPersistancePort persistancePort;
    private final ExistingCompaniesService existingCompaniesService;
    private final PortfolioOutboxFacade portfolioOutboxFacade;
    private final PortfolioFacade portfolioFacade;

    @Transactional
    public void create(PortfolioCreateCommand portfolioCreateCommand) {
        PortfolioDraft portfolioDraft = new PortfolioDraft(null, portfolioCreateCommand.name(), portfolioCreateCommand.assets());
        PortfolioDraft savedEntity = persistancePort.save(portfolioDraft);
        Set<String> tickers = portfolioDraft.getAssetsCreationRequests().stream()
                .map(AssetCreateCommand::ticker)
                .collect(Collectors.toSet());

        if (!existingCompaniesService.allNamesExist(tickers)) {
            portfolioOutboxFacade.send(new PortfolioSendToQueueCommand(portfolioCreateCommand.assets(), portfolioCreateCommand.name(), savedEntity.getId()));
        } else {
            Portfolio portfolio = portfolioDraft.toPortfolio();
            portfolioFacade.create(portfolio);
        }
    }

    // valid portfolio means that is has only existing companies names
    public Set<PortfolioDraft> findValidPortfolios() {
        Set<PortfolioDraft> validPortfolios = new HashSet<>();
        Set<PortfolioDraft> readyForProcessing = persistancePort.findSentToQueue();
        readyForProcessing.forEach(draft -> {
            Set<String> requestedTickers = draft.getAssetsCreationRequests().stream()
                    .map(AssetCreateCommand::ticker)
                    .collect(Collectors.toSet());
            if (existingCompaniesService.allNamesExist(requestedTickers)) {
                validPortfolios.add(draft);
            }
        });

        return validPortfolios;
    }

    @Transactional
    public void markAsCreated(UUID id) {
        PortfolioDraft portfolioDraft = persistancePort.findById(id).orElseThrow(EntityNotFoundException::new);
        portfolioDraft.markAsCreated();
        persistancePort.save(portfolioDraft);
    }
}
