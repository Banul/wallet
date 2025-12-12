package com.banulsoft.wallet.portfoliostatistics.domain;

import com.banulsoft.wallet.shared.Sector;
import com.banulsoft.wallet.shared.Percent;

import java.util.Objects;

public record SectorShare(Sector sector, Percent percent) {
    public SectorShare {
        Objects.requireNonNull(sector);
        Objects.requireNonNull(percent);
    }
}
