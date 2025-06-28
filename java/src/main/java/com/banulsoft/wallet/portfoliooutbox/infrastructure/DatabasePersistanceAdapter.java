package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.Asset;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
class DatabasePersistanceAdapter implements PersistancePort {
    private final PortfolioOutboxJpaRepository portfolioOutboxJpaRepository;

    public DatabasePersistanceAdapter(PortfolioOutboxJpaRepository portfolioOutboxJpaRepository) {
        this.portfolioOutboxJpaRepository = portfolioOutboxJpaRepository;
    }

    @Override
    public PortfolioOutbox save(PortfolioOutbox portfolio) {
        PortfolioOutboxEntity entity = new PortfolioOutboxEntity(portfolio.getAssets());
        portfolioOutboxJpaRepository.save(entity);
        return portfolio;
    }

    @Override
    public void markAsSuccess(UUID requestId) {
        portfolioOutboxJpaRepository.markAsSuccess(requestId);
    }

    @Override
    public void markAsFailure(UUID requestId) {
        portfolioOutboxJpaRepository.markAsFailure(requestId);
    }

    @Override
    public Set<PortfolioOutbox> findReadyForProcessing() {
        Set<PortfolioOutboxEntity> readyForProcessing = portfolioOutboxJpaRepository.findReadyForProcessing();
        return readyForProcessing
                .stream()
                .map(x -> new PortfolioOutbox(x.getId(), x.getAssets()))
                .collect(Collectors.toSet());
    }
}