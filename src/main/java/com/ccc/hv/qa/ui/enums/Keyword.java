package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 23/09/20.
 */

public enum Keyword {
    IMPRINT("Imprint"),
    PUBLISHER_NAME("PublisherName");

    private final String optionValue;

    Keyword(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionValue() {
        return this.optionValue;
    }
}
