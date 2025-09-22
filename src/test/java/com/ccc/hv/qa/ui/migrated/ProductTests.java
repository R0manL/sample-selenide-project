package com.ccc.hv.qa.ui.migrated;

import com.ccc.hv.qa.db.pojo.*;
import com.ccc.hv.qa.ui.pages.ProductAddPage;
import com.ccc.hv.qa.ui.pages.ProductDetailsPage;
import com.ccc.hv.qa.ui.pages.SearchProductResultsPage;
import com.ccc.hv.qa.ui.pages.TopMenu;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.ui.services.*;
import com.codeborne.selenide.*;
import com.ccc.hv.qa.api.enums.OnixVersion;
import com.ccc.hv.qa.core.CustomCollectors;
import com.ccc.hv.qa.db.enums.DistributionValidationMessage;
import com.ccc.hv.qa.db.pojo.*;
import com.ccc.hv.qa.db.services.BatchDBService;
import com.ccc.hv.qa.file.pojo.OnixProduct;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.data.PredBUs;
import com.ccc.hv.qa.ui.data.PredUsers;
import com.ccc.hv.qa.ui.enums.AssetType;
import com.ccc.hv.qa.ui.enums.ContentType;
import com.ccc.hv.qa.ui.enums.MetadataActivityPhase;
import com.ccc.hv.qa.ui.pages.*;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.ui.services.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.ccc.hv.qa.api.services.ProductAPIService.getProductAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.enums.AssetDistributionStatus.DISTRIB_SUCCESS;
import static com.ccc.hv.qa.db.enums.AssetDistributionStatus.PACKAGE_FAIL;
import static com.ccc.hv.qa.db.enums.AssetProcessingError.XPATH_EXPRESSION_INVALID_TAG;
import static com.ccc.hv.qa.db.services.BatchDBService.*;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAServerIDBy;
import static com.ccc.hv.qa.db.services.ProductDBService.*;
import static com.ccc.hv.qa.db.services.TenantDBService.*;
import static com.ccc.hv.qa.db.services.WorkflowDBService.getWorkflowAssetStatusDetailFor;
import static com.ccc.hv.qa.db.services.WorkflowDBService.getWorkflowProductStatusDetailsFor;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds2;
import static com.ccc.hv.qa.ui.data.BUs.secondBUForAQAAccount;
import static com.ccc.hv.qa.ui.data.BatchODDEntities.*;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.*;
import static com.ccc.hv.qa.ui.data.Channels.*;
import static com.ccc.hv.qa.ui.data.CreateTagEntities.TAG_ENTITY_FOR_PRODUCT_CARD_FILTERING;
import static com.ccc.hv.qa.ui.data.MetadataOptimizations.REGRESSION_OPT_21_FAILED_PATH_SUBSTITUTE_INVALID_SORT_ORDER_FOR_ASSET;
import static com.ccc.hv.qa.ui.data.PredUsers.*;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.FileTestService.getFileTestService;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.testng.Assert.assertTrue;

/**
 * Created by R0manL on 7/19/21.
 */

@Epic("Product")
@Story("As logged in user I can create, search, delete a new product.")
@Test(groups = {"ui", "regression", "product"})
public class ProductTests extends UITestBase {
    private static final String EXPECTED_ONIX_TAG_VALUE = "Lakeside Book Company";


    @DataProvider(name = "usersForProductDeletion")
    private Object[][] usersForProductDeletion() {
        return new Object[][]{
                {PredUsers.SUPER_ADMIN},
                {PredUsers.ACC_ADMIN},
                {PredUsers.METADATA_ADMIN}};
    }

    @Test(dataProvider = "usersForProductDeletion")
    public void verifyUsersCanDeleteProductOnSearchResultsPage(User user) {
        getLoginPage().loginAs(user);

        OnixTestService onix = new OnixTestService("regression/verifyProductDeletionOnSearchResultsPage/9781419728762.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        if (user.equals(SUPER_ADMIN)) {
            getTopMenuTestService().selectDefaultAccount();
        }
        getTopMenuTestService().selectDefaultBU();

        final String PRODUCT_TITLE = onix.getSingleProductTitle();
        getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .activateBulkSelection()
                .selectProduct()
                .clickDelete()
                .confirmProductDeletion();

        assertTrue(hasProductMarkedForDeletion(PRODUCT_TITLE), "Product '" + PRODUCT_TITLE + "' must be marked for deletion.");

        getTopMenu()
                .searchDeletedProductBy(PRODUCT_TITLE)
                .productWithTitle()
                .shouldNotBe(visible);
    }

    @Test
    public void verifyTenantUserCanNotDeleteProduct() {
        getLoginPage().loginAs(TENANT_USR);
        OnixTestService onix = new OnixTestService("regression/verifyTenantUserCanNotDeleteProduct/9781419728763.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenuTestService().selectDefaultBU();

        final String PRODUCT_TITLE = onix.getSingleProductTitle();
        SearchProductResultsPage page = getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE);

        page.getPageTitleElm().shouldHave(text(PRODUCT_TITLE));
        page.getDeleteProductButton().shouldNotBe(visible);
    }

