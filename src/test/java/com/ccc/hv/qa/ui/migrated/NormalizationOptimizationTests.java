package com.ccc.hv.qa.ui.migrated;

import com.google.common.collect.ImmutableMap;
import com.ccc.hv.qa.core.CustomCollectors;
import com.ccc.hv.qa.db.enums.BatchStatus;
import com.ccc.hv.qa.db.pojo.AssetDistrStatusDetailsDB;
import com.ccc.hv.qa.db.pojo.PhaseTrackMasterDB;
import com.ccc.hv.qa.file.pojo.OnixProduct;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.enums.AssetType;
import com.ccc.hv.qa.ui.enums.MetadataActivityPhase;
import com.ccc.hv.qa.ui.pages.MetadataActivityPage;
import com.ccc.hv.qa.ui.pages.ProductDetailsPage;
import com.ccc.hv.qa.ui.pojos.ChannelAssociation;
import com.ccc.hv.qa.ui.pojos.ChannelPublic;
import com.ccc.hv.qa.ui.pojos.NormalizationRule;
import com.ccc.hv.qa.ui.services.AssetTestService;
import com.ccc.hv.qa.ui.services.ChannelAndCATestService;
import com.ccc.hv.qa.ui.services.MetadataTestService;
import com.ccc.hv.qa.ui.services.OnixTestService;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.Condition;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.*;
import static com.ccc.hv.qa.api.services.ProductAPIService.getProductAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.enums.AssetDistributionStatus.PACKAGE_FAIL;
import static com.ccc.hv.qa.db.enums.AssetProcessingError.XPATH_EXPRESSION_INVALID_TAG;
import static com.ccc.hv.qa.db.services.BatchDBService.*;
import static com.ccc.hv.qa.db.services.ProductDBService.waitOnProductInDbBy;
import static com.ccc.hv.qa.db.services.TenantDBService.getTenantNormalizationProfileOnix21By;
import static com.ccc.hv.qa.db.services.TenantDBService.getTenantNormalizationProfileOnix30By;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds2;
import static com.ccc.hv.qa.ui.data.BatchODDEntities.*;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.*;
import static com.ccc.hv.qa.ui.data.Channels.*;
import static com.ccc.hv.qa.ui.data.MetadataOptimizations.*;
import static com.ccc.hv.qa.ui.data.NormalizationRules.*;
import static com.ccc.hv.qa.ui.data.PredUsers.SUPER_ADMIN;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.EmailTestService.hasNormalizationFailureEmailReseivedFor;
import static com.ccc.hv.qa.ui.services.FileTestService.getFileTestService;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static com.ccc.hv.qa.utils.FileOpsUtils.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.condition.AnyOf.anyOf;
import static org.awaitility.Awaitility.await;


@Test(singleThreaded = true, groups = "ui") //Execute all test alphabetically in single thread.
public class NormalizationOptimizationTests extends UITestBase {
    private static final String BU_2_NAME = ENV_CONFIG.testBusinessUnitName2();

