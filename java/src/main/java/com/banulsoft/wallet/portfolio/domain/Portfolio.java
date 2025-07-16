package com.banulsoft.wallet.portfolio.domain;

import com.banulsoft.wallet.position.Position;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class Portfolio {
    private final Set<Position> positions;

    public Portfolio(Set<Position> positions) {
        this.positions = positions;
    }
}
