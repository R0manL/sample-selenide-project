package com.ccc.hv.qa.utils.enums;

/**
 * Created by R0manL on 31.03.2021.
 */

public enum DatePattern {
    ONIX("yyyyMMdd"),
    UI_BLACKOUT("yyyy-MM-dd"),
    UI_PRODUCT("MM/dd/yyyy"),
    EVENT_VIEW("MMM d yyyy"),
    FEED_EVENT_VIEW("MMM dd yyyy");


    private final String pattern;

    DatePattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return this.pattern;
    }
}
