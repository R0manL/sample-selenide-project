package com.ccc.hv.qa.ui.migrated;

import com.codeborne.selenide.Condition;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.data.PredBUs;
import com.ccc.hv.qa.ui.data.Users;
import com.ccc.hv.qa.ui.enums.UserRole;
import com.ccc.hv.qa.ui.pages.EditUserPage;
import com.ccc.hv.qa.ui.pages.UserAddPage;
import com.ccc.hv.qa.ui.pages.UserManagePage;
import com.ccc.hv.qa.ui.pages.UserViewPage;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import com.ccc.hv.qa.ui.pojos.EditUser;
import com.ccc.hv.qa.ui.pojos.User;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.ccc.hv.qa.ui.data.BUs.*;
import static com.ccc.hv.qa.ui.data.EditUsers.*;
import static com.ccc.hv.qa.ui.data.PredChannels.BU;
import static com.ccc.hv.qa.ui.data.PredUsers.*;
import static com.ccc.hv.qa.ui.data.Users.*;
import static com.ccc.hv.qa.ui.enums.InputFieldErrorMsg.*;
import static com.ccc.hv.qa.ui.enums.UserRole.*;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.NavigationBar.getNavigationBar;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static com.ccc.hv.qa.ui.services.UserTestService.getUserTestService;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("User")
@Story("As logged in user I can create, edit, search a new user.")
@Test(groups = {"ui", "regression", "user"})
public class UserTests extends UITestBase {
    private static final User[] HV_VIEW_MNG_USERS = {HV_VIEW_MNG_ROLE_TEST, TENANT_USR_ROLE_HV_VIEW_MNG_TEST};
    private static final User[] METADATA_ADMIN_USERS = {METADATA_ADMIN_ROLE_TEST, ACCOUNT_MNG_ROLE_TEST, TENANT_USR_ROLE_METADATA_ADMIN_TEST};


    @DataProvider(name = "usersWithDifferentRoles")
    private Object[][] usersWithDifferentRoles() {
        return new Object[][]{
                {HV_VIEW_MNG},
                {ACC_ADMIN},
                {METADATA_ADMIN}};
    }

    @TmsLink("AUT-658")
    @Test(dataProvider = "usersWithDifferentRoles")
    public void verifyUserCreationEmptyRequiredFieldsValidation(User user) {
        getLoginPage().loginAs(user);

        UserAddPage userAddPage = getTopMenu()
                .clickAddUser()
                .clickSave();

        userAddPage.getFirstNameValidation().shouldHave(exactText(NON_BLANK.toString()));
        userAddPage.getLastNameValidation().shouldHave(exactText(NON_BLANK.toString()));
        userAddPage.getEmailValidation().shouldHave(exactText(NON_BLANK.toString()));
        userAddPage.getPhoneValidation().shouldHave(exactText(NON_BLANK.toString()));
    }

    @TmsLink("AUT-658")
    @Test(dataProvider = "usersWithDifferentRoles")
    public void verifyUserCreationNotSelectedRoleValidation(User user) {
        getLoginPage().loginAs(user);
        User validationUser = Users.validationUser;

        UserAddPage userAddPage = getTopMenu()
                .clickAddUser()
                .setFirstAndLastName(validationUser)
                .setUserPhone(validationUser)
                .setUserEmail(validationUser)
                .clickSave();

        userAddPage.getRoleValidation()
                .shouldHave(exactText(UNKNOWN_ROLE.toString()));

        userAddPage.selectUserRole(validationUser)
                .clickSave()
                .getBUValidation()
                .shouldHave(exactText(BUSINESS_UNIT_REQUIRED.toString()));
    }

    @TmsLink("AUT-658")
    @Test(dataProvider = "usersWithDifferentRoles")
    public void verifyUserCreationAlreadyInUseEmailValidation(User user) {
        getLoginPage().loginAs(user);
        User validationUser = Users.validationUser;

        getTopMenu()
                .clickAddUser()
                .setFirstAndLastName(validationUser)
                .setUserPhone(validationUser)
                .setUserEmail(user)
                .clickSave()
                .getEmailValidation()
                .shouldHave(exactText(USER_ALREADY_EXIST.toString()));
    }

    @TmsLink("AUT-658")
    @Test(dataProvider = "usersWithDifferentRoles")
    public void verifyUserCreationNotWellFormattedEmailValidation(User user) {
        getLoginPage().loginAs(user);
        User validationUser = notWellFormattedEmailValidationUser;

        getTopMenu()
                .clickAddUser()
                .setFirstAndLastName(validationUser)
                .setUserPhone(validationUser)
                .setUserEmail(validationUser)
                .clickSave()
                .getEmailValidation()
                .shouldHave(exactText(NOT_A_WELL_FORMATTED_EMAIL_ADDRESS.toString()));
    }

