package com.banulsoft.wallet.stockinformation.infrastructure;

import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "company_details")
public class StockInformationEntity extends BaseEntity {
    private String ticker;
    private String country;

    private String industry;
    private String sector;

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCountry() {
        return country;
    }

    public String getIndustry() {
        return industry;
    }

    public String getSector() {
        return sector;
    }

    public StockInformationEntity() {
    }
}


/*
    ticker varchar,
    country varchar,
    industry varchar,
    sector varchar,
 */