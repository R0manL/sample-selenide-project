package com.ccc.hv.qa.ui.migrated;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.ccc.hv.qa.db.pojo.AddressDB;
import com.ccc.hv.qa.db.pojo.TenantDB;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.data.BUs;
import com.ccc.hv.qa.ui.data.PredAccounts;
import com.ccc.hv.qa.ui.data.PredBUs;
import com.ccc.hv.qa.ui.data.PredUsers;
import com.ccc.hv.qa.ui.pages.BusinessUnitAddPage;
import com.ccc.hv.qa.ui.pages.BusinessUnitEditPage;
import com.ccc.hv.qa.ui.pages.BusinessUnitViewPage;
import com.ccc.hv.qa.ui.pages.BusinessUnitsManagePage;
import com.ccc.hv.qa.ui.pojos.Account;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import com.ccc.hv.qa.ui.pojos.NormalizationRule;
import com.ccc.hv.qa.ui.pojos.User;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.CollectionCondition.textsInAnyOrder;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.ccc.hv.qa.api.services.AccountAPIService.getAccountAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.AccountDBService.getAccountPhysicalAddress;
import static com.ccc.hv.qa.db.services.TenantDBService.*;
import static com.ccc.hv.qa.ui.data.Accounts.*;
import static com.ccc.hv.qa.ui.data.BUs.*;
import static com.ccc.hv.qa.ui.data.CreateTagEntities.TAG_ENTITY_FROM_BU;
import static com.ccc.hv.qa.ui.data.NormalizationRules.REGRESSION_NORMA_RULE_WITH_COMMENT;
import static com.ccc.hv.qa.ui.data.PredUsers.SUPER_ADMIN;
import static com.ccc.hv.qa.ui.data.PredUsers.*;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.NavigationBar.getNavigationBar;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Created by R0manL on 7/19/21.
 */

@Epic("Business Unit")
@Story("As logged in user I can create a new business unit under different roles and can cancel creating new business unit")
@Test(groups = {"ui", "regression", "bu"})
public class BUTests extends UITestBase {
    private static final String EMPTY_FIELD_VALIDATION_ERROR_MSG = "must not be blank";


    @DataProvider(name = "buCreatorsTestData")
    private Object[][] buCreatorsTestData() {
        return new Object[][]{
                {PredUsers.SUPER_ADMIN, BUs.testBU1},
                {PredUsers.ACC_ADMIN, BUs.testBU2},
                {PredUsers.METADATA_ADMIN, BUs.testBU3}};
    }

    @Test(dataProvider = "buCreatorsTestData")
    public void verifyUserCanCreateNewBusinessUnit(User user, BusinessUnit bu) {
        getLoginPage().loginAs(user);

        if (user.equals(PredUsers.SUPER_ADMIN)) {
            getTopMenuTestService().selectDefaultAccount();
        }

        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(bu)
                .getBUCreatedSuccessMessageElm().shouldBe(visible);

        getNavigationBar()
                .clickLogoutLink();
    }

    @DataProvider(name = "buCreatorsTestData2")
    private Object[][] buCreatorsTestData2() {
        return new Object[][]{
                {PredUsers.SUPER_ADMIN, BUs.regressionBUWithParentAccAddr1},
                {PredUsers.ACC_ADMIN, BUs.regressionBUWithParentAccAddr2},
                {PredUsers.METADATA_ADMIN, BUs.regressionBUWithParentAccAddr3}};
    }

    @TmsLink("AUT-451")
    @Test(dataProvider = "buCreatorsTestData2")
    public void verifySuperAdminCanCreateBUWithParentAccountAddress(User user, BusinessUnit bu) {
        final Account PRED_ACCOUNT = PredAccounts.AQA_ACCOUNT;

        getLoginPage().loginAs(user);

        if (user.equals(PredUsers.SUPER_ADMIN)) {
            getTopMenuTestService().selectDefaultAccount();
        }

        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(bu)
                .getBUCreatedSuccessMessageElm().shouldBe(visible);

        AddressDB expectedAddress = getAccountPhysicalAddress(PRED_ACCOUNT.getName());
        AddressDB actualAddress = getTenantPhysicalAddressBy(bu.getName());

        assertThat(actualAddress)
                .usingRecursiveComparison().ignoringFields("hvAddmasAddressId", "createDateUtc")
                .isEqualTo(expectedAddress);
    }

