package com.banulsoft.wallet.position.asset;

import com.banulsoft.wallet.shared.Ticker;

public class Asset {
    private Ticker ticker;
    private Type type;
    private Industry industry;

    public Ticker getTicker() {
        return ticker;
    }

    public Type getType() {
        return type;
    }

    public Industry getIndustry() {
        return industry;
    }
}
