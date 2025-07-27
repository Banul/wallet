package com.banulsoft.wallet.portfolio.infrastructure;

import com.banulsoft.wallet.shared.kafka.TickerDetailsResponse;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Component
class PortfolioMessagingAdapter {
    private final JdbcTemplate jdbcTemplate;

    public PortfolioMessagingAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @KafkaListener(
            id = "stock-listener",
            topics = "stock_tracking_response",
            groupId = "my-group",
            containerFactory = "kafkaListenerContainerFactoryPortfolioCreationResponse"
    )
    @Transactional
    public void handleTicker(@Payload TickerDetailsResponse event) {
        boolean tickerExists = Objects.equals(event.getStatus(), "OK");
        String sql = "INSERT INTO searched_companies (id, ticker, does_exist) VALUES (?, ?, ?) ON CONFLICT (ticker) DO NOTHING";
        jdbcTemplate.update(sql, UUID.randomUUID(), event.getTicker(), tickerExists);
        if (tickerExists) {
            String sql2 = "INSERT INTO company_details (id, ticker, country, industry, sector, created_date) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql2, UUID.randomUUID(), event.getTicker(), event.getCountry(), event.getIndustry(), event.getSector(), Timestamp.from(Instant.now()));
        }
    }
}