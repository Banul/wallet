package com.banulsoft.wallet.portfoliooutbox.application;

import com.banulsoft.wallet.existingcompanies.ExistingCompaniesFacade;
import com.banulsoft.wallet.portfoliooutbox.domain.AssetsCreationRequest;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PortfolioOutboxFacade {
    private final PersistancePort persistancePort;
    private final ExistingCompaniesFacade existingCompaniesFacade;

    public PortfolioOutboxFacade(PersistancePort persistancePort, ExistingCompaniesFacade existingCompaniesFacade) {
        this.persistancePort = persistancePort;
        this.existingCompaniesFacade = existingCompaniesFacade;
    }

    public PortfolioOutbox create(PortfolioCreateCommand portfolioCreateCommand) {
        PortfolioOutbox portfolioOutbox = new PortfolioOutbox(portfolioCreateCommand);
        return persistancePort.save(portfolioOutbox);
    }

    // valid portfolio means that is has only existing company names
    public Set<PortfolioOutbox> findValidPortfolios() {
        Set<PortfolioOutbox> validPortfolios = new HashSet<>();
        Set<PortfolioOutbox> readyForProcessing = persistancePort.findSentToKafka();
        readyForProcessing.forEach(portfolioOutbox -> {
            Set<String> requestedTickers = portfolioOutbox.getRequests().stream().map(AssetsCreationRequest::ticker).collect(Collectors.toSet());
            if (existingCompaniesFacade.allNamesExist(requestedTickers)) {
                validPortfolios.add(portfolioOutbox);
            }
        });

        return validPortfolios;
    }

    public void markAsCreated(UUID id) {
        persistancePort.marAsCreated(id);
    }
}
