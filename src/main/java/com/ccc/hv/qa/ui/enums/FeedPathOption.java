package com.ccc.hv.qa.ui.enums;

public enum FeedPathOption {
    ACTUAL_BU_NAME("ACTUAL_BU_NAME"),
    CHANNEL_NAME("CHANNEL_NAME"),
    DATE("DATE"),
    HOURS_MIN_SEC("HOURS_MIN_SEC"),
    MLS("MLS");

    private final String option;

    FeedPathOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return this.option;
    }
}
