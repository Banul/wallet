package com.banulsoft.wallet.portfoliodraft.infrastructure;

import java.util.UUID;

class SearchedCompany {
    private UUID id;
    private String ticker;
    private boolean doesExist;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public boolean isDoesExist() {
        return doesExist;
    }

    public void setDoesExist(boolean doesExist) {
        this.doesExist = doesExist;
    }
}
