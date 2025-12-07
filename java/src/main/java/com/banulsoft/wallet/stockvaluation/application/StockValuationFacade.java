package com.banulsoft.wallet.stockvaluation.application;

import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.stockvaluation.domain.PersistancePort;
import com.banulsoft.wallet.stockvaluation.domain.StockValuation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StockValuationFacade {
    private final PersistancePort persistancePort;
    public Set<StockValuation> getValuationForTickers(Set<Ticker> tickers) {
        return persistancePort.findByTickers(tickers);
    }
}