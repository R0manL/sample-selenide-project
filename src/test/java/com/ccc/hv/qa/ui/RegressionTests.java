package com.ccc.hv.qa.ui;

import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pages.*;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.ui.services.*;
import com.codeborne.selenide.*;
import com.google.common.collect.ImmutableMap;
import com.ccc.hv.qa.core.CustomCollectors;
import com.ccc.hv.qa.db.enums.AssetDistributionStatus;
import com.ccc.hv.qa.db.pojo.AssetDistrStatusDetailsDB;
import com.ccc.hv.qa.db.pojo.AssetPrecompDetailsDB;
import com.ccc.hv.qa.db.pojo.ChannelAssociationDetails;
import com.ccc.hv.qa.db.services.AccountDBService;
import com.ccc.hv.qa.file.pojo.OnixProduct;
import com.ccc.hv.qa.ui.data.AssociationSrvs;
import com.ccc.hv.qa.ui.data.PredAccounts;
import com.ccc.hv.qa.ui.data.PredBUs;
import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pages.*;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.ui.services.*;
import com.ccc.hv.qa.utils.enums.DatePattern;
import io.qameta.allure.TmsLink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.*;
import static com.ccc.hv.qa.api.services.EventAPIService.getEventAPIService;
import static com.codeborne.selenide.Selenide.open;
import static com.ccc.hv.qa.api.services.ProductAPIService.getProductAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.enums.AssetDistributionStatus.DISTRIB_SUCCESS;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.*;
import static com.ccc.hv.qa.db.services.ChannelDBService.*;
import static com.ccc.hv.qa.db.services.ProductDBService.*;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds;
import static com.ccc.hv.qa.ui.data.BatchODDEntities.*;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.*;
import static com.ccc.hv.qa.ui.data.Channels.*;
import static com.ccc.hv.qa.ui.data.CreateTagEntities.*;
import static com.ccc.hv.qa.ui.data.PredUsers.*;
import static com.ccc.hv.qa.ui.data.TestProducts.*;
import static com.ccc.hv.qa.ui.enums.FeedPathOption.CHANNEL_NAME;
import static com.ccc.hv.qa.ui.pages.AssociatePage.getChannelAssociatePage;
import static com.ccc.hv.qa.ui.pages.CurrentActivityFeed.TITLE;
import static com.ccc.hv.qa.ui.pages.CurrentActivityFeed.getCurrentActivityFeed;
import static com.ccc.hv.qa.ui.pages.HomePage.getHomePage;
import static com.ccc.hv.qa.ui.pages.LinkEventsPage.getLinkEventsPage;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.ManageEventsPage.getManageEventsPage;
import static com.ccc.hv.qa.ui.pages.NavigationBar.getNavigationBar;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.FileTestService.getFileTestService;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static com.ccc.hv.qa.utils.DateTimeUtils.*;
import static com.ccc.hv.qa.utils.FileOpsUtils.*;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;
import static java.time.LocalDate.now;
import static org.apache.commons.io.FilenameUtils.EXTENSION_SEPARATOR;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.awaitility.Awaitility.await;


//@Report
//@Listeners(TextReport.class)
@Test(groups = {"regression", "ui"})
public class RegressionTests extends UITestBase {

