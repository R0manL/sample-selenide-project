package com.ccc.hv.qa.ui;

import com.ccc.hv.qa.ui.data.BUs;
import com.ccc.hv.qa.ui.data.Channels;
import com.ccc.hv.qa.ui.data.PredUsers;
import com.ccc.hv.qa.ui.data.Users;
import com.ccc.hv.qa.ui.services.*;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.ccc.hv.qa.db.enums.AssetAction;
import com.ccc.hv.qa.db.pojo.ProductDB;
import com.ccc.hv.qa.file.pojo.OnixProduct;
import com.ccc.hv.qa.ui.data.*;
import com.ccc.hv.qa.ui.enums.AssetType;
import com.ccc.hv.qa.ui.pages.ProductDetailsPage;
import com.ccc.hv.qa.ui.pages.SearchProductResultsPage;
import com.ccc.hv.qa.ui.pojos.Asset;
import com.ccc.hv.qa.ui.pojos.User;
import com.ccc.hv.qa.ui.services.*;
import com.ccc.hv.qa.utils.enums.DatePattern;
import com.ccc.service.xfer.TransferProtocol;
import io.qameta.allure.TmsLink;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.ccc.hv.qa.api.services.ProductAPIService.getProductAPIService;
import static com.ccc.hv.qa.api.services.ZiplineAPIService.getZiplineAPIService;
import static com.ccc.hv.qa.db.enums.AssetDistributionStatus.DISTRIB_SUCCESS;
import static com.ccc.hv.qa.db.services.AssetDBService.*;
import static com.ccc.hv.qa.db.services.ProductDBService.*;
import static com.ccc.hv.qa.db.services.ProductTypeDBService.getAllProductTypeNames;
import static com.ccc.hv.qa.db.services.TenantDBService.getTenantNameBy;
import static com.ccc.hv.qa.file.services.OnixFileService.readOnixFile;
import static com.ccc.hv.qa.file.services.XmlFileService.readXmlFile;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds;
import static com.ccc.hv.qa.ui.data.BatchODDEntities.SMOKE_BATCH_ODD_ENTITY;
import static com.ccc.hv.qa.ui.data.BatchODDEntities.SMOKE_BATCH_ODD_ENTITY_ONIX30;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.*;
import static com.ccc.hv.qa.ui.data.Channels.*;
import static com.ccc.hv.qa.ui.data.MetadataOptimizations.SMOKE_NON_APPLE_OPTIMIZATION_RULES_APPLY;
import static com.ccc.hv.qa.ui.data.MetadataOptimizations.SMOKE_OPTIMIZATION_APPLE;
import static com.ccc.hv.qa.ui.data.PredUsers.*;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.NewUserPasswordPage.openRegistrationUrlFromEmailAndSetNewPasswordFor;
import static com.ccc.hv.qa.ui.pages.ProductAppleMetadataPage.getProductAppleMetadataPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.EmailTestService.getUserActivationUrlFor;
import static com.ccc.hv.qa.ui.services.FileTestService.getFileTestService;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static com.ccc.hv.qa.utils.DateTimeUtils.changeDateFormat;
import static com.ccc.hv.qa.utils.DateTimeUtils.convertFromDBAssetTimeStamp;
import static com.ccc.hv.qa.utils.FileOpsUtils.*;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by R0manL on 17/08/20.
 */

//@Report
//@Listeners(TextReport.class)
@Test(groups = {"smoke", "ui"})
public class SmokeTests extends UITestBase {

    @DataProvider(name = "usersWithDifferentRoles")
    private Object[][] usersWithDifferentRoles() {
        return new Object[][]{
                {Users.smokeAccountAdmin},
                {Users.smokeHVViewMng},
                {Users.smokeMetadataAdmin},
                {Users.smokeAccMng},
                {Users.smokeTenantUser}};
    }

    @Test(dataProvider = "usersWithDifferentRoles")
    public void verifyUserCreationWithDifferentRolesAndLoginLogout(User user) {
        getLoginPage().loginAs(ACC_ADMIN);

        getTopMenu()
                .clickAddUser()
                .createUserWithValidation(user)
                .userAddedMessage().should(exist);

        String activationURL = getUserActivationUrlFor(user.getEmail());
        openRegistrationUrlFromEmailAndSetNewPasswordFor(user, activationURL);
        getLoginPage().loginAs(user);
    }

    @Test
    public void verifyBusinessUnitCreation() {
        getLoginPage().loginAs(ACC_ADMIN);

        final String BU_NAME = BUs.smokeBU.getName();
        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(BUs.smokeBU)
                .getBUCreatedSuccessMessageElm()
                .shouldHave(Condition.exactText(BU_NAME));

        getTopMenu()
                .getListOfBusinessUnits()
                .filter(text(BU_NAME))
                .shouldHave(size(1));
    }

