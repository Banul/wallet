package com.banulsoft.wallet.portfoliodraft.application;

import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;
import com.banulsoft.wallet.portfolio.shared.PortfolioCreateCommand;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraft;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraftId;
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
    public UUID create(PortfolioCreateCommand portfolioCreateCommand) {
        PortfolioDraft portfolioDraft = new PortfolioDraft(null, portfolioCreateCommand.name(), portfolioCreateCommand.assets());
        PortfolioDraft savedDraft = persistencePort.save(portfolioDraft);
        Set<String> tickers = portfolioDraft.getAssetsCreationRequests().stream()
                .map(AssetCreateCommand::ticker)
                .collect(Collectors.toSet());

        if (!existingCompaniesService.allNamesExist(tickers)) {
            // todo - send only ones that not exists (not all)
            portfolioOutboxFacade.send(new PortfolioSendToQueueCommand(portfolioCreateCommand.assets(), portfolioCreateCommand.name(), savedDraft.getId()));
        } else {
            savedDraft.markAsReadyForProcessing();
            persistencePort.update(savedDraft);
        }

        return savedDraft.getId();
    }

    public PortfolioDraft findById(PortfolioDraftId id) {
        return persistencePort.findById(id).orElseThrow(PortfolioDraftNotFoundException::new);
    }

    // valid portfolio means that is has only existing companies names
    @Transactional
    public Set<PortfolioDraft> findPendingDrafts() {
        Set<PortfolioDraft> pendingPortfolios = new HashSet<>();
        Set<PortfolioDraft> readyForProcessing = persistencePort.findPending();
        readyForProcessing.forEach(draft -> {
            Set<String> requestedTickers = draft.getAssetsCreationRequests().stream()
                    .map(AssetCreateCommand::ticker)
                    .collect(Collectors.toSet());
            if (existingCompaniesService.allNamesExist(requestedTickers)) {
                pendingPortfolios.add(draft);
            }
        });

        return pendingPortfolios;
    }

    @Transactional
    public void markAsCreated(UUID id) {
        persistencePort.markAsCreated(id);
        portfolioOutboxFacade.markAsConsumed(id);
    }
}
