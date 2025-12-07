package com.banulsoft.wallet.portfolio.domain;

import com.banulsoft.wallet.shared.Ticker;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class Portfolio {
    private final String name;
    private UUID id;
    private final Set<Position> positions;

    public Portfolio(UUID id, Set<Position> positions, String name) {
        this.id = id;
        this.positions = positions;
        this.name = name;
    }


    // todo - add some rules of amount etc
    public Portfolio(Set<Position> positions, String name) {
        this.positions = positions;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Set<Ticker> getTickers() {
        return positions.stream().map(Position::getTicker).collect(Collectors.toSet());
    }
}
