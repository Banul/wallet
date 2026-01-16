package com.banulsoft.wallet.portfoliostatistics.domain;

import com.banulsoft.wallet.shared.SectorName;
import com.banulsoft.wallet.shared.Percent;

import java.util.Objects;

public record SectorShare(SectorName sectorName, Percent percent) {
    public SectorShare {
        Objects.requireNonNull(sectorName);
        Objects.requireNonNull(percent);
    }
}
