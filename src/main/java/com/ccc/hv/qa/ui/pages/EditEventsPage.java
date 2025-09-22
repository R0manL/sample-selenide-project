package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.*;
import com.ccc.hv.qa.core.CustomCollectors;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.EventOption;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Keys;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.ManageEventsPage.getManageEventsPage;

public class EditEventsPage {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private EditEventsPage() {
        // NONE
    }

    public static EditEventsPage getEditEventsPage() {
        return new EditEventsPage();
    }

    public SelenideElement getEventRow(@NotNull String title, @NotNull String date) {
        ElementsCollection rows = $(".edit-listing")
                .$$x(".//div[contains(@class,'edit-list-item') and .//div[contains(@class,'name-viewer') and normalize-space(text())='" + title + "']]")
                .shouldHave(CollectionCondition.sizeGreaterThan(0));

        return rows.asDynamicIterable().stream()
                .filter(element -> Objects.equals(element.$(".startdate-picker").getValue(), date))
                .collect(CustomCollectors.toSingleton());
    }

    @Step("Update event name: {0}")
    public EditEventsPage updateEventName(@NotNull SelenideElement row, @NotNull String newName) {
        log.info("Update event's name: " + newName);
        row.$(".name-viewer").click();
        String initialText = row.$(".name-editor").shouldBe(Condition.visible).getValue();
        Objects.requireNonNull(initialText);

        for (int i = 0; i < initialText.length(); i++) {
            Selenide.actions().sendKeys(Keys.RIGHT).perform();
            Selenide.actions().sendKeys(Keys.BACK_SPACE).perform();
        }
        row.$(".name-editor").sendKeys(newName);
        Selenide.actions().sendKeys(Keys.ENTER).perform();

        return this;
    }

    @Step("Clear event name: {0}")
    public EditEventsPage clearEventName(@NotNull SelenideElement row) {
        log.info("Clear event's name: ");
        row.$(".name-viewer").click();
        String initialText = row.$(".name-editor").shouldBe(Condition.visible).getValue();
        Objects.requireNonNull(initialText);
        for (int i = 0; i < initialText.length(); i++) {
            Selenide.actions().sendKeys(Keys.RIGHT).perform();
            Selenide.actions().sendKeys(Keys.BACK_SPACE).perform();
        }
        Selenide.actions().sendKeys(Keys.ENTER).perform();

        return this;
    }

    @Step("Update start date: {0}")
    public EditEventsPage updateStartDate(@NotNull SelenideElement row, @NotNull LocalDate date) {
        log.info("Update start date: " + date);

        final String calQuerySelector = "#" + row.$(".startdate-picker").getAttribute("id");
        selectDateInCalendar(calQuerySelector, date);
        row.$(calQuerySelector).click();
        $("#ui-datepicker-div").$("a.ui-state-active").click();
        return this;
    }

    @Step("Update end date: {0}")
    public EditEventsPage updateEndDate(@NotNull SelenideElement row, @NotNull LocalDate date) {
        log.info("Update end date: " + date);
        final String calQuerySelector = "#" + row.$(".enddate-picker").getAttribute("id");
        selectDateInCalendar(calQuerySelector, date);
        row.$(calQuerySelector).click();
        $("#ui-datepicker-div").$("a.ui-state-active").click();

        return this;
    }

    @Step("Add end date: {0}")
    public EditEventsPage addEndDate(@NotNull SelenideElement row, @NotNull LocalDate date) {
        log.info("Add end date: " + date);
        row.$(".add-end-date").click();
        updateEndDate(row, date);

        return this;
    }

    @Step("Get end date")
    public SelenideElement getEndDate(@NotNull SelenideElement row) {
        log.info("Get end date");
        return row.$(".enddate-picker");
    }

    @Step("Delete end date")
    public EditEventsPage deleteEndDate(@NotNull SelenideElement row) {
        log.info("Delete end date");
        row.$$x(".//input[contains(@class,'enddate-picker')]/following::span[1]").first().click();

        return this;
    }

    @Step("Click Save button")
    public EditEventsPage clickSaveButton(@NotNull SelenideElement row) {
        log.info("Click save button");
        getSaveButton(row).click();
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

        return this;
    }

    @Step("Click I'm done with changes")
    public ManageEventsPage clickImDoneWithChanges() {
        log.info("Click I'm done with changes");
        $x(".//div[@class='cancel']//a[normalize-space(text())=\"I'm done with changes\"]")
                .scrollIntoView(false)
                .click();
        return getManageEventsPage();
    }

