package com.banulsoft.wallet.portfoliodraft.application;

import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;
import com.banulsoft.wallet.portfolio.shared.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraft;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraftPersistancePort;
import com.banulsoft.wallet.portfoliodraft.infrastructure.ExistingCompaniesService;
import com.banulsoft.wallet.portfoliodraft.infrastructure.PortfolioSendToQueueCommand;
import com.banulsoft.wallet.portfoliooutbox.application.PortfolioOutboxFacade;
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
    private final PortfolioDraftPersistancePort persistencePort;
    private final ExistingCompaniesService existingCompaniesService;
    private final PortfolioOutboxFacade portfolioOutboxFacade;

    @Transactional
    public void create(PortfolioCreateCommand portfolioCreateCommand) {
        PortfolioDraft portfolioDraft = new PortfolioDraft(null, portfolioCreateCommand.name(), portfolioCreateCommand.assets());
        PortfolioDraft savedEntity = persistencePort.save(portfolioDraft);
        Set<String> tickers = portfolioDraft.getAssetsCreationRequests().stream()
                .map(AssetCreateCommand::ticker)
                .collect(Collectors.toSet());

        if (!existingCompaniesService.allNamesExist(tickers)) {
            // todo - send only ones that not exists (not all)
            portfolioOutboxFacade.send(new PortfolioSendToQueueCommand(portfolioCreateCommand.assets(), portfolioCreateCommand.name(), savedEntity.getId()));
        } else {
            portfolioDraft.markAsReadyForProcessing();
            persistencePort.save(portfolioDraft);
        }
    }

    // valid portfolio means that is has only existing companies names
    @Transactional
    public Set<PortfolioDraft> findValidPortfolios() {
        Set<PortfolioDraft> validPortfolios = new HashSet<>();
        Set<PortfolioDraft> readyForProcessing = persistencePort.findPending();
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
        persistencePort.markAsCreated(id);
        portfolioOutboxFacade.markAsConsumed(id);
    }
}
