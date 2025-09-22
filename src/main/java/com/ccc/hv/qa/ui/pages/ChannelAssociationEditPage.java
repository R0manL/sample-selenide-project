package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.AssociationServer;
import com.ccc.hv.qa.ui.pojos.AssociationServerITMS;
import com.ccc.hv.qa.ui.pojos.ChannelAssociation;
import com.ccc.hv.qa.ui.pojos.ChannelAssociationContent;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAIDBy;
import static com.ccc.hv.qa.ui.pages.AssociatePage.getChannelAssociatePage;
import static com.ccc.hv.qa.ui.pages.BusinessUnitEditPage.getBusinessUnitEditPage;
import static com.ccc.hv.qa.ui.pages.CreateTagPage.getCreateTagPage;

/**
 * Created by R0manL on 5/12/21.
 */

public class ChannelAssociationEditPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final SelenideElement changeAutoDistrRuleModal = $x("//*[@class='modal-content' " +
            "and .//*[@id='modal-title-heading' and text()='Change Automated Distribution Rules']]");
    private static final String LIST_ELEMENT_LOCATOR_TEMPLATE = ".//li[. and .//span[normalize-space(text())='%s']]";

    AssociatePage associatePage = getChannelAssociatePage();


    private ChannelAssociationEditPage() {
        // None
    }

    public static ChannelAssociationEditPage getChannelAssociationEditPage() {
        $x(".//h1[@id = 'page-title' and contains(text(),'Edit')]")
                .shouldBe(visible, Duration.ofMillis(ENV_CONFIG.webElmLoadTimeout() * 2));
        return new ChannelAssociationEditPage();
    }

    public SelenideElement getPageTitleElm() {
        return $x(".//h1[@id = 'page-title' and contains(text(),'Edit')]");
    }

    public ChannelAssociationEditPage editChannelAssociationWith(ChannelAssociation ca) {
        log.info("Edit channel association.");

        associatePage.setAdvancedKeywordSettings(ca.getAdvancedKeywordSettings());
        boolean wasAutoDistributionSelected = $("#automaticDistribution").isSelected();
        associatePage.enableAutomatedDistributionRules(ca.isAutomatedDistributionRules());

        if (ca.isAutomatedDistributionRules() ^ wasAutoDistributionSelected) {
            log.info("Confirm 'Change Automated Distribution Rules' alert.");
            //Note. We need this combination: appear + wait + disappear that helps to solve a problem with > popup
            // does not close when run tests in multi-threads.
            changeAutoDistrRuleModal.$("#automaticDistributionConfirmed").should(appear);
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
            changeAutoDistrRuleModal.$("#automaticDistributionConfirmed").click();
            changeAutoDistrRuleModal.should(Condition.disappear);
        }

        associatePage.setOfOnix30DetermineDistrRules(ca.isUseOnix30ToDetermineDistrRules());

        associatePage.preventDistributionOfCurrentAssetVersions(ca.isPreventOfDistributionOfCurrentAssetVersions());

        if (ca.getDistributionRule() != null) {
            associatePage.setDistributionRules(ca.getDistributionRule());
        }

        List<AssociationServer> assocSrvs = ca.getAssociationServers();
        if (assocSrvs.stream().noneMatch(AssociationServerITMS.class::isInstance)) {
            associatePage.setSendTriggerFile(ca.isSendTriggerFile());
        }

        associatePage.setAssociationServers(assocSrvs);
        associatePage.setContactInformation(ca.getContactInformation());
        associatePage.setDistributionNotification(ca.getDistributionNotificationRecipients());
        associatePage.setTakeDownNotification(ca.getTakeDownNotificationRecipients());
        associatePage.setBlackoutDates(ca.getBlackoutDates());
        associatePage.setComments(ca.getComment());

        return this;
    }

    public boolean isWatermarkOptionAvailableFor(ChannelAssociationContent content) {
        return selectContent(content)
                .openOptionsFor(content)
                .getOptionModalRootElmFor(content)
                .$(".enable-watermarking.inline").isDisplayed();
    }

    private ChannelAssociationEditPage selectContent(ChannelAssociationContent content) {
        log.info("Select content type.");
        SelenideElement contentTypeElm = getContentElm(content);
        if (!contentTypeElm.has(cssClass("selected"))) {
            contentTypeElm.click();
        } else {
            log.warn("Content has already selected");
        }
        return this;
    }

    private ChannelAssociationEditPage openOptionsFor(ChannelAssociationContent content) {
        log.info("Open content settings section.");
        SelenideElement contentTypeElm = getContentElm(content);
        contentTypeElm.$(".panel-toggle-btn").click();

        return this;
    }

    private SelenideElement getOptionModalRootElmFor(ChannelAssociationContent content) {
        SelenideElement contentTypeElm = getContentElm(content);
        String contentTypeId = Objects.requireNonNull(contentTypeElm.getAttribute("data-asset-type-id"));
        return $("ul[data-option-panel-group='content'] li.options-panel[data-asset-panel-id='" + contentTypeId + "']")
                .shouldBe(visible);
    }

    private SelenideElement getContentElm(ChannelAssociationContent content) {
        String contentTypeName = content.getContentType().getText();
        log.info("Get content type: '" + contentTypeName + "'");

        return $x(String.format(LIST_ELEMENT_LOCATOR_TEMPLATE, contentTypeName));
    }


    public CreateTagPage navigateToCreateTagPage() {
        log.debug("Navigate to tag filter section.");
        expandDropdownPanel($("#isbnfilter-collapse a"));

        log.debug("Select tag filter options.");
        $("#useIsbnFilters").setSelected(true);

        log.info("Click on 'Don't see a tag in the list? You can create a new tag here.'");
        $("#isbnFilterOptions")
                .$x(".//a[contains(.,\"Don't see a tag in the list? You can create a new tag here.\")]")
                .shouldBe(visible).click();

        return getCreateTagPage();
    }

    public void clickSave() {
        associatePage.clickSave();
    }

    public SelenideElement getCAPageErrorElm() {
        return $("#associate-channel-error");
    }

    public SelenideElement getPublisherPinIsNotProvidedWarningMsgElm() {
        return $(".warning-message");
    }

    public BusinessUnitEditPage navigateToTheBUFromWarningMsg() {
        getPublisherPinIsNotProvidedWarningMsgElm().$("a").click();

        return getBusinessUnitEditPage();
    }

    public void clickSaveAndWaitOnSuccess() {
        associatePage
                .clickSave()
                .isSuccessMsgVisible();
    }

    public void expandServerSection(@NotNull String serverName) {
        SelenideElement serverLinkElm = $x(".//a[contains(@class,'panel-toggle') and normalize-space(text())='" + serverName + "']");
        if (serverLinkElm.has(Condition.cssClass("collapsed"))) {
            log.info("Server " + serverName + " is collapsed, should be expanded.");
            serverLinkElm.click();
        } else {
            log.info("Server " + serverName + " is already expanded. Skip this step.");
        }
        // wait for section to be expanded
        serverLinkElm.$x(".//ancestor::div[@class='panel panel-server']//div[@class='panel-body collapse in']")
                .shouldHave(Condition.attribute("aria-expanded", "true"));
    }

    public ChannelAssociationEditPage expandTagsSection() {
        log.info("Expand 'Tags' section.");
        $("#isbnFilterToggle.panel-toggle.collapsed").click();

        return this;
    }

    public SelenideElement getUseTagFiltersCheckBoxElm() {
        return $("input#useIsbnFilters");
    }

    public SelenideElement getChannelAssociationIDElm(@NotNull String channelName) {
        int caID = getCAIDBy(channelName);
        return $x(".//h1[@id='page-title' and ./*[normalize-space(text())='ID - " + caID + "']]");
    }

    private void expandDropdownPanel(@NotNull SelenideElement expandLinkElm) {
        expandLinkElm.shouldBe(visible);
        String expandedAttribute = expandLinkElm.getAttribute("aria-expanded");
        if (expandedAttribute == null || "false".equals(expandedAttribute)) {
            log.info("Expand dropdown '" + expandLinkElm.getText() + "'.");
            expandLinkElm.click();
        } else {
            log.warn("Dropdown '" + expandLinkElm.getText() + "' has already expanded. Skip expanding.");
        }
    }
}