    @Step("Click I'm done with deleting")
    public ManageEventsPage clickImDoneWithDeleting() {
        log.info("Click I'm done with deleting");
        SelenideElement imDoneWithDeletingElm =
                $x(".//*[@class='goback']/a[normalize-space(text())=\"I'm done with deleting\"]");
        if (imDoneWithDeletingElm.isDisplayed()) {
            imDoneWithDeletingElm.click();
        } else {
            log.info("'I'm done with deleting' is not displayed, view Manage Event mode is navigated");
        }


        return getManageEventsPage();
    }

    @Step("Get Event notes")
    public SelenideElement getNotes(@NotNull SelenideElement row) {
        log.info("Get Event notes");
        return row.$(".comments-viewer");
    }

    @Step("Add Event notes")
    public EditEventsPage addEventNotes(@NotNull SelenideElement row, @NotNull String notesText) {
        log.info("Add Event notes");
        getNotes(row).click();
        row.$(".comments-editor").sendKeys(notesText);
        Selenide.actions().sendKeys(Keys.ENTER).perform();
        return this;
    }

    @Step("Update Event notes")
    public EditEventsPage updateEventNotes(@NotNull SelenideElement row, @NotNull String notesText) {
        log.info("Update Event notes");
        getNotes(row).click();
        String initialText = row.$(".comments-editor").shouldBe(Condition.visible).getValue();
        Objects.requireNonNull(initialText);

        for (int i = 0; i < initialText.length(); i++) {
            Selenide.actions().sendKeys(Keys.RIGHT).perform();
            Selenide.actions().sendKeys(Keys.BACK_SPACE).perform();
        }

        row.$(".comments-editor").sendKeys(notesText);
        Selenide.actions().sendKeys(Keys.ENTER).perform();
        return this;
    }

    @Step("Delete Event notes")
    public EditEventsPage deleteEventNotes(@NotNull SelenideElement row) {
        log.info("Delete Event note");
        getNotes(row).click();
        row.$(".comments-editor").clear();
        row.$(".comments-editor").click();
        Selenide.actions().sendKeys(Keys.ENTER).perform();
        return this;
    }

    @Step("Select Option {0}")
    public EditEventsPage selectOption(@NotNull SelenideElement row, @NotNull EventOption option) {
        log.info("Select Option " + option);
        row.$x(".//span[normalize-space(text())='" + option.getText() + "']").click();
        return this;
    }

    @Step("Select Start Benchmark on start {0} and stop date")
    public EditEventsPage startBenchmarkOnStartStopDate(@NotNull SelenideElement row, @NotNull LocalDate startDate, @NotNull LocalDate stopDate) {
        log.info("Select Start Benchmark on start " + startDate + " and stop date " + stopDate);

        final String calQuerySelectorStartDate = "#" + row.$(".start-picker").getAttribute("id");
        selectDateInBenchmarkCalendar(calQuerySelectorStartDate, startDate);
        $(calQuerySelectorStartDate).$("a.ui-state-active").click();

        final String calQuerySelectorStopDate = "#" + row.$(".stop-picker").getAttribute("id");
        selectDateInBenchmarkCalendar(calQuerySelectorStopDate, stopDate);
        $(calQuerySelectorStopDate).$("a.ui-state-active").click();

        return this;
    }

    @Step("Click Save button")
    public SelenideElement getSaveButton(@NotNull SelenideElement row) {
        log.info("Get save button");
        return row.$(".save");
    }

    public SelenideElement getDuplicateErrorMsg() {
        log.info("Get duplicate error msg");
        return $("#duplicate");
    }

    public SelenideElement getSystemErrorMsg() {
        log.info("Get system error msg");
        return $(".msg.error");
    }

    @Step("Select date in calendar: {0}, {1}")
    private void selectDateInCalendar(@NotNull String querySelector, LocalDate date) {
        $(querySelector).shouldBe(Condition.visible);

        executeJavaScript("$('" + querySelector
                        + "').datepicker().datepicker('setDate', new Date(arguments[0], arguments[1], arguments[2]));",
                date.getYear(),
                date.getMonthValue() - 1,
                date.getDayOfMonth());
    }

    @Step("Select date in benchmark calendar: {0}, {1}")
    private void selectDateInBenchmarkCalendar(@NotNull String querySelector, LocalDate date) {
        $(querySelector).shouldBe(Condition.visible);

        executeJavaScript("$('" + querySelector
                        + "').datepicker('setDate', new Date(arguments[0], arguments[1], arguments[2]));",
                date.getYear(),
                date.getMonthValue() - 1,
                date.getDayOfMonth());
    }
}
