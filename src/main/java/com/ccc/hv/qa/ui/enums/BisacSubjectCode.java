package com.ccc.hv.qa.ui.enums;

import java.util.Arrays;

/**
 * Created by R0manL on 09/09/20.
 */

public enum BisacSubjectCode {
    ANT045000,
    ART000000,
    ART001000,
    ART002000,
    ART023000,
    ART003000,
    CGN000000,
    CGN001000,
    CKB088000,
    BUS043000,
    FIC030000,
    FIC000000,
    JUV001000,
    JUV013050,
    YAF058020;

    @Override
    public String toString() {
        return name();
    }

    public static BisacSubjectCode fromText(String text) {
        return Arrays.stream(values()).sequential()
                .filter(code -> code.toString().equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Bisac Subject Code with text '" + text + "' has been found."));
    }
}
