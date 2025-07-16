package com.banulsoft.wallet.stockValuation.domain;

import java.math.BigDecimal;

public record Price(BigDecimal price, Currency currency) { }
