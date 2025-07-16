package com.banulsoft.wallet.position;

import com.banulsoft.wallet.shared.Ticker;

public class Position {
    private Ticker ticker;
    double amount;

    public double getAmount() {
        return amount;
    }

    public Position(Ticker ticker, double amount) {
        this.ticker = ticker;
        this.amount = amount;
    }

    public Position() {
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
