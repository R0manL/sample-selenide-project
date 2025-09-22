package com.ccc.hv.qa.ui.migrated;

import com.ccc.hv.qa.core.CustomCollectors;
import com.ccc.hv.qa.db.enums.AssetProcessingError;
import com.ccc.hv.qa.db.pojo.AssetDistrStatusDetailsDB;
import com.ccc.hv.qa.db.pojo.PhaseTrackMasterDB;
import com.ccc.hv.qa.db.services.BatchDBService;
import com.ccc.hv.qa.file.pojo.OnixProduct;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.enums.AssetType;
import com.ccc.hv.qa.ui.enums.MetadataActivityPhase;
import com.ccc.hv.qa.ui.pages.MetadataActivityPage;
import com.ccc.hv.qa.ui.pages.ProductDetailsPage;
import com.ccc.hv.qa.ui.pojos.ChannelAssociation;
import com.ccc.hv.qa.ui.pojos.ChannelPublic;
import com.ccc.hv.qa.ui.pojos.Email;
import com.ccc.hv.qa.ui.pojos.MetadataOptimization;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.ui.services.AssetTestService;
import com.ccc.hv.qa.ui.services.ChannelAndCATestService;
import com.ccc.hv.qa.ui.services.OnixTestService;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.codeborne.selenide.Condition.*;
import static com.ccc.hv.qa.api.services.ProductAPIService.getProductAPIService;
import static com.ccc.hv.qa.db.enums.AssetDistributionStatus.PACKAGE_FAIL;
import static com.ccc.hv.qa.db.enums.AssetProcessingError.*;
import static com.ccc.hv.qa.db.services.ProductDBService.waitOnProductInDbBy;
import static com.ccc.hv.qa.file.services.OnixFileService.readOnixFile;
import static com.ccc.hv.qa.file.services.XmlFileService.readXmlFile;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds2;
import static com.ccc.hv.qa.ui.data.BatchODDEntities.*;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.*;
import static com.ccc.hv.qa.ui.data.Channels.*;
import static com.ccc.hv.qa.ui.data.MetadataOptimizations.*;
import static com.ccc.hv.qa.ui.data.PredUsers.SUPER_ADMIN;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.MetadataOptimizationPage.getChannelOptimizationPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.FileTestService.getFileTestService;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static com.ccc.hv.qa.utils.DateTimeUtils.getServerLocalDateNow;
import static com.ccc.hv.qa.utils.EmailUtils.getSingleEmailAndDeleteItFor;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@Test(groups = {"ui", "optimization"})
public class OptimizationTests extends UITestBase {

