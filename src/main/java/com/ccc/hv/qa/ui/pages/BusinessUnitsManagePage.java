package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.BusinessUnitViewPage.getBusinessUnitViewPage;
import static com.ccc.hv.qa.ui.pages.ChannelAssociatedListPage.getChannelAssociatedListPage;
import static com.ccc.hv.qa.ui.pages.CreateTagPage.getCreateTagPage;
import static com.ccc.hv.qa.ui.pages.NormalizationPage.getNormalizationPage;

/**
 * Created by R0manL on 22/09/20.
 */

public class BusinessUnitsManagePage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String ACTIVATE_BTN_SELECTOR = "button.btn-danger";
    private static final String DEACTIVATE_BTN_SELECTOR = "button.btn-success";
    private static final String CHANGE_STATUS_BTN_SELECTOR = "button.btn-default";
    private static final SelenideElement TABLE_HEADER_NAME_ELM = activePageContainer.$x(".//th[contains(@aria-label,'Name') and @aria-controls='business-unit-table']");


    private BusinessUnitsManagePage() {
        // None
    }

    public static BusinessUnitsManagePage getBusinessUnitsManagePage() {
        return new BusinessUnitsManagePage();
    }

    public SelenideElement getPageTitleElm() {
        return $x(".//*[@id='page-title' and normalize-space(text())='Manage Business Units']");
    }

    public BusinessUnitViewPage selectBusinessUnitBy(@NotNull String buName) {
        activePageContainer.$x(String.format(".//td[.//a[normalize-space(text())='%s']]/a", buName))
                .shouldBe(Condition.enabled)
                .click();

        return getBusinessUnitViewPage();
    }

    public BusinessUnitsManagePage filterBusinessUnitBy(@NotNull String name) {
        activePageContainer.$("input[aria-controls='business-unit-table']").val(name);

        return this;
    }

    public ElementsCollection getListOfBusinessUnitNameLinkElms() {
        return activePageContainer.$$x(".//a[contains(@href,'#/bsunitsview/')]");
    }

    public List<String> getListOfBusinessUnitNames() {
        List<String> result = new ArrayList<>();
        ElementsCollection buElms = getListOfBusinessUnitNameLinkElms();
        // Wait till all BUs will load (names will not be empty).
        for (SelenideElement buElm : buElms) {
            result.add(buElm.shouldNotBe(Condition.empty).getOwnText());
        }

        return result;
    }

    public NormalizationPage openNormalizationPageFor(@NotNull String buName) {
        log.info("Open normalization page for '" + buName + "' business unit.");

        activePageContainer.$x(String.format(".//td[.//a[normalize-space(text())='%s']]", buName))
                .sibling(0)
                .$x(".//a[@class='gotoNormalizationsManagement']")
                .shouldBe(visible, Duration.ofMillis(ENV_CONFIG.webElmLoadTimeout() * 2))
                .click();

        return getNormalizationPage();
    }

    public ChannelAssociatedListPage openChannelAssociationPageFor(@NotNull String buName) {
        log.info("Open channel association page for '" + buName + "' business unit.");

        activePageContainer.$x(String.format(".//td[.//a[normalize-space(text())='%s']]", buName))
                .sibling(1)
                .$x(".//a[normalize-space(text())='View List']")
                .click();

        return getChannelAssociatedListPage();
    }

    public ElementsCollection getListOfBusinessUnitNameElms() {
        return $$(".bsunits-row.sorting_1 a");
    }

    public SelenideElement getNoMatchingRecordsMsgElm() {
        return $x(".//*[text()='No matching records found']");
    }

    public BusinessUnitsManagePage clickSortBusinessUnitsByName(boolean ascending) {
        if (ascending ^ isListOfBusinessUnitsSortedByNameAscending()) {
            TABLE_HEADER_NAME_ELM.click();
        }

        return this;
    }

    private boolean isListOfBusinessUnitsSortedByNameAscending() {
        String sorted = TABLE_HEADER_NAME_ELM.getAttribute("aria-sort");
        Objects.requireNonNull(sorted, "Can't identify if BU sorted by name. No 'aria-sort' attribute.");

        return "ascending".equals(sorted);
    }

    public BusinessUnitsManagePage changeStatus(boolean toActivate, @NotNull String buName) {
        log.info("Change status for '" + buName + "' Business Unit.");

        if (isBUActive(buName) != toActivate) {
            SelenideElement statusCell = activePageContainer
                    .$x(String.format(".//a[normalize-space(text())='%s']/parent::td", buName)).sibling(1).shouldBe(visible);
            statusCell.$(CHANGE_STATUS_BTN_SELECTOR).click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout()); //Note. Sometimes selenide click but status has not set.
            $("button#status-modal-action").click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout()); //Note. Sometimes selenide click but status has not set.
            $(".modal-content button.btn-default").click();
        } else {
            log.warn("BU has expected status. Skip this step.");
        }
        return this;
    }

    public CreateTagPage clickCreateTagFor(@NotNull String buName) {
        activePageContainer
                .$x(".//a[normalize-space(text())='" + buName + "']/parent::td/parent::tr")
                .$x(".//a[normalize-space(text())='Manage']").click();
        return getCreateTagPage();
    }

    private boolean isBUActive(@NotNull String buName) {
        SelenideElement statusCell = activePageContainer
                .$x(".//a[normalize-space(text())='" + buName + "']/parent::td").sibling(1).shouldBe(visible);

        SelenideElement activateBtn = statusCell.$(ACTIVATE_BTN_SELECTOR);
        SelenideElement deactivateBtn = statusCell.$(DEACTIVATE_BTN_SELECTOR);

        if (activateBtn.isDisplayed() && !deactivateBtn.isDisplayed()) {
            return false;
        }

        if (deactivateBtn.isDisplayed() && !activateBtn.isDisplayed()) {
            return true;
        }

        throw new IllegalStateException("Unexpected state for activate and deactivate buttons.");

    }
}
