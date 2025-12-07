package com.banulsoft.wallet.portfoliostatistics.web;

import java.math.BigDecimal;
record CountryShareDto(
        String countryCode,
        String countryName,
        BigDecimal percentageValue
) {}