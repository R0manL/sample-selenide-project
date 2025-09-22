package com.ccc.hv.qa.db.enums;


/**
 * Created by R0manL on 18/12/20.
 */

public enum DistributionValidationMessage {
    FILTERED("Filtered");

    private final String msg;

    DistributionValidationMessage(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return this.msg;
    }
}
