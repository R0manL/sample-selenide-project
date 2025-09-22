package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 20/08/20.
 */

public enum IngestMode {
    NORMAL("Normal"),
    PREVIOUSLY_DISTRIBUTED("Previously Distributed");

    private final String text;

    IngestMode(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
