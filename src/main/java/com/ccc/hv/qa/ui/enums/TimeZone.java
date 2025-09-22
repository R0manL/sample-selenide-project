package com.ccc.hv.qa.ui.enums;

/**
 * List of time zones available on ChannelExclusive creation page.
 * option - option value for dropdown list.
 * text - text shown on page.
 * Created by R0manL on 25/08/20.
 */
public enum TimeZone {
    ACDT("000", "Australian Central Daylight Time - UTC+10:30"),
    ACST("001", "Australian Central Standard Time - UTC+09:30"),
    ACT("002", "ASEAN Common Time - UTC+08"),
    ADT("003", "Atlantic Daylight Time - UTC-03"),
    AEDT("004", "Australian Eastern Daylight Time - UTC+11"),
    AEST("005", "Australian Eastern Standard Time - UTC+10"),
    AFT("006", "Afghanistan Time - UTC+04:30"),
    AKDT("007", "Alaska Daylight Time - UTC-08"),
    CDT("028", "Central Daylight Time (North America) - UTC-05"),
    CST("046", "CST - Central Standard Time (North America) - UTC-06"),
    YEKT("191", "Yekaterinburg Time - UTC+05"),
    UTC("174", "UTC - Coordinated Universal Time - UTC");

    private final String optionValue;
    private final String text;

    TimeZone(String optionValue, String text) {
        this.optionValue = optionValue;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public String getOptionValue() {
        return this.optionValue;
    }
}
