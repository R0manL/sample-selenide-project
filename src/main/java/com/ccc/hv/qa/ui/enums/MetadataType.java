package com.ccc.hv.qa.ui.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by R0manL on 01/09/20.
 */

public enum MetadataType {
    //Metadata
    ONIX21("ONIX version 2.1"),
    ONIX30("ONIX version 3.0"),
    PRINT_SPECIFICATION_DATA("Print Specification Data"),
    SIDECAR("Sidecar Metadata"),
    PRINT_SPEC("Print Specification Data"),
    UNKNOWN_METADATA("Unknown Metadata");

    private final String text;

    MetadataType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static MetadataType fromText(@NotNull String text) {
        return Arrays.stream(values()).sequential()
                .filter(metadataType -> metadataType.toString().equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Metadata Type with text '" + text + "' has been found."));
    }
}
