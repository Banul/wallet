package com.banulsoft.wallet.portfolio.infrastructure;

import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfolio.domain.PortfolioPersistancePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PortfolioPersistanceAdapter implements PortfolioPersistancePort {
    private final PortfolioJpaRepository portfolioJpaRepository;

    @Override
    public void save(Portfolio portfolio) {
        PortfolioEntity portfolioEntity = new PortfolioEntity();
        portfolioEntity.setPositions(portfolio.getPositions());
        portfolioJpaRepository.save(portfolioEntity);
    }
}
