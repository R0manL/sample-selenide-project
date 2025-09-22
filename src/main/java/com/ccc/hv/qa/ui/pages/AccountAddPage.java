package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.Account;
import com.ccc.hv.qa.ui.pojos.Address;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by R0manL on 31/08/20.
 */

public class AccountAddPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final AccountProfileTab accountProfileTab;


    private AccountAddPage(AccountProfileTab accountProfileTab) {
        this.accountProfileTab = accountProfileTab;
    }

    public static AccountAddPage getAccountAddPage() {
        return new AccountAddPage(new AccountProfileTab());
    }

    public void createAccount(@NotNull Account account) {
        log.info("Create '" + account.getName() + "' account.");

        setAccountName(account);
        setUseHrvDistribution(account.isUseHrvDistribution());
        if (account.isUseHrvDistribution()) {
            setUseWatermarkAssets(account.isUseWatermarkAssets());
        }
        accountProfileTab.setUseMarketplaceMonitoring(account.isUseMarketplaceMonitoring());
        if (account.isUseMarketplaceMonitoring()) {
            accountProfileTab
                    .setUseMarketplaceComplianceData(account.isUseMarketplaceComplianceData())
                    .setUseConsumerEngagement(account.isUseConsumerEngagement())
                    .setUseCompetitiveAnalytics(account.isUseCompetitiveAnalytics());

            if (account.isUseCompetitiveAnalytics()) {
                accountProfileTab.setCompetitorProducts(account.getCompetitorProducts());
            }

            accountProfileTab.setUseBuyBoxAnalysis(account.isUseBuyBoxAnalysis());
            accountProfileTab.setEvents(account.isUseEvents());

        }

        if (account.isUseHrvDistribution()
                && account.isUseWatermarkAssets()
                && account.isUseMarketplaceComplianceData()
                && account.isUseConsumerEngagement()
                && account.isUserAutoCorrect()) {
            accountProfileTab.setUserAutoCorrect(account.isUserAutoCorrect());
        }

        setAllAddress(account).clickSave();
        getAlertSuccessElm().shouldBe(visible);
    }

    public AccountAddPage setAccountName(@NotNull Account account) {
        accountProfileTab.setAccountName(account.getName());

        return this;

    }

    public AccountAddPage setUseWatermarkAssets(boolean useWatermarkAssets) {
        accountProfileTab.setUseWatermarkAssets(useWatermarkAssets);

        return this;
    }

    public AccountAddPage setMarketingMonitoring(boolean useMarketingMonitoring) {
        accountProfileTab
                .setUseMarketplaceMonitoring(useMarketingMonitoring);

        return this;
    }

    public AccountAddPage setEvents(boolean useEvents) {
        accountProfileTab
                .setEvents(useEvents);

        return this;
    }

    public AccountAddPage setUseHrvDistribution(boolean useHrvDistribution) {
        accountProfileTab
                .setUseHrvDistribution(useHrvDistribution);

        return this;
    }

    public AccountBillingDataTab setAllAddress(@NotNull Account account) {
        Address contactAddr = account.getContactAddress();
        Address billingAddr = account.getBillingAddress();
        return accountProfileTab
                .setFirstName(contactAddr.getFirstName())
                .setLastName(contactAddr.getLastName())
                .setContactTitle(contactAddr.getContactTitle())
                .setPhoneNumber(contactAddr.getPhoneNumber())
                .setEmail(contactAddr.getEmail())
                .setAddress1(contactAddr.getAddressOne())
                .setAddress2(contactAddr.getAddressTwo())
                .setCity(contactAddr.getCity())
                .setState(contactAddr.getState())
                .setPostalCode(contactAddr.getPostalCode())
                .setCountry(contactAddr.getCountry())
                .clickContinue()
                .setFirstName(billingAddr.getFirstName())
                .setLastName(billingAddr.getLastName())
                .setContactTitle(billingAddr.getContactTitle())
                .setPhoneNumber(billingAddr.getPhoneNumber())
                .setEmail(billingAddr.getEmail())
                .setAddress1(billingAddr.getAddressOne())
                .setAddress2(billingAddr.getAddressTwo())
                .setCity(billingAddr.getCity())
                .setState(billingAddr.getState())
                .setPostalCode(billingAddr.getPostalCode())
                .setCountry(billingAddr.getCountry());
    }

    public boolean isDistributionCheckboxSelected() {
        return accountProfileTab.isDistributionCheckboxSelected();
    }

    public boolean isDistributionCheckboxDisplayed() {
        return accountProfileTab.isDistributionCheckboxDisplayed();
    }

    public boolean isWatermarkCheckboxDisplayed() {
        return accountProfileTab.isWatermarkCheckboxDisplayed();
    }

    public boolean isWatermarkCheckboxSelected() {
        return accountProfileTab.isWatermarkCheckboxSelected();
    }

    public boolean isEventCheckboxDisplayed() {
        return accountProfileTab.isEventCheckboxDisplayed();
    }

    public boolean isEventCheckboxSelected() {
        return accountProfileTab.isEventCheckboxSelected();
    }

    public SelenideElement getAlertSuccessElm() {
        return $(".alerts.success");
    }
}
