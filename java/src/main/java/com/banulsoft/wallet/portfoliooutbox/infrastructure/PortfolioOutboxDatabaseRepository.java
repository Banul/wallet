package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class PortfolioOutboxDatabaseRepository implements PortfolioOutboxRepository {
    private final PortfolioOutboxRepository portfolioOutboxJpaRepository;

    public PortfolioOutboxDatabaseRepository(PortfolioOutboxRepository portfolioOutboxJpaRepository) {
        this.portfolioOutboxJpaRepository = portfolioOutboxJpaRepository;
    }

    @Override
    public PortfolioOutbox save(PortfolioOutbox portfolio) {
        return portfolioOutboxJpaRepository.save(portfolio);
    }
}
