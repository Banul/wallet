package com.banulsoft.wallet.portfoliovaluation.infrastructure;

import com.banulsoft.wallet.portfoliovaluation.domain.PortfolioValuation;
import com.banulsoft.wallet.portfoliovaluation.domain.PortfolioValuationPort;
import com.banulsoft.wallet.stockvaluation.domain.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 *
 */
@Component
@RequiredArgsConstructor
class PortfolioValuationRepository implements PortfolioValuationPort {
    private final JdbcTemplate jdbcTemplate;

    public void save(PortfolioValuation portfolioValuation) {
        saveSnapshot(portfolioValuation);
    }

    private void saveSnapshot(PortfolioValuation portfolioValuation) {
        String sql = """
        INSERT INTO portfolio_valuation_snapshot (id, portfolio_id, valuation, currency, created_date)
        VALUES (?, ?, ?, ?, ?)
        """;

        UUID id = UUID.randomUUID();
        UUID portfolioId = portfolioValuation.baseInformation().portfolioId().id();
        BigDecimal valuation = portfolioValuation.valuation().price();

        // todo - for now hardcoded, but user should be able to choose
        Currency currency = Currency.PLN;
        Instant createdDate = Instant.now();

        jdbcTemplate.update(sql, id, portfolioId, valuation, currency, createdDate);
    }
}
