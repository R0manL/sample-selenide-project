package com.ccc.hv.qa.ui.migrated;

import com.ccc.hv.qa.db.pojo.AccountDB;
import com.ccc.hv.qa.db.services.AccountDBService;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.pages.AccountAddPage;
import com.ccc.hv.qa.ui.pages.AccountEditPage;
import com.ccc.hv.qa.ui.pojos.Account;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.AssertionsForClassTypes;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.visible;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.data.Accounts.*;
import static com.ccc.hv.qa.ui.data.BUs.activateDeactivateBU;
import static com.ccc.hv.qa.ui.data.PredUsers.SUPER_ADMIN;
import static com.ccc.hv.qa.ui.pages.AccountAddPage.getAccountAddPage;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.ManageAccountsPage.getManageAccountPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Created by R0manL on 7/19/21.
 */

@Test(groups = {"ui", "account"})
public class AccountTests extends UITestBase {

    @TmsLink("AUT-450")
    public void createAccountWithDefaultSettingsAndMinInfoTest1() {
        getLoginPage().loginAs(SUPER_ADMIN);

        Account account = AQA_CREATE_ACCOUNT_WITH_DEFAULT_SETTINGS;

        getTopMenu()
                .addAccountWithJS()
                .createAccount(account);

        AccountDB createdAccountDB = AccountDBService.getAccountBy(account.getName());

        assertThat(createdAccountDB.isDistributionEnabledYn())
                .as("Distribution is expected to be false")
                .isFalse();

        assertThat(createdAccountDB.isAccmasWatermarkEnabledYn())
                .as("Watermarking is expected to be false")
                .isFalse();
    }

    @TmsLink("AUT-450")
    public void createAccountWithSelectedDistrOptionsTest2() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenu()
                .addAccountWithJS();

        Account account = AQA_CREATE_ACCOUNT_WITH_SELECTED_DISRT_OPTIONS;

        AccountAddPage accountAddPage = getAccountAddPage().setAccountName(account);

        assertThat(accountAddPage.isDistributionCheckboxDisplayed()).isTrue();
        assertThat(accountAddPage.isDistributionCheckboxSelected()).isFalse();
        assertThat(accountAddPage.isWatermarkCheckboxDisplayed()).isFalse();

        accountAddPage.setUseHrvDistribution(true);

        assertThat(accountAddPage.isDistributionCheckboxSelected()).isTrue();
        assertThat(accountAddPage.isWatermarkCheckboxDisplayed()).isTrue();
        assertThat(accountAddPage.isWatermarkCheckboxSelected()).isFalse();

        accountAddPage.setUseWatermarkAssets(true);

        assertThat(accountAddPage.isDistributionCheckboxSelected()).isTrue();
        assertThat(accountAddPage.isWatermarkCheckboxSelected()).isTrue();

        accountAddPage
                .setAllAddress(account)
                .clickSave()
                .getAlertSuccessElm().shouldBe(visible);

        AccountDB createdAccountDB = AccountDBService.getAccountBy(account.getName());

        assertThat(createdAccountDB.isDistributionEnabledYn())
                .as("Distribution is expected to be true")
                .isTrue();

