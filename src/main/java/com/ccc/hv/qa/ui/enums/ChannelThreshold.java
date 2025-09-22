package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 25/08/20.
 */

public enum ChannelThreshold {
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    THREE_HUNDRED(300),
    FOUR_HUNDRED(400);

    private final int optionValue;

    ChannelThreshold(int optionValue) {
        this.optionValue = optionValue;
    }

    public int getOptionValue(){
        return this.optionValue;
    }

    @Override
    public String toString() {
        return Integer.toString(this.optionValue);
    }
}
