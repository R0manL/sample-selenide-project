package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.*;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.ContentType;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.api.services.ProductAPIService.getProductAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.AccountAddPage.getAccountAddPage;
import static com.ccc.hv.qa.ui.pages.BusinessUnitAddPage.getBusinessUnitAddPage;
import static com.ccc.hv.qa.ui.pages.ChannelAddPage.getChannelAddPage;
import static com.ccc.hv.qa.ui.pages.ChannelsEditPage.getChannelEditPagePage;
import static com.ccc.hv.qa.ui.pages.CurrentActivityFeed.getCurrentActivityFeed;
import static com.ccc.hv.qa.ui.pages.IngestAlertBox.getIngestAlertBox;
import static com.ccc.hv.qa.ui.pages.ManageEventsPage.getManageEventsPage;
import static com.ccc.hv.qa.ui.pages.MetadataActivityPage.getMetadataActivityPage;
import static com.ccc.hv.qa.ui.pages.ProductAddPage.getProductAddPage;
import static com.ccc.hv.qa.ui.pages.UserAddPage.getUserAddPage;
import static com.ccc.hv.qa.ui.pages.BusinessUnitsManagePage.getBusinessUnitsManagePage;
import static com.ccc.hv.qa.ui.pages.ChannelAssociatedListPage.getChannelAssociatedListPage;
import static com.ccc.hv.qa.ui.pages.DistributeProductsPage.getDistributeProductsPage;
import static com.ccc.hv.qa.ui.pages.ManageAccountsPage.getManageAccountPage;
import static com.ccc.hv.qa.ui.pages.UserManagePage.getUserManagePage;

/**
 * Created by R0manL on 05/08/20.
 */

public class TopMenu extends PageBase {
    private static final String ADD_USER_CSS_SELECTOR = "#navGotoUserAdd";
    private static final String MANAGE_USERS_CSS_SELECTOR = "#nav-users";
    private static final String ADD_CHANNEL_CSS_SELECTOR = "#gotoChannelAdd";
    private static final String MANAGE_CHANNELS_CSS_SELECTOR = "#nav-new-exclusive";
    private static final String EDIT_CHANNELS_CSS_SELECTOR = "#menu-manage-channels";
    private static final String ADD_BU_CSS_SELECTOR = "#navGotoBusinessUnitAdd";
    private static final String MANAGE_BU_CSS_SELECTOR = "#nav-business-units";
    private static final String BU_CSS_SELECTOR_TEMPLATE = "[data-bsunit-entry-name=\"%s\"] a";
    private static final String MANAGE_ACCOUNT_CSS_SELECTOR = "#menu-manage-accounts";
    private static final String ADD_ACCOUNT_CSS_SELECTOR = "#navGotoAccountAdd";
    private static final String SELECT_ACCOUNT_CSS_SELECTOR_TEMPLATE = "[data-account-entry-name=\"%s\"] a";
    private static final String PRODUCT_DROPDOWN_EXPANDED_SELECTOR = "#productFlyout .dropdown-toggle.selected";

    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private TopMenu() {
        // None
    }

    public static TopMenu getTopMenu() {
        return new TopMenu();
    }

    public TopMenu selectAccountBy(@NotNull String name) {
        if (!getAccountName().equals(name)) {
            expandAccountDropdown();
            log.info("Select account: " + name);
            final String SELECT_ACCOUNT_CSS_SELECTOR = String.format(SELECT_ACCOUNT_CSS_SELECTOR_TEMPLATE, name);
            getActiveMenuContainer().$(SELECT_ACCOUNT_CSS_SELECTOR).click();
            waitOnLoadingMsgDisappeared();
        } else {
            log.info("Account '" + name + "' has already selected. Skip account selection.");
        }

        return this;
    }

    public TopMenu selectAccountWithJSBy(@NotNull String name) {
        if (!getAccountName().equals(name)) {
            log.info("Select account (with JS): " + name);
            final String ACCOUNT_CSS_LOCATOR = String.format(SELECT_ACCOUNT_CSS_SELECTOR_TEMPLATE, name);
            clickWithJsBy(ACCOUNT_CSS_LOCATOR);
            waitOnLoadingMsgDisappeared();
        } else {
            log.info("Account '" + name + "' has already selected. Skip account selection.");
        }

        return this;
    }

