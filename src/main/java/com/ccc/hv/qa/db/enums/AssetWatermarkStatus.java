package com.ccc.hv.qa.db.enums;

/**
 * Created by R0manL on 12/21/20.
 */

public enum AssetWatermarkStatus {
    WATERMARKING,
    READY,
    SUCCESS;

    @Override
    public String toString() {
        return name();
    }
}
