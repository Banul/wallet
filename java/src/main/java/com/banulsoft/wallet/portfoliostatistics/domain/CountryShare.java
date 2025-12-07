package com.banulsoft.wallet.portfoliostatistics.domain;

import com.banulsoft.wallet.shared.CountryCode;
import com.banulsoft.wallet.shared.Percent;

import java.util.Objects;

public record CountryShare(CountryCode countryCode, Percent percent) {
    public CountryShare {
        Objects.requireNonNull(countryCode);
        Objects.requireNonNull(percent);
    }
}
