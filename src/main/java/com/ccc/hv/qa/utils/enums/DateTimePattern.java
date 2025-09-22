package com.ccc.hv.qa.utils.enums;

/**
 * Created by R0manL on 31.03.2021.
 */

public enum DateTimePattern {
    DB_ASSET_TIMESTAMP("yyyy-MM-dd HH:mm:ss"),
    UI_ASSET_TIMESTAMP("MM/dd/yyyy h:mm:ss a"),
    YYMMDDHHMMSS("yyMMddhhmmss"),
    FILE_MODIFY_DATE("EEE MMM dd HH:mm:ss zzz yyyy"),
    BATCH_ODD_CONFIRM_MSG("MM/dd, h:mm a");

    private final String pattern;

    DateTimePattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return this.pattern;
    }
}
