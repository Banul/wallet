package com.banulsoft.wallet.stockinformation.infrastructure;

import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.stockinformation.domain.StockInformation;
import com.banulsoft.wallet.stockinformation.domain.StockInformationReadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class StockInformationReadAdapter implements StockInformationReadPort {
    private final StockInformationJpaRepository stockInformationJpaRepository;

    @Override
    public Set<StockInformation> fetchInformationForTickers(Set<Ticker> tickers) {
        Set<String> tickersAsStrings = tickers.stream()
                .map(Ticker::name)
                .collect(Collectors.toSet());

        return stockInformationJpaRepository.findByTickerIn(tickersAsStrings)
                .stream()
                .map(x -> new StockInformation(
                        new Ticker(x.getTicker()),
                        x.getSector(),
                        x.getIndustry(),
                        x.getCountry())).collect(Collectors.toSet());
    }
}