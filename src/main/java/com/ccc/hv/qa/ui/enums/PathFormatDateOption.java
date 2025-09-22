package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 09/09/20.
 */

public enum PathFormatDateOption implements PathFormatOption {
    XFER_MMDDYY("${XFER MMddyy}");

    private final String format;

    PathFormatDateOption(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return this.format;
    }
}
