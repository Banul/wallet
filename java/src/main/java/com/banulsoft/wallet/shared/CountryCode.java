package com.banulsoft.wallet.shared;

public enum CountryCode {
    USA("United States"),
    POL("Poland"),
    DEU("Germany"),
    GBR("United Kingdom"),
    FRA("France"),
    JPN("Japan"),
    CHN("China"),
    CAN("Canada"),
    AUS("Australia"),
    IND("India"),
    BRA("Brazil"),
    KOR("South Korea"),
    NLD("Netherlands"),
    CHE("Switzerland"),
    SWE("Sweden"),
    ESP("Spain"),
    ITA("Italy"),
    MEX("Mexico"),
    NOR("Norway"),
    DNK("Denmark"),
    FIN("Finland"),
    BEL("Belgium"),
    AUT("Austria"),
    IRL("Ireland"),
    ISR("Israel"),
    SGP("Singapore"),
    HKG("Hong Kong"),
    TWN("Taiwan"),
    ZAF("South Africa"),
    RUS("Russia"),
    ARG("Argentina"),
    CHL("Chile"),
    THA("Thailand"),
    MYS("Malaysia"),
    IDN("Indonesia"),
    PHL("Philippines"),
    NZL("New Zealand"),
    PRT("Portugal"),
    GRC("Greece"),
    TUR("Turkey"),
    SAU("Saudi Arabia"),
    ARE("United Arab Emirates"),
    URY("Uruguay"),
    UNKNOWN("Unknown");

    private final String countryName;

    CountryCode(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCode() {
        return this.name();
    }
    public static CountryCode fromYahooName(String yahooCountryName) {
        if (yahooCountryName == null || yahooCountryName.isBlank()) {
            return UNKNOWN;
        }

        for (CountryCode code : values()) {
            if (code.getCountryName().equalsIgnoreCase(yahooCountryName.trim())) {
                return code;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return this.name();
    }
}