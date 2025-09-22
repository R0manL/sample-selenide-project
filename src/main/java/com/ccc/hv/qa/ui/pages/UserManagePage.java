package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.ui.pages.UserViewPage.getUserViewPage;

public class UserManagePage {
    public static final String MANAGE_USERS = "Manage Users";
    public static final SelenideElement MNG_USERS_TABLE = $x(".//table[@id='users-table']");

    private UserManagePage() {
        // None
    }

    public static UserManagePage getUserManagePage() {
        return new UserManagePage();
    }

    public SelenideElement getPageTitleElm() {
        return $("#page-title");
    }

    public SelenideElement getLastNameTableHeaderElm() {
        return MNG_USERS_TABLE.$x(".//th[contains(@aria-label,'Last Name')]");
    }

    public SelenideElement getFirstNameTableHeaderElm() {
        return MNG_USERS_TABLE.$x(".//th[contains(@aria-label,'First Name')]");
    }

    public SelenideElement getRoleTableHeaderElm() {
        return MNG_USERS_TABLE.$x(".//th[contains(@aria-label,'Role')]");
    }

    public SelenideElement getBusinessUnitTableHeaderElm() {
        return MNG_USERS_TABLE.$x(".//th[contains(@aria-label,'Business Unit')]");
    }

    public SelenideElement getStatusTableHeaderElm() {
        return MNG_USERS_TABLE.$x(".//th[contains(@aria-label,'Status')]");
    }

    public UserManagePage sortLastNameColumn(boolean ascending) {
        if (ascending ^ isColumnSortByAsc(getLastNameTableHeaderElm())) {
            getLastNameTableHeaderElm().click();
        }

        return this;
    }

    public List<String> getListOfLastNames() {
        return MNG_USERS_TABLE.$$(".users-row a").texts();
    }

    public UserViewPage clickAtUserWith(@NotNull String userLastName) {
        $x(".//a[normalize-space(text())='" + userLastName + "']").click();
        return getUserViewPage();
    }

    private boolean isColumnSortByAsc(SelenideElement tableHeaderElm) {
        Objects.requireNonNull(tableHeaderElm.attr("class"), "Can't table's column sorting state.");
        return tableHeaderElm.has(Condition.attribute("class", "sorting_asc"));
    }
}