    //Note. All other channel / CAs have been tested in other tests.
    public void verifyPublicChannelsAndChannelAssociationsCreation() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();
        new ChannelAndCATestService(smokePublicChannelFTP, smokeChannelAssociationFTP).createChannelWithCA();
    }

    @Test
    public void verifyExclusiveChannelCreation() {
        getLoginPage().loginAs(PredUsers.ACC_ADMIN);

        getTopMenuTestService()
                .selectDefaultBU()
                .clickAddChannel()
                .addExclusiveChannel(Channels.smokeExclusiveChannel);
    }

    @Test
    public void verifyProductTypesOnix21() {
        OnixTestService onix = new OnixTestService("smoke/verifyProductTypesOnix21/productTypesOnix21.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        List<OnixProduct> updatedProducts = onix.getProducts();
        Map<Integer, String> productTypesInDB = getAllProductTypeNames();
        SoftAssert soft = new SoftAssert();
        updatedProducts.forEach(onixProduct -> {
            String productTitle = onixProduct.getTitle();
            ProductDB dbProduct = waitAndGetProductFromDbBy(productTitle);
            int productTypeId = dbProduct.getHvPrdtypProductTypeId();
            String expectedProductType = productTypesInDB.get(productTypeId);
            soft.assertEquals(
                    onixProduct.getType(),
                    expectedProductType,
                    "Wrong product type for product: '" + onixProduct.getTitle() + "'.");
        });

        soft.assertAll();
    }

    @Test
    public void verifyProductTypesOnix30() {
        OnixTestService onix = new OnixTestService("smoke/verifyProductTypesOnix30/productTypesOnix30.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        List<OnixProduct> updatedProducts = onix.getProducts();
        Map<Integer, String> productTypesInDB = getAllProductTypeNames();
        SoftAssert soft = new SoftAssert();
        updatedProducts.forEach(onixProduct -> {
            String productTitle = onixProduct.getTitle();
            ProductDB dbProduct = waitAndGetProductFromDbBy(productTitle);
            int productTypeId = dbProduct.getHvPrdtypProductTypeId();
            String expectedProductType = productTypesInDB.get(productTypeId);
            soft.assertEquals(
                    onixProduct.getType(),
                    expectedProductType,
                    "Wrong product type for product: '" + onixProduct.getTitle() + "'.");
        });

        soft.assertAll();
    }

    @Test(groups = {"singleODD", "negative"})
    public void verifyNonAppleChannelOptimizationWhenRulesNotApply() {
        getLoginPage().loginAs(METADATA_ADMIN);
        getTopMenuTestService().selectDefaultBU();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokeExclusiveChannelSFTPWithNotAppliedOptimization,
                smokeChannelAssociationSFTPWithOptimization)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName());

        OnixTestService onix = new OnixTestService("smoke/verifyNonAppleChannelOptWhenRulesNotApply/9780826913586.xml")
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

        MetadataTestService metadata = onix.toAsset();
        Path expectedRemoteFilePath = Paths.get(metadata.getDistrPathFor(channelAndCA.getChannelName()));
        Path localTestDir = metadata.getFilePath().getParent();
        Path noOptDownloadedOnixPath = getFileTestService().downloadFileFromCrushFTP(expectedRemoteFilePath, localTestDir);

        assertThat(readOnixFile(noOptDownloadedOnixPath).hasOnixNode("j145"))
                .as("Expect that node <j145> should be present in Onix file.")
                .isTrue();
    }

    @Test(groups = "singleODD")
    public void verifyNonAppleChannelOptimizationWhenRulesApply() {
        getLoginPage().loginAs(METADATA_ADMIN);
        getTopMenuTestService().selectDefaultBU();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokeExclusiveChannelSFTPWithAppliedOptimization,
                smokeChannelAssociationSFTPWithOptimization)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(SMOKE_NON_APPLE_OPTIMIZATION_RULES_APPLY);

        OnixTestService onix = new OnixTestService("smoke/verifyNonAppleChannelOptWhenRulesApply/9780826941862.xml")
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

        MetadataTestService metadata = onix.toAsset();
        Path expectedRemoteFilePath = Paths.get(metadata.getDistrPathFor(channelAndCA.getChannelName()));
        Path localTestDir = metadata.getFilePath().getParent();
        Path optOptDownloadedOnixPath = getFileTestService().downloadFileFromCrushFTP(expectedRemoteFilePath, localTestDir);

        assertThat(readOnixFile(optOptDownloadedOnixPath).hasOnixNode("j145"))
                .as("Expect that node <j145> should be present in Onix file.")
                .isFalse();
    }

    @Test(groups = {"singleODD", "itms"})
    public void verifyAppleChannelOptimizationRules() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelITMSWithOptimization,
                smokeChannelAssociationITMSWithOptimization)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(SMOKE_OPTIMIZATION_APPLE);

        OnixTestService onix = new OnixTestService("smoke/verifyAppleChannelOptimizationRules/9781496430144.xml")
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

        Path metadataFilePath = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .clickAppleMetadataLink()
                .downloadAppleMetadataFile();

        String expectedXpath = "//*[local-name()='book']/*[local-name()='metadata']/" +
                "*[local-name()='contributors']/*[local-name()='contributor']/*[local-name()='roles']/*[local-name()='role' and text()='Created by']";
        assertThat(readXmlFile(metadataFilePath).hasNode(expectedXpath))
                .as("Contributor with value='Created by' has not present. ")
                .isTrue();

        String xpathToRemovedTag = "//*[local-name()='book']/*[local-name()='metadata']" +
                "/*[local-name()='title' and text()=\"God's Call to a Deeper Life: Unveiling and Embracing the Depths of His Love\"]";
        assertThat(readXmlFile(metadataFilePath).hasNode(xpathToRemovedTag))
                .as("Second title has not been removed.")
                .isFalse();
    }

    @Test
    public void verifyProductSearchResultsPage() {
        OnixTestService onix = new OnixTestService("smoke/verifyProductSearchResultsPage/9781424550753.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getFileTestService().uploadFilesToCrushFTPRootFolder(Paths.get("smoke/verifyProductSearchResultsPage/assets"));

        OnixProduct expectedProduct = onix.getSingleProduct();

        getLoginPage().loginAs(ACC_ADMIN);
        SearchProductResultsPage srchResPage = getTopMenuTestService()
                .selectDefaultBU()
                .searchPresentProductBy(expectedProduct.getTitle());

        srchResPage.getPageTitleElm().shouldHave(text(expectedProduct.getTitle()));
        srchResPage.productWithTitle().shouldBe(visible);
        srchResPage.productWithAuthor(expectedProduct.getAuthor()).shouldBe(visible);
        srchResPage.productWithIsbn(expectedProduct.getIsbn13()).shouldBe(visible);
        srchResPage.productWithProductType(expectedProduct.getType()).shouldBe(visible);
        String buNameFromOnix = getTenantNameBy(expectedProduct.getRecordSourceName());
        srchResPage.productWithBusinessUnit(buNameFromOnix).shouldBe(visible);
        srchResPage.productWithPublisher(expectedProduct.getPublisher()).shouldBe(visible);
        srchResPage.productWithRecordReference(expectedProduct.getRecordReference()).shouldBe(visible);
        srchResPage.productWithMetadataBadge(true).shouldBe(visible);
        srchResPage.productWithCollateralBadge(true).shouldBe(visible);
        srchResPage.productWithContentBadge(true).shouldBe(visible);
        srchResPage.productWithHoldDistribution(false).shouldBe(visible);
        srchResPage.productWithLockIndicator(false).shouldBe(visible);
    }

    @Test
    public void verifyInformationOnProductDetailsPage() {
        OnixTestService onix = new OnixTestService("smoke/verifyInformationOnProductDetailsPage/9780824909475.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getLoginPage().loginAs(SUPER_ADMIN);
        ProductDetailsPage productDetailsPage = getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        OnixProduct product = onix.getSingleProduct();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productDetailsPage.getProductTitle(), product.getTitle(), "Invalid product title.");
        soft.assertEquals(productDetailsPage.getAuthorName(), product.getAuthor(), "Invalid author.");
        soft.assertEquals(productDetailsPage.getIsbn(), product.getIsbn13(), "Invalid isbn.");
        soft.assertEquals(productDetailsPage.getPublisherName(), product.getPublisher(), "Invalid publisher name.");
        soft.assertEquals(productDetailsPage.getProductType(), product.getType(), "Invalid product type.");
        String expectedOnSaleDate = changeDateFormat(product.getOnSaleDate(), DatePattern.UI_PRODUCT);
        soft.assertEquals(productDetailsPage.getOnSaleDate(), expectedOnSaleDate, "Invalid onSaleDate.");
        soft.assertEquals(productDetailsPage.getRecordReference(), product.getRecordReference(), "Invalid record reference.");
        soft.assertEquals(productDetailsPage.getProductSubject(), product.getSubject(), "Invalid product subject.");
        soft.assertEquals(productDetailsPage.getProductImprints(), product.getImprints(), "Invalid product imprint.");
        soft.assertFalse(productDetailsPage.isProductLocked(), "Invalid '" + product.getTitle() + "' product lock status");
        soft.assertAll();
    }

    @Test
    public void verifyAssetsOnProductDetailsPage() {
        getFileTestService().uploadFilesToCrushFTPRootFolder(
                TransferProtocol.FTP,
                Paths.get("smoke/verifyAssetsOnProductDetailsPage/assets"));

        OnixTestService onix = new OnixTestService("smoke/verifyAssetsOnProductDetailsPage/9781646430239.xml")
                .readOnixFile()
                .updateTitles()
                .updateMessageCreationDateAsToday()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        final int PRODUCT_ID = getProductIDBy(onix.getSingleProductTitle());
        String metadataFileName = "9781646430239.xml";
        final Asset SMOKE_METADATA_ASSET = Asset.builder()
                .fileName(metadataFileName)
                .type(AssetType.ONIX21)
                .firstAdded(convertFromDBAssetTimeStamp(getFirstAddedTimeStamp(metadataFileName, PRODUCT_ID)))
                .lastRevisioned(convertFromDBAssetTimeStamp(getLastRevTimeStamp(metadataFileName, PRODUCT_ID)))
                .fileSize("7.81 KB")
                .build();

        final String IMG_FILE_NAME = "9781646430239_marketingimage.jpg";
        final Asset SMOKE_IMAGE_ASSET = Asset.builder()
                .fileName(IMG_FILE_NAME)
                .type(AssetType.MARKETING_IMAGE)
                .firstAdded(convertFromDBAssetTimeStamp(getFirstAddedTimeStamp(IMG_FILE_NAME, PRODUCT_ID)))
                .lastRevisioned(convertFromDBAssetTimeStamp(getLastRevTimeStamp(IMG_FILE_NAME, PRODUCT_ID)))
                .fileSize("4.6 KB")
                .build();

        final String PDF_FILE_NAME = "9781646430239_print.pdf";
        final Asset SMOKE_PDF_ASSET = Asset.builder()
                .fileName(PDF_FILE_NAME)
                .type(AssetType.PRINT_REPLICA)
                .firstAdded(convertFromDBAssetTimeStamp(getFirstAddedTimeStamp(PDF_FILE_NAME, PRODUCT_ID)))
                .lastRevisioned(convertFromDBAssetTimeStamp(getLastRevTimeStamp(PDF_FILE_NAME, PRODUCT_ID)))
                .fileSize("100.72 KB")
                .build();

        getLoginPage().loginAs(ACC_ADMIN);
        ProductDetailsPage productDetailsPage = getTopMenuTestService()
                .selectDefaultBU()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        //Verify assets details
        List<Asset> expectedAssets = Arrays.asList(SMOKE_METADATA_ASSET, SMOKE_IMAGE_ASSET, SMOKE_PDF_ASSET);
        List<Asset> actualAssets = productDetailsPage.getAllAssets();
        assertThat(actualAssets).usingRecursiveComparison().isEqualTo(expectedAssets);

        //Verify asset files
        List<File> expectedAssetFiles = getFileTestService().getListOfFilesFrom(Paths.get("smoke/verifyAssetsOnProductDetailsPage/assets"));
        expectedAssetFiles.add(onix.getFilePath().toFile());
        compareFileLists(expectedAssetFiles, productDetailsPage.downloadAssets());
    }

    @Test
    public void verifyAssetRemovingFromDetailsPage() {
        getFileTestService().uploadFilesToCrushFTPRootFolder(Paths.get("smoke/verifyAssetRemovingFromDetailsPage/assets"));

        OnixTestService onix = new OnixTestService("smoke/verifyAssetRemovingFromDetailsPage/9780544791442.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        final String PRODUCT_TITLE = onix.getSingleProductTitle();

        getLoginPage().loginAs(ACC_ADMIN);
        ProductDetailsPage productDetailsPage = getTopMenuTestService()
                .selectDefaultBU()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage();

        AssetType assetType = AssetType.MARKETING_IMAGE;
        String assetFileName = "9780544791442_marketingimage.jpg";

        //Verify before deletion
        assertThat(getAssetActionStatus(assetFileName, PRODUCT_TITLE))
                .as("Wrong asset action.")
                .isEqualTo(AssetAction.NONE);

        productDetailsPage.getAllAssetElements().shouldHave(CollectionCondition.size(2))
                .filterBy(text(assetFileName)).shouldHave(size(1));
        productDetailsPage.getDeleteButton().shouldBe(disabled);

        //Delete asset
        productDetailsPage.selectAssetsBy(assetType);
        productDetailsPage.getDeleteButton().shouldBe(enabled);
        productDetailsPage.deleteSelectedAsset();

        //Verify after deletion
        productDetailsPage.getAllAssetElements().shouldHave(CollectionCondition.size(1))
                .filterBy(text(assetFileName)).shouldBe(CollectionCondition.empty);
        assertThat(getAssetActionStatus(assetFileName, PRODUCT_TITLE))
                .as("Wrong asset action.")
                .isEqualTo(AssetAction.DELETED);
    }

    @Test
    public void verifyAddProductAndAddAsset() {
        OnixTestService onix = new OnixTestService("smoke/verifyAddProductAndAddAsset/9781424555147.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds);

        getLoginPage().loginAs(ACC_ADMIN);

        //Add product manually.
        getTopMenuTestService()
                .selectDefaultBU()
                .clickAddProduct()
                .uploadProductsOrAssets(onix.getFilePath());

        onix.waitOnProductsInDB();

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        AssetTestService content = new ContentTestService("smoke/verifyAddProductAndAddAsset/assets/9781424555147_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference());

        productDetailsPage.getAllAssetElements()
                .shouldHave(CollectionCondition.size(1))
                .filterBy(text(content.getFileName()))
                .shouldBe(CollectionCondition.empty);

        //Add asset manually.
        productDetailsPage
                .addAssets()
                .uploadProductsOrAssets(content.getFilePath());

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAllAssetElements().shouldHave(CollectionCondition.size(2))
                .filterBy(text(content.getFileName())).shouldHave(size(1));
    }

    @Test
    public void verifyOnHoldFunctionality() {
        OnixTestService onix = new OnixTestService("smoke/verifyOnHoldFunctionality/9781419727375.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds);

        getLoginPage().loginAs(ACC_ADMIN);

        final String PRODUCT_TITLE = onix.getSingleProductTitle();
        ProductDetailsPage productDetailsPage = getTopMenuTestService()
                .selectDefaultBU()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage()
                .putOnHold();

        assertTrue(productDetailsPage.isOnHoldActive());
        assertTrue(hasProductOnHold(PRODUCT_TITLE));

        productDetailsPage.removeOnHold();
        assertFalse(productDetailsPage.isOnHoldActive());
        assertFalse(hasProductOnHold(PRODUCT_TITLE));
    }

    @Test(groups = "singleODD")
    public void verifyODDAndDistributionStatusForOnix21() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelSFTPForODD,
                smokeChannelAssociationSFTPForODD)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("smoke/verifyODDAndDistributionStatusForOnix21/9781424555413.xml")
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

    @Test(groups = "singleODD")
    public void verifyProductDeletion() {
        OnixTestService onix = new OnixTestService("smoke/verifyProductDeletion/9781419728761.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getLoginPage().loginAs(ACC_ADMIN);

        final String PRODUCT_TITLE = onix.getSingleProductTitle();
        getTopMenuTestService()
                .selectDefaultBU()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage()
                .deleteProduct();

        assertTrue(hasProductMarkedForDeletion(PRODUCT_TITLE), "Product '" + PRODUCT_TITLE + "' must be marked for deletion.");

        getTopMenu()
                .searchDeletedProductBy(PRODUCT_TITLE)
                .productWithTitle()
                .shouldNotBe(visible);
    }

    @Test
    public void verifyProductTracking() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        new ChannelAndCATestService(
                smokePublicChannelSFTPForProductTracking,
                smokeChannelAssociationSFTPForProductTracking)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("smoke/verifyProductTracking/9781463960537.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        final String PRODUCT_TITLE = onix.getSingleProductTitle();
        SearchProductResultsPage resultsPage = getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .activateBulkSelection()
                .selectProduct()
                .clickTrackProductsAndConfirm();

        resultsPage.productWithTrackedBadge(true).shouldBe(visible);
        ProductDB productDB = getProductBy(PRODUCT_TITLE);
        assertThat(productDB).as("Product '" + PRODUCT_TITLE + "' has not been created.").isNotNull();
        assertThat(productDB.isTrackedYn()).as("Product '" + PRODUCT_TITLE + "' has not tracked.").isTrue();
    }

    @Test
    public void verifyLockFunctionality() {
        OnixTestService onix = new OnixTestService("smoke/verifyLockFunctionality/9781496434050.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getLoginPage().loginAs(ACC_ADMIN);

        final String PRODUCT_TITLE = onix.getSingleProductTitle();
        SearchProductResultsPage resultsPage = getTopMenuTestService()
                .selectDefaultBU()
                .searchPresentProductBy(PRODUCT_TITLE);

        resultsPage.productWithLockIndicator(true).shouldBe(visible);
        ProductDetailsPage productDetailsPage = resultsPage.openProductDetailsPage();
        assertTrue(productDetailsPage.isProductLocked(), "Wrong '" + PRODUCT_TITLE + "' product locked status on UI.");
        assertTrue(isProductLocked(PRODUCT_TITLE), "Wrong '" + PRODUCT_TITLE + "' product locked status in DB.");
    }

    @Test(groups = {"watermarking"})
    public void verifyEpubWatermarking() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelSFTPForEpubWatermarking,
                smokeChannelAssociationSFTPForEpubWatermarking)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("smoke/verifyEpubWatermarking/9780136612766.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("smoke/verifyEpubWatermarking/assets/9780136612766.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleContent())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        content.waitOnWatermarkingCompletionFor(channelAndCA.getChannelName());
        String actualRemoteDistrPath = content.getDistrPathFor(channelAndCA.getChannelName());

        assertThat(actualRemoteDistrPath)
                .as("Wrong distribution path.")
                .isEqualTo("Watermark/" + AssetType.EPUB.getDistributionPathValue() + "/" + content.getFileName());

        String localTestDir = content.getFilePath().getParent() + "/downloadedAsset";
        Path downloadDir = createDirectory(localTestDir);
        Path downloadedFilePath = getFileTestService().downloadFileFromCrushFTP(Paths.get(actualRemoteDistrPath), downloadDir);

        assertThat(FileUtils.sizeOf(downloadedFilePath.toFile()))
                .isGreaterThan(FileUtils.sizeOf(content.getFilePath().toFile()));

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAssetDistributionStatusElmFor(
                        channelAndCA.getChannelName(),
                        channelAndCA.getAssetTypeFromSingleContent(),
                        DISTRIB_SUCCESS)
                .shouldBe(visible);
    }

    @Test(groups = "watermarking")
    @TmsLink("AUT-229")
    public void verifyPdfWatermarking() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelSFTPForPdfWatermarking,
                smokeChannelAssociationSFTPPdfForWatermarking)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("smoke/verifyPdfWatermarking/9781292286723.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("smoke/verifyPdfWatermarking/assets/9781292286723.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleContent())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        content.waitOnWatermarkingCompletionFor(channelAndCA.getChannelName());
        String actualRemoteDistrPath = content.getDistrPathFor(channelAndCA.getChannelName());

        assertThat(actualRemoteDistrPath)
                .as("Wrong distribution path.")
                .isEqualTo("Watermark/" + AssetType.WEB_OPTIMIZED_PDF.getDistributionPathValue() + "/" + content.getFileName());

        String localTestDir = content.getFilePath().getParent() + "/downloadedAsset";
        Path downloadDir = createDirectory(localTestDir);
        Path downloadedFilePath = getFileTestService().downloadFileFromCrushFTP(Paths.get(actualRemoteDistrPath), downloadDir);

        assertThat(FileUtils.sizeOf(downloadedFilePath.toFile()))
                .isGreaterThan(FileUtils.sizeOf(content.getFilePath().toFile()));

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAssetDistributionStatusElmFor(channelAndCA.getChannelName(), channelAndCA.getAssetTypeFromSingleContent(), DISTRIB_SUCCESS)
                .shouldBe(visible);
    }

    @Test(groups = {"batchodd"})
    public void verifyBatchODD() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelFTPSForBatchODD,
                smokeChannelAssociationFTPSForBatchODD)
                .createChannelWithCA();

        //Product setup 1
        OnixTestService onix1 = new OnixTestService("smoke/verifyBatchODD/9780636178007.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService collateral1 = new CollateralTestService("smoke/verifyBatchODD/assets_9780636178007/9780636178007_marketingimage.jpg")
                .cloneFileWith(onix1.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        //Product setup 2
        OnixTestService onix2 = new OnixTestService("smoke/verifyBatchODD/9781775955221.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService collateral2 = new CollateralTestService("smoke/verifyBatchODD/assets_9781775955221/9781775955221_marketingimage.jpg")
                .cloneFileWith(onix2.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(SMOKE_BATCH_ODD_ENTITY)
                .getScheduledConfirmationMsgElm().shouldBe(visible);

        Arrays.asList(collateral1, collateral2)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));

        getTopMenu()
                .searchPresentProductBy(onix1.getSingleProductTitle())
                .openProductDetailsPage()
                .getAssetDistributionStatusElmFor(channelAndCA.getChannelName(),
                        channelAndCA.getAssetTypeFromSingleCollateral(),
                        DISTRIB_SUCCESS)
                .shouldBe(visible);

        getTopMenu()
                .searchPresentProductBy(onix2.getSingleProductTitle())
                .openProductDetailsPage()
                .getAssetDistributionStatusElmFor(channelAndCA.getChannelName(),
                        channelAndCA.getAssetTypeFromSingleCollateral(),
                        DISTRIB_SUCCESS)
                .shouldBe(visible);
    }

    @Test(groups = {"singleODD", "itms"})
    public void verifyAppleNonOptDistribution() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        OnixTestService onix = new OnixTestService("smoke/verifyAppleNonOptDistribution/9781414396248.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService content = new ContentTestService("smoke/verifyAppleNonOptDistribution/assets/9781414396248.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("smoke/verifyAppleNonOptDistribution/assets/9781414396248_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelITMSForDistribution,
                smokeChannelAssociationITMSForDistribution)
                .createChannelWithCA();

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral(),
                        channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAppleMetadataLinkFor(AssetType.ONIX21)
                .click();

        Path filePath = getProductAppleMetadataPage().downloadAppleMetadataFile();

        //TODO here the epubAssetFileName is expected but now the epub asset name is isbn.epub
        final String ONIX_FILE_NODE_XPATH_TEMPLATE = "//*[local-name()='assets']//*[local-name()='file_name' and text()='%s']";
        String expectedEpubXpath = String.format(ONIX_FILE_NODE_XPATH_TEMPLATE, onix.getSingleProduct().getIsbn13() + ".epub");
        assertThat(readXmlFile(filePath).hasNode(expectedEpubXpath))
                .as("Tag with filename '" + content.getFileName() + "' is not found.")
                .isTrue();

        String expectedImageXpath = String.format(ONIX_FILE_NODE_XPATH_TEMPLATE, collateral.getFileName());
        assertThat(readXmlFile(filePath).hasNode(expectedImageXpath))
                .as("Tag with filename '" + collateral.getFileName() + "' is not found")
                .isTrue();
    }

    @Test(groups = "singleODD")
    public void verifyODDAndDistributionStatusForOnix30() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelSFTPForODDONIX30,
                smokeChannelAssociationSFTPForODDONIX30)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("smoke/verifyODDAndDistributionStatusForOnix30/9780830851973.xml")
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

    @Test(groups = "singleODD")
    public void verifyAudioDistribution() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelSFTPForAudioDistribution,
                smokeChannelAssociationSFTPForAudioDistribution)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("smoke/verifyAudioDistribution/9780358343134.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content1 = new ContentTestService("smoke/verifyAudioDistribution/assets/9780358343134_054.wav")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService content2 = new ContentTestService("smoke/verifyAudioDistribution/assets/9780358343134_057.wav")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(content1.getFileName(), content2.getFileName())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        getZiplineAPIService().triggerZipFileDistributionFor(onix.getSingleProductTitle());

        content1.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
        content2.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        Path localTestDir = content1.getFilePath().getParent();
        Path remoteFilePath = Paths.get(channelAndCA.getSingleContent().getDestinationFolder() + onix.getSingleProduct().getIsbn13() + ".zip");
        Path downloadedZipFilePath = getFileTestService().downloadFileFromCrushFTP(remoteFilePath, localTestDir);

        assertThat(getFileNamesFromZipFile(downloadedZipFilePath))
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(Arrays.asList(content1.getFileName(), content2.getFileName()));
    }

    @Test
    public void verifyMetadataProductDetailsPageOnix30() {
        OnixTestService onix = new OnixTestService("smoke/verifyMetadataProductDetailsPageOnix30/9780830845774.xml");
        final String origOnixFileName = onix.getFileNameWithRecRef();

        onix.readOnixFile()
                .updateTitles()
                .updateMessageCreationDateAsToday()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getLoginPage().loginAs(ACC_ADMIN);
        getTopMenuTestService().selectDefaultBU();

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        int productId = getProductIDBy(onix.getSingleProductTitle());
        //ToDo verify if next line works
        LocalDateTime firstAddedTimestamp = convertFromDBAssetTimeStamp(getFirstAddedTimeStamp(origOnixFileName, productId));
        LocalDateTime lastRevTimeStamp = convertFromDBAssetTimeStamp(getLastRevTimeStamp(origOnixFileName, productId));

        final Asset EXPECTED_ONIX30_METADATA_ASSET = Asset.builder()
                .fileName(origOnixFileName)
                .type(AssetType.ONIX30)
                .fileSize("13.36 KB")
                .firstAdded(firstAddedTimestamp)
                .lastRevisioned(lastRevTimeStamp)
                .build();

        assertThat(EXPECTED_ONIX30_METADATA_ASSET).usingRecursiveComparison().isEqualTo(productDetailsPage.getAllAssets().get(0));
        File originalOnixFile = onix.getFilePath().toFile();
        compareFileLists(Collections.singletonList(originalOnixFile), productDetailsPage.downloadAssets());
    }

    @Test(groups = "batchODD")
    public void verifyBatchODDOnix30() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelFTPSForBatchODDOnix30,
                smokeChannelAssociationFTPSForBatchODDOnix30)
                .createChannelWithCA();

        OnixTestService onix1 = new OnixTestService("smoke/verifyBatchODDOnix30/9780830845828.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onix2 = new OnixTestService("smoke/verifyBatchODDOnix30/9780830852772.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(SMOKE_BATCH_ODD_ENTITY_ONIX30)
                .getScheduledConfirmationMsgElm().shouldBe(visible);

        onix1.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
        onix2.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        Arrays.asList(onix1, onix2)
                .forEach(onix -> {
                    ProductDetailsPage page = getTopMenu()
                            .searchPresentProductBy(onix.getSingleProductTitle())
                            .openProductDetailsPage();

                    page.getAllAssetElements()
                            .asFixedIterable().stream()
                            .forEach(asset ->
                                    page.getAssetDistributionStatusElmFor(
                                    channelAndCA.getChannelName(),
                                    channelAndCA.getAssetTypeFromSingleMetadata(),
                                    DISTRIB_SUCCESS)
                                    .shouldBe(visible));
                });
    }

    @Test(groups = "singleODD")
    public void verifyProductEditMetadataForOnix21() {
        OnixTestService onix = new OnixTestService("smoke/verifyProductEditMetadataForOnix21/9781683228356.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        final String OLD_PRODUCT_TITLE = onix.getSingleProductTitle();
        final String NEW_PRODUCT_TITLE = generateUniqueStringBasedOnDate("New_test_product_title_rev_21_");
        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(OLD_PRODUCT_TITLE)
                .openProductDetailsPage()
                .clickEditProductMetadata()
                .updateValueInEditorBy(OLD_PRODUCT_TITLE, NEW_PRODUCT_TITLE)
                .validateAndSave()
                .clickBack();

        assertThat(productDetailsPage.getProductTitle()).as("Invalid title on UI.").isEqualTo(NEW_PRODUCT_TITLE);

        String downloadedOnixFilePath = productDetailsPage.downloadAssetBy(AssetType.ONIX21).getAbsolutePath();
        OnixProduct editedProduct = new OnixTestService(downloadedOnixFilePath).getSingleProduct();
        assertThat(editedProduct.getTitle())
                .as("Invalid product title (in onix file).")
                .isEqualTo(NEW_PRODUCT_TITLE);
    }

    @Test
    public void verifyProductEditMetadataForOnix30() {
        OnixTestService onix = new OnixTestService("smoke/verifyProductEditMetadataForOnix30/9781928226277.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        final String OLD_PRODUCT_TITLE = onix.getSingleProductTitle();
        final String NEW_PRODUCT_TITLE = generateUniqueStringBasedOnDate("New_test_product_title_rev_30_");
        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(OLD_PRODUCT_TITLE)
                .openProductDetailsPage()
                .clickEditProductMetadata()
                .updateValueInEditorBy(OLD_PRODUCT_TITLE, NEW_PRODUCT_TITLE)
                .validateAndSave()
                .clickBack();

        assertThat(productDetailsPage.getProductTitle()).as("Invalid title on UI.").isEqualTo(NEW_PRODUCT_TITLE);

        String downloadedOnixFilePath = productDetailsPage.downloadAssetBy(AssetType.ONIX30).getAbsolutePath();
        OnixProduct editedProduct = new OnixTestService(downloadedOnixFilePath).getSingleProduct();
        assertThat(editedProduct.getTitle())
                .as("Invalid product title (in onix file).")
                .isEqualTo(NEW_PRODUCT_TITLE);
    }

    @Test(groups = "autoDistribution")
    public void verifyAutoDistribution() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                smokePublicChannelFTPSForAutoDistr,
                smokeChannelAssociationPTPSForAutoDistr)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("smoke/verifyAutoDistribution/9780544086661.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("smoke/verifyAutoDistribution/assets/9780544086661.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("smoke/verifyAutoDistribution/assets/9780544086661_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getProductAPIService().triggerAutoDistribution();

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
        content.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
        collateral.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        Arrays.asList(
                channelAndCA.getAssetTypeFromSingleContent(),
                channelAndCA.getAssetTypeFromSingleCollateral(),
                channelAndCA.getAssetTypeFromSingleMetadata())
                .forEach(assetType -> productDetailsPage.getAssetDistributionStatusElmFor(
                        channelAndCA.getChannelName(),
                        assetType,
                        DISTRIB_SUCCESS).shouldBe(visible)
                );
    }
}