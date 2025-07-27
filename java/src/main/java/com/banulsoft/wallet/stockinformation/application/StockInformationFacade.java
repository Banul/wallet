package com.banulsoft.wallet.stockinformation.application;

import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.stockinformation.domain.StockInformation;
import com.banulsoft.wallet.stockinformation.domain.StockInformationReadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StockInformationFacade {
    private final StockInformationReadPort stockInformationReadPort;
    public Set<StockInformation> findByTickers(Set<Ticker> tickers) {
       return stockInformationReadPort.fetchInformationForTickers(tickers);
    }
}