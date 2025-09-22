package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by R0manL on 31/08/20.
 */

public class AccountEditPage {
    private final AccountProfileTab accountProfileTab;


    private AccountEditPage(AccountProfileTab accountProfileTab) {
        this.accountProfileTab = accountProfileTab;
    }

    public static AccountEditPage getAccountEditPage() {
        return new AccountEditPage(new AccountProfileTab());
    }

    public AccountEditPage setUseWatermarkAssets(boolean useWatermarkAssets) {
        accountProfileTab.setUseWatermarkAssets(useWatermarkAssets);

        return this;
    }

    public AccountEditPage setUseHrvDistribution(boolean useHrvDistribution) {
        accountProfileTab.setUseHrvDistribution(useHrvDistribution);

        return this;
    }

    public AccountEditPage setMarketingMonitoring(boolean useMarketingMonitoring) {
        accountProfileTab
                .setUseMarketplaceMonitoring(useMarketingMonitoring);

        return this;
    }

    public AccountEditPage setEvents(boolean useEvents) {
        accountProfileTab
                .setEvents(useEvents);

        return this;
    }

    public boolean isDistributionCheckboxSelected() {
        return accountProfileTab.isDistributionCheckboxSelected();
    }

    public boolean isDistributionCheckboxDisplayed() {
        return accountProfileTab.isDistributionCheckboxDisplayed();
    }

    public boolean isEventCheckboxDisplayed() {
        return accountProfileTab.isEventCheckboxDisplayed();
    }

    public boolean isEventCheckboxSelected() {
        return accountProfileTab.isEventCheckboxSelected();
    }

    public boolean isWatermarkCheckboxDisplayed() {
        return accountProfileTab.isWatermarkCheckboxDisplayed();
    }

    public boolean isWatermarkCheckboxSelected() {
        return accountProfileTab.isWatermarkCheckboxSelected();
    }

    public AccountBillingDataTab clickContinue() {
        return accountProfileTab.clickContinue();
    }

    public AccountEditPage checkEnteredAccountName(@NotNull String accountName) {
        accountProfileTab.getAccountNameElm(accountName).shouldBe(visible);

        return this;
    }

    public SelenideElement getAlertSuccessElm() {
        return $(".alerts.success");
    }

    public AccountEditPage setUseBuyBoxAnalysis(boolean setSelected) {
        if (accountProfileTab.isBuyBoxAnalysisEnabled() ^ setSelected) {
            accountProfileTab.setUseBuyBoxAnalysis(setSelected);
        }

        return this;
    }
}
