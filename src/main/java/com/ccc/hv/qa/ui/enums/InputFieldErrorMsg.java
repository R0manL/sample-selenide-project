package com.ccc.hv.qa.ui.enums;

import org.jetbrains.annotations.NotNull;

/**
 * Created by R0manL on 31.03.2021.
 */

public enum InputFieldErrorMsg {
    UNIQUE("This must be unique"),
    NON_BLANK("must not be blank"),
    NON_NULL("must not be null"),
    INVALID("is invalid"),
    DUPLICATE_SERVER_NAME("server name already exists"),
    UNKNOWN_ROLE("Unknown role"),
    BUSINESS_UNIT_REQUIRED("Business Unit required"),
    USER_ALREADY_EXIST("User already exists"),
    NOT_A_WELL_FORMATTED_EMAIL_ADDRESS("must be a well-formed email address");


    private final String errorMsg;

    InputFieldErrorMsg(@NotNull String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return this.errorMsg;
    }
}
