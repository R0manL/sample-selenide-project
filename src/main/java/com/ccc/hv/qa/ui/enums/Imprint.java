package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 09/09/20.
 */

public enum Imprint implements ChannelAssociationItem {
    ALBERT_WHITMAN_COMPANY("Albert Whitman & Company"),
    VENDOME_PRESS("Vendome Press"),
    TYNDALE_HOUSE_PUBLISHER("Tyndale House Publishers, Inc.");


    private final String text;

    Imprint(String text) {
        this.text = text;
    }

    public String getText() { return this.text; }

    @Override
    public String toString() {
        return name();
    }
}
