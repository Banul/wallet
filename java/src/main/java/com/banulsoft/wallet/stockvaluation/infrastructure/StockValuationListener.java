package com.banulsoft.wallet.stockvaluation.infrastructure;

import com.banulsoft.wallet.portfoliovaluation.application.PortfolioValuationFacade;
import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.shared.kafka.StockValuationResponse;
import com.banulsoft.wallet.stockvaluation.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockValuationListener {
    private final PersistancePort persistancePort;
    private final PortfolioValuationFacade portfolioValuationFacade;

    @KafkaListener(
            id = "stock-valuation-listener",
            topics = "stock_valuation_response",
            groupId = "my-group",
            containerFactory = "kafkaListenerContainerFactoryStockValuationResponse"
    )
    public void fetchStockPrice(@Payload StockValuationResponse event) {
        Currency currency = Currency.valueOf(event.getCurrency());
        StockValuation stockValuation = new StockValuation(new Ticker(event.getTicker()), new Valuation(event.getPrice(), currency));
        persistancePort.save(stockValuation);
    }
}