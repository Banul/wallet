package com.banulsoft.wallet.portfoliostatistics.web;

import com.banulsoft.wallet.portfoliostatistics.domain.CountryShare;
import com.banulsoft.wallet.portfoliostatistics.domain.SectorShare;
import com.banulsoft.wallet.portfoliostatistics.domain.PortfolioStatistics;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PortfolioStatisticsMapper {
    public PortfolioStatisticsDto toResponse(PortfolioStatistics statistics) {
        List<CountryShareDto> countryShares = statistics.getCountryShares().stream()
                .map(this::toCountryShareDto)
                .toList();

        List<IndustryShareDto> industryShares = statistics.getIndustryShares().stream()
                .map(this::toIndustryShareDto)
                .toList();

        return new PortfolioStatisticsDto(countryShares, industryShares);
    }

    private CountryShareDto toCountryShareDto(CountryShare countryShare) {
        return new CountryShareDto(
                countryShare.countryCode().getCode(),
                countryShare.countryCode().getCountryName(),
                countryShare.percent().value()
        );
    }

    private IndustryShareDto toIndustryShareDto(SectorShare sectorShare) {
        return new IndustryShareDto(
                sectorShare.sector().industry(),
                sectorShare.percent().value()
        );
    }
}
