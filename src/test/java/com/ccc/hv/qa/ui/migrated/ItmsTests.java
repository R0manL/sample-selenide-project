package com.ccc.hv.qa.ui.migrated;

import com.ccc.hv.qa.ui.pojos.ChannelAssociation;
import com.ccc.hv.qa.ui.pojos.ChannelPublic;
import com.ccc.hv.qa.ui.pojos.MetadataOptimization;
import com.codeborne.selenide.CollectionCondition;
import com.ccc.hv.qa.db.enums.AssetDistributionStatus;
import com.ccc.hv.qa.db.enums.AssetProcessingError;
import com.ccc.hv.qa.db.pojo.AssetDistrStatusDetailsDB;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.enums.AssetType;
import com.ccc.hv.qa.ui.pages.ProductDetailsPage;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.ui.services.ChannelAndCATestService;
import com.ccc.hv.qa.ui.services.ContentTestService;
import com.ccc.hv.qa.ui.services.OnixTestService;
import io.qameta.allure.TmsLink;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.util.*;

import static com.codeborne.selenide.Condition.visible;
import static com.ccc.hv.qa.db.enums.AssetDistributionStatus.PACKAGE_FAIL;
import static com.ccc.hv.qa.file.services.XmlFileService.readXmlFile;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.*;
import static com.ccc.hv.qa.ui.data.Channels.*;
import static com.ccc.hv.qa.ui.data.MetadataOptimizations.*;
import static com.ccc.hv.qa.ui.data.PredUsers.SUPER_ADMIN;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@Test(groups = {"ui"})
public class ItmsTests extends UITestBase {

    @DataProvider(name = "relaxNGValidationTestData")
    private Object[][] relaxNGValidationTestData() {
        return new Object[][]{
                {"regression/verifyAppleMetaRelaxNGValidation/9781292084572.xml",
                        regressionPubChnlITMSForAppleMetaRelaxNGValidation21,
                        regressionChannelAssociationITMSForRelaxNGValidation21
                },
                {"regression/verifyAppleMetaRelaxNGValidation/ONIX3_short_Test_Product-fails_Apple_validation_20220203.xml",
                        regressionPubChnlITMSForRelaxNGValidation30,
                        regressionChannelAssociationITMSForRelaxNGValidation30
                }};
    }

    @TmsLink("AUT-717")
    @Test(dataProvider = "relaxNGValidationTestData", groups = "itms")
    public void verifyAppleMetaRelaxNGValidation(@NotNull String onixFilePath,
                                                 ChannelPublic channel,
                                                 ChannelAssociation ca) {
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
                .as("Relax ng validation fail has expected.")
                .isEqualTo(AssetProcessingError.RELAX_NG_APPLE_FAIL_VALIDATION.getErrorText());
    }

    @DataProvider(name = "packagingIssuesTestData")
    private Object[][] packagingIssuesTestData() {
        return new Object[][]{
                {"regression/verifyAppleMetaAppleConverterProcessPackagingIssues/9781292002830.xml",
                        regressionPubChnlITMSForAppleConverterProcessPackagingIssue21,
                        regressionChannelAssociationITMSForAppleConverterProcessPackagingIssue21,
                        REGRESSION_APPLE_META_OPT_PROCESS_ISSUES_21
                },
                {"regression/verifyAppleMetaAppleConverterProcessPackagingIssues/9780807596490.xml",
                        regressionPubChnlITMSForAppleConverterProcessPackagingIssue30,
                        regressionChannelAssociationITMSForAppleConverterProcessPackagingIssue30,
                        REGRESSION_APPLE_META_OPT_PROCESS_ISSUES_30
                }};
    }

    @TmsLink("AUT-718")
    @Test(dataProvider = "packagingIssuesTestData", groups = "itms")
    public void verifyAppleMetaAppleConverterProcessPackagingIssues(@NotNull String onixFilePath,
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
                .as("Relax ng validation fail has expected.")
                .isEqualTo(AssetProcessingError.RELAX_NG_APPLE_FAIL_VALIDATION.getErrorText());
    }

