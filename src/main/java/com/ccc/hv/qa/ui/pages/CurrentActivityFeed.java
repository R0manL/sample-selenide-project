package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.*;

public class CurrentActivityFeed {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final SelenideElement ROOT_ELM = $$(".event-current-activity.feed").filter(Condition.visible).first();
    public static final String TITLE = "Current Events";

    private CurrentActivityFeed() {
        // NONE
    }

    public static CurrentActivityFeed getCurrentActivityFeed() {
        return new CurrentActivityFeed();
    }

    public ElementsCollection getElementsList() {
        return ROOT_ELM.$$(".event-current-activity-list .event");
    }

    public SelenideElement getTitleElm() {
        return ROOT_ELM.$(".event-flyout-column-header");
    }

    public SelenideElement getEventStartDate(@NotNull SelenideElement row){
        return row.$(".start-date");
    }

    public SelenideElement getEventNumberOfProducts(@NotNull SelenideElement row){
        return row.$(".count");
    }

    public SelenideElement getUser(@NotNull SelenideElement row){
        return row.$(".user");
    }
}