    public String getAccountName() {
        return $("#accountToggle").getAttribute("data-initial-account-name");
    }

    public AccountAddPage addAccount() {
        log.info("Click add account.");
        expandAccountDropdown();
        getActiveMenuContainer().$(ADD_ACCOUNT_CSS_SELECTOR).click();
        waitOnLoadingMsgDisappeared();

        return getAccountAddPage();
    }

    public AccountAddPage addAccountWithJS() {
        log.info("Click add account (with JS).");
        clickWithJsBy(ADD_ACCOUNT_CSS_SELECTOR);
        waitOnLoadingMsgDisappeared();

        return getAccountAddPage();
    }

    public ManageAccountsPage clickManageAccounts() {
        log.info("Click manage account.");
        expandAccountDropdown();
        getActiveMenuContainer().$(MANAGE_ACCOUNT_CSS_SELECTOR).click();
        waitOnLoadingMsgDisappeared();

        return getManageAccountPage();
    }

    public ManageAccountsPage clickManageAccountsWithJS() {
        log.info("Click manage account (with JS).");
        clickWithJsBy(MANAGE_ACCOUNT_CSS_SELECTOR);
        waitOnLoadingMsgDisappeared();

        return getManageAccountPage();
    }

    /**
     * @return current page.
     * @deprecated CAUTION: BU list does not have a stroller, use this method only then you aware that item that
     * you select will be on top of the list.
     */
    @Deprecated
    public TopMenu selectBusinessUnitBy(@NotNull String name) {
        final String BU_CSS_SELECTOR = String.format(BU_CSS_SELECTOR_TEMPLATE, name);
        // Wait until menu with BU has been loaded.
        getActiveMenuContainer().$(BU_CSS_SELECTOR).should(exist);
        if (!getSelectedBusinessUnitName().equals(name)) {
            expandBUDropdown();
            log.info("Select '" + name + "' business unit.");
            getActiveMenuContainer().$(BU_CSS_SELECTOR).click();
            waitOnLoadingMsgDisappeared();
        } else {
            log.warn("Business Unit '" + name + "' has already selected. Skip BU selection.");
        }

        return this;
    }

    public TopMenu selectBusinessUnitWithJSBy(@NotNull String name) {
        if (getSelectedBusinessUnitName().equals(name)) {
            log.warn("Business Unit '" + name + "' has already selected. Skip BU selection.");
        } else {
            log.info("Select '" + name + "' business unit (with JS).");
            SelenideElement buLink = $(String.format(BU_CSS_SELECTOR_TEMPLATE, name));
            buLink.should(exist);
            executeJavaScript("arguments[0].click()", buLink);
            waitOnLoadingMsgDisappeared();
        }

        return this;
    }

    public String getSelectedBusinessUnitName() {
        log.info("Get selected business unit.");
        return $("#bsunitToggle")
                .getOwnText()
                .trim();
    }

    /**
     * @return current page.
     * @deprecated CAUTION: BU list does not have a stroller, use this method only then you aware that item that you select will
     * be on top of the list.
     */
    @Deprecated
    public BusinessUnitAddPage clickAddBusinessUnit() {
        log.info("Click add business unit.");
        expandBUDropdown();
        getActiveMenuContainer().$(ADD_BU_CSS_SELECTOR).click();
        waitOnLoadingMsgDisappeared();

        return getBusinessUnitAddPage();
    }

    public BusinessUnitAddPage clickAddBusinessUnitWithJS() {
        log.info("Click add business unit (with JS).");
        clickWithJsBy(ADD_BU_CSS_SELECTOR);
        waitOnLoadingMsgDisappeared();

        return getBusinessUnitAddPage();
    }

