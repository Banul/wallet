package com.banulsoft.wallet.portfolio.infrastructure;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMessagingAdapter {

    @KafkaListener(
            id = "stock-listener",
            topics = "stock_tracking_response",
            groupId = "my-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleStockPrice(@Payload PositionCreationResponse event) {
        System.out.println("Received stock update: " + event.getTicker()
                + "Status is: " + event.getStatus());
        // Tutaj dodaj logikę przetwarzania (np. zapis do bazy, aktualizacja portfela)
    }
}