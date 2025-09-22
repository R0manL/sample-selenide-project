package com.ccc.hv.qa.ui.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by R0manL on 01/09/20.
 */

public enum CollateralType {
    SCREENSHOT("Screenshot"),
    MARKETING_IMAGE("Marketing Image"),
    OUTSIDE_FRONT_COVER("Outside Front Cover"),
    OUTSIDE_BACK_COVER("Outside Back Cover"),
    OUTSIDE_SPINE_COVER("Outside Spine Cover"),
    AUXILLIARY_PDF("Auxilliary PDF"),
    AUXILLIARY_EPUB("Auxilliary Epub"),
    GOOGLE_MARKETING_IMAGE_VARIANT("Google Marketing Image Variant"),
    AMAZON_MARKETING_IMAGE_VARIANT("Amazon Marketing Image Variant"),
    B_AND_N_MARKETING_IMAGE_VARIANT("B&N Marketing Image Variant"),
    APPLE_MARKETING_IMAGE_VARIANT("Apple Marketing Image Variant"),
    KOBO_MARKETING_IMAGE_VARIANT("Kobo Marketing Image Variant"),
    HIGH_RESOLUTION_FRONT_COVER("High-resolution Front Cover"),
    FRONT_COVER_PROOF("Front Cover Proof"),
    AUDIO_SUPPLEMENT("Audio Supplement"),
    COVER_MECHANICAL_PACKAGE("Cover Mechanical Package"),
    PROOF("Proof"),
    INTERIOR_PACKAGE("Interior Package"),
    IN_DESIGN_PACKAGE("InDesign Package"),
    SAMPLE("Sample EPUB"),
    QUARK_PACKAGE("Quark Package"),
    UNKNOWN_COLLATERAL("Unknown Collateral");

    private final String text;

    CollateralType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static CollateralType fromText(@NotNull String text) {
        return Arrays.stream(values()).sequential()
                .filter(collateralType -> collateralType.toString().equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Collateral Type with text '" + text + "' has been found."));
    }
}
