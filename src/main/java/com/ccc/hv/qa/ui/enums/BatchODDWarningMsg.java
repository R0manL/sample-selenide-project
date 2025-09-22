package com.ccc.hv.qa.ui.enums;

public enum BatchODDWarningMsg {

    PRODUCT_IS_LOCKED("Requested product record Locked"),
    PRODUCT_IS_ON_HOLD("Requested product record On Hold");

    private final String msgTest;

    BatchODDWarningMsg(String msgTest) {
        this.msgTest = msgTest;
    }

    public String getMsgText() {
        return msgTest;
    }
}
