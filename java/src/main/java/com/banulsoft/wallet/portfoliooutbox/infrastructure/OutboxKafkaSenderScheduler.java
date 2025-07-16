package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.*;
import com.banulsoft.wallet.shared.Ticker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class OutboxKafkaSenderScheduler {
    private final PersistancePort persistancePort;
    private final MessagingPort messagingPort;

    public OutboxKafkaSenderScheduler(PersistancePort persistancePort, MessagingPort messagingPort) {
        this.persistancePort = persistancePort;
        this.messagingPort = messagingPort;
    }

    @Scheduled(fixedRate = 10000)
    public void sendToKafka() {
        Set<PortfolioOutbox> readyForProcessing = persistancePort.findNotSendToKafka();
        readyForProcessing.forEach(entry -> {
            Set<Ticker> tickers = entry.getRequests().stream().map(AssetsCreationRequest::ticker)
                    .collect(Collectors.toSet())
                    .stream()
                    .map(Ticker::new)
                    .collect(Collectors.toSet());

            PortfolioCreationRequestedEvent event = new PortfolioCreationRequestedEvent(entry.getId(), tickers);

            messagingPort.sendPortfolioRequest(event);
            messagingPort.markAsSent(event);
        });
    }
}