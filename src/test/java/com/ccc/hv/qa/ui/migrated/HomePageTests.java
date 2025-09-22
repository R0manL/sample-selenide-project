package com.ccc.hv.qa.ui.migrated;

import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.data.PredUsers;
import com.ccc.hv.qa.ui.pages.ReportsPage;
import com.ccc.hv.qa.ui.pojos.Account;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import com.ccc.hv.qa.ui.pojos.User;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.ccc.hv.qa.api.services.AccountAPIService.getAccountAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.data.Accounts.ACCOUNT_WITHOUT_ANALYTICS_TEST;
import static com.ccc.hv.qa.ui.data.BUs.regressionBUWithoutAnalytics;
import static com.ccc.hv.qa.ui.data.PredUsers.SUPER_ADMIN;
import static com.ccc.hv.qa.ui.pages.HomePage.getHomePage;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;

@Epic("Login")
@Story("As user I should be able to see Homepage with valid elements and links on it.")
public class HomePageTests extends UITestBase {

    @DataProvider(name = "users")
    private Object[][] users() {
        return new Object[][]{
                {PredUsers.SUPER_ADMIN},
                {PredUsers.ACC_ADMIN},
                {PredUsers.METADATA_ADMIN}};
    }

    @TmsLink("AUT-635")
    @Test(dataProvider = "users")
    public void verifyHomePageLinks(User user) {
        getLoginPage().loginAs(user);
        if (user.equals(SUPER_ADMIN)) {
            getTopMenuTestService().selectDefaultTestAccountAndBu();
        } else {
            getTopMenuTestService().selectDefaultBU();
        }
        getHomePage().getAnalyticsLinkElm()
                .shouldHave(exactText("Analytics"));
        getHomePage().getReportLinkElm()
                .shouldHave(exactText("Reports"))
                .shouldHave(attribute("href", ENV_CONFIG.hrvReaperHostUrl() + "/reaper/private/homepage#/dashboards/1"));
        getHomePage().getCopyRightElm()
                .shouldHave(exactText("© copyright " + LocalDate.now().getYear() + " Lakeside Book Company. All rights reserved."));
        getHomePage().getHelpElm()
                .shouldHave(exactText("Help"))
                .shouldHave(attribute("href", ENV_CONFIG.hrvReaperHostUrl() + "/reaper/private/hrvhelp"));
        getHomePage().getFAQElm()
                .shouldHave(exactText("FAQ"))
                .shouldHave(attribute("href", ENV_CONFIG.hrvReaperHostUrl() + "/reaper/private/hrvhelp?faq=true"));
        getTopMenu().getEventsDropdownElm().shouldHave(exactText("Events"));
        getTopMenu().clickManageEvents().getPageTitleElm().shouldHave(exactText("Manage Events"));
    }

    @TmsLink("AUT-775")
    @Test
    public void verifyReportsNavigateToSisenseDashboard() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ReportsPage page = getHomePage().clickReports();

        page.getToolBarElm().shouldBe(visible);
        page.getLeftViewSectionElm().shouldBe(visible);
        page.getDashboardSectionElm().shouldBe(visible);
    }

    @TmsLink("AUT-777")
    @Test
    public void verifyHomePageAnalyticsLinkShouldNotBeDisplayed() {
        getLoginPage().loginAs(SUPER_ADMIN);

        final Account ACCOUNT = ACCOUNT_WITHOUT_ANALYTICS_TEST;
        final BusinessUnit BU = regressionBUWithoutAnalytics;

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT);

        getTopMenu()
                .selectAccountBy(ACCOUNT.getName())
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(BU)
                .getBUCreatedSuccessMessageElm().shouldBe(visible);

        getAccountAPIService().activateAccount(ACCOUNT.getName());

        getTopMenu().selectBusinessUnitWithJSBy(BU.getName());

        getHomePage().getAnalyticsLinkElm().shouldNotBe(visible);
    }

    @TmsLink("AUT-777")
    @Test
    public void verifyHomePageAnalyticsLinkShouldBeDisplayed() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();
        getHomePage().getAnalyticsLinkElm()
                .shouldHave(exactText("Analytics"));
        getHomePage().navigateToBuyBoxAnalysis()
                .getBuyBoxAnalyticsTitle()
                .shouldBe(visible)
                .shouldHave(exactText("BUY BOX ANALYSIS"));
    }
}