    @TmsLink("AUT-481")
    @Test(groups = {"normalization"})
    public void verifyNormalizationLongToShortFormat() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "88";

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REGRESSION_NORMA_LONG_TO_SHORT);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForNormalizationLongToShort,
                regressionCASFTPForNormalizationLongToShort)
                .createChannelWithCA();

        String expectedNormRule = REGRESSION_NORMA_LONG_TO_SHORT.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        OnixTestService onixFile = new OnixTestService("regression/verifyNormalizationLongToShortFormat/9780000001989.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onixFile.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

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
        assertThat(onixTestService.isLongFormat()).as("Invalid Onix format.").isFalse();

        onixTestService.getProducts()
                .forEach(product -> {
                    assertThat(product.getNotificationType()).isEqualTo(EXPECTED_VALUE_AFTER_SUBSTITUTION);
                });
    }

    @TmsLink("AUT-482")
    @Test(groups = {"normalization"})
    public void verifyNormalizationRulesAddElement() {
        getLoginPage().loginAs(SUPER_ADMIN);

        final String EXPECTED_PRODUCT_ID_TYPE_NAME = "Test Identifier";
        final String EXPECTED_PRODUCT_ID_TYPE_VALUE = "XXXXX";

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(ADD_ELEMENT_NORMALIZATION_RULE);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPAddElementNormRule,
                regressionChannelAssociationSFTPForAddElementNormRule)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationRulesAddElement/9780000001912.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product -> {
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
        });

        onix.toAssets().stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList())
                .forEach(path -> {
                            Path downloadDir = onix.getParentDir();
                            Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(path), downloadDir);
                            OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

                            assertThat(downloadedOnix.getProductsIDTypeNames()).contains(EXPECTED_PRODUCT_ID_TYPE_NAME);
                            assertThat(downloadedOnix.getProductsIDTypeValues()).contains(EXPECTED_PRODUCT_ID_TYPE_VALUE);
                        }
                );
    }

    @TmsLink("AUT-483")
    @Test(groups = {"normalization"})
    public void verifyNormalizationRulesSubstituteElement() {
        getLoginPage().loginAs(SUPER_ADMIN);

        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "89";

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(SUBSTITUTE_ELEMENT_NORMALIZATION_RULE);

        String expectedNormRule = SUBSTITUTE_ELEMENT_NORMALIZATION_RULE.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPSubstituteElementNormRule,
                regressionChannelAssociationSFTPForSubstituteElementNormRule)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationRulesSubstituteElement/9780000001913.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product -> {
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
        });

        onix.toAssets().stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList())
                .forEach(path -> {

                            Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(path), onix.getParentDir());
                            OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

                            assertThat(downloadedOnix.getNotificationTypes()).isNotEmpty()
                                    .allMatch(notfType ->
                                            notfType.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION)
                                    );
                        }
                );
    }

    @TmsLink("AUT-484")
    @Test(groups = {"normalization"})
    public void verifyNormalizationRulesRemoveElement() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REMOVE_ELEMENT_NORMALIZATION_RULE);

        String expectedNormRule = REMOVE_ELEMENT_NORMALIZATION_RULE.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPRemoveElementNormRule,
                regressionChannelAssociationSFTPForRemoveElementNormRule)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationRulesRemoveElement/9780000001914.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product -> {
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
        });

        onix.toAssets().stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList())
                .forEach(path -> {

                            Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(path), onix.getParentDir());
                            OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

                            assertThat(downloadedOnix.getProductsIDTypeNames())
                                    .as("Expect the b233 tag be removed during Normalization process")
                                    .isEmpty();
                        }
                );
    }

    @TmsLink("AUT-488")
    @Test(groups = {"normalization"})
    public void verifyNormalizationRulesMultiply() {

        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "89";
        final String SUFFIX = "pdf";

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REGRESSION_NORMA_MULTIPLY);

        String expectedNormRule = REGRESSION_NORMA_MULTIPLY.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPMultiplyNormOptRule,
                regressionChannelAssociationSFTPForMultiplyNormOptRule)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_SUBSTITUTE_OPTIMIZATION_RULES);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationRulesMultiply/9780000011923.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product -> {
            final String PRODUCT_TITLE = product.getTitle();
            final String REC_REF = product.getRecordReference() + getRevisionDelimiter() + SUFFIX;
            waitOnProductInDbBy(PRODUCT_TITLE);

            getTopMenu()
                    .searchPresentProductBy(PRODUCT_TITLE, REC_REF)
                    .openProductDetailsPage()
                    .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                    .clickDistribute()
                    .selectChannelsBy(channelAndCA.getChannelName())
                    .clickSendFiles()
                    .completeSuccessfulDistribution();
        });

        onix.readOnixFile()
                .updateRecordReferencesWithSuffix(SUFFIX)
                .saveAsNewFile();

        onix.toAssets().stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList())
                .forEach(path -> {

                            Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(path), onix.getParentDir());
                            OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

                            assertThat(downloadedOnix.getNotificationTypes()).isNotEmpty()
                                    .allMatch(notfType ->
                                            notfType.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION)
                                    );
                        }
                );
    }

    @TmsLink("AUT-488")
    @Test(groups = {"normalization"})
    public void verifyNormalizationRulesMultiplyOnix30() {
        final String SUFFIX = "pdf";
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(MULTIPLY_NORMALIZATION_RULE_ONIX_30)
                .setRuleAndComment(EMPTY_MULTIPLY_NORMALIZATION_RULE_ONIX_21);

        String expectedNormRule = MULTIPLY_NORMALIZATION_RULE_ONIX_30.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPMultiplyNormOptRuleOnix30,
                regressionChannelAssociationSFTPForMultiplyNormOptRuleOnix30)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationRulesMultiplyOnix30/9780000001944.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        OnixProduct product = onix.getProducts().get(0);
        final String PRODUCT_TITLE = product.getTitle();
        final String REC_REF = product.getRecordReference() + getRevisionDelimiter() + SUFFIX;
        waitOnProductInDbBy(PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE, REC_REF)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        onix.readOnixFile()
                .updateRecordReferencesWithSuffix(SUFFIX)
                .saveAsNewFile();

        String distrPath = onix.toAsset().getDistrPathFor(channelAndCA.getChannelName());

        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPath), onix.getParentDir());
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);
        assertThat(downloadedOnix.getProductsRecordReferences()).contains(REC_REF);
    }

    @TmsLink("AUT-511")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptimizationRules21WithDistributeAsSingleOnixFileOption() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "89";

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_SEND_ONIX_21_SINGLE_FILE)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_SEND_ONIX_SINGLE_FILE_EMPTY_30_RULE);

        String expectedNormRule = REMOVE_ELEMENT_NORM_RULE_SEND_ONIX_21_SINGLE_FILE.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPNormOptRule21SendSingleOnixFile,
                regressionChannelAssociationSFTPForNormOptRule21SendSingle)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_21_SUBSTITUTE_RULE_SEND_SINGLE_ONIX);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOptimizationRules21WithDistributeAsSingleOnixFileOption/9780000011955.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product ->
                waitOnProductInDbBy(product.getTitle())
        );

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_NORM_OPT_21_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX)
                .getScheduledConfirmationMsgElm()
                .shouldBe(visible);

        List<String> expectedDistrPaths = onix.getFileNamesWithRecRef();
        List<String> actualDistrPaths = onix.toAssets()
                .stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList());

        assertThat(actualDistrPaths)
                .as("Onix 21 file has not distributed as individual file")
                .hasSameElementsAs(expectedDistrPaths);

        actualDistrPaths.forEach(path -> {
                    Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(path), onix.getParentDir());
                    OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

                    assertThat(downloadedOnix.getNotificationTypes()).isNotEmpty()
                            .allMatch(notfType ->
                                    notfType.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION)
                            );

                    assertThat(downloadedOnix.getProductsIDTypeNames())
                            .as("Expect the b233 tag be removed during Normalization process")
                            .isEmpty();
                }
        );
    }

    @TmsLink("AUT-511")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptimizationRules21WithGroupByPublisherAndImprint() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "89";
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(NORMA_REMOVE_RULE_21_GROUP_BY_PUBLISHER_AND_IMPRINT)
                .setRuleAndComment(NORMA_EMPTY_RULE_30_GROUP_BY_PUBLISHER_AND_IMPRINT);

        String expectedNormRule = NORMA_REMOVE_RULE_21_GROUP_BY_PUBLISHER_AND_IMPRINT.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPNormOptRule21GroupByPublisherAndImprint,
                regressionChannelAssociationSFTPForNormOptRule21GroupByPublisherAndImprint)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_SUBSTITUTE_RULE_21_GROUP_BY_PUBLISHER_AND_IMPRINT);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOptimizationRules21WithGroupByPublisherAndImprint/9780000011956.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product ->
                waitOnProductInDbBy(product.getTitle())
        );

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_NORM_OPT_21_GROUP_BY_PUBLISHER_AND_IMPRINT)
                .getScheduledConfirmationMsgElm()
                .shouldBe(visible);

        List<MetadataTestService> metadataAssets = onix.toAssets();

        final int EXPECTED_FEEDS_QUANTITY = 2;

        Set<String> actualUniqueDistrPaths = metadataAssets.stream()
                .map(metadata -> metadata.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toSet());

        assertThat(actualUniqueDistrPaths).as("Wrong number of distribution feeds")
                .hasSize(EXPECTED_FEEDS_QUANTITY);

        Map<String, String> expectedImprintAndPublisher = new HashMap<String, String>() {{
            put("Test Imprint", "Test Publisher");
            put("Test Imprint 2", "Test Publisher 2");
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

            assertThat(downloadedOnix.getProductsIDTypeNames())
                    .as("Expect the b233 tag be removed during Normalization process")
                    .isEmpty();

            assertThat(downloadedOnix.getNotificationTypes()).isNotEmpty()
                    .allMatch(notfType ->
                            notfType.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION)
                    );
        });
    }

    @TmsLink("AUT-521")
    @Test(groups = {"normalization"})
    public void verifyNormalizationFailWhenInvalidZipFile() {
        final NormalizationRule expectedNormRule = REGRESSION_NORMALIZATION_FAIL_WITH_INVALID_ZIP_FILE;

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(expectedNormRule);

        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule.getRule());
        });

        Path origZippedOnix = getAbsoluteResourceFilePath(Paths.get("regression/verifyNormalizationFailWhenInvalidZipFile/Two_Products-short_ONIX-Invalid.zip"));
        Path clonedZippedOnix = cloneFile(addUniqueSuffixTo(origZippedOnix).getFileName().toString(), origZippedOnix.toString());
        String zippedOnixWithUniqueFileName = clonedZippedOnix.getFileName().toString();
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(clonedZippedOnix, crushFtpUploadCreds2);

        await().untilAsserted(() -> {
            boolean isBatchIdExist = isAnyBatchRecordExistFor(zippedOnixWithUniqueFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        AssertionsForClassTypes.assertThat(hasNormalizationFailureEmailReseivedFor(zippedOnixWithUniqueFileName))
                .as("No normalization fail email has been found.")
                .isTrue();
    }

    @TmsLink("AUT-521")
    @Test(groups = {"normalization"})
    public void verifyNormalizationFailWhenInvalidNormaRuleWithUnrecognizableRecordSourceName() {
        final NormalizationRule expectedNormRule = REGRESSION_NORMALIZATION_FAIL_WITH_INVALID_NORMA_RULE_UNREC_REC_SOURCE_NAME;

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(expectedNormRule);

        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule.getRule());
        });

        Path origOnixFile = getAbsoluteResourceFilePath(Paths.get("regression/verifyNormalizationFailWhenInvalidNormaRuleWithUnrecognizableRecordSourceName/Two_Products-short_ONIX-Wrong_RSN.xml"));
        Path clonnedOnixFile = cloneFile(addUniqueSuffixTo(origOnixFile).getFileName().toString(), origOnixFile.toString());
        Path zippedOnix = zipFile(clonnedOnixFile);
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isAnyBatchRecordExistFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });
    }

    @TmsLink("AUT-521")
    @Test(groups = {"normalization"})
    public void verifyNormalizationFailWhenInvalidNormaRuleWithSyntaxError() {
        final NormalizationRule expectedNormRule = REGRESSION_NORMALIZATION_FAIL_WITH_INVALID_NORMA_RULE_SYNTAX_ERROR;

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(expectedNormRule);

        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule.getRule());
        });

        Path origOnixFile = getAbsoluteResourceFilePath(Paths.get("regression/verifyNormalizationFailWhenInvalidNormaRuleWithSyntaxError/9780000001933.xml"));
        Path clonnedOnixFile = cloneFile(addUniqueSuffixTo(origOnixFile).getFileName().toString(), origOnixFile.toString());
        Path zippedOnix = zipFile(clonnedOnixFile);
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isAnyBatchRecordExistFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        AssertionsForClassTypes.assertThat(hasNormalizationFailureEmailReseivedFor(zipFileName))
                .as("No normalization fail email has been found.")
                .isTrue();
    }

    @TmsLink("AUT-526")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptimizationFailedPath() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(NORMALIZATION_OPTIMIZATION_FAILED_PATH_REMOVE_RULE);

        String expectedNormRule = NORMALIZATION_OPTIMIZATION_FAILED_PATH_REMOVE_RULE.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPNormOptFailedPath,
                regressionChannelAssociationSFTPForNormOptFailedPath)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_NORM_FAILED_PATH_SUBSTITUTE_INVALID);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOptimizationFailedPath/9780000011944.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product ->
                waitOnProductInDbBy(product.getTitle())
        );

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
                .as("Invalid distribution processing error.")
                .contains(XPATH_EXPRESSION_INVALID_TAG.getErrorText());
    }

    @TmsLink("AUT-529")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptWithConversionOnixFrom21To30() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "88";
        final NormalizationRule expectedNormRule = REGRESSION_NORMA_SHORT_21_TO_30;

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(expectedNormRule);

        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule.getRule());
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForNormalization21To30,
                regressionCASFTPForNormalization21To30)
                .createChannelWithCA();

        OnixTestService onixFile = new OnixTestService("regression/verifyNormalizationOptWithConversionOnixFrom21To30/9780000001977.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onixFile.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB for: '" + zipFileName + "' file.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: '" + zipFileName + "'.")
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

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

    @TmsLink("AUT-649")
    @Test(groups = {"normalization"})
    public void verifyNormalizationRulesAddElementOnix30() {
        final String EXPECTED_PRODUCT_ID_TYPE_NAME = "Test Identifier";
        final String EXPECTED_PRODUCT_ID_TYPE_VALUE = "XXXXX";

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(ADD_NORMALIZATION_RULE_ONIX_30)
                .setRuleAndComment(EMPTY_SUBSTITUTE_ELEMENT_NORMALIZATION_RULE_ONIX21);

        String expectedNormRule = ADD_NORMALIZATION_RULE_ONIX_30.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPAddElementNormRuleOnix30,
                regressionChannelAssociationSFTPForAddElementNormRuleOnix30)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationRulesAddElementOnix30/9780000001991.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        OnixProduct product = onix.getProducts().get(0);
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

    @TmsLink("AUT-649")
    @Test(groups = {"normalization"})
    public void verifyNormalizationRulesRemoveElementOnix30() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REMOVE_ELEMENT_NORMALIZATION_RULE_ONIX_30)
                .setRuleAndComment(EMPTY_REMOVE_NORMALIZATION_RULE_ONIX_21);

        String expectedNormRule = REMOVE_ELEMENT_NORMALIZATION_RULE_ONIX_30.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPRemoveElementNormRuleOnix30,
                regressionChannelAssociationSFTPForRemoveElementNormRuleOnix30)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationRulesRemoveElementOnix30/9780000001996.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        OnixProduct product = onix.getProducts().get(0);
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

        assertThat(downloadedOnix.getProductsIDTypeNames())
                .as("Expect the b233 tag be removed during Normalization process")
                .isEmpty();
    }

    @TmsLink("AUT-650")
    @Test(groups = {"normalization"})
    public void verifyNormalizationRulesSubstituteElementOnix30() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "89";

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(SUBSTITUTE_ELEMENT_NORMALIZATION_RULE_ONIX30)
                .setRuleAndComment(EMPTY_SUBSTITUTE_ELEMENT_NORMALIZATION_RULE_ONIX21);

        String expectedNormRule = SUBSTITUTE_ELEMENT_NORMALIZATION_RULE_ONIX30.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPSubstituteElementNormRuleOnix30,
                regressionChannelAssociationSFTPForSubstituteElementNormRuleOnix30)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationRulesSubstituteElementOnix30/9780000001997.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        OnixProduct product = onix.getProducts().get(0);
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

        assertThat(downloadedOnix.getNotificationTypes()).isNotEmpty()
                .allMatch(notfType -> notfType.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION));
    }

    @TmsLink("AUT-653")
    @Test(groups = {"normalization"})
    public void verifyNormalizationOnix30WhenBoth30And21ProfilesAreSetup() {
        getLoginPage().loginAs(SUPER_ADMIN);

        final String EXPECTED_PRODUCT_ID_TYPE_NAME = "Test Identifier 30";
        final String EXPECTED_PRODUCT_ID_TYPE_VALUE = "XXX30";

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REGRESSION_NORMA_ONIX30_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES)
                .setRuleAndComment(REGRESSION_NORMA_ONIX21_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES);

        await().untilAsserted(() -> {
            String actualNormalization21Profile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalization21Profile)
                    .as("Invalid normalization 2.1 rule has been applied to BU.")
                    .isEqualTo(REGRESSION_NORMA_ONIX21_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES.getRule());
        });

        String actualNormalization30Profile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
        assertThat(actualNormalization30Profile)
                .as("Invalid normalization 3.0 rule has been applied to BU.")
                .isEqualTo(REGRESSION_NORMA_ONIX30_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES.getRule());

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForOnix30WhenBothNormaProfilesAreSetup,
                regressionChannelAssociationSFTPForOnix30WhenBothNormaProfilesAreSetup)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOnix30WhenBoth30And21NormaProfilesAreSetup/9780000001956.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        final String PRODUCT_TITLE = onix.getProducts().get(0).getTitle();
        waitOnProductInDbBy(PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String distributionPath = onix.toAsset().getDistrPathFor(channelAndCA.getChannelName());

        Path downloadDir = onix.getParentDir();
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distributionPath), downloadDir);
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

        assertThat(downloadedOnix.getProductsIDTypeNames()).contains(EXPECTED_PRODUCT_ID_TYPE_NAME);
        assertThat(downloadedOnix.getProductsIDTypeValues()).contains(EXPECTED_PRODUCT_ID_TYPE_VALUE);
    }

    @TmsLink("AUT-654")
    @Test(groups = {"normalization"})
    public void verifyNormalizationOnix21WhenBoth30And21ProfilesAreSetup() {
        getLoginPage().loginAs(SUPER_ADMIN);

        final String EXPECTED_PRODUCT_ID_TYPE_NAME = "Test Identifier 21";
        final String EXPECTED_PRODUCT_ID_TYPE_VALUE = "XXX21";

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REGRESSION_NORMA_ONIX30_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES_2)
                .setRuleAndComment(REGRESSION_NORMA_ONIX21_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES_2);

        await().untilAsserted(() -> {
            String actualNormalization21Profile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalization21Profile)
                    .as("Invalid normalization 2.1 rule has been applied to BU.")
                    .isEqualTo(REGRESSION_NORMA_ONIX21_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES.getRule());
        });

        String actualNormalization30Profile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
        assertThat(actualNormalization30Profile)
                .as("Invalid normalization 3.0 rule has been applied to BU.")
                .isEqualTo(REGRESSION_NORMA_ONIX30_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES.getRule());

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForOnix21WhenBothNormaProfilesAreSetup,
                regressionChannelAssociationSFTPForOnix21WhenBothNormaProfilesAreSetup)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOnix21WhenBoth30And21NormaProfilesAreSetup/9780000001978.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        final String PRODUCT_TITLE = onix.getProducts().get(0).getTitle();
        waitOnProductInDbBy(PRODUCT_TITLE);

        getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage()
                .selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        String distributionPath = onix.toAsset().getDistrPathFor(channelAndCA.getChannelName());

        Path downloadDir = onix.getParentDir();
        Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distributionPath), downloadDir);
        OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

        assertThat(downloadedOnix.getProductsIDTypeNames()).contains(EXPECTED_PRODUCT_ID_TYPE_NAME);
        assertThat(downloadedOnix.getProductsIDTypeValues()).contains(EXPECTED_PRODUCT_ID_TYPE_VALUE);
    }

    @TmsLink("AUT-656")
    @Test(groups = {"normalization"})
    public void verifyNormalizationOnix30WhenInvalid30AndValid21NormaProfilesAreSetup() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REGRESSION_REMOVE_INVALID_NORMALIZATION_30_RULE)
                .setRuleAndComment(REGRESSION_REMOVE_VALID_NORMALIZATION_21_RULE);

        await().untilAsserted(() -> {
            String actualNormalization21Profile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalization21Profile)
                    .as("Invalid normalization 2.1 rule has been applied to BU.")
                    .isEqualTo(REGRESSION_REMOVE_VALID_NORMALIZATION_21_RULE.getRule());
        });

        String actualNormalization30Profile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
        assertThat(actualNormalization30Profile)
                .as("Invalid normalization 3.0 rule has been applied to BU.")
                .isEqualTo(REGRESSION_REMOVE_INVALID_NORMALIZATION_30_RULE.getRule());

        Path origOnixFile = getAbsoluteResourceFilePath(Paths.get("regression/verifyNormalizationOnix30WhenInvalid30AndValid21NormaProfilesAreSetup/9780000001951.xml"));
        Path clonnedOnixFile = cloneFile(addUniqueSuffixTo(origOnixFile).getFileName().toString(), origOnixFile.toString());
        Path zippedOnix = zipFile(clonnedOnixFile);
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isAnyBatchRecordExistFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        AssertionsForClassTypes.assertThat(hasNormalizationFailureEmailReseivedFor(zipFileName))
                .as("No normalization fail email has been found.")
                .isTrue();
    }

    @TmsLink("AUT-655")
    @Test(groups = {"normalization"})
    public void verifyNormalization30WhenNormaRule30IsInvalid() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REGRESSION_NORMA30_INVALID_NORMALIZATION_30_RULE)
                .setRuleAndComment(REGRESSION_NORMA30_EMPTY_RULE);

        await().untilAsserted(() -> {
            String actualNormalization21Profile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalization21Profile)
                    .as("Invalid normalization 2.1 rule has been applied to BU.")
                    .isNull();
        });

        String actualNormalization30Profile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
        assertThat(actualNormalization30Profile)
                .as("Invalid normalization 3.0 rule has been applied to BU.")
                .isEqualTo(REGRESSION_NORMA30_INVALID_NORMALIZATION_30_RULE.getRule());

        Path origOnixFile = getAbsoluteResourceFilePath(Paths.get("regression/verifyNormalization30WhenNormaRule30IsInvalid/9780000001950.xml"));
        Path clonnedOnixFile = cloneFile(addUniqueSuffixTo(origOnixFile).getFileName().toString(), origOnixFile.toString());
        Path zippedOnix = zipFile(clonnedOnixFile);
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isAnyBatchRecordExistFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(hasNormalizationFailureEmailReseivedFor(zipFileName))
                .as("No normalization fail email has been found.")
                .isTrue();
    }

    @DataProvider(name = "toLongOnix21TestData")
    private Object[][] toLongOnix21TestData() {
        return new Object[][]{
                {"regression/verifyNormalizationLongToLongFormatOnix21/9780000001990_long.xml",
                        regressionPublicChannelSFTPForNormalizationLongToLongOnix21,
                        regressionCASFTPForNormalizationLongToLongOnix21
                },
                {"regression/verifyNormalizationLongToLongFormatOnix21/9780000001971_short.xml",
                        regressionPublicChannelSFTPForNormalizationShortToLongOnix21,
                        regressionCASFTPForNormalizationShortToLongOnix21
                }};
    }

    @TmsLink("AUT-674")
    @Test(dataProvider = "toLongOnix21TestData", groups = {"normalization"})
    public void verifyNormalizationLongToLongFormatOnix21(@NotNull String metadataOnixFile,
                                                          ChannelPublic channel,
                                                          ChannelAssociation ca) {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REGRESSION_NORMA_LONG_TO_LONG_ONIX_21)
                .setRuleAndComment(EMPTY_NORMA_LONG_TO_LONG_ONIX_ONIX30_FOR_ONIX21_TEST);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_RULE_LONG_TO_LONG_ONIX21);

        String expectedNormRule = REGRESSION_NORMA_LONG_TO_LONG_ONIX_21.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        OnixTestService onixFile = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onixFile.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        OnixProduct firstProduct = onixFile.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage();

        String downloadedOnixFilePath = productDetailsPage.downloadAssetBy(AssetType.ONIX21).getAbsolutePath();
        OnixTestService metadataOnix = new OnixTestService(downloadedOnixFilePath);
        assertThat(metadataOnix.isLongFormat()).as("Invalid Onix format.").isTrue();

        productDetailsPage.selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        final String distrPathForFirstProduct = onixFile.toAssets().get(0).getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedOnixFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPathForFirstProduct), onixFile.getParentDir());

        OnixTestService onixTestService = new OnixTestService(downloadedOnixFile);
        assertThat(onixTestService.isLongFormat()).as("Invalid Onix format.").isTrue();

    }

    @DataProvider(name = "toLongOnix30TestData")
    private Object[][] toLongOnix30TestData() {
        return new Object[][]{
                {"regression/verifyNormalizationLongOrShortToLongFormatOnix30/9780830842870_long.xml",
                        regressionPublicChannelSFTPForNormalizationLongToLongOnix30,
                        regressionCASFTPForNormalizationLongToLongOnix30
                },
                {"regression/verifyNormalizationLongOrShortToLongFormatOnix30/9780830842870_short.xml",
                        regressionPublicChannelSFTPForNormalizationShortToLongOnix30,
                        regressionCASFTPForNormalizationShortToLongOnix30
                }};
    }


    @TmsLink("AUT-675")
    @Test(dataProvider = "toLongOnix30TestData", groups = {"normalization"})
    public void verifyNormalizationLongOrShortToLongFormatOnix30(@NotNull String metadataOnixFile,
                                                                 ChannelPublic channel,
                                                                 ChannelAssociation ca) {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REGRESSION_NORMA_LONG_TO_LONG_ONIX_30)
                .setRuleAndComment(EMPTY_NORMA_LONG_TO_LONG_ONIX_ONIX21_FOR_ONIX30_TEST);

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(channel, ca)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_RULE_LONG_TO_LONG_ONIX30);

        String expectedNormRule = REGRESSION_NORMA_LONG_TO_LONG_ONIX_30.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        OnixTestService onixFile = new OnixTestService(metadataOnixFile)
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onixFile.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        OnixProduct firstProduct = onixFile.getProducts().get(0);
        final String FIRST_PRODUCT_TITLE = firstProduct.getTitle();
        waitOnProductInDbBy(FIRST_PRODUCT_TITLE);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(FIRST_PRODUCT_TITLE)
                .openProductDetailsPage();

        String downloadedOnixFilePath = productDetailsPage.downloadAssetBy(AssetType.ONIX30).getAbsolutePath();
        OnixTestService metadataOnix = new OnixTestService(downloadedOnixFilePath);
        assertThat(metadataOnix.isLongFormat()).as("Invalid Onix format.").isTrue();

        productDetailsPage.selectAssetsBy(channelAndCA.getAssetTypeFromSingleMetadata())
                .clickDistribute()
                .selectChannelsBy(channelAndCA.getChannelName())
                .clickSendFiles()
                .completeSuccessfulDistribution();

        final String distrPathForFirstProduct = onixFile.toAssets().get(0).getDistrPathFor(channelAndCA.getChannelName());
        Path downloadedOnixFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(distrPathForFirstProduct), onixFile.getParentDir());

        OnixTestService onixTestService = new OnixTestService(downloadedOnixFile);
        assertThat(onixTestService.isLongFormat()).as("Invalid Onix format.").isTrue();
    }

    @TmsLink("AUT-678")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptimizationRules30WithDistributeAsSingleOnixFileOption() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "88";

        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(NORM_EMPTY_RULE_21_SEND_SINGLE_ONIX_IN_SINGLE_FILE)
                .setRuleAndComment(NORM_REMOVE_RULE_30_SEND_SINGLE_ONIX_IN_SINGLE_FILE);

        String expectedNormRule = NORM_REMOVE_RULE_30_SEND_SINGLE_ONIX_IN_SINGLE_FILE.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPNormOptRule30SendSingleOnixFile,
                regressionChannelAssociationSFTPForNormOptRule30SendSingle)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_30_SUBSTITUTE_RULE_SEND_SINGLE_ONIX);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOptimizationRules30WithDistributeAsSingleOnixFileOption/9780000011958.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product ->
                waitOnProductInDbBy(product.getTitle())
        );

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_NORM_OPT_30_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX)
                .getScheduledConfirmationMsgElm()
                .shouldBe(visible);

        List<String> expectedDistrPaths = onix.getFileNamesWithRecRef();
        List<String> actualDistrPaths = onix.toAssets()
                .stream()
                .map(asset -> asset.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toList());

        assertThat(actualDistrPaths)
                .as("Onix file is not distributed as individual file")
                .hasSameElementsAs(expectedDistrPaths);

        actualDistrPaths.forEach(path -> {
                    Path downloadedFile = getFileTestService().downloadFileFromCrushFTP(Paths.get(path), onix.getParentDir());
                    OnixTestService downloadedOnix = new OnixTestService(downloadedFile);

                    assertThat(downloadedOnix.getNotificationTypes()).isNotEmpty()
                            .allMatch(notfType ->
                                    notfType.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION)
                            );

                    assertThat(downloadedOnix.getProductsIDTypeNames())
                            .as("Expect the b233 tag be removed during Normalization process")
                            .isEmpty();
                }
        );
    }

    @TmsLink("AUT-678")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptimizationRules30WithGroupByPublisherAndImprint() {
        final String EXPECTED_VALUE_AFTER_SUBSTITUTION = "88";
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(NORM_REMOVE_RULE_30_GROUP_BY_PUB_AND_IMPRINT)
                .setRuleAndComment(NORM_EMPTY_RULE_21_GROUP_BY_PUB_AND_IMPRINT);

        String expectedNormRule = NORM_REMOVE_RULE_30_GROUP_BY_PUB_AND_IMPRINT.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPNormOptRule30GroupByPublisherAndImprint,
                regressionChannelAssociationSFTPForNormOptRule30GroupByPublisherAndImprint)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_SUBSTITUTE_RULE_30_GROUP_BY_PUBLISHER_AND_IMPRINT);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOptimizationRules30WithGroupByPublisherAndImprint/9780000011959.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product ->
                waitOnProductInDbBy(product.getTitle())
        );

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_NORM_OPT_30_GROUP_BY_PUBLISHER_AND_IMPRINT)
                .getScheduledConfirmationMsgElm()
                .shouldBe(visible);

        List<MetadataTestService> metadataAssets = onix.toAssets();

        final int EXPECTED_FEEDS_QUANTITY = 2;

        Set<String> actualUniqueDistrPaths = metadataAssets.stream()
                .map(metadata -> metadata.getDistrPathFor(channelAndCA.getChannelName()))
                .collect(Collectors.toSet());

        assertThat(actualUniqueDistrPaths).as("Wrong number of distribution feeds")
                .hasSize(EXPECTED_FEEDS_QUANTITY);

        Map<String, String> expectedImprintAndPublisher = new HashMap<String, String>() {{
            put("Test Imprint", "Test Publisher");
            put("Test Imprint 2", "Test Publisher 2");
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

            assertThat(downloadedOnix.getProductsIDTypeNames())
                    .as("Expect the b233 tag be removed during Normalization process")
                    .isEmpty();

            assertThat(downloadedOnix.getNotificationTypes()).isNotEmpty()
                    .allMatch(notfType ->
                            notfType.equals(EXPECTED_VALUE_AFTER_SUBSTITUTION)
                    );
        });
    }

    @TmsLink("AUT-765")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptimizationRulesMetadataActivityOnix21() {
        getLoginPage().loginAs(SUPER_ADMIN);

        List<MetadataActivityPhase> zipFileMetadataActivityPhases = new ArrayList<>(
                Arrays.asList(MetadataActivityPhase.NORMALIZATION,
                        MetadataActivityPhase.INGEST,
                        MetadataActivityPhase.OPTIMIZATION,
                        MetadataActivityPhase.DISTRIBUTION
                ));

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY_30_RULE);

        String expectedNormRule = REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPNormOptMetadataActivityOnix21,
                regressionChannelAssociationSFTPForNormOptMetadataActivityOnix21)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_21_SUBSTITUTE_RULE_METADATA_ACTIVITY);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOptimizationRulesMetadataActivityOnix21/9780000011951.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .as("Batch statuses are not expected")
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product -> waitOnProductInDbBy(product.getTitle()));

        onix.toAssets().forEach(AssetTestService::updateOnixDistributionDateAndIntervalForCA);

        getProductAPIService().triggerAutoDistribution();

        zipFileMetadataActivityPhases.forEach(phase -> getProductAPIService().waitOnCompleteStatusForFile(zipFileName, phase));

        MetadataActivityPage metadataActivityPage = getTopMenu().clickManageMetadata();

        Arrays.asList(MetadataActivityPhase.NORMALIZATION, MetadataActivityPhase.INGEST)
                .forEach(phase -> metadataActivityPage.getStatusIconOfPhaseElm(zipFileName, phase).shouldBe(visible));

        metadataActivityPage.getCompletionSummaryElm(zipFileName).shouldHave(text(zipFileName));
        metadataActivityPage.getAllAssetsProceededElm(zipFileName).shouldBe(visible);

        //normalization phase
        final int EXPECTED_COUNT_OF_NORM_PHASE = 1;
        PhaseTrackMasterDB normalizationPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.NORMALIZATION);

        assertThat(normalizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid normalized product target count")
                .isEqualTo(normalizationPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid normalized products success count.")
                .isEqualTo(EXPECTED_COUNT_OF_NORM_PHASE);

        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.NORMALIZATION))
                .as("Invalid number of failures.")
                .isEqualTo(String.valueOf(normalizationPhaseTrackMasterDB.getFailCount()));

        //ingest phase
        PhaseTrackMasterDB ingestPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.INGEST);

        metadataActivityPage.getNumberOfProductsElm(zipFileName)
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())))
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getSuccessCount())));

        metadataActivityPage.getNumberOfIncomingProductsElm(zipFileName)
                .shouldHave(text(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())));

        assertThat(ingestPhaseTrackMasterDB.getFailCount())
                .as("Invalid number of created products from ingested file.")
                .isZero();

        //optimization phase
        PhaseTrackMasterDB optimizationPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.OPTIMIZATION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.OPTIMIZATION))
                .as("Invalid optimization fail count.")
                .isEqualTo(String.valueOf(optimizationPhaseTrackMasterDB.getFailCount()));

        assertThat(optimizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid optimization phase track target count.")
                .isNotZero();
        assertThat(optimizationPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid phase track success count.")
                .isNotZero();

        //distribution phase
        PhaseTrackMasterDB distrPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.DISTRIBUTION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.DISTRIBUTION))
                .as("Invalid number of failures of distribution phase.")
                .isEqualTo(String.valueOf(distrPhaseTrackMasterDB.getFailCount()));

        assertThat(distrPhaseTrackMasterDB.getTargetCount())
                .as("Invalid distribution phase track target count.")
                .isNotZero();
        assertThat(distrPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid Distribution phase track success count.")
                .isNotZero();
    }

    @TmsLink("AUT-823")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptimizationRulesMetadataActivityOnix30() {
        getLoginPage().loginAs(SUPER_ADMIN);

        List<MetadataActivityPhase> zipFileMetadataActivityPhases = new ArrayList<>(
                Arrays.asList(MetadataActivityPhase.NORMALIZATION,
                        MetadataActivityPhase.INGEST,
                        MetadataActivityPhase.OPTIMIZATION,
                        MetadataActivityPhase.DISTRIBUTION
                ));

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_ONIX_30_METADATA_ACTIVITY)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_ONIX_30_METADATA_ACTIVITY_30_RULE);

        String expectedNormRule = REMOVE_ELEMENT_NORM_RULE_ONIX_30_METADATA_ACTIVITY_30_RULE.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPNormOptMetadataActivityOnix30,
                regressionChannelAssociationSFTPForNormOptMetadataActivityOnix30)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_30_SUBSTITUTE_RULE_METADATA_ACTIVITY);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOptimizationRulesMetadataActivityOnix30/9780000011911.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .as("Batch statuses are not expected")
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product -> waitOnProductInDbBy(product.getTitle()));

        onix.toAssets().forEach(AssetTestService::updateOnixDistributionDateAndIntervalForCA);

        getProductAPIService().triggerAutoDistribution();

        zipFileMetadataActivityPhases.forEach(phase -> getProductAPIService().waitOnCompleteStatusForFile(zipFileName, phase));

        MetadataActivityPage metadataActivityPage = getTopMenu().clickManageMetadata();

        Arrays.asList(MetadataActivityPhase.NORMALIZATION, MetadataActivityPhase.INGEST)
                .forEach(phase -> metadataActivityPage.getStatusIconOfPhaseElm(zipFileName, phase).shouldBe(visible));

        metadataActivityPage.getCompletionSummaryElm(zipFileName).shouldHave(text(zipFileName));
        metadataActivityPage.getAllAssetsProceededElm(zipFileName).shouldBe(visible);

        //normalization phase
        final int EXPECTED_COUNT_OF_NORM_PHASE = 1;
        PhaseTrackMasterDB normalizationPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.NORMALIZATION);

        assertThat(normalizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid normalized product target count")
                .isEqualTo(normalizationPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid normalized products success count.")
                .isEqualTo(EXPECTED_COUNT_OF_NORM_PHASE);

        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.NORMALIZATION))
                .as("Invalid number of failures.")
                .isEqualTo(String.valueOf(normalizationPhaseTrackMasterDB.getFailCount()));

        //ingest phase
        PhaseTrackMasterDB ingestPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.INGEST);

        metadataActivityPage.getNumberOfProductsElm(zipFileName)
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())))
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getSuccessCount())));

        metadataActivityPage.getNumberOfIncomingProductsElm(zipFileName)
                .shouldHave(text(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())));

        assertThat(ingestPhaseTrackMasterDB.getFailCount())
                .as("Invalid number of created products from ingested file.")
                .isZero();

        //optimization phase
        PhaseTrackMasterDB optimizationPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.OPTIMIZATION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.OPTIMIZATION))
                .as("Invalid optimization fail count.")
                .isEqualTo(String.valueOf(optimizationPhaseTrackMasterDB.getFailCount()));

        assertThat(optimizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid optimization phase track target count.")
                .isNotZero();
        assertThat(optimizationPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid phase track success count.")
                .isNotZero();

        //distribution phase
        PhaseTrackMasterDB distrPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.DISTRIBUTION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.DISTRIBUTION))
                .as("Invalid number of failures of distribution phase.")
                .isEqualTo(String.valueOf(distrPhaseTrackMasterDB.getFailCount()));

        assertThat(distrPhaseTrackMasterDB.getTargetCount())
                .as("Invalid distribution phase track target count.")
                .isNotZero();
        assertThat(distrPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid Distribution phase track success count.")
                .isNotZero();
    }

    @TmsLink("AUT-807")
    @Test(groups = {"normalization", "optimization"})
    public void verifyMultipleIngestionSkipCountOfZIPOnix21File() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY_SKIP_COUNT)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY_SKIP_COUNT_30_RULE);

        String expectedNormRule = REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY_SKIP_COUNT.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        OnixTestService onix = new OnixTestService("regression/verifyMultipleIngestionSkipCountOfZIPOnix21File/9780000011950.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zipFilePath = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zipFilePath, crushFtpUploadCreds2);
        onix.getProducts().forEach(product -> waitOnProductInDbBy(product.getTitle()));

        String zipFileName = zipFilePath.getFileName().toString();
        getProductAPIService().waitOnCompleteStatusForFile(zipFileName, MetadataActivityPhase.NORMALIZATION);
        getProductAPIService().waitOnCompleteStatusForFile(zipFileName, MetadataActivityPhase.INGEST);
        int firstBatchId = getBatchIdFor(zipFileName);
        PhaseTrackMasterDB ingestPhaseFirstIngestion = getPhaseTrackMasterFor(firstBatchId, MetadataActivityPhase.INGEST);

        int expNumbOfTargetAndSuccessProductInFirstIngestion = 2;
        int expNumbOfSkippedProductInFisrtIngestion = 0;

        assertThat(ingestPhaseFirstIngestion.getSuccessCount())
                .as("Expected to have all onix file products to be created successfully")
                .isEqualTo(ingestPhaseFirstIngestion.getTargetCount())
                .isEqualTo(expNumbOfTargetAndSuccessProductInFirstIngestion);
        assertThat(ingestPhaseFirstIngestion.getSkipCount())
                .as("Invalid fail count or skip count.")
                .isEqualTo(ingestPhaseFirstIngestion.getFailCount())
                .isEqualTo(expNumbOfSkippedProductInFisrtIngestion);

        // ingest the same onix file second time

        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zipFilePath, crushFtpUploadCreds2);

        int expBatchIdListSize = 2;
        int expBatchIdListSizeAfterRemovingFirstBatchId  = 1;

        getProductAPIService().waitOnNumberOfBatchIdRecordsForFile(zipFileName, expBatchIdListSize);

        List<Integer> batchIdList = getBatchIdListFor(zipFileName);

        assertThat(batchIdList)
                .as("Number of lines is not expected, should be the same with the number of ingestions")
                .hasSize(expBatchIdListSize);
        batchIdList.remove(Integer.valueOf(firstBatchId));
        assertThat(batchIdList)
                .as("Number of lines are batch ids are not expected")
                .hasSize(expBatchIdListSizeAfterRemovingFirstBatchId);

        int secondBatchId = batchIdList.stream().collect(CustomCollectors.toSingleton());

        getProductAPIService().waitOnCompleteStatusForFile(secondBatchId, MetadataActivityPhase.NORMALIZATION);
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

    @TmsLink("AUT-823")
    @Test(groups = {"normalization", "optimization"})
    public void verifyMultipleIngestionSkipCountOfZIPOnix30File() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_ONIX_30_SKIP_COUNT_METADATA_ACTIVITY)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_ONIX_30_SKIP_COUNT_METADATA_ACTIVITY_30_RULE);

        String expectedNormRule = REMOVE_ELEMENT_NORM_RULE_ONIX_30_SKIP_COUNT_METADATA_ACTIVITY_30_RULE.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        OnixTestService onix = new OnixTestService("regression/verifyMultipleIngestionSkipCountOfZIPOnix30File/9780000011913.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zipFilePath = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zipFilePath, crushFtpUploadCreds2);
        onix.getProducts().forEach(product -> waitOnProductInDbBy(product.getTitle()));

        String zipFileName = zipFilePath.getFileName().toString();
        getProductAPIService().waitOnCompleteStatusForFile(zipFileName, MetadataActivityPhase.NORMALIZATION);
        getProductAPIService().waitOnCompleteStatusForFile(zipFileName, MetadataActivityPhase.INGEST);
        int firstBatchId = getBatchIdFor(zipFileName);
        PhaseTrackMasterDB ingestPhaseFirstIngestion = getPhaseTrackMasterFor(firstBatchId, MetadataActivityPhase.INGEST);

        int expNumbOfTargetAndSuccessProductInFirstIngestion = 2;
        int expNumbOfSkippedProductInFisrtIngestion = 0;

        assertThat(ingestPhaseFirstIngestion.getSuccessCount())
                .as("Expected to have all onix file products to be created successfully")
                .isEqualTo(ingestPhaseFirstIngestion.getTargetCount())
                .isEqualTo(expNumbOfTargetAndSuccessProductInFirstIngestion);
        assertThat(ingestPhaseFirstIngestion.getSkipCount())
                .as("Invalid fail count or skip count.")
                .isEqualTo(ingestPhaseFirstIngestion.getFailCount())
                .isEqualTo(expNumbOfSkippedProductInFisrtIngestion);

        // ingest the same onix file second time
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zipFilePath, crushFtpUploadCreds2);

        int expBatchIdListSize = 2;
        int expBatchIdListSizeAfterRemovingFirstBatchId  = 1;

        getProductAPIService().waitOnNumberOfBatchIdRecordsForFile(zipFileName, expBatchIdListSize);

        List<Integer> batchIdList = getBatchIdListFor(zipFileName);

        assertThat(batchIdList)
                .as("Number of lines is not expected, should be the same with the number of ingestions")
                .hasSize(expBatchIdListSize);
        batchIdList.remove(Integer.valueOf(firstBatchId));
        assertThat(batchIdList)
                .as("Number of lines are batch ids are not expected")
                .hasSize(expBatchIdListSizeAfterRemovingFirstBatchId);

        int secondBatchId = batchIdList.stream().collect(CustomCollectors.toSingleton());

        getProductAPIService().waitOnCompleteStatusForFile(secondBatchId, MetadataActivityPhase.NORMALIZATION);
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

    @TmsLink("AUT-823")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptimization21FailedMetadataActivity() {
        getLoginPage().loginAs(SUPER_ADMIN);

        List<MetadataActivityPhase> zipFileMetadataActivityPhases = new ArrayList<>(
                Arrays.asList(MetadataActivityPhase.NORMALIZATION,
                        MetadataActivityPhase.INGEST,
                        MetadataActivityPhase.OPTIMIZATION,
                        MetadataActivityPhase.DISTRIBUTION
                ));

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_OPT_NORM_FAIL_METADATA_ACTIVITY)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_OPT_NORM_FAIL_METADATA_ACTIVITY_30_RULE);

        String expectedNormRule = REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix21By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPNormOptFailOnix21MetadataActivity,
                regressionChannelAssociationSFTPForNormOptFailOnix21MetadataActivity)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_21_FAILED_METADATA_ACTIVITY_PATH_SUBSTITUTE_INVALID);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOptimization21FailedMetadataActivity/9780000011952.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .as("Batch statuses are not expected")
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product -> waitOnProductInDbBy(product.getTitle()));

        onix.toAssets().forEach(AssetTestService::updateOnixDistributionDateAndIntervalForCA);

        getProductAPIService().triggerAutoDistribution();

        zipFileMetadataActivityPhases.forEach(phase -> getProductAPIService().waitOnCompleteStatusForFile(zipFileName, phase));

        MetadataActivityPage metadataActivityPage = getTopMenu().clickManageMetadata();

        metadataActivityPage.getStatusIconOfPhaseElm(zipFileName, MetadataActivityPhase.INGEST).shouldBe(visible);

        metadataActivityPage.getCompletionSummaryElm(zipFileName).shouldHave(text(zipFileName));
        metadataActivityPage.getAllAssetsProceededElm(zipFileName).shouldBe(visible);

        //normalization phase
        final int EXPECTED_COUNT_OF_NORM_PHASE = 1;
        PhaseTrackMasterDB normalizationPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.NORMALIZATION);

        assertThat(normalizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid normalized products target count.")
                .isEqualTo(normalizationPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid normalized products success count.")
                .isEqualTo(EXPECTED_COUNT_OF_NORM_PHASE);

        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.NORMALIZATION))
                .as("Invalid number of failures.")
                .isEqualTo(String.valueOf(normalizationPhaseTrackMasterDB.getFailCount()));

        //ingest phase
        PhaseTrackMasterDB ingestPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.INGEST);

        metadataActivityPage.getNumberOfProductsElm(zipFileName)
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())))
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getSuccessCount())));

        metadataActivityPage.getNumberOfIncomingProductsElm(zipFileName)
                .shouldHave(text(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())));

        assertThat(ingestPhaseTrackMasterDB.getFailCount())
                .as("Invalid number of created products from ingested file.")
                .isZero();

        //optimization phase
        final int expectedFailOptimizationCount = 2;

        PhaseTrackMasterDB optimizationPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.OPTIMIZATION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.OPTIMIZATION))
                .as("Invalid optimization fail count.")
                .isEqualTo(String.valueOf(optimizationPhaseTrackMasterDB.getFailCount()));

        assertThat(optimizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid optimization phase track target count.")
                .isGreaterThanOrEqualTo(expectedFailOptimizationCount);

        assertThat(optimizationPhaseTrackMasterDB.getFailCount())
                .as("Invalid optimization phase track success count.")
                .isGreaterThanOrEqualTo(expectedFailOptimizationCount);
    }

    @TmsLink("AUT-823")
    @Test(groups = {"normalization", "optimization"})
    public void verifyNormalizationOptimization30FailedMetadataActivity() {
        getLoginPage().loginAs(SUPER_ADMIN);

        List<MetadataActivityPhase> zipFileMetadataActivityPhases = new ArrayList<>(
                Arrays.asList(MetadataActivityPhase.NORMALIZATION,
                        MetadataActivityPhase.INGEST,
                        MetadataActivityPhase.OPTIMIZATION,
                        MetadataActivityPhase.DISTRIBUTION
                ));

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .selectBusinessUnitWithJSBy(BU_2_NAME)
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_2_NAME)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_ONIX_30_OPT_NORM_FAIL_METADATA_ACTIVITY)
                .setRuleAndComment(REMOVE_ELEMENT_NORM_RULE_ONIX_30_OPT_NORM_FAIL_METADATA_ACTIVITY_30_RULE);

        String expectedNormRule = REMOVE_ELEMENT_NORM_RULE_ONIX_30_OPT_NORM_FAIL_METADATA_ACTIVITY_30_RULE.getRule();
        await().untilAsserted(() -> {
            String actualNormalizationProfile = getTenantNormalizationProfileOnix30By(BU_2_NAME);
            assertThat(actualNormalizationProfile)
                    .as("Invalid normalization rule has been applied to BU.")
                    .isEqualTo(expectedNormRule);
        });

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelSFTPNormOptFailOnix30MetadataActivity,
                regressionChannelAssociationSFTPForNormOptFailOnix30MetadataActivity)
                .createChannelWithCA();

        getTopMenu()
                .clickManageChannelWithJS()
                .openOptimizationPageFor(channelAndCA.getChannelName())
                .setOptimizationRulesAndComments(REGRESSION_OPT_NORM_30_FAILED_METADATA_ACTIVITY_PATH_SUBSTITUTE_INVALID);

        OnixTestService onix = new OnixTestService("regression/verifyNormalizationOptimization30FailedMetadataActivity/9780000011945.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile();

        Path zippedOnix = zipFile(onix.getFilePath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        String zipFileName = zippedOnix.getFileName().toString();
        await().untilAsserted(() -> {
            boolean isBatchIdExist = isBatchMasterIdExistsFor(zipFileName);
            assertThat(isBatchIdExist)
                    .as("Batch record does not exist in DB.")
                    .isTrue();
        });

        assertThat(getBatchValidationErrorFor(zipFileName))
                .as("Validation error has been found for: " + zipFileName)
                .isNull();

        assertThat(getBatchStatusFor(zipFileName))
                .as("Batch statuses are not expected")
                .has(anyOf(new Condition<>(status -> status.equals(BatchStatus.VALID), BatchStatus.VALID.toString()),
                        new Condition<>(status -> status.equals(BatchStatus.RECEIVED_SUCCESS), BatchStatus.RECEIVED_SUCCESS.toString())));

        onix.getProducts().forEach(product -> waitOnProductInDbBy(product.getTitle()));

        onix.toAssets().forEach(AssetTestService::updateOnixDistributionDateAndIntervalForCA);

        getProductAPIService().triggerAutoDistribution();

        zipFileMetadataActivityPhases.forEach(phase -> getProductAPIService().waitOnCompleteStatusForFile(zipFileName, phase));

        MetadataActivityPage metadataActivityPage = getTopMenu().clickManageMetadata();

        metadataActivityPage.getStatusIconOfPhaseElm(zipFileName, MetadataActivityPhase.INGEST).shouldBe(visible);

        metadataActivityPage.getCompletionSummaryElm(zipFileName).shouldHave(text(zipFileName));
        metadataActivityPage.getAllAssetsProceededElm(zipFileName).shouldBe(visible);

        //normalization phase
        final int EXPECTED_COUNT_OF_NORM_PHASE = 1;
        PhaseTrackMasterDB normalizationPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.NORMALIZATION);

        assertThat(normalizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid normalized products target count.")
                .isEqualTo(normalizationPhaseTrackMasterDB.getSuccessCount())
                .as("Invalid normalized products success count.")
                .isEqualTo(EXPECTED_COUNT_OF_NORM_PHASE);

        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.NORMALIZATION))
                .as("Invalid number of failures.")
                .isEqualTo(String.valueOf(normalizationPhaseTrackMasterDB.getFailCount()));

        //ingest phase
        PhaseTrackMasterDB ingestPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.INGEST);

        metadataActivityPage.getNumberOfProductsElm(zipFileName)
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())))
                .shouldHave(exactText(String.valueOf(ingestPhaseTrackMasterDB.getSuccessCount())));

        metadataActivityPage.getNumberOfIncomingProductsElm(zipFileName)
                .shouldHave(text(String.valueOf(ingestPhaseTrackMasterDB.getTargetCount())));

        assertThat(ingestPhaseTrackMasterDB.getFailCount())
                .as("Invalid number of created products from ingested file.")
                .isZero();

        //optimization phase
        final int expectedFailOptimizationCount = 2;

        PhaseTrackMasterDB optimizationPhaseTrackMasterDB = getPhaseTrackMasterFor(zipFileName, MetadataActivityPhase.OPTIMIZATION);
        assertThat(metadataActivityPage.getNumberOfFailuresOfPhase(zipFileName, MetadataActivityPhase.OPTIMIZATION))
                .as("Invalid optimization fail count.")
                .isEqualTo(String.valueOf(optimizationPhaseTrackMasterDB.getFailCount()));

        assertThat(optimizationPhaseTrackMasterDB.getTargetCount())
                .as("Invalid optimization phase track target count.")
                .isGreaterThanOrEqualTo(expectedFailOptimizationCount);

        assertThat(optimizationPhaseTrackMasterDB.getFailCount())
                .as("Invalid optimization phase track success count.")
                .isGreaterThanOrEqualTo(expectedFailOptimizationCount);
    }
}
