package com.banulsoft.wallet.portfoliodetails.application;

import com.banulsoft.wallet.portfolio.application.PortfolioFacade;
import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfoliodetails.domain.PortfolioDetails;
import com.banulsoft.wallet.shared.Ticker;
import com.banulsoft.wallet.stockinformation.application.StockInformationFacade;
import com.banulsoft.wallet.stockinformation.domain.StockInformation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioDetailsFacade {
    private final StockInformationFacade stockInformationFacade;
    private final PortfolioFacade portfolioFacade;

    @Transactional
    public PortfolioDetails findDetails(UUID portfolioId) {
        Portfolio portfolio = portfolioFacade.findById(portfolioId);
        Set<Ticker> tickers = portfolio.getTickers();
        Set<StockInformation> stockInfo = stockInformationFacade.findByTickers(tickers);
        return new PortfolioDetails(stockInfo);
    }
}
