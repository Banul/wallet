package com.banulsoft.wallet.portfoliovalue.web;

import com.banulsoft.wallet.stockvaluation.domain.Currency;

import java.math.BigDecimal;

public record ValueResponseDto(BigDecimal value, Currency currency) { }
