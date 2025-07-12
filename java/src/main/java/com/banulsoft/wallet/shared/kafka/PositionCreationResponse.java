package com.banulsoft.wallet.shared.kafka;

import lombok.Getter;

@Getter
public class PositionCreationResponse {
    private String ticker;
    private String status;

    public PositionCreationResponse() {
    }

    public PositionCreationResponse(String ticker, String status) {
        this.ticker = ticker;
        this.status = status;
    }
}