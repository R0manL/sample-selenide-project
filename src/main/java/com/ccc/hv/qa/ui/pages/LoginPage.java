package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.api.pojo.KeycloakUser;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.User;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.api.services.UserAPIService.getUserAPIService;
import static com.ccc.hv.qa.ui.pages.HomePage.switchToHrvFrame;
import static com.ccc.hv.qa.ui.pages.NavigationBar.getNavigationBar;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    public static final String INCORRECT_LOGIN_PASS_MSG = "Invalid email or password.";


    private LoginPage() {
        // None
    }

    public static LoginPage getLoginPage() {
        return new LoginPage();
    }

    /**
     * Login with username, password.
     *
     * @param username username to login
     * @param password pass to login
     * @return new instance of HomePage.
     */
    public HomePage loginAs(@NotNull String username, @NotNull String password) {
        openLoginPage();

        if (!getUsernameInputElm().exists()) {
            if (hasUserLoggedIn(username)) {
                return switchToHrvFrame();
            } else {
                getNavigationBar().clickLogoutLink();

                cleanCookiesAndCloseDriver();

                openLoginPage();
            }
        }

        setUserName(username)
                .setPassword(password)
                .clickLoginBtn();

        assertThat(hasUserLoggedIn(username))
                .as("We have logged in with wrong user.")
                .isTrue();

        waitOnHrvAppLoaded();

        return switchToHrvFrame();
    }

    public HomePage loginAs(User user) {
        return getLoginPage().loginAs(user.getEmail(), user.getPassword());
    }

    public LoginPage loginWithInvalid(@NotNull String username, @NotNull String password) {
        openLoginPage();
        if (!getUsernameInputElm().exists()) {
            getNavigationBar().clickLogoutLink();
        }
        openLoginPage()
                .setUserName(username)
                .setPassword(password)
                .clickLoginBtn();

        return getLoginPage();
    }

    public void waitOnHrvAppLoaded() {
        log.debug("Wait until hrv application will be loaded...");
        $("#appframe").shouldBe(visible, Duration.ofMillis(Configuration.pageLoadTimeout));
    }

    public SelenideElement errorMessageElm() {
        return $(".alert-error");
    }

    private boolean hasUserLoggedIn(@NonNull String username) {
        KeycloakUser keycloakUser = getUserAPIService().getUserIfExistsBy(username);
        String expectedUser = keycloakUser.getFirstName() + " " + keycloakUser.getLastName();
        log.debug("Check if we login as '" + expectedUser + "' user.");
        String loggedInUser = $("#userToggle a span").getText();
        return expectedUser.equals(loggedInUser);
    }

    public SelenideElement getUsernameLabelElm(){
        return getUsernameInputElm().$x("./parent::div/label");
    }

    public SelenideElement getPasswordLabelElm(){
        return getPasswordInputElm().$x("./parent::div/label");
    }

    public SelenideElement getLoginButtonElm() {
        return $("#kc-login");
    }

    public SelenideElement getPublisherHubLogoElm(){
        return $("#kc-header");
    }

    public LoginPage openLoginPage() {
        log.info("Open login page and navigate to Hrv.");
        open("/hrv");
        return getLoginPage();
    }

    private SelenideElement getUsernameInputElm() {
        return $("#username");
    }

    private SelenideElement getPasswordInputElm() {
        return $("#password");
    }

    private LoginPage setUserName(@NotNull String name) {
        getUsernameInputElm().val(name);
        return this;
    }

    private LoginPage setPassword(@NotNull String password) {
        $("#password").val(password);
        return this;
    }

    private void clickLoginBtn() {
        getLoginButtonElm().click();
    }

    private void cleanCookiesAndCloseDriver() {
        log.info("Clean cookies, local storage and close webdriver.");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        closeWebDriver();
    }
}