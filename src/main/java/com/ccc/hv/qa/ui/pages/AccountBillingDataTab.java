package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Selenide;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.AccountAddPage.getAccountAddPage;

/**
 * Created by R0manL on 31/08/20.
 */

public class AccountBillingDataTab {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    public AccountBillingDataTab setFirstName(@NotNull String firstName) {
        log.info("Set billing first name: '" + firstName + "'.");
        $("#account-billing-first-name").val(firstName);

        return this;
    }

    public AccountBillingDataTab setLastName(@NotNull String lastName) {
        log.info("Set billing last name: '" + lastName + "'.");
        $("#account-billing-last-name").val(lastName);

        return this;
    }

    public AccountBillingDataTab setContactTitle(@NotNull String title) {
        log.info("Set billing title: '" + title + "'.");
        $("#account-billing-title").val(title);

        return this;
    }

    public AccountBillingDataTab setPhoneNumber(@NotNull String phone) {
        log.info("Set billing phone: '" + phone + "'.");
        $("#account-billing-phone").val(phone);

        return this;
    }

    public AccountBillingDataTab setEmail(@NotNull String email) {
        log.info("Set billing email: '" + email + "'.");
        $("#account-billing-email").val(email);

        return this;
    }

    public AccountBillingDataTab setAddress1(@NotNull String address) {
        log.info("Set billing address: '" + address + "'.");
        $("#account-billing-address1").val(address);

        return this;
    }

    public AccountBillingDataTab setAddress2(@NotNull String address) {
        log.info("Set billing address: '" + address + "'.");
        $("#account-billing-address2").val(address);

        return this;
    }

    public AccountBillingDataTab setCity(@NotNull String city) {
        log.info("Set billing city: '" + city + "'.");
        $("#account-billing-city").val(city);

        return this;
    }

    public AccountBillingDataTab setState(@NotNull String state) {
        log.info("Set billing state: '" + state + "'.");
        $("#account-billing-state").val(state);

        return this;
    }

    public AccountBillingDataTab setPostalCode(@NotNull String zip) {
        log.info("Set billing zip: '" + zip + "'.");
        $("#account-billing-zip").val(zip);

        return this;
    }

    public AccountBillingDataTab setCountry(@NotNull String country) {
        log.info("Set billing country: '" + country + "'.");
        $("#account-billing-country").val(country);

        return this;
    }

    public AccountAddPage clickSave() {
        log.info("Click save.");
        // User is navigated to the middle of the Billing Address page of Edit Account mode
        // and click have no time to be performed
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);

        $x("//button[normalize-space(text())='Save']")
                .scrollTo()
                .click();

        return getAccountAddPage();
    }
}
