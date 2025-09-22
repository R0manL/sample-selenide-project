package com.ccc.hv.qa.db.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by R0manL on 18/12/20.
 */

public enum AssetAction {
    NONE("None"),
    DELETED("Deleted");

    private final String actionText;

    AssetAction(String actionText) {
        this.actionText = actionText;
    }

    public static AssetAction getActionBy(@NotNull String actionValue) {
        return Arrays.stream(values()).sequential()
                .filter(v -> v.actionText.equals(actionValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid asset action (" + actionValue + ") text has been provided."));
    }
}
