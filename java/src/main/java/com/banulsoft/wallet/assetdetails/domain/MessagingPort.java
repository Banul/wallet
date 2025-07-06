package com.banulsoft.wallet.assetdetails.domain;

import java.util.Set;

public interface MessagingPort {
    void refreshTrackedCompanies(Set<Ticker> companiesTickers);
}