    @TmsLink("AUT-451")
    @Test
    public void verifySuperAdminCanCancelBUCreationFromProfileTab() {
        getLoginPage().loginAs(PredUsers.SUPER_ADMIN);

        getTopMenuTestService().selectDefaultAccount();

        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .setBusinessUnitProfileInformation(regressionCancelBUCreation)
                .clickCancel()
                .getPageTitleElm().shouldBe(visible);
    }

    @TmsLink("AUT-451")
    @Test
    public void verifySuperAdminCanCancelBUCreationFromBillingInfoTab() {
        final BusinessUnit PRED_BU = regressionCancelBUCreation;

        getLoginPage().loginAs(PredUsers.SUPER_ADMIN);

        getTopMenuTestService().selectDefaultAccount();

        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .setBusinessUnitProfileInformation(PRED_BU)
                .clickContinueBtn()
                .setBusinessUnitBillingContactInformation(PRED_BU)
                .clickCancel()
                .getPageTitleElm().shouldBe(visible);
    }

    @TmsLink("AUT-451")
    @Test
    public void verifyNoPublisherIdPINAndThresholdFieldsIfWatermarkingIsDisabled() {
        getLoginPage().loginAs(PredUsers.SUPER_ADMIN);

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT_WITH_WATERMARK_DISABLED);

        getAccountAPIService().activateAccount(ACCOUNT_WITH_WATERMARK_DISABLED.getName());

        BusinessUnitAddPage businessUnitAddPage = getTopMenu()
                .selectAccountWithJSBy(ACCOUNT_WITH_WATERMARK_DISABLED.getName())
                .clickAddBusinessUnitWithJS();

