package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.*;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.ChannelAssociationHeaders;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.ArrayList;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.AssociatePage.getChannelAssociatePage;
import static com.ccc.hv.qa.ui.pages.ChannelAssociationViewPage.getChannelAssociationViewPage;
import static com.ccc.hv.qa.ui.pages.MetadataOptimizationPage.getChannelOptimizationPage;

/**
 * Created by R0manL on 25/08/20.
 */

public class ChannelAssociatedListPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final SelenideElement TABLE_CA_NAME_HEADER_ELM = activePageContainer.$x(".//th[contains(@aria-label," +
            "'Channel Association') and @aria-controls='channel-association-table']");
    private static final SelenideElement TABLE_HEADER_ID_ELM = activePageContainer.$("#channel-association-table thead")
            .$x(".//th[@role='columnheader' and normalize-space(text())='" + ChannelAssociationHeaders.ID + "']");


    private ChannelAssociatedListPage() {
        // None
    }

    public static ChannelAssociatedListPage getChannelAssociatedListPage() {
        return new ChannelAssociatedListPage();
    }

    public SelenideElement getPageTitleElm() {
        return $x(".//h1[@id='page-title' and contains(.,'Channel Associations')]");
    }

    public AssociatePage openAddChannelAssociationsPageFor(@NotNull String caName) {
        log.info("Open add CA for '" + caName + "' channel.");
        activePageContainer.$x(".//button[@data-channel-name='" + caName + "']")
                .shouldBe(visible, Duration.ofMillis(Configuration.pageLoadTimeout))
                .click();

        return getChannelAssociatePage();
    }

    public MetadataOptimizationPage openOptimizationPageFor(@NotNull String channelName) {
        log.info("Open '" + channelName + "' channel's optimization page.");
        SelenideElement channelNameElm = activePageContainer.$x(".//td[.//a[normalize-space(text())='" + channelName + "']]");
        SelenideElement watermarkOnOptimizationEditElm = channelNameElm.sibling(1).$x(".//a[@class='gotoOptimizationsManagement']");
        SelenideElement watermarkOffOptimizationEditElm = channelNameElm.sibling(0).$x(".//a[@class='gotoOptimizationsManagement']");
        getFirstVisibleElement(watermarkOnOptimizationEditElm, watermarkOffOptimizationEditElm).click();

        return getChannelOptimizationPage();
    }

    public SelenideElement getChannelLinkElmWith(@NotNull String uniqueChannelName) {
        return activePageContainer.$$x(".//td/a[normalize-space(text())='" + uniqueChannelName + "']")
                .shouldHave(CollectionCondition.size(1))
                .first();
    }

    public ChannelAssociationViewPage openChannelAssociationViewPage(@NotNull String channelName) {
        log.info("Open channel association view page for: '" + channelName + "' channel.");
        activePageContainer.$x(".//td/a[normalize-space(text())='" + channelName + "']").click();

        return getChannelAssociationViewPage();
    }

    public List<String> getListOfAllChannelAssociationNames() {
        ElementsCollection channels = $$("a[data-channel-association-id]");
        List<String> result = new ArrayList<>();

        // Wait till all Channels will load (names will not be empty).
        for(SelenideElement channel : channels) {
            result.add(channel.shouldNotBe(Condition.empty).getOwnText().trim());
        }

        return result;
    }

    public ChannelAssociatedListPage filterChannelAssociationsBy(@NotNull String text) {
        log.info("Filter channel associations by '" + text + "'.");
        activePageContainer.$("#channel-association-table_filter input").val(text).pressEnter();

        return this;
    }

    public ChannelAssociatedListPage clearFiltering() {
        log.info("Clear CA filtering.");
        filterChannelAssociationsBy("");

        return this;
    }

    public ChannelAssociatedListPage activateChannelAssociationFor(@NotNull String channelName) {
        log.info("Activate '" + channelName + "' channel association.");
        getInactiveBtnFor(channelName).click();
        confirmActivationOrDeactivation();

        return this;
    }

    public ChannelAssociatedListPage deactivateChannel(@NotNull String channelName) {
        log.info("Deactivate '" + channelName + "' channel association.");
        getActiveBtnFor(channelName).click();
        confirmActivationOrDeactivation();

        return this;
    }

    public SelenideElement getActiveBtnFor(@NotNull String channelName) {
        return activePageContainer.$x(".//td/*[@data-channel-name='" + channelName + "' " +
                "and ./button[contains(@class,'btn-success')]]");
    }

    public SelenideElement getInactiveBtnFor(@NotNull String caName) {
        return activePageContainer.$x(".//td/*[@data-channel-name='" + caName + "' " +
                "and ./button[contains(@class,'btn-danger')]]");
    }

    public SelenideElement getNoRecordInTableElm() {
        return activePageContainer.$(".dataTables_empty");
    }

    public ChannelAssociatedListPage clickSortChannelAssociationsByName(boolean ascending) {
        if(ascending ^ isListOfChannelsSortedByNameAscending()) {
            TABLE_CA_NAME_HEADER_ELM.click();
        }

        return this;
    }

    private boolean isListOfChannelsSortedByNameAscending() {
        String sorted = TABLE_CA_NAME_HEADER_ELM.getAttribute("aria-sort");
        Objects.requireNonNull(sorted, "Can't identify if CA sorted by name. No 'aria-sort' attribute.");

        return "ascending".equals(sorted);
    }

    private void confirmActivationOrDeactivation() {
        activePageContainer.$("button#associate-channel-status-modal-action").click();
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout()); // Note. Check if activation/deactivation (verifyChannelAssociationActivationDeactivationBySuperAdmin) test start working more stable.
        activePageContainer.$x(".//*[@id='associate-channel-status-modal']//button[@data-dismiss='modal' and normalize-space(text())='Close']").click();
    }

    public ElementsCollection getChannelAssociationHeaderElms() {
        return activePageContainer.$("#channel-association-table thead").$$("th[role='columnheader']");
    }

    public ChannelAssociatedListPage clickSortCAByID(boolean ascending) {
        if (ascending ^ isListOfCASortedByIdAscending()) {
            TABLE_HEADER_ID_ELM.click();
        }

        return this;
    }

    private boolean isListOfCASortedByIdAscending() {
        TABLE_HEADER_ID_ELM.click();
        String sorted = TABLE_HEADER_ID_ELM.getAttribute("aria-sort");
        Objects.requireNonNull(sorted, "Can't identify if CA sorted by ID. No 'aria-sort' attribute.");

        return "ascending".equals(sorted);
    }

    public ElementsCollection getListOfIdElms() {
        return activePageContainer.$("#channel-association-table tbody").$$x(".//tr/td[2]");
    }

    @NotNull
    public List<String> getListOfStringsNotEmptyIds() {
        return getListOfIdElms().filter(not(empty)).texts();
    }

    @NotNull
    public List<Integer> getListOfIntegersNotEmptyIds() {
        return getListOfStringsNotEmptyIds().stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
