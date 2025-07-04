package com.banulsoft.wallet.portfolio.infrastructure;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMessagingAdapter {

    @KafkaListener(
            id = "stock-listener",
            topics = "portfolio_response_topic",
            groupId = "my-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleStockPrice(@Payload StockPriceEvent event) {
        System.out.println("Received stock update: " + event.getTicker()
                + " - $" + event.getPrice());
        // Tutaj dodaj logikę przetwarzania (np. zapis do bazy, aktualizacja portfela)
    }
}