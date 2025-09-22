package com.ccc.hv.qa.api.enums;

/**
 * Created by R0manL on 23/09/20.
 */

public enum AssetType {
    CONTENT,
    COLLATERAL,
    METADATA;

    @Override
    public String toString() {
        return name();
    }
}
