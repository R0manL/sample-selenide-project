package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 09/09/20.
 */

public enum AudienceCode implements ChannelAssociationItem {
    GENERAL_TRADE("01 General/trade"),
    CHILDREN_JUVENILE("02 Children/juvenile"),
    YOUNG_ADULT("03 Young adult"),
    PROFESSIONAL_AND_SCHOLARLY("06 Professional and scholarly");

    private final String text;

    AudienceCode(String text) {
        this.text = text;
    }

    public String getText() { return this.text; }

    @Override
    public String toString() {
        return name();
    }
}