    @TmsLink("AUT-658")
    @Test(dataProvider = "usersWithDifferentRoles")
    public void verifyCancelUserCreation(User user) {
        getLoginPage().loginAs(user);

        getTopMenu()
                .clickAddUser()
                .clickCancel()
                .getPageTitleElm()
                .shouldHave(text(UserManagePage.MANAGE_USERS));
    }

    @DataProvider(name = "usersCreationWithDifferentRoles")
    private Object[][] usersCreationWithDifferentRoles() {
        return new Object[][]{
                {HV_VIEW_MNG, HV_VIEW_MNG_USERS},
                {METADATA_ADMIN, METADATA_ADMIN_USERS}};
    }

    @TmsLink("AUT-657")
    @Test(dataProvider = "usersCreationWithDifferentRoles")
    public void verifyUserCanCreateUsersWithDifferentRoles(User user, User[] usersForCreation) {
        for (User userForCreation : usersForCreation) {
            getLoginPage().loginAs(user);
            getUserTestService().createUserViaUI(userForCreation);
        }
    }


    @DataProvider(name = "usersHasAccessToManageUsersPage")
    private Object[][] usersHasAccessToManageUsersPage() {
        return new Object[][]{
                {SUPER_ADMIN},
                {HV_VIEW_MNG},
                {ACC_ADMIN},
                {METADATA_ADMIN},
                {ACCOUNT_MNG}};
    }

    @TmsLink("AUT-660")
    @Test(dataProvider = "usersHasAccessToManageUsersPage")
    public void verifyUserCanOpenManageUsersPage(User user) {
        getLoginPage().loginAs(user);

        getTopMenu().getUsersDropdownLnk().shouldBe(visible);
    }

    @TmsLink("AUT-660")
    @Test
    public void verifyTenantUserCanNotOpenManageUsersPage() {
        getLoginPage().loginAs(TENANT_USR);

        getTopMenu().getUsersDropdownLnk().shouldNotBe(visible);
    }

    @TmsLink("AUT-660")
    @Test(dataProvider = "usersHasAccessToManageUsersPage")
    public void verifyManageUsersPage(User user) {
        getLoginPage().loginAs(user);

        UserManagePage mngUserPage = getTopMenu().clickManageUsers();

        mngUserPage.getFirstNameTableHeaderElm().shouldBe(visible);
        mngUserPage.getLastNameTableHeaderElm().shouldBe(visible);
        mngUserPage.getBusinessUnitTableHeaderElm().shouldBe(visible);
        mngUserPage.getRoleTableHeaderElm().shouldBe(visible);
        mngUserPage.getStatusTableHeaderElm().shouldBe(visible);

        mngUserPage.sortLastNameColumn(true);
        List<String> lastNamesSortedByAsc = mngUserPage.getListOfLastNames();
        assertThat(lastNamesSortedByAsc)
                .as("Users (last names) are not sorted well (ascending).")
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);

