package com.banulsoft.wallet.portfoliovaluation.application;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.domain.*;
import com.banulsoft.wallet.portfoliovaluation.domain.PortfolioValuation;
import com.banulsoft.wallet.portfoliovaluation.domain.PortfolioValuationBaseInformation;
import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.stockvaluation.application.StockValuationFacade;
import com.banulsoft.wallet.stockvaluation.domain.Currency;
import com.banulsoft.wallet.stockvaluation.domain.StockValuation;
import com.banulsoft.wallet.stockvaluation.domain.Valuation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PortfolioValuationFacade {
    private final BigDecimal USD_VAL = BigDecimal.valueOf(3.65);
    private final BigDecimal EUR_VAL = BigDecimal.valueOf(4.25);
    private final BigDecimal DKK_VAL = BigDecimal.valueOf(0.57);
    private final PortfolioFacade portfolioFacade;
    private final StockValuationFacade stockValuationFacade;

    public List<PortfolioValuation> getValuationForAllPortfolios() {
        List<PortfolioValuation> portfolioValuations = new ArrayList<>();
        List<Portfolio> portfolios = portfolioFacade.findAll();
        for (Portfolio portfolio : portfolios) {
            BigDecimal portfolioValue = calculate(portfolio.getId());
            portfolioValuations.add(new PortfolioValuation(new Valuation(portfolioValue, Currency.PLN), new PortfolioValuationBaseInformation(new PortfolioId(portfolio.getId()), portfolio.getName())));
        }

        return portfolioValuations;
    }

    public PortfolioValuation getValuationForPortfolio(UUID portfolioId) {
        Portfolio portfolio = portfolioFacade.findById(portfolioId);
        BigDecimal portfolioValue = calculate(portfolioId);
        return new PortfolioValuation(
                new Valuation(portfolioValue, Currency.PLN),
                new PortfolioValuationBaseInformation(new PortfolioId(portfolioId), portfolio.getName()));
    }

    public List<TickerValue> valuesPerTicker(PortfolioId portfolioId) {
        return valuePerTicker(portfolioId).toList();
    }

    // todo - for now just in pln, enable more currencies
    private BigDecimal calculate(UUID portfolioId) {
        return valuePerTicker(new PortfolioId(portfolioId))
                .map(TickerValue::value)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Stream<TickerValue> valuePerTicker(PortfolioId portfolioId) {
        Portfolio portfolio = portfolioFacade.findById(portfolioId.id());
        Set<Position> positions = portfolio.getPositions();
        Set<Ticker> tickers = positions.stream().map(Position::getTicker).collect(Collectors.toSet());
        Set<StockValuation> stockValuations = stockValuationFacade.getValuationForTickers(tickers);
        Map<Ticker, Double> amountPerTicker = positions.stream()
                .collect(Collectors.toMap(Position::getTicker, Position::getAmount));

        return stockValuations.stream().map(val -> {
            Double amount = amountPerTicker.get(val.getTicker());
            BigDecimal multiplier = switch (val.getPrice().currency()) {
                case PLN -> BigDecimal.ONE;
                case USD -> USD_VAL;
                case EUR -> EUR_VAL;
                case DKK -> DKK_VAL;
                case UNKNOWN -> throw new IllegalStateException();
            };
            BigDecimal moneyInvestedInTicker = BigDecimal.valueOf(amount).multiply(multiplier).multiply(val.price());
            return new TickerValue(val.getTicker(), moneyInvestedInTicker);
        });
    }
}
