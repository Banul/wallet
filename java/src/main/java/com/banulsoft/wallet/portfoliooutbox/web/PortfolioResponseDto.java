package com.banulsoft.wallet.portfoliooutbox.web;

import java.math.BigDecimal;
import java.util.UUID;

record PortfolioResponseDto(UUID id, String name, BigDecimal value) { }
