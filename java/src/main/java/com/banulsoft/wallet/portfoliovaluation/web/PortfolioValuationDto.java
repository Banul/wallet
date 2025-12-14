package com.banulsoft.wallet.portfoliovaluation.web;

import java.math.BigDecimal;
import java.util.UUID;

record PortfolioValuationDto(UUID id, String name, BigDecimal value) { }
