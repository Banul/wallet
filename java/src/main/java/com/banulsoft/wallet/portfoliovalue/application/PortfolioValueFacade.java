package com.banulsoft.wallet.portfoliovalue.application;

import com.banulsoft.wallet.portfolio.application.PortfolioBaseInformation;
import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfoliovalue.domain.PortfolioValue;
import com.banulsoft.wallet.position.Position;
import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.stockvaluation.application.StockValuationFacade;
import com.banulsoft.wallet.stockvaluation.domain.Currency;
import com.banulsoft.wallet.stockvaluation.domain.StockValuation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioValueFacade {
    private final BigDecimal USD_VAL = BigDecimal.valueOf(3.65);
    private final BigDecimal EUR_VAL = BigDecimal.valueOf(4.25);
    private final BigDecimal DKK_VAL = BigDecimal.valueOf(0.57);
    private final PortfolioFacade portfolioFacade;
    private final StockValuationFacade stockValuationFacade;

    // todo - for now just in pln, enable more currencies
    public PortfolioValue calculate(UUID portfolioId) {
        Portfolio portfolio = portfolioFacade.findById(portfolioId);
        Set<Position> positions = portfolio.getPositions();
        Set<Ticker> tickers = positions.stream().map(Position::getTicker).collect(Collectors.toSet());
        Set<StockValuation> stockValuations = stockValuationFacade.findByTickers(tickers);
        Map<Ticker, Double> amountPerTicker = positions.stream()
                .collect(Collectors.toMap(Position::getTicker, Position::getAmount));

        BigDecimal priceInPln = stockValuations.stream().map(val -> {
            Double amount = amountPerTicker.get(val.getTicker());
            BigDecimal multiplier = switch (val.getPrice().currency()) {
                case PLN -> BigDecimal.ONE;
                case USD -> USD_VAL;
                case EUR -> EUR_VAL;
                case DKK -> DKK_VAL;
                case UNKNOWN -> throw new IllegalStateException();
            };
            return BigDecimal.valueOf(amount).multiply(multiplier).multiply(val.price());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PortfolioValue(priceInPln);
    }

    public List<PortfolioBaseInformation> getBaseInformation() {
        List<PortfolioBaseInformation> portfolioBaseInformation = new ArrayList<>();
        List<Portfolio> portfolios = portfolioFacade.findAll();
        for (Portfolio portfolio : portfolios) {
            PortfolioValue portfolioValue = calculate(portfolio.getId());
            portfolioBaseInformation.add(new PortfolioBaseInformation(portfolio.getId(), portfolio.getName(), portfolioValue.getPrice(), Currency.PLN));
        }

        return portfolioBaseInformation;
    }
}
