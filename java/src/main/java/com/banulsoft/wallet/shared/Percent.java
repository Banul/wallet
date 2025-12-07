package com.banulsoft.wallet.shared;

import com.banulsoft.wallet.shared.exception.PercentCannotBeAboveHundredException;
import com.banulsoft.wallet.shared.exception.PercentCannotBeBelowHundredException;

import java.math.BigDecimal;

/**
 * Shared object representing percent value
 */
public record Percent(BigDecimal value) {
    public Percent {
        if (value.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new PercentCannotBeAboveHundredException();
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new PercentCannotBeBelowHundredException();
        }

    }
}
