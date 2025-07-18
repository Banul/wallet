package com.banulsoft.wallet.portfolio.domain;

import java.util.Optional;
import java.util.UUID;

public interface PortfolioPersistancePort {
    void save(Portfolio portfolio);
    Optional<Portfolio> findById(UUID portfolioId);
}
