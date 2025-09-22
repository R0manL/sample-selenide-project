package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 09/09/20.
 */

public enum PathFormatDateTimeOption implements PathFormatOption {
    XFER_YYYYMMDDHHMMSSSSS("${XFER yyyyMMddHHmmssSSS}"),
    XFER_YYYYMMDD_DASH_HHMMSSSSS("${XFER yyyyMMdd-HHmmssSSS}"),
    UPD_YYYYMMDDHHMMSSSSS("${UPD yyyyMMddHHmmssSSS}"),
    UPD_MM_DOT_DD_DOT_YY_DASH_HHMMSSSSS("${UPD MM.dd.YY-HHmmssSSS}");

    private final String format;

    PathFormatDateTimeOption(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return this.format;
    }
}
