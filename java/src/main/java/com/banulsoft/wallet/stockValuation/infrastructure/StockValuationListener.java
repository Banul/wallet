package com.banulsoft.wallet.stockValuation.infrastructure;

import com.banulsoft.wallet.shared.kafka.PositionCreationResponse;
import com.banulsoft.wallet.shared.kafka.StockValuationResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class StockValuationListener {

    @KafkaListener(
            id = "stock-valuation-listener",
            topics = "stock_valuation_response",
            groupId = "my-group",
            containerFactory = "kafkaListenerContainerFactoryStockValuationResponse"
    )
    public void fetchStockPrice(@Payload StockValuationResponse event) {
        System.out.println(event);
    }
}