package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 23/09/20.
 */

public enum IsbnFilterType {
    NONE,
    INCLUDE,
    EXCLUDE;

    @Override
    public String toString() {
        return name();
    }
}
