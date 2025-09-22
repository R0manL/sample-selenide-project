package com.ccc.hv.qa.ui.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Text values for Distribution Server modes.
 * Created by R0manL on 25/08/20.
 */

public enum DistributionServerMode {
    PASSIVE("Passive"),
    ACTIVE("Active");

    private final String optionValue;

    DistributionServerMode(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionValue() {
        return optionValue;
    }
    public static DistributionServerMode fromText(@NotNull String text) {
        return Arrays.stream(values()).sequential()
                .filter(mode -> mode.getOptionValue().equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Distribution server mode with text " + text + " has been found"));
    }
}
