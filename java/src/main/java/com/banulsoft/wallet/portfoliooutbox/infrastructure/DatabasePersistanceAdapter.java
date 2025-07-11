package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import com.banulsoft.wallet.shared.Ticker;
import org.hibernate.type.SqlTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
class DatabasePersistanceAdapter implements PersistancePort {
    private final PortfolioOutboxJpaRepository portfolioOutboxJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    public DatabasePersistanceAdapter(PortfolioOutboxJpaRepository portfolioOutboxJpaRepository, JdbcTemplate jdbcTemplate) {
        this.portfolioOutboxJpaRepository = portfolioOutboxJpaRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PortfolioOutbox save(PortfolioOutbox portfolio) {
        PortfolioOutboxEntity entity = new PortfolioOutboxEntity(portfolio.getRequests());
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

    @Override
    public void addTrackedCompanies(Set<Ticker> tickers) {
        String sql = "INSERT INTO tracked_companies (id, ticker) VALUES (?, ?) ON CONFLICT (ticker) DO NOTHING";
        jdbcTemplate.batchUpdate(sql, tickers, tickers.size(),
                (ps, ticker) -> {
                    ps.setObject(1, UUID.randomUUID());
                    ps.setString(2, ticker.name());
                }
        );
    }

}