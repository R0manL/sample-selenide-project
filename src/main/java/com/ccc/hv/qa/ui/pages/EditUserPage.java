package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import com.ccc.hv.qa.ui.pojos.EditUser;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.ui.enums.UserRole.*;
import static com.ccc.hv.qa.ui.pages.PageBase.activePageContainer;
import static com.ccc.hv.qa.ui.pages.UserManagePage.getUserManagePage;
import static com.ccc.hv.qa.ui.pages.UserViewPage.getUserViewPage;

public class EditUserPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private EditUserPage() {
        // None
    }

    public static EditUserPage getUserEditPage() {
        return new EditUserPage();
    }

    public EditUserPage updateUserWithValidation(@NotNull EditUser user) {
        updateUser(user);
        userUpdatedMessage(user.getFirstName()).shouldBe(visible);
        log.info("User has been saved.");
        return this;
    }

    public EditUserPage updateUser(@NotNull EditUser user) {
        log.info("Update user: '" + user.getEmail() + "'.");
        if (user.getRole() != null) selectUserRole(user);
        if (user.getHasAccessToBusinessUnits() != null) selectBU(user);
        if (user.getFirstName() != null) setFirstName(user);
        if (user.getLastName() != null) setLastName(user);
        if (user.getEmail() != null) setUserEmail(user);
        if (user.getPhoneNumber() != null) setUserPhone(user);
        clickSave();

        return this;
    }

    public EditUserPage selectUserRole(@NotNull EditUser user) {
        log.info("Select role='" + user.getRole().getOptionValue() + "'");
        activePageContainer.$("#roleSelector")
                .selectOptionByValue(user.getRole().getOptionValue());

        return this;
    }

    public EditUserPage setFirstName(@NotNull EditUser user) {
        $("#user-first-name").clear();
        $("#user-first-name").val(user.getFirstName());
        return this;
    }

    public EditUserPage setLastName(@NotNull EditUser user) {
        $("#user-last-name").clear();
        $("#user-last-name").val(user.getLastName());
        return this;
    }

    public EditUserPage setUserEmail(@NotNull EditUser user) {
        final int USERNAME_MAX_LENGTH = 48;
        String userName = user.getEmail();
        if (userName.length() >= USERNAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Username max length (" + USERNAME_MAX_LENGTH + ") exceeded. " +
                    "Current = '" + userName.length() + "' characters.");
        } else {
            $("#user-email").clear();
            $("#user-email").val(userName);
        }

        return this;
    }

    public EditUserPage selectBU(@NotNull EditUser user) {
        if (HARVEST_VIEW_MANAGER.equals(user.getRole()) || ACCOUNT_MANAGER.equals(user.getRole()) || TENANT_USER.equals(user.getRole())) {
            for (BusinessUnit bu : user.getHasAccessToBusinessUnits()) {
                log.info("Remove all selected business units.");
                ElementsCollection selectedBUs = $("select#user-business-unit-selection").$$("option").filter(not(empty));
                selectedBUs.forEach(selectedBU -> {
                    selectedBU.click();
                    $("button#user-business-unit-deselector").click();
                });
                log.info("Grant access to '" + bu.getName() + "' business unit.");
                $("#user-business-unit-options").selectOption(bu.getName());
                $("#user-business-unit-selector").click();
            }
        }

        return this;
    }

    public EditUserPage setUserPhone(@NotNull EditUser user) {
        $("#user-phone").clear();
        $("#user-phone").val(user.getPhoneNumber());

        return this;
    }


    public SelenideElement userUpdatedMessage(@NotNull String firstName) {
        return activePageContainer.$x(".//div[@id='messageSpan']/b[contains(text(),'" + firstName + "')]");
    }

    public EditUserPage clickSave() {
        log.info("Click save user.");
        activePageContainer.$x(".//*[normalize-space(text())='Save']").click();

        return this;
    }

    public UserManagePage clickCancel() {
        log.info("Click cancel.");
        activePageContainer.$x(".//*[normalize-space(text())='Cancel']").click();

        return getUserManagePage();
    }

    public UserViewPage viewUpdatedUser() {
        log.info("Click View User.");
        activePageContainer.$("a#view-user-link").click();

        return getUserViewPage();
    }

    public ElementsCollection getUserRoles() {
        log.info("Get available user roles list");
        return activePageContainer.$("#roleSelector").$$("option");
    }
}
