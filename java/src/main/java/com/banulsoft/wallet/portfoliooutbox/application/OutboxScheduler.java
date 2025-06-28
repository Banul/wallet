package com.banulsoft.wallet.portfoliooutbox.application;

import com.banulsoft.wallet.portfoliooutbox.domain.MessagingPort;
import com.banulsoft.wallet.portfoliooutbox.domain.PersistancePort;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;
import com.banulsoft.wallet.portfoliooutbox.infrastructure.PortfolioCreationRequestedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class OutboxScheduler {
    private final PersistancePort persistancePort;
    private final MessagingPort messagingPort;

    public OutboxScheduler(PersistancePort persistancePort, MessagingPort messagingPort) {
        this.persistancePort = persistancePort;
        this.messagingPort = messagingPort;
    }

    @Scheduled(fixedRate = 10000)
    public void sendToKafka() {
        Set<PortfolioOutbox> readyForProcessing = persistancePort.findReadyForProcessing();
        readyForProcessing.forEach(entry -> {
            PortfolioCreationRequestedEvent event = new PortfolioCreationRequestedEvent(entry.getId(), entry.getAssets());
            messagingPort.sendPortfolioRequest(event);
        });
    }

}
