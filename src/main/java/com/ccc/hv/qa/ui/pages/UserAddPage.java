package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import com.ccc.hv.qa.ui.pojos.User;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.ui.enums.UserRole.*;
import static com.ccc.hv.qa.ui.pages.PageBase.activePageContainer;
import static com.ccc.hv.qa.ui.pages.UserManagePage.getUserManagePage;

/**
 * Created by R0manL on 17/08/20.
 */

public class UserAddPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private UserAddPage() {
        // None
    }

    public static UserAddPage getUserAddPage() {
        return new UserAddPage();
    }

    public UserAddPage createUserWithValidation(User user) {
        createUser(user);
        userAddedMessage().shouldBe(visible);
        log.info("User has been saved.");
        return this;
    }

    public UserAddPage createUser(User user) {
        log.info("Creating user: '" + user.getEmail() + "'.");

        selectUserRole(user)
                .selectAccount(user)
                .setFirstAndLastName(user)
                .setUserEmail(user)
                .setUserPhone(user)
                .clickSave();

        return this;
    }

    public UserAddPage selectUserRole(@NotNull User user) {
        log.info("Select role='" + user.getRole().getOptionValue() + "'");
        activePageContainer.$("#roleSelector")
                .selectOptionByValue(user.getRole().getOptionValue());

        return this;
    }

    public UserAddPage selectAccount(@NotNull User user) {
        if (user.getAccountName() != null) {
            log.info("Check if user has assigned correct account. NOTE: account must be selected before user creation.");
            $("#user-account-wrapper")
                    .$x(".//*[normalize-space(text())='" + user.getAccountName() + "']")
                    .shouldBe(visible);
        }

        if (HARVEST_VIEW_MANAGER.equals(user.getRole()) || ACCOUNT_MANAGER.equals(user.getRole()) || TENANT_USER.equals(user.getRole())) {
            for (BusinessUnit bu : user.getHasAccessToBusinessUnits()) {
                log.info("Grant access to '" + bu.getName() + "' business unit.");
                $("#user-business-unit-options").selectOption(bu.getName());
                $("#user-business-unit-selector").click();
            }
        }

        return this;
    }

    public UserAddPage setFirstAndLastName(@NotNull User user) {
        $("#user-first-name").val(user.getFirstName());
        $("#user-last-name").val(user.getLastName());

        return this;
    }

    public UserAddPage setUserEmail(@NotNull User user) {
        final int USERNAME_MAX_LENGTH = 48;
        String userName = user.getEmail();
        if (userName.length() >= USERNAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Username max length (" + USERNAME_MAX_LENGTH + ") exceeded. " +
                    "Current = '" + userName.length() + "' characters.");
        } else {
            $("#user-email").val(userName);
        }

        return this;
    }

    public UserAddPage setUserPhone(@NotNull User user) {
        $("#user-phone").val(user.getPhoneNumber());

        return this;
    }


    public SelenideElement userAddedMessage() {
        return activePageContainer.$x(".//h1[@id='page-title' and text()='User Added']/..//*[@id='firstNameField']");
    }

    public UserAddPage clickSave() {
        log.info("Click save user.");
        activePageContainer.$("#page-user-add").$x(".//*[normalize-space(text())='Save']").click();

        return this;
    }

    public UserManagePage clickCancel() {
        log.info("Click cancel.");
        activePageContainer.$("#page-user-add").$x(".//*[normalize-space(text())='Cancel']").click();

        return getUserManagePage();
    }

    public SelenideElement getRoleValidation() {
        return $("#user-role-help");
    }

    public SelenideElement getBUValidation() {
        return $("#user-business-unit-help");
    }

    public SelenideElement getLastNameValidation() {
        return $("#user-last-name-help");
    }

    public SelenideElement getFirstNameValidation() {
        return $("#user-first-name-help");
    }

    public SelenideElement getEmailValidation() {
        return $("#user-email-help");
    }

    public SelenideElement getPhoneValidation() {
        return $("#user-phone-help");
    }
}