    @Test(groups = {"singleODD", "negative"})
    public void verifySingleODDValidationCheck() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPSForSingleODDValidationCheck,
                regressionChannelAssociationFTPSForSingleODDValidationCheck)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySingleODDValidationCheck/9781424551378.xml")
                .readOnixFile()
                .updateTitles()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifySingleODDValidationCheck/assets/9781424551378.epub")
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifySingleODDValidationCheck/assets/9781424551378_marketingimage.jpg")
                .uploadToCrushFTP(crushFtpUploadCreds);

        DistributionConfirmPage distributionConfirmPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(AssetType.ONIX21, AssetType.MARKETING_IMAGE, AssetType.EPUB)
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles();

        Arrays.asList(onix.getFileNameWithRecRef(), content.getFileName(), collateral.getFileName())
                .forEach(assetName -> assertThat(distributionConfirmPage
                        .isDistributionAllowedTo(channelAndCA.getChannelName(), assetName))
                        .as("Invalid distribution message has been shown.")
                        .isFalse());

        assertThat(distributionConfirmPage.isOkButtonEnabled())
                .as("Invalid state (enabled) of [Ok] button.")
                .isFalse();
    }

    @Test(groups = {"singleODD"})
    public void verifySingleODDToMultipleChannels() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCAPrint = new ChannelAndCATestService(
                regressionPublicChannelFTPForSingleODDToMultipleChannelsPrint,
                regressionChannelAssociationFTPForSingleODDToMultipleChannelsPrint)
                .createChannelWithCA();

        ChannelAndCATestService channelAndCAEpub = new ChannelAndCATestService(
                regressionPublicChannelFTPForSingleODDToMultipleChannelsEpub,
                regressionChannelAssociationFTPForSingleODDToMultipleChannelsEpub)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySingleODDToMultipleChannels/9781424555475.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifySingleODDToMultipleChannels/assets/9781424555475.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifySingleODDToMultipleChannels/assets/9781424555475_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        DistributionConfirmPage distributionConfirmPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(AssetType.ONIX21, AssetType.MARKETING_IMAGE, AssetType.EPUB)
                .clickDistribute()
                .selectChannelsBy(channelAndCAPrint.getChannelName(), channelAndCAEpub.getChannelName())
                .clickSendFiles();

        distributionConfirmPage.getAllDistributionChannelTables().shouldHave(CollectionCondition.size(2));

        String updatedOnixFileName = onix.getFileNameWithRecRef();
        List<String> assetNames = Arrays.asList(updatedOnixFileName, content.getFileName(), collateral.getFileName());
        assetNames.forEach(assetName -> {
            assertThat(distributionConfirmPage.isDistributionAllowedTo(channelAndCAPrint.getChannelName(), assetName))
                    .as("Invalid distribution message has been shown.")
                    .isFalse();

            assertThat(distributionConfirmPage.isDistributionAllowedTo(channelAndCAEpub.getChannelName(), assetName))
                    .as("Invalid distribution message has been shown.")
                    .isTrue();
        });

        distributionConfirmPage.getOkButton().click();

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCAEpub.getChannelName()));
    }

    @Test(groups = {"singleODD"})
    public void verifySingleODDMixedAllowedAndNotAllowedAssets() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForSingleODDToMixedAssets,
                regressionChannelAssociationFTPForSingleODDToMixedAssets)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySingleODDMixedAllowedAndNotAllowedAssets/9780547525341.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifySingleODDMixedAllowedAndNotAllowedAssets/assets/9780547525341.pdf");
        content.cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifySingleODDMixedAllowedAndNotAllowedAssets/assets/9780547525341_marketingimage.jpg");
        collateral.cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        DistributionConfirmPage distributionConfirmPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleMetadata(),
                        AssetType.WEB_OPTIMIZED_PDF,
                        channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles();

        assertThat(distributionConfirmPage.isDistributionAllowedTo(channelAndCA.getChannelName(), content.getFileName()))
                .as("Invalid distribution message has been shown.")
                .isFalse();

        assertThat(distributionConfirmPage.isDistributionAllowedTo(channelAndCA.getChannelName(), onix.getFileNameWithRecRef()))
                .as("Invalid distribution message has been shown.")
                .isTrue();

        assertThat(distributionConfirmPage.isDistributionAllowedTo(channelAndCA.getChannelName(), collateral.getFileName()))
                .as("Invalid distribution message has been shown.")
                .isTrue();

        distributionConfirmPage.getOkButton().click();

        AssetTestService onixAsset = onix.toAsset();
        onixAsset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
        collateral.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @Test(groups = {"singleODD", "distributionPathOptions"})
    public void verifyDistrByAdvKeywordUsingPubName() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForPublisherName,
                regressionChannelAssociationSFTPForPublisherName)
                .createChannelWithCA();

        Arrays.asList(theNavigatorsPubTestProduct, tyndaleHousePublishersIncProduct, focusOnTheFamilyTestProduct)
                .forEach(testProduct -> {
                    OnixTestService onix = new OnixTestService(testProduct.getOnixFilePath())
                            .readOnixFile()
                            .updateTitles()
                            .updateRecordReferences()
                            .saveAsNewFile()
                            .uploadToCrushFTP(crushFtpUploadCreds)
                            .waitOnProductsInDB();

                    AssetTestService content = new ContentTestService(testProduct.getContentFilePath())
                            .cloneFileWith(onix.getSingleProductRecordReference())
                            .uploadToCrushFTP(crushFtpUploadCreds);

                    AssetTestService collateral = new CollateralTestService(testProduct.getCollateralFilePath())
                            .cloneFileWith(onix.getSingleProductRecordReference())
                            .uploadToCrushFTP(crushFtpUploadCreds);

                    getTopMenu()
                            .searchPresentProductBy(onix.getSingleProductTitle())
                            .openProductDetailsPage()
                            .selectAssetsBy(
                                    channelAndCA.getAssetTypeFromSingleMetadata(),
                                    channelAndCA.getAssetTypeFromSingleContent(),
                                    channelAndCA.getAssetTypeFromSingleCollateral())
                            .clickDistribute()
                            .selectChannelsBy(channelAndCA.getChannelName())
                            .clickSendFiles()
                            .completeSuccessfulDistribution();

                    Arrays.asList(onix.toAsset(), content, collateral).forEach(asset -> {
                        AssetDistrStatusDetailsDB assetDistrDetails = asset.getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName());
                        String distrPath = assetDistrDetails.getDistributedPath();
                        String pathTemplate = channelAndCA.getSingleMetadata().getDestinationFolder();

                        String publisher = getPathFormatOptionValueFrom(distrPath, pathTemplate, PathFormatTextOption.PUBLISHER);
                        assertThat(publisher).as("Wrong publisher value in distribution path").isEqualTo(testProduct.getPubReplacementValue());

                        PathFormatDateTimeOption pathFormatOption = PathFormatDateTimeOption.XFER_YYYYMMDDHHMMSSSSS;
                        String dateTime = getPathFormatOptionValueFrom(distrPath, pathTemplate, pathFormatOption);
                        assertThat(convertToLocalDateTime(pathFormatOption, dateTime))
                                .as("Date and time in the distr path is not close to now")
                                .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

                        assertThat(getFileTestService().isFileExistsOnSFTPServer(distrPath))
                                .as("File by path '{}' does not exist on channel two ", distrPath)
                                .isTrue();
                    });
                });
    }

    @Test(groups = {"singleODD", "distributionPathOptions"})
    public void verifyDistributionByAdvancedKeywordUsingImprintName() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForImprintName,
                regressionChannelAssociationSFTPForImprintName)
                .createChannelWithCA();

        Arrays.asList(theAddisonWesleyProfessionalTestProduct, adobePressTestProduct, pearsonTestProduct, prenticeHallTestProduct)
                .forEach(testProduct -> {
                    OnixTestService onix = new OnixTestService(testProduct.getOnixFilePath())
                            .readOnixFile()
                            .updateTitles()
                            .updateRecordReferences()
                            .saveAsNewFile()
                            .uploadToCrushFTP(crushFtpUploadCreds)
                            .waitOnProductsInDB();

                    AssetTestService collateral = new CollateralTestService(testProduct.getCollateralFilePath())
                            .cloneFileWith(onix.getSingleProductRecordReference())
                            .uploadToCrushFTP(crushFtpUploadCreds);

                    List<AssetTestService> assets = new ArrayList<>();
                    assets.add(onix.toAsset());
                    assets.add(collateral);

                    if (testProduct.getContentFilePath() != null) {
                        assets.add(new ContentTestService(testProduct.getContentFilePath())
                                .cloneFileWith(onix.getSingleProductRecordReference())
                                .uploadToCrushFTP(crushFtpUploadCreds));
                    }

                    getTopMenu()
                            .searchPresentProductBy(onix.getSingleProductTitle())
                            .openProductDetailsPage()
                            .selectAssetsBy(assets.stream().map(AssetTestService::getFileName).toArray(String[]::new))
                            .clickDistribute()
                            .selectChannelsBy(channelAndCA.getChannelName())
                            .clickSendFiles()
                            .completeSuccessfulDistribution();

                    assets.forEach(asset -> {
                        String distrPath = asset
                                .getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName())
                                .getDistributedPath();

                        if (distrPath.contains("onix/")) {
                            String pathTemplate = channelAndCA.getSingleMetadata().getDestinationFolder();
                            String imprint = getPathFormatOptionValueFrom(distrPath, pathTemplate, PathFormatTextOption.IMPRINT);
                            assertThat(imprint).as("Wrong imprint value in distribution path").isEqualTo(testProduct.getImprintReplacementValue());

                            PathFormatDateTimeOption pathFormatOption = PathFormatDateTimeOption.XFER_YYYYMMDDHHMMSSSSS;
                            String dateTime = getPathFormatOptionValueFrom(distrPath, pathTemplate, pathFormatOption);
                            assertThat(convertToLocalDateTime(pathFormatOption, dateTime)).as("Date and time in the distr path is not close to now")
                                    .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

                        } else if (distrPath.contains("ebooks/")) {
                            String pathTemplate = channelAndCA.getSingleContent().getDestinationFolder();
                            String imprint = getPathFormatOptionValueFrom(distrPath, pathTemplate, PathFormatTextOption.IMPRINT);
                            assertThat(imprint).as("Wrong imprint value in distribution path").isEqualTo(testProduct.getImprintReplacementValue());

                            String isbn = getPathFormatOptionValueFrom(distrPath, pathTemplate, PathFormatTextOption.ISBN13);
                            final String EXPECTED_ISBN = onix.getSingleProduct().getIsbn13();
                            assertThat(isbn).as("Wrong isbn value in distribution path").isEqualTo(EXPECTED_ISBN);
                        } else {
                            throw new IllegalArgumentException("The root folder is unexpected");
                        }

                        assertThat(getFileTestService().isFileExistsOnSFTPServer(distrPath))
                                .as("File by path '{}' does not exist on channel two ", distrPath)
                                .isTrue();
                    });
                });
    }

    @Test(groups = {"batchODD", "caServerSettings"})
    public void verifySendSingleProductsInSingleOnixFileOption() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForSendSingleProductsInSingleOnixFile,
                regressionCASFTPForSendSingleProductsInSingleOnixFile)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySendSingleProductsInSingleOnixFileOption/9781496450746.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        List<OnixProduct> products = onix.getProducts();
        final int EXPECTED_PRODUCT_QUANTITY_IN_ONIX = 2;
        assertThat(products)
                .as("Invalid number of products in Onix file")
                .hasSize(EXPECTED_PRODUCT_QUANTITY_IN_ONIX);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        List<String> expectedDistrPaths = onix.getFileNamesWithRecRef();
        List<String> actualDistrPaths = onix.toAssets()
                .stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList());

        assertThat(actualDistrPaths)
                .as("Onix file is not distributed as individual file")
                .hasSameElementsAs(expectedDistrPaths);
    }

    @TmsLink("AUT-406")
    @Test(groups = {"batchODD", "caServerSettings"})
    public void verifySendSingleProductsInSingleOnixFileOptionOnix30() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForSendSingleProductsInSingleOnixFileOnix30,
                regressionCASFTPForSendSingleProductsInSingleOnixFileOnix30)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySendSingleProductsInSingleOnixFileOptionOnix30/9780807536788.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        List<OnixProduct> products = onix.getProducts();
        final int EXPECTED_PRODUCT_QUANTITY_IN_ONIX = 2;
        assertThat(products)
                .as("Invalid number of products in Onix file")
                .hasSize(EXPECTED_PRODUCT_QUANTITY_IN_ONIX);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX_ONIX30)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        List<String> expectedDistrPaths = onix.getFileNamesWithRecRef()
                .stream()
                .map(recRef -> "ONIX 3.0" + FILE_PATH_DELIMITER + recRef)
                .collect(Collectors.toList());

        List<String> actualDistrPaths = onix.toAssets()
                .stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList());

        assertThat(actualDistrPaths)
                .as("Onix file is not distributed as individual file")
                .hasSameSizeAs(expectedDistrPaths)
                .hasSameElementsAs(expectedDistrPaths);
    }

    @DataProvider(name = "onsetOffsetDateTestData")
    private Object[][] onsetOffsetDateTestData() {
        return new Object[][]{
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/9781424550135.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9781424550135.epub",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9781424550135_marketingimage.jpg",
                        regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInPastAndOffsetOnix21,
                        regressionCAFTPForAutoDistContCollatWithOnSaleDateAndOffsetInPastForOnix21},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/9781424550135.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9781424550135.epub",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9781424550135_marketingimage.jpg",
                        regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInPastAndOnsetOnix21,
                        regressionCAFTPForAutoDistContCollatWithOnSaleDateInPastAndOnsetForOnix21},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/9780830830589.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9780830830589_print.pdf",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9780830830589_screenshot001.png",
                        regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInPastAndOffsetOnix30,
                        regressionCAFTPForAutoDistContCollatWithOnSaleDateInPastAndOffsetForOnix30},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/9780830830589.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9780830830589_print.pdf",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9780830830589_screenshot001.png",
                        regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInPastAndOnsetOnix30,
                        regressionCAFTPForAutoDistContCollatWithOnSaleDateAndOnsetInPastForOnix30},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/9781635861341_mobi.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9781635861341_mobi.mobi",
                        null,
                        regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInFutureAndOffsetOnix21,
                        regressionCAFTPForAutoDistContCollatWithOnSaleDateInFutureAndOffsetOnix21},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/9781635861341_mobi.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9781635861341_mobi.kpf",
                        null,
                        regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInFutureAndOnsetOnix21,
                        regressionCAFTPForAutoDistContCollatWithOnSaleDateInFutureAndOnsetOnix21},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/9781615196555_search.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9781615196555_search.pdf",
                        null,
                        regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInFutureAndOffsetOnix30,
                        regressionCAFTPForAutoDistContCollatWithOnSaleDateInFutureAndOffsetOnix30},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/9781615196555_search.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate/assets/9781615196555_search.pdf",
                        null,
                        regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInFutureAndOnsetOnix30,
                        regressionCAFTPForAutoDistContCollatWithOnSaleDateInFutureAndOnsetOnix30}
        };
    }

    @DataProvider(name = "onsaledateTestData")
    private Object[][] onsaledateTestData() {
        return new Object[][]{
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/9780134741376.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/assets/9780134741376.epub",
                        null,
                        regressionPubChannelFTPForAutoDistrContentWithOnSaleDateInPastForOnix21,
                        regressionCAFTPForAutoDistrContentWithOnSaleDateInPastForOnix21},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/9781317616009.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/assets/9781317616009.epub",
                        null,
                        regressionPubChannelFTPForAutoDistrContentWithOnSaleDateInPastForOnix30,
                        regressionCAFTPForAutoDistrContentWithOnSaleDateInPastForOnix30},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/9781424556908.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/assets/9781424556908.epub",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/assets/9781424556908_marketingimage.jpg",
                        regressionPubChannelFTPForAutoDistrContentWithOnSaleDateInFutureForOnix21,
                        regressionCAFTPForAutoDistrContentWithOnSaleDateInFutureForOnix21},
                {"regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/9780807505854.xml",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/assets/9780807505854_print.pdf",
                        "regression/verifyAutoDistrContentAndCollateralWithOnSaleDate/assets/9780807505854_marketingimage.jpg",
                        regressionPubChannelFTPForAutoDistrContentWithOnSaleDateInFutureForOnix30,
                        regressionCAFTPForAutoDistrContentWithOnSaleDateInFutureForOnix30}
        };
    }

    @TmsLink("AUT-62")
    @TmsLink("AUT-63")
    @Test(dataProvider = "onsaledateTestData", groups = {"onSaleDate", "autoDistribution"})
    public void verifyAutoDistrContentAndCollateralWithOnSaleDate(@NotNull String onixFilePath,
                                                                  @NotNull String contentFilePath,
                                                                  @Nullable String collateralFilePath,
                                                                  ChannelPublic channel,
                                                                  ChannelAssociation ca) {
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

        AssetPrecompDetailsDB contentPrecompDetails = new ContentTestService(contentFilePath)
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds)
                .getPrecompDetailsWhenExistFor(channel.getName());

        LocalDate dateFromOnix = onix.getSingleProduct().getOnSaleDate();
        LocalDateTime releaseDateFromDB = contentPrecompDetails.getReleaseDateUtc();
        LocalDateTime precomDateFromDB = contentPrecompDetails.getPreComputedDateUtc();

        assertThat(releaseDateFromDB.toLocalDate())
                .as("Product's onSaleDate is not equal to date from Onix.")
                .isEqualTo(dateFromOnix);

        assertThat(releaseDateFromDB)
                .as("Product's release date is not equal to onSaleDate for content asset.")
                .isEqualTo(precomDateFromDB);

        //Upload collateral
        if (collateralFilePath != null) {
            AssetPrecompDetailsDB collateralPrecompDetails = new CollateralTestService(collateralFilePath)
                    .cloneFileWith(onix.getSingleProductRecordReference())
                    .uploadToCrushFTP(crushFtpUploadCreds)
                    .getPrecompDetailsWhenExistFor(channel.getName());

            LocalDate collateralPrecompDateFromDB = collateralPrecompDetails.getPreComputedDateUtc().toLocalDate();

            assertThat(collateralPrecompDateFromDB)
                    .as("Collateral's release date is not TODAY. Note. For collateral onSaleDate does not apply.")
                    .isEqualTo(getServerLocalDateNow());
        }
    }

    @TmsLink("AUT-64")
    @Test(dataProvider = "onsetOffsetDateTestData", groups = {"onsetOffset"})
    public void verifyAutoDistrContentAndCollateralWithOnSaleDateAndOffsetOnsetDate(@NotNull String onixFilePath,
                                                                                    @NotNull String contentFilePath,
                                                                                    @Nullable String collateralFilePath,
                                                                                    ChannelPublic channel,
                                                                                    ChannelAssociation ca) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca).createChannelWithCA();

        OnixTestService onix = new OnixTestService(onixFilePath);
        onix
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService(contentFilePath)
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetPrecompDetailsDB contentPrecompDetails = content.getPrecompDetailsWhenExistFor(channel.getName());
        LocalDate dateFromOnix = onix.getSingleProduct().getOnSaleDate();
        LocalDate releaseDateFromDB = contentPrecompDetails.getReleaseDateUtc().toLocalDate();
        LocalDate precomDateFromDB = contentPrecompDetails.getPreComputedDateUtc().toLocalDate();

        assertThat(releaseDateFromDB).as("Product's onSaleDate is not equal to date from Onix.")
                .isEqualTo(dateFromOnix);

        ChannelAssociationContent caContent = channelAndCA.getSingleContent();
        if (Distribute.BEFORE.equals(caContent.getDistribute())) {
            final int OFFSET = caContent.getDaysBeforeOnSaleDate();
            assertThat(releaseDateFromDB.minusDays(OFFSET))
                    .as("Product's release date is not equal to onSaleDate - offset for content asset.")
                    .isEqualTo(precomDateFromDB);
        }

        if (Distribute.AFTER.equals(caContent.getDistribute())) {
            final int ONSET = caContent.getDaysBeforeOnSaleDate();
            assertThat(releaseDateFromDB.plusDays(ONSET))
                    .as("Product's release date is not equal to onSaleDate + onset for content asset.")
                    .isEqualTo(precomDateFromDB);
        }

        //Upload collateral
        if (collateralFilePath != null) {
            LocalDate collateralPrecompDateFromDB = new CollateralTestService(collateralFilePath)
                    .cloneFileWith(onix.getSingleProductRecordReference())
                    .uploadToCrushFTP(crushFtpUploadCreds)
                    .getPrecompDetailsWhenExistFor(channel.getName())
                    .getPreComputedDateUtc()
                    .toLocalDate();

            assertThat(collateralPrecompDateFromDB)
                    .as("Collateral's release date is not TODAY. Note. For collateral onSaleDate does not apply.")
                    .isEqualTo(getServerLocalDateNow());
        }
    }

    @DataProvider(name = "autoDistrFailureTestData")
    private Object[][] autoDistrFailureTestData() {
        return new Object[][]{
                {"regression/verifyAutoDistributionFailureFTPS/9780547752136.xml",
                        null,
                        "regression/verifyAutoDistributionFailureFTPS/assets/9780547752136_outsidefrontcover.jpg",
                        regressionPublicChannelFTPSForDistributionFailure,
                        regressionChannelAssociationFTPSForDistributionFailure
                },
                {"regression/verifyAutoDistributionFailureSFTP/9780807593080.xml",
                        "regression/verifyAutoDistributionFailureSFTP/assets/9780807593080.epub",
                        "regression/verifyAutoDistributionFailureSFTP/assets/9780807593080_marketingimage.jpg",
                        regressionPublicChannelSFTPForDistributionFailure,
                        regressionChannelAssociationSFTPForDistributionFailure
                }};
    }

    @TmsLink("AUT-65")
    @TmsLink("AUT-66")
    @Test(dataProvider = "autoDistrFailureTestData", groups = {"autoDistribution", "negative"})
    public void verifyAutoDistributionFailure(@NotNull String onixFilePath,
                                              @Nullable String contentFilePath,
                                              @NotNull String collateralFilePath,
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

        List<AssetTestService> assets = new ArrayList<>();
        assets.add(onix.toAsset());

        if (contentFilePath != null) {
            assets.add(new ContentTestService(contentFilePath)
                    .cloneFileWith(onix.getSingleProductRecordReference())
                    .uploadToCrushFTP(crushFtpUploadCreds));
        }

        assets.add(new ContentTestService(collateralFilePath)
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds));

        getProductAPIService().triggerAutoDistribution();

        assets.forEach(asset -> {
            AssetDistrStatusDetailsDB assetDistrDetails = asset.getDistrStatusDetailsWhenExistsTo(channel.getName());

            assertThat(asset.getDistrStatusFrom(assetDistrDetails))
                    .as("Wrong asset distribution status.")
                    .isEqualTo(AssetDistributionStatus.DISTRIB_FAIL);

            LocalDateTime failedDistrTime = convertToLocalDateTime(assetDistrDetails.getTimeStamp());
            assertThat(failedDistrTime)
                    .as("Distribution TimeStamp in assetStatusDetail table is not close to the current time")
                    .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

            final int EXPECTED_FAILURE_COUNT = 1;
            AssetPrecompDetailsDB assetPrecompDetails = asset.getPrecompDetailsWhenExistFor(channel.getName());
            assertThat(assetPrecompDetails.getDistFailureCount())
                    .as("Failure count in precompdd table is not equal to 1").isEqualTo(EXPECTED_FAILURE_COUNT);

            LocalDateTime preComputedDate = assetPrecompDetails.getPreComputedDateUtc();

            final int CHANNEL_RETRY_INTERVAL_DB = getChannelRetryInterval(channel.getName());
            final int CHANNEL_RETRY_INTERVAL_TEST_DATA = channelAndCA.getChannelRetryInterval();

            assertThat(Duration.between(failedDistrTime, preComputedDate).toHours())
                    .as("Difference between failed distribution time and the next planned distribution time is not '{}'", CHANNEL_RETRY_INTERVAL_TEST_DATA)
                    .isEqualTo(CHANNEL_RETRY_INTERVAL_DB)
                    .isEqualTo(CHANNEL_RETRY_INTERVAL_TEST_DATA);
        });
    }

    @TmsLink("AUT-67")
    @Test(groups = {"onSaleDate", "autoDistribution"})
    public void verifyAutoDistrWithOnSaleDateAndPublicationDate() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForAutoDistrWithOnSaleDateAndPublicationDate,
                regressionCAFTPForForAutoDistrWithOnSaleDateAndPublicationDate)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyAutoDistrWithOnSaleDateAndPublicationDate/9781635862249.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyAutoDistrWithOnSaleDateAndPublicationDate/assets/9781635862249_print.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyAutoDistrWithOnSaleDateAndPublicationDate/assets/9781635862249_MarketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        LocalDate collateralPrecompDateFromDB = collateral
                .getPrecompDetailsWhenExistFor(channelAndCA.getChannelName())
                .getPreComputedDateUtc()
                .toLocalDate();

        assertThat(collateralPrecompDateFromDB)
                .as("Collateral's release date is not TODAY. Note. For collateral onSaleDate does not apply.")
                .isEqualTo(getServerLocalDateNow());

        OnixProduct expectedProduct = onix.getSingleProduct();
        LocalDate expectedOnSaleDateFromOnix = expectedProduct.getOnSaleDate();
        LocalDate expectedPublicationDateFromOnix = expectedProduct.getPublicationDate();
        AssetPrecompDetailsDB contentPrecompDetails = content.getPrecompDetailsWhenExistFor(channelAndCA.getChannelName());
        LocalDateTime actualReleaseDateFromDB = contentPrecompDetails.getReleaseDateUtc();
        LocalDateTime actualPrecomDateFromDB = contentPrecompDetails.getPreComputedDateUtc();

        assertThat(actualReleaseDateFromDB.toLocalDate())
                .as("Product's onSaleDate is not equal to date from Onix.")
                .isEqualTo(expectedOnSaleDateFromOnix)
                .as("Product's onSaleDate is equal to publication date from Onix.")
                .isNotEqualTo(expectedPublicationDateFromOnix);

        assertThat(actualReleaseDateFromDB)
                .as("Product's onSaleDate is not equal to precomp date.")
                .isEqualTo(actualPrecomDateFromDB);
    }

    @DataProvider(name = "singleODDDistrFailureTestData")
    private Object[][] singleODDDistrFailureTestData() {
        return new Object[][]{
                {"regression/verifySingleODDFailure/9780830842896.xml",
                        "regression/verifySingleODDFailure/assets/9780830842896_print.pdf",
                        "regression/verifySingleODDFailure/assets/9780830842896_marketingimage.jpg",
                        regressionPublicChannelFTPForDistributionFailure,
                        regressionChannelAssociationFTPForDistributionFailure
                },
                {"regression/verifySingleODDFailure/9781424552702.xml",
                        "regression/verifySingleODDFailure/assets/9781424552702.epub",
                        "regression/verifySingleODDFailure/assets/9781424552702_outsidebackcover.jpg",
                        regressionPublicChannelITMSForDistributionFailure,
                        regressionChannelAssociationITMSForDistributionFailure
                }};
    }

    @TmsLink("AUT-68")
    @TmsLink("AUT-69")
    @Test(dataProvider = "singleODDDistrFailureTestData", groups = {"singleODD", "negative", "itms"})
    public void verifySingleODDFailure(@NotNull String onixFilePath,
                                       @NotNull String contentFilePath,
                                       @NotNull String collateralFilePath,
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

        AssetTestService content = new ContentTestService(contentFilePath)
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService(collateralFilePath)
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleMetadata(),
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channel.getName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        Arrays.asList(onix.toAsset(), content, collateral).forEach(asset -> {
            AssetDistrStatusDetailsDB assetDistrDetails = asset.getDistrStatusDetailsWhenExistsTo(channel.getName());

            assertThat(asset.getDistrStatusFrom(assetDistrDetails))
                    .as("Wrong asset distribution status.")
                    .isEqualTo(AssetDistributionStatus.DISTRIB_FAIL);

            LocalDateTime failedDistrTime = convertToLocalDateTime(assetDistrDetails.getTimeStamp());
            assertThat(failedDistrTime)
                    .as("Distribution TimeStamp in assetStatusDetail table is not close to the current time")
                    .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

            final int EXPECTED_FAILURE_COUNT = 1;
            AssetPrecompDetailsDB assetPrecompDetails = asset.getPrecompDetailsWhenExistFor(channel.getName());
            assertThat(assetPrecompDetails.getDistFailureCount())
                    .as("Failure count in precompdd table is not equal to 1").isEqualTo(EXPECTED_FAILURE_COUNT);

            LocalDateTime preComputedDate = assetPrecompDetails.getPreComputedDateUtc();

            final int CHANNEL_RETRY_INTERVAL_DB = getChannelRetryInterval(channel.getName());
            final int CHANNEL_RETRY_INTERVAL_TEST_DATA = channelAndCA.getChannelRetryInterval();

            assertThat(Duration.between(failedDistrTime, preComputedDate).toHours())
                    .as("Difference between failed distribution time and the next planned distribution time is not '{}'", CHANNEL_RETRY_INTERVAL_TEST_DATA)
                    .isEqualTo(CHANNEL_RETRY_INTERVAL_DB)
                    .isEqualTo(CHANNEL_RETRY_INTERVAL_TEST_DATA);
        });
    }

    @TmsLink("AUT-135")
    @Test(groups = {"singleODD", "caServerSettings"})
    public void verifySendMetadataWithEveryAssetContent() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForSendMetadataWithEveryAssetContent,
                regressionCASFTPForSendMetadataWithEveryAssetContent)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySendMetadataWithEveryAsset/9781947951334.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifySendMetadataWithEveryAsset/assets/9781947951334.epub")
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

        String distributedPath = content.getDistrPathFor(channelAndCA.getChannelName());

        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleContent().getDestinationFolder();
        final String EXPECTED_FOLDER_NAME = channelAndCA.getAssetTypeFromSingleContent().toString().toUpperCase();
        final String ACTUAL_FOLDER_NAME = getPathFormatOptionValueFrom(distributedPath, DESTINATION_PATH_TEMPLATE, PathFormatTextOption.ASSET_TYPE);

        assertThat(ACTUAL_FOLDER_NAME)
                .as("Expected to have '{}' as folder name", EXPECTED_FOLDER_NAME)
                .isEqualTo(EXPECTED_FOLDER_NAME);

        assertThat(getFileTestService().isFileExistsOnSFTPServer(distributedPath))
                .as("File by path '" + distributedPath + "' does not exist on crushFTP.")
                .isTrue();

        String onixFilePath = FILE_PATH_DELIMITER + onix.getFileNameWithRecRef();
        assertThat(getFileTestService().isFileExistsOnSFTPServer(onixFilePath))
                .as("File by path '" + onixFilePath + "' does not exist on crushFTP.", onixFilePath)
                .isTrue();
    }

    @TmsLink("AUT-135")
    @Test(groups = {"singleODD", "caServerSettings"})
    public void verifySendMetadataWithEveryAssetCollateral() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForSendMetadataWithEveryAssetCollateral,
                regressionCASFTPForSendMetadataWithEveryAssetCollateral)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySendMetadataWithEveryAsset/9781947951334.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService collateral = new CollateralTestService("regression/verifySendMetadataWithEveryAsset/assets/9781947951334_screenshot001.png")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String distributedPath = collateral.getDistrPathFor(channelAndCA.getChannelName());
        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleCollateral().getDestinationFolder();
        final String EXPECTED_FOLDER_NAME = channelAndCA.getAssetTypeFromSingleCollateral().toString().toUpperCase();
        final String ACTUAL_FOLDER_NAME = getPathFormatOptionValueFrom(distributedPath, DESTINATION_PATH_TEMPLATE, PathFormatTextOption.ASSET_TYPE);

        assertThat(ACTUAL_FOLDER_NAME)
                .as("Expected to have '" + EXPECTED_FOLDER_NAME + "' as folder name")
                .isEqualTo(EXPECTED_FOLDER_NAME);

        assertThat(getFileTestService().isFileExistsOnSFTPServer(distributedPath))
                .as("File by path " + distributedPath + " does not exist on crushFTP.")
                .isTrue();

        String onixFilePath = FILE_PATH_DELIMITER + onix.getFileNameWithRecRef();
        assertThat(getFileTestService().isFileExistsOnSFTPServer(onixFilePath))
                .as("File by path " + onixFilePath + " does not exist on crushFTP.", onixFilePath)
                .isTrue();
    }

    @TmsLink("AUT-141")
    @Test(groups = {"singleODD", "caServerSettings"})
    public void verifyServerSettingsSendTriggerFile() {
        OnixTestService onix = new OnixTestService("regression/verifyServerSettingsSendTriggerFile/9781424552504.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyServerSettingsSendTriggerFile/assets/9781424552504.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyServerSettingsSendTriggerFile/assets/9781424552504_OutsideBackCover.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForSrvSettingsSendTriggerFile,
                regressionCAFTPForSrvSettingsSendTriggerFile)
                .createChannelWithCA();

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        Arrays.asList(content, collateral).forEach(asset -> {
            String assetDistPath = asset
                    .getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName())
                    .getDistributedPath();

            assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                    .as("No file by path '{}' has been found on channel two.", assetDistPath)
                    .isTrue();
        });

        final String OK_FILE_PATH = "/" + onix.getSingleProductRecordReference() + "/ok";
        assertThat(getFileTestService().isFileExistsOnSFTPServer(OK_FILE_PATH))
                .as("No OK file by path '{}' has been found on channel two.", OK_FILE_PATH)
                .isTrue();
    }

    @TmsLink("AUT-319")
    @Test(groups = {"singleODD", "caServerSettings"})
    public void verifySendMetadataWithEveryAssetContentRevisionCheck() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForSendMetadataWithEveryAssetContentRevisionCheck,
                regressionCASFTPForSendMetadataWithEveryAssetContentRevisionCheck)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySendMetadataWithEveryAsset/9781947951334.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifySendMetadataWithEveryAsset/assets/9781947951334.epub")
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

        String distributedPath = content.getDistrPathFor(channelAndCA.getChannelName());
        LocalDateTime contentFirstRevisionTime = getFileTestService().getFileModifyDateOnCrushFTP(distributedPath);
        String onixFilePath = FILE_PATH_DELIMITER + onix.getFileNameWithRecRef();
        LocalDateTime onixFirstRevisionTime = getFileTestService().getFileModifyDateOnCrushFTP(onixFilePath);

        // Upload onix, content second time
        content.uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleContent())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        content.waitOnSuccessfulDistributionOfSecondVersionTo(channelAndCA.getChannelName());

        await().untilAsserted(() -> {
            LocalDateTime onixSecondRevisionTime = getFileTestService().getFileModifyDateOnCrushFTP(onixFilePath);
            assertThat(onixSecondRevisionTime)
                    .as("File modify date is not valid for: '" + onixFilePath + "'.")
                    .isAfter(onixFirstRevisionTime);
        });

        await().untilAsserted(() -> {
            LocalDateTime contentSecondRevisionTime = getFileTestService().getFileModifyDateOnCrushFTP(distributedPath);
            assertThat(contentSecondRevisionTime)
                    .as("File modify date is not valid for: '" + distributedPath + "'.")
                    .isAfter(contentFirstRevisionTime);
        });

        Asset actualAssetOnUI = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAssetDetailsBy(content.getFileName());

        assertThat(actualAssetOnUI.getLastRevisioned())
                .as("Invalid 'last revisioned date' for: '" + content.getFileName() + "'.")
                .isAfter(actualAssetOnUI.getFirstAdded());
    }

    @TmsLink("AUT-319")
    @Test(groups = {"singleODD", "caServerSettings"})
    public void verifySendMetadataWithEveryAssetCollateralRevisionCheck() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForSendMetadataWithEveryAssetCollateralRevisionCheck,
                regressionCASFTPForSendMetadataWithEveryAssetCollateralRevisionCheck)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySendMetadataWithEveryAsset/9781947951334.xml");
        onix
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService collateral = new CollateralTestService("regression/verifySendMetadataWithEveryAsset/assets/9781947951334_screenshot001.png")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String collateralDistributedPath = collateral.getDistrPathFor(channelAndCA.getChannelName());
        LocalDateTime remoteCollateralFirstRevisionTime = getFileTestService().getFileModifyDateOnCrushFTP(collateralDistributedPath);
        String remoteOnixFilePath = FILE_PATH_DELIMITER + onix.getFileNameWithRecRef();
        LocalDateTime remoteOnixFirstRevisionTime = getFileTestService().getFileModifyDateOnCrushFTP(remoteOnixFilePath);

        // Upload collateral second time
        collateral.uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        await().untilAsserted(() -> {
            LocalDateTime onixSecondRevisionTime = getFileTestService().getFileModifyDateOnCrushFTP(remoteOnixFilePath);
            assertThat(onixSecondRevisionTime)
                    .as("File modify date is not valid for: '" + remoteOnixFilePath + "'.")
                    .isAfter(remoteOnixFirstRevisionTime);
        });

        await().untilAsserted(() -> {
            LocalDateTime contentSecondRevisionTime = getFileTestService().getFileModifyDateOnCrushFTP(collateralDistributedPath);
            assertThat(contentSecondRevisionTime)
                    .as("File modify date is not valid for: '" + collateralDistributedPath + "'.")
                    .isAfter(remoteCollateralFirstRevisionTime);
        });

        Asset assetDetails = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .getAssetDetailsBy(collateral.getFileName());

        assertThat(assetDetails.getLastRevisioned())
                .as("Invalid 'last revisioned date' for: '" + collateral.getFileName() + "'.")
                .isAfter(assetDetails.getFirstAdded());
    }

    @TmsLink("AUT-141")
    @Test(groups = {"batchODD", "caServerSettings"})
    public void verifyGroupByPublisherAndImprintOption() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForGroupByPublisherImprint,
                regressionCASFTPForGroupByPublisherImprint)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyGroupByPublisherAndImprintOption/9780636154148.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        List<OnixProduct> products = onix.getProducts();
        final int EXPECTED_PRODUCTS_QUANTITY = 5;
        assertThat(products).as("Wrong number of products").hasSize(EXPECTED_PRODUCTS_QUANTITY);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REGRESSION_BATCH_ODD_ENTITY_GROUP_BY_PUBLISHER)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        List<MetadataTestService> metadataAssets = onix.toAssets();

        final int EXPECTED_FEEDS_QUANTITY = 2;
        Set<String> actualUniqueDistrPaths = metadataAssets.stream()
                .map(metadata -> metadata.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toSet());

        assertThat(actualUniqueDistrPaths).as("Wrong number of distribution feeds").hasSize(EXPECTED_FEEDS_QUANTITY);

        Map<String, String> expectedImprintAndPublisher = new HashMap<>() {{
            put("Maskew Miller Longman (Pearson Marang)", "Pearson South Africa");
            put("Maskew Miller Longman", "Pearson");
        }};

        actualUniqueDistrPaths.forEach(feed -> {
            Path downloadDir = onix.getParentDir();
            Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(feed), downloadDir);

            OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

            List<String> imprints = downloadedOnix.getProductsImprints();
            String firstImprint = imprints.get(0);
            assertThat(imprints).as("Some imprints are not the same. All imprints must be equal.")
                    .allMatch(imprint -> imprint.equals(firstImprint));

            List<String> publishers = downloadedOnix.getProductsPublishers();
            String firstPublisher = publishers.get(0);
            assertThat(publishers).as("Some publishers are not the same. All publishers must be the same.")
                    .allMatch(publisher -> publisher.equals(firstPublisher));

            assertThat(expectedImprintAndPublisher).as("Found imprint and publisher are not related to expected.")
                    .containsAllEntriesOf(ImmutableMap.of(firstImprint, firstPublisher));
            boolean isValueRemoved = expectedImprintAndPublisher.values().remove(firstPublisher);
            assertThat(isValueRemoved).as("Imprint and publisher are not removed from the expected map")
                    .isTrue();
        });
    }

    @TmsLink("AUT-140")
    @Test(groups = {"batchODD", "negative"})
    public void verifyOnixFeedDistributionFailurePath() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForOnixFeedDistrFailurePath,
                regressionCAFTPForOnixFeedDistrFailurePath)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyOnixFeedDistributionFailurePath/9782374950822.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_OXIX_FEED_DISTR_FAILURE)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        final int CHANNEL_RETRY_INTERVAL_DB = getChannelRetryInterval(channelAndCA.getChannelName());
        final int CHANNEL_RETRY_INTERVAL_TEST_DATA = channelAndCA.getChannelRetryInterval();
        final int EXPECTED_FAILURE_COUNT = 1;
        List<MetadataTestService> metadataAssets = onix.toAssets();

        metadataAssets.forEach(metadata -> {
            AssetDistrStatusDetailsDB distrStatusDetail = metadata.getDistrStatusDetailsWhenExistsTo(channelAndCA.getChannelName());

            assertThat(metadata.getDistrStatusFrom(distrStatusDetail))
                    .as("Wrong asset distribution status.")
                    .isEqualTo(AssetDistributionStatus.DISTRIB_FAIL);

            LocalDateTime actualFailedDistrDateTime = convertToLocalDateTime(distrStatusDetail.getTimeStamp());
            assertThat(actualFailedDistrDateTime)
                    .as("Distribution TimeStamp in assetStatusDetail table is not close to the current time")
                    .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

            AssetPrecompDetailsDB precompDetail = metadata.getPrecompDetailsWhenExistFor(channelAndCA.getChannelName());
            LocalDateTime actualPreComputedDateTime = precompDetail.getPreComputedDateUtc();

            assertThat(precompDetail.getDistFailureCount())
                    .as("Invalid failure count in precompdd table.")
                    .isEqualTo(EXPECTED_FAILURE_COUNT);

            assertThat(Duration.between(actualFailedDistrDateTime, actualPreComputedDateTime).toHours())
                    .as("Invalid difference between failed distribution time and the next planned distribution time for: '"
                            + channelAndCA.getChannelName() + "' channel.")
                    .isEqualTo(CHANNEL_RETRY_INTERVAL_DB)
                    .isEqualTo(CHANNEL_RETRY_INTERVAL_TEST_DATA);
        });
    }

    @TmsLink("AUT-139")
    @Test(groups = {"batchODD", "feedDistributionOptions"})
    public void verifyOnixFeedDistributionSuccessPath() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForOnixFeedDistributionSuccessPath,
                regressionCASFTPForOnixFeedDistributionSuccessPath)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyOnixFeedDistributionSuccessPath/9780865653955.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_ONIX_FEED_DISTR_SUCCESS_PATH)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        List<String> distrPaths = onix.toAssets().stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList());

        String firstDistPath = distrPaths.get(0);
        assertThat(distrPaths)
                .as("Some publishers are not the same. All publishers must be the same.")
                .allMatch(distrPath -> distrPath.equals(firstDistPath));

        String actualBuName = getOptionValueFromFeed(firstDistPath, FeedPathOption.ACTUAL_BU_NAME);
        assertThat(actualBuName).as("Wrong BU in the feed distribution path").isEqualTo(PredBUs.PRED_AUTOMATION_BU.getName());
        String actualChannelName = getOptionValueFromFeed(firstDistPath, CHANNEL_NAME);
        assertThat(actualChannelName).as("Wrong Channel in the feed distribution path").isEqualTo(channelAndCA.getChannelName());
        String date = getOptionValueFromFeed(firstDistPath, FeedPathOption.DATE);
        String time = getOptionValueFromFeed(firstDistPath, FeedPathOption.HOURS_MIN_SEC);
        String mls = getOptionValueFromFeed(firstDistPath, FeedPathOption.MLS);

        assertThat(convertToLocalDateTime(PathFormatDateTimeOption.XFER_YYYYMMDDHHMMSSSSS, date + time + mls))
                .as("Date and time in the distr path is not close to now")
                .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));


        Path downloadDir = onix.getParentDir();
        Path downloadOnixPath = getFileTestService().downloadFileFromCrushFTP(Paths.get(firstDistPath), downloadDir);
        List<String> actualRecRefs = new OnixTestService(downloadOnixPath)
                .getProducts()
                .stream()
                .map(OnixProduct::getRecordReference)
                .collect(Collectors.toList());

        List<String> expectedRecRefs = onix
                .getProducts()
                .stream()
                .map(OnixProduct::getRecordReference)
                .collect(Collectors.toList());

        assertThat(actualRecRefs)
                .as("Distributed feed file does not contain record references of distributed products")
                .containsAll(expectedRecRefs);
    }

    @TmsLink("AUT-142")
    @Test(groups = "singleODD")
    public void verifySingleODDDistrProductsOnHold() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPSForSingleODDDistributeOnHold,
                regressionCAFTPSForSingleODDDistrOnHold)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySingleODDDistrProductsOnHold/9780547415987.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifySingleODDDistrProductsOnHold/assets/9780547415987.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifySingleODDDistrProductsOnHold/assets/9780547415987_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleContent())
                .putOnHold();

        assertThat(productDetailsPage.isOnHoldActive())
                .as("OnHold is not active on '" + onix.getSingleProductTitle() + "' product details page.")
                .isTrue();

        assertThat(hasProductOnHold(onix.getSingleProductTitle()))
                .as("Product '" + onix.getSingleProductTitle() + "' is not in OnHold state in DB.")
                .isTrue();

        DistributionOnHoldOrLockedModal distributionOnHoldOrLockedModal = productDetailsPage.selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleMetadata(),
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistributeOnHoldOrLocked();

        distributionOnHoldOrLockedModal.getContent().shouldBe(Condition.visible);
        distributionOnHoldOrLockedModal.getHeader().shouldHave(Condition.text("Please confirm."));
        distributionOnHoldOrLockedModal.getBody().shouldHave(Condition.text("This product is on hold. Do you want to continue?"));

        distributionOnHoldOrLockedModal
                .clickCancelButton()
                .clickDistributeOnHoldOrLocked()
                .clickContinueButton()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .confirmDistribution()
                .distributionSuccessMsgElm();

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-136")
    @Test(groups = {"caServerSettings", "negative"})
    public void verifySendMetadataWithEveryAssetIfOnixIsNotSelected() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();
        final ChannelPublic CHANNEL = regressionSendMetadataWithEveryAssetOnixNotSelected;
        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(CHANNEL);

        getTopMenu()
                .clickManageChannel()
                .openAddChannelAssociationsPageFor(CHANNEL.getName())
                .createChannelAssociation(regressionCASFTPForSendMetadataWithEveryAssetOnixNotSelected)
                .clickSave();

        getChannelAssociatePage().getCAPageErrorElm()
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("1 error(s) detected. Please correct & resubmit."));

        getChannelAssociatePage().getCASectionErrorElm()
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Send metadata with every asset requires selection of metadata for each product"));
    }

    @DataProvider(name = "publicationDateWithOnsetOffsetDateTestData")
    private Object[][] publicationDateWithOnsetOffsetDateTestData() {
        return new Object[][]{
                {"regression/verifyAutoDistrContentWithPublicationDateAndOnsetOffsetDate/9780358393726.xml",
                        "regression/verifyAutoDistrContentWithPublicationDateAndOnsetOffsetDate/assets/9780358393726.epub",
                        regressionPubChannelSFTPForAutoDistrContentWithPublicationDateAndOffsetDateOnix21,
                        regressionCASFTPForAutoDistrContentWithPublicationDateAndOffsetDateOnix21},
                {"regression/verifyAutoDistrContentWithPublicationDateAndOnsetOffsetDate/9780807516997.xml",
                        "regression/verifyAutoDistrContentWithPublicationDateAndOnsetOffsetDate/assets/9780807516997_print.pdf",
                        regressionPubChannelSFTPForAutoDistrContentWithPublicationDateAndOnsetDateOnix30,
                        regressionCAFTPSForAutoDistrContentWithPublicationDateAndOnsetDateOnix30}
        };
    }

    @TmsLink("AUT-132")
    @Test(dataProvider = "publicationDateWithOnsetOffsetDateTestData", groups = {"onsetOffset", "autoDistribution"})
    public void verifyAutoDistrContentWithPublicationDateAndOnsetOffsetDate(@NotNull String onixFilePath,
                                                                            @NotNull String contentFilePath,
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

        AssetTestService content = new ContentTestService(contentFilePath)
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetPrecompDetailsDB contentPrecompDetails = content.getPrecompDetailsWhenExistFor(channel.getName());
        LocalDate publicationDateFromOnix = onix.getSingleProduct().getPublicationDate();
        LocalDate precomDateFromDB = contentPrecompDetails.getPreComputedDateUtc().toLocalDate();
        ChannelAssociationContent caContent = channelAndCA.getSingleContent();

        if (caContent.getDistribute().equals(Distribute.BEFORE)) {
            assertThat(precomDateFromDB)
                    .as("Precompute date in DB is not valid. Expect [publication date] - [offset].")
                    .isEqualTo(publicationDateFromOnix.minusDays(caContent.getDaysBeforeOnSaleDate()));
        } else {
            assertThat(precomDateFromDB)
                    .as("Precompute date in DB is not valid. Expect [publication date] + [onset].")
                    .isEqualTo(publicationDateFromOnix.plusDays(caContent.getDaysBeforeOnSaleDate()));
        }
    }

    @TmsLink("AUT-143")
    @Test(groups = "singleODD")
    public void verifySingleODDDistrProductIsLocked() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForSingleODDDistributeLocked,
                regressionCASFTPForSingleODDDistrOnLocked)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySingleODDDistrProductIsLocked/9780807536797.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct actualProduct = onix.getSingleProduct();
        final String PRODUCT_TITLE = actualProduct.getTitle();
        assertThat(PublishingStatus.fromValue(actualProduct.getPublishingStatusCode()))
                .as("Product '" + PRODUCT_TITLE + "' status is not locked in Onix file.")
                .isEqualTo(PublishingStatus.WITHDRAWN_FROM_SALE);

        AssetTestService content = new ContentTestService("regression/verifySingleODDDistrProductIsLocked/assets/9780807536797.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifySingleODDDistrProductIsLocked/assets/9780807536797_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        assertThat(productDetailsPage.isProductLocked())
                .as("Wrong '" + PRODUCT_TITLE + "' product locked status on UI.")
                .isTrue();

        assertThat(isProductLocked(PRODUCT_TITLE))
                .as("Wrong '" + PRODUCT_TITLE + "' product locked status in DB.")
                .isTrue();

        DistributionOnHoldOrLockedModal distributionOnHoldOrLockedModal = productDetailsPage
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleMetadata(),
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistributeOnHoldOrLocked();

        distributionOnHoldOrLockedModal.getContent().shouldBe(Condition.visible);
        distributionOnHoldOrLockedModal.getHeader().shouldHave(Condition.text("Please confirm."));
        distributionOnHoldOrLockedModal.getBody().shouldHave(Condition.text("This product is locked. Do you want to continue?"));

        distributionOnHoldOrLockedModal
                .clickCancelButton()
                .clickDistributeOnHoldOrLocked()
                .clickContinueButton()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .confirmDistribution()
                .distributionSuccessMsgElm();

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-145")
    @Test(groups = {"autoDistribution"})
    public void verifyAutoDistrLockedProduct() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        OnixTestService onix = new OnixTestService("regression/verifyAutoDistrLockedProduct/9780807516977.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyAutoDistrLockedProduct/assets/9780807516977_print.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyAutoDistrLockedProduct/assets/9780807516977_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenuTestService()
                .selectDefaultBU()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        assertThat(productDetailsPage.isProductLocked())
                .as("'" + onix.getSingleProductTitle() + "' product has not locked on product details page.")
                .isTrue();

        assertThat(isProductLocked(onix.getSingleProductTitle()))
                .as("Product '" + onix.getSingleProductTitle() + "' has not locked in DB.")
                .isTrue();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPSForAutoDistrLockedProduct,
                regressionCAFTPSForAutoDistrLockedProduct)
                .createChannelWithCA();

        getProductAPIService().triggerAutoDistribution();

        MetadataTestService metadata = onix.toAsset();
        assertThat(metadata.isDistributionScheduledFor(channelAndCA.getChannelName()))
                .as("Record in precombdd table is unexpected for {}, {}",
                        channelAndCA.getChannelName(), metadata.getFileName())
                .isTrue();

        Arrays.asList(content, collateral).forEach(asset ->
                assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("No record in precombdd table has been found for {}, {}",
                                channelAndCA.getChannelName(), asset.getFileName())
                        .isFalse()
        );

        metadata.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-147")
    @Test(groups = {"singleODD"})
    public void verifySingleODDMultipleAssetsDistributionToMultipleChannels() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA1 = new ChannelAndCATestService(
                regressionPubChannelFTPSForSingleODDMultipleAssetsDistributionToMultipleChannels,
                regressionCAFTPSForSingleODDMultipleAssetsDistributionToMultipleChannels)
                .createChannelWithCA();

        ChannelAndCATestService channelAndCA2 = new ChannelAndCATestService(
                regressionPubChannelSFTPForSingleODDMultipleAssetsDistributionToMultipleChannels,
                regressionCASFTPForSingleODDMultipleAssetsDistributionToMultipleChannels)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySingleODDMultipleAssetsDistributionToMultipleChannels/9780134741376.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifySingleODDMultipleAssetsDistributionToMultipleChannels/assets/9780134741376.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifySingleODDMultipleAssetsDistributionToMultipleChannels/assets/9780134741376_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        DistributionConfirmPage distributionConfirmPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(AssetType.EPUB, AssetType.MARKETING_IMAGE)
                .clickDistribute()
                .selectChannelsBy(channelAndCA1.getChannelName(), channelAndCA2.getChannelName())
                .clickSendFiles();

        List<String> channelsNames = Arrays.asList(channelAndCA1.getChannelName(), channelAndCA2.getChannelName());
        List<AssetTestService> assets = Arrays.asList(content, collateral);

        channelsNames.forEach(channelsName -> assets.forEach(asset ->
                assertThat(distributionConfirmPage.isDistributionAllowedTo(channelsName, asset.getFileName()))
                        .as("Invalid distribution message on Confirm distribution page")
                        .isTrue()));

        distributionConfirmPage.completeSuccessfulDistribution();

        channelsNames.forEach(channelName -> assets.forEach(asset -> asset.getDistrStatusDetailsWhenExistsTo(channelName)));

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        channelsNames.forEach(channelsName -> {
            productDetailsPage
                    .getAssetDistributionStatusElmFor(channelsName, channelAndCA1.getAssetTypeFromSingleContent(), DISTRIB_SUCCESS)
                    .shouldBe(Condition.visible);

            productDetailsPage
                    .getAssetDistributionStatusElmFor(channelsName, channelAndCA1.getAssetTypeFromSingleCollateral(), DISTRIB_SUCCESS)
                    .shouldBe(Condition.visible);
        });
    }

    @TmsLink("AUT-148")
    @Test(groups = {"singleODD"})
    public void verifyConfirmDistributionPageCancelOption() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPSForConfirmDistributionPageCancel,
                regressionCAFTPSForConfirmDistributionPageCancel)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyConfirmDistributionPageCancelOption/9781612910284.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyConfirmDistributionPageCancelOption/assets/9781612910284.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyConfirmDistributionPageCancelOption/assets/9781612910284_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleMetadata(),
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .clickCancel();

        assertThat(productDetailsPage.getProductTitle())
                .as("Invalid title on product details page.")
                .isEqualTo(onix.getSingleProductTitle());
    }

    @TmsLink("AUT-149")
    @Test(groups = "singleODD")
    public void verifyConfirmDistributionPageBackOption() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPSForConfirmDistributionPageBack,
                regressionCAFTPSForConfirmDistributionPageBack)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyConfirmDistributionPageBackOption/9781612910248.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyConfirmDistributionPageBackOption/assets/9781612910248.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyConfirmDistributionPageBackOption/assets/9781612910248_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu().searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral(),
                        channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .clickBack()
                .getPageTitle()
                .shouldBe(Condition.visible);
    }

    @TmsLink("AUT-150")
    @Test(groups = "singleODD")
    public void verifySuccessDistributionPageBackOption() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPSForSuccessDistributionBackOption,
                regressionCAFTPSForSuccessDistributionBackOption)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySuccessDistributionPageBackOption/9781612910247.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifySuccessDistributionPageBackOption/assets/9781612910247.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifySuccessDistributionPageBackOption/assets/9781612910247_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getNavigationBar().clickLogoutLink().loginAs(METADATA_ADMIN);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral(),
                        channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution()
                .clickReturn();

        assertThat(productDetailsPage.getProductTitle())
                .as("Wrong product has displayed.")
                .isEqualTo(onix.getSingleProductTitle());
    }

    @TmsLink("AUT-151")
    @Test(groups = "singleODD")
    public void verifySingleODDSelectAllChannels() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelFTPSForSingleODDSelectAllChannels,
                regressionCAFTPSForSingleODDSelectAllChannels)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifySingleODDSelectAllChannels/9780134741376.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        new ContentTestService("regression/verifySingleODDSelectAllChannels/assets/9780134741376.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        new CollateralTestService("regression/verifySingleODDSelectAllChannels/assets/9780134741376_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelSelectPage channelSelectPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral(),
                        channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectAllChannels();

        int expectedNumberOfChannels = getAllActiveUnlockedTestChannels().size();

        ElementsCollection channelsCollection = channelSelectPage.getAllChannels();
        channelsCollection.filterBy(not(selected)).filter(not(disabled)).shouldHave(CollectionCondition.size(0));

        assertThat(channelsCollection)
                .as("Invalid number of channels to select. " +
                        "Note: some tests that were executing in parallel may create extra channels.")
                .hasSize(expectedNumberOfChannels);

        int actualNumberOfDistributionTables = channelSelectPage
                .clickSendFiles()
                .getAllDistributionChannelTables().size();

        assertThat(actualNumberOfDistributionTables)
                .as("Invalid number of channels to distribute. " +
                        "Note: some tests that were executing in parallel may create extra channels.")
                .isEqualTo(expectedNumberOfChannels);
    }

    @TmsLink("AUT-152")
    @Test(groups = {"singleODD", "tokenReplacement"})
    public void verifyTokenReplacementOnDistributionSetup1() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForTokenReplacementOnDistribution1,
                regressionCASFTPForTokenReplacementOnDistribution1)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyTokenReplacementOnDistributionSetup1/9780273723905.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updateIsbn13()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyTokenReplacementOnDistributionSetup1/assets/9780273723905.pdf")
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

        String assetDistPath = content.getDistrPathFor(channelAndCA.getChannelName());

        final String EXPECTED_ISBN13 = onix.getSingleProduct().getIsbn13();
        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleContent().getDestinationFolder();
        final String ACTUAL_ISBN13 = getPathFormatOptionValueFrom(assetDistPath,
                DESTINATION_PATH_TEMPLATE,
                PathFormatTextOption.ISBN13);

        assertThat(ACTUAL_ISBN13).as("Wrong isbn value in distribution path.").isEqualTo(EXPECTED_ISBN13);

        assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                .as("File '{}' should exist on channel two.", assetDistPath)
                .isTrue();
    }

    @TmsLink("AUT-152")
    @Test(groups = {"singleODD", "tokenReplacement"})
    public void verifyTokenReplacementOnDistributionSetup2() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForTokenReplacementOnDistribution2,
                regressionCASFTPForTokenReplacementOnDistribution2)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyTokenReplacementOnDistributionSetup2/9780826913586.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService metadata = onix.toAsset()
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String actualDistPath = metadata.getDistrPathFor(channelAndCA.getChannelName());
        final String EXPECTED_RECORD_REFERENCE = onix.getSingleProductRecordReference();
        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleMetadata().getDestinationFolder();
        PathFormatDateTimeOption pathFormatDateTimeOption = PathFormatDateTimeOption.XFER_YYYYMMDDHHMMSSSSS;
        final String ACTUAL_RECORD_REFERENCE = getPathFormatOptionValueFrom(
                actualDistPath,
                DESTINATION_PATH_TEMPLATE,
                PathFormatTextOption.RECORD_REFERENCE);
        final String ACTUAL_TRANSFER_TIMESTAMP = getPathFormatOptionValueFrom(
                actualDistPath,
                DESTINATION_PATH_TEMPLATE,
                pathFormatDateTimeOption);

        assertThat(ACTUAL_RECORD_REFERENCE).as("Record reference value is not correct.").isEqualTo(EXPECTED_RECORD_REFERENCE);

        assertThat(convertToLocalDateTime(pathFormatDateTimeOption, ACTUAL_TRANSFER_TIMESTAMP))
                .as("Date and time in the distr path is not close to now")
                .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

        assertThat(getFileTestService().isFileExistsOnSFTPServer(actualDistPath))
                .as("File '{}' should exist on channel two.", actualDistPath)
                .isTrue();
    }

    @TmsLink("AUT-152")
    @Test(groups = {"singleODD", "tokenReplacement"})
    public void verifyTokenReplacementOnDistributionSetup3() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForTokenReplacementOnDistribution3,
                regressionCASFTPForTokenReplacementOnDistribution3)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyTokenReplacementOnDistributionSetup3/9780807587430.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService collateral = new CollateralTestService("regression/verifyTokenReplacementOnDistributionSetup3/assets/9780807587430_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String assetDistPath = collateral.getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName()).getDistributedPath();
        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleCollateral().getDestinationFolder();
        PathFormatDateTimeOption pathFormatDateTimeOption = PathFormatDateTimeOption.XFER_YYYYMMDD_DASH_HHMMSSSSS;
        final String ACTUAL_XFER_DATE = getPathFormatOptionValueFrom(
                assetDistPath,
                DESTINATION_PATH_TEMPLATE,
                pathFormatDateTimeOption);

        assertThat(convertToLocalDateTime(pathFormatDateTimeOption, ACTUAL_XFER_DATE))
                .as("Date and time in the distr path is not close to now")
                .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

        assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                .as("File '{}' should exist on channel two.", assetDistPath)
                .isTrue();
    }

    @TmsLink("AUT-152")
    @Test(groups = {"singleODD", "tokenReplacement"})
    public void verifyTokenReplacementOnDistributionSetup4() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForTokenReplacementOnDistribution4,
                regressionCASFTPForTokenReplacementOnDistribution4)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyTokenReplacementOnDistributionSetup4/9780807517437.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService collateral = new CollateralTestService("regression/verifyTokenReplacementOnDistributionSetup4/assets/9780807517437_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String assetDistPath = collateral.getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName()).getDistributedPath();
        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleCollateral().getDestinationFolder();
        PathFormatDateOption pathFormatDateOption = PathFormatDateOption.XFER_MMDDYY;
        final String ACTUAL_PATH_FORMAT_OPTION_VALUE = getPathFormatOptionValueFrom(
                assetDistPath,
                DESTINATION_PATH_TEMPLATE,
                pathFormatDateOption);

        LocalDate actualDate = convertToLocalDate(pathFormatDateOption, ACTUAL_PATH_FORMAT_OPTION_VALUE);
        assertThat(actualDate)
                .as("Date and time in the distr path is not close to now")
                .isEqualTo(getServerLocalDateNow());

        assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                .as("File '{}' should exist on channel two.", assetDistPath)
                .isTrue();
    }

    @TmsLink("AUT-152")
    @Test(groups = {"singleODD", "tokenReplacement"})
    public void verifyTokenReplacementOnDistributionSetup5() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForTokenReplacementOnDistribution5,
                regressionCASFTPForTokenReplacementOnDistribution5)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyTokenReplacementOnDistributionSetup5/9780547350103.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyTokenReplacementOnDistributionSetup5/assets/9780547350103.epub")
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

        String assetDistPath = content.getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName()).getDistributedPath();
        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleContent().getDestinationFolder();
        PathFormatDateTimeOption pathFormatDateTimeOption = PathFormatDateTimeOption.UPD_MM_DOT_DD_DOT_YY_DASH_HHMMSSSSS;
        final String ACTUAL_PATH_FORMAT_OPT_VALUE = getPathFormatOptionValueFrom(
                assetDistPath,
                DESTINATION_PATH_TEMPLATE,
                pathFormatDateTimeOption);

        LocalDateTime actualDateTime = convertToLocalDateTime(pathFormatDateTimeOption, ACTUAL_PATH_FORMAT_OPT_VALUE);
        assertThat(actualDateTime)
                .as("Date and time in the distr path is not close to now")
                .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

        assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                .as("File '{}' should exist on channel two.", assetDistPath)
                .isTrue();
    }

    @TmsLink("AUT-152")
    @Test(groups = {"singleODD", "tokenReplacement"})
    public void verifyTokenReplacementOnDistributionSetup6() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForTokenReplacementOnDistribution6,
                regressionCASFTPForTokenReplacementOnDistribution6)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyTokenReplacementOnDistributionSetup6/9781414395579.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updateIsbn13()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService collateral = new CollateralTestService("regression/verifyTokenReplacementOnDistributionSetup6/assets/9781414395579_outsidebackcover.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String assetDistPath = collateral.getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName()).getDistributedPath();
        final String EXPECTED_ISBN13 = onix.getSingleProduct().getIsbn13();
        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleCollateral().getDestinationFolder();
        final String ACTUAL_ISBN13 = getPathFormatOptionValueFrom(
                assetDistPath,
                DESTINATION_PATH_TEMPLATE,
                PathFormatTextOption.ISBN13);

        assertThat(ACTUAL_ISBN13)
                .as("Invalid ISBN13 value in distribution path.")
                .isEqualTo(EXPECTED_ISBN13);

        assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                .as("File '{}' should exist on channel two.", assetDistPath)
                .isTrue();
    }

    @TmsLink("AUT-152")
    @Test(groups = {"singleODD", "tokenReplacement"})
    public void verifyTokenReplacementOnDistributionSetup7() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForTokenReplacementOnDistribution7,
                regressionCASFTPForTokenReplacementOnDistribution7)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyTokenReplacementOnDistributionSetup7/9780830862603.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService collateral = new CollateralTestService("regression/verifyTokenReplacementOnDistributionSetup7/assets/9780830862603_outsidefrontcover.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String assetDistPath = collateral.getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName()).getDistributedPath();
        final String EXPECTED_DESTINATION_PATH = channelAndCA.getSingleCollateral().getDestinationFolder();

        assertThat(FILE_PATH_DELIMITER + assetDistPath)
                .as("Invalid distribution path.")
                .isEqualTo(EXPECTED_DESTINATION_PATH + EXTENSION_SEPARATOR + getFileExtension(collateral.getFileName()));

        assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                .as("File '{}' should exist on channel two.", assetDistPath)
                .isTrue();
    }

    @TmsLink("AUT-152")
    @Test(groups = {"singleODD", "tokenReplacement"})
    public void verifyTokenReplacementOnDistributionSetup8() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForTokenReplacementOnDistribution8,
                regressionCASFTPForTokenReplacementOnDistribution8)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyTokenReplacementOnDistributionSetup8/9781523513819.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updateIsbn10()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService collateral = new CollateralTestService("regression/verifyTokenReplacementOnDistributionSetup8/assets/9781523513819_frontcoverhigh.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String assetDistPath = collateral.getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName()).getDistributedPath();
        final String EXPECTED_ISBN10 = onix.getSingleProduct().getIsbn10();
        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleCollateral().getDestinationFolder();
        final String ACTUAL_ISBN10 = getPathFormatOptionValueFrom(assetDistPath, DESTINATION_PATH_TEMPLATE, PathFormatTextOption.ISBN10);

        assertThat(ACTUAL_ISBN10).as("Wrong isbn value in distribution path.").isEqualTo(EXPECTED_ISBN10);

        assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                .as("File '{}' should exist on channel two.", assetDistPath)
                .isTrue();
    }

    @TmsLink("AUT-227")
    @Test(groups = {"singleODD", "negative", "caServerSettings"})
    public void verifyServerSettingsSendTriggerFileFailure() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForSrvSettingsSendTriggerFileFailure,
                regressionCASFTPForSrvSettingsSendTriggerFileFailure)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyServerSettingsSendTriggerFileFailure/9781424551903.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyServerSettingsSendTriggerFileFailure/assets/9781424551903.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyServerSettingsSendTriggerFileFailure/assets/9781424551903_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(
                        channelAndCA.getAssetTypeFromSingleMetadata(),
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.getDistrStatusWhenExistsFor(channelAndCA.getChannelName()))
                        .as("Invalid distribution status. Should be fail.")
                        .isEqualTo(AssetDistributionStatus.DISTRIB_FAIL));

        final String FOLDER_PATH = FILE_PATH_DELIMITER + onix.getFileNameWithRecRef();
        assertThat(getFileTestService().isFileExistsOnSFTPServer(FOLDER_PATH))
                .as("Folder '{}' shouldn't exist on channel two.", FOLDER_PATH)
                .isFalse();
    }

    @TmsLink("AUT-144")
    @Test(groups = "batchODD")
    public void verifyBatchODDWhenProductOnHoldAllowedContentCollateralAreDistr() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDContentCollateralDistrOnHold,
                regressionCASFTPForBatchODDContentCollateralDistrOnHold)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDWhenProductOnHoldContentCollateralNotDistr/9780547415978.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDWhenProductOnHoldContentCollateralNotDistr/assets/9780547415978.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDWhenProductOnHoldContentCollateralNotDistr/assets/9780547415978_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .putOnHold();

        assertThat(productDetailsPage.isOnHoldActive())
                .as("OnHold has not activated for '" + onix.getSingleProductTitle() + "' product details page.")
                .isTrue();

        assertThat(hasProductOnHold(onix.getSingleProductTitle()))
                .as("OnHold has not activated for '" + onix.getSingleProductTitle() + "' product in DB")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_CONTENT_COLLATERAL_PRODUCT_ON_HOLD)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-230")
    @Test(groups = "batchODD")
    public void verifyBatchODDWhenProductLockedAllowedContentCollateralAreDistr() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPSForBatchODDDistributeLocked,
                regressionCAFTPSForBatchODDDistrLocked)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDWhenProductLockedContentCollateralNotDistr/9780807536796.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDWhenProductLockedContentCollateralNotDistr/assets/9780807536796.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDWhenProductLockedContentCollateralNotDistr/assets/9780807536796_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage();

        assertThat(productDetailsPage.isProductLocked())
                .as("Locked has not activated on '" + onix.getSingleProductTitle() + "' product details page.")
                .isTrue();

        assertThat(isProductLocked(onix.getSingleProductTitle()))
                .as("Locked is not active for '" + onix.getSingleProductTitle() + "' product in DB.")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_CONTENT_COLLATERAL_ON_LOCKED_PRODUCT)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-233")
    @Test(groups = {"autoDistribution"})
    public void verifyAutoDistrProductOnHoldOnlyOnixHasDistributed() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        OnixTestService onix = new OnixTestService("regression/verifyAutoDistrProductOnHoldOnlyOnixHasDistributed/9780807516970.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyAutoDistrProductOnHoldOnlyOnixHasDistributed/assets/9780807516970_print.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyAutoDistrProductOnHoldOnlyOnixHasDistributed/assets/9780807516970_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .putOnHold();

        assertThat(productDetailsPage.isOnHoldActive())
                .as("OnHold has not activated on '" + onix.getSingleProductTitle() + "' product details page.")
                .isTrue();

        assertThat(hasProductOnHold(onix.getSingleProductTitle()))
                .as("OnHold has not activated for '" + onix.getSingleProductTitle() + "' product in DB.")
                .isTrue();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                publicChannelFTPForAutoDistrProductOnHoldOnlyOnixHasDistributed,
                caFTPForAutoDistrProductOnHoldOnlyOnixHasDistributed)
                .createChannelWithCA();

        getProductAPIService().triggerAutoDistribution();

        MetadataTestService metadata = onix.toAsset();
        assertThat(metadata.isDistributionScheduledFor(channelAndCA.getChannelName()))
                .as("Record in precombdd table is unexpected for '" +
                        channelAndCA.getChannelName() + "', '" +
                        metadata.getFileName() + "'")
                .isTrue();

        Arrays.asList(content, collateral).forEach(asset ->
                assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("No record in precombdd table is expected for '" + channelAndCA.getChannelName()
                                + "', '" + asset.getFileName() + "'.")
                        .isFalse());

        metadata.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-254")
    @Test(groups = "publicationDateFilter")
    public void verifyFilteringByPubDateDefaultDateAndStateBeforeIsSelected() {
        ChannelPublic channel = regressionPublicChannelFTPForFilteringByPubDateDefaultDateAndState;
        ChannelAssociation ca = regressionCAFTPForFilteringByPubDateDefaultDateAndState;
        boolean shouldPublicationFilterBeforeOptionBeSelected = true;
        @NotNull LocalDate expectedDate = getServerLocalDateNow();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        final String CHANNEL_NAME = channel.getName();

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(channel);

        AssociatePage associatePage = getTopMenu()
                .clickManageChannel()
                .openAddChannelAssociationsPageFor(CHANNEL_NAME)
                .createChannelAssociation(ca);

        assertThat(associatePage.isPublicationFilterBeforeOptionSelected())
                .as("Wrong Publication filter option is selected")
                .isEqualTo(shouldPublicationFilterBeforeOptionBeSelected);

        associatePage.getPublicationFilterInstructionText()
                .shouldHave(Condition.text("Distribute assets that have a publication date"));

        assertThat(associatePage.getSelectedPubDate())
                .as("Default (selected) Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(expectedDate);

        associatePage
                .clickSave()
                .isSuccessMsgVisible();

        ChannelAssociationDetails channelAssociationDetails = getChannelAssociationDetailsFor(CHANNEL_NAME);

        final DistributionRule EXPECTED_DISTRIBUTION_RULE = ca.getDistributionRule();

        assertThat(channelAssociationDetails.isPublicationDateFilterActiveYn())
                .as("Invalid Publication Filter status")
                .isEqualTo(EXPECTED_DISTRIBUTION_RULE.isPubDateFilterActive());

        assertThat(channelAssociationDetails.isPublicationDateFilterBeforeYn())
                .as("Invalid Publication Filter option is selected")
                .isEqualTo(EXPECTED_DISTRIBUTION_RULE.isPubDateFilterBefore());

        assertThat(channelAssociationDetails.getPublicationDateFilterValue())
                .as("Invalid Publication Filter Date Value")
                .isEqualTo(expectedDate);

    }

    @TmsLink("AUT-255")
    @Test(groups = "publicationDateFilter")
    public void verifyFilteringByPubDateDefaultDateAndStateAfterOptionIsSelected() {
        final DistributionRule regressionFilteringByPubDateDefaultDateAndAfterState = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow().plusDays(2))
                .build();

        final ChannelAssociation regressionCAFTPForFilteringByPubDateDefaultDateAndAfterState = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateDefaultDateAndAfterState)
                .associationServer(AssociationSrvs.regressionAssociationSrvFTPForFilteringByPubDateDefaultDateAndAfterState)
                .build();

        ChannelPublic channel = regressionPublicChannelFTPForFilteringByPubDateDefaultDateAndAfterState;
        boolean shouldPublicationFilterBeforeOptionBeSelected = false;
        @NotNull LocalDate expectedDate = getServerLocalDateNow().plusDays(2);

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        final String CHANNEL_NAME = channel.getName();

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(channel);

        AssociatePage associatePage = getTopMenu()
                .clickManageChannel()
                .openAddChannelAssociationsPageFor(CHANNEL_NAME)
                .createChannelAssociation(regressionCAFTPForFilteringByPubDateDefaultDateAndAfterState);

        assertThat(associatePage.isPublicationFilterBeforeOptionSelected())
                .as("Wrong Publication filter option is selected")
                .isEqualTo(shouldPublicationFilterBeforeOptionBeSelected);

        associatePage.getPublicationFilterInstructionText()
                .shouldHave(Condition.text("Distribute assets that have a publication date"));

        assertThat(associatePage.getSelectedPubDate())
                .as("Default (selected) Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(expectedDate);

        associatePage
                .clickSave()
                .isSuccessMsgVisible();

        ChannelAssociationDetails channelAssociationDetails = getChannelAssociationDetailsFor(CHANNEL_NAME);

        final DistributionRule EXPECTED_DISTRIBUTION_RULE = regressionCAFTPForFilteringByPubDateDefaultDateAndAfterState.getDistributionRule();

        assertThat(channelAssociationDetails.isPublicationDateFilterActiveYn())
                .as("Invalid Publication Filter status")
                .isEqualTo(EXPECTED_DISTRIBUTION_RULE.isPubDateFilterActive());

        assertThat(channelAssociationDetails.isPublicationDateFilterBeforeYn())
                .as("Invalid Publication Filter option is selected")
                .isEqualTo(EXPECTED_DISTRIBUTION_RULE.isPubDateFilterBefore());

        assertThat(channelAssociationDetails.getPublicationDateFilterValue())
                .as("Invalid Publication Filter Date Value")
                .isEqualTo(expectedDate);
    }

    @TmsLink("AUT-256")
    @Test(groups = {"publicationDateFilter", "autoDistribution"})
    public void verifyFilteringByPubDateBeforeInOnixAssetsAreDistributed() {
        final DistributionRule regressionFilteringByPubDateBeforeInOnix = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(true)
                .pubDateFilterValue(getServerLocalDateNow().minusDays(1))
                .build();

        final ChannelAssociation regressionCAFTPForFilteringByPubDateBeforeInOnix = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateBeforeInOnix)
                .associationServer(AssociationSrvs.regressionAssociationSrvFTPForFilteringByPubDateBeforeInOnix)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForFilteringByPubDateBeforeInOnix,
                regressionCAFTPForFilteringByPubDateBeforeInOnix)
                .createChannelWithCA();

        LocalDate onixPubDateNow = getServerLocalDateNow().minusDays(2);
        OnixTestService onix = new OnixTestService("regression/verifyProductFilteringByPubDateBeforeInOnixAssetsAreDistributed/9780136161424.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(onixPubDateNow)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyProductFilteringByPubDateBeforeInOnixAssetsAreDistributed/assets/9780136161424.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyProductFilteringByPubDateBeforeInOnixAssetsAreDistributed/assets/9780136161424_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(actualCADetailsFromDB.getPublicationDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Publication Date Value in CA should be after Publication Date in Onix")
                .isAfter(onixPubDateNow);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-257")
    @Test(groups = {"publicationDateFilter", "autoDistribution", "negative"})
    public void verifyFilteringByPubDateIsAfterOnixPubDate() {
        final DistributionRule regressionFilteringByPubDateIsAfterOnixPubDate = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(true)
                .pubDateFilterValue(getServerLocalDateNow().minusDays(1))
                .build();

        final ChannelAssociation regressionCAFTPForFilteringByPubDateIsAfterOnixPubDate = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateIsAfterOnixPubDate)
                .associationServer(AssociationSrvs.regressionAssociationSrvFTPForFilteringByPubDateIsAfterOnixPubDate)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForFilteringByPubDateIsAfterOnixPubDate,
                regressionCAFTPForFilteringByPubDateIsAfterOnixPubDate)
                .createChannelWithCA();

        LocalDate onixPubDateNow = getServerLocalDateNow();
        OnixTestService onix = new OnixTestService("regression/verifyProductFilteringByPubDateIsAfterOnixPubDate/9780136161401.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(onixPubDateNow)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyProductFilteringByPubDateIsAfterOnixPubDate/assets/9780136161401.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyProductFilteringByPubDateIsAfterOnixPubDate/assets/9780136161401_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Selected Publication filter date is not today minus one day. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(actualCADetailsFromDB.getPublicationDateFilterValue())
                .as("Publication Date Value in CA should be after Publication Date in Onix")
                .isBefore(onixPubDateNow);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected")
                        .isFalse());
    }

    @TmsLink("AUT-258")
    @Test(groups = {"publicationDateFilter", "autoDistribution", "negative"})
    public void verifyFilteringByPubDateEqualsOnixPubDate() {
        final DistributionRule regressionFilteringByPubDateEqualOnixDate = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(true)
                .pubDateFilterValue(getServerLocalDateNow())
                .build();

        final ChannelAssociation regressionCAFTPForFilteringByPubDateEqualsOnixDate = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateEqualOnixDate)
                .associationServer(AssociationSrvs.regressionAssociationSrvFTPForFilteringByPubDateEqualsOnixDate)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        LocalDate onixPubDateNow = getServerLocalDateNow();

        OnixTestService onix = new OnixTestService("regression/verifyProductFilteringByPubDateEqualsOnixPubDate/9780136161400.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(onixPubDateNow)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForFilteringByPubDateEqualsOnixPubDate,
                regressionCAFTPForFilteringByPubDateEqualsOnixDate)
                .createChannelWithCA();

        AssetTestService content = new ContentTestService("regression/verifyProductFilteringByPubDateEqualsOnixPubDate/assets/9780136161400.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyProductFilteringByPubDateEqualsOnixPubDate/assets/9780136161400_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(actualCADetailsFromDB.getPublicationDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Publication Date Value in CA should be after Publication Date in Onix")
                .isEqualTo(onixPubDateNow);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("record for this asset in the precompdd table is unexpected")
                        .isFalse());
    }

    @TmsLink("AUT-262")
    @Test(groups = {"publicationDateFilter", "autoDistribution", "negative"})
    public void verifyFilteringByPubDateEqualsOnixPubDateAfterOption() {
        final DistributionRule regressionFilteringByPubDateEqualOnixDateAfterOption = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow())
                .build();

        final ChannelAssociation regressionCAFTPForFilteringByPubDateEqualsOnixDateAfterOption = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateEqualOnixDateAfterOption)
                .associationServer(AssociationSrvs.regressionAssociationSrvFTPForFilteringByPubDateEqualsOnixDateAfterOption)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        LocalDate onixPubDateNow = getServerLocalDateNow();

        OnixTestService onix = new OnixTestService("regression/verifyProductFilteringByPubDateEqualsOnixPubDate/9780136161466.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(onixPubDateNow)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForFilteringByPubDateEqualsOnixPubDateAfterOption,
                regressionCAFTPForFilteringByPubDateEqualsOnixDateAfterOption)
                .createChannelWithCA();

        AssetTestService content = new ContentTestService("regression/verifyProductFilteringByPubDateEqualsOnixPubDate/assets/9780136161466.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyProductFilteringByPubDateEqualsOnixPubDate/assets/9780136161466_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(actualCADetailsFromDB.getPublicationDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Publication Date Value in CA should be after Publication Date in Onix")
                .isEqualTo(onixPubDateNow);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("record for this asset in the precompdd table is unexpected")
                        .isFalse());
    }

    @TmsLink("AUT-259")
    @Test(groups = {"publicationDateFilter", "autoDistribution", "negative"})
    public void verifyPublicationDateFilterFailADWithBeforeOptionMissedPubDateInOnix() {
        final DistributionRule regressionFilteringByPubDateBeforeNoPubDateInOnix = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(true)
                .pubDateFilterValue(getServerLocalDateNow())
                .build();

        final ChannelAssociation regressionCAFTPForPublicationDateFilterBeforeWhenNoPubDateInOnix = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateBeforeNoPubDateInOnix)
                .associationServer(AssociationSrvs.regressionAssociationSrvFTPForFilteringByPubDateBeforeNoPubDateInOnix)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForPublicationDateFilterBeforeWhenNoPubDateInOnix,
                regressionCAFTPForPublicationDateFilterBeforeWhenNoPubDateInOnix)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyPublicationDateFilterFailADWithBeforeOptionMissedPubDateInOnix/9780134618968.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        assertThat(onix.getSingleProduct().getPublicationDateNullable()).isNull();

        AssetTestService content = new ContentTestService("regression/verifyPublicationDateFilterFailADWithBeforeOptionMissedPubDateInOnix/assets/9780134618968.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyPublicationDateFilterFailADWithBeforeOptionMissedPubDateInOnix/assets/9780134618968_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(actualCADetailsFromDB.getPublicationDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(channelAndCA.getDistributionRule().getPubDateFilterValue());

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record should not appear in precompdd and be scheduled for distribution.")
                        .isFalse());
    }


    @TmsLink("AUT-263")
    @Test(groups = {"publicationDateFilter", "autoDistribution", "negative"})
    public void verifyPublicationDateFilterFailADWithBeforeOptionMissedPubDateInOnixAfterOptionInCA() {
        final DistributionRule regressionFilteringByPubDateAfterNoPubDateInOnix = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow())
                .build();

        final ChannelAssociation regressionCAFTPForPublicationDateFilterAfterWhenNoPubDateInOnix = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateAfterNoPubDateInOnix)
                .associationServer(AssociationSrvs.regressionAssociationSrvFTPForFilteringByPubDateAfterNoPubDateInOnix)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForPublicationDateFilterAfterWhenNoPubDateInOnix,
                regressionCAFTPForPublicationDateFilterAfterWhenNoPubDateInOnix)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyPublicationDateFilterFailADWithBeforeOptionMissedPubDateInOnix/9780830847433.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        assertThat(onix.getSingleProduct().getPublicationDateNullable()).isNull();

        AssetTestService content = new ContentTestService("regression/verifyPublicationDateFilterFailADWithBeforeOptionMissedPubDateInOnix/assets/9780830847433.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyPublicationDateFilterFailADWithBeforeOptionMissedPubDateInOnix/assets/9780830847433_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(actualCADetailsFromDB.getPublicationDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(channelAndCA.getDistributionRule().getPubDateFilterValue());

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record should not appear in precompdd and be scheduled for distribution.")
                        .isFalse());
    }

    @TmsLink("AUT-260")
    @Test(groups = {"publicationDateFilter", "autoDistribution"})
    public void verifyPublicationDateFilterWhenPubDateInOnixIsAfterPubDateInFilter() {
        final DistributionRule regressionFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow())
                .build();

        final ChannelAssociation regressionCASFTPForFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr)
                .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr)
                .build();


        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr,
                regressionCASFTPForFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr)
                .createChannelWithCA();

        LocalDate updatedOnixPubDate = getServerLocalDateNow().plusDays(1);
        OnixTestService onix = new OnixTestService("regression/verifyPublicationDateFilterWhenPubDateInOnixIsAfterPubDateInFilter/9780136161411.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(updatedOnixPubDate)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyPublicationDateFilterWhenPubDateInOnixIsAfterPubDateInFilter/assets/9780136161411.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyPublicationDateFilterWhenPubDateInOnixIsAfterPubDateInFilter/assets/9780136161411_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(actualCADetailsFromDB.getPublicationDateFilterValue())
                .as("Publication Date Value in CA should be after Publication Date in Onix")
                .isBefore(updatedOnixPubDate);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-261")
    @Test(groups = {"publicationDateFilter", "autoDistribution", "negative"})
    public void verifyFilteringByPubDateIsBeforeOnixPubDate() {
        final DistributionRule regressionFilteringByPubDateIsBeforeOnixPubDate = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow())
                .build();

        final ChannelAssociation regressionCAFTPForFilteringByPubDateIsBeforeOnixPubDate = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateIsBeforeOnixPubDate)
                .associationServer(AssociationSrvs.regressionAssociationSrvFTPForFilteringByPubDateIsBeforeOnixPubDate)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        LocalDate updateOnixPubDate = getServerLocalDateNow().minusDays(1);

        OnixTestService onix = new OnixTestService("regression/verifyProductFilteringByPubDateIsBeforeOnixPubDate/9780136161429.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(updateOnixPubDate)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyProductFilteringByPubDateIsBeforeOnixPubDate/assets/9780136161429.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyProductFilteringByPubDateIsBeforeOnixPubDate/assets/9780136161429_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPForFilteringByPubDateIsBeforeOnixPubDate,
                regressionCAFTPForFilteringByPubDateIsBeforeOnixPubDate)
                .createChannelWithCA();

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());

        assertThat(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isEqualTo(actualCADetailsFromDB.getPublicationDateFilterValue())
                .as("Publication Date Value in CA should be before Publication Date in Onix")
                .isAfter(updateOnixPubDate);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected")
                        .isFalse());
    }

    @TmsLink("AUT-275")
    @Test(groups = {"batchODD", "distributionPathOptions"})
    public void verifyMultipleProductTypesAndAssetsDistributionByPaths() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPSForMultipleProductTypesAndAssetsDistributionByPaths,
                regressionCAFTPSForMultipleProductTypesAndAssetsDistributionByPaths)
                .createChannelWithCA();


        Map<String, AssetTestService> testResults = new HashMap<>();
        List<TestProduct> testProducts = Arrays.asList(epubTestProduct, printTestProduct, pdfTestProduct, mobiTestProduct);
        testProducts.forEach(testProduct -> {
            OnixTestService onix = new OnixTestService(testProduct.getOnixFilePath())
                    .readOnixFile()
                    .updateTitles()
                    .updateRecordReferences()
                    .saveAsNewFile()
                    .uploadToCrushFTP(crushFtpUploadCreds)
                    .waitOnProductsInDB();

            AssetTestService collateral = new CollateralTestService(testProduct.getCollateralFilePath())
                    .cloneFileWith(onix.getSingleProductRecordReference())
                    .uploadToCrushFTP(crushFtpUploadCreds);

            testResults.put(testProduct.getRootDistributionFolder(), collateral);
        });

        getTopMenu().clickDistributeProducts().setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS);

        testResults.forEach((expectedRootDistrFolder, asset) -> {
            final String ACTUAL_DISTR_PATH = asset.getDistrPathFor(channelAndCA.getChannelName());
            String distrPathAssetType = AssetType.MARKETING_IMAGE.getDistributionPathValue();
            String EXPECTED_DISTR_PATH = expectedRootDistrFolder + distrPathAssetType + "/" + asset.getFileName();

            assertThat(ACTUAL_DISTR_PATH).as("Wrong imprint value in distribution path")
                    .isEqualTo(EXPECTED_DISTR_PATH);
        });
    }

    @TmsLink("AUT-264")
    @Test(groups = {"singleODD", "publicationDateFilter"})
    public void verifyPublicationDateFilterSuccODDWithOptionOneDayDiffInFutureAfterOptionIsSelectedInCA() {
        final DistributionRule regressionFilteringByPubDateSuccODDWithOptionAfterOneDayDiffInFuture = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow())
                .build();

        final ChannelAssociation regressionCASFTPForFilteringByPubDateSuccODDWithOptionAfterOneDayDiffInFuture = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateSuccODDWithOptionAfterOneDayDiffInFuture)
                .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilteringByPubDateSuccODDWithOptionAfterOneDayDiffInFuture)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForFilteringByPubDateSuccODDWithOptionAfterOneDayDiffInFuture,
                regressionCASFTPForFilteringByPubDateSuccODDWithOptionAfterOneDayDiffInFuture)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyPublicationDateFilterSuccODDWithOptionOneDayDiffInFuture/9780136161422.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(getServerLocalDateNow().minusDays(1))
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyPublicationDateFilterSuccODDWithOptionOneDayDiffInFuture/assets/9780136161422.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyPublicationDateFilterSuccODDWithOptionOneDayDiffInFuture/assets/9780136161422_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata(),
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        Arrays.asList(onix.toAsset(), content, collateral).forEach(asset -> {
            String assetDistPath = asset.getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName())
                    .getDistributedPath();

            assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                    .as("No file by path '{}' has been found on channel two.", assetDistPath)
                    .isTrue();
        });
    }

    @TmsLink("AUT-265")
    @Test(groups = {"singleODD", "publicationDateFilter"})
    public void verifyPublicationDateFilterSuccODDWithOptionOneDayDiffInFutureBeforeOptionIsSelectedInCA() {
        final DistributionRule regressionFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture = DistributionRule.builder()
                .pubDateFilterActive(true)
                .pubDateFilterBefore(true)
                .pubDateFilterValue(getServerLocalDateNow().minusDays(1))
                .build();

        final ChannelAssociation regressionCASFTPForFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture)
                .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture,
                regressionCASFTPForFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyPublicationDateFilterSuccODDWithOptionOneDayDiffInFuture/9780136161433.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(getServerLocalDateNow())
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyPublicationDateFilterSuccODDWithOptionOneDayDiffInFuture/assets/9780136161433.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyPublicationDateFilterSuccODDWithOptionOneDayDiffInFuture/assets/9780136161433_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .searchPresentProductBy(onix.getSingleProductTitle())
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata(),
                        channelAndCA.getAssetTypeFromSingleContent(),
                        channelAndCA.getAssetTypeFromSingleCollateral())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        Arrays.asList(onix.toAsset(), content, collateral).forEach(asset -> {
            String assetDistPath = asset.getSuccessDistrStatusDetailsTo(channelAndCA.getChannelName())
                    .getDistributedPath();

            assertThat(getFileTestService().isFileExistsOnSFTPServer(assetDistPath))
                    .as("No file by path '{}' has been found on channel two.", assetDistPath)
                    .isTrue();
        });
    }

    @TmsLink("AUT-266")
    @Test(groups = {"autoDistribution", "publicationDateFilter"})
    public void verifyPublicationDateFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode() {
        final DistributionRule regressionPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode = DistributionRule.builder()
                .discountCode("Agency", "This is discount code for automation testing")
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow().plusDays(1))
                .build();

        final ChannelAssociation regressionCASFTPForPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode)
                .associationServer(AssociationSrvs.regressionAssocSrvSFTPForPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode,
                regressionCASFTPForPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode)
                .createChannelWithCA();

        LocalDate updatedOnixPubDate = getServerLocalDateNow().plusDays(2);
        OnixTestService onix = new OnixTestService("regression/verifyPublicationDateFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode/9780136161402.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(updatedOnixPubDate)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyPublicationDateFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode/assets/9780136161402.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyPublicationDateFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode/assets/9780136161402_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(updatedOnixPubDate)
                .as("Publication Date Value in CA should be after Publication Date in Onix")
                .isAfter(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isAfter(actualCADetailsFromDB.getPublicationDateFilterValue());

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-267")
    @Test(groups = {"autoDistribution", "publicationDateFilter", "negative"})
    public void verifyPublicationDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode() {
        final DistributionRule regressionPubDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode = DistributionRule.builder()
                .discountCode("PHE", "This is discount code for automation testing")
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow().plusDays(2))
                .build();

        final ChannelAssociation regressionCASFTPForPubDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionPubDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode)
                .associationServer(AssociationSrvs.regressionAssocSrvSFTPForPubDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicationDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode,
                regressionCASFTPForPubDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode)
                .createChannelWithCA();

        LocalDate updatedOnixPubDate = getServerLocalDateNow().plusDays(1);
        OnixTestService onix = new OnixTestService("regression/verifyPublicationDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode/9780136161403.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(updatedOnixPubDate)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyPublicationDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode/assets/9780136161403.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyPublicationDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode/assets/9780136161403_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(updatedOnixPubDate)
                .as("Invalid Publication Date Value in CA vs Publication Date in Onix")
                .isBefore(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isBefore(actualCADetailsFromDB.getPublicationDateFilterValue());

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());
    }

    @TmsLink("AUT-268")
    @Test(groups = {"autoDistribution", "publicationDateFilter", "negative"})
    public void verifyPublicationDateFilterFailPathWithDiscountCode() {
        @NotNull LocalDate onixPubDate = getServerLocalDateNow().plusDays(1);

        final DistributionRule regressionPubDateFilterFailODDWithValidFilterAndInvalidDiscountCodeRule = DistributionRule.builder()
                .discountCode("INV", "Invalid discount code for automation testing")
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow())
                .build();

        final ChannelAssociation regressionCASFTPForPubDateFilterFailODDWithValidFilterAndInvalidDiscountCode = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionPubDateFilterFailODDWithValidFilterAndInvalidDiscountCodeRule)
                .associationServer(AssociationSrvs.regressionAssocSrvSFTPForPubDateFilterFailODDWithValidFilterAndInvalidDiscountCode)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubDateFilterFailODDWithValidFilterAndInvalidDiscountCode,
                regressionCASFTPForPubDateFilterFailODDWithValidFilterAndInvalidDiscountCode)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyPublicationDateFilterFailODDWithValidFilterAndInvalidDiscountCode/9780136161404.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(onixPubDate)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyPublicationDateFilterFailODDWithValidFilterAndInvalidDiscountCode/assets/9780136161404.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyPublicationDateFilterFailODDWithValidFilterAndInvalidDiscountCode/assets/9780136161404_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(onixPubDate)
                .as("Invalid Publication Date Value in CA vs Publication Date in Onix")
                .isAfter(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isAfter(actualCADetailsFromDB.getPublicationDateFilterValue());

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());
    }


    @TmsLink("AUT-269")
    @Test(groups = {"autoDistribution", "publicationDateFilter", "negative"})
    public void verifyPublicationDateFilterFailPathWithDiscountCodeAfterOptionIsSelected() {
        @NotNull LocalDate onixPubDate = getServerLocalDateNow().plusDays(2);

        final DistributionRule regressionPubDateFilterFailODDWithValidFilterAndMissedDiscountCodeRule = DistributionRule.builder()
                .discountCode("PHE", "Missed discount code for automation testing")
                .pubDateFilterActive(true)
                .pubDateFilterBefore(false)
                .pubDateFilterValue(getServerLocalDateNow())
                .build();

        final ChannelAssociation regressionCASFTPForPubDateFilterFailODDWithValidFilterAndMissedDiscountCode = ChannelAssociation.builder()
                .automatedDistributionRules(true)
                .distributionRule(regressionPubDateFilterFailODDWithValidFilterAndMissedDiscountCodeRule)
                .associationServer(AssociationSrvs.regressionAssocSrvSFTPForPubDateFilterFailODDWithValidFilterAndMissedDiscountCode)
                .build();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubDateFilterFailODDWithValidFilterAndMissedDiscountCode,
                regressionCASFTPForPubDateFilterFailODDWithValidFilterAndMissedDiscountCode)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyPublicationDateFilterFailODDWithValidFilterAndMissedDiscountCode/9780136161405.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .updatePublicationDates(onixPubDate)
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyPublicationDateFilterFailODDWithValidFilterAndMissedDiscountCode/assets/9780136161405.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyPublicationDateFilterFailODDWithValidFilterAndMissedDiscountCode/assets/9780136161405_marketingimage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAssociationDetails actualCADetailsFromDB = getChannelAssociationDetailsFor(channelAndCA.getChannelName());
        assertThat(onixPubDate)
                .as("Invalid Publication Date Value in CA vs Publication Date in Onix")
                .isAfter(channelAndCA.getDistributionRule().getPubDateFilterValue())
                .as("Selected Publication filter date is not today. " +
                        ("Note. If test fails due to 1 day (more/less) difference check " +
                                "that the test wasn't executed on the edge of 2 days."))
                .isAfter(actualCADetailsFromDB.getPublicationDateFilterValue());

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());
    }

    @DataProvider(name = "tokenReplacementForFeedTestData")
    private Object[][] tokenReplacementForFeedTestData() {
        return new Object[][]{
                {"regression/verifyTokenReplacementForOnixFeedDistr/9781424551366.xml",
                        regressionPublicChannelSFTPForTokenReplacementForOnixFeedDistXFER,
                        regressionCASFTPForTokenReplacementForOnixFeedDistXFER,
                        PathFormatDateTimeOption.XFER_YYYYMMDDHHMMSSSSS},
                {"regression/verifyTokenReplacementForOnixFeedDistr/9781424551399.xml",
                        regressionPublicChannelSFTPForTokenReplacementForOnixFeedDistUPD,
                        regressionCASFTPForTokenReplacementForOnixFeedDistUPD,
                        PathFormatDateTimeOption.UPD_YYYYMMDDHHMMSSSSS}
        };
    }

    @TmsLink("AUT-270")
    @Test(dataProvider = "tokenReplacementForFeedTestData", groups = {"feedDistributionOptions", "singleODD"})
    public void verifyTokenReplacementForOnixFeedDistr(@NotNull String onixFilePath,
                                                       ChannelPublic channel,
                                                       ChannelAssociation ca,
                                                       PathFormatDateTimeOption dateTimeOption) {
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

        String distributedPath = onix.toAsset().getDistrPathFor(channelAndCA.getChannelName());

        final String DESTINATION_PATH_TEMPLATE = channelAndCA.getSingleMetadata().getDestinationFolder();
        final String ACTUAL_ASSET_TYPE = getPathFormatOptionValueFrom(distributedPath, DESTINATION_PATH_TEMPLATE, PathFormatTextOption.ASSET_TYPE);
        final String EXPECTED_ASSET_TYPE = AssetType.ONIX21.getDistributionPathValue();
        assertThat(ACTUAL_ASSET_TYPE)
                .as("Expected to have '{}' as folder name", EXPECTED_ASSET_TYPE)
                .isEqualTo(EXPECTED_ASSET_TYPE);

        final String ACTUAL_DATE_TIME = getPathFormatOptionValueFrom(distributedPath, DESTINATION_PATH_TEMPLATE, dateTimeOption);
        assertThat(convertToLocalDateTime(dateTimeOption, ACTUAL_DATE_TIME))
                .as("Date and time in the distr path is not close to now")
                .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

        assertThat(distributedPath).startsWith("Automation/" + ACTUAL_DATE_TIME + FILE_PATH_DELIMITER +
                EXPECTED_ASSET_TYPE + FILE_PATH_DELIMITER);

        assertThat(getFileTestService().isFileExistsOnSFTPServer(distributedPath))
                .as("File by path " + distributedPath + " does not exist on channel two ")
                .isTrue();
    }

    @TmsLink("AUT-271")
    @Test(groups = {"feedDistributionOptions", "singleODD", "negative"})
    public void verifyTokenReplacementIgnoredForOnixFeedDistribution() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChaSFTPForTokenReplacementIgnoredForOnixFeedDistribution,
                regressionCASFTPForTokenReplacementIgnoredForOnixFeedDistribution)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyTokenReplacementIgnoredForOnixFeedDistribution/9780807587400.xml")
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
        String actualAssetDistrPath = metadata.getDistrPathFor(channelAndCA.getChannelName());
        assertThat(actualAssetDistrPath)
                .as("Feed distribution path contains record reference")
                .doesNotContain(onix.getSingleProductRecordReference());

        String actualBuName = getOptionValueFromFeed(actualAssetDistrPath, FeedPathOption.ACTUAL_BU_NAME);
        assertThat(actualBuName)
                .as("Wrong BU in the feed distribution path")
                .isEqualTo(PredBUs.PRED_AUTOMATION_BU.getName());

        String actualChannelName = getOptionValueFromFeed(actualAssetDistrPath, FeedPathOption.CHANNEL_NAME);
        assertThat(actualChannelName)
                .as("Wrong Channel in the feed distribution path")
                .isEqualTo(channelAndCA.getChannelName());

        String date = getOptionValueFromFeed(actualAssetDistrPath, FeedPathOption.DATE);
        String time = getOptionValueFromFeed(actualAssetDistrPath, FeedPathOption.HOURS_MIN_SEC);
        String mls = getOptionValueFromFeed(actualAssetDistrPath, FeedPathOption.MLS);

        assertThat(convertToLocalDateTime(PathFormatDateTimeOption.XFER_YYYYMMDDHHMMSSSSS, date + time + mls))
                .as("Date and time in the distr path is not close to now")
                .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.maxDistributionTimeout(), ChronoUnit.SECONDS));

        assertThat(getFileTestService().isFileExistsOnSFTPServer(actualAssetDistrPath))
                .as("File '{}' should exist on crashFTP.", actualAssetDistrPath)
                .isTrue();
    }

    @TmsLink("AUT-408")
    @Test(groups = {"caServerSettings", "autoDistribution"})
    public void verifyAutoDistributionSendSingleProductsInSingleOnixFileOptionOnix30() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPForAutoDistrSendSingleProductsInSingleOnixFileOnix30,
                regressionCASFTPForAutoSendSingleProductsInSingleOnixFileOnix30)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyAutoDistributionSendSingleProductsInSingleOnixFileOptionOnix30/9780807536777.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        List<OnixProduct> products = onix.getProducts();
        final int EXPECTED_PRODUCT_QUANTITY_IN_ONIX = 2;
        assertThat(products)
                .as("Invalid number of products in Onix file")
                .hasSize(EXPECTED_PRODUCT_QUANTITY_IN_ONIX);

        getProductAPIService().triggerAutoDistribution();

        List<String> expectedDistrPaths = onix.getFileNamesWithRecRef()
                .stream()
                .map(recRef -> "ONIX 3.0" + FILE_PATH_DELIMITER + recRef)
                .collect(Collectors.toList());

        List<String> actualDistrPaths = onix.toAssets()
                .stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList());

        assertThat(actualDistrPaths)
                .as("Onix file is not distributed as individual file")
                .hasSameSizeAs(expectedDistrPaths)
                .hasSameElementsAs(expectedDistrPaths);
    }

    @TmsLink("AUT-752")
    @Test
    public void verifyDistributeWhenTagMatchForOnix21() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .clickCreateTagFor(PredBUs.PRED_AUTOMATION_BU.getName())
                .createProductTag(TAG_ENTITY_TAG_MATCH_FOR_ONIX_21)
                .getUploadedTagName(TAG_ENTITY_TAG_MATCH_FOR_ONIX_21.getTagName())
                .shouldBe(Condition.visible);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPForDistributeWhenTagMatchForOnix21,
                regressionChannelAssociationSFTPForDistributeWhenTagMatchForOnix21)
                .createChannelWithCA();

        OnixTestService onixShouldBeDistributed = new OnixTestService("regression/verifyDistributeWhenTagMatchForOnix21/9780136161460.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onixShouldNotBeDistributed = new OnixTestService("regression/verifyDistributeWhenTagMatchForOnix21/9780136161491.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_TAG_MATCH_FOR_ONIX_21)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        onixShouldBeDistributed.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        assertThat(onixShouldNotBeDistributed.toAsset().isDistributionScheduledFor(channelAndCA.getChannelName()))
                .as("The record appears in precompdd table is unexpected.")
                .isFalse();
    }

    @TmsLink("AUT-752")
    @Test
    public void verifyDistributeWhenTagMatchForOnix30() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .clickCreateTagFor(PredBUs.PRED_AUTOMATION_BU.getName())
                .createProductTag(TAG_ENTITY_TAG_MATCH_FOR_ONIX_30)
                .getUploadedTagName(TAG_ENTITY_TAG_MATCH_FOR_ONIX_30.getTagName())
                .shouldBe(Condition.visible);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPForDistributeWhenTagMatchForOnix30,
                regressionChannelAssociationSFTPForDistributeWhenTagMatchForOnix30)
                .createChannelWithCA();

        OnixTestService onixShouldBeDistributed = new OnixTestService("regression/verifyDistributeWhenTagMatchForOnix30/9780807596421.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onixShouldNotBeDistributed = new OnixTestService("regression/verifyDistributeWhenTagMatchForOnix30/9780807596422.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_TAG_MATCH_FOR_ONIX_30)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        onixShouldBeDistributed.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());

        assertThat(onixShouldNotBeDistributed.toAsset().isDistributionScheduledFor(channelAndCA.getChannelName()))
                .as("The record appears in precompdd table is unexpected.")
                .isFalse();
    }

    @TmsLink("AUT-752")
    @Test
    public void verifyDistributeWhenTagDoesNotMatchForOnix21() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        getTopMenu()
                .clickManageBusinessUnitWithJS()
                .clickCreateTagFor(PredBUs.PRED_AUTOMATION_BU.getName())
                .createProductTag(TAG_ENTITY_TAG_DOES_NOT_MATCH_FOR_ONIX_21)
                .getUploadedTagName(TAG_ENTITY_TAG_DOES_NOT_MATCH_FOR_ONIX_21.getTagName())
                .shouldBe(Condition.visible);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPForDistributeWhenTagDoesNotMatchForOnix21,
                regressionChannelAssociationSFTPForDistributeWhenTagDoesNotMatchForOnix21)
                .createChannelWithCA();

        OnixTestService onixShouldBeDistributed = new OnixTestService("regression/verifyDistributeWhenTagDoesNotMatchForOnix21/9780136161465.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixTestService onixShouldNotBeDistributed = new OnixTestService("regression/verifyDistributeWhenTagDoesNotMatchForOnix21/9780136161493.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_TAG_DOES_NOT_MATCH_FOR_ONIX_21)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onixShouldBeDistributed, onixShouldNotBeDistributed).forEach(onix ->
                assertThat(onix.toAsset().isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse()
        );
    }

    @TmsLink("HRV-29861")
    @Test(groups = {"autoDistribution"})
    public void verifyEnablingMarkExternalForNewChannel() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        OnixTestService onix = new OnixTestService("regression/verifyEnablingMarkExternalForNewChannel/9781424550136.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyEnablingMarkExternalForNewChannel/assets/9781424550136.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAndCATestService channelAndCAMarkExternalTrue = new ChannelAndCATestService(
                publicChannelFTPForAutoDistrEnablingMarkExternalForNewChannelTrue,
                caFTPForAutoDistrEnablingMarkExternalForNewChannelTrue)
                .createChannelWithCA();

        ChannelAndCATestService channelAndCAMarkExternalFalse = new ChannelAndCATestService(
                publicChannelFTPForAutoDistrEnablingMarkExternalForNewChannelFalse,
                caFTPForAutoDistrEnablingMarkExternalForNewChannelFalse)
                .createChannelWithCA();

        getProductAPIService().triggerAutoDistribution();

        content.checkIsNotDistributedTo(channelAndCAMarkExternalTrue.getChannelName());
        content.waitOnSuccessfulExternalDistributionTo(channelAndCAMarkExternalTrue.getChannelName());

        assertThat(content.isDistributionScheduledFor(channelAndCAMarkExternalFalse.getChannelName()))
                .as("Record in precombdd table is expected for '" + channelAndCAMarkExternalFalse.getChannelName()
                        + "', '" + content.getFileName() + "'.")
                .isTrue();

        content.waitOnSuccessfulDistributionTo(channelAndCAMarkExternalFalse.getChannelName());
    }

    @TmsLink("HRV-29861")
    @Test(groups = {"autoDistribution"})
    public void verifyEnablingMarkExternalForEditedChannel() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        OnixTestService onix = new OnixTestService("regression/verifyEnablingMarkExternalForEditedChannel/9780807595566.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyEnablingMarkExternalForEditedChannel/assets/9780807595566.epub")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                publicChannelFTPForAutoDistrEnablingMarkExternalForEditedChannel,
                caFTPForAutoDistrEnablingMarkExternalForEditChannel)
                .createChannelWithCA();

        getProductAPIService().triggerAutoDistribution();

        content.checkIsNotDistributedTo(channelAndCA.getChannelName());
        content.waitOnSuccessfulExternalDistributionTo(channelAndCA.getChannelName());

        getTopMenu()
                .clickManageChannelWithJS()
                .openChannelAssociationViewPage(channelAndCA.getChannelName())
                .clickEditChannelAssociation()
                .editChannelAssociationWith(regressionChannelAssociationFTPForMarkExternalUpdateFalse)
                .clickSaveAndWaitOnSuccess();

        getProductAPIService().triggerAutoDistribution();

        content.waitOnSuccessfulExternalDistributionTo(channelAndCA.getChannelName());
        content.checkIsNotDistributedTo(channelAndCA.getChannelName());
    }

    @TmsLink("HRV-15035")
    @Test(groups = {"manageEvents"})
    public void verifyManageEventsDisplaying() {
        getLoginPage().loginAs(SUPER_ADMIN);
        boolean isEventsAvailable = AccountDBService.getAccountBy(PredAccounts.AQA_ACCOUNT.getName()).isEventsEnabledYn();
        assertThat(isEventsAvailable).as("Events checkbox value is not expected").isTrue();
        getTopMenuTestService().selectDefaultTestAccountAndBu();
        getTopMenu().clickManageEvents().getPageTitleElm().shouldBe(visible);

        isEventsAvailable = AccountDBService.getAccountBy(PredAccounts.AQA_ACCOUNT_2.getName()).isEventsEnabledYn();
        assertThat(isEventsAvailable).as("Events checkbox value is not expected").isFalse();
        getTopMenuTestService().selectSecondTestAccountAndBu();
        getTopMenu().getEventsDropdownLnk().shouldNotBe(visible);
    }

    @TmsLink("HRV-15037")
    @Test(groups = {"event", "manageEvents"})
    public void verifySuperAdminCanAddEvent() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        final String eventTitle = generateUniqueStringBasedOnDate("AUTO add event ");
        ManageEventsPage mngEventsPage = getTopMenu()
                .clickManageEvents()
                .clickCreateNewEvent();

        mngEventsPage
                .getEventNameInput().shouldHave(attribute("placeholder", mngEventsPage.getEventNameInputPrePopulatedText()));

        mngEventsPage
                .setEventName(eventTitle)
                .selectStartDate(now())
                .clickAddRequiredParameters()
                .selectEndDate(now())
                .setNotes(generateUniqueStringBasedOnDate("AUTO add note "))
                .clickAddOptionalParameters()
                .clickAddEvent();

        SelenideElement successCreatedMsgElm = mngEventsPage.getSuccessCreatedEventMsgElm();
        successCreatedMsgElm.shouldBe(visible);
        successCreatedMsgElm.shouldHave(text(mngEventsPage.getSuccessCreatedEventMsgText()));
    }

    @TmsLink("HRV-15037")
    @Test(groups = {"event", "manageEvents"})
    public void verifyDuplicateEventCreation() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        final String eventTitle = generateUniqueStringBasedOnDate("AUTO duplicate event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(eventTitle)
                .selectStartDate(now())
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        manageEventsPage.getEventRow(eventTitle, changeDateFormat(now(), DatePattern.EVENT_VIEW)).shouldBe(visible);

        SelenideElement duplicateMsgElm = manageEventsPage
                .clickCreateNewEvent()
                .setEventName(eventTitle)
                .selectStartDate(now())
                .getDuplicateEventMsgElm();

        duplicateMsgElm.shouldBe(visible);
        duplicateMsgElm.shouldHave(text("Oops. Looks like there is an existing event"));
    }

    @TmsLink("HRV-15037")
    @Test(groups = {"event", "manageEvents"})
    public void verifyEventCreationWithStartDateAfterEndDate() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        final String eventTitle = generateUniqueStringBasedOnDate("AUTO event start after end ");
        ManageEventsPage mngEventsPage = getTopMenu()
                .clickManageEvents()
                .clickCreateNewEvent()
                .setEventName(eventTitle)
                .selectStartDate(now().plusDays(1))
                .clickAddRequiredParameters()
                .selectEndDate(now().minusDays(1))
                .clickAddOptionalParameters()
                .clickAddEvent();

        SelenideElement successCreatedMsgElm = mngEventsPage.getSuccessCreatedEventMsgElm();
        successCreatedMsgElm.shouldBe(visible);
        successCreatedMsgElm.shouldHave(text(mngEventsPage.getSuccessCreatedEventMsgText()));
    }

    @TmsLink("HRV-15037")
    @Test(groups = {"event", "manageEvents"})
    public void verifyEventDeletion() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        final String eventTitle = generateUniqueStringBasedOnDate("AUTO event deletion ");
        final LocalDate date = now().minusDays(1);
        final String dateStringFormat = changeDateFormat(date, DatePattern.EVENT_VIEW);
        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents()
                .clickCreateNewEvent()
                .setEventName(eventTitle)
                .selectStartDate(date)
                .clickAddRequiredParameters()
                .selectEndDate(date)
                .clickAddOptionalParameters()
                .clickAddEvent();

        manageEventsPage.getEventRow(eventTitle, dateStringFormat).shouldBe(visible);
        DeleteEventsPage deleteEventsPage = manageEventsPage.clickDeleteEvent();

        deleteEventsPage.getRowWith(eventTitle).shouldBe(visible);
        deleteEventsPage
                .deleteEvent(eventTitle)
                .getRowWith(eventTitle)
                .shouldNotBe(visible);
    }

    @TmsLink("HRV-29893")
    @Test(groups = {"event", "manageEvents"})
    public void verifyPastFutureAndCurrentEventsView() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu().clickManageEvents();

        int numOfTotalEventsBeforeCreation = manageEventsPage.getTotalNumOfEvents();
        int numOfOccuringNowEventsBeforeCreation = manageEventsPage.getNumOfEventsOccurringNow();
        int numOfUpcomingEventsBeforeCreation = manageEventsPage.getNumOfUpcomingEvents();

        final String pastEventTitle = generateUniqueStringBasedOnDate("AUTO past event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(pastEventTitle)
                .selectStartDate(now().minusDays(5))
                .clickAddRequiredParameters()
                .selectEndDate(now().minusDays(1))
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getEventRow(pastEventTitle, changeDateFormat(now().minusDays(5), DatePattern.EVENT_VIEW))
                .shouldBe(visible);

        final String occurEventTitle = generateUniqueStringBasedOnDate("AUTO occur event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(occurEventTitle)
                .selectStartDate(now().minusDays(1))
                .clickAddRequiredParameters()
                .selectEndDate(now().plusDays(2))
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getEventRow(occurEventTitle, changeDateFormat(now().minusDays(1), DatePattern.EVENT_VIEW))
                .shouldBe(visible);

        final String upcomingEventTitle = generateUniqueStringBasedOnDate("AUTO upcoming event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(upcomingEventTitle)
                .selectStartDate(now().plusDays(10))
                .clickAddRequiredParameters()
                .selectEndDate(now().plusDays(14))
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getEventRow(upcomingEventTitle, changeDateFormat(now().plusDays(10), DatePattern.EVENT_VIEW))
                .shouldBe(visible);

        assertThat(manageEventsPage.getTotalNumOfEvents())
                .as("Invalid total number of events.")
                .isEqualTo(numOfTotalEventsBeforeCreation + 3);

        assertThat(manageEventsPage.getNumOfEventsOccurringNow())
                .as("Invalid number of occuring now events.")
                .isEqualTo(numOfOccuringNowEventsBeforeCreation + 1);

        assertThat(manageEventsPage.getNumOfUpcomingEvents())
                .as("Invalid number of upcoming events.")
                .isEqualTo(numOfUpcomingEventsBeforeCreation + 1);
    }

    @TmsLink("HRV-29893")
    @Test(groups = {"event", "manageEvents"})
    public void verifyCancelingEventsViewCreation() {
        getLoginPage().loginAs(SUPER_ADMIN);
        ManageEventsPage manageEventsPage = getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .clickManageEvents();

        final String cancelEventTitle = generateUniqueStringBasedOnDate("AUTO cancel event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(cancelEventTitle)
                .selectStartDate(now())
                .clickCancel()
                .clickCreateNewEvent()
                .setEventName(cancelEventTitle)
                .selectStartDate(now())
                .clickAddRequiredParameters()
                .selectEndDate(now().plusDays(20))
                .clickCancel()
                .clickCreateNewEvent()
                .setEventName(cancelEventTitle)
                .selectStartDate(now())
                .clickAddRequiredParameters()
                .selectEndDate(now().plusDays(30))
                .clickAddOptionalParameters()
                .clickCancel();

        List<String> actualEventTitles = manageEventsPage
                .getListOfEvents().stream()
                .map(EventView::getTitle)
                .collect(Collectors.toList());

        assertThat(actualEventTitles)
                .as("Invalid total number of events.")
                .isNotEmpty()
                .doesNotContain(cancelEventTitle);
    }

    @TmsLink("HRV-29980")
    @Test(groups = {"event", "manageEvents"})
    public void verifyEventsViewSorting() {
        getLoginPage().loginAs(SUPER_ADMIN);
        ManageEventsPage manageEventsPage = getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .clickManageEvents();

        final String pastEventTitle = generateUniqueStringBasedOnDate("AUTO past event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(pastEventTitle)
                .selectStartDate(now().minusDays(5))
                .clickAddRequiredParameters()
                .selectEndDate(now().minusDays(1))
                .clickAddOptionalParameters()
                .clickAddEvent();

        final String occurEventTitle = generateUniqueStringBasedOnDate("AUTO occur event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(occurEventTitle)
                .selectStartDate(now().minusDays(1))
                .clickAddRequiredParameters()
                .selectEndDate(now().plusDays(2))
                .clickAddOptionalParameters()
                .clickAddEvent();

        final String upcomingEventTitle = generateUniqueStringBasedOnDate("AUTO upcoming event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(upcomingEventTitle)
                .selectStartDate(now().plusDays(10))
                .clickAddRequiredParameters()
                .selectEndDate(now().plusDays(14))
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getEventRow(upcomingEventTitle, changeDateFormat(now().plusDays(10), DatePattern.EVENT_VIEW))
                .shouldBe(visible);

        manageEventsPage.setEventSortingAs(true);

        List<String> actualTitlesDesc = manageEventsPage
                .getListOfEvents().stream()
                .map(EventView::getTitle)
                .collect(Collectors.toList())
                .stream()
                .filter(title -> (title.equals(pastEventTitle)
                        || title.equals(occurEventTitle)
                        || title.equals(upcomingEventTitle)))
                .collect(Collectors.toList());

        assertThat(actualTitlesDesc)
                .as("Invalid order of titles in event view table.")
                .containsSequence(upcomingEventTitle, occurEventTitle, pastEventTitle);

        manageEventsPage.setEventSortingAs(false);

        List<String> actualTitlesAsc = manageEventsPage
                .getListOfEvents().stream()
                .map(EventView::getTitle)
                .collect(Collectors.toList())
                .stream()
                .filter(title -> (title.equals(pastEventTitle)
                        || title.equals(occurEventTitle)
                        || title.equals(upcomingEventTitle)))
                .collect(Collectors.toList());

        assertThat(actualTitlesAsc)
                .as("Invalid order of titles in event view table.")
                .containsSequence(pastEventTitle, occurEventTitle, upcomingEventTitle);
    }

    @TmsLink("HRV-29980")
    @Test(groups = {"event", "manageEvents"})
    public void verifyShowHideUpcommingEvents() {
        getLoginPage().loginAs(SUPER_ADMIN);
        ManageEventsPage manageEventsPage = getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .clickManageEvents();

        final String pastEventTitle = generateUniqueStringBasedOnDate("AUTO past event ");
        LocalDate pastEventStartDate = now().minusDays(6);
        LocalDate pastEventEndDate = now().minusDays(1);
        manageEventsPage.clickCreateNewEvent()
                .setEventName(pastEventTitle)
                .selectStartDate(pastEventStartDate)
                .clickAddRequiredParameters()
                .selectEndDate(pastEventEndDate)
                .clickAddOptionalParameters()
                .clickAddEvent();

        final String occurEventTitle = generateUniqueStringBasedOnDate("AUTO occur event ");
        LocalDate occurEventStartDate = now();
        LocalDate occurEventEndDate = now().plusDays(5);
        manageEventsPage.clickCreateNewEvent()
                .setEventName(occurEventTitle)
                .selectStartDate(occurEventStartDate)
                .clickAddRequiredParameters()
                .selectEndDate(occurEventEndDate)
                .clickAddOptionalParameters()
                .clickAddEvent();

        final String upcomingEventTitle = generateUniqueStringBasedOnDate("AUTO upcoming event ");
        LocalDate upcomingEventStartDate = now().plusDays(7);
        LocalDate upcomingEventEndDate = now().plusDays(10);
        manageEventsPage.clickCreateNewEvent()
                .setEventName(upcomingEventTitle)
                .selectStartDate(upcomingEventStartDate)
                .clickAddRequiredParameters()
                .selectEndDate(upcomingEventEndDate)
                .clickAddOptionalParameters()
                .clickAddEvent();

        // Verify events in the list.
        List<EventView> actualTitles = manageEventsPage.getListOfEvents();

        EventView actualPastEvent = actualTitles.stream()
                .filter(event -> event.getTitle().equals(pastEventTitle))
                .collect(CustomCollectors.toSingleton());

        LocalDate actualPastEventStartDate = convertToLocalDate(DatePattern.EVENT_VIEW, actualPastEvent.getStartDate());
        assertThat(actualPastEventStartDate)
                .as("Invalid start date for past event.")
                .isEqualTo(pastEventStartDate);

        LocalDate actualPastEventEndDate = convertToLocalDate(DatePattern.EVENT_VIEW, actualPastEvent.getEndDate());
        assertThat(actualPastEventEndDate)
                .as("Invalid end date for past event.")
                .isEqualTo(pastEventEndDate);

        EventView actualOccurEvent = actualTitles.stream()
                .filter(event -> event.getTitle().equals(occurEventTitle))
                .collect(CustomCollectors.toSingleton());

        LocalDate actualOccurEventStartDate = convertToLocalDate(DatePattern.EVENT_VIEW, actualOccurEvent.getStartDate());
        assertThat(actualOccurEventStartDate)
                .as("Invalid start date for occur event.")
                .isEqualTo(occurEventStartDate);

        LocalDate actualOccurEventEndDate = convertToLocalDate(DatePattern.EVENT_VIEW, actualOccurEvent.getEndDate());
        assertThat(actualOccurEventEndDate)
                .as("Invalid end date for occur event.")
                .isEqualTo(occurEventEndDate);

        EventView actualUpcomingEvent = actualTitles.stream()
                .filter(event -> event.getTitle().equals(upcomingEventTitle))
                .collect(CustomCollectors.toSingleton());

        LocalDate actualUpcomingEventStartDate = convertToLocalDate(DatePattern.EVENT_VIEW, actualUpcomingEvent.getStartDate());
        assertThat(actualUpcomingEventStartDate)
                .as("Invalid start date for upcoming event.")
                .isEqualTo(upcomingEventStartDate);

        LocalDate actualUpcomingEventEndDate = convertToLocalDate(DatePattern.EVENT_VIEW, actualUpcomingEvent.getEndDate());
        assertThat(actualUpcomingEventEndDate)
                .as("Invalid end date for upcoming event.")
                .isEqualTo(upcomingEventEndDate);


        // Verify hiding upcoming events
        manageEventsPage.clickHideUpcomingBtn();

        manageEventsPage.getEventTitleElmInList(pastEventTitle).shouldBe(visible);
        manageEventsPage.getEventTitleElmInList(occurEventTitle).shouldBe(visible);
        manageEventsPage.getEventTitleElmInList(upcomingEventTitle).shouldNotBe(visible);

        manageEventsPage.clickShowUpcomingBtn();


        // Verify show / hide event details
        ElementsCollection eventDetailsElms = manageEventsPage.getEventDetailsElms();
        eventDetailsElms.filter(visible).shouldHave(CollectionCondition.size(0));

        manageEventsPage.clickShowEventDetails();

        eventDetailsElms.filter(visible).shouldHave(CollectionCondition.size(manageEventsPage.getTotalNumOfEvents()));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyUserIsAbleToEditEventTitleAndStartDateAndSaveChanges() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate initialStartDate = now();
        String initialDateStringFormat = changeDateFormat(initialStartDate, DatePattern.EVENT_VIEW);
        LocalDate updatedStartDate = initialStartDate.minusDays(10);
        String updatedDateStringFormat = changeDateFormat(updatedStartDate, DatePattern.EVENT_VIEW);

        final String eventTitle = generateUniqueStringBasedOnDate("AUTO init event ");
        final String updatedEventTitle = generateUniqueStringBasedOnDate("AUTO upd event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(eventTitle)
                .selectStartDate(initialStartDate)
                .clickAddRequiredParameters()
                .selectEndDate(now())
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(eventTitle, initialDateStringFormat).shouldBe();

        //update event name
        editEventsPage.updateEventName(row, updatedEventTitle);
        row = editEventsPage.getEventRow(updatedEventTitle, initialDateStringFormat);
        editEventsPage.clickSaveButton(row);

        //update event start date
        editEventsPage.updateStartDate(row, updatedStartDate);
        row = editEventsPage.getEventRow(updatedEventTitle, updatedDateStringFormat);
        editEventsPage.clickSaveButton(row)
                .getEventRow(updatedEventTitle, updatedDateStringFormat).shouldBe(visible);

        editEventsPage.clickImDoneWithChanges()
                .getEventRow(updatedEventTitle, updatedDateStringFormat).shouldBe(visible);
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyUserIsAbleToAddEndDateAndSaveChanges() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate startDate = now();
        String startDateStringFormat = changeDateFormat(startDate, DatePattern.EVENT_VIEW);
        LocalDate endDate = startDate.plusDays(5);
        String endDateStringFormat = changeDateFormat(endDate, DatePattern.EVENT_VIEW);

        final String eventTitle = generateUniqueStringBasedOnDate("AUTO add end date event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(eventTitle)
                .selectStartDate(startDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(eventTitle, startDateStringFormat);

        editEventsPage.addEndDate(row, endDate)
                .clickSaveButton(row)
                .getEndDate(row)
                .shouldHave(value(endDateStringFormat));

        editEventsPage.clickImDoneWithChanges()
                .getEventRow(eventTitle, startDateStringFormat).shouldHave(text(endDateStringFormat));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyUserIsAbleToEditEndDateAndSaveChanges() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate startDate = now();
        String startDateStringFormat = changeDateFormat(startDate, DatePattern.EVENT_VIEW);
        LocalDate endDate = startDate.plusDays(5);
        LocalDate updatedEndDate = startDate.plusDays(6);
        String updatedEndDateStringFormat = changeDateFormat(updatedEndDate, DatePattern.EVENT_VIEW);

        final String eventTitle = generateUniqueStringBasedOnDate("AUTO add end date event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(eventTitle)
                .selectStartDate(startDate)
                .clickAddRequiredParameters()
                .selectEndDate(endDate)
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(eventTitle, startDateStringFormat);

        editEventsPage.updateEndDate(row, updatedEndDate)
                .clickSaveButton(row)
                .getEndDate(row)
                .shouldHave(value(updatedEndDateStringFormat));

        editEventsPage.clickImDoneWithChanges()
                .getEventRow(eventTitle, startDateStringFormat).shouldHave(text(updatedEndDateStringFormat));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyUserIsAbleToDeleteEndDateAndSaveChanges() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate startDate = now();
        String startDateStringFormat = changeDateFormat(startDate, DatePattern.EVENT_VIEW);
        LocalDate endDate = startDate.plusDays(5);
        String endDateStringFormat = changeDateFormat(endDate, DatePattern.EVENT_VIEW);

        final String eventTitle = generateUniqueStringBasedOnDate("AUTO remove end date event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(eventTitle)
                .selectStartDate(startDate)
                .clickAddRequiredParameters()
                .selectEndDate(endDate)
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(eventTitle, startDateStringFormat);

        editEventsPage.deleteEndDate(row)
                .clickSaveButton(row)
                .getEndDate(row)
                .shouldNotHave(value(endDateStringFormat));

        editEventsPage.clickImDoneWithChanges()
                .getEventRow(eventTitle, startDateStringFormat).shouldNotHave(text(endDateStringFormat));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyDuplicatedTitleEventEditing() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate startDate = now();
        String startDateStringFormat = changeDateFormat(startDate, DatePattern.EVENT_VIEW);

        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO first event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(startDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        final String secondEventTitle = generateUniqueStringBasedOnDate("AUTO second event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(secondEventTitle)
                .selectStartDate(startDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(secondEventTitle, startDateStringFormat);

        editEventsPage.updateEventName(row, firstEventTitle)
                .getDuplicateErrorMsg()
                .shouldHave(text("Oops. Looks like there is an existing event on " + startDateStringFormat + "  with that same name"));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyDuplicatedStartDateEventEditing() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now();
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.EVENT_VIEW);
        LocalDate secondEventStartDate = now().plusDays(1);
        String secondEventStartDateStringFormat = changeDateFormat(secondEventStartDate, DatePattern.EVENT_VIEW);

        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        final String secondEventTitle = generateUniqueStringBasedOnDate("AUTO event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(secondEventTitle)
                .selectStartDate(secondEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(secondEventTitle, secondEventStartDateStringFormat)
                .shouldBe(visible);

        editEventsPage.updateEventName(row, firstEventTitle);
        row = editEventsPage.getEventRow(firstEventTitle, secondEventStartDateStringFormat);
        editEventsPage.clickSaveButton(row);

        editEventsPage.updateStartDate(row, firstEventStartDate)
                .getDuplicateErrorMsg()
                .shouldHave(text("Oops. Looks like there is an existing event on " + firstEventStartDateStringFormat + "  with that same name"));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyEmptyTitleEventEditing() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now();
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.EVENT_VIEW);

        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");
        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldBe(visible);

        editEventsPage.clearEventName(row)
                .clickSaveButton(editEventsPage.getEventRow("", firstEventStartDateStringFormat))
                .getSystemErrorMsg()
                .shouldHave(text("Sorry there was a problem editing your event. Please try again later."));

        editEventsPage
                .clickImDoneWithChanges();
        editEventsPage
                .clickImDoneWithDeleting()
                .getEventRow(firstEventTitle, firstEventStartDateStringFormat).shouldBe(visible);
        assertThat(manageEventsPage.getListOfEvents().stream()
                .noneMatch(eventView -> eventView.getTitle().equals("")))
                .as("Event with empty title is not expected")
                .isTrue();
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyAddCommentEventEditing() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now();
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");
        final String noteText = generateUniqueStringBasedOnDate("AUTO note ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldBe(visible);

        editEventsPage.addEventNotes(row, noteText)
                .clickSaveButton(row)
                .clickImDoneWithChanges();

        editEventsPage.clickImDoneWithDeleting();

        manageEventsPage.expandRow(firstEventTitle, firstEventStartDateStringFormat)
                .getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldHave(text(noteText));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyEditCommentEventEditing() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now();
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");
        final String noteText = generateUniqueStringBasedOnDate("AUTO note ");
        final String updatedNoteText = generateUniqueStringBasedOnDate("AUTO note ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .setNotes(noteText)
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldBe(visible);

        editEventsPage.updateEventNotes(row, updatedNoteText)
                .clickSaveButton(row)
                .getNotes(row).shouldHave(text(updatedNoteText));

        editEventsPage.clickImDoneWithChanges();

        manageEventsPage.expandRow(firstEventTitle, firstEventStartDateStringFormat)
                .getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldHave(text(updatedNoteText));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyDeleteCommentEventEditing() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now();
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");
        final String noteText = generateUniqueStringBasedOnDate("AUTO note ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .setNotes(noteText)
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldBe(visible);

        editEventsPage.deleteEventNotes(row)
                .clickSaveButton(row)
                .getNotes(row).shouldBe(empty);

        editEventsPage.clickImDoneWithChanges();

        manageEventsPage.expandRow(firstEventTitle, firstEventStartDateStringFormat)
                .getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldNotHave(text(noteText));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyUseThe30DaysBeforeTheStartOfTheEventEventEditing() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now();
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");
        LocalDate benchmarkStartDate = firstEventStartDate.minusDays(30);
        LocalDate benchmarkStopDate = firstEventStartDate.minusDays(15);

        String benchmarkStartDateStringFormat = changeDateFormat(benchmarkStartDate, DatePattern.EVENT_VIEW);
        String benchmarkStopDateStringFormat = changeDateFormat(benchmarkStopDate, DatePattern.EVENT_VIEW);

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .selectOption(EventOption.LET_ME_CHOOSE_THE_START_OF_THE_EVENT)
                .startBenchmarkOnStartStopDate(benchmarkStartDate, benchmarkStopDate)
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldBe(visible);

        editEventsPage.selectOption(row, EventOption.USE_30_DAYS_BEFORE_THE_START_OF_THE_EVENT);
        editEventsPage.getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldHave(text("Starts on " + benchmarkStartDateStringFormat + " Stops on " + benchmarkStopDateStringFormat));

        editEventsPage.clickSaveButton(row)
                .clickImDoneWithChanges();
        editEventsPage.clickImDoneWithDeleting();

        manageEventsPage.expandRow(firstEventTitle, firstEventStartDateStringFormat)
                .getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldNotHave(text(benchmarkStartDateStringFormat))
                .shouldNotHave(text(benchmarkStopDateStringFormat));
    }

    @TmsLink("HRV-15040")
    @Test(groups = {"event", "editEvents"})
    public void verifyLetMeChooseTheStartOfTheEventEventEditing() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now();
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");
        LocalDate benchmarkStartDate = firstEventStartDate.minusDays(30);
        LocalDate benchmarkStopDate = firstEventStartDate.minusDays(15);

        String benchmarkStartDateStringFormat = changeDateFormat(benchmarkStartDate, DatePattern.EVENT_VIEW);
        String benchmarkStopDateStringFormat = changeDateFormat(benchmarkStopDate, DatePattern.EVENT_VIEW);

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        EditEventsPage editEventsPage = manageEventsPage.clickEditEvents();
        SelenideElement row = editEventsPage.getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldBe(visible);

        editEventsPage.selectOption(row, EventOption.LET_ME_CHOOSE_THE_START_OF_THE_EVENT)
                .startBenchmarkOnStartStopDate(row, benchmarkStartDate, benchmarkStopDate)
                .clickSaveButton(row);

        editEventsPage.clickImDoneWithChanges();
        editEventsPage.clickImDoneWithDeleting();

        manageEventsPage.expandRow(firstEventTitle, firstEventStartDateStringFormat)
                .getEventRow(firstEventTitle, firstEventStartDateStringFormat)
                .shouldHave(text(benchmarkStartDateStringFormat))
                .shouldHave(text(benchmarkStopDateStringFormat));
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedStartDateInPastEndDateInPast() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now().minusDays(20);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .selectEndDate(firstEventStartDate.plusDays(10))
                .clickAddOptionalParameters()
                .clickAddEvent();

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        getCurrentActivityFeed().getTitleElm().shouldHave(exactText(TITLE));

        List<SelenideElement> resultList = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(Collectors.toList());

        assertThat(resultList).isEmpty();
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedStartDateInFutureEndDateInFuture() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now().plusDays(20);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .selectEndDate(firstEventStartDate.plusDays(10))
                .clickAddOptionalParameters()
                .clickAddEvent();

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        getCurrentActivityFeed().getTitleElm().shouldHave(exactText(TITLE));

        List<SelenideElement> resultList = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(Collectors.toList());

        assertThat(resultList).isEmpty();
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedStartDateInFutureNoEndDate() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now().plusDays(20);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent();

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        getCurrentActivityFeed().getTitleElm().shouldHave(exactText(TITLE));

        List<SelenideElement> resultList = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(Collectors.toList());

        assertThat(resultList).isEmpty();
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedStartDateInPastNoEndDate() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now().minusDays(1);
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.FEED_EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .selectOption(EventOption.USE_30_DAYS_BEFORE_THE_START_OF_THE_EVENT)
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        getCurrentActivityFeed().getTitleElm().shouldHave(exactText(TITLE));

        SelenideElement row = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(CustomCollectors.toSingleton());

        getCurrentActivityFeed().getEventStartDate(row)
                .shouldHave(text(firstEventStartDateStringFormat));

        getCurrentActivityFeed().getEventNumberOfProducts(row)
                .shouldHave(exactText("0"));

        getCurrentActivityFeed().getUser(row)
                .shouldHave(text("Manny Calavera"));
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedCurrentStartDateNoEndDate() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now();
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.FEED_EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .selectOption(EventOption.USE_30_DAYS_BEFORE_THE_START_OF_THE_EVENT)
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        getCurrentActivityFeed().getTitleElm().shouldHave(exactText(TITLE));

        SelenideElement row = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(CustomCollectors.toSingleton());

        getCurrentActivityFeed().getEventStartDate(row)
                .shouldHave(text(firstEventStartDateStringFormat));

        getCurrentActivityFeed().getEventNumberOfProducts(row)
                .shouldHave(exactText("0"));

        getCurrentActivityFeed().getUser(row)
                .shouldHave(text("Manny Calavera"));
    }

    @DataProvider(name = "ActivityFeedStartDateStartDateEndDate")
    private Object[][] ActivityFeedStartDateStartDateEndDate() {
        return new Object[][]{
                {LocalDate.now().minusDays(1), LocalDate.now().plusDays(10)},
                {LocalDate.now().minusDays(10), LocalDate.now()},
                {LocalDate.now(), LocalDate.now()},
                {LocalDate.now(), LocalDate.now().plusDays(10)}};
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"}, dataProvider = "ActivityFeedStartDateStartDateEndDate")
    public void verifyCurrentActivityFeedStartDateEndDate(LocalDate startDate, LocalDate endDate) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        String firstEventStartDateStringFormat = changeDateFormat(startDate, DatePattern.FEED_EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(startDate)
                .clickAddRequiredParameters()
                .selectEndDate(endDate)
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        getCurrentActivityFeed().getTitleElm().shouldHave(exactText(TITLE));

        SelenideElement row = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(CustomCollectors.toSingleton());

        getCurrentActivityFeed().getEventStartDate(row)
                .shouldHave(text(firstEventStartDateStringFormat));

        getCurrentActivityFeed().getEventNumberOfProducts(row)
                .shouldHave(exactText("0"));

        getCurrentActivityFeed().getUser(row)
                .shouldHave(text("Manny Calavera"));
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedDeleteEvent() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate firstEventStartDate = LocalDate.now();
        String firstEventStartDateStringFormat = changeDateFormat(firstEventStartDate, DatePattern.FEED_EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(firstEventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        getCurrentActivityFeed().getTitleElm().shouldHave(exactText(TITLE));

        SelenideElement row = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(CustomCollectors.toSingleton());

        getCurrentActivityFeed().getEventStartDate(row)
                .shouldHave(text(firstEventStartDateStringFormat));

        getCurrentActivityFeed().getEventNumberOfProducts(row)
                .shouldHave(exactText("0"));

        getCurrentActivityFeed().getUser(row)
                .shouldHave(text("Manny Calavera"));

        getTopMenu()
                .clickManageEvents().clickDeleteEvent()
                .deleteEvent(firstEventTitle)
                .getRowWith(firstEventTitle)
                .shouldNotBe(visible);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        List<SelenideElement> resultList = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(Collectors.toList());

        assertThat(resultList).isEmpty();
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedEditEndDateInPastEvent() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate eventStartDate = LocalDate.now().minusDays(10);
        LocalDate eventEndDate = LocalDate.now().minusDays(5);
        String eventStartDateStringFormat = changeDateFormat(eventStartDate, DatePattern.FEED_EVENT_VIEW);
        String eventEndDateStringFormat = changeDateFormat(eventEndDate, DatePattern.FEED_EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(eventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        SelenideElement row = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(CustomCollectors.toSingleton());

        getCurrentActivityFeed().getEventStartDate(row)
                .shouldHave(text(eventStartDateStringFormat));

        getCurrentActivityFeed().getEventNumberOfProducts(row)
                .shouldHave(exactText("0"));

        getCurrentActivityFeed().getUser(row)
                .shouldHave(text("Manny Calavera"));

        EditEventsPage editEventsPage = getTopMenu()
                .clickManageEvents().clickEditEvents();
        SelenideElement editEventRow = editEventsPage.getEventRow(firstEventTitle, eventStartDateStringFormat);
        editEventsPage.addEndDate(editEventRow, eventEndDate)
                .clickSaveButton(editEventRow)
                .getEndDate(editEventRow)
                .shouldHave(value(eventEndDateStringFormat));

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        List<SelenideElement> resultList = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(Collectors.toList());

        assertThat(resultList).isEmpty();
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedEditEndDateInFutureEvent() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate eventStartDate = LocalDate.now().minusDays(10);
        LocalDate eventEndDateInPast = LocalDate.now().minusDays(5);
        LocalDate eventEndInFutureDate = LocalDate.now().plusDays(5);
        String eventStartDateStringFormat = changeDateFormat(eventStartDate, DatePattern.FEED_EVENT_VIEW);
        String eventEndDateInFutureStringFormat = changeDateFormat(eventEndInFutureDate, DatePattern.FEED_EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(eventStartDate)
                .clickAddRequiredParameters()
                .selectEndDate(eventEndDateInPast)
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        List<SelenideElement> resultList = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(Collectors.toList());

        assertThat(resultList).isEmpty();

        EditEventsPage editEventsPage = getTopMenu()
                .clickManageEvents().clickEditEvents();
        SelenideElement editEventRow = editEventsPage.getEventRow(firstEventTitle, eventStartDateStringFormat);
        editEventsPage.updateEndDate(editEventRow, eventEndInFutureDate)
                .clickSaveButton(editEventRow)
                .getEndDate(editEventRow)
                .shouldHave(value(eventEndDateInFutureStringFormat));

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        SelenideElement row = getTopMenu().expandEventsDropdown().getElementsList()
                .filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(CustomCollectors.toSingleton());

        getCurrentActivityFeed().getEventStartDate(row)
                .shouldHave(text(eventStartDateStringFormat));

        getCurrentActivityFeed().getEventNumberOfProducts(row)
                .shouldHave(exactText("0"));

        getCurrentActivityFeed().getUser(row)
                .shouldHave(text("Manny Calavera"));
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedEditStartDateInFutureEvent() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate eventStartDate = LocalDate.now().minusDays(10);
        LocalDate eventStartDateInFuture = LocalDate.now().plusDays(5);
        String eventStartDateStringFormat = changeDateFormat(eventStartDate, DatePattern.FEED_EVENT_VIEW);
        String eventStartDateInFutureStringFormat = changeDateFormat(eventStartDateInFuture, DatePattern.FEED_EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(eventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        SelenideElement row = getTopMenu().expandEventsDropdown().getElementsList()
                .filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(CustomCollectors.toSingleton());

        getCurrentActivityFeed().getEventStartDate(row)
                .shouldHave(text(eventStartDateStringFormat));

        getCurrentActivityFeed().getEventNumberOfProducts(row)
                .shouldHave(exactText("0"));

        getCurrentActivityFeed().getUser(row)
                .shouldHave(text("Manny Calavera"));

        EditEventsPage editEventsPage = getTopMenu()
                .clickManageEvents().clickEditEvents();
        SelenideElement eventRow = editEventsPage.getEventRow(firstEventTitle, eventStartDateStringFormat);
        editEventsPage.updateStartDate(eventRow, eventStartDateInFuture);
        SelenideElement updatedEventRow = editEventsPage.getEventRow(firstEventTitle, eventStartDateInFutureStringFormat);

        editEventsPage.clickSaveButton(updatedEventRow);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        List<SelenideElement> resultList = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(Collectors.toList());

        assertThat(resultList).isEmpty();
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "currentActivityFeed"})
    public void verifyCurrentActivityFeedEditStartDateInPastEvent() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ManageEventsPage manageEventsPage = getTopMenu()
                .clickManageEvents();

        LocalDate eventStartDate = LocalDate.now().plusDays(10);
        LocalDate eventStartDateInPast = LocalDate.now().minusDays(5);
        String eventStartDateStringFormat = changeDateFormat(eventStartDate, DatePattern.EVENT_VIEW);
        String eventStartDateInPastStringFormat = changeDateFormat(eventStartDateInPast, DatePattern.EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        manageEventsPage.clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(eventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        ElementsCollection elementsList = getTopMenu().expandEventsDropdown().getElementsList();

        List<SelenideElement> resultList = elementsList.filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(Collectors.toList());

        assertThat(resultList).isEmpty();

        EditEventsPage editEventsPage = getTopMenu()
                .clickManageEvents().clickEditEvents();

        SelenideElement eventRow = editEventsPage.getEventRow(firstEventTitle, eventStartDateStringFormat);
        editEventsPage.updateStartDate(eventRow, eventStartDateInPast);
        SelenideElement updatedEventRow = editEventsPage.getEventRow(firstEventTitle, eventStartDateInPastStringFormat);

        editEventsPage.clickSaveButton(updatedEventRow);

        getEventAPIService().setRetrieveTheListOfCurrentEvents();

        getTopMenu().clickAddProduct();

        SelenideElement row = getTopMenu().expandEventsDropdown().getElementsList()
                .filter(text(firstEventTitle)).asDynamicIterable()
                .stream()
                .collect(CustomCollectors.toSingleton());

        getCurrentActivityFeed().getEventStartDate(row)
                .shouldHave(text(eventStartDateInPastStringFormat));

        getCurrentActivityFeed().getEventNumberOfProducts(row)
                .shouldHave(exactText("0"));

        getCurrentActivityFeed().getUser(row)
                .shouldHave(text("Manny Calavera"));
    }

    @TmsLink("HRV-15051")
    @Test(groups = {"event", "linkProductToEvent"})
    public void verifyLinkProductToEventFromProductListingPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        OnixTestService onix = new OnixTestService("regression/verifyLinkProductToEventFromProductListingPage/9781612910249.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        final String PRODUCT_TITLE = onix.getSingleProductTitle();

        getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .activateBulkSelection()
                .selectProduct()
                .clickTrackProductsAndConfirm()
                .productWithTrackedBadge(true)
                .shouldBe(visible);

        LocalDate eventStartDate = LocalDate.now().plusDays(10);
        String eventStartDateStringFormat = changeDateFormat(eventStartDate, DatePattern.FEED_EVENT_VIEW);
        final String firstEventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        getTopMenu().clickManageEvents()
                .clickCreateNewEvent()
                .setEventName(firstEventTitle)
                .selectStartDate(eventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);


        getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .clickActions()
                .clickLinkEvent()
                .linkProductToEvent(firstEventTitle, eventStartDateStringFormat);
    }

    @TmsLink("HRV-30071")
    @Test(groups = {"event", "linkProductToEvent"})
    public void verifyLinkProductToEventFromManageEventsPage() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        OnixTestService onix = new OnixTestService("regression/verifyLinkProductToEventFromManageEventsPage/9781612910250.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        Path isbnFilePath = getAbsoluteResourceFilePath(
                Paths.get("regression/verifyLinkProductToEventFromManageEventsPage/link_event_isbn.csv"));

        LocalDate eventStartDate = LocalDate.now().plusDays(10);
        String eventStartDateStringFormat = changeDateFormat(eventStartDate, DatePattern.FEED_EVENT_VIEW);
        final String eventTitle = generateUniqueStringBasedOnDate("AUTO event ");

        getTopMenu().clickManageEvents()
                .clickCreateNewEvent()
                .setEventName(eventTitle)
                .selectStartDate(eventStartDate)
                .clickAddRequiredParameters()
                .clickAddOptionalParameters()
                .clickAddEvent()
                .getSuccessCreatedEventMsgElm()
                .shouldBe(visible);

        getManageEventsPage()
                .clickLinkEvents()
                .selectLinkOption()
                .uploadFileToEvent(eventTitle, eventStartDateStringFormat, isbnFilePath)
                .getEventMsg().shouldHave(exactText(eventTitle + " on " + eventStartDateStringFormat));

        getLinkEventsPage().getSummary().shouldBe(visible);
    }

    @TmsLink("HRV-30185")
    public void verifyNormalizationRules() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu();

        SelenideElement numberOfFoundFilesElm = getHomePage()
                .clickSystem()
                .navigateToOriginalNormalizationFilesPage()
                .enterAlphaCode("AQ2")
                .getNumberOfFoundFilesElm();

        numberOfFoundFilesElm.shouldHave(exactText("0 zip files found"));
    }
}
