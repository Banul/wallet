package com.banulsoft.wallet.stockValuation.infrastructure;

import com.banulsoft.wallet.shared.kafka.StockValuationResponse;
import com.banulsoft.wallet.stockValuation.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockValuationListener {
    private final PersistancePort persistancePort;

    @KafkaListener(
            id = "stock-valuation-listener",
            topics = "stock_valuation_response",
            groupId = "my-group",
            containerFactory = "kafkaListenerContainerFactoryStockValuationResponse"
    )
    public void fetchStockPrice(@Payload StockValuationResponse event) {
        Currency currency = Currency.valueOf(event.getCurrency());
        StockValuation stockValuation = new StockValuation(new Ticker(event.getTicker()), new Price(event.getPrice(), currency));
        persistancePort.save(stockValuation);
    }
}