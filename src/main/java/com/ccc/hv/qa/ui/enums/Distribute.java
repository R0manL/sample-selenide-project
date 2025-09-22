package com.ccc.hv.qa.ui.enums;

/**
 * Created by R0manL on 04/09/20.
 *
 * Used for 'Distribute {before} / {after} it's on Sale Date' option in channel association</>'
 */

public enum Distribute {
    BEFORE("before"),
    AFTER("after");

    private final String text;

    Distribute(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() { return this.text; }
}
