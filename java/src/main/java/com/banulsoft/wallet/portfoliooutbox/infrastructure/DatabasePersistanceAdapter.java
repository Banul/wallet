package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import org.springframework.jdbc.core.JdbcTemplate;
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
        PortfolioOutboxEntity entity = new PortfolioOutboxEntity(portfolio.getRequests());
        portfolioOutboxJpaRepository.save(entity);
        return portfolio;
    }

    @Override
    public void markAsFailure(UUID requestId) {
        portfolioOutboxJpaRepository.markAsFailure(requestId);
    }

    @Override
    public void markAsSent(UUID requestId) {
        portfolioOutboxJpaRepository.markAsSent(requestId);
    }

    @Override
    public void marAsCreated(UUID requestId) {
        portfolioOutboxJpaRepository.markAsCreated(requestId);
    }

    @Override
    public Set<PortfolioOutbox> findNotSendToKafka() {
        Set<PortfolioOutboxEntity> readyToBeSend = portfolioOutboxJpaRepository.findReadyForProcessing();
        return readyToBeSend
                .stream()
                .map(x -> new PortfolioOutbox(x.getId(), x.getAssets()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PortfolioOutbox> findSentToKafka() {
        Set<PortfolioOutboxEntity> sent = portfolioOutboxJpaRepository.findSent();
        return sent.stream()
                .map(x -> new PortfolioOutbox(x.getId(), x.getAssets()))
                .collect(Collectors.toSet());
    }
}