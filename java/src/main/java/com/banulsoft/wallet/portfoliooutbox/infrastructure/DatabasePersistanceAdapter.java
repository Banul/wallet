package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
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
        PortfolioOutboxEntity entity = new PortfolioOutboxEntity(portfolio);
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
    public void markAsConsumed(UUID draftId) {
        portfolioOutboxJpaRepository.markAsConsumed(draftId);
    }


    @Override
    public Set<PortfolioOutbox> findNotSendToKafka() {
        Set<PortfolioOutboxEntity> readyToBeSend = portfolioOutboxJpaRepository.findReadyForProcessing();
        return readyToBeSend
                .stream()
                .map(x -> new PortfolioOutbox(x.getId(), x.getName(), toCreateCommand(x)))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PortfolioOutbox> findSentToKafka() {
        Set<PortfolioOutboxEntity> sent = portfolioOutboxJpaRepository.findSent();
        return sent.stream()
                .map(x -> new PortfolioOutbox(x.getId(), x.getName(), toCreateCommand(x)))
                .collect(Collectors.toSet());
    }

    private Set<AssetCreateCommand> toCreateCommand(PortfolioOutboxEntity x) {
        return x.getAssets().stream().map(z -> new AssetCreateCommand(z.ticker(), z.amount())).collect(Collectors.toSet());
    }
}