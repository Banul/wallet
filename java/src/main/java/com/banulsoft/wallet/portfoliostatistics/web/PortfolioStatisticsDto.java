package com.banulsoft.wallet.portfoliostatistics.web;

import java.util.List;
record PortfolioStatisticsDto(
        List<CountryShareDto> countryShares,
        List<IndustryShareDto> industryShares
) {}