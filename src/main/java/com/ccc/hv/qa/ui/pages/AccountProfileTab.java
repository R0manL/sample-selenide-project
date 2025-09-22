package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by R0manL on 06/04/21.
 */

public class AccountProfileTab {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private final SelenideElement accountNameElm = $("#account-name");
    private final SelenideElement distributionElm = $("#distribution");
    private final SelenideElement watermarkElm = $("#watermarkEnabled");
    private final SelenideElement marketplaceMonitoringElm = $("#monitoring");
    private final SelenideElement complianceElm = $("#compliance");
    private final SelenideElement engagementElm = $("#engagement");
    private final SelenideElement compAnalyticsElm = $("#synthetics");

    private final SelenideElement events = $("#events");
    private final SelenideElement compProductsElm = $("#synthLimit");
    private final SelenideElement buyBoxElm = $("#buybox");
    private final SelenideElement autoCorrectElm = $("#autocorrect");
    private final SelenideElement accFirstNameElm = $("#account-physical-first-name");
    private final SelenideElement accLastNameElm = $("#account-physical-last-name");
    private final SelenideElement accTitleElm = $("#account-physical-title");
    private final SelenideElement accPhoneElm = $("#account-physical-phone");
    private final SelenideElement accEmailElm = $("#account-physical-email");
    private final SelenideElement accAddr1Elm = $("#account-physical-address1");
    private final SelenideElement accAddr2Elm = $("#account-physical-address2");
    private final SelenideElement accCityElm = $("#account-physical-city");
    private final SelenideElement accStateElm = $("#account-physical-state");
    private final SelenideElement accZipElm = $("#account-physical-zip");
    private final SelenideElement accCountryElm = $("#account-physical-country");
    private final SelenideElement nextBtn = $("#gotoValidate");

    public AccountProfileTab setAccountName(@NotNull String accountName) {
        log.info("Set account name: '" + accountName + "'.");
        accountNameElm.val(accountName);

        return this;
    }

    public SelenideElement getAccountNameElm(@NotNull String accountName) {
        log.info("Entered account name should be: '" + accountName + "'.");

        return accountNameElm.shouldHave(value(accountName));
    }

    public AccountProfileTab setUseHrvDistribution(boolean useHrvDistribution) {
        log.info("Set use hrv distribution: '" + useHrvDistribution + "'.");
        distributionElm.setSelected(useHrvDistribution);

        return this;
    }

    public AccountProfileTab setUseWatermarkAssets(boolean useWatermarkAssets) {
        log.info("Set use watermark assets: '" + useWatermarkAssets + "'.");
        watermarkElm.setSelected(useWatermarkAssets);

        return this;
    }

    public AccountProfileTab setUseMarketplaceMonitoring(boolean useMarketplaceMonitoring) {
        log.info("Set use marketplace monitoring: '" + useMarketplaceMonitoring + "'.");
        marketplaceMonitoringElm.setSelected(useMarketplaceMonitoring);

        return this;
    }

    public AccountProfileTab setUseMarketplaceComplianceData(boolean useMarketplaceComplianceData) {
        log.info("Set use marketplace compliance data: '" + useMarketplaceComplianceData + "'.");
        complianceElm.setSelected(useMarketplaceComplianceData);

        return this;
    }

    public AccountProfileTab setUseConsumerEngagement(boolean useConsumerEngagement) {
        log.info("Set use consumer engagement: '" + useConsumerEngagement + "'.");
        engagementElm.setSelected(useConsumerEngagement);

        return this;
    }

    public AccountProfileTab setUseCompetitiveAnalytics(boolean useCompetitiveAnalytics) {
        log.info("Set use competitive analytics: '" + useCompetitiveAnalytics + "'.");
        compAnalyticsElm.setSelected(useCompetitiveAnalytics);

        return this;
    }

    public AccountProfileTab setEvents(boolean useEvents) {
        log.info("Set use competitive analytics: '" + useEvents + "'.");
        events.setSelected(useEvents);

        return this;
    }

    public AccountProfileTab setCompetitorProducts(int competitorProducts) {
        log.info("Set competitor products: '" + competitorProducts + "'.");
        compProductsElm.val(Integer.toString(competitorProducts));

        return this;
    }

    public AccountProfileTab setUseBuyBoxAnalysis(boolean useBuyBoxAnalysis) {
        log.info("Set use buy box analysis: '" + useBuyBoxAnalysis + "'.");
        buyBoxElm.setSelected(useBuyBoxAnalysis);

        return this;
    }

    public AccountProfileTab setUserAutoCorrect(boolean userAutoCorrect) {

        log.info("Set user auto correct: '" + userAutoCorrect + "'.");
        autoCorrectElm.setSelected(userAutoCorrect);

        return this;
    }

    public AccountProfileTab setFirstName(@NotNull String firstName) {
        log.info("Set first name: '" + firstName + "'.");
        accFirstNameElm.val(firstName);

        return this;
    }

    public AccountProfileTab setLastName(@NotNull String lastName) {
        log.info("Set last name: '" + lastName + "'.");
        accLastNameElm.val(lastName);

        return this;
    }

    public AccountProfileTab setContactTitle(@NotNull String title) {
        log.info("Set title: '" + title + "'.");
        accTitleElm.val(title);

        return this;
    }

    public AccountProfileTab setPhoneNumber(@NotNull String phone) {
        log.info("Set phone: '" + phone + "'.");
        accPhoneElm.val(phone);

        return this;
    }

    public AccountProfileTab setEmail(@NotNull String email) {
        log.info("Set email: '" + email + "'.");
        accEmailElm.val(email);

        return this;
    }

    public AccountProfileTab setAddress1(@NotNull String address) {
        log.info("Set address: '" + address + "'.");
        accAddr1Elm.val(address);

        return this;
    }

    public AccountProfileTab setAddress2(@NotNull String address) {
        log.info("Set address: '" + address + "'.");
        accAddr2Elm.val(address);

        return this;
    }

    public AccountProfileTab setCity(@NotNull String city) {
        log.info("Set city: '" + city + "'.");
        accCityElm.val(city);

        return this;
    }

    public AccountProfileTab setState(@NotNull String state) {
        log.info("Set state: '" + state + "'.");
        accStateElm.val(state);

        return this;
    }

    public AccountProfileTab setPostalCode(@NotNull String zip) {
        log.info("Set zip: '" + zip + "'.");
        accZipElm.val(zip);

        return this;
    }

    public AccountProfileTab setCountry(@NotNull String country) {
        log.info("Set country: '" + country + "'.");
        accCountryElm.val(country);

        return this;
    }

    public AccountBillingDataTab clickContinue() {
        log.info("Click continue.");
        nextBtn.click();

        return new AccountBillingDataTab();
    }

    public boolean isBuyBoxAnalysisEnabled() {
        return buyBoxElm.isSelected();
    }

    public boolean isWatermarkCheckboxDisplayed(){
        return watermarkElm.isDisplayed();
    }

    public boolean isWatermarkCheckboxSelected(){
        return watermarkElm.isSelected();
    }

    public boolean isDistributionCheckboxDisplayed(){
        return distributionElm.isDisplayed();
    }

    public boolean isEventCheckboxDisplayed(){
        return events.isDisplayed();
    }

    public boolean isEventCheckboxSelected(){
        return events.isSelected();
    }

    public boolean isDistributionCheckboxSelected(){
        return distributionElm.isSelected();
    }
}
