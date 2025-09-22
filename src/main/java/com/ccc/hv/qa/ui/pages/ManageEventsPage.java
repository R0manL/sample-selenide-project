package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.*;
import com.ccc.hv.qa.core.CustomCollectors;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.EventOption;
import com.ccc.hv.qa.ui.pojos.EventView;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.DeleteEventsPage.getDeleteEventsPage;
import static com.ccc.hv.qa.ui.pages.EditEventsPage.getEditEventsPage;
import static com.ccc.hv.qa.ui.pages.LinkEventsPage.getLinkEventsPage;
import static java.lang.Integer.parseInt;

public class ManageEventsPage {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final SelenideElement ACTION_PANEL_ROOT_ELM = $("#eventsDrawer");
    private static final SelenideElement LIST_ROOT_ELM = $(".listing-container");

    private static final String EVENT_NAME_PRE_POPULATED_TEXT = "eg. Keywords, blog post, giveaway";
    private static final String VALID_TITLE_MSG_TEMPLATE = "Great it looks like the event name %s is available";
    private static final String SUCCESSFULLY_CREATED_EVENT_MSG = "Your event was successfully added!";


    private ManageEventsPage() {
        // NONE
    }

    public static ManageEventsPage getManageEventsPage() {
        return new ManageEventsPage();
    }

    public SelenideElement getPageTitleElm() {
        return $(".page-title");
    }

    @Step("Click create a new event")
    public ManageEventsPage clickCreateNewEvent() {
        log.info("Click create a new event");
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        $("#addMainAction").click();

        return this;
    }

    @Step("Set event name: {0}")
    public ManageEventsPage setEventName(@NotNull String value) {
        log.info("Set event's name: " + value);
        getEventNameInput().val(value);

        return this;
    }

    public ManageEventsPage selectStartDate(@NotNull LocalDate date) {
        log.info("Select start date: " + date.toString());
        final String calQuerySelector = "#addRequiredStartDate";
        selectDateInCalendar(calQuerySelector, date);

        return this;
    }

    @Step("Click add (required)")
    public ManageEventsPage clickAddRequiredParameters() {
        log.info("Click add (required)");
        ACTION_PANEL_ROOT_ELM.$("#addEventToOptions").click();

        return this;
    }

    @Step
    public ManageEventsPage selectEndDate(@NotNull LocalDate date) {
        log.info("Select end date: " + date.toString());
        final String calQuerySelector = "#addOptionalEndDate";
        $(calQuerySelector).shouldBe(Condition.visible);
        selectDateInCalendar(calQuerySelector, date);

        return this;
    }

    @Step("Click add (optional)")
    public ManageEventsPage clickAddOptionalParameters() {
        log.info("Click add (optional)");
        ACTION_PANEL_ROOT_ELM.$("#addEventToBenchmark").click();

        return this;
    }

    @Step("Set notes: {0}")
    public ManageEventsPage setNotes(@NotNull String value) {
        log.info("Set notes: " + value);
        ACTION_PANEL_ROOT_ELM.$("#addOptionalNotes").val(value);

        return this;
    }

    @Step("Select Option {0}")
    public ManageEventsPage selectOption(@NotNull EventOption option) {
        log.info("Select Option " + option);
        $x(".//span[normalize-space(text())='" + option.getText() + "']").click();
        return this;
    }

    @Step("Select Start Benchmark on start {0} and stop date")
    public ManageEventsPage startBenchmarkOnStartStopDate(@NotNull LocalDate startDate, @NotNull LocalDate stopDate) {
        log.info("Select Start Benchmark on start " + startDate + " and stop date " + stopDate);
        final String startDateQuerySelector = "#" + $(".start-picker").getAttribute("id");
        selectDateInCalendar(startDateQuerySelector, startDate);
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

        final String stopDateQuerySelector = "#" + $(".stop-picker").getAttribute("id");
        selectDateInCalendar(stopDateQuerySelector, stopDate);
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

        return this;
    }

    @Step("Click add event")
    public ManageEventsPage clickAddEvent() {
        ACTION_PANEL_ROOT_ELM.$("#addEventSubmit").click();
        waitLoadingSpinnerDisappear();

        return this;
    }

    @Step("Click cancel")
    public ManageEventsPage clickCancel() {
        ACTION_PANEL_ROOT_ELM.$(".cancel").click();

        return this;
    }

    @Step("Click edit events")
    public EditEventsPage clickEditEvents() {
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        ACTION_PANEL_ROOT_ELM.$("#editMainAction").click();

        return getEditEventsPage();
    }

    @Step("Click link events")
    public LinkEventsPage clickLinkEvents() {
        ACTION_PANEL_ROOT_ELM.$("#linkMainAction").shouldBe(Condition.visible).click();
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);

