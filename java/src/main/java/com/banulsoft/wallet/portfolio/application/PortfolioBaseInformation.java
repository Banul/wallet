package com.banulsoft.wallet.portfolio.application;

import com.banulsoft.wallet.stockvaluation.domain.Currency;

import java.math.BigDecimal;
import java.util.UUID;

public record PortfolioBaseInformation(UUID id,
                                       String name,
                                       BigDecimal value,
                                       Currency currency) {
}
