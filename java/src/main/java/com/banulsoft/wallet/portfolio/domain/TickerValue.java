package com.banulsoft.wallet.portfolio.domain;

import com.banulsoft.wallet.shared.Ticker;

import java.math.BigDecimal;

/**
 * Describes how much money we have invested in given ticker (company, etf)
 */
public record TickerValue(Ticker ticker, BigDecimal value) {
}