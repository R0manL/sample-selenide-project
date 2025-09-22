package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 09/09/20.
 */

public enum Publisher implements ChannelAssociationItem {
    ALBERT_WHITMAN_COMPANY("Albert Whitman & Company"),
    AMERICAN_TECHNICAL_PUBLISHERS("American Technical Publishers"),
    AQA_APPLE_ONIX("AQA APPLE ONIX"),
    AQA_AUTO_DISTRIBUTION("AQA Auto Distribution"),
    AQA_PUBLISHER("AQA Publisher"),
    HMH_BOOKS("HMH Books"),
    TEST_PUBLISHER("Test Publisher");

    private final String text;

    Publisher(String text) {
        this.text = text;
    }

    public String getText() { return this.text; }

    @Override
    public String toString() {
        return name();
    }
}
