package com.ccc.hv.qa.api.enums;

/**
 * Created by R0manL on 23/09/20.
 */

public enum BisacCodeFilterType {
    INCLUSION("Inclusion"),
    EXCLUSION("Exclusion");

    private final String text;

    BisacCodeFilterType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
