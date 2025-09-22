package com.ccc.hv.qa.ui.migrated;

import com.codeborne.selenide.Selenide;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.data.PredUsers;
import com.ccc.hv.qa.ui.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.ccc.hv.qa.ui.pages.LoginPage.INCORRECT_LOGIN_PASS_MSG;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.NavigationBar.getNavigationBar;

/**
 * Created by R0manL on 31/07/20.
 */

@Epic("Login")
@Story("As user I should be able login to system from Login page when specify valid credentials in other case validation error should be displayed.")
@Test(groups = {"regression", "ui", "login"})
public class LoginPageTests extends UITestBase {

    @DataProvider(name = "credsDataProvider")
    private Object[][] credsDataProvider() {
        return new Object[][]{
                {"Incorrect login", PredUsers.ACC_ADMIN.getPassword()},
                {PredUsers.HV_VIEW_MNG.getEmail(), "Incorrect password"},
                {PredUsers.ACCOUNT_MNG.getEmail(), "1='1"}};
    }

    @Test(dataProvider = "credsDataProvider")
    public void verifyUserCantLoginWithIncorrectCredentials(String username, String password) {
        getLoginPage()
                .loginWithInvalid(username, password)
                .errorMessageElm()
                .shouldHave(text(INCORRECT_LOGIN_PASS_MSG));

        Selenide.refresh();
    }

    @TmsLink("AUT-637")
    @Test(groups = {"regression", "ui", "login"})
    public void verifyLoginPage() {
        LoginPage loginPage = getLoginPage().openLoginPage();

        if (loginPage.getPublisherHubLogoElm().is(not(visible))) {
            getNavigationBar().clickLogoutLink();
            getLoginPage().openLoginPage();
        }

        loginPage.getPublisherHubLogoElm().shouldHave(exactText("Log into your account\n" +
                "Welcome! Please enter your account details to access the Publishers Hub."));
        loginPage.getUsernameLabelElm().shouldHave(exactText("Email"));
        loginPage.getPasswordLabelElm().shouldHave(exactText("Password"));
        loginPage.getLoginButtonElm().shouldHave(value("Sign In")).shouldHave(enabled);
    }
}