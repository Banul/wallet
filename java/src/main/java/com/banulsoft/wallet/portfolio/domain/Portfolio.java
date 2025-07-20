package com.banulsoft.wallet.portfolio.domain;

import com.banulsoft.wallet.position.Position;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
public class Portfolio {
    private String name;
    private UUID id;
    private final Set<Position> positions;

    public Portfolio(UUID id, Set<Position> positions, String name) {
        this.id = id;
        this.positions = positions;
        this.name = name;
    }

    public Portfolio(Set<Position> positions, String name) {
        this.positions = positions;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