        return getLinkEventsPage();
    }

    public SelenideElement getEventNameInput() {
        return ACTION_PANEL_ROOT_ELM.$("input#addRequiredName");
    }

    public String getEventNameInputPrePopulatedText() {
        return EVENT_NAME_PRE_POPULATED_TEXT;
    }

    public int getTotalNumOfEvents() {
        log.debug("Getting total number of events.");
        String textValue = $(".event-counts .total").getOwnText();
        return parseInt(textValue);
    }

    public int getNumOfEventsOccurringNow() {
        log.debug("Getting number of events occuring now.");
        String textValue = $(".event-counts .now").getOwnText();
        return parseInt(textValue);
    }

    public int getNumOfUpcomingEvents() {
        log.debug("Getting number of upcoming events.");
        String textValue = $(".event-counts .upcoming").getOwnText();
        return parseInt(textValue);
    }

    @NotNull
    public String getValidTitleMessageTemplate() {
        return VALID_TITLE_MSG_TEMPLATE;
    }

    public SelenideElement getDuplicateEventMsgElm() {
        return $("#duplicate");
    }

    @NotNull
    public String getDuplicateEventMsgText() {
        return "Duplicate title";
    }

    public SelenideElement getSuccessCreatedEventMsgElm() {
        return $(".msg.success #addSuccess");
    }

    @NotNull
    public String getSuccessCreatedEventMsgText() {
        return SUCCESSFULLY_CREATED_EVENT_MSG;
    }

    public DeleteEventsPage clickDeleteEvent() {
        $("#deleteMainAction").click();

        return getDeleteEventsPage();
    }

    public SelenideElement getEventTitleElmInList(@NotNull String title) {
        return $x(".//*[@class='event-component']//a[@class='title-viewer' and text()='" + title + "']");
    }

    public ManageEventsPage clickHideUpcomingBtn() {
        $x("//button[contains(.,'Hide Upcoming')]").click();

        return this;
    }

    public ManageEventsPage clickShowUpcomingBtn() {
        $x("//button[contains(.,'Show Upcoming')]").click();

        return this;
    }

    public boolean hasSortedAsc() {
        SelenideElement sortBtn = LIST_ROOT_ELM.$x(".//div[.//span[@class='sort']]").$("i.fas");
        String classAttrValue = sortBtn.getAttribute("class");

        Objects.requireNonNull(classAttrValue, "No 'class' attribute has been found for 'sort' events button.");

        if (classAttrValue.contains("fa-sort-amount-down")) {
            return false;
        }

        if (classAttrValue.contains("fa-sort-amount-up")) {
            return true;
        }

        throw new IllegalStateException("Can't identify if 'sort' events button has set as ASC or DESC.");
    }

    public ManageEventsPage setEventSortingAs(boolean ascending) {
        log.info("Set event sorting ascending = " + ascending);
        if (hasSortedAsc() ^ ascending) {
            LIST_ROOT_ELM.$x(".//div[.//span[@class='sort']]").$("i").click();
        } else {
            log.warn("Sorting has already set ascending = " + ascending);
        }

        return this;
    }

    public List<EventView> getListOfEvents() {
        log.debug("Get list of events from view table.");
        List<EventView> resultList = new ArrayList<>();
        $$(".manage-event-view .panel-heading")
                .asFixedIterable().stream()
                .forEach(row -> {
                    String endDate = null;
                    if (row.$(".end-date").isDisplayed()) {
                        endDate = row.$(".end-date").getText().trim().replace("- ", "");
                    }
                    EventView event = EventView.builder()
                            .startDate(row.$(".start-date").getText().trim())
                            .endDate(endDate)
                            .title(row.$(".title-viewer").getText().trim())
                            .build();
                    resultList.add(event);
                });
        return resultList;
    }

    public ManageEventsPage clickShowEventDetails() {
        log.info("Click show events details");
        $("#eventSizeToggle .glyphicon-resize-full").click();

        return this;
    }

    public ManageEventsPage clickHideEventDetails() {
        log.info("Click hide events details");
        $("#eventSizeToggle .glyphicon-resize-small").click();

        return this;
    }

    public ElementsCollection getEventDetailsElms() {
        return $$(".view-list-item .event-details");
    }

    @Step("Select date in calendar: {0}, {1}")
    private void selectDateInCalendar(@NotNull String querySelector, LocalDate date) {
        ACTION_PANEL_ROOT_ELM.$(querySelector).shouldBe(Condition.visible);

        executeJavaScript("$('" + querySelector
                        + "').datepicker('setDate', new Date(arguments[0], arguments[1], arguments[2]));",
                date.getYear(),
                date.getMonthValue() - 1,
                date.getDayOfMonth());

        $(querySelector + " a.ui-state-active").click();
    }

    private void waitLoadingSpinnerDisappear() {
        log.debug("Wait when loading spinner disappear...");
        $("#addSpinner").should(Condition.disappear);
        log.debug("Loading spinner has disappeared");
    }

    public SelenideElement getEventRow(@NotNull String title, @NotNull String date) {

        ElementsCollection rows = $(".product-listing")
                .$$x(".//div[contains(@class,'list-item') and .//div[contains(@class,'title-viewer') and .//*[normalize-space(text())='" + title + "']]]")
                .shouldHave(CollectionCondition.sizeGreaterThan(0));

        return rows.asDynamicIterable().stream()
                .filter(element -> Objects.equals(element.$(".start-date").getText(), date))
                .collect(CustomCollectors.toSingleton());
    }

    public ManageEventsPage expandRow(@NotNull String title, @NotNull String date) {
        log.debug("Expand row with title: " + title);
        getEventRow(title, date).click();
        return this;
    }
}
