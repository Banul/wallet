package com.banulsoft.wallet.portfoliostatistics.web;

import java.math.BigDecimal;
 record IndustryShareDto(
        String industry,
        BigDecimal percentageValue
) {}