        businessUnitAddPage.getPublisherIdElm().shouldNotBe(visible);
        businessUnitAddPage.getPublisherPINElm().shouldNotBe(visible);
        businessUnitAddPage.getImageThresholdElm().shouldNotBe(visible);
    }

    @TmsLink("AUT-463")
    @Test
    public void verifyBUCreationWithEmptyBillingContactInfo() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        BusinessUnitAddPage page = getTopMenu()
                .clickAddBusinessUnitWithJS()
                .setBusinessUnitProfileInformation(buWithEmptyBillingContactInfo)
                .clickContinueBtn()
                .setBusinessUnitBillingContactInformation(buWithEmptyBillingContactInfo);

        page.clickSaveBtn();

        page.getBillingFirstNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingLastNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingTitleErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingPhoneErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingEmailErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingAddress1ErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingCityErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingStateErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingZipErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingCountryErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
    }

    @TmsLink("AUT-464")
    @Test
    public void verifyBUCreationWithDuplicateName() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        BusinessUnitAddPage page = getTopMenu()
                .clickAddBusinessUnitWithJS()
                .setBusinessUnitProfileInformation(buNameValidation)
                .clickContinueBtn();

        page.getBuNameErrorElm().should(text("Business Unit already exists"));
    }

    @TmsLink("AUT-465")
    @Test
    public void verifyBUCreationWithDuplicateAlphaCode() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        BusinessUnitAddPage page = getTopMenu()
                .clickAddBusinessUnitWithJS()
                .setBusinessUnitProfileInformation(buDuplicateAlphaCodeValidation)
                .clickContinueBtn();

        final String DUPLICATE_ALPHACODE_ERROR_MSG = "Please select a different value, this code is already in use";
        page.getAlphaCodeErrorElm().should(text(DUPLICATE_ALPHACODE_ERROR_MSG));

        page.setBusinessUnitProfileInformation(buInvalidAlphaCodeValidation)
                .clickContinueBtn();

        final String TOO_SHORT_ALPHACODE_ERROR_MSG = "Use between 3 and 50 uppercase alpha characters. No spaces or special characters are allowed";
        page.getAlphaCodeErrorElm().should(text(TOO_SHORT_ALPHACODE_ERROR_MSG));
    }

    @TmsLink("AUT-466")
    @Test
    public void verifyBUCreationWithEmptyProfileInfo() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        BusinessUnitAddPage page = getTopMenu()
                .clickAddBusinessUnitWithJS()
                .setBusinessUnitProfileInformation(allEmptyFieldsBU)
                .clickContinueBtn();

        page.getBuNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getRecordSourceErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getAlphaCodeErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalFirstNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalLastNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalContactTitleErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalPhoneErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalEmailErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalAddr1ErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalCityErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalStateErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalZipErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalCountryErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getUserIdErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPasswordErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
    }

    @TmsLink("AUT-467")
    @Test
    public void verifyUserCanEditBUAndSavePubIdAndPubPinEmpty() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        final String BU_NAME = buWithFulfilledPubPINAndPubID.getName();

        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(buWithFulfilledPubPINAndPubID)
                .getBUCreatedSuccessMessageElm().shouldBe(visible);

        TenantDB createdBU = getTenantBy(BU_NAME);

        assertThat(createdBU.getTenmasDigimarcPublisherPin())
                .isEqualTo(buWithFulfilledPubPINAndPubID.getPublisherPin());
        assertThat(createdBU.getTenmasDigimarcPublisherId())
                .isEqualTo(buWithFulfilledPubPINAndPubID.getPublisherId());

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .selectBusinessUnitBy(BU_NAME)
                .clickEdit()
                .setPublisherPin("")
                .setPublisherID("")
                .clickContinueBtn()
                .clickSaveBtn()
                .getBUUpdatedSuccessMessageElm().shouldBe(visible);

        TenantDB editedBU = getTenantBy(BU_NAME);

        assertThat(editedBU.getTenmasDigimarcPublisherPin()).isNull();
        assertThat(editedBU.getTenmasDigimarcPublisherId()).isNull();
    }

    @TmsLink("AUT-467")
    @Test
    public void verifyUserCanEditBUAndSavePubIdAndPubPinFulfilled() {
        final String EDITED_PUBLISHER_PIN = "12345678";
        final String EDITED_PUBLISHER_ID = "87654321";
        final String EDITED_IMAGE_THRESHOLD = "40";
        final String BU_NAME = buWithEmptyPubPINAndPubID.getName();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(buWithEmptyPubPINAndPubID)
                .getBUCreatedSuccessMessageElm().shouldBe(visible);

        TenantDB createdBU = getTenantBy(BU_NAME);

        assertThat(createdBU.getTenmasDigimarcPublisherPin()).isNull();
        assertThat(createdBU.getTenmasDigimarcPublisherId()).isNull();
        assertThat(createdBU.getTenmasDigimarcImageThreshold())
                .isEqualTo(Integer.valueOf(buWithEmptyPubPINAndPubID.getImageThreshold()));

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .selectBusinessUnitBy(BU_NAME)
                .clickEdit()
                .setPublisherPin(EDITED_PUBLISHER_PIN)
                .setPublisherID(EDITED_PUBLISHER_ID)
                .setImageThreshold(EDITED_IMAGE_THRESHOLD)
                .clickContinueBtn()
                .clickSaveBtn()
                .getBUUpdatedSuccessMessageElm().shouldBe(visible);

        TenantDB editedBU = getTenantBy(BU_NAME);

        assertThat(editedBU.getTenmasDigimarcPublisherPin())
                .isEqualTo(EDITED_PUBLISHER_PIN);
        assertThat(editedBU.getTenmasDigimarcPublisherId())
                .isEqualTo(EDITED_PUBLISHER_ID);
        assertThat(editedBU.getTenmasDigimarcImageThreshold())
                .isEqualTo(Integer.valueOf(EDITED_IMAGE_THRESHOLD));
    }

    @TmsLink("AUT-467")
    @Test
    public void verifyNoPublisherIdPINAndThresholdFieldsIfWatermarkingIsDisabledBUEditMode() {
        getLoginPage().loginAs(PredUsers.SUPER_ADMIN);

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT_WITH_WATERMARK_DISABLED_BU_EDIT_TEST);

        getAccountAPIService()
                .activateAccount(ACCOUNT_WITH_WATERMARK_DISABLED_BU_EDIT_TEST.getName());

        getTopMenu()
                .selectAccountWithJSBy(ACCOUNT_WITH_WATERMARK_DISABLED_BU_EDIT_TEST.getName())
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(buWithEmptyPubPINAndPubIDWatermarkDisabled)
                .getBUCreatedSuccessMessageElm().shouldBe(visible);

        BusinessUnitEditPage editBUPage = getTopMenu()
                .clickManageBusinessUnitWithJS()
                .selectBusinessUnitBy(buWithEmptyPubPINAndPubIDWatermarkDisabled.getName())
                .clickEdit();

        editBUPage.getContinueBtn().shouldBe(visible);

        editBUPage.getPublisherIdElm().shouldNotBe(visible);
        editBUPage.getPublisherPINElm().shouldNotBe(visible);
        editBUPage.getImageThresholdElm().shouldNotBe(visible);
    }


    @TmsLink("AUT-473")
    @Test
    public void verifyBUEditWithEmptyProfileInfo() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(editWithEmptyProfileInfo)
                .getBUCreatedSuccessMessageElm().should(visible);

        BusinessUnitEditPage page = getTopMenu()
                .clickManageBusinessUnitWithJS()
                .selectBusinessUnitBy(editWithEmptyProfileInfo.getName())
                .clickEdit()
                .updateBusinessUnitProfileInformation(allEmptyFieldsBU)
                .clickContinueBtn();

        page.getBuNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getRecordSourceErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getAlphaCodeErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalFirstNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalLastNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalContactTitleErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalPhoneErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalEmailErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalAddr1ErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalCityErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalStateErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalZipErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPhysicalCountryErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getUserIdErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getPasswordErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
    }

    @TmsLink("AUT-473")
    @Test
    public void verifyBUEditWithEmptyBillingInfo() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(buSimpleEmptyBillingInfo)
                .getBUCreatedSuccessMessageElm().should(visible);

        BusinessUnitEditPage page = getTopMenu()
                .clickManageBusinessUnitWithJS()
                .selectBusinessUnitBy(buSimpleEmptyBillingInfo.getName())
                .clickEdit()
                .clickContinueBtn().updateBusinessUnitBillingContactInformationWith(allEmptyFieldsBU);

        page.clickSaveBtn();

        page.getBillingFirstNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingLastNameErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingTitleErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingPhoneErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingEmailErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingAddress1ErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingCityErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingStateErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingZipErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
        page.getBillingCountryErrorElm().should(text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
    }

    @TmsLink("AUT-474")
    @Test
    public void verifyBUCancelEditingOnProfileTab() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(buCancelEditingOnProfileTab)
                .getBUCreatedSuccessMessageElm().should(visible);

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .selectBusinessUnitBy(buCancelEditingOnProfileTab.getName())
                .clickEdit()
                .clickCancel()
                .getPageTitleElm().shouldBe(visible);
    }

    @TmsLink("AUT-474")
    @Test
    public void verifyBUCancelEditingOnBillingInfoTab() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(buCancelEditingOnBillingInfoTab)
                .getBUCreatedSuccessMessageElm().should(visible);

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .selectBusinessUnitBy(buCancelEditingOnBillingInfoTab.getName())
                .clickEdit()
                .clickContinueBtn()
                .clickCancel()
                .getPageTitleElm().shouldBe(visible);
    }

    @TmsLink("AUT-475")
    @Test
    public void verifyBUSearchByPartOfFirstWordInBUName() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        String predBuName = ENV_CONFIG.testBusinessUnitName();
        String predBuThreeCharactersPrefix = predBuName.substring(0, 3);
        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .filterBusinessUnitBy(predBuThreeCharactersPrefix)
                .getListOfBusinessUnitNameLinkElms().shouldHave(CollectionCondition.itemWithText(predBuName));
    }

    @TmsLink("AUT-475")
    @Test
    public void verifyBUSearchByFirstWordInBUName() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenu().selectAccountBy(ENV_CONFIG.accountName2());

        String predBuName = ENV_CONFIG.testBusinessUnitName2();
        String buNameWordsSpliterator = " ";
        Optional<String> firstWordInBuName = Arrays.stream(predBuName.split(buNameWordsSpliterator)).findFirst();
        assertThat(firstWordInBuName).as("Bu name is empty").isPresent();

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .filterBusinessUnitBy(firstWordInBuName.get())
                .getListOfBusinessUnitNameLinkElms()
                .shouldHave(CollectionCondition.itemWithText(predBuName));
    }

    @TmsLink("AUT-475")
    @Test
    public void verifyBUSearchBySecondWordInBUName() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenu().selectAccountBy(ENV_CONFIG.accountName2());

        String predBuName = ENV_CONFIG.testBusinessUnitName2();
        String buNameWordsSpliterator = " ";
        String buSecondWordInName = Arrays.asList(predBuName.split(buNameWordsSpliterator)).get(1);

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .filterBusinessUnitBy(buSecondWordInName)
                .getListOfBusinessUnitNameLinkElms()
                .shouldHave(CollectionCondition.itemWithText(predBuName));
    }

    @TmsLink("AUT-475")
    @Test
    public void verifyBUSearchNoResultsWithNotExistedBUName() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        String notExistedBuName = "!" + ENV_CONFIG.testBusinessUnitName();
        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .filterBusinessUnitBy(notExistedBuName)
                .getNoMatchingRecordsMsgElm().shouldBe(visible);
    }

    @TmsLink("AUT-556")
    @Test(groups = {"normalization"})
    public void verifyBuEditNormalizationRuleWithComment() {
        final NormalizationRule NORMA_RULE_WITH_COMMENT = REGRESSION_NORMA_RULE_WITH_COMMENT;

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(buWithNormaRuleAndComment);

        await().untilAsserted(() -> {
            TenantDB tenantDB = getTenantBy(buWithNormaRuleAndComment.getName());

            assertThat(tenantDB.getNormProfileOnix21()).as("Invalid normalization rule.").isNull();
            assertThat(tenantDB.getNormProfileOnix21Desc()).as("Invalid normalization comment.").isNull();
        });

        //Edit BU with normalization rule and comment.
        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(buWithNormaRuleAndComment.getName())
                .setRuleAndComment(NORMA_RULE_WITH_COMMENT);

        await().untilAsserted(() -> {
            TenantDB tenantDB = getTenantBy(buWithNormaRuleAndComment.getName());

            assertThat(tenantDB.getNormProfileOnix21()).as("Invalid normalization rule.").isEqualTo(NORMA_RULE_WITH_COMMENT.getRule());
            assertThat(tenantDB.getNormProfileOnix21Desc()).as("Invalid normalization coment.").isEqualTo(NORMA_RULE_WITH_COMMENT.getComment());
        });
    }

    @TmsLink("AUT-557")
    @Test
    public void verifyReturningToManageBUPageFromBuViewPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        String buName = PredBUs.PRED_AUTOMATION_BU.getName();
        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .selectBusinessUnitBy(buName)
                .clickReturnToAllBusinessUnits()
                .getPageTitleElm().shouldBe(visible);
    }

    @TmsLink("AUT-557")
    @Test
    public void verifyNavigationToChannelAssociationPageFromToManageBUPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        String buName = PredBUs.PRED_AUTOMATION_BU.getName();
        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .openChannelAssociationPageFor(buName)
                .getPageTitleElm().shouldBe(visible);
    }

    @TmsLink("AUT-557")
    @Test
    public void verifySortingOnBUManageBUPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

       BusinessUnitsManagePage buManagePage = getTopMenu()
                .clickManageBusinessUnitWithJS();

       buManagePage.clickSortBusinessUnitsByName(true);
       List<String> actualAscendSortedBuNames = buManagePage.getListOfBusinessUnitNames();
       assertThat(actualAscendSortedBuNames)
               .as("Business units are not sorted well (ascending).")
               .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);

        buManagePage.clickSortBusinessUnitsByName(false);
        List<String> actualDescendSortedBuNames = buManagePage.getListOfBusinessUnitNames();
        assertThat(actualDescendSortedBuNames)
                .as("Business units are not sorted well (descending).")
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER.reversed());
    }

    @TmsLink("AUT-559")
    @Test
    public void verifyNoPublisherIdPINAndThresholdFieldsIfWatermarkingIsDisabledBUViewMode() {
        getLoginPage().loginAs(PredUsers.SUPER_ADMIN);

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT_WITH_WATERMARK_DISABLED_BU_VIEW_TEST);

        getAccountAPIService()
                .activateAccount(ACCOUNT_WITH_WATERMARK_DISABLED_BU_VIEW_TEST.getName());

        getTopMenu()
                .selectAccountWithJSBy(ACCOUNT_WITH_WATERMARK_DISABLED_BU_VIEW_TEST.getName())
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(buWithEmptyPubPINAndPubIDWatermarkDisabledViewMode)
                .getBUCreatedSuccessMessageElm().shouldBe(visible);

        BusinessUnitViewPage businessUnitViewPage = getTopMenu()
                .clickManageBusinessUnitWithJS()
                .selectBusinessUnitBy(buWithEmptyPubPINAndPubIDWatermarkDisabledViewMode.getName());

        businessUnitViewPage.getBUNameElm().shouldBe(visible);
        businessUnitViewPage.getImageThresholdFieldElm().shouldNotBe(visible);
        businessUnitViewPage.getPublisherIdFieldElm().shouldNotBe(visible);
        businessUnitViewPage.getPublisherPinElm().shouldNotBe(visible);
    }

    @TmsLink("AUT-560")
    @Test
    public void verifyAccountAdminCanChangeActiveBU() {
        final String BU_NAME = simpleBUTest.getName();

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(simpleBUTest)
                .getBUCreatedSuccessMessageElm().should(visible);

        getLoginPage().loginAs(ACC_ADMIN);

        TenantDB tenantDB = getTenantBy(BU_NAME);
        assertThat(tenantDB.isActiveYn())
                .as("Invalid BU state.")
                .isTrue();

        BusinessUnitsManagePage businessUnitsManagePage = getTopMenu()
                .clickManageBusinessUnitWithJS()
                .changeStatus(false, BU_NAME);

        final long MAX_TIMEOUT = ENV_CONFIG.awaitilityTimeout();
        await().atMost(MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    TenantDB inactiveTenantDB = getTenantBy(BU_NAME);

                    assertThat(inactiveTenantDB.isActiveYn())
                            .as("Invalid BU state.")
                            .isFalse();
                });

        businessUnitsManagePage.changeStatus(true, BU_NAME);

        await().atMost(MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    TenantDB activeTenantDB = getTenantBy(BU_NAME);

                    assertThat(activeTenantDB.isActiveYn())
                            .as("Invalid BU state.")
                            .isTrue();
                });
    }

    @TmsLink("AUT-560")
    @Test
    public void verifyAccountAdminCanSeeAllBUs() {
        getLoginPage().loginAs(ACC_ADMIN);

        BusinessUnitsManagePage buManagePage = getTopMenu().clickManageBusinessUnitWithJS();
        buManagePage.getPageTitleElm().shouldBe(visible);

        List<String> expectedBuNamesDB = getListOfTenantNamesFor(PredAccounts.AQA_ACCOUNT.getName());
        buManagePage.getListOfBusinessUnitNameElms().shouldHave(textsInAnyOrder(expectedBuNamesDB));
    }

    @TmsLink("AUT-558")
    @Test
    public void verifyUserCanOpenManageTagsFromManageBUsPage() {
        final String BU_NAME = createTagFromManageBUsPageTest.getName();

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(createTagFromManageBUsPageTest)
                .getBUCreatedSuccessMessageElm().should(visible);

        getLoginPage().loginAs(ACC_ADMIN);

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .clickCreateTagFor(BU_NAME)
                .createProductTag(TAG_ENTITY_FROM_BU)
                .getUploadedTagName(TAG_ENTITY_FROM_BU.getTagName())
                .shouldBe(Condition.visible);
    }
}
