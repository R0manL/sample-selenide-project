package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 25/08/20.
 */

public enum HrvViewMarketMapping {
    NONE("None"),
    AMAZON("Amazon"),
    APPLE("Apple"),
    BARNES_AND_NOBLE("Barnes & Noble"),
    GOOGLE("Google"),
    KOBO("Kobo"),
    GOOD_READS("Goodreads"),
    CHRISTIAN_BOOKS("Christianbooks.com"),
    WALMART("Walmart.com");

    private final String optionValue;

    HrvViewMarketMapping(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionValue() {
        return this.optionValue;
    }

    @Override
    public String toString() {
        return this.optionValue;
    }
}
