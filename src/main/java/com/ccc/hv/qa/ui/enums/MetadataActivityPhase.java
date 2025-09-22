package com.ccc.hv.qa.ui.enums;

public enum MetadataActivityPhase {
    NORMALIZATION("Normalization"),
    INGEST("Ingest"),
    OPTIMIZATION("Optimization"),
    DISTRIBUTION("Distribution");

    private final String text;

    MetadataActivityPhase(String text) {
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
