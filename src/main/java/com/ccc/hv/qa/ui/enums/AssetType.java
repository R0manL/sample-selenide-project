package com.ccc.hv.qa.ui.enums;

import java.util.Arrays;

/**
 * Created by R0manL on 01/09/20.
 */

public enum AssetType {
    ONIX21("ONIX version 2.1", "ONIX_2_1"),
    ONIX30("ONIX version 3.0", "ONIX_3_0"),
    MARKETING_IMAGE("Marketing Image", "MARKETINGIMAGE"),
    PRINT_REPLICA("Print Replica", "PRINTREPLICA"),
    ENHANCED_FIXED_EPUB("Enhanced Fixed EPUB", ""),
    CD_AUDIO("CD-Audio", ""),
    EPUB("EPUB", "EPUB"),
    SCREENSHOT("Screenshot", "SCREENSHOT"),
    WEB_OPTIMIZED_PDF("Web Optimized PDF", "Web Optimized PDF"),
    OUTSIDE_FRONT_COVER("Outside Front Cover", ""),
    OUTSIDE_SPINE_COVER("Outside Spine Cover", ""),
    OUTSIDE_BACK_COVER("Outside Back Cover", ""),
    HIGH_RESOLUTION_FRONT_COVER("High-resolution Front Cover", "");


    private final String text;
    private final String distributionPathValue;

    AssetType(String text, String distributionPathValue) {
        this.text = text;
        this.distributionPathValue = distributionPathValue;
    }

    public String getText() {
        return this.text;
    }

    public String getDistributionPathValue() {
        return this.distributionPathValue;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static AssetType from(String text) {
        return Arrays.stream(values()).sequential()
                .filter(productType -> productType.toString().equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Asset Type with text '" + text + "' has been found."));
    }

    public static AssetType from(ContentType contentType) {
        return from(contentType.getText());
    }

    public static AssetType from(CollateralType collateralType) {
        return from(collateralType.getText());
    }

    public static AssetType from(MetadataType metadataType) {
        return from(metadataType.getText());
    }
}
