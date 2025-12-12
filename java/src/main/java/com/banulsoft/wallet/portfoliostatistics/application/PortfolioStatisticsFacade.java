package com.banulsoft.wallet.portfoliostatistics.application;

import com.banulsoft.wallet.portfolio.application.PortfolioBaseInformation;
import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.application.exception.PortfolioNotExistsException;
import com.banulsoft.wallet.portfolio.domain.PortfolioId;
import com.banulsoft.wallet.portfolio.domain.TickerValue;
import com.banulsoft.wallet.portfoliostatistics.domain.CountryShare;
import com.banulsoft.wallet.portfoliostatistics.domain.SectorShare;
import com.banulsoft.wallet.portfoliostatistics.domain.PortfolioStatistics;
import com.banulsoft.wallet.shared.CountryCode;
import com.banulsoft.wallet.shared.Sector;
import com.banulsoft.wallet.shared.Percent;
import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.stockinformation.application.StockInformationFacade;
import com.banulsoft.wallet.stockinformation.domain.StockInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class PortfolioStatisticsFacade {
    private final PortfolioFacade portfolioFacade;
    private final StockInformationFacade stockInformationFacade;

    public PortfolioStatistics calculateStatisticsForPortfolio(PortfolioId portfolioId) {
        List<TickerValue> tickerValues = portfolioFacade.getTickerValuesForPortfolio(portfolioId);
        Map<Ticker, StockInformation> stockInfoMap = fetchStockInformationMap(tickerValues);
        BigDecimal totalValue = getTotalPortfolioValue(portfolioId);

        List<CountryShare> countryShares = calculateCountryShares(tickerValues, stockInfoMap, totalValue);
        List<SectorShare> sectorShares = calculateIndustryShares(tickerValues, stockInfoMap, totalValue);

        return new PortfolioStatistics(countryShares, sectorShares);
    }

    private Map<Ticker, StockInformation> fetchStockInformationMap(List<TickerValue> tickerValues) {
        Set<Ticker> tickers = tickerValues.stream()
                .map(TickerValue::ticker)
                .collect(Collectors.toSet());

        Set<StockInformation> stockInformations = stockInformationFacade.findByTickers(tickers);

        return stockInformations.stream()
                .collect(Collectors.toMap(StockInformation::getTicker, Function.identity()));
    }

    private BigDecimal getTotalPortfolioValue(PortfolioId portfolioId) {
        return portfolioFacade.getBaseInformationForPortfolio(portfolioId.id())
                .map(PortfolioBaseInformation::getValue)
                .orElseThrow(PortfolioNotExistsException::new);
    }

    private List<CountryShare> calculateCountryShares(
            List<TickerValue> tickerValues,
            Map<Ticker, StockInformation> stockInfoMap,
            BigDecimal totalValue) {

        Map<CountryCode, BigDecimal> countryValues = aggregateValuesByCountry(tickerValues, stockInfoMap);

        return countryValues.entrySet().stream()
                .map(entry -> new CountryShare(entry.getKey(), calculatePercent(entry.getValue(), totalValue)))
                .toList();
    }

    private List<SectorShare> calculateIndustryShares(
            List<TickerValue> tickerValues,
            Map<Ticker, StockInformation> stockInfoMap,
            BigDecimal totalValue) {

        Map<Sector, BigDecimal> industryValues = aggregateValuesByIndustry(tickerValues, stockInfoMap);

        return industryValues.entrySet().stream()
                .map(entry -> new SectorShare(entry.getKey(), calculatePercent(entry.getValue(), totalValue)))
                .toList();
    }

    private Map<CountryCode, BigDecimal> aggregateValuesByCountry(
            List<TickerValue> tickerValues,
            Map<Ticker, StockInformation> stockInfoMap) {

        Map<CountryCode, BigDecimal> countryValues = new HashMap<>();

        for (TickerValue tickerValue : tickerValues) {
            StockInformation stockInfo = stockInfoMap.get(tickerValue.ticker());

            if (stockInfo == null) {
                continue;
            }

            CountryCode countryCode = CountryCode.fromYahooName(stockInfo.getCountry());
            if (countryCode != CountryCode.UNKNOWN) {
                countryValues.merge(countryCode, tickerValue.value(), BigDecimal::add);
            }
        }

        return countryValues;
    }

    private Map<Sector, BigDecimal> aggregateValuesByIndustry(
            List<TickerValue> tickerValues,
            Map<Ticker, StockInformation> stockInfoMap) {

        Map<Sector, BigDecimal> industryValues = new HashMap<>();

        for (TickerValue tickerValue : tickerValues) {
            StockInformation stockInfo = stockInfoMap.get(tickerValue.ticker());

            if (stockInfo == null) {
                continue;
            }

            String sectorName = stockInfo.getSector().getName();
            if (sectorName != null && !sectorName.isBlank()) {
                Sector sector = new Sector(sectorName);
                industryValues.merge(sector, tickerValue.value(), BigDecimal::add);
            }
        }

        return industryValues;
    }

    private Percent calculatePercent(BigDecimal part, BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            return new Percent(BigDecimal.ZERO);
        }

        BigDecimal percentage = part
                .divide(total, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));

        return new Percent(percentage);
    }
}