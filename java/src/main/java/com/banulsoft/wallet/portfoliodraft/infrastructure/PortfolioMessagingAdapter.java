package com.banulsoft.wallet.portfoliodraft.infrastructure;

import com.banulsoft.wallet.portfoliodraft.domain.TickerExistenceInformation;
import com.banulsoft.wallet.shared.kafka.TickerDetailsResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
class PortfolioMessagingAdapter {
    private final ExistingCompaniesService existingCompaniesService;

    @KafkaListener(
            id = "stock-listener",
            topics = "stock_tracking_response",
            groupId = "my-group",
            containerFactory = "kafkaListenerContainerFactoryPortfolioCreationResponse"
    )
    @Transactional
    public void handleTicker(@Payload TickerDetailsResponse event) {
        existingCompaniesService.handleTicker(new TickerExistenceInformation(event));
    }
}