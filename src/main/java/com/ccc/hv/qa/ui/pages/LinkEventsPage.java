package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;

import static com.codeborne.selenide.Selenide.*;

public class LinkEventsPage {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private LinkEventsPage() {
        // NONE
    }

    public static LinkEventsPage getLinkEventsPage() {
        return new LinkEventsPage();
    }

    @Step("Select Link Option")
    public LinkEventsPage selectLinkOption() {
        $("#linkMainAction").shouldBe(Condition.visible).click();

        return this;
    }

    @Step("Upload file to Event")
    public LinkEventsPage uploadFileToEvent(@NotNull String title, @NotNull String date, @NotNull Path xlsxFilePath) {
        getEventRowWith(title, date)
                .$("input[type='file']")
                .uploadFile(xlsxFilePath.toFile());

        return this;
    }

    public SelenideElement getEventMsg(){
        return $("#eventMessage");
    }

    public SelenideElement getSummary(){
        return $(".summary");
    }

    private SelenideElement getEventRowWith(@NotNull String title, @NotNull String date) {
        return $(".link-listing")
                .$x(".//div[@class='event'][.//span[normalize-space(text())='" + date + "']]" +
                        "[.//span[normalize-space(text())='" + title + "']]");
    }
}
