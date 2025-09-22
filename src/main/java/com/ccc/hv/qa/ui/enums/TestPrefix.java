package com.ccc.hv.qa.ui.enums;

import lombok.NonNull;

import java.util.Arrays;

/**
 * Created by R0manL on 31.03.2021.
 */

public enum TestPrefix {
    PREDEFINED("Predefined"),
    REGRESSION("Regression"),
    AREGRESSION("Aregression"),
    aREGRESSION("aregression"),
    rEGRESSION("regression"),
    SMOKE("Smoke");


    private final String prefix;

    TestPrefix(String prefix) {
        this.prefix = prefix;
    }

    public static boolean isStartWithTestPrefix(@NonNull String text) {
        return Arrays.stream(values()).sequential().anyMatch(prefix -> text.startsWith(prefix.toString()));
    }

    @Override
    public String toString() {
        return this.prefix;
    }
}
