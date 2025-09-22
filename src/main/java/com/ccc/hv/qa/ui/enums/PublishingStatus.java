package com.ccc.hv.qa.ui.enums;

import java.util.Arrays;

public enum PublishingStatus {
    //FYI: The full list of codes with description is by the link: https://onix-codelists.io/codelist/64
    UNSPECIFIED("00"),
    ACTIVE("04"),
    WITHDRAWN_FROM_SALE("11");

    private final String value;

    PublishingStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static PublishingStatus fromValue(String value) {
        return Arrays.stream(values()).sequential()
                .filter(productType -> productType.toString().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Publishing Status with code '" + value + "' has been found."));
    }
}
