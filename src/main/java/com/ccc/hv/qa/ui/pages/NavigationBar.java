package com.ccc.hv.qa.ui.pages;

import com.ccc.hv.qa.logging.AllureLogger;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;

/**
 * Created by R0manL on 11/16/20.
 */

public class NavigationBar {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private NavigationBar() {
        // None
    }

    public static NavigationBar getNavigationBar() {
        log.debug("Switch to parent iframe.");
        switchTo().parentFrame();
        return new NavigationBar();
    }

    /**
     * Logout from the application.
     * Admin has menu (we must click 'users' to see logout link), users with business unit - not (just logout link).
     * Check if we have menu first, then logout.
     * @return login page.
     */
    public LoginPage clickLogoutLink() {
        log.info("Initiate logout.");
        expandUserDropdown();
        $(".drop-menu-item .pp-btn").click();

        return getLoginPage();
    }

    public LoginPage logout() {
        log.info("Logout via URL.");
        open("/logout");

        return getLoginPage();
    }

    private NavigationBar expandUserDropdown() {
        log.debug("Click at user dropdown.");
        $("#userToggle").click();
        return this;
    }
}
