package com.banulsoft.wallet.position;

import com.banulsoft.wallet.position.asset.Asset;
import com.banulsoft.wallet.position.asset.Industry;
import com.banulsoft.wallet.position.asset.Ticker;
import com.banulsoft.wallet.position.asset.Type;

public class Position {
    private Asset asset;
    double amount;

    public Ticker getTicker() {
        return asset.getTicker();
    }

    public Type getType() {
        return asset.getType();
    }

    public Industry getIndustry() {
        return asset.getIndustry();
    }

    public double getAmount() {
        return amount;
    }
}
