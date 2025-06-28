package com.banulsoft.wallet.portfolio.domain;

import com.banulsoft.wallet.position.Position;
import lombok.Getter;

import java.util.List;

@Getter
public class Portfolio {
    private List<Position> positions;
}
