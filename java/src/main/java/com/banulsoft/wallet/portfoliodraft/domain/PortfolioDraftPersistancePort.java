package com.banulsoft.wallet.portfoliodraft.domain;

import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PortfolioDraftPersistancePort {
    PortfolioDraft save(PortfolioDraft portfolioDraft);
    Set<PortfolioDraft> findSentToQueue();

    Optional<PortfolioDraft> findById(UUID id);
}