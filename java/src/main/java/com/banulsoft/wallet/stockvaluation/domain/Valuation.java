package com.banulsoft.wallet.stockvaluation.domain;

import java.math.BigDecimal;

public record Valuation(BigDecimal price, Currency currency) { }
