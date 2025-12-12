package com.banulsoft.wallet.portfoliostatistics.domain;

import java.util.List;

public class PortfolioStatistics {
    private final List<CountryShare> countryShares;
    private List<SectorShare> sectorShares;

    public PortfolioStatistics(List<CountryShare> countryShares, List<SectorShare> sectorShares) {
        this.countryShares = countryShares;
        this.sectorShares = sectorShares;
    }

    public List<CountryShare> getCountryShares() {
        return countryShares;
    }

    public List<SectorShare> getIndustryShares() {
        return sectorShares;
    }
}