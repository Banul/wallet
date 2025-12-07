package com.banulsoft.wallet.portfoliostatistics.domain;

import com.banulsoft.wallet.shared.Percent;

import java.util.List;

public class PortfolioStatistics {
    private final List<CountryShare> countryShares;
    private List<IndustryShare> industryShares;

    public PortfolioStatistics(List<CountryShare> countryShares, List<IndustryShare> industryShares) {
        this.countryShares = countryShares;
        this.industryShares = industryShares;
    }

    public List<CountryShare> getCountryShares() {
        return countryShares;
    }

    public List<IndustryShare> getIndustryShares() {
        return industryShares;
    }
}