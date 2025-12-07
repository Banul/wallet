package com.banulsoft.wallet.portfoliostatistics.domain;

import com.banulsoft.wallet.shared.Industry;
import com.banulsoft.wallet.shared.Percent;

import java.util.Objects;

public record IndustryShare(Industry industry, Percent percent) {
    public IndustryShare {
        Objects.requireNonNull(industry);
        Objects.requireNonNull(percent);
    }
}
