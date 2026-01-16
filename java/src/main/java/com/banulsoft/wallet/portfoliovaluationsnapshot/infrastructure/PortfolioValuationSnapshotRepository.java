package com.banulsoft.wallet.portfoliovaluationsnapshot.infrastructure;

import com.banulsoft.wallet.portfoliovaluationsnapshot.domain.ValuationSnapshot;
import com.banulsoft.wallet.portfoliovaluationsnapshot.domain.ValuationSnapshotPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class PortfolioValuationSnapshotRepository implements ValuationSnapshotPersistencePort {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(List<ValuationSnapshot> snapshots) {
        List<PortfolioValuationSnapshotEntity> snapshotEntities = snapshots.stream().map(s -> {
            PortfolioValuationSnapshotEntity portfolioValuationSnapshotEntity = new PortfolioValuationSnapshotEntity();
            portfolioValuationSnapshotEntity.setValuation(s.price());
            portfolioValuationSnapshotEntity.setCurrency(s.currency().name());
            portfolioValuationSnapshotEntity.setPortfolioId(s.portfolioId().id());
            return portfolioValuationSnapshotEntity;
        }).toList();

        if (snapshots.isEmpty()) {
            return;
        }

        String sql = """
                INSERT INTO portfolio_valuation_snapshot 
                (id, portfolio_id, valuation, currency, created_date) 
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.batchUpdate(sql,
                snapshotEntities,
                100,
                (PreparedStatement ps, PortfolioValuationSnapshotEntity snapshot) -> {
                    ps.setObject(1, UUID.randomUUID());
                    ps.setObject(2, snapshot.getPortfolioId());
                    ps.setBigDecimal(3, snapshot.getValuation());
                    ps.setString(4, snapshot.getCurrency());
                    ps.setTimestamp(5, Timestamp.from(snapshot.getCreatedDate()));
                }
        );
    }
}