        mngUserPage.sortLastNameColumn(false);
        List<String> lastNamesSortedByDesc = mngUserPage.getListOfLastNames();
        assertThat(lastNamesSortedByDesc)
                .as("Users (last names) are not sorted well (descending).")
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER.reversed());
    }

    @DataProvider(name = "usersEditWithDifferentRoles")
    private Object[][] usersEditWithDifferentRoles() {
        return new Object[][]{
                {ACC_ADMIN, ACCOUNT_ADMIN_USR_ROLE_UPDATE_TEST, ACC_ADMIN_USR_ROLE_UPDATE_TO_TENANT_USER_TEST,
                        Arrays.asList(ACCOUNT_ADMINISTRATOR, HARVEST_VIEW_MANAGER, METADATA_ADMINISTRATOR, ACCOUNT_MANAGER, TENANT_USER)},
                {HV_VIEW_MNG, HV_VIEW_MNG_ROLE_UPDATE_TEST, TENANT_USR_ROLE_UPDATE_TO_HV_VIEW_MNG_TEST,
                        Arrays.asList(HARVEST_VIEW_MANAGER, TENANT_USER)},
                {METADATA_ADMIN, METADATA_ADMIN_ROLE_UPDATE_TEST, METADATA_ADMIN_ROLE_UPDATE_TO_ACCOUNT_MANAGER_TEST,
                        Arrays.asList(METADATA_ADMINISTRATOR, ACCOUNT_MANAGER, TENANT_USER)},
                {ACCOUNT_MNG, ACCOUNT_MNG_ROLE_UPDATE_TEST, ACC_MANAGER_USR_ROLE_UPDATE_TO_TENANT_USER_TEST,
                        Arrays.asList(ACCOUNT_MANAGER, TENANT_USER)}};
    }

    @TmsLink("AUT-657")
    @Test(dataProvider = "usersEditWithDifferentRoles")
    public void verifyUserCanEditUsersWithDifferentRoles(User user, User userForCreation, EditUser userForUpdate, List<UserRole> userRoleList) {
        final List<String> EXPECT_BU_LIST = userForUpdate.getHasAccessToBusinessUnits()
                .stream()
                .map(BusinessUnit::getName)
                .collect(Collectors.toList());

        final List<String> USER_ROLE_LIST = userRoleList.stream().map(UserRole::getText).collect(Collectors.toList());

        getLoginPage().loginAs(user);
        getUserTestService().createUserViaUI(userForCreation);

        getLoginPage().loginAs(user);
        EditUserPage editUserPage = getTopMenu().clickManageUsers()
                .clickAtUserWith(userForCreation.getLastName())
                .editUser();
        editUserPage.getUserRoles().shouldHave(exactTexts(USER_ROLE_LIST));
        UserViewPage viewPage = editUserPage.updateUserWithValidation(userForUpdate)
                .viewUpdatedUser();

        viewPage.getRoleValue().shouldHave(exactText(userForUpdate.getRole().getText()));
        viewPage.getBUValueElms().shouldHave(exactTexts(EXPECT_BU_LIST));
        viewPage.getFirstNameValueElm().shouldHave(exactText(userForUpdate.getFirstName()));
        viewPage.getLastNameValueElm().shouldHave(exactText(userForUpdate.getLastName()));
        viewPage.getEmailValueElm().shouldHave(exactText(userForUpdate.getEmail()));
        viewPage.getPhoneNumberValueElm().shouldHave(exactText(userForUpdate.getPhoneNumber()));
    }

    @TmsLink("AUT-761")
    public void verifyEditSuperAdminUserRoles() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getUserTestService().createUserViaUI(SUPER_ADMIN_UPDATE);
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenu().clickManageUsers()
                .clickAtUserWith(SUPER_ADMIN_UPDATE.getLastName())
                .editUser()
                .getUserRoles()
                .shouldHave(exactTexts(SUPER_ADMINISTRATOR.getText()));
    }

    @DataProvider(name = "usersEditWithBU")
    private Object[][] usersEditWithBU() {
        return new Object[][]{
                {buForHrvViewManagerTest, HV_VIEW_MNG_WITH_MULTIPLE_BUs, HV_VIEW_MNG_WITH_SINGLE_BU, HV_VIEW_MNG_BU_UPDATE},
                {buForHrvAccountManagerTest, ACCOUNT_MNG_WITH_MULTIPLE_BUs, ACCOUNT_MNG_WITH_SINGLE_BU, ACC_MNG_BU_UPDATE},
                {buForHrvTenantUserTest, ACCOUNT_MNG_FOR_TENANT_USER_WITH_MULTIPLE_BUs, TENANT_USR_WITH_SINGLE_BU, TNT_USER_BU_UPDATE}};
    }

    @TmsLink("AUT-762")
    @Test(dataProvider = "usersEditWithBU")
    public void verifyBUSelectionAccountManager(BusinessUnit BU2, User userWithMultipleBUs,
                                                User userWithSingleBU, EditUser editUser) {
        final BusinessUnit BU1 = PredBUs.PRED_AUTOMATION_BU;

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();
        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(BU2)
                .getBUCreatedSuccessMessageElm().shouldBe(Condition.visible);

        //create HV manager with 2 BUs
        userWithMultipleBUs.setHasAccessToBusinessUnits(Arrays.asList(BU1, BU2));
        getUserTestService().createUserViaUI(userWithMultipleBUs);

        //create HV manager with 1st BU
        getLoginPage().loginAs(userWithMultipleBUs);

        getTopMenu()
                .getListOfBusinessUnits()
                .shouldHave(exactTexts(Arrays.asList(BU1.getName(), BU2.getName())));

        userWithSingleBU.setHasAccessToBusinessUnits(Collections.singletonList(BU2));
        getUserTestService().createUserViaUI(userWithSingleBU);
        getLoginPage().loginAs(userWithSingleBU);
        getTopMenu()
                .getListOfBusinessUnits()
                .shouldHave(exactTexts(Collections.singletonList(BU2.getName())));
        getNavigationBar().clickLogoutLink();

        //update HV manager to have 2nd BU
        getLoginPage().loginAs(userWithMultipleBUs);
        getTopMenu().clickManageUsers()
                .clickAtUserWith(userWithSingleBU.getLastName())
                .editUser()
                .updateUserWithValidation(editUser);

        getLoginPage().loginAs(userWithSingleBU);
        getTopMenu()
                .getListOfBusinessUnits()
                .shouldHave(exactTexts(Collections.singletonList(BU.getName())));
    }
}
