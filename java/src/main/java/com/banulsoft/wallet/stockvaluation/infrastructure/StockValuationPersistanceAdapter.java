package com.banulsoft.wallet.stockvaluation.infrastructure;

import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.stockvaluation.domain.Currency;
import com.banulsoft.wallet.stockvaluation.domain.PersistancePort;
import com.banulsoft.wallet.stockvaluation.domain.Valuation;
import com.banulsoft.wallet.stockvaluation.domain.StockValuation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class StockValuationPersistanceAdapter implements PersistancePort {
    private final StockValuationHistoryJpaRepository stockValuationHistoryJpaRepository;
    private final StockValuationCurrentJpaRepository stockValuationCurrentJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public StockValuation save(StockValuation stockValuation) {
        StockValuationHistoryEntity stockValuationHistoryEntity = StockValuationHistoryEntity.from(stockValuation);
        saveHistory(stockValuationHistoryEntity);
        saveCurrent(stockValuation);
        return stockValuation;
    }

    private void saveCurrent(StockValuation stockValuation) {
        jdbcTemplate.update("""
                insert into stock_valuation_current (id, ticker, price, currency, created_date) values (?, ?, ?, ?, ?)
                on conflict (ticker) do update set
                price = EXCLUDED.price,
                created_date = EXCLUDED.created_date
                 """, UUID.randomUUID(),
                stockValuation.tickerName(),
                stockValuation.price(),
                stockValuation.currencyName(),
                Timestamp.from(Instant.now()));
    }

    private void saveHistory(StockValuationHistoryEntity stockValuationHistoryEntity) {
        stockValuationHistoryJpaRepository.save(stockValuationHistoryEntity);
    }

    @Override
    public Set<StockValuation> findByTickers(Set<Ticker> tickers) {
        Set<String> tickerNames = tickers.stream().map(Ticker::name).collect(Collectors.toSet());
        return stockValuationCurrentJpaRepository.findByTickers(tickerNames)
                .stream().map(StockValuationPersistanceAdapter::createValuation)
                .collect(Collectors.toSet());
    }

    private static StockValuation createValuation(StockValuationCurrentEntity x) {
        return new StockValuation(new Ticker(x.getTicker()), new Valuation(x.getPrice(), Currency.valueOf(x.getCurrency())));
    }
}