    /**
     * @return current page.
     * @deprecated CAUTION: BU list does not have a stroller, use this method only then you aware that item that you select will
     * be on top of the list.
     */
    @Deprecated
    public BusinessUnitsManagePage clickManageBusinessUnit() {
        log.info("Click manage business unit.");
        expandBUDropdown();
        getActiveMenuContainer().$(MANAGE_BU_CSS_SELECTOR).click();
        waitOnLoadingMsgDisappeared();

        return getBusinessUnitsManagePage();
    }

    public BusinessUnitsManagePage clickManageBusinessUnitWithJS() {
        log.info("Click manage business unit (with JS).");
        clickWithJsBy(MANAGE_BU_CSS_SELECTOR);
        waitOnLoadingMsgDisappeared();

        return getBusinessUnitsManagePage();
    }

    public ElementsCollection getListOfBusinessUnits() {
        log.info("Getting list of business units.");
        expandBUDropdown();
        return getActiveMenuContainer().$$("a.business-unit");
    }

    /**
     * Search product by title or isbn and rebuild product search index before if needed.
     *
     * @param title - title value.
     * @return - search result page.
     */
    public SearchProductResultsPage searchPresentProductBy(@NotNull String title) {
        searchByPresentProductTitle(title);

        return new SearchProductResultsPage(title);
    }

    public SearchProductResultsPage searchDeletedProductBy(@NotNull String title) {
        searchDeletedProductByTitle(title);

        return new SearchProductResultsPage(title);
    }

    public SearchProductResultsPage searchPresentProductBy(@NotNull String title, @NotNull String recordReference) {
        searchByPresentProductTitle(title);

        return new SearchProductResultsPage(title, recordReference);
    }


    public ProductAddPage clickAddProduct() {
        log.info("Click add product.");
        expandProductsDropdown();
        getActiveMenuContainer().$("#gotoQuickIngest").shouldBe(enabled).click();
        waitOnLoadingMsgDisappeared();

        return getProductAddPage();
    }

    public MetadataActivityPage clickManageMetadata() {
        log.info("Click manage metadata.");
        expandProductsDropdown();
        getActiveMenuContainer().$("#gotoMetadataManager").shouldBe(enabled).click();
        waitOnLoadingMsgDisappeared();

        return getMetadataActivityPage();
    }

    public ChannelAddPage clickAddChannel() {
        log.info("Click add channel.");
        expandChannelsDropdown();
        getAddChannelButtonElm().click();

        return getChannelAddPage();
    }

    public SelenideElement getAddChannelButtonElm() {
        return getActiveMenuContainer().$(ADD_CHANNEL_CSS_SELECTOR);
    }

    public SelenideElement getEditChannelsButtonElm() {
        return getActiveMenuContainer().$(EDIT_CHANNELS_CSS_SELECTOR);
    }

    public ChannelAddPage clickAddChannelWithJS() {
        log.info("Click add channel (with JS).");
        clickWithJsBy(ADD_CHANNEL_CSS_SELECTOR);

        ChannelAddPage channelAddPage = getChannelAddPage();
        clickWithJSIfElmHasNotVisible(channelAddPage.getPageTitleElm(), ADD_CHANNEL_CSS_SELECTOR);
        waitOnLoadingMsgDisappeared();

        return channelAddPage;
    }

    public ChannelAssociatedListPage clickManageChannel() {
        log.info("Click manage channels.");
        expandChannelsDropdown();
        getManageChannelsBtnElm().click();
        waitOnLoadingMsgDisappeared();

        return getChannelAssociatedListPage();
    }

    public SelenideElement getManageChannelsBtnElm() {
        return getActiveMenuContainer().$(MANAGE_CHANNELS_CSS_SELECTOR);
    }

    public ChannelAssociatedListPage clickManageChannelWithJS() {
        log.info("Click manage channels (with JS).");
        clickWithJsBy(MANAGE_CHANNELS_CSS_SELECTOR);

        ChannelAssociatedListPage caPage = getChannelAssociatedListPage();
        clickWithJSIfElmHasNotVisible(caPage.getPageTitleElm(), MANAGE_CHANNELS_CSS_SELECTOR);
        waitOnLoadingMsgDisappeared();

        return caPage;
    }

