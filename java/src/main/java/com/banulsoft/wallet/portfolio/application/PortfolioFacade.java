package com.banulsoft.wallet.portfolio.application;

import com.banulsoft.wallet.portfolio.application.exception.PortfolioNotExistsException;
import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfolio.domain.PortfolioPersistancePort;
import com.banulsoft.wallet.portfoliovalue.application.PortfolioValueFacade;
import com.banulsoft.wallet.portfoliovalue.domain.PortfolioValue;
import com.banulsoft.wallet.stockvaluation.domain.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioFacade {
    private final PortfolioPersistancePort portfolioPersistancePort;
    public Portfolio findById(UUID portfolioId) {
       return portfolioPersistancePort.findById(portfolioId).orElseThrow(PortfolioNotExistsException::new);
    }

    public List<Portfolio> findAll() {
        return portfolioPersistancePort.findAll();
    }
}
