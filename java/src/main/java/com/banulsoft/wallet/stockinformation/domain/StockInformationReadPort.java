package com.banulsoft.wallet.stockinformation.domain;

import com.banulsoft.wallet.shared.Ticker;

import java.util.Set;

public interface StockInformationReadPort {
    Set<StockInformation> fetchInformationForTickers(Set<Ticker> tickers);
}