    public ChannelsEditPage clickEditChannels() {
        log.info("Click edit channels.");
        expandChannelsDropdown();
        getEditChannelsButtonElm().click();
        waitOnLoadingMsgDisappeared();

        return getChannelEditPagePage();
    }

    public UserAddPage clickAddUser() {
        log.info("Click add user.");
        expandUsersDropdown();
        getActiveMenuContainer().$(ADD_USER_CSS_SELECTOR).click();
        waitOnLoadingMsgDisappeared();

        return getUserAddPage();
    }

    public UserAddPage clickAddUserWithJS() {
        log.info("Click add user (with JS).");
        clickWithJsBy(ADD_USER_CSS_SELECTOR);
        waitOnLoadingMsgDisappeared();

        return getUserAddPage();
    }

    public UserManagePage clickManageUsers() {
        log.info("Click manage users.");
        expandUsersDropdown();
        getActiveMenuContainer().$(MANAGE_USERS_CSS_SELECTOR).click();
        waitOnLoadingMsgDisappeared();

        getUserManagePage().getPageTitleElm().shouldBe(visible);

        return getUserManagePage();
    }

    public DistributeProductsPage clickDistributeProducts() {
        log.info("Click distribute products.");
        expandProductsDropdown();
        getActiveMenuContainer().$("#gotoBatchDistribution").shouldBe(enabled).click();
        waitOnLoadingMsgDisappeared();

        return getDistributeProductsPage();
    }

    public ManageEventsPage clickManageEvents() {
        log.info("Click manage events.");
        expandEventsDropdown();
        getActiveMenuContainer().$("#gotoanalyticsevents").shouldBe(enabled).click();
        waitOnLoadingMsgDisappeared();

        return getManageEventsPage();
    }

    public SelenideElement getSearchFieldElm() {
        return getActiveMenuContainer().$("#searchField");
    }

    private void searchByPresentProductTitle(@NotNull String title) {
        log.debug("Clear local storage.");
        Selenide.clearBrowserLocalStorage();
        getProductAPIService().rebuildProductSearchIndex();
        getProductAPIService().verifyCreatedProductIsInSearchServiceResults(title);
        log.info("Search '" + title + "' product.");
        expandProductsDropdown()
                .getSearchFieldElm()
                .val(title)
                .pressEnter();

        waitOnLoadingMsgDisappeared();
    }

    private void searchDeletedProductByTitle(@NotNull String title) {
        log.debug("Clear local storage.");
        Selenide.clearBrowserLocalStorage();
        getProductAPIService().rebuildProductSearchIndex();
        log.info("Search '" + title + "' product.");
        expandProductsDropdown()
                .getSearchFieldElm()
                .val(title)
                .pressEnter();

        waitOnLoadingMsgDisappeared();
    }

    private void expandAccountDropdown() {
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        if ($(".account-switcher .btn-group.open").exists()) {
            log.debug("Account dropdown has already expanded. Skip this step.");
        } else {
            log.debug("Expand account dropdown.");
            getActiveMenuContainer().$(".account-switcher .btn-group button").click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        }
    }

    private void expandBUDropdown() {
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        if ($(".business-unit .btn-group.open").exists()) {
            log.debug("Business unit dropdown has already expanded. Skip this step.");
        } else {
            log.debug("Expand Business unit dropdown.");
            getActiveMenuContainer().$(".business-unit .btn-group button").click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        }
    }

    public TopMenu expandProductsDropdown() {
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        if (activePageContainer.$(PRODUCT_DROPDOWN_EXPANDED_SELECTOR).exists()) {
            log.debug("Products dropdown has already expanded. Skip this step.");
        } else {
            log.debug("Expand Products dropdown.");
            SelenideElement dropDown = getActiveMenuContainer().$("#productFlyout .dropdown-toggle");
            dropDown.click();
            dropDown.shouldHave(Condition.cssClass("selected"));
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        }

        return this;
    }