    @TmsLink("AUT-485")
    @TmsLink("AUT-796")
    @Issue("HRV-29833")
    @Test(groups = {"optimization"})
    public void verifyOptimization21FailedPath() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOpt21FailedPath,
                regressionChannelAssociationSFTPForOpt21FailedPath)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_21_FAILED_PATH_SUBSTITUTE_INVALID);

        OnixTestService onix = new OnixTestService("regression/verifyOptimization21FailedPath/9780000011988.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        AssetDistrStatusDetailsDB assetDistrStatusDetails = onix.toAssets().get(0)
                .getDistrStatusDetailsForPackingWhenExistsTo(channelAndCA.getChannelName());
        assertThat(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId())
                .as("Optimization file is broken, the failed packaging is expected")
                .isEqualTo(PACKAGE_FAIL.getId());
        assertThat(assetDistrStatusDetails.getProcessingErrors())
                .as("Optimization file is broken, the 'fail' processing error is expected")
                .contains(XPATH_EXPRESSION_INVALID_TAG.getErrorText());

        getProductAPIService().triggerOptimizationFailureDigest();
        Email email = getSingleEmailAndDeleteItFor("test@ccc.com");
        assertThat(email.getSubject())
                .contains("[QA] Hrv Optimization Failure Digest");

        String emailContent = email.getContent();
        assertThat(emailContent)
                .contains("This email is to inform you that the following assets have failed optimization.")
                .contains("Please do not reply to this email, the email box is not monitored.")
                .contains(FIRST_PRODUCT_TITLE)
                .contains(channelAndCA.getChannelName())
                .contains(firstProduct.getIsbn13())
                .contains(channelAndCA.getAssetTypeFromSingleMetadata().getText())
                .contains(firstProduct.getRecordReference());
    }

    @TmsLink("AUT-485")
    @Test(groups = {"optimization"})
    public void verifyOptimization21FromLongOnix21ToShort30AddSubstitute() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "89";
        final String EXPECTED_PRODUCT_ID_TYPE_NAME = "Test Identifier";
        final String EXPECTED_PRODUCT_ID_TYPE_VALUE = "XXXXX";

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOpt21FromLong21ToShort30AddSubstitute,
                regressionChannelAssociationSFTPForOpt21FromLong21ToShort30AddSubstitute)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_21_LONG_ONIX21_TO_SHORT_30_ADD_SUBSTITUTE_RULE);

        OnixTestService onix = new OnixTestService("regression/verifyOptimization21FromLongOnix21ToShort30AddSubstitute/Two_Products-Long_ONIX.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String distrPath = onix.toAssets().get(0).getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onix.getParentDir());
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

        assertThat(downloadedOnix.isOnix30()).as("Onix 3.0 format is expected for optimized onix").isTrue();
        assertThat(downloadedOnix.isLongFormat()).as("Short format is expected for optimized onix").isFalse();
        assertThat(downloadedOnix.getNotificationTypes()).isNotEmpty()
                .allMatch(notificationType -> notificationType.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION));
        assertThat(downloadedOnix.getProductsIDTypeNames()).contains(EXPECTED_PRODUCT_ID_TYPE_NAME);
        assertThat(downloadedOnix.getProductsIDTypeValues()).contains(EXPECTED_PRODUCT_ID_TYPE_VALUE);
    }

    @TmsLink("AUT-485")
    @Test(groups = {"optimization"})
    public void verifyOptimization21RemoveRule() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOpt21Remove,
                regressionChannelAssociationSFTPForOpt21Remove)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_21_REMOVE_RULE);

        OnixTestService onixProduct1 = new OnixTestService("regression/verifyOptimization21RemoveRule/9780000001999.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onixProduct2 = new OnixTestService("regression/verifyOptimization21RemoveRule/9780000001986.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        Arrays.asList(onixProduct1, onixProduct2).forEach(onix -> {
            OnixProduct firstProduct = onix.getSingleProduct();
            final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
            waitOnProductInDbBy(FIRST_PRODUCT_TITLE);
        });

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_OPT_ONIX21)
                .getScheduledConfirmationMsgElm().shouldBe(visible);

        Arrays.asList(onixProduct1, onixProduct2)
                .forEach(onix -> {
                    String distrPath = onix.toAsset().getDistrPathFor(channelAndCA.getChannelName());
                    Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onix.getParentDir());
                    OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

                    assertThat(downloadedOnix.getProductsIDTypeNames())
                            .as("Expect the b233 tag be removed during Optimization process")
                            .isEmpty();
                });
    }

    @TmsLink("AUT-485")
    @Test(groups = {"optimization"})
    public void verifyOptimization21MultiplyRule() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectDefaultTestAccountAndBu();


        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOptMultiply,
                regressionChannelAssociationSFTPForOptMultiply)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_21_MULTIPLY_RULE);

        OnixTestService onix = new OnixTestService("regression/verifyOptimizationMultiplyRule/9780000001993.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String distrPath = onix.toAssets().get(0).getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onix.getParentDir());
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);
        final int EXPECTED_DOWNLOADED_ONIX_FILE_SIZE = 2;

        assertThat(EXPECTED_DOWNLOADED_ONIX_FILE_SIZE).isEqualTo(downloadedOnix.getProducts().size());

        OnixProduct multipliedOnixProduct = downloadedOnix.getProducts().stream()
                .filter(product -> product.getRecordReference().equals(firstProduct.getRecordReference() + "_mobi"))
                .collect(CustomCollectors.toSingleton());

        assertThat(firstProduct.getRecordReference() + "_mobi")
                .isEqualTo(multipliedOnixProduct.getRecordReference());
        assertThat("Mobipocket")
                .isEqualTo(multipliedOnixProduct.getType());
    }

    @TmsLink("AUT-487")
    @Test(groups = {"optimization", "itms"})
    public void verifyOptimization21OnixAddRemoveSubstituteTransformationAppleMetadata() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectSecondTestAccountAndBu();

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSWithOptimization21,
                regressionChannelAssociationITMSWithOptimization21)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPTIM_21_ADD_REPL_REMOVE_APPLE_META_PROFILE);

        OnixTestService onix = new OnixTestService("regression/verifyOptimization21AddRemoveSubstituteTransformationAppleMetadata/Apple-ONIX.xml")
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

        String expectedAddTagXpath = ".//*[local-name()='product']/*[local-name()='price_tier' and text()='20']";
        assertThat(readXmlFile(metadataFilePath).hasNode(expectedAddTagXpath))
                .as("'Add' optimization has not applied.")
                .isTrue();

        String expectedSubstituteTagXpath = ".//*[local-name()='product']/*[local-name()='release_type' and text()='other']";
        assertThat(readXmlFile(metadataFilePath).hasNode(expectedSubstituteTagXpath))
                .as("'Substitute' optimization has not applied.")
                .isFalse();

        String expectedRemoveReplaceTagXpath = ".//*[local-name()='contributor']/*[local-name()='name' and text()='Emily Kane']";
        assertThat(readXmlFile(metadataFilePath).hasNode(expectedRemoveReplaceTagXpath))
                .as("'Remove - replace' optimization has not applied.")
                .isTrue();

        String expectedRemoveReplaceTagNotShownXpath = ".//*[local-name()='contributor']/*[local-name()='name' and text()='John M. Smith']";
        assertThat(readXmlFile(metadataFilePath).hasNode(expectedRemoveReplaceTagNotShownXpath))
                .as("'Remove - replace' optimization has not applied.")
                .isFalse();
    }

    @DataProvider(name = "optFailedOnix30TestData")
    private Object[][] optFailedOnix30TestData() {
        return new Object[][]{
                {"regression/verifyOptimization30FailedPath/9780000001921.xml",
                        regressionPublicChannelSFTPOpt30FailedPathSyntaxError,
                        regressionChannelAssociationSFTPForOpt30FailedPathSyntaxError,
                        REGRESSION_OPT_30_FAILED_PATH_SUBSTITUTE_INVALID_SYNTAX_ERROR,
                        XPATH_EXPRESSION_INVALID_TAG
                },
                {"regression/verifyOptimization30FailedPath/9780000001920.xml",
                        regressionPublicChannelSFTPOpt30FailedPathRemoveRecRef,
                        regressionChannelAssociationSFTPForOpt30FailedPathRemoveRecRef,
                        REGRESSION_OPT_30_FAILED_PATH_SUBSTITUTE_INVALID_REMOVE_REC_REF,
                        SAX_EXCEPTION_INVALID_CONTENT
                }};
    }

    @TmsLink("AUT-676")
    @TmsLink("AUT-796")
    @Issue("HRV-29833")
    @Test(dataProvider = "optFailedOnix30TestData", groups = "optimization")
    public void verifyOptimization30FailedPath(@NotNull String metadataOnixFile,
                                               ChannelPublic channel,
                                               ChannelAssociation ca,
                                               MetadataOptimization metadataOptimization,
                                               AssetProcessingError expectedError) {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(metadataOptimization);

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

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        AssetDistrStatusDetailsDB assetDistrStatusDetails = onix.toAssets().get(0)
                .getDistrStatusDetailsForPackingWhenExistsTo(channelAndCA.getChannelName());
        assertThat(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId())
                .as("Optimization file is broken, the failed packaging is expected")
                .isEqualTo(PACKAGE_FAIL.getId());
        assertThat(assetDistrStatusDetails.getProcessingErrors())
                .as("Optimization file is broken, the processing error is expected")
                .contains(expectedError.getErrorText());

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .getAssetDistributionStatusElmFor(
                        channelAndCA.getChannelName(),
                        channelAndCA.getAssetTypeFromSingleMetadata(),
                        PACKAGE_FAIL)
                .shouldBe(visible);

        getProductAPIService().triggerOptimizationFailureDigest();
        Email email = getSingleEmailAndDeleteItFor("test@ccc.com");
        assertThat(email.getSubject())
                .contains("[QA] Hrv Optimization Failure Digest");

        String emailContent = email.getContent();
        assertThat(emailContent)
                .contains("This email is to inform you that the following assets have failed optimization.")
                .contains("Please do not reply to this email, the email box is not monitored.")
                .contains(FIRST_PRODUCT_TITLE)
                .contains(channelAndCA.getChannelName())
                .contains(firstProduct.getIsbn13())
                .contains(channelAndCA.getAssetTypeFromSingleMetadata().getText())
                .contains(firstProduct.getRecordReference());
    }

    @TmsLink("AUT-677")
    @Test(groups = {"optimization", "itms"})
    public void verifyOptimizationOnix30AddReplaceTransformationAppleMetadata() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSWithOptimization30AddReplace,
                regressionChannelAssociationITMSWithOptimization30AddReplace)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPTIM_30_ADD_REPL_APPLE_META_PROFILE);

        OnixTestService onix = new OnixTestService("regression/verifyOptimizationOnix30AddReplaceTransformationAppleMetadata/Apple-ONIX.xml")
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

        String gbSectionRoot = ".//*[local-name()='product'][child::*[local-name()='territory' and text()='GB']]";
        String gbPriceSalesStartDateTag = gbSectionRoot + "/*[local-name()='sales_start_date' and text()='2009-01-01']";
        String gbPriceClearedForSaleTag = gbSectionRoot + "/*[local-name()='cleared_for_sale' and text()='true']";
        String gbPricePhysicalListPriceTag = gbSectionRoot + "/*[local-name()='physical_list_price' and text()='20.99']";
        String gbPriceTierTag = gbSectionRoot + "/*[local-name()='price_tier' and text()='10']";
        String gbPriceDRMFreeTag = gbSectionRoot + "/*[local-name()='drm_free' and text()='false']";

        Arrays.asList(gbPriceSalesStartDateTag, gbPriceClearedForSaleTag,
                        gbPricePhysicalListPriceTag, gbPriceTierTag, gbPriceDRMFreeTag)
                .forEach(tag -> assertThat(readXmlFile(metadataFilePath).hasNode(tag))
                        .as("'Add' optimization has not applied.").isTrue());

        String gbPriceReleaseTypeTag = gbSectionRoot + "/*[local-name()='release_type' and text()='digital-only']";

        assertThat(readXmlFile(metadataFilePath).hasNode(gbPriceReleaseTypeTag))
                .as("'Substitute' optimization has not applied.").isTrue();
    }

    @TmsLink("AUT-677")
    @Test(groups = {"optimization", "itms"})
    public void verifyOptimizationOnix30SubstituteReplaceTransformationAppleMetadata() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSWithOptimization30SubsReplace,
                regressionChannelAssociationITMSWithOptimization30SubsReplace)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPTIM_30_SUBS_REPL_APPLE_META_PROFILE);

        OnixTestService onix = new OnixTestService("regression/verifyOptimizationOnix30SubstituteReplaceTransformationAppleMetadata/Apple-ONIX.xml")
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

        String crSectionRoot = ".//*[local-name()='product'][child::*[local-name()='territory' and text()='CR']]";
        String crPriceSalesStartDateTag = crSectionRoot + "/*[local-name()='sales_start_date' and text()='2009-01-01']";
        String crPriceClearedForSaleTag = crSectionRoot + "/*[local-name()='cleared_for_sale' and text()='true']";
        String gbPriceReleaseTypeTag = crSectionRoot + "/*[local-name()='release_type' and text()='other']";
        String crPricePhysicalListPriceTag = crSectionRoot + "/*[local-name()='physical_list_price' and text()='5.99']";
        String crPriceTierTag = crSectionRoot + "/*[local-name()='price_tier' and text()='6']";
        String crPriceDRMFreeTag = crSectionRoot + "/*[local-name()='drm_free' and text()='false']";

        Arrays.asList(crPriceSalesStartDateTag, crPriceClearedForSaleTag, gbPriceReleaseTypeTag,
                        crPricePhysicalListPriceTag, crPriceTierTag, crPriceDRMFreeTag)
                .forEach(tag -> assertThat(readXmlFile(metadataFilePath).hasNode(tag))
                        .as("'Substitute' optimization has not applied.").isTrue());
    }

    @TmsLink("AUT-677")
    @Test(groups = {"optimization", "itms"})
    public void verifyOptimizationOnix30RemoveReplaceTransformationAppleMetadata() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSWithOptimization30RemoveReplace,
                regressionChannelAssociationITMSWithOptimization30RemoveReplace)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPTIM_30_REMOVE_REPL_APPLE_META_PROFILE);

        OnixTestService onix = new OnixTestService("regression/verifyOptimizationOnix30RemoveReplaceTransformationAppleMetadata/Apple-ONIX.xml")
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

        String contributorSectionRoot = ".//*[local-name()='contributor'][child::*[local-name()='name' and text()='Other Contributor']]";
        String contributorSortName = contributorSectionRoot + "/*[local-name()='sort_name' and text()='Contributor, Other']";
        String contributorPrimary = contributorSectionRoot + "/*[local-name()='primary' and text()='false']";
        String contributorRole = contributorSectionRoot + "/*[local-name()='roles']/*[local-name()='role' and text()='Illustrated by']";

        Arrays.asList(contributorSortName, contributorPrimary, contributorRole)
                .forEach(tag -> assertThat(readXmlFile(metadataFilePath).hasNode(tag))
                        .as("'Remove' optimization has not applied.").isFalse());
    }

    @TmsLink("AUT-679")
    @Test(groups = {"optimization"})
    public void verifyOptimization30AddRule() {
        final String EXPECTED_PRODUCT_ID_TYPE_NAME = "Test Identifier 30";
        final String EXPECTED_PRODUCT_ID_TYPE_VALUE = "XXX30";

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOpt30Add,
                regressionChannelAssociationSFTPForOpt30Add)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_30_ADD_RULE);

        OnixTestService onix = new OnixTestService("regression/verifyOptimization30AddRule/9780000001958.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct product = onix.getSingleProduct();
        final String PRODUCT_TITLE = product.getTitle();
        waitOnProductInDbBy(PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String distrPath = onix.toAsset().getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onix.getParentDir());
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

        assertThat(downloadedOnix.getProductsIDTypeNames()).contains(EXPECTED_PRODUCT_ID_TYPE_NAME);
        assertThat(downloadedOnix.getProductsIDTypeValues()).contains(EXPECTED_PRODUCT_ID_TYPE_VALUE);
    }

    @TmsLink("AUT-680")
    @Test(groups = {"optimization"})
    public void verifyOptimization30RemoveRule() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOpt30Remove,
                regressionChannelAssociationSFTPForOpt30Remove)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_30_REMOVE_RULE);

        OnixTestService onixProduct1 = new OnixTestService("regression/verifyOptimization30RemoveRule/9780000001957.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onixProduct2 = new OnixTestService("regression/verifyOptimization30RemoveRule/9780000001923.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();


        Arrays.asList(onixProduct1, onixProduct2).forEach(onix -> {
            OnixProduct product = onix.getSingleProduct();
            final String PRODUCT_TITLE = product.getTitle();
            waitOnProductInDbBy(PRODUCT_TITLE);
        });

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_OPT_ONIX30)
                .getScheduledConfirmationMsgElm().shouldBe(visible);

        Arrays.asList(onixProduct1, onixProduct2)
                .forEach(onix -> {
                    String distrPath = onix.toAsset().getDistrPathFor(channelAndCA.getChannelName());
                    Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onix.getParentDir());
                    OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

                    assertThat(downloadedOnix.getProductsIDTypeNames())
                            .as("Expect the b233 tag be removed during Optimization process")
                            .isEmpty();
                });
    }

    @TmsLink("AUT-681")
    @Test(groups = {"optimization"})
    public void verifyOptimization30SubstituteRuleOnix() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "89";

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOpt30Substitute,
                regressionChannelAssociationSFTPForOpt30Substitute)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_30_SUBSTITUTE_RULE);

        OnixTestService onix = new OnixTestService("regression/verifyOptimization30SubstituteRule/9780000001992.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String distrPath = onix.toAssets().get(0).getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onix.getParentDir());
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

        assertThat(downloadedOnix.isOnix30()).as("Onix 3.0 format is expected for optimized onix").isTrue();
        assertThat(downloadedOnix.getNotificationTypes()).isNotEmpty()
                .allMatch(notificationType -> notificationType.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION));
    }

    @TmsLink("AUT-683")
    @Test(groups = {"optimization"})
    public void verifyOptimizationRulesWhenTheyNotMatchOnixVersion21() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlSFTPOptNonMatchOnix21,
                regressionChannelAssociationSFTPForOptNonMatchOnix21)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(OPT_REMOVE_RULE_21_FOR_ONIX_21);

        OnixTestService onix = new OnixTestService("regression/verifyOptimizationRulesWhenTheyNotMatchOnixVersion21/9780000002000.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String distrPath = onix.toAssets().get(0).getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onix.getParentDir());
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

        assertThat(downloadedOnix.getProductsIDTypeNames())
                .as("The b233 tag must be present, since optimization must not applied.")
                .isNotEmpty();
    }

    @TmsLink("AUT-683")
    @Test(groups = {"optimization"})
    public void verifyOptimizationRulesWhenTheyNotMatchOnixVersion30() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "88";

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlSFTPOptNonMatchOnix30,
                regressionChannelAssociationSFTPForOptNonMatchOnix30)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(OPT_SUBSTITUTE_RULE_30_FOR_ONIX_30);

        OnixTestService onix = new OnixTestService("regression/verifyOptimizationRulesWhenTheyNotMatchOnixVersion30/9780000001995.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String distrPath = onix.toAssets().get(0).getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onix.getParentDir());
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

        assertThat(downloadedOnix.isOnix30()).as("Onix 3.0 format has expected.").isTrue();
        assertThat(downloadedOnix.getNotificationTypes()).as("Notification type has missed.").isNotEmpty()
                .as("NotificationType's value must not be equal to: '" + EXPECTED_VALUE_AFTER_SUBSTITUTION +
                        "'. Optimization must not be applied.").noneMatch(nt -> nt.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION));
    }

    @TmsLink("AUT-694")
    @Test(groups = {"optimization"})
    public void verifyOptimizationWithConversionOnixFrom21To30() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "88";

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForOptConversion21To30,
                regressionCASFTPForOptConversion21To30)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_30_CONVERSION_SUBSTITUTE_RULE);

        OnixTestService onixFile = new OnixTestService("regression/verifyOptimizationWithConversionOnixFrom21To30/9780000001980.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onixFile.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        final String distrPathForFirstProduct = onixFile.toAssets().get(0).getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedOnixFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPathForFirstProduct), onixFile.getParentDir());

        OnixTestService onixTestService = new OnixTestService(downloadedOnixFile);
        assertThat(onixTestService.isOnix30()).as("Invalid Onix version.").isTrue();

        onixTestService.getProducts()
                .forEach(product -> assertThat(product.getNotificationType()).isEqualTo(EXPECTED_VALUE_AFTER_SUBSTITUTION));
    }

    @TmsLink("AUT-704")
    @Test(groups = {"optimization"})
    public void verifyPrefixAndTitleElementWereNotRemovedOnAddElementOperation() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlSFTPForVerifyPrefixAndTitleElement,
                regressionChnlAssocSFTPForVerifyPrefixAndTitleElement)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_30_ADD_PUBLISHING_STATUS_NOTE);

        OnixTestService onixFile = new OnixTestService("regression/verifyPrefixAndTitleElementWereNotRemovedOnAddElementOperation/9780830893270.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct product = onixFile.getSingleProduct();
        final String FIRST_PRODUCT_TITLE = product.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        final String distrPathForFirstProduct = onixFile.toAsset().getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedOnixFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPathForFirstProduct), onixFile.getParentDir());

        assertThat(readOnixFile(downloadedOnixFile).hasOnixNode("b395"))
                .as("Optimization rule has not applied. Expect node <b395> to be present in Onix file.")
                .isTrue();

        assertThat(readOnixFile(downloadedOnixFile).hasOnixNode("b030"))
                .as("Expect node <b030> to be present in Onix file.")
                .isTrue();

        assertThat(readOnixFile(downloadedOnixFile).hasOnixNode("b031"))
                .as("Expect node <b031> to be present in Onix file.")
                .isTrue();
    }

    @TmsLink("AUT-713")
    @Test(groups = {"optimization", "itms"})
    public void verifyOnix30CAOptionsOn() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectSecondTestAccountAndBu();

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSWithOnix30CAOptionsOn,
                regressionChannelAssociationITMSWithOnix30CAOptionsOn)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyOnix30CAOptionsOn/Apple-ONIX.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(getServerLocalDateNow().minusMonths(1))
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

        String crSectionRoot = ".//*[local-name()='product'][child::*[local-name()='territory' and text()='CA']]";
        String usSectionRoot = ".//*[local-name()='product'][child::*[local-name()='territory' and text()='US']]";
        String DRMFreeTag = "/*[local-name()='drm_free' and text()='true']";

        Arrays.asList(crSectionRoot + DRMFreeTag, usSectionRoot + DRMFreeTag)
                .forEach(tag -> assertThat(readXmlFile(metadataFilePath).hasNode(tag))
                        .as("DRM free Apple CA option is not appiled.").isTrue());

        String explicitTag = ".//*[local-name()='explicit' and text()='true']";
        assertThat(readXmlFile(metadataFilePath).hasNode(explicitTag))
                .as("Explicit Apple CA is not appiled.").isTrue();

        String imprintTag = ".//*[local-name()='publisher' and text()='Test Imprint']";
        assertThat(readXmlFile(metadataFilePath).hasNode(imprintTag))
                .as("Imprint Apple CA is not appiled.").isTrue();

        String preorderProvidersTag = ".//*[local-name()='preorder_previews' and text()='true']";
        assertThat(readXmlFile(metadataFilePath).hasNode(preorderProvidersTag))
                .as("Preorder Providers Apple CA is not appiled.").isTrue();

        String releaseTypeTag = ".//*[local-name()='release_type' and text()='other']";
        assertThat(readXmlFile(metadataFilePath).hasNode(releaseTypeTag))
                .as("ReleaseType Apple CA is not appiled.").isFalse();

        String sectionRootTemplate = ".//*[local-name()='product'][child::*[local-name()='territory' and text()='%s']]";
        Arrays.asList("AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ",
                "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HN", "HU", "IE", "IT",
                "JP", "LT", "LU", "LV", "MT", "MX", "NI", "NL", "NO", "NZ", "PA", "PE", "PL", "PT", "PY",
                "RO", "SE", "SI", "SK", "SV", "US").forEach(territory -> {
            String gbPriceReleaseTypeTag = String.format(sectionRootTemplate, territory) + "/*[local-name()='release_type' and text()='new-release']";
            assertThat(readXmlFile(metadataFilePath).hasNode(gbPriceReleaseTypeTag))
                    .as("ReleaseType Apple CA is not appiled.").isTrue();
        });
    }

    @TmsLink("AUT-713")
    @Test(groups = {"optimization", "itms"})
    public void verifyOnix21CAOptionsOn() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSWithOnix21CAOptionsOn,
                regressionChannelAssociationITMSWithOnix21CAOptionsOn)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPTIM_21_CA_OPTIONS_ON_APPLE_META_PROFILE);

        OnixTestService onix = new OnixTestService("regression/verifyOnix21CAOptionsOn/Apple-ONIX.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(getServerLocalDateNow().minusMonths(1))
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

        String usSectionRoot = ".//*[local-name()='product'][child::*[local-name()='territory' and text()='US']]";
        String DRMFreeTag = "/*[local-name()='drm_free' and text()='true']";

        assertThat(readXmlFile(metadataFilePath).hasNode(usSectionRoot + DRMFreeTag))
                .as("DRM free Apple CA option is not appiled.").isTrue();

        String explicitTag = ".//*[local-name()='explicit' and text()='true']";
        assertThat(readXmlFile(metadataFilePath).hasNode(explicitTag))
                .as("Explicit Apple CA is not appiled.").isTrue();

        String imprintTag = ".//*[local-name()='publisher' and text()='Test Imprint']";
        assertThat(readXmlFile(metadataFilePath).hasNode(imprintTag))
                .as("Imprint Apple CA is not appiled.").isTrue();

        String preorderProvidersTag = ".//*[local-name()='preorder_previews' and text()='true']";
        assertThat(readXmlFile(metadataFilePath).hasNode(preorderProvidersTag))
                .as("Preorder Providers Apple CA is not appiled.").isTrue();

        String releaseTypeTag = ".//*[local-name()='release_type' and text()='other']";
        assertThat(readXmlFile(metadataFilePath).hasNode(releaseTypeTag))
                .as("ReleaseType Apple CA is not appiled.").isFalse();

        String sectionRootTemplate = ".//*[local-name()='product'][child::*[local-name()='territory' and text()='%s']]";
        Arrays.asList("AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ",
                "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HN", "HU", "IE", "IT",
                "JP", "LT", "LU", "LV", "MT", "MX", "NI", "NL", "NO", "NZ", "PA", "PE", "PL", "PT", "PY",
                "RO", "SE", "SI", "SK", "SV", "US").forEach(territory -> {
            String gbPriceReleaseTypeTag = String.format(sectionRootTemplate, territory) + "/*[local-name()='release_type' and text()='new-release']";
            assertThat(readXmlFile(metadataFilePath).hasNode(gbPriceReleaseTypeTag))
                    .as("ReleaseType Apple CA is not appiled.").isTrue();
        });
    }

    @DataProvider(name = "appleMetaOptProcessTestData")
    private Object[][] appleMetaOptProcessTestData() {
        return new Object[][]{
                {"regression/verifyAppleMetaOptProcessIssues/9781292002828.xml",
                        regressionPubChnlITMSForAppleMetaOptProcessIssues21,
                        regressionChannelAssociationITMSForAppleMetaOptProcessIssues21,
                        REGRESSION_APPLE_META_OPT_ISSUES_21
                },
                {"regression/verifyAppleMetaOptProcessIssues/9780807596488.xml",
                        regressionPubChnlITMSForAppleMetaOptProcessIssues30,
                        regressionChannelAssociationITMSForAppleMetaOptProcessIssues30,
                        REGRESSION_APPLE_META_OPT_ISSUES_30
                }};
    }

    @DataProvider(name = "appleMetaInvalidOptProfileTestData")
    private Object[][] appleMetaInvalidOptProfileTestData() {
        return new Object[][]{
                {"regression/verifyAppleMetaInvalidSingleOptimizationProfile/9781292002829.xml",
                        regressionPubChnlITMSForAppleMetaInvalidOptProfile21,
                        regressionChannelAssociationITMSForAppleMetaInvalidOptProfile21,
                        REGRESSION_APPLE_META_INVALID_OPT_PROFILE_21
                },
                {"regression/verifyAppleMetaInvalidSingleOptimizationProfile/9780807596489.xml",
                        regressionPubChnlITMSForAppleMetaInvalidOptProfile30,
                        regressionChannelAssociationITMSForAppleMetaInvalidOptProfile30,
                        REGRESSION_APPLE_META_INVALID_OPT_PROFILE_30
                }};
    }

    @TmsLink("AUT-714")
    @Test(dataProvider = "appleMetaInvalidOptProfileTestData", groups = {"optimization", "itms"})
    public void verifyAppleMetaInvalidSingleOptimizationProfile(@NotNull String onixFilePath,
                                                                ChannelPublic channel,
                                                                ChannelAssociation ca,
                                                                MetadataOptimization optRuleFilePath) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(optRuleFilePath);

        OnixTestService onix = new OnixTestService(onixFilePath)
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

        AssetDistrStatusDetailsDB assetDistrStatusDetails = onix.toAsset()
                .getDistrStatusDetailsForPackingWhenExistsTo(channelAndCA.getChannelName());

        assertThat(assetDistrStatusDetails.getProcessingErrors())
                .as("Invalid asset processing error.")
                .isEqualTo(AssetProcessingError.OPTIMIZATION_PROBLEM_FOR_APPLE_DISTR.getErrorText());

        assertThat(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId())
                .as("Invalid asset distribution status ref ID.")
                .isEqualTo(PACKAGE_FAIL.getId());
    }

    @TmsLink("AUT-715")
    @Test(dataProvider = "appleMetaOptProcessTestData", groups = {"optimization", "itms"})
    public void verifyAppleMetaOptProcessIssues(@NotNull String onixFilePath,
                                                ChannelPublic channel,
                                                ChannelAssociation ca,
                                                MetadataOptimization optRuleFilePath) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(optRuleFilePath);

        OnixTestService onix = new OnixTestService(onixFilePath)
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

        AssetDistrStatusDetailsDB assetDistrStatusDetails = onix.toAsset()
                .getDistrStatusDetailsForPackingWhenExistsTo(channelAndCA.getChannelName());

        assertThat(assetDistrStatusDetails.getProcessingErrors())
                .as("Invalid asset processing error.")
                .isEqualTo(AssetProcessingError.OPTIMIZATION_PROBLEM_FOR_APPLE_DISTR.getErrorText());

        assertThat(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId())
                .as("Invalid asset distribution status ref ID.")
                .isEqualTo(PACKAGE_FAIL.getId());
    }

    @Issue("HRV-29717")
    @TmsLink("AUT-720")
    @Test(groups = {"optimization", "itms"})
    public void verifyAppleMetaWhenBothOptimizationProfilesAreValid() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSWithAppleMetaBothValidOptProfiles,
                regressionChannelAssociationITMSForAppleMetaBothValidOptProfiles).createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_APPLE_META_BOTH_VALID_OPT_PROFILES_FOR_ONIX_21);

        getChannelOptimizationPage()
                .setOptimizationRulesAndComments(REGRESSION_APPLE_META_BOTH_VALID_OPT_PROFILES_FOR_ONIX_30);

        final String REC_REF_SUFFIX = generateUniqueStringBasedOnDate();
        OnixTestService onix = new OnixTestService("regression/verifyAppleMetaWhenBothOptimizationProfilesAreValid/9781292301426.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferencesWithSuffix(REC_REF_SUFFIX)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onix2 = new OnixTestService("regression/verifyAppleMetaWhenBothOptimizationProfilesAreValid/ONIX3_short_Test_Product-20220203.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferencesWithSuffix(REC_REF_SUFFIX)
                .saveAsNewFile();

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .addAssets()
                .uploadProductsOrAssets(onix2.getFilePath());

        onix2.waitOnProductsInDB();

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix2.getSingleProductTitle())
                .openProductDetailsPage();

        channelAndCA.getMultipleMetadata()
                .forEach(assetType -> productDetailsPage.selectAssetsBy(AssetType.from(assetType.getMetadataType())));

        productDetailsPage
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution()
                .clickReturn();

        onix.toAsset().waitOnSuccessfullyMultipleDistrStatusDetailsTo(channelAndCA.getChannelName());

        getTopMenu()
                .searchPresentProductBy(onix2.getSingleProductTitle())
                .openProductDetailsPage();

        channelAndCA.getMultipleMetadata()
                .forEach(assetType -> productDetailsPage
                        .getAppleMetadataLinkFor(AssetType.from(assetType.getMetadataType()))
                        .shouldBe(visible));
    }

    @TmsLink("AUT-720")
    @Test(groups = {"optimization", "itms"})
    public void verifyAppleMetaWhenBothOptimizationProfilesAreInValid() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSWithAppleMetaBothInValidOptProfiles,
                regressionChannelAssociationITMSForAppleMetaBothInValidOptProfiles).createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_APPLE_META_BOTH_INVALID_OPT_PROFILES_FOR_ONIX_21);

        getChannelOptimizationPage()
                .setOptimizationRulesAndComments(REGRESSION_APPLE_META_BOTH_INVALID_OPT_PROFILES_FOR_ONIX_30);

        final String REC_REF_SUFFIX = generateUniqueStringBasedOnDate();
        OnixTestService onix = new OnixTestService("regression/verifyAppleMetaWhenBothOptimizationProfilesAreInValid/9781292301427.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferencesWithSuffix(REC_REF_SUFFIX)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onix2 = new OnixTestService("regression/verifyAppleMetaWhenBothOptimizationProfilesAreInValid/ONIX3_short_Test_Product-20220203.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferencesWithSuffix(REC_REF_SUFFIX)
                .saveAsNewFile();

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .addAssets()
                .uploadProductsOrAssets(onix2.getFilePath());

        onix2.waitOnProductsInDB();

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix2.getSingleProductTitle())
                .openProductDetailsPage();

        channelAndCA.getMultipleMetadata()
                .forEach(assetType -> productDetailsPage.selectAssetsBy(AssetType.from(assetType.getMetadataType())));

        productDetailsPage
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution()
                .clickReturn();

        getTopMenu()
                .searchPresentProductBy(onix2.getSingleProductTitle())
                .openProductDetailsPage();

        channelAndCA.getMultipleMetadata()
                .forEach(assetType -> productDetailsPage
                        .getAppleMetadataLinkFor(AssetType.from(assetType.getMetadataType()))
                        .shouldNotBe(visible));
    }

    @TmsLink("AUT-780")
    @Test(groups = {"optimization"})
    public void verifyPartiallyFailOptimizationOnix30() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPPartiallyFailOptOnix30,
                regressionChannelAssociationSFTPForOptPartiallyFailOnix30)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_PARTIALLY_FAIL_ONIX30);

        OnixTestService onixProductOptPass = new OnixTestService("regression/verifyPartiallyFailOptimizationOnix30/9780000001024.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onixProductOptFail = new OnixTestService("regression/verifyPartiallyFailOptimizationOnix30/9780000001017.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        Arrays.asList(onixProductOptPass, onixProductOptFail).forEach(onix -> {
            OnixProduct product = onix.getSingleProduct();
            final String PRODUCT_TITLE = product.getTitle();
            waitOnProductInDbBy(PRODUCT_TITLE);
        });

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_OPT_PARTIALLY_FAIL_ONIX30)
                .getScheduledConfirmationMsgElm().shouldBe(visible);

        String distrPath = onixProductOptPass.toAsset().getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onixProductOptPass.getParentDir());
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

        String tagPathCad = ".//*[child::*[local-name()='j152' and text()='CAD']]";
        assertThat(readXmlFile(downloadedOnix.getFilePath()).hasNode(tagPathCad)).as("Optimization has not applied.").isTrue();

        String tagPathUsd = ".//*[child::*[local-name()='j152' and text()='USD']]";
        assertThat(readXmlFile(downloadedOnix.getFilePath()).hasNode(tagPathUsd)).as("Optimization has not applied.").isFalse();

        AssetDistrStatusDetailsDB assetDistrStatusDetails = onixProductOptFail.toAsset()
                .getDistrStatusDetailsForPackingWhenExistsTo(channelAndCA.getChannelName());
        assertThat(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId())
                .as("Optimization file is broken, the failed packaging is expected")
                .isEqualTo(PACKAGE_FAIL.getId());

        assertThat(assetDistrStatusDetails.getProcessingErrors())
                .as("Optimization file is broken, the processing error is expected")
                .contains(SAX_EXCEPTION_INVALID_NOT_COMPLETE_SUPPLY_DETAIL.getErrorText());
    }

    @TmsLink("AUT-793")
    @Test(groups = {"optimization"})
    public void verifyPartiallyFailOptimizationOnix21() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPPartiallyFailOptOnix21,
                regressionChannelAssociationSFTPForOptPartiallyFailOnix21)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_PARTIALLY_FAIL_ONIX21);

        OnixTestService onixProductOptPass = new OnixTestService("regression/verifyPartiallyFailOptimizationOnix21/9780061851637.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onixProductOptFail = new OnixTestService("regression/verifyPartiallyFailOptimizationOnix21/9781433521684.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        Arrays.asList(onixProductOptPass, onixProductOptFail).forEach(onix -> {
            OnixProduct product = onix.getSingleProduct();
            final String PRODUCT_TITLE = product.getTitle();
            waitOnProductInDbBy(PRODUCT_TITLE);
        });

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_OPT_PARTIALLY_FAIL_ONIX21)
                .getScheduledConfirmationMsgElm().shouldBe(visible);

        String distrPath = onixProductOptPass.toAsset().getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onixProductOptPass.getParentDir());
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

        String tagPathCad = ".//*[child::*[local-name()='j152' and text()='CAD']]";
        assertThat(readXmlFile(downloadedOnix.getFilePath()).hasNode(tagPathCad)).as("Optimization has not applied.").isTrue();

        String tagPathUsd = ".//*[child::*[local-name()='j152' and text()='USD']]";
        assertThat(readXmlFile(downloadedOnix.getFilePath()).hasNode(tagPathUsd)).as("Optimization has not applied.").isFalse();

        AssetDistrStatusDetailsDB assetDistrStatusDetails = onixProductOptFail.toAsset()
                .getDistrStatusDetailsForPackingWhenExistsTo(channelAndCA.getChannelName());
        assertThat(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId())
                .as("Optimization file is broken, the failed packaging is expected")
                .isEqualTo(PACKAGE_FAIL.getId());

        assertThat(assetDistrStatusDetails.getProcessingErrors())
                .as("Optimization file is broken, the processing error is expected")
                .contains(SAX_EXCEPTION_INVALID_NOT_COMPLETE_SUPPLY_DETAIL.getErrorText());
    }

    @TmsLink("AUT-765")
    @Test(groups = {"optimization"})
    public void verifyOptimizationMetadataActivityOnix21() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectSecondTestAccountAndBu();

        List<MetadataActivityPhase> xmlFileMetadataActivityPhases = new ArrayList<>(
                Arrays.asList(MetadataActivityPhase.INGEST,
                        MetadataActivityPhase.OPTIMIZATION,
                        MetadataActivityPhase.DISTRIBUTION));

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOptMetadataActivityOnix21,
                regressionChannelAssociationSFTPForOptMetadataActivityOnix21)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_METADATA_ACTIVITY);

        OnixTestService onix = new OnixTestService("regression/verifyOptimizationMetadataActivityOnix21/9780000001922.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds2)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        String onixFileName = onix.getFilePath().getFileName().toString();

        onix.toAssets().forEach(AssetTestService::updateOnixDistributionDateAndIntervalForCA);

        getProductAPIService().triggerAutoDistribution();

        xmlFileMetadataActivityPhases.forEach(phase->getProductAPIService().waitOnCompleteStatusForFile(onixFileName, phase));

        MetadataActivityPage metadataActivityPage = getTopMenu().clickManageMetadata();

        metadataActivityPage.getStatusIconOfPhaseElm(onixFileName, MetadataActivityPhase.INGEST).shouldBe(visible);

        metadataActivityPage.getCompletionSummaryElm(onixFileName).shouldHave(text(onixFileName));
        metadataActivityPage.getAllAssetsProceededElm(onixFileName).shouldBe(visible);

        //normalization phase
        metadataActivityPage.getStatusIconOfPhaseElm(onixFileName, MetadataActivityPhase.NORMALIZATION).shouldNotBe(visible);

        //ingest phase
        PhaseTrackMasterDB ingestPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.INGEST);

        metadataActivityPage.getNumberOfProductsElm(onixFileName)
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())))
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getSuccessCount())));

        metadataActivityPage.getNumberOfIncomingProductsElm(onixFileName)
                .shouldHave(text(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())));

        assertThat(ingestPhaseTrackMasterDB.getFailCount())
                .as("Invalid number of created products from ingested file.")
                .isZero();

        //optimization phase
        PhaseTrackMasterDB optimizationPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.OPTIMIZATION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(onixFileName, MetadataActivityPhase.OPTIMIZATION))
                .as("Invalid optimization fail count.")
                .isEqualTo(String.valueOf(optimizationPhaseTrackMasterDB.getFailCount()));
        assertThat(optimizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid optimization phase track target count.")
                .isNotZero();
        assertThat(optimizationPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid optimization phase track success count.")
                .isNotZero();

        //distribution phase
        PhaseTrackMasterDB distrPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.DISTRIBUTION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(onixFileName, MetadataActivityPhase.DISTRIBUTION))
                .as("Invalid number of failures of distribution phase.")
                .isEqualTo(String.valueOf(distrPhaseTrackMasterDB.getFailCount()));
        assertThat(distrPhaseTrackMasterDB.getTargetCount())
                .as("Invalid distribution phase track target count.")
                .isNotZero();
        assertThat(distrPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid distribution phase track success count.")
                .isNotZero();

    }

    @TmsLink("AUT-823")
    @Test(groups = {"optimization"})
    public void verifyOptimizationMetadataActivityOnix30() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectSecondTestAccountAndBu();

        List<MetadataActivityPhase> xmlFileMetadataActivityPhases = new ArrayList<>(
                Arrays.asList(MetadataActivityPhase.INGEST,
                        MetadataActivityPhase.OPTIMIZATION,
                        MetadataActivityPhase.DISTRIBUTION));

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOptMetadataActivityOnix30,
                regressionChannelAssociationSFTPForOptMetadataActivityOnix30)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_METADATA_ACTIVITY_30_ADD_RULE);

        OnixTestService onix = new OnixTestService("regression/verifyOptimizationMetadataActivityOnix30/9780000001959.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds2)
                .waitOnProductsInDB();

        OnixProduct firstProduct = onix.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        String onixFileName = onix.getFilePath().getFileName().toString();

        onix.toAssets().forEach(AssetTestService::updateOnixDistributionDateAndIntervalForCA);

        getProductAPIService().triggerAutoDistribution();

        xmlFileMetadataActivityPhases.forEach(phase -> getProductAPIService().waitOnCompleteStatusForFile(onixFileName, phase));

        MetadataActivityPage metadataActivityPage = getTopMenu().clickManageMetadata();

        metadataActivityPage.getStatusIconOfPhaseElm(onixFileName, MetadataActivityPhase.INGEST).shouldBe(visible);

        metadataActivityPage.getCompletionSummaryElm(onixFileName).shouldHave(text(onixFileName));
        metadataActivityPage.getAllAssetsProceededElm(onixFileName).shouldBe(visible);

        //normalization phase
        metadataActivityPage.getStatusIconOfPhaseElm(onixFileName, MetadataActivityPhase.NORMALIZATION).shouldNotBe(visible);

        //ingest phase
        PhaseTrackMasterDB ingestPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.INGEST);

        metadataActivityPage.getNumberOfProductsElm(onixFileName)
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())))
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getSuccessCount())));

        metadataActivityPage.getNumberOfIncomingProductsElm(onixFileName)
                .shouldHave(text(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())));

        assertThat(ingestPhaseTrackMasterDB.getFailCount())
                .as("Invalid number of created products from ingested file.")
                .isZero();

        //optimization phase
        PhaseTrackMasterDB optimizationPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.OPTIMIZATION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(onixFileName, MetadataActivityPhase.OPTIMIZATION))
                .as("Invalid optimization fail count.")
                .isEqualTo(String.valueOf(optimizationPhaseTrackMasterDB.getFailCount()));
        assertThat(optimizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid optimization phase track target count.")
                .isNotZero();
        assertThat(optimizationPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid optimization phase track success count.")
                .isNotZero();

        //distribution phase
        PhaseTrackMasterDB distrPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.DISTRIBUTION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(onixFileName, MetadataActivityPhase.DISTRIBUTION))
                .as("Invalid number of failures of distribution phase.")
                .isEqualTo(String.valueOf(distrPhaseTrackMasterDB.getFailCount()));
        assertThat(distrPhaseTrackMasterDB.getTargetCount())
                .as("Invalid distribution phase track target count.")
                .isNotZero();
        assertThat(distrPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid distribution phase track success count.")
                .isNotZero();

    }

    @TmsLink("AUT-809")
    @Test(groups = {"optimization"})
    public void verifyOptimization21FailedPathMetadataActivity() {
        List<MetadataActivityPhase> zipFileMetadataActivityPhases = new ArrayList<>(
                Arrays.asList(MetadataActivityPhase.INGEST,
                        MetadataActivityPhase.OPTIMIZATION,
                        MetadataActivityPhase.DISTRIBUTION));

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectSecondTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOpt21FailedMetadataActivityPath,
                regressionChannelAssociationSFTPForOpt21FailedMetadataActivityPath)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_NORM_21_FAILED_METADATA_ACTIVITY_PATH_SUBSTITUTE_INVALID);

        OnixTestService onix = new OnixTestService("regression/verifyOptimization21FailedPathMetadataActivity/9780000011989.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds2)
                .waitOnProductsInDB();

        onix.getProducts().forEach(product -> waitOnProductInDbBy(product.getTitle()));

        String onixFileName = onix.getFilePath().getFileName().toString();

        onix.toAssets().forEach(AssetTestService::updateOnixDistributionDateAndIntervalForCA);

        getProductAPIService().triggerAutoDistribution();

        zipFileMetadataActivityPhases.forEach(phase -> getProductAPIService().waitOnCompleteStatusForFile(onixFileName, phase));

        MetadataActivityPage metadataActivityPage = getTopMenu().clickManageMetadata();

        metadataActivityPage.getStatusIconOfPhaseElm(onixFileName, MetadataActivityPhase.INGEST).shouldBe(visible);

        metadataActivityPage.getCompletionSummaryElm(onixFileName).shouldHave(text(onixFileName));
        metadataActivityPage.getAllAssetsProceededElm(onixFileName).shouldBe(visible);

        //normalization phase
        metadataActivityPage.getStatusIconOfPhaseElm(onixFileName, MetadataActivityPhase.NORMALIZATION).shouldNotBe(visible);

        //ingest phase
        PhaseTrackMasterDB ingestPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.INGEST);

        metadataActivityPage.getNumberOfProductsElm(onixFileName)
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())))
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getSuccessCount())));

        metadataActivityPage.getNumberOfIncomingProductsElm(onixFileName)
                .shouldHave(text(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())));

        assertThat(ingestPhaseTrackMasterDB.getFailCount())
                .as("Invalid number of created products from ingested file.")
                .isZero();

        //optimization phase
        final int expectedFailOptimizationCount = 2;

        PhaseTrackMasterDB optimizationPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.OPTIMIZATION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(onixFileName, MetadataActivityPhase.OPTIMIZATION))
                .as("Invalid Optimization fail count.")
                .isEqualTo(String.valueOf(optimizationPhaseTrackMasterDB.getFailCount()));

        assertThat(optimizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid optimization phase track target count.")
                .isGreaterThanOrEqualTo(expectedFailOptimizationCount);

        assertThat(optimizationPhaseTrackMasterDB.getFailCount())
                .as("Invalid optimization phase track success count.")
                .isGreaterThanOrEqualTo(expectedFailOptimizationCount);
    }

    @TmsLink("AUT-823")
    @Test(groups = {"optimization"})
    public void verifyOptimization30FailedPathMetadataActivity() {
        List<MetadataActivityPhase> zipFileMetadataActivityPhases = new ArrayList<>(
                Arrays.asList(MetadataActivityPhase.INGEST,
                        MetadataActivityPhase.OPTIMIZATION,
                        MetadataActivityPhase.DISTRIBUTION));

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService().selectSecondTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPOpt30FailedMetadataActivityPath,
                regressionChannelAssociationSFTPForOpt30FailedMetadataActivityPath)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_30_FAILED_METADATA_ACTIVITY_PATH_SUBSTITUTE_INVALID);

        OnixTestService onix = new OnixTestService("regression/verifyOptimization30FailedMetadataActivity/9780000001019.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds2)
                .waitOnProductsInDB();

        onix.getProducts().forEach(product -> waitOnProductInDbBy(product.getTitle()));

        String onixFileName = onix.getFilePath().getFileName().toString();

        onix.toAssets().forEach(AssetTestService::updateOnixDistributionDateAndIntervalForCA);

        getProductAPIService().triggerAutoDistribution();

        zipFileMetadataActivityPhases.forEach(phase -> getProductAPIService().waitOnCompleteStatusForFile(onixFileName, phase));

        MetadataActivityPage metadataActivityPage = getTopMenu().clickManageMetadata();

        metadataActivityPage.getStatusIconOfPhaseElm(onixFileName, MetadataActivityPhase.INGEST).shouldBe(visible);

        metadataActivityPage.getCompletionSummaryElm(onixFileName).shouldHave(text(onixFileName));
        metadataActivityPage.getAllAssetsProceededElm(onixFileName).shouldBe(visible);

        //normalization phase
        metadataActivityPage.getStatusIconOfPhaseElm(onixFileName, MetadataActivityPhase.NORMALIZATION).shouldNotBe(visible);

        //ingest phase
        PhaseTrackMasterDB ingestPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.INGEST);

        metadataActivityPage.getNumberOfProductsElm(onixFileName)
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())))
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getSuccessCount())));

        metadataActivityPage.getNumberOfIncomingProductsElm(onixFileName)
                .shouldHave(text(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())));

        assertThat(ingestPhaseTrackMasterDB.getFailCount())
                .as("Invalid number of created products from ingested file.")
                .isZero();

        //optimization phase
        final int expectedFailOptimizationCount = 2;

        PhaseTrackMasterDB optimizationPhaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(onixFileName, MetadataActivityPhase.OPTIMIZATION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(onixFileName, MetadataActivityPhase.OPTIMIZATION))
                .as("Invalid Optimization fail count.")
                .isEqualTo(String.valueOf(optimizationPhaseTrackMasterDB.getFailCount()));

        assertThat(optimizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid optimization phase track target count.")
                .isGreaterThanOrEqualTo(expectedFailOptimizationCount);

        assertThat(optimizationPhaseTrackMasterDB.getFailCount())
                .as("Invalid optimization phase track success count.")
                .isGreaterThanOrEqualTo(expectedFailOptimizationCount);
    }
}
