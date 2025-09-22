package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 25/08/20.
 */

public enum RetryInterval {
    TWO(2),
    FOUR(4),
    EIGHT(8),
    TWELV(12);

    private final int optionValue;

    RetryInterval(int optionValue) {
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
