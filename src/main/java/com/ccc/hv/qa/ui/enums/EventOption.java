package com.ccc.hv.qa.ui.enums;

public enum EventOption {
    USE_30_DAYS_BEFORE_THE_START_OF_THE_EVENT("Use the 30 days before the start of the event"),
    LET_ME_CHOOSE_THE_START_OF_THE_EVENT("Let me choose a start and stop date");

    private final String text;

    EventOption(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

}
