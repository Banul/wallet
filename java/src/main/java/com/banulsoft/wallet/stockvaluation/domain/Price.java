package com.banulsoft.wallet.stockvaluation.domain;

import java.math.BigDecimal;

public record Price(BigDecimal price, Currency currency) { }
