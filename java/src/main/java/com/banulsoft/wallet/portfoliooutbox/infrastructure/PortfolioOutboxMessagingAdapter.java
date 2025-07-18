package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.MessagingPort;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
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
    @Transactional
    public void sendPortfolioRequest(PortfolioCreationRequestedEvent portfolioCreationRequestedEvent) {
        CompletableFuture<SendResult<String, PortfolioCreationRequestedEvent>> completableFuture = kafkaTemplate.send(topic, portfolioCreationRequestedEvent);
        completableFuture.whenComplete((result, ex) -> {
            if (ex == null) {
                transactionTemplate.executeWithoutResult(status -> {
                    persistancePort.markAsSent(portfolioCreationRequestedEvent.requestId());
                });
            } else {
                persistancePort.markAsFailure(portfolioCreationRequestedEvent.requestId());
            }
        });
    }

    @Override
    @Transactional
    public void markAsSent(PortfolioCreationRequestedEvent portfolioCreationRequestedEvent) {
        persistancePort.markAsSent(portfolioCreationRequestedEvent.requestId());
    }
}