    @TmsLink("AUT-636")
    @Test
    public void verifyNavigationToHomePageFromProductDetailsPage() {
        OnixTestService onix = new OnixTestService("regression/verifyNavigationToHomePageFromProductDetailsPage/9780824909412.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .navigateToHomePage()
                .getHomePageMarketplaceMonitoringSectionElm()
                .shouldBe(visible, Duration.ofMillis(Configuration.pageLoadTimeout));
    }

    @TmsLink("AUT-636")
    @Test
    public void verifyUploadedContentIsDisplayedInProductDetailsPage() {
        final int EXPECTED_NUMBER_OF_ASSETS = 2;
        final int EXPECTED_NUMBER_OF_CONTENTS = 1;

        getLoginPage().loginAs(SUPER_ADMIN);

        OnixTestService onix = new OnixTestService("regression/verifyUploadedContentIsDisplayedInProductDetailsPage/9781424556912.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyUploadedContentIsDisplayedInProductDetailsPage/assets/9781424556912.epub")
                .cloneFileWith(onix.getSingleProductRecordReference());

        ProductDetailsPage productDetailsPage = getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        productDetailsPage.addAssets().uploadProductsOrAssets(content.getFilePath());

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAllAssetElements().shouldHave(CollectionCondition.size(EXPECTED_NUMBER_OF_ASSETS))
                .filterBy(text(content.getFileName())).shouldHave(size(EXPECTED_NUMBER_OF_CONTENTS));
    }

    @TmsLink("AUT-636")
    @Test
    public void verifyProcessingErrorIsDisplayedUponAttachingInvalidContent() {
        final String ERROR = "Error";
        final String FILE_IS_NOT_ALLOWED_ERROR_MSG = "File type not allowed";
        final int EXPECTED_NUMBER_OF_ASSETS = 1;

        OnixTestService onix = new OnixTestService("regression/verifyProcessingErrorIsDisplayedUponAttachingInvalidContent/9781424556922.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyProcessingErrorIsDisplayedUponAttachingInvalidContent/assets/9781424556922.ep")
                .cloneFileWith(onix.getSingleProductRecordReference());

        getLoginPage().loginAs(SUPER_ADMIN);
        ProductAddPage addPage = getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .addAssets().uploadFile(content.getFilePath());

        addPage.getErrorStatusElm().shouldHave(text(ERROR));
        addPage.getErrorMsgElm().shouldHave(attribute("title", FILE_IS_NOT_ALLOWED_ERROR_MSG));

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAllAssetElements().shouldHave(CollectionCondition.size(EXPECTED_NUMBER_OF_ASSETS));
    }

    @DataProvider(name = "changingOnixValueOnix21")
    private Object[][] changingOnixValueOnix21() {
        return new Object[][]{
                {"regression/verifyChangingCompanyOnixElementValueToLakesideBookCompanyOnix21/9781424556955.xml",
                        regressionPublicChannelSFTPForChangingCompOnixElValOnix21Short,
                        regressionCASFTPForChangingCompOnixElValOnix21Short,
                },
                {"regression/verifyChangingCompanyOnixElementValueToLakesideBookCompanyOnix21/9780000001998.xml",
                        regressionPublicChannelSFTPForChangingCompOnixElValOnix21Long,
                        regressionCASFTPForChangingCompOnixElValOnix21Long
                }};
    }

    @TmsLink("AUT-695")
    @Test(dataProvider = "changingOnixValueOnix21")
    public void verifyChangingCompanyOnixElementValueToLakesideBookCompanyOnix21(@NotNull String metadataOnixFile,
                                                                                 ChannelPublic channel,
                                                                                 ChannelAssociation ca) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage();

        String downloadedOnixFilePath = productDetailsPage.downloadAssetBy(AssetType.ONIX21).getAbsolutePath();
        OnixTestService metadataOnix = new OnixTestService(downloadedOnixFilePath);
        assertThat(metadataOnix.getSingleProduct().getFromCompany()).isEqualTo(EXPECTED_ONIX_TAG_VALUE);

        productDetailsPage.selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        final String distrPathForFirstProduct = onix.toAsset().getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedOnixFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPathForFirstProduct), onix.getParentDir());
        OnixTestService onixTestService = new OnixTestService(downloadedOnixFile);
        assertThat(onixTestService.getSingleProduct().getFromCompany()).isEqualTo(EXPECTED_ONIX_TAG_VALUE);
    }

    @DataProvider(name = "changingOnixValueOnix30")
    private Object[][] changingOnixValueOnix30() {
        return new Object[][]{
                {"regression/verifyChangingCompanyOnixElementValueToLakesideBookCompanyOnix30/9781317616011.xml",
                        regressionPublicChannelSFTPForChangingCompOnixElValOnix30Short,
                        regressionCASFTPForChangingCompOnixElValOnix30Short,
                },
                {"regression/verifyChangingCompanyOnixElementValueToLakesideBookCompanyOnix30/9780807505844.xml",
                        regressionPublicChannelSFTPForChangingCompOnixElValOnix30Long,
                        regressionCASFTPForChangingCompOnixElValOnix30Long
                }};
    }

    @TmsLink("AUT-695")
    @Test(dataProvider = "changingOnixValueOnix30")
    public void verifyChangingCompanyOnixElementValueToLakesideBookCompanyOnix30(@NotNull String metadataOnixFile,
                                                                                 ChannelPublic channel,
                                                                                 ChannelAssociation ca) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage();

        String downloadedOnixFilePath = productDetailsPage.downloadAssetBy(AssetType.ONIX30).getAbsolutePath();
        OnixTestService metadataOnix = new OnixTestService(downloadedOnixFilePath);
        assertThat(metadataOnix.getSingleProduct().getFromCompany()).isEqualTo(EXPECTED_ONIX_TAG_VALUE);

        productDetailsPage.selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        final String distrPathForFirstProduct = onix.toAsset().getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedOnixFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPathForFirstProduct), onix.getParentDir());
        OnixTestService onixTestService = new OnixTestService(downloadedOnixFile);
        assertThat(onixTestService.getSingleProduct().getFromCompany()).isEqualTo(EXPECTED_ONIX_TAG_VALUE);
    }

    @DataProvider(name = "filterByPublisherSuccessPath")
    private Object[][] filterByPublisherSuccessPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByPublisherSuccessPath/9780000001023_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByPublisherSuccPath1,
                        regressionChannelAssociationSFTPForFilterByPublisherSuccPath1,
                        REG_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_PATH_1
                },
                {"regression/verifyProductFilteringByPublisherSuccessPath/9780000001030_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByPublisherSuccPath2,
                        regressionChannelAssociationSFTPForFilterByPublisherSuccPath2,
                        REG_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_PATH_2
                }};
    }

    @TmsLink("AUT-753")
    @Test(dataProvider = "filterByPublisherSuccessPath", groups = {"batchODD"})
    public void verifyProductFilteringByPublisherSuccessPath(@NotNull String metadataOnixFile,
                                                             ChannelPublic channel,
                                                             ChannelAssociation ca,
                                                             BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @DataProvider(name = "filterByPublisherFailPath")
    private Object[][] filterByPublisherFailPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByPublisherFailPath/9780000001048_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByPublisherFailPath1,
                        regressionChannelAssociationSFTPForFilterByPublisherFailPath1,
                        REG_BATCH_ODD_FILTER_BY_PUBLISHER_FAIL_PATH_1
                },
                {"regression/verifyProductFilteringByPublisherFailPath/9780000001054_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByPublisherFailPath2,
                        regressionChannelAssociationSFTPForFilterByPublisherFailPath2,
                        REG_BATCH_ODD_FILTER_BY_PUBLISHER_FAIL_PATH_2
                },
                {"regression/verifyProductFilteringByPublisherFailPath/9780000001061_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByPublisherFailPath3,
                        regressionChannelAssociationSFTPForFilterByPublisherFailPath1,
                        REG_BATCH_ODD_FILTER_BY_PUBLISHER_FAIL_PATH_3
                },
                {"regression/verifyProductFilteringByPublisherFailPath/9780000001078_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByPublisherFailPath4,
                        regressionChannelAssociationSFTPForFilterByPublisherFailPath2,
                        REG_BATCH_ODD_FILTER_BY_PUBLISHER_FAIL_PATH_4
                }};
    }

    @TmsLink("AUT-753")
    @Test(dataProvider = "filterByPublisherFailPath", groups = {"batchODD"})
    public void verifyProductFilteringByPublisherFailPath(@NotNull String metadataOnixFile,
                                                          ChannelPublic channel,
                                                          ChannelAssociation ca,
                                                          BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        await().untilAsserted(() -> {
            String actualValidationMessage = getBatchOddValidationMsgFor(channelAndCA.getChannelName());
            assertThat(actualValidationMessage)
                    .as("Distribution has not failed as expected.")
                    .isEqualTo(DistributionValidationMessage.FILTERED.getMessage());
        });
    }

    @TmsLink("AUT-21")
    @Test(groups = "singleODD")
    public void verifyODDAndDistributionStatusForOnix21WithNoRecordSourceName() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelSFTPForODD2,
                smokeChannelAssociationSFTPForODD)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyODDAndDistributionStatusForOnix21WithNoRecordSourceName/9781424555444.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAssetDistributionStatusElmFor(
                        channelAndCA.getChannelName(),
                        channelAndCA.getAssetTypeFromSingleMetadata(),
                        DISTRIB_SUCCESS)
                .shouldBe(visible);
    }

    @TmsLink("AUT-778")
    @Test
    public void verifyProductFlyoutNumberOfAddedProductsWithinPast24Hours() {
        final String defaultBUName = PredBUs.PRED_AUTOMATION_BU.getName();
        final int numOfProductsBeforeIngest = getProductAPIService()
                .getNumberOfRecentlyAddedProductsFor(defaultBUName, ContentType.EPUB);

        OnixTestService onix = new OnixTestService("regression/verifyProductFlyoutNumberOfAddedProductsWithinPast24Hours/9780000001085.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getProductAPIService().refreshProductRecentsInProductFlyoutSection();
        getProductAPIService().waitOnNumAddedProductsWithinPast24HoursIsGreaterThan(numOfProductsBeforeIngest, ContentType.EPUB, defaultBUName);

        getLoginPage().loginAs(SUPER_ADMIN);


        int numOfProductsAfterIngestOnUI = getTopMenuTestService()
                .selectDefaultAccount()
                .selectBusinessUnitWithJSBy(defaultBUName)
                .expandProductsDropdown()
                .getNumberOfAddedProductsWithinPast24HoursFor(ContentType.fromText(onix.getSingleProduct().getType()));

        assertThat(numOfProductsAfterIngestOnUI)
                .as("Number of added products must be +1 (on UI) after ingestion.")
                .isGreaterThan(numOfProductsBeforeIngest);
    }

    @TmsLink("AUT-778")
    @Test
    public void verifyProductFlyoutNumberOfUpdatedProductsWithinPast24Hours() {
        final String defaultBUName = PredBUs.PRED_AUTOMATION_BU.getName();
        final int numOfProductsBeforeIngest = getProductAPIService()
                .getNumberOfRecentlyUpdatedProductsFor(defaultBUName, ContentType.EPUB);

        OnixTestService onix = new OnixTestService("regression/verifyProductFlyoutNumberOfUpdatedProductsWithinPast24Hours/9780000001092.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getProductAPIService().refreshProductRecentsInProductFlyoutSection();
        getProductAPIService().waitOnNumOfUpdatedProductsWithinPast24HoursIsGreaterThan(numOfProductsBeforeIngest, ContentType.EPUB, defaultBUName);

        getLoginPage().loginAs(SUPER_ADMIN);
        int numOfProductsAfterIngestOnUI = getTopMenuTestService()
                .selectDefaultAccount()
                .selectBusinessUnitWithJSBy(defaultBUName)
                .expandProductsDropdown()
                .getNumberOfUpdatedProductsWithinPast24HoursFor(ContentType.fromText(onix.getSingleProduct().getType()));

        assertThat(numOfProductsAfterIngestOnUI)
                .as("Number of updated products must be +1 (on UI) after ingestion.")
                .isGreaterThan(numOfProductsBeforeIngest);
    }

    @TmsLink("AUT-778")
    @Test
    public void verifyProductFlyoutTotalNumberOfProductsWithinPast24Hours() {
        final String defaultBUName = PredBUs.PRED_AUTOMATION_BU.getName();
        final int numOfProductsBeforeIngest = getProductAPIService()
                .getNumberOfProductsInProductSnapshotFor(defaultBUName, ContentType.EPUB);

        final int numOfProductsWithAssetBeforeIngest = getProductAPIService()
                .getNumberOfProductsWithAssetInProductSnapshotFor(defaultBUName, ContentType.EPUB);

        OnixTestService onix = new OnixTestService("regression/verifyProductFlyoutTotalNumberOfProductsWithinPast24Hours/9780000001115.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onix2 = new OnixTestService("regression/verifyProductFlyoutTotalNumberOfProductsWithinPast24Hours/9780000001108.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds);

        new ContentTestService("regression/verifyProductFlyoutTotalNumberOfProductsWithinPast24Hours/9780000001108.epub")
                .cloneFileWith(onix2.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getProductAPIService().waitOnNumOfAddedProductsIsGreaterThan(
                numOfProductsBeforeIngest + 2,
                ContentType.EPUB,
                defaultBUName);

        getProductAPIService().waitOnNumOfAddedProductsWithAssetIsGreaterThan(
                numOfProductsWithAssetBeforeIngest + 1,
                ContentType.EPUB,
                defaultBUName);

        getLoginPage().loginAs(SUPER_ADMIN);

        TopMenu topMenu = getTopMenuTestService()
                .selectDefaultAccount()
                .selectBusinessUnitWithJSBy(defaultBUName)
                .expandProductsDropdown();

        int numOfProductsAfterIngestOnUI = topMenu
                .getTotalNumberOfIngestedProductsOf(ContentType.fromText(onix.getSingleProduct().getType()));

        assertThat(numOfProductsAfterIngestOnUI)
                .as("Number of products must be at least: +2 (on UI) after ingestion.")
                .isGreaterThan(numOfProductsBeforeIngest);

        int numOfProductsWithAssetAfterIngestOnUI = topMenu
                .getTotalNumberOfIngestedProductsWithAssetOf(ContentType.fromText(onix.getSingleProduct().getType()));

        assertThat(numOfProductsWithAssetAfterIngestOnUI)
                .as("Number of products with asset must be at least: +1 (on UI) after ingestion.")
                .isGreaterThan(numOfProductsWithAssetBeforeIngest);
    }

    @DataProvider(name = "filterByAudienceCodeSuccessPath")
    private Object[][] filterByAudienceCodeSuccessPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByAudienceCodeSuccessPath/9780000001028_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByAudienceCodeSuccPath1,
                        regressionChannelAssociationSFTPForFilterByAudienceCodeSuccPath1,
                        REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_PATH_1
                },
                {"regression/verifyProductFilteringByAudienceCodeSuccessPath/9780000001037_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByAudienceCodeSuccPath2,
                        regressionChannelAssociationSFTPForFilterByAudienceCodeSuccPath2,
                        REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_PATH_2
                }};
    }

    @TmsLink("AUT-757")
    @Test(dataProvider = "filterByAudienceCodeSuccessPath", groups = {"batchODD"})
    public void verifyProductFilteringByAudienceCode(@NotNull String metadataOnixFile,
                                                     ChannelPublic channel,
                                                     ChannelAssociation ca,
                                                     BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @DataProvider(name = "filterByAudienceCodeFailPath")
    private Object[][] filterByAudienceCodeFailPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByAudienceCodeFailPath/9780000001046_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByAudienceCodeFailPath1,
                        regressionChannelAssociationSFTPForFilterByAudienceCodeFailPath1,
                        REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_FAIL_PATH_1
                },
                {"regression/verifyProductFilteringByAudienceCodeFailPath/9780000001055_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByAudienceCodeFailPath2,
                        regressionChannelAssociationSFTPForFilterByAudienceCodeFailPath2,
                        REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_FAIL_PATH_2
                },
                {"regression/verifyProductFilteringByAudienceCodeFailPath/9780000001062_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByAudienceCodeFailPath3,
                        regressionChannelAssociationSFTPForFilterByAudienceCodeFailPath1,
                        REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_FAIL_PATH_3
                },
                {"regression/verifyProductFilteringByAudienceCodeFailPath/9780000001079_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByAudienceCodeFailPath4,
                        regressionChannelAssociationSFTPForFilterByAudienceCodeFailPath2,
                        REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_FAIL_PATH_4
                }};
    }

    @TmsLink("AUT-753")
    @Test(dataProvider = "filterByAudienceCodeFailPath", groups = {"batchODD"})
    public void verifyProductFilteringByAudienceCodeFailPath(@NotNull String metadataOnixFile,
                                                             ChannelPublic channel,
                                                             ChannelAssociation ca,
                                                             BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        await().untilAsserted(() -> {
            String actualValidationMessage = getBatchOddValidationMsgFor(channelAndCA.getChannelName());
            assertThat(actualValidationMessage)
                    .as("Distribution has not failed as expected.")
                    .isEqualTo(DistributionValidationMessage.FILTERED.getMessage());
        });
    }

    @DataProvider(name = "filterByTerritoryRightsSuccessPath")
    private Object[][] filterByTerritoryRightsSuccessPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByTerritoryRightsSuccessPath/9780000001025_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByTerritoryRightsSuccPath1,
                        regressionChannelAssociationSFTPForFilterByTerritoryRightsSuccPath1,
                        REG_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_PATH_1
                },
                {"regression/verifyProductFilteringByTerritoryRightsSuccessPath/9780000001031_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByTerritoryRightsSuccPath2,
                        regressionChannelAssociationSFTPForFilterByTerritoryRightsSuccPath2,
                        REG_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_PATH_2
                }};
    }

    @TmsLink("AUT-756")
    @Test(dataProvider = "filterByTerritoryRightsSuccessPath", groups = {"batchODD"})
    public void verifyProductFilteringByTerritoryRights(@NotNull String metadataOnixFile,
                                                        ChannelPublic channel,
                                                        ChannelAssociation ca,
                                                        BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @DataProvider(name = "filterByTerritoryRightsFailPath")
    private Object[][] filterByTerritoryRightsFailPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByTerritoryRightsFailPath/9780000001042_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByTerritoryRightsFailPath1,
                        regressionChannelAssociationSFTPForFilterByTerritoryRightsFailPath1,
                        REG_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_FAIL_PATH_1
                },
                {"regression/verifyProductFilteringByTerritoryRightsFailPath/9780000001033_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByTerritoryRightsFailPath2,
                        regressionChannelAssociationSFTPForFilterByTerritoryRightsFailPath2,
                        REG_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_FAIL_PATH_2
                }
        };
    }

    @TmsLink("AUT-756")
    @Test(dataProvider = "filterByTerritoryRightsFailPath", groups = {"batchODD"})
    public void verifyProductFilteringByTerritoryRightsFailPath(@NotNull String metadataOnixFile,
                                                                ChannelPublic channel,
                                                                ChannelAssociation ca,
                                                                BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        await().untilAsserted(() -> {
            String actualValidationMessage = getBatchOddValidationMsgFor(channelAndCA.getChannelName());
            assertThat(actualValidationMessage)
                    .as("Distribution has not failed as expected.")
                    .isEqualTo(DistributionValidationMessage.FILTERED.getMessage());
        });
    }

    @DataProvider(name = "filterByImprintSuccessPath")
    private Object[][] filterByImprintSuccessPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByImprint/9780000001025_impr_pass11_onix21.xml",
                        regressionPubChannelSFTPFilterByImprintSuccPath11,
                        regressionChannelAssociationSFTPForFilterByImprintSuccPath11,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_11
                },
                {"regression/verifyProductFilteringByImprint/9780000001026_impr_pass12_onix30.xml",
                        regressionPubChannelSFTPFilterByImprintSuccPath12,
                        regressionChannelAssociationSFTPForFilterByImprintSuccPath12,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_12
                },
                {"regression/verifyProductFilteringByImprint/9780000001029_impr_pass21_onix21.xml",
                        regressionPubChannelSFTPFilterByImprintSuccPath21,
                        regressionChannelAssociationSFTPForFilterByImprintSuccPath21,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_21
                },
                {"regression/verifyProductFilteringByImprint/9780000001031_impr_pass22_onix30.xml",
                        regressionPubChannelSFTPFilterByImprintSuccPath22,
                        regressionChannelAssociationSFTPForFilterByImprintSuccPath22,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_22
                },
                {"regression/verifyProductFilteringByImprint/9780000001032_impr_pass31_onix21.xml",
                        regressionPubChannelSFTPFilterByImprintSuccPath31,
                        regressionChannelAssociationSFTPForFilterByImprintSuccPath31,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_31
                },
                {"regression/verifyProductFilteringByImprint/9780000001033_impr_pass32_onix30.xml",
                        regressionPubChannelSFTPFilterByImprintSuccPath32,
                        regressionChannelAssociationSFTPForFilterByImprintSuccPath32,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_32
                }
        };
    }

    @Issue("HRV-29689")
    @TmsLink("AUT-754")
    @Test(dataProvider = "filterByImprintSuccessPath", groups = {"batchODD"})
    public void verifyProductFilteringByImprintSuccessPath(@NotNull String metadataOnixFile,
                                                           ChannelPublic channel,
                                                           ChannelAssociation ca,
                                                           BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @DataProvider(name = "filterByImprintFailPath")
    private Object[][] filterByImprintFailPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByImprint/9780000001034_noimpr_fail11_onix21.xml",
                        regressionPubChannelSFTPFilterByImprintFailPath11,
                        regressionChannelAssociationSFTPForFilterByImprintFailPath11,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_FAIL_PATH_11
                },
                {"regression/verifyProductFilteringByImprint/9780000001035_noimpr_fail12_onix30.xml",
                        regressionPubChannelSFTPFilterByImprintFailPath12,
                        regressionChannelAssociationSFTPForFilterByImprintFailPath12,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_FAIL_PATH_12
                },
                {"regression/verifyProductFilteringByImprint/9780000001036_impr_fail21_onix21.xml",
                        regressionPubChannelSFTPFilterByImprintFailPath21,
                        regressionChannelAssociationSFTPForFilterByImprintFailPath21,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_FAIL_PATH_21
                },
                {"regression/verifyProductFilteringByImprint/9780000001037_impr_fail22_onix30.xml",
                        regressionPubChannelSFTPFilterByImprintFailPath22,
                        regressionChannelAssociationSFTPForFilterByImprintFailPath22,
                        REG_BATCH_ODD_FILTER_BY_IMPRINT_FAIL_PATH_22
                }
        };
    }

    @TmsLink("AUT-754")
    @Test(dataProvider = "filterByImprintFailPath", groups = {"batchODD"})
    public void verifyProductFilteringByImprintFailPath(@NotNull String metadataOnixFile,
                                                        ChannelPublic channel,
                                                        ChannelAssociation ca,
                                                        BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        await().untilAsserted(() -> {
            String actualValidationMessage = getBatchOddValidationMsgFor(channelAndCA.getChannelName());
            assertThat(actualValidationMessage)
                    .as("Distribution has not failed as expected.")
                    .isEqualTo(DistributionValidationMessage.FILTERED.getMessage());
        });
    }

    @DataProvider(name = "filterBySupplyIdentifierSuccessPath")
    private Object[][] filterBySupplyIdentifierSuccessPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001038_supply_id_pass11_onix21.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath11,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath11,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_11
                },
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001039_supply_id_pass12_onix30.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath12,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath12,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_12
                },
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001040_supply_id_pass21_onix21.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath21,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath21,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_21
                },
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001041_supply_id_pass22_onix30.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath22,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath22,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_22
                },
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001042_supply_id_pass31_onix21.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath31,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath31,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_31
                },
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001043_supply_id_pass32_onix30.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath32,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath32,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_32
                }
        };
    }

    @TmsLink("AUT-755")
    @Test(dataProvider = "filterBySupplyIdentifierSuccessPath", groups = {"batchODD"})
    public void verifyProductFilteringBySupplyIdentifierSuccessPath(@NotNull String metadataOnixFile,
                                                                    ChannelPublic channel,
                                                                    ChannelAssociation ca,
                                                                    BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @DataProvider(name = "filterBySupplyIdentifierFailPath")
    private Object[][] filterBySupplyIdentifierFailPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001044_no_supply_id_fail11_onix21.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierFailPath11,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierFailPath11,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_FAIL_PATH_11
                },
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001045_no_supply_id_fail12_onix30.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierFailPath12,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierFailPath12,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_FAIL_PATH_12
                },
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001046_supply_id_fail21_onix21.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierFailPath21,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierFailPath21,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_FAIL_PATH_21
                },
                {"regression/verifyProductFilteringBySupplyIdentifier/9780000001047_supply_id_fail22_onix30.xml",
                        regressionPubChannelSFTPFilterBySupplyIdentifierFailPath22,
                        regressionChannelAssociationSFTPForFilterBySupplyIdentifierFailPath22,
                        REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_FAIL_PATH_22
                }
        };
    }

    @TmsLink("AUT-755")
    @Test(dataProvider = "filterBySupplyIdentifierFailPath", groups = {"batchODD"})
    public void verifyProductFilteringBySupplyIdentifierFailPath(@NotNull String metadataOnixFile,
                                                                 ChannelPublic channel,
                                                                 ChannelAssociation ca,
                                                                 BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        await().untilAsserted(() -> {
            String actualValidationMessage = getBatchOddValidationMsgFor(channelAndCA.getChannelName());
            assertThat(actualValidationMessage)
                    .as("Distribution has not failed as expected.")
                    .isEqualTo(DistributionValidationMessage.FILTERED.getMessage());
        });
    }

    @DataProvider(name = "filterByMultipleDistrRulesFailPath")
    private Object[][] filterByMultipleDistrRulesFailPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByMultipleDistrFailPath/9780000001088_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByMultipleDistrRulesFailPath1,
                        regressionChannelAssociationSFTPForFilterByMultipleDistrRulesFailPath1,
                        REG_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_RULE_FAIL_PATH_1
                },
                {"regression/verifyProductFilteringByMultipleDistrFailPath/9780000001089_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByMultipleDistrRulesFailPath2,
                        regressionChannelAssociationSFTPForFilterByMultipleDistrRulesFailPath2,
                        REG_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_RULE_FAIL_PATH_2
                }
        };
    }

    @TmsLink("AUT-756")
    @Test(dataProvider = "filterByMultipleDistrRulesFailPath", groups = {"batchODD"})
    public void verifyProductFilteringByMultipleDistrRulesFailPath(@NotNull String metadataOnixFile,
                                                                   ChannelPublic channel,
                                                                   ChannelAssociation ca,
                                                                   BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        await().untilAsserted(() -> {
            String actualValidationMessage = getBatchOddValidationMsgFor(channelAndCA.getChannelName());
            assertThat(actualValidationMessage)
                    .as("Distribution has not failed as expected.")
                    .isEqualTo(DistributionValidationMessage.FILTERED.getMessage());
        });
    }

    @DataProvider(name = "verifyProductFilteringByMultipleDistrFailRules")
    private Object[][] verifyProductFilteringByMultipleDistrFailRules() {
        return new Object[][]{
                {"regression/verifyProductFilteringByMultipleDistrRulesSuccessPath/9780000001066_pub_onix21.xml",
                        regressionPubChannelSFTPFilterByMultipleDistrRulesSuccPath1,
                        regressionChannelAssociationSFTPForFilterByMultipleDistrRulesSuccPath1,
                        REG_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_SUCCESS_PATH_1
                },
                {"regression/verifyProductFilteringByMultipleDistrRulesSuccessPath/9780000001067_pub_onix30.xml",
                        regressionPubChannelSFTPFilterByMultipleDistrRulesSuccPath2,
                        regressionChannelAssociationSFTPForFilterByMultipleDistrRulesSuccPath2,
                        REG_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_SUCCESS_PATH_2
                }};
    }

    @TmsLink("AUT-756")
    @Test(dataProvider = "verifyProductFilteringByMultipleDistrFailRules", groups = {"batchODD"})
    public void verifyProductFilteringByMultipleDistrRules(@NotNull String metadataOnixFile,
                                                           ChannelPublic channel,
                                                           ChannelAssociation ca,
                                                           BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @DataProvider(name = "filterByBISACSubjectsSuccessPath")
    private Object[][] filterByBISACSubjectsSuccessPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9782374950823_p11.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsSuccPath11,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath11,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_11
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9780807596492_p12.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsSuccPath12,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath12,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_12
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9782374950824_p21.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsSuccPath21,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath21,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_21
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9780807596493_p22.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsSuccPath22,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath22,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_22
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9782374950825_p31.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsSuccPath31,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath31,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_31
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9780807596494_p32.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsSuccPath32,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath32,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_32
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9782374950826_p41.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsSuccPath41,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath41,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_41
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9780807596495_p42.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsSuccPath42,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath42,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_42
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9782374950830_p51.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsPassPath51,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsPassPath51,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCC_PATH_51
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9780807596499_p52.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsPassPath52,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccessPath52,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCC_PATH_52
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9782374950831_p61.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsPassPath61,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccessPath61,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCC_PATH_61
                },
                {"regression/verifyProductFilteringByBISACSubjectsSuccessPath/9780807596500_p62.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsPassPath62,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccessPath62,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCC_PATH_62
                },
        };
    }

    @TmsLink("AUT-755")
    @Test(dataProvider = "filterByBISACSubjectsSuccessPath", groups = {"batchODD"})
    public void verifyProductFilteringByBISACSubjectsSuccessPath(@NotNull String metadataOnixFile,
                                                                 ChannelPublic channel,
                                                                 ChannelAssociation ca,
                                                                 BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @DataProvider(name = "filterByBISACSubjectsFailPath")
    private Object[][] filterByBISACSubjectsFailPath() {
        return new Object[][]{
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9782374950827_f11.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath11,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath11,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_11
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9780807596496_f12.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath12,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath12,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_12
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9782374950828_f21.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath21,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath21,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_21
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9780807596497_f22.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath22,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath22,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_22
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9782374950829_f31.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath31,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath31,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_31
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9780807596498_f32.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath32,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath32,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_32
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9782374950832_f61.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath61,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath61,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_61
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9780807596501_f62.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath62,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath62,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_62
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9782374950833_f71.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath71,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath71,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_71
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9780807596502_f72.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath72,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath72,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_72
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9782374950834_f81.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath81,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath81,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_81
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9780807596503_f82.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath82,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath82,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_82
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9782374950835_f91.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath91,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath91,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_91
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9780807596504_f92.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath92,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath92,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_92
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9782374950836_f101.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath101,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath101,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_101
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9780807596505_f102.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath102,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath102,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_102
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9782374950837_f111.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath111,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath111,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_111
                },
                {"regression/verifyProductFilteringByBISACSubjectsFailPath/9780807596506_f112.xml",
                        regressionPubChannelSFTPFilterByBISACSubjectsFailPath112,
                        regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath112,
                        REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_112
                }
        };
    }

    @TmsLink("AUT-755")
    @Test(dataProvider = "filterByBISACSubjectsFailPath", groups = {"batchODD"})
    public void verifyProductFilteringByBISACSubjectsFailPath(@NotNull String metadataOnixFile,
                                                              ChannelPublic channel,
                                                              ChannelAssociation ca,
                                                              BatchODDEntity batchODDEntity) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        await().untilAsserted(() -> {
            String actualValidationMessage = getBatchOddValidationMsgFor(channelAndCA.getChannelName());
            assertThat(actualValidationMessage)
                    .as("Distribution has not failed as expected.")
                    .isEqualTo(DistributionValidationMessage.FILTERED.getMessage());
        });
    }

    @TmsLink("AUT-764")
    @Test(groups = {"batchODD"})
    public void verifyProductCardFilteringWithContentAndCollateralAndMetadata() {
        final String onixFilePath = "regression/verifyProductCardFilteringWithContentAndCollateralAndMetadata/9780807505855.xml";
        final String contentFilePath = "regression/verifyProductCardFilteringWithContentAndCollateralAndMetadata/9780807505855.epub";
        final String collateralFilePath = "regression/verifyProductCardFilteringWithContentAndCollateralAndMetadata/9780807505855_marketingimage.jpg";
        final ChannelPublic channel = regressionPubChannelSFTPForProductCardFilteringSomeFiltersMatch;
        final ChannelAssociation ca = regressionChannelAssociationSFTPForProductCardFilteringSomeFiltersMatch;
        final BatchODDEntity batchODDEntity = REG_BATCH_ODD_CARD_FILTERING_WITH_CONTENT_COLLATERAL_META;

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(onixFilePath)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService testContent = new ContentTestService(contentFilePath)
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService testCollateral = new CollateralTestService(collateralFilePath)
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        MetadataTestService metadata = onix.toAsset();
        metadata.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        int expectedCAServerID = getCAServerIDBy(channel.getName());
        String expectedBU = ENV_CONFIG.alphaCodePrefix();
        int expectedCurrentVersion = 1;

        Arrays.asList(metadata.getFileName(), testContent.getFileName(), testCollateral.getFileName())
                .forEach(fileName -> {
                    WorkflowAssetStatusDetailsDB workflowDetails = getWorkflowAssetStatusDetailFor(fileName, channel.getName());

                    assertThat(workflowDetails.getHvCsadetChaServerAssocId())
                            .as("Invalid CA server ID.")
                            .isEqualTo(expectedCAServerID);

                    final int expectedTenantID = getTenantIDBy(ENV_CONFIG.testBusinessUnitName());
                    assertThat(workflowDetails.getHvTenmasTenantMasterId())
                            .as("Invalid BU id.")
                            .isEqualTo(expectedTenantID);

                    assertThat(workflowDetails.getWasdetCurrentVersion())
                            .as("Invalid 'current version'.")
                            .isEqualTo(expectedCurrentVersion);
                });

        //Distribute onix second time.
        OnixTestService updatedOnix = new OnixTestService(metadata.getFilePath())
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        WorkflowAssetStatusDetailsDB workflowDetails = getWorkflowAssetStatusDetailFor(metadata.getFileName(), channel.getName());
        final int expectedCurrentVersionAfterSecondDistribution = expectedCurrentVersion + 1;

        assertThat(workflowDetails.getWasdetCurrentVersion())
                .as("Invalid 'current version'.")
                .isEqualTo(expectedCurrentVersionAfterSecondDistribution);
    }

    @TmsLink("AUT-764")
    @Test(groups = {"batchODD"})
    public void verifyProductCardFilteringWithNoFiltersSetup() {
        final String onixFilePath = "regression/verifyProductCardFilteringWithNoFiltersSetup/9780807505856.xml";
        final ChannelPublic channel = regressionPubChannelSFTPForProductCardFilteringWithNoFiltersSetup;
        final ChannelAssociation ca = regressionChannelAssociationSFTPForProductCardFilteringWithNoFiltersSetup;
        final BatchODDEntity batchODDEntity = REG_BATCH_ODD_CARD_FILTERING_WITH_NO_FILTERS_SETUP;

        final String channelName = channel.getName();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(onixFilePath)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        MetadataTestService metadata = onix.toAsset();
        metadata.waitOnSuccessfulDistributionTo(channelName);

        WorkflowProductStatusDetailsDB workflowProductStatusDetails = getWorkflowProductStatusDetailsFor(onix.getSingleProductTitle(), channelName);

        final int expectedTenantID = getTenantIDBy(ENV_CONFIG.testBusinessUnitName());
        assertThat(workflowProductStatusDetails.getHvTenmasTenantMasterId())
                .as("Invalid BU id.")
                .isEqualTo(expectedTenantID);

        assertThat(workflowProductStatusDetails.getWpsdetFilterAllYn())
                .as("Invalid product filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterPubDateYn())
                .as("Invalid 'publication date' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterAudienceCodeYn())
                .as("Invalid 'audience code' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterBisacYn())
                .as("Invalid 'bisac' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterDiscountCodeYn())
                .as("Invalid 'discount code' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterImprintYn())
                .as("Invalid 'imprint' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterIsbnTagYn())
                .as("Invalid 'isbn tag' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterPublisherNameYn())
                .as("Invalid 'publisher name' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterSalesOutletYn())
                .as("Invalid 'sales outlet' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterSupplierIdentYn())
                .as("Invalid 'supplier identifier' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterTerritoryYn())
                .as("Invalid 'territory' filtering status.")
                .isNull();
    }

    @TmsLink("AUT-764")
    @Test(groups = {"batchODD"})
    public void verifyProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet() {
        final String onixFilePath = "regression/verifyProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet/9780807505857_NoSalesOutlet_Short3_0.xml";
        final ChannelPublic channel = regressionPubChannelSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet;
        final ChannelAssociation ca = regressionChannelAssociationSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet;
        final BatchODDEntity batchODDEntity = REG_BATCH_ODD_CARD_FILTERING_WITH_ALL_FILTERS_SETUP_AND_MATCH_NO_SALES_OUTLET;

        final String channelName = channel.getName();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .clickCreateTagFor(PredBUs.PRED_AUTOMATION_BU.getName())
                .createProductTag(TAG_ENTITY_FOR_PRODUCT_CARD_FILTERING)
                .getUploadedTagName(TAG_ENTITY_FOR_PRODUCT_CARD_FILTERING.getTagName())
                .shouldBe(Condition.visible);

        new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(onixFilePath)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        MetadataTestService metadata = onix.toAsset();
        metadata.waitOnSuccessfulDistributionTo(channelName);

        WorkflowProductStatusDetailsDB workflowProductStatusDetails = getWorkflowProductStatusDetailsFor(onix.getSingleProductTitle(), channelName);

        final int expectedTenantID = getTenantIDBy(ENV_CONFIG.testBusinessUnitName());
        assertThat(workflowProductStatusDetails.getHvTenmasTenantMasterId())
                .as("Invalid BU id.")
                .isEqualTo(expectedTenantID);

        assertThat(workflowProductStatusDetails.getWpsdetFilterAllYn())
                .as("Invalid product filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterAudienceCodeYn())
                .as("Invalid 'audience code' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterBisacYn())
                .as("Invalid 'bisac' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterDiscountCodeYn())
                .as("Invalid 'discount code' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterImprintYn())
                .as("Invalid 'imprint' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterIsbnTagYn())
                .as("Invalid 'isbn tag' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterPublisherNameYn())
                .as("Invalid 'publisher name' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterSalesOutletYn())
                .as("Invalid 'sales outlet' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterSupplierIdentYn())
                .as("Invalid 'supplier identifier' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterTerritoryYn())
                .as("Invalid 'territory' filtering status.")
                .isTrue();
    }

    @TmsLink("AUT-764")
    @Test(groups = {"batchODD"})
    public void verifyProductCardFilteringWithAllFiltersSetupSomeDoNotMatchWithoutSalesOutlet() {
        final String onixFilePath = "regression/verifyProductCardFilteringWithAllFiltersSetupSomeDoNotMatchWithoutSalesOutlet/9780807505858_NoSalesOutlet_Short3_0.xml";
        final ChannelPublic channel = regressionPubChannelSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet;
        final ChannelAssociation ca = regressionChannelAssociationSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet;
        final BatchODDEntity batchODDEntity = REG_BATCH_ODD_CARD_FILTERING_WITH_ALL_FILTERS_SETUP_AND_SOME_NOT_MATCH_NO_SALES_OUTLET;

        final String channelName = channel.getName();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .clickCreateTagFor(PredBUs.PRED_AUTOMATION_BU.getName())
                .createProductTag(TAG_ENTITY_FOR_PRODUCT_CARD_FILTERING)
                .getUploadedTagName(TAG_ENTITY_FOR_PRODUCT_CARD_FILTERING.getTagName())
                .shouldBe(Condition.visible);

        new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(onixFilePath)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        WorkflowProductStatusDetailsDB workflowProductStatusDetails = getWorkflowProductStatusDetailsFor(onix.getSingleProductTitle(), channelName);

        final int expectedTenantID = getTenantIDBy(ENV_CONFIG.testBusinessUnitName());
        assertThat(workflowProductStatusDetails.getHvTenmasTenantMasterId())
                .as("Invalid BU id.")
                .isEqualTo(expectedTenantID);

        assertThat(workflowProductStatusDetails.getWpsdetFilterAllYn())
                .as("Invalid product filtering status.")
                .isFalse();

        assertThat(workflowProductStatusDetails.getWpsdetFilterAudienceCodeYn())
                .as("Invalid 'audience code' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterBisacYn())
                .as("Invalid 'bisac' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterDiscountCodeYn())
                .as("Invalid 'discount code' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterImprintYn())
                .as("Invalid 'imprint' filtering status.")
                .isFalse();

        assertThat(workflowProductStatusDetails.getWpsdetFilterIsbnTagYn())
                .as("Invalid 'isbn tag' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterPublisherNameYn())
                .as("Invalid 'publisher name' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterSalesOutletYn())
                .as("Invalid 'sales outlet' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterSupplierIdentYn())
                .as("Invalid 'supplier identifier' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterTerritoryYn())
                .as("Invalid 'territory' filtering status.")
                .isFalse();
    }

    @TmsLink("AUT-798")
    @Test(groups = {"batchODD"})
    public void verifyProductCardFilteringWithSalesOutlet() {
        final String onixFilePath = "regression/verifyProductCardFilteringWithSalesOutlet/9780807505859_short.xml";
        final ChannelPublic channel = regressionPubChannelSFTPForProductCardFilteringWithSalesOutlet;
        final ChannelAssociation ca = regressionChannelAssociationSFTPForProductCardFilteringWithSalesOutlet;
        final BatchODDEntity batchODDEntity = REG_BATCH_ODD_CARD_FILTERING_WITH_SALES_OUTLET;

        final String channelName = channel.getName();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(onixFilePath)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(batchODDEntity)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        WorkflowProductStatusDetailsDB workflowProductStatusDetails = getWorkflowProductStatusDetailsFor(onix.getSingleProductTitle(), channelName);

        final int expectedTenantID = getTenantIDBy(ENV_CONFIG.testBusinessUnitName());
        assertThat(workflowProductStatusDetails.getHvTenmasTenantMasterId())
                .as("Invalid BU id.")
                .isEqualTo(expectedTenantID);

        assertThat(workflowProductStatusDetails.getWpsdetFilterAllYn())
                .as("Invalid product filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterAudienceCodeYn())
                .as("Invalid 'audience code' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterBisacYn())
                .as("Invalid 'bisac' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterDiscountCodeYn())
                .as("Invalid 'discount code' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterImprintYn())
                .as("Invalid 'imprint' filtering status.")
                .isFalse();

        assertThat(workflowProductStatusDetails.getWpsdetFilterIsbnTagYn())
                .as("Invalid 'isbn tag' filtering status.")
                .isNull();

        assertThat(workflowProductStatusDetails.getWpsdetFilterPublisherNameYn())
                .as("Invalid 'publisher name' filtering status.")
                .isFalse();

        assertThat(workflowProductStatusDetails.getWpsdetFilterSalesOutletYn())
                .as("Invalid 'sales outlet' filtering status.")
                .isTrue();

        assertThat(workflowProductStatusDetails.getWpsdetFilterSupplierIdentYn())
                .as("Invalid 'supplier identifier' filtering status.")
                .isFalse();

        assertThat(workflowProductStatusDetails.getWpsdetFilterTerritoryYn())
                .as("Invalid 'territory' filtering status.")
                .isFalse();

        assertThat(workflowProductStatusDetails.getWpsdetFilterPubDateYn())
                .as("Invalid 'Publication date' filtering status.")
                .isNull();
    }

    // Note. On a fresh env. Alert ingestion icon can be missed in top menu. Call triggerIngestAlert() a few times to make it appears.
    @TmsLink("AUT-776")
    @Test
    public void verifyOnixAlertNotificationsSection() {
        //Ingest failed onix
        new OnixTestService("regression/verifyOnixAlertNotifications/9781424556914_failed.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds);

        //Ingest a new product
        OnixTestService onix = new OnixTestService("regression/verifyOnixAlertNotifications/9781424556913.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        // Update product
        onix
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getProductAPIService().waitWhenNumberOfIngestFailuresIsGreaterThen(0, PredBUs.PRED_AUTOMATION_BU.getName(), OnixVersion.ONIX_2_1);

        getLoginPage().loginAs(SUPER_ADMIN);
        getProductAPIService().triggerIngestAlert();
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        getTopMenu().expandIngestAlertBox().clickTotalLink().getPageRootElm().shouldBe(visible);
        getTopMenu().expandIngestAlertBox().clickNewLink().getPageRootElm().shouldBe(visible);
        getTopMenu().expandIngestAlertBox().clickUpdatedLink().getPageRootElm().shouldBe(visible);
        getTopMenu().expandIngestAlertBox().getNumberOfFailedProductsElm().shouldBe(visible);
    }

    @TmsLink("AUT-802")
    @Test
    public void verifyAutoDistrOnlyContentAndCollateral() {
        final ChannelPublic channel = regressionPubChannelSFTPForAutoDistrOnlyContentAndCollateral;
        final ChannelAssociation ca = regressionCASFTPForAutoDistrOnlyContentAndCollateral;

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyAutoDistrOnlyContentAndCollateral/9780134741377.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetPrecompDetailsDB contentPrecompDetails = new ContentTestService("regression/verifyAutoDistrOnlyContentAndCollateral/9780134741377.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds)
                .getPrecompDetailsWhenExistFor(channel.getName());

        int actualContentDistrFailCount = contentPrecompDetails.getDistFailureCount();
        assertThat(actualContentDistrFailCount).as("Invalid content distribution fail count.").isZero();

        AssetPrecompDetailsDB collateralPrecompDetails = new CollateralTestService("regression/verifyAutoDistrOnlyContentAndCollateral/9780134741377_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds)
                .getPrecompDetailsWhenExistFor(channel.getName());

        int actualCollateralDistrFailCount = collateralPrecompDetails.getDistFailureCount();
        assertThat(actualCollateralDistrFailCount).as("Invalid collateral distribution fail count.").isZero();
    }

    @TmsLink("AUT-823")
    @Test(groups = {"optimization"})
    public void verifyMultipleIngestionSkipCountOfXMLOnix21File() {
        OnixTestService onix = new OnixTestService("regression/verifyMultipleIngestionSkipCountOfXMLOnix21File/9780000001926.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds2)
                .waitOnProductsInDB();

        String onixFileName = onix.getFilePath().getFileName().toString();
        getProductAPIService().waitOnCompleteStatusForFile(onixFileName, MetadataActivityPhase.INGEST);
        int firstBatchId = getBatchIdFor(onixFileName);
        PhaseTrackMasterDB ingestPhaseFirstIngestion = getPhaseTrackMasterFor(firstBatchId, MetadataActivityPhase.INGEST);

        int expNumbOfTargetAndSuccessProductInFirstIngestion = 2;
        int expNumbOfSkippedProductInFisrtIngestion = 0;

        assertThat(ingestPhaseFirstIngestion.getSuccessCount())
                .as("Expected to have all onix file products to be created successfully")
                .isEqualTo(ingestPhaseFirstIngestion.getTargetCount())
                .isEqualTo(expNumbOfTargetAndSuccessProductInFirstIngestion);
        assertThat(ingestPhaseFirstIngestion.getSkipCount())
                .as("Fail count or skip count that is bigger than 0 is not expected")
                .isEqualTo(ingestPhaseFirstIngestion.getFailCount())
                .isEqualTo(expNumbOfSkippedProductInFisrtIngestion);

        // ingest the same onix file second time

        new OnixTestService(onix.getFilePath())
                .uploadToCrushFTP(crushFtpUploadCreds2)
                .waitOnProductsInDB();

        List<Integer> batchIdList = getBatchIdListFor(onixFileName);

        int expBatchIdListSize = 2;
        int expBatchIdListSizeAfterRemovingFirstBatchId = 1;

        assertThat(batchIdList.size())
                .as("Number of lines is not expected, should be the same with the number of ingestions")
                .isEqualTo(expBatchIdListSize);
        batchIdList.remove(Integer.valueOf(firstBatchId));
        assertThat(batchIdList.size())
                .as("Number of lines are batch ids are not expected")
                .isEqualTo(expBatchIdListSizeAfterRemovingFirstBatchId);

        int secondBatchId = batchIdList.stream().collect(CustomCollectors.toSingleton());

        getProductAPIService().waitOnCompleteStatusForFile(secondBatchId, MetadataActivityPhase.INGEST);

        PhaseTrackMasterDB ingestPhaseSecondIngestion = getPhaseTrackMasterFor(secondBatchId, MetadataActivityPhase.INGEST);

        int expNumbOfTargetAndSkippedProductInSecondIngestion = 2;
        int expNumbOfSuccessAndFailedProductInSecondIngestion = 0;

        assertThat(ingestPhaseSecondIngestion.getSuccessCount())
                .as("Fail count that differs from 0 is not expected")
                .isEqualTo(ingestPhaseSecondIngestion.getFailCount())
                .isEqualTo(expNumbOfSuccessAndFailedProductInSecondIngestion);
        assertThat(ingestPhaseSecondIngestion.getSkipCount())
                .as("Expected the target count to be equal skip count because product is already created in the system")
                .isEqualTo(ingestPhaseSecondIngestion.getTargetCount())
                .isEqualTo(expNumbOfTargetAndSkippedProductInSecondIngestion);
    }

    @TmsLink("AUT-807")
    @Test
    public void verifyMultipleIngestionSkipCountOfXMLOnix30File() {
        OnixTestService onix = new OnixTestService("regression/verifyMultipleIngestionSkipCountOfXMLOnix30File/9780000001952.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds2)
                .waitOnProductsInDB();

        String onixFileName = onix.getFilePath().getFileName().toString();
        getProductAPIService().waitOnCompleteStatusForFile(onixFileName, MetadataActivityPhase.INGEST);
        int firstBatchId = getBatchIdFor(onixFileName);
        PhaseTrackMasterDB ingestPhaseFirstIngestion = getPhaseTrackMasterFor(firstBatchId, MetadataActivityPhase.INGEST);

        int expNumbOfTargetAndSuccessProductInFirstIngestion = 2;
        int expNumbOfSkippedProductInFisrtIngestion = 0;

        assertThat(ingestPhaseFirstIngestion.getSuccessCount())
                .as("Expected to have all onix file products to be created successfully")
                .isEqualTo(ingestPhaseFirstIngestion.getTargetCount())
                .isEqualTo(expNumbOfTargetAndSuccessProductInFirstIngestion);
        assertThat(ingestPhaseFirstIngestion.getSkipCount())
                .as("Fail count or skip count that is bigger than 0 is not expected")
                .isEqualTo(ingestPhaseFirstIngestion.getFailCount())
                .isEqualTo(expNumbOfSkippedProductInFisrtIngestion);

        // ingest the same onix file second time

        new OnixTestService(onix.getFilePath())
                .uploadToCrushFTP(crushFtpUploadCreds2)
                .waitOnProductsInDB();

        List<Integer> batchIdList = getBatchIdListFor(onixFileName);

        int expBatchIdListSize = 2;
        int expBatchIdListSizeAfterRemovingFirstBatchId = 1;

        assertThat(batchIdList.size())
                .as("Number of lines is not expected, should be the same with the number of ingestions")
                .isEqualTo(expBatchIdListSize);
        batchIdList.remove(Integer.valueOf(firstBatchId));
        assertThat(batchIdList.size())
                .as("Number of lines are batch ids are not expected")
                .isEqualTo(expBatchIdListSizeAfterRemovingFirstBatchId);

        int secondBatchId = batchIdList.stream().collect(CustomCollectors.toSingleton());

        getProductAPIService().waitOnCompleteStatusForFile(secondBatchId, MetadataActivityPhase.INGEST);

        PhaseTrackMasterDB ingestPhaseSecondIngestion = getPhaseTrackMasterFor(secondBatchId, MetadataActivityPhase.INGEST);

        int expNumbOfTargetAndSkippedProductInSecondIngestion = 2;
        int expNumbOfSuccessAndFailedProductInSecondIngestion = 0;

        assertThat(ingestPhaseSecondIngestion.getSuccessCount())
                .as("Fail count that differs from 0 is not expected")
                .isEqualTo(ingestPhaseSecondIngestion.getFailCount())
                .isEqualTo(expNumbOfSuccessAndFailedProductInSecondIngestion);
        assertThat(ingestPhaseSecondIngestion.getSkipCount())
                .as("Expected the target count to be equal skip count because product is already created in the system")
                .isEqualTo(ingestPhaseSecondIngestion.getTargetCount())
                .isEqualTo(expNumbOfTargetAndSkippedProductInSecondIngestion);
    }

    @DataProvider(name = "onixFeedWithTwoProductsAndDifferentBUs")
    private Object[][] onixFeedWithTwoProductsAndDifferentBUs() {
        return new Object[][]{
                {"regression/verifyBatchCloningWhenOnixFeedHasTwoProductsForDifferentBUs/9780000001926.xml"},
                {"regression/verifyBatchCloningWhenOnixFeedHasTwoProductsForDifferentBUs/9780000001952.xml"}
        };
    }

    @TmsLink("AUT-803")
    @Test(dataProvider = "onixFeedWithTwoProductsAndDifferentBUs", groups = {"batchODD"})
    public void verifyBatchCloningWhenOnixFeedAssociatedWithDifferentBUs(@NotNull String onixFilePath) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        // Create 2d BU.
        BusinessUnit secondBU = secondBUForAQAAccount;

        if (!isTenantExist(secondBU.getName())) {
            getTopMenu()
                    .clickAddBusinessUnitWithJS()
                    .createBusinessUnit(secondBU)
                    .getBUCreatedSuccessMessageElm().shouldBe(visible);
        }

        final String secondBUAlphaCode = secondBU.getAlphaCode();
        final int secondProductIndex = 1;
        OnixTestService onix = new OnixTestService(onixFilePath)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .setRecordSourceName(secondBUAlphaCode, secondProductIndex)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        String onixFileName = onix.getFilePath().getFileName().toString();

        await().atMost(ENV_CONFIG.awaitilityTimeout(), TimeUnit.SECONDS).untilAsserted(() -> {
            int batchMasterID = BatchDBService.getBatchMasterIDBy(secondBU.getName(), onixFileName);
            Assertions.assertThat(batchMasterID)
                    .as("No batch master ID for tenant: '" + secondBU.getName() + "' and onix feed: '" + onixFileName + "'.")
                    .isNotZero();
        });

        int batchID = BatchDBService.getBatchMasterIDBy(secondBU.getName(), onixFileName);
        int batchTrackId = BatchDBService.getBatchTrackMasterIdFor(batchID);

        assertThat(batchTrackId)
                .as("No batch track master ID for tenant: '" + secondBU.getName() + "' and onix feed: '" + onixFileName + "'.")
                .isNotZero();

        PhaseTrackMasterDB phaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(batchID, MetadataActivityPhase.INGEST);
        assertThat(phaseTrackMasterDB)
                .as("No phase track master record has been found for onix feed: '" + onixFileName + "' in phase: '" + MetadataActivityPhase.INGEST + "'.")
                .isNotNull();
    }

    @DataProvider(name = "onixFeedWithTwoProductsAndDifferentBUsAndAccounts")
    private Object[][] onixFeedWithTwoProductsAndDifferentBUsAndAccounts() {
        return new Object[][]{
                {"regression/verifyBatchCloningWhenOnixFeedHasTwoProductsForDifferentBUs/9780000001928.xml"},
                {"regression/verifyBatchCloningWhenOnixFeedHasTwoProductsForDifferentBUs/9780000001961.xml"}
        };
    }

    @TmsLink("AUT-803")
    @Test(dataProvider = "onixFeedWithTwoProductsAndDifferentBUsAndAccounts", groups = {"batchODD"})
    public void verifyBatchCloningWhenOnixFeedAssociatedWithDifferentBUsAndDifferentAccounts(@NotNull String onixFilePath) {
        OnixTestService onix = new OnixTestService(onixFilePath)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds);

        Selenide.sleep(ENV_CONFIG.awaitilityTimeout() * 100);

        List<OnixProduct> products = onix.getProducts();
        String firstProductTitle = products.get(0).getTitle();
        String secondProductTitle = products.get(1).getTitle();

        assertThat(getProductBy(firstProductTitle))
                .as("Product: '" + firstProductTitle + "' must not be ingested.")
                .isNull();

        assertThat(getProductBy(secondProductTitle))
                .as("Product: '" + secondProductTitle + "' must not be ingested.")
                .isNull();
    }

    @TmsLink("AUT-833")
    @Test
    public void verifyProductSearchWithSpecialSymbol() {
        final String VALIDATION_PATTERN = "^[^\\/]+$";

        final String INVALID_PRODUCT_TITLE = "Proverbs Wisdom from Above / Below";

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .expandProductsDropdown()
                .getSearchFieldElm()
                .val(INVALID_PRODUCT_TITLE)
                .pressEnter();

        new SearchProductResultsPage(INVALID_PRODUCT_TITLE)
                .getPageTitleElm().shouldNotBe(visible);

        getTopMenu().getSearchFieldElm().shouldHave(attribute("pattern", VALIDATION_PATTERN));
    }

    @TmsLink("AUT-834")
    @Test(groups = {"singleODD"})
    public void verifySortOrderForAssetStatusOnProductDetailsPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCAWithR = new ChannelAndCATestService(
                regressionSortOrderForAssetStatusOnProductDetailsPageWithR,
                regressionSortOrderForAssetStatusOnProductDetailsPage)
                .createChannelWithCA();

        ChannelAndCATestService channelAndCAWithr = new ChannelAndCATestService(
                regressionSortOrderForAssetStatusOnProductDetailsPageWithr,
                regressionSortOrderForAssetStatusOnProductDetailsPage)
                .createChannelWithCA();

        ChannelAndCATestService channelAndCAWithA = new ChannelAndCATestService(
                regressionSortOrderForAssetStatusOnProductDetailsPageWithA,
                regressionSortOrderForAssetStatusOnProductDetailsPage)
                .createChannelWithCA();

        ChannelAndCATestService channelAndCAWitha = new ChannelAndCATestService(
                regressionSortOrderForAssetStatusOnProductDetailsPageWitha,
                regressionSortOrderForAssetStatusOnProductDetailsPage)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySortOrderForAssetStatusOnProductDetailsPage/9781424555476.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(AssetType.ONIX21)
                .clickDistribute()
                .selectChannelsBy(channelAndCAWithR.getChannelName(), channelAndCAWithr.getChannelName(),
                        channelAndCAWithA.getChannelName(), channelAndCAWitha.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        MetadataTestService metadata = onix.toAsset();
        Arrays.asList(channelAndCAWithR, channelAndCAWithr, channelAndCAWithA, channelAndCAWitha)
                .forEach(channel -> metadata.waitOnSuccessfulDistributionTo(channel.getChannelName()));

        List<String> channelList = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAssetDistributionChannelsElmsFor(channelAndCAWithr.getAssetTypeFromSingleMetadata())
                .texts();

        AssertionsForInterfaceTypes.assertThat(channelList)
                .as("Channel associations are not sorted well (ascending).")
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);
    }

    @TmsLink("AUT-834")
    @Test(groups = {"singleODD"})
    public void verifySortOrderForAssetStatusOnProductDetailsPageFailedDistribution() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCAWithR = new ChannelAndCATestService(
                regressionSortOrderForAssetStatusOnProductDetailsPageFailedDistrWithR,
                regressionSortOrderForAssetStatusOnProductDetailsPage)
                .createChannelWithCA();

        ChannelAndCATestService channelAndCAWithr = new ChannelAndCATestService(
                regressionSortOrderForAssetStatusOnProductDetailsPageFailedDistrWithr,
                regressionSortOrderForAssetStatusOnProductDetailsPage)
                .createChannelWithCA();

        ChannelAndCATestService channelAndCAWithA = new ChannelAndCATestService(
                regressionSortOrderForAssetStatusOnProductDetailsPageFailedDistrWithA,
                regressionSortOrderForAssetStatusOnProductDetailsPage)
                .createChannelWithCA();

        ChannelAndCATestService channelAndCAWitha = new ChannelAndCATestService(
                regressionSortOrderForAssetStatusOnProductDetailsPageFailedDistrWitha,
                regressionSortOrderForAssetStatusOnProductDetailsPage)
                .createChannelWithCA();


        Arrays.asList(channelAndCAWithR, channelAndCAWithr, channelAndCAWithA, channelAndCAWitha).forEach(channel->
            getTopMenu()
                    .clickManageChannelWithJS()
                    .openOptimizationPageFor(channel.getChannelName())
                    .setOptimizationRulesAndComments(REGRESSION_OPT_21_FAILED_PATH_SUBSTITUTE_INVALID_SORT_ORDER_FOR_ASSET)
        );

        OnixTestService onix = new OnixTestService("regression/verifySortOrderForAssetStatusOnProductDetailsPageFailedDistribution/9781424555478.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(AssetType.ONIX21)
                .clickDistribute()
                .selectChannelsBy(channelAndCAWithR.getChannelName(), channelAndCAWithr.getChannelName(),
                        channelAndCAWithA.getChannelName(), channelAndCAWitha.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        MetadataTestService metadata = onix.toAsset();
        Arrays.asList(channelAndCAWithR, channelAndCAWithr, channelAndCAWithA, channelAndCAWitha)
                .forEach(channel -> {
                    AssetDistrStatusDetailsDB assetDistrStatusDetails = metadata
                            .getDistrStatusDetailsForPackingWhenExistsTo(channel.getChannelName());
                    assertThat(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId())
                            .as("Optimization file is broken, the failed packaging is expected")
                            .isEqualTo(PACKAGE_FAIL.getId());
                    assertThat(assetDistrStatusDetails.getProcessingErrors())
                            .as("Optimization file is broken, the 'fail' processing error is expected")
                            .contains(XPATH_EXPRESSION_INVALID_TAG.getErrorText());
                });

        List<String> channelList = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAssetDistributionChannelsElmsFor(channelAndCAWithr.getAssetTypeFromSingleMetadata())
                .texts();

        AssertionsForInterfaceTypes.assertThat(channelList)
                .as("Channel associations are not sorted well (ascending).")
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);
    }
}
