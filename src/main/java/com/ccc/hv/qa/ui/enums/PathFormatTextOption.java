package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 09/09/20.
 */

public enum PathFormatTextOption implements PathFormatOption {
    RECORD_REFERENCE("${RECORDREFERENCE}"),
    ASSET_TYPE("${ASSETTYPE}"),
    FILE_NAME("${FILENAME}"),
    ISBN13("${ISBN13}"),
    ISBN10("${ISBN10}"),
    IMPRINT("${IMPRINTCODE}"),
    PUBLISHER("${PUBLISHER}");

    private final String format;

    PathFormatTextOption(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return this.format;
    }
}