    @TmsLink("AUT-719")
    @Test(groups = {"itms"})
    public void verifyAppleMetaTransportationDistrIssuesOnix21() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSForAppleMetaTransportationDistrIssues21,
                regressionChannelAssociationITMSForAppleMetaTransportationDistrIssues21).createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_APPLE_META_TRANSPORTATION_DISTR_ISSUES_21);

        OnixTestService onix = new OnixTestService("regression/verifyAppleMetaTransportationDistrIssuesOnix21/9781292002831.xml")
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
                .getDistrStatusDetailsWhenExistsTo(channelAndCA.getChannelName());

        assertThat(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId())
                .as("Relax ng validation fail has expected.")
                .isEqualTo(AssetDistributionStatus.DISTRIB_FAIL.getId());

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAppleMetadataLinkFor(channelAndCA.getAssetTypeFromSingleMetadata()).shouldBe(visible);
    }

    @TmsLink("AUT-719")
    @Test(groups = {"itms"})
    public void verifyAppleMetaTransportationDistrIssues30() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSForAppleMetaTransportationDistrIssues30,
                regressionChannelAssociationITMSForAppleMetaTransportationDistrIssues30).createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_APPLE_META_TRANSPORTATION_DISTR_ISSUES_30);

        OnixTestService onix = new OnixTestService("regression/verifyAppleMetaTransportationDistrIssuesOnix30/9780807596491.xml")
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

        assertThat(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId())
                .as("Packaging fail has expected.")
                .isEqualTo(PACKAGE_FAIL.getId());

        assertThat(assetDistrStatusDetails.getProcessingErrors())
                .as("'There was a problem with optimization ONIX for Apple distribution' error has expected.")
                .isEqualTo(AssetProcessingError.OPTIMIZATION_PROBLEM_FOR_APPLE_DISTR.getErrorText());
    }

    @DataProvider(name = "releaseTypeData")
    private Object[][] releaseTypeData() {
        return new Object[][]{
                {"regression/verifyAppleMetaOnix30CAReleaseTypeTag/TEST-Digital-Only-Reference-new-release.xml",
                        regressionPubChnlITMSWithReleaseTypeNewReleaseOnix30,
                        regressionChannelAssociationITMSWithReleaseTypeNewReleaseOnix30,
                        "new-release"
                },
                {"regression/verifyAppleMetaOnix30CAReleaseTypeTag/TEST-Digital-Only-Reference-digital-only.xml",
                        regressionPubChnlITMSWithReleaseTypeDigitalOnlyOnix30,
                        regressionChannelAssociationITMSWithReleaseTypeDigitalOnlyOnix30,
                        "digital-only"
                }};
    }

    @TmsLink("AUT-728")
    @Test(dataProvider = "releaseTypeData", groups = "itms")
    public void verifyAppleMetaOnix30CAReleaseTypeTag(@NotNull String onixFilePath,
                                                      ChannelPublic channel,
                                                      ChannelAssociation ca,
                                                      @NotNull String releaseTypeValue) {
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

        String sectionRootTemplate = ".//*[local-name()='product'][child::*[local-name()='territory' and text()='%s']]";
        Arrays.asList("AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ",
                "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HN", "HU", "IE", "IT",
                "JP", "LT", "LU", "LV", "MT", "MX", "NI", "NL", "NO", "NZ", "PA", "PE", "PL", "PT", "PY",
                "RO", "SE", "SI", "SK", "SV", "US").forEach(territory -> {
            String gbPriceReleaseTypeTag = String.format(sectionRootTemplate, territory) + "/*[local-name()='release_type' and text()='" + releaseTypeValue + "']";
            assertThat(readXmlFile(metadataFilePath).hasNode(gbPriceReleaseTypeTag))
                    .as("ReleaseType Apple CA is not applied.").isTrue();
        });
    }

    @TmsLink("AUT-739")
    @Test(groups = {"itms"})
    public void verifyAppleMetaLinkWithOnix21AndProductType() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSForAppleMetaLinkWithOnixAndProductType21,
                regressionChannelAssociationITMSForAppleMetaLinkWithOnixAndProductType21).createChannelWithCA();

        final String onixSuffix = generateUniqueStringBasedOnDate();
        OnixTestService onix21 = new OnixTestService("regression/verifyAppleMetaLinkWithOnix21AndCollateral/9780000001009_ONIX_2_1.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferencesWithSuffix(onixSuffix)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onix30 = new OnixTestService("regression/verifyAppleMetaLinkWithOnix21AndCollateral/9780000001009.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferencesWithSuffix(onixSuffix)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        new ContentTestService("regression/verifyAppleMetaLinkWithOnix21AndCollateral/9780000001009_marketingimage.jpg")
                .cloneFileWith(onix30.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix30.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        onix21.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        ProductDetailsPage page = getTopMenu()
                .searchPresentProductBy(onix30.getSingleProductTitle())
                .openProductDetailsPage();

        page.getAppleMetadataLinkFor(AssetType.MARKETING_IMAGE).shouldBe(visible);
        page.getAppleMetadataLinkFor(AssetType.ONIX21).shouldBe(visible);
        page.getAppleMetadataLinkFor(AssetType.ONIX30).shouldNotBe(visible);
    }

    @TmsLink("AUT-739")
    @Test(groups = {"itms"})
    public void verifyAppleMetaLinkWithOnix30AndCollateral() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChnlITMSForAppleMetaLinkWithOnixAndProductType30,
                regressionChannelAssociationITMSForAppleMetaLinkWithOnixAndProductType30).createChannelWithCA();

        final String onixSuffix = generateUniqueStringBasedOnDate();

        OnixTestService onix30 = new OnixTestService("regression/verifyAppleMetaLinkWithOnix30AndContent/9780807595565_ONIX_3_0.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferencesWithSuffix(onixSuffix)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        new ContentTestService("regression/verifyAppleMetaLinkWithOnix30AndContent/9780807595565.epub")
                .cloneFileWith(onix30.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix30.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(AssetType.ONIX30, AssetType.EPUB)
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        onix30.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        ProductDetailsPage page = getTopMenu()
                .searchPresentProductBy(onix30.getSingleProductTitle())
                .openProductDetailsPage();

        page.getAppleMetadataLinkFor(AssetType.EPUB).shouldBe(visible);
        page.getAppleMetadataLinkFor(AssetType.ONIX30).shouldBe(visible);
        page.getAllAssetElements().shouldHave(CollectionCondition.size(2));
    }

    @DataProvider(name = "twoAdditionalTitleConditions")
    private Object[][] twoAdditionalTitleConditions() {
        return new Object[][]{
                {"regression/verifyAppleMetaConverterHandlesTwoAdditionalTitleConditions/Test_Apple_30_Prefix_Short.xml",
                        regressionPubChnlITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions1,
                        regressionChannelAssociationITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions1
                },
                {"regression/verifyAppleMetaConverterHandlesTwoAdditionalTitleConditions/Test_Apple_30_NoPrefix_Short.xml",
                        regressionPubChnlITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions2,
                        regressionChannelAssociationITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions2
                }};
    }

    @TmsLink("AUT-746")
    @Test(dataProvider = "twoAdditionalTitleConditions", groups = {"itms"})
    public void verifyAppleMetaConverterHandlesTwoAdditionalTitleConditions(@NotNull String onixFilePath,
                                                                            ChannelPublic channel,
                                                                            ChannelAssociation ca) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix30 = new OnixTestService(onixFilePath)
                .readOnixFile()
                .updateTitlesWithoutPrefix()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        String productTitle = onix30.getSingleProductTitleWithPrefix();
        getTopMenu()
                .searchPresentProductBy(productTitle)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        onix30.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        Path appleMetaFilePath = getTopMenu()
                .searchPresentProductBy(productTitle)
                .openProductDetailsPage()
                .clickAppleMetadataLink()
                .downloadAppleMetadataFile();

        String expectedXpath = ".//*[local-name()='book']/*[local-name()='metadata']" +
                "/*[local-name()='title' and text()='" + productTitle + "']";

        assertThat(readXmlFile(appleMetaFilePath).hasNode(expectedXpath))
                .as("No product's title after apple metadata converter.")
                .isTrue();
    }
}
