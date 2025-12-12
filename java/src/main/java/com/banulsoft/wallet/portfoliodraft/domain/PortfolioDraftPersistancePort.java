package com.banulsoft.wallet.portfoliodraft.domain;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PortfolioDraftPersistancePort {
    PortfolioDraft save(PortfolioDraft portfolioDraft);
    Set<PortfolioDraft> findPending();

    void markAsCreated(UUID id);
}