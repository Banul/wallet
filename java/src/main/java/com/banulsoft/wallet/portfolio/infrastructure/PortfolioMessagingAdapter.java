package com.banulsoft.wallet.portfolio.infrastructure;

import com.banulsoft.wallet.shared.kafka.PositionCreationResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

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
    public void handlePosition(@Payload PositionCreationResponse event) {
        String sql = "INSERT INTO searched_companies (id, ticker, does_exist) VALUES (?, ?, ?) ON CONFLICT (ticker) DO NOTHING";
        jdbcTemplate.update(sql, UUID.randomUUID(), event.getTicker(), Objects.equals(event.getStatus(), "OK"));
    }
}