        assertThat(createdAccountDB.isAccmasWatermarkEnabledYn())
                .as("Watermarking is expected to be true")
                .isTrue();
    }

    @TmsLink("AUT-450")
    public void createAccountWithUncheckedDistrOptionsTest3() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenu()
                .addAccountWithJS();

        Account account = AQA_CREATE_ACCOUNT_WITH_UNCHECKED_DISTR_OPTIONS;

        AccountAddPage accountAddPage = getAccountAddPage()
                .setAccountName(account)
                .setUseHrvDistribution(true)
                .setUseWatermarkAssets(true)
                .setUseWatermarkAssets(false);

        assertThat(accountAddPage.isWatermarkCheckboxSelected()).isFalse();

        accountAddPage.setUseHrvDistribution(false);

        assertThat(accountAddPage.isDistributionCheckboxSelected()).isFalse();
        assertThat(accountAddPage.isWatermarkCheckboxSelected()).isFalse();

        accountAddPage
                .setUseHrvDistribution(true)
                .setUseWatermarkAssets(true);

        assertThat(accountAddPage.isDistributionCheckboxSelected()).isTrue();
        assertThat(accountAddPage.isWatermarkCheckboxSelected()).isTrue();

        accountAddPage.setUseHrvDistribution(false);

        assertThat(accountAddPage.isDistributionCheckboxSelected()).isFalse();
        assertThat(accountAddPage.isWatermarkCheckboxDisplayed()).isFalse();

        accountAddPage
                .setAllAddress(account)
                .clickSave()
                .getAlertSuccessElm().shouldBe(visible);

        AccountDB createdAccountDB = AccountDBService.getAccountBy(account.getName());

        assertThat(createdAccountDB.isDistributionEnabledYn())
                .as("Distribution is expected to be false")
                .isFalse();

        assertThat(createdAccountDB.isAccmasWatermarkEnabledYn())
                .as("Watermarking is expected to be false")
                .isFalse();
    }

    @TmsLink("AUT-448")
    public void editAccountWithDefaultSettingsAndMinInfoTest1() {
        getLoginPage().loginAs(SUPER_ADMIN);

        Account account = AQA_EDIT_ACCOUNT_WITH_DEFAULT_SETTINGS;

        getTopMenu()
                .addAccountWithJS()
                .createAccount(account);

        AccountEditPage accountEditPage = getTopMenu()
                .clickManageAccountsWithJS()
                .editAccount(account.getName());

        assertThat(accountEditPage.isDistributionCheckboxDisplayed()).isTrue();
        assertThat(accountEditPage.isDistributionCheckboxSelected()).isFalse();
        assertThat(accountEditPage.isWatermarkCheckboxDisplayed()).isFalse();

        accountEditPage.setUseHrvDistribution(true);

        assertThat(accountEditPage.isDistributionCheckboxSelected()).isTrue();
        assertThat(accountEditPage.isWatermarkCheckboxDisplayed()).isTrue();
        assertThat(accountEditPage.isWatermarkCheckboxSelected()).isFalse();

        accountEditPage.setUseWatermarkAssets(true);

        assertThat(accountEditPage.isDistributionCheckboxSelected()).isTrue();
        assertThat(accountEditPage.isWatermarkCheckboxSelected()).isTrue();

        accountEditPage
                .clickContinue()
                .clickSave()
                .getAlertSuccessElm().shouldBe(visible);

        AccountDB editedAccountDB = AccountDBService.getAccountBy(account.getName());

        assertThat(editedAccountDB.isDistributionEnabledYn())
                .as("Distribution is expected to be true")
                .isTrue();

        assertThat(editedAccountDB.isAccmasWatermarkEnabledYn())
                .as("Watermarking is expected to be true")
                .isTrue();
    }

    @TmsLink("AUT-448")
    public void editAccountWithUncheckedDistrOptionsTest2() {
        getLoginPage().loginAs(SUPER_ADMIN);

        final Account ACCOUNT = AQA_EDIT_ACCOUNT_WITH_UNCHECKED_DISTR_OPTIONS;

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT);

        AccountEditPage accountEditPage = getTopMenu()
                .clickManageAccountsWithJS()
                .editAccount(ACCOUNT.getName());

        assertThat(accountEditPage.isDistributionCheckboxSelected()).isTrue();
        assertThat(accountEditPage.isWatermarkCheckboxSelected()).isTrue();

        accountEditPage.setUseWatermarkAssets(false);

        assertThat(accountEditPage.isDistributionCheckboxSelected()).isTrue();
        assertThat(accountEditPage.isWatermarkCheckboxDisplayed()).isTrue();
        assertThat(accountEditPage.isWatermarkCheckboxSelected()).isFalse();

        accountEditPage.setUseHrvDistribution(false);

        assertThat(accountEditPage.isDistributionCheckboxSelected()).isFalse();
        assertThat(accountEditPage.isWatermarkCheckboxDisplayed()).isFalse();

        accountEditPage.setUseHrvDistribution(true)
                .setUseWatermarkAssets(true);

        assertThat(accountEditPage.isDistributionCheckboxSelected()).isTrue();
        assertThat(accountEditPage.isWatermarkCheckboxSelected()).isTrue();

        accountEditPage.setUseHrvDistribution(false);

        assertThat(accountEditPage.isDistributionCheckboxSelected()).isFalse();
        assertThat(accountEditPage.isWatermarkCheckboxDisplayed()).isFalse();

        accountEditPage
                .clickContinue()
                .clickSave()
                .getAlertSuccessElm().shouldBe(visible);

        AccountDB editedAccountDB = AccountDBService.getAccountBy(ACCOUNT.getName());

        assertThat(editedAccountDB.isDistributionEnabledYn())
                .as("Distribution is expected to be false")
                .isFalse();

        assertThat(editedAccountDB.isAccmasWatermarkEnabledYn())
                .as("Watermarking is expected to be false")
                .isFalse();
    }

    @TmsLink("AUT-462")
    public void verifyUserCanActivateDeactivateAccount() {
        getLoginPage().loginAs(SUPER_ADMIN);

        final Account ACCOUNT = AQA_ACTIVATED_INACTIVATED_TEST_ACCOUNT;
        final BusinessUnit BU = activateDeactivateBU;

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT);

        getTopMenu()
                .selectAccountBy(ACCOUNT.getName())
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(BU)
                .getBUCreatedSuccessMessageElm().shouldBe(visible);

        getTopMenu()
                .clickManageAccountsWithJS()
                .activateAccount(ACCOUNT.getName());

        final long MAX_TIMEOUT = ENV_CONFIG.awaitilityTimeout();
        await().atMost(MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    AccountDB accountDB = AccountDBService.getAccountBy(ACCOUNT.getName());

                    assertThat(accountDB.isActiveYn())
                            .as("Account is expected to be active")
                            .isTrue();
                });

        getManageAccountPage().deactivateAccount(ACCOUNT.getName());

        await().atMost(MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    AccountDB accountDB = AccountDBService.getAccountBy(ACCOUNT.getName());

                    assertThat(accountDB.isActiveYn())
                            .as("Account is expected to be inactive")
                            .isFalse();
                });
    }

    @TmsLink("HRV-15035")
    @Test(groups = {"manageEvents"})
    public void verifyManageEventsCheckboxEventsOnAccountAddPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        final Account ACCOUNT = ACCOUNT_FOR_ADD_EVENTS_ON_TEST;

        getTopMenu().addAccountWithJS();

        AccountAddPage accountAddPage = getAccountAddPage()
                .setAccountName(ACCOUNT);

        assertThat(accountAddPage.isEventCheckboxDisplayed()).isFalse();

        accountAddPage.setMarketingMonitoring(true);
        assertThat(accountAddPage.isEventCheckboxDisplayed()).isTrue();
        assertThat(accountAddPage.isEventCheckboxSelected()).isFalse();

        accountAddPage.setEvents(true);
        accountAddPage.setMarketingMonitoring(false);
        assertThat(accountAddPage.isEventCheckboxDisplayed()).isFalse();

        accountAddPage.setMarketingMonitoring(true);
        assertThat(accountAddPage.isEventCheckboxDisplayed()).isTrue();
        assertThat(accountAddPage.isEventCheckboxSelected()).isFalse();

        accountAddPage.setEvents(true);

        accountAddPage
                .setAllAddress(ACCOUNT)
                .clickSave()
                .getAlertSuccessElm()
                .shouldBe(visible);

        boolean isEventsAvailable = AccountDBService.getAccountBy(ACCOUNT.getName()).isEventsEnabledYn();
        AssertionsForClassTypes.assertThat(isEventsAvailable).as("Events checkbox value is not expected").isTrue();
    }

    @TmsLink("HRV-15035")
    @Test(groups = {"manageEvents"})
    public void verifyManageEventsCheckboxEventsOffAccountAddPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        final Account ACCOUNT = ACCOUNT_FOR_ADD_EVENTS_OFF_TEST;

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT);

        boolean isEventsAvailable = AccountDBService.getAccountBy(ACCOUNT.getName()).isEventsEnabledYn();
        AssertionsForClassTypes.assertThat(isEventsAvailable).as("Events checkbox value is not expected").isFalse();
    }

    @TmsLink("HRV-15035")
    @Test(groups = {"manageEvents"})
    public void verifyManageEventsCheckboxEventsOnAccountEditPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        final Account ACCOUNT = ACCOUNT_FOR_EDIT_EVENTS_ON_TEST;

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT);

        AccountEditPage accountEditPage = getTopMenu()
                .clickManageAccountsWithJS()
                .editAccount(ACCOUNT.getName());

        assertThat(accountEditPage.isEventCheckboxDisplayed()).isFalse();

        accountEditPage.setMarketingMonitoring(true);
        assertThat(accountEditPage.isEventCheckboxDisplayed()).isTrue();
        assertThat(accountEditPage.isEventCheckboxSelected()).isFalse();

        accountEditPage.setEvents(true);
        accountEditPage.setMarketingMonitoring(false);
        assertThat(accountEditPage.isEventCheckboxDisplayed()).isFalse();
        assertThat(accountEditPage.isEventCheckboxSelected()).isFalse();

        accountEditPage.setMarketingMonitoring(true);
        assertThat(accountEditPage.isEventCheckboxDisplayed()).isTrue();

        accountEditPage.setEvents(true);

        accountEditPage
                .clickContinue()
                .clickSave()
                .getAlertSuccessElm()
                .shouldBe(visible);

        boolean isEventsAvailable = AccountDBService.getAccountBy(ACCOUNT.getName()).isEventsEnabledYn();
        AssertionsForClassTypes.assertThat(isEventsAvailable).as("Events checkbox value is not expected").isTrue();
    }

    @TmsLink("HRV-15035")
    @Test(groups = {"manageEvents"})
    public void verifyManageEventsCheckboxEventsOffAccountEditPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        final Account ACCOUNT = ACCOUNT_FOR_EDIT_EVENTS_OFF_TEST;

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT);

        AccountEditPage accountEditPage = getTopMenu()
                .clickManageAccountsWithJS()
                .editAccount(ACCOUNT.getName());

        assertThat(accountEditPage.isEventCheckboxSelected()).isTrue();

        accountEditPage.setMarketingMonitoring(false);
        assertThat(accountEditPage.isEventCheckboxDisplayed()).isFalse();

        accountEditPage.setMarketingMonitoring(true);
        assertThat(accountEditPage.isEventCheckboxDisplayed()).isTrue();
        assertThat(accountEditPage.isEventCheckboxSelected()).isFalse();

        accountEditPage
                .clickContinue()
                .clickSave()
                .getAlertSuccessElm()
                .shouldBe(visible);


        boolean isEventsAvailable = AccountDBService.getAccountBy(ACCOUNT.getName()).isEventsEnabledYn();
        AssertionsForClassTypes.assertThat(isEventsAvailable).as("Events checkbox value is not expected").isFalse();
    }
}
