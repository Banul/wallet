package com.banulsoft.wallet.portfolio.application;

import com.banulsoft.wallet.portfolio.application.exception.PortfolioNotExistsException;
import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfolio.domain.PortfolioId;
import com.banulsoft.wallet.portfolio.domain.PortfolioPersistancePort;
import com.banulsoft.wallet.portfolio.domain.TickerValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioFacade {
    private final PortfolioPersistancePort portfolioPersistancePort;
    private final PortfolioValueService portfolioValueService;
    public Portfolio findById(UUID portfolioId) {
       return portfolioPersistancePort.findById(portfolioId).orElseThrow(PortfolioNotExistsException::new);
    }
    public List<PortfolioBaseInformation> getBaseInformationForAllPortfolios() {
        return portfolioValueService.getValuationForAllPortfolios();
    }
    public Optional<PortfolioBaseInformation> getBaseInformationForPortfolio(UUID id) {
        return portfolioValueService.getValuationForPortfolio(id);
    }
    public List<TickerValue> getTickerValuesForPortfolio(PortfolioId portfolioId) {
        return portfolioValueService.valuesPerTicker(portfolioId);
    }
    
    public void create(Portfolio portfolio) {
        portfolioPersistancePort.save(portfolio);
    }
}