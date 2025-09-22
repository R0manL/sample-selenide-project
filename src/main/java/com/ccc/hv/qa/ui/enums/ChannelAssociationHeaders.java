package com.ccc.hv.qa.ui.enums;

public enum ChannelAssociationHeaders {
    CHANNEL_ASSOCIATIONS("CHANNEL ASSOCIATIONS"),
    ID("ID"),
    OPTIMIZATIONS("OPTIMIZATIONS"),
    AUTOMATED_DISTRIBUTIONS("AUTOMATED DISTRIBUTIONS"),
    DISTRIBUTION_STATUS("DISTRIBUTION STATUS"),
    MARKETPLACE_MONITORING("MARKETPLACE MONITORING");

    private final String text;

    ChannelAssociationHeaders(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
