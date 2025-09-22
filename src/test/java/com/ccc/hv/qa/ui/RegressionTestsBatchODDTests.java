package com.ccc.hv.qa.ui;

import com.ccc.hv.qa.ui.services.*;
import com.codeborne.selenide.Condition;
import com.ccc.hv.qa.file.pojo.OnixProduct;
import com.ccc.hv.qa.ui.enums.PublishingStatus;
import com.ccc.hv.qa.ui.pages.DistributeProductsPage;
import com.ccc.hv.qa.ui.pages.ProductDetailsPage;
import com.ccc.hv.qa.ui.services.*;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.AssertionsForClassTypes;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.visible;
import static com.ccc.hv.qa.db.services.ProductDBService.hasProductOnHold;
import static com.ccc.hv.qa.db.services.ProductDBService.isProductLocked;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds;
import static com.ccc.hv.qa.ui.data.BatchODDEntities.*;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.*;
import static com.ccc.hv.qa.ui.data.Channels.*;
import static com.ccc.hv.qa.ui.data.PredUsers.SUPER_ADMIN;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@Test(singleThreaded = true, groups = "ui") //Execute all test alphabetically in single thread.
public class RegressionTestsBatchODDTests extends UITestBase {

    @TmsLink("AUT-378")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario1() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario1,
                regressionCASFTPForBatchODDScenario1)
                .createChannelWithCA();

        //product that should pass batchODD distribution setup
        OnixTestService passOnix = new OnixTestService("regression/verifyBatchODDScenario1/9780136161499.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService passContent = new ContentTestService("regression/verifyBatchODDScenario1/assets/9780136161499.pdf")
                .cloneFileWith(passOnix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService passCollateral = new CollateralTestService("regression/verifyBatchODDScenario1/assets/9780136161499_marketingImage.jpg")
                .cloneFileWith(passOnix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        //product that should NOT pass batchODD distribution setup
        OnixTestService failOnix = new OnixTestService("regression/verifyBatchODDScenario1/9780136161443.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService failContent = new ContentTestService("regression/verifyBatchODDScenario1/assets/9780136161443.pdf")
                .cloneFileWith(failOnix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService failCollateral = new CollateralTestService("regression/verifyBatchODDScenario1/assets/9780136161443_marketingImage.jpg")
                .cloneFileWith(failOnix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_1)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(failOnix.toAsset(), failContent, failCollateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());

        Arrays.asList(passOnix.toAsset(), passContent, passCollateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-385")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario2() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario2,
                regressionCASFTPForBatchODDScenario2)
                .createChannelWithCA();

        OnixTestService onixWithoutDistrCode = new OnixTestService("regression/verifyBatchODDScenario2/9780136161444.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario2/assets/9780136161444.pdf")
                .cloneFileWith(onixWithoutDistrCode.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario2/assets/9780136161444_marketingImage.jpg")
                .cloneFileWith(onixWithoutDistrCode.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_2)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onixWithoutDistrCode.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());
    }

    @TmsLink("AUT-388")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario3() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario3,
                regressionCASFTPForBatchODDScenario3)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario3/9780136161445.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario3/assets/9780136161445.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario3/assets/9780136161445_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_3)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-389")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario4() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario4,
                regressionCASFTPForBatchODDScenario4)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario4/9780136161446.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario4/assets/9780136161446.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario4/assets/9780136161446_marketingImage.jpg")
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

        AssertionsForClassTypes.assertThat(hasProductOnHold(onix.getSingleProductTitle()))
                .as("Product '" + onix.getSingleProductTitle() + "' is not in OnHold state in DB.")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_4)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-390")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario5() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario5,
                regressionCASFTPForBatchODDScenario5)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario5/9780136161497.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario5/assets/9780136161497.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario5/assets/9780136161497_marketingImage.jpg")
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

        AssertionsForClassTypes.assertThat(hasProductOnHold(onix.getSingleProductTitle()))
                .as("Product '" + onix.getSingleProductTitle() + "' is not in OnHold state in DB.")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_5)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-391")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario6() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario6,
                regressionCASFTPForBatchODDScenario6)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario6/9780136161495.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario6/assets/9780136161495.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario6/assets/9780136161495_marketingImage.jpg")
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

        AssertionsForClassTypes.assertThat(hasProductOnHold(onix.getSingleProductTitle()))
                .as("Product '" + onix.getSingleProductTitle() + "' is not in OnHold state in DB.")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_6)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-392")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario7() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario7, regressionCASFTPForBatchODDScenario7)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario7/9780136161496.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct product = onix.getSingleProduct();
        final String PRODUCT_TITLE = product.getTitle();
        assertThat(PublishingStatus.fromValue(onix.getSingleProduct().getPublishingStatusCode()))
                .as("Product '" + PRODUCT_TITLE + "' status is not locked in Onix file.")
                .isEqualTo(PublishingStatus.WITHDRAWN_FROM_SALE);

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario7/assets/9780136161496.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario7/assets/9780136161496_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage();

        assertThat(productDetailsPage.isProductLocked())
                .as("'" + PRODUCT_TITLE + "' product has not locked on product details page.")
                .isTrue();

        AssertionsForClassTypes.assertThat(isProductLocked(onix.getSingleProductTitle()))
                .as("Product '" + PRODUCT_TITLE + "' has not locked in DB.")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_7)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(content, collateral)
                .forEach(asset -> asset.checkIsNotDistributedTo(channelAndCA.getChannelName()));

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-393")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario8() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario8,
                regressionCASFTPForBatchODDScenario8)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario8/9780136161477.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario8/assets/9780136161477.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario8/assets/9780136161477_marketingImage.jpg")
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

        AssertionsForClassTypes.assertThat(hasProductOnHold(onix.getSingleProductTitle()))
                .as("Product '" + onix.getSingleProductTitle() + "' is not in OnHold state in DB.")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_8)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-394")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario9() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario9, regressionCASFTPForBatchODDScenario9)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario9/9780136161488.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct product = onix.getSingleProduct();
        final String PRODUCT_TITLE = product.getTitle();
        assertThat(PublishingStatus.fromValue(onix.getSingleProduct().getPublishingStatusCode()))
                .as("Product '" + PRODUCT_TITLE + "' status is not locked in Onix file.")
                .isEqualTo(PublishingStatus.WITHDRAWN_FROM_SALE);

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario9/assets/9780136161488.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario9/assets/9780136161488_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage();

        assertThat(productDetailsPage.isProductLocked())
                .as("'" + PRODUCT_TITLE + "' product has not locked on product details page.")
                .isTrue();

        AssertionsForClassTypes.assertThat(isProductLocked(onix.getSingleProductTitle()))
                .as("Product '" + PRODUCT_TITLE + "' has not locked in DB.")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_9)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-395")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario10() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario10,
                regressionCASFTPForBatchODDScenario10)
                .createChannelWithCA();

        //product that should pass batchODD distribution setup
        OnixTestService passOnix = new OnixTestService("regression/verifyBatchODDScenario10/9780136161490.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService passContent = new ContentTestService("regression/verifyBatchODDScenario10/assets/9780136161490.pdf")
                .cloneFileWith(passOnix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService passCollateral = new CollateralTestService("regression/verifyBatchODDScenario10/assets/9780136161490_marketingImage.jpg")
                .cloneFileWith(passOnix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        //product that should NOT pass batchODD distribution setup
        OnixTestService failOnix = new OnixTestService("regression/verifyBatchODDScenario10/9780136161447.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService failContent = new ContentTestService("regression/verifyBatchODDScenario10/assets/9780136161447.pdf")
                .cloneFileWith(failOnix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService failCollateral = new CollateralTestService("regression/verifyBatchODDScenario10/assets/9780136161447_marketingImage.jpg")
                .cloneFileWith(failOnix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_10)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(failOnix.toAsset(), failContent, failCollateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());

        Arrays.asList(passOnix.toAsset(), passContent, passCollateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-396")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario11() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario11, regressionCASFTPForBatchODDScenario11)
                .createChannelWithCA();

        OnixTestService onixWithoutDistrCode = new OnixTestService("regression/verifyBatchODDScenario11/9780136161448.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario11/assets/9780136161448.pdf")
                .cloneFileWith(onixWithoutDistrCode.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario11/assets/9780136161448_marketingImage.jpg")
                .cloneFileWith(onixWithoutDistrCode.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_11)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onixWithoutDistrCode.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());
    }

    @TmsLink("AUT-397")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario12() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario12, regressionCASFTPForBatchODDScenario12)
                .createChannelWithCA();

        OnixTestService onixWithoutDistrCode = new OnixTestService("regression/verifyBatchODDScenario12/9780136161442.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario12/assets/9780136161442.pdf")
                .cloneFileWith(onixWithoutDistrCode.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario12/assets/9780136161442_marketingImage.jpg")
                .cloneFileWith(onixWithoutDistrCode.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_12)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onixWithoutDistrCode.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-398")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario13() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario13,
                regressionCASFTPForBatchODDScenario13)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario13/9780136161449.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario13/assets/9780136161449.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario13/assets/9780136161449_marketingImage.jpg")
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

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_13)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-399")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario14() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario14,
                regressionCASFTPForBatchODDScenario14)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario14/9780136161498.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario14/assets/9780136161498.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario14/assets/9780136161498_marketingImage.jpg")
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

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_14)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-400")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario15() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario15, regressionCASFTPForBatchODDScenario15)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario15/9780136161423.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario15/assets/9780136161423.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario15/assets/9780136161423_marketingImage.jpg")
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

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_15)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-401")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario16() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario16, regressionCASFTPForBatchODDScenario16)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario16/9780136161434.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct product = onix.getSingleProduct();
        final String PRODUCT_TITLE = product.getTitle();
        assertThat(PublishingStatus.fromValue(onix.getSingleProduct().getPublishingStatusCode()))
                .as("Product '" + PRODUCT_TITLE + "' status is not locked in Onix file.")
                .isEqualTo(PublishingStatus.WITHDRAWN_FROM_SALE);

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario16/assets/9780136161434.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario16/assets/9780136161434_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage();

        assertThat(productDetailsPage.isProductLocked())
                .as("'" + PRODUCT_TITLE + "' product has not locked on product details page.")
                .isTrue();

        assertThat(isProductLocked(onix.getSingleProductTitle()))
                .as("Product '" + PRODUCT_TITLE + "' has not locked in DB.")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_16)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());

        onix.toAsset().waitOnSuccessfulDistributionTo(channelAndCA.getChannelName());
    }

    @TmsLink("AUT-402")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario17() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario17,
                regressionCASFTPForBatchODDScenario17)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario17/9780136161232.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario17/assets/9780136161232.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario17/assets/9780136161232_marketingImage.jpg")
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

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_17)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));
    }

    @TmsLink("AUT-401")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario18() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario18, regressionCASFTPForBatchODDScenario18)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario18/9780136161471.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        OnixProduct product = onix.getSingleProduct();
        final String PRODUCT_TITLE = product.getTitle();
        assertThat(PublishingStatus.fromValue(onix.getSingleProduct().getPublishingStatusCode()))
                .as("Product '" + PRODUCT_TITLE + "' status is not locked in Onix file.")
                .isEqualTo(PublishingStatus.WITHDRAWN_FROM_SALE);

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario18/assets/9780136161471.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario18/assets/9780136161471_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        ProductDetailsPage productDetailsPage = getTopMenu()
                .searchPresentProductBy(PRODUCT_TITLE)
                .openProductDetailsPage();

        assertThat(productDetailsPage.isProductLocked())
                .as("'" + PRODUCT_TITLE + "' product has not locked on product details page.")
                .isTrue();

        assertThat(isProductLocked(onix.getSingleProductTitle()))
                .as("Product '" + PRODUCT_TITLE + "' has not locked in DB.")
                .isTrue();

        getTopMenu()
                .clickDistributeProducts()
                .setupBatchODDWithConfirmation(REG_BATCH_ODD_ENTITY_SCENARIO_18)
                .getScheduledConfirmationMsgElm()
                .shouldBe(Condition.visible);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> asset.waitOnSuccessfulDistributionTo(channelAndCA.getChannelName()));

    }

    @TmsLink("AUT-404")
    @Test(groups = {"batchODD"})
    public void verifyBatchODDScenario19() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelSFTPForBatchODDScenario19, regressionCASFTPForBatchODDScenario19)
                .createChannelWithCA();

        OnixTestService onix = new OnixTestService("regression/verifyBatchODDScenario19/9780136161431.xml")
                .readOnixFile()
                .updateTitles()
                .updateRecordReferences()
                .saveAsNewFile()
                .uploadToCrushFTP(crushFtpUploadCreds)
                .waitOnProductsInDB();

        AssetTestService content = new ContentTestService("regression/verifyBatchODDScenario19/assets/9780136161431.pdf")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        AssetTestService collateral = new CollateralTestService("regression/verifyBatchODDScenario19/assets/9780136161431_marketingImage.jpg")
                .cloneFileWith(onix.getSingleProductRecordReference())
                .uploadToCrushFTP(crushFtpUploadCreds);

        DistributeProductsPage distributeProductsPage = getTopMenu()
                .clickDistributeProducts()
                .setupBatchODD(REG_BATCH_ODD_ENTITY_SCENARIO_19);

        distributeProductsPage.getNoIsbnsInUploadedFileErrorMsgElm().shouldBe(visible);
        distributeProductsPage.getScheduledConfirmationMsgElm().shouldNotBe(visible);

        Arrays.asList(onix.toAsset(), content, collateral)
                .forEach(asset -> assertThat(asset.isDistributionScheduledFor(channelAndCA.getChannelName()))
                        .as("The record appears in precompdd table is unexpected.")
                        .isFalse());
    }
}
