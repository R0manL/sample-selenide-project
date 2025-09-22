package com.ccc.hv.qa.db.enums;

import java.util.Arrays;

/**
 * Created by R0manL on 01/09/20.
 */

public enum AssetDistributionStatus {
    RECEIVED_SUCCESS(0, "Received Successfully"),
    RECEIVED_FAIL(1, "Receive Failure"),
    VALID(2, "Ready to Go"),
    INVALID(3, "Failed Validation"),
    STORE_SUCCESS(4, "Stored"),
    STORE_FAIL(5, "Failed Storage"),
    ACCEPTANCE_SUCCESS(6, "Accepted"),
    ACCEPTANCE_FAIL(7, "Acceptance Failure"),
    DISTRIB_SUCCESS(8, "Distributed"),
    DISTRIB_FAIL(9, "Failed Distribution"),
    PACKAGE_SUCCESS(10, "Packaging Succeeded"),
    PACKAGE_FAIL(11, "Packaging Failed"),
    READY_FOR_PACKAGING(12, "Ready for Packaging");

    private final int id;
    private final String description;

    AssetDistributionStatus(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }
    public String getDescription() { return this.description; }

    public static AssetDistributionStatus getValueBy(int id) {
        return Arrays.stream(values()).sequential()
                .filter(v -> v.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid id (" + id + ") has been provided."));
    }

    @Override
    public String toString() {
        return name();
    }
}
