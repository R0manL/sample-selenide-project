package com.ccc.hv.qa.ui.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Text values for Distribution Server security.
 * Created by R0manL on 25/08/20.
 */

public enum DistributionServerSecurity {
    IMPLICIT("Implicit"),
    EXPLICIT("Explicit");

    private final String name;

    DistributionServerSecurity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static DistributionServerSecurity fromText(@NotNull String text) {
        return Arrays.stream(values()).sequential()
                .filter(security -> security.toString().equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Distribution server security with text " + text + " has been found"));
    }
}
