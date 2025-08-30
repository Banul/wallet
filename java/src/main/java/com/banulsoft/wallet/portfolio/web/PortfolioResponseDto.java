package com.banulsoft.wallet.portfolio.web;

import java.math.BigDecimal;
import java.util.UUID;

record PortfolioResponseDto(UUID id, String name, BigDecimal value) { }
