package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.MessagingPort;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CompletableFuture;

@Component
class PortfolioOutboxMessagingAdapter implements MessagingPort {
    private final KafkaTemplate<String, PortfolioCreationRequestedEvent> kafkaTemplate;
    private final String topic;
    private final PersistancePort persistancePort;

    private final TransactionTemplate transactionTemplate;

    public PortfolioOutboxMessagingAdapter(KafkaTemplate<String, PortfolioCreationRequestedEvent> kafkaTemplate,
                                           @Value("${kafka.topic.portfolio-creation-request}") String topic,
                                           DatabasePersistanceAdapter persistancePort,
                                           TransactionTemplate transactionTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.persistancePort = persistancePort;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void sendPortfolioRequest(PortfolioCreationRequestedEvent portfolioCreationRequestedEvent) {
        CompletableFuture<SendResult<String, PortfolioCreationRequestedEvent>> completableFuture = kafkaTemplate.send(topic, portfolioCreationRequestedEvent);
        completableFuture.whenComplete((result, ex) -> {
            if (ex == null) {
                transactionTemplate.executeWithoutResult(status -> {
                    persistancePort.markAsSuccess(portfolioCreationRequestedEvent.requestId());

                    // TODO: not here, but in listener
                    persistancePort.addTrackedCompanies(portfolioCreationRequestedEvent.tickers());
                });
            } else {
                persistancePort.markAsFailure(portfolioCreationRequestedEvent.requestId());
            }
        });
    }
}
