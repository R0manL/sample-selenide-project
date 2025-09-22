package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.$x;

public class LinkEventToProductPage {

    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private LinkEventToProductPage() {
        // NONE
    }

    public static LinkEventToProductPage getLinkEventToProductPage() {
        return new LinkEventToProductPage();
    }

    @Step("Link Product to Event with title {0} and date {1}")
    public LinkEventToProductPage linkProductToEvent(@NotNull String title, @NotNull String date) {
        log.info("Link Product to event with title " + title + " and date " + date);

        SelenideElement rowElm = $x(".//div[contains(@class,'row event-info') " +
                "and .//*[@class='start' and normalize-space(text())='" + date + "'] " +
                "and .//*[contains(@class,'title') and normalize-space(text())='" + title + "']]");
        rowElm.$(".link-icon.unlinked").click();

        return this;
    }
}