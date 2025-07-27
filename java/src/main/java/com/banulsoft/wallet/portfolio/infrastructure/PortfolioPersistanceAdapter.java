package com.banulsoft.wallet.portfolio.infrastructure;

import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfolio.domain.PortfolioPersistancePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class PortfolioPersistanceAdapter implements PortfolioPersistancePort {
    private final PortfolioJpaRepository portfolioJpaRepository;

    @Override
    public void save(Portfolio portfolio) {
        PortfolioEntity portfolioEntity = new PortfolioEntity();
        portfolioEntity.setPositions(portfolio.getPositions());
        portfolioEntity.setName(portfolio.getName());
        portfolioJpaRepository.save(portfolioEntity);
    }

    @Override
    public Optional<Portfolio> findById(UUID portfolioId) {
        return portfolioJpaRepository.findById(portfolioId)
                .map(x -> new Portfolio(x.getPositions(), x.getName()));
    }

    @Override
    public List<Portfolio> findAll() {
        return portfolioJpaRepository.findAll()
                .stream().map(x -> new Portfolio(x.getId(), x.getPositions(), x.getName()))
                .toList();
    }
}