    public TopMenu expandChannelsDropdown() {
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        if ($("#channelsFlyout .dropdown-toggle.selected").exists()) {
            log.debug("Channels dropdown has already expanded. Skip this step.");
        } else {
            log.debug("Expand Channels dropdown.");
            getActiveMenuContainer().$("#channelsFlyout .dropdown-toggle").click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        }

        return this;
    }

    public ElementsCollection getChannelsAddedOrUpdatedForLast7DaysWith(@NotNull String channelName) {
        log.info("Getting added or updated channels for the last 7 days with name: " + channelName);
        return activePageContainer.$$(".last-7days-added-updated-CA a").filter(ownText(channelName));
    }

    public ElementsCollection getChannelsDeactivatedForLast7DaysWith(@NotNull String channelName) {
        log.info("Getting deactivated channels for the last 7 days with name: " + channelName);
        return activePageContainer.$$(".last-7days-deactivated-channels a").filter(ownText(channelName));
    }

    public SelenideElement getEventsDropdownElm() {
        return getActiveMenuContainer().$("#eventsFlyout");
    }

    public CurrentActivityFeed expandEventsDropdown() {
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        if ($("#eventsFlyout .dropdown-toggle.selected").exists()) {
            log.debug("Events dropdown has already expanded. Skip this step.");
        } else {
            log.debug("Expand Events dropdown.");
            getEventsDropdownLnk().click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        }

        return getCurrentActivityFeed();
    }

    public SelenideElement getEventsDropdownLnk() {
        return getActiveMenuContainer().$("#eventsFlyout .dropdown-toggle");
    }

    public SelenideElement getUsersDropdownLnk() {
        return getActiveMenuContainer().$(".btn-group #userToggle");
    }

    public int getNumberOfAddedProductsWithinPast24HoursFor(ContentType contentType) {
        log.info("Get number of added products within past 24 hours with content type: " + contentType);
        if (activePageContainer.$("nothing-added").isDisplayed()) {
            return 0;
        }

        SelenideElement countField = activePageContainer.$(".recently-added a[data-plliptype='" + contentType + "'] .count");
        return Integer.parseInt(countField.getOwnText());
    }

    public int getNumberOfUpdatedProductsWithinPast24HoursFor(ContentType contentType) {
        log.info("Get number of updated products within past 24 hours with content type: " + contentType);
        if (activePageContainer.$(".nothing-updated").isDisplayed()) {
            return 0;
        }

        SelenideElement countField = activePageContainer.$(".recently-updated a[data-plliptype='" + contentType + "'] .count");
        return Integer.parseInt(countField.getOwnText());
    }

    public int getTotalNumberOfIngestedProductsOf(ContentType contentType) {
        log.info("Get total number of ingested products of content type: " + contentType);

        SelenideElement countField = activePageContainer.$x(".//*[@class='progress']/*[text()='" + contentType + "']/../*[@class='count total']");
        return Integer.parseInt(countField.getOwnText());
    }

    public int getTotalNumberOfIngestedProductsWithAssetOf(ContentType contentType) {
        log.info("Get total number of ingested products with asset(s) of content type: " + contentType);

        SelenideElement countField = activePageContainer.$x(".//*[@class='progress']/*[text()='" + contentType + "']/../..//*[@class='count assets']");
        return Integer.parseInt(countField.getOwnText());
    }

    public IngestAlertBox expandIngestAlertBox() {
        log.info("Expand ingest alert box");
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        activePageContainer.$("button[style*='display: inline-block'].hrv-alert").click();

        return getIngestAlertBox();
    }

    private void expandUsersDropdown() {
        if ($(".btn-group.open #userToggle").exists()) {
            log.debug("Users dropdown has already expanded. Skip this step.");
        } else {
            log.debug("Expand Users dropdown.");
            getUsersDropdownLnk().click();
        }
    }

    private SelenideElement getActiveMenuContainer() {
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        return $$(".hrv-nav-container")
                .filter(visible)
                .shouldHave(CollectionCondition.size(1))
                .first();
    }
}
