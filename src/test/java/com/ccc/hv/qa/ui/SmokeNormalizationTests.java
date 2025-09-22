package com.ccc.hv.qa.ui;

import com.ccc.hv.qa.file.pojo.OnixFile;
import com.ccc.hv.qa.ui.data.PredBUs;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static com.codeborne.selenide.Condition.visible;
import static com.ccc.hv.qa.db.services.ProductDBService.waitOnProductInDbBy;
import static com.ccc.hv.qa.db.services.TenantDBService.getTenantNormalizationProfileOnix21By;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds2;
import static com.ccc.hv.qa.file.services.OnixFileService.readOnixFile;
import static com.ccc.hv.qa.ui.data.NormalizationRules.SMOKE_SIMPLE_NORMALIZATION_RULE;
import static com.ccc.hv.qa.ui.data.PredUsers.SUPER_ADMIN;
import static com.ccc.hv.qa.ui.pages.HomePage.getHomePage;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.OriginalNormalizationFilesPage.getOriginalNormalizationFilesPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.FileTestService.getFileTestService;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static com.ccc.hv.qa.utils.FileOpsUtils.compareFileLists;
import static com.ccc.hv.qa.utils.FileOpsUtils.zipFile;
import static org.assertj.core.api.Assertions.assertThat;

@Test(singleThreaded = true, groups = {"smoke", "ui"})
public class SmokeNormalizationTests extends UITestBase {

    @TmsLink("HRV-30185")
    public void verifyNormalizationRules() {
        final String BU_NAME = PredBUs.PRED_AUTOMATION_BU_2.getName();
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickManageBusinessUnitWithJS()
                .openNormalizationPageFor(BU_NAME)
                .setRuleAndComment(SMOKE_SIMPLE_NORMALIZATION_RULE);

        String expectedNormRule = SMOKE_SIMPLE_NORMALIZATION_RULE.getRule();
        assertThat(getTenantNormalizationProfileOnix21By(BU_NAME))
                .as("Invalid normalization rule has applied to BU.")
                .isEqualTo(expectedNormRule);

        Path origOnixFilePath = Paths.get("smoke/verifyNormalizationRules/autoNormalize.xml");
        OnixFile updatedOnixFile = readOnixFile(origOnixFilePath)
                .updateProductsTitle()
                .saveAsNewFile();

        Path zippedOnix = zipFile(updatedOnixFile.getPath());
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(zippedOnix, crushFtpUploadCreds2);

        updatedOnixFile.getProducts().forEach(product -> {
            final String PRODUCT_TITLE = product.getTitle();
            waitOnProductInDbBy(PRODUCT_TITLE);

            getTopMenu()
                    .searchPresentProductBy(PRODUCT_TITLE)
                    .productWithBusinessUnit(BU_NAME)
                    .shouldBe(visible);
        });

        String expectedZippedOnixFileName = zippedOnix.getFileName().toString();

        File downloadedFilteredOriginalNormZipFile = getHomePage()
                .clickSystem()
                .navigateToOriginalNormalizationFilesPage()
                .enterAlphaCode(PredBUs.PRED_AUTOMATION_BU_2.getAlphaCode())
                .clickLoad()
                .enterTextToFilterInput(expectedZippedOnixFileName)
                .downloadFileWithName(expectedZippedOnixFileName);

        compareFileLists(Collections.singletonList(zippedOnix.toFile()), Collections.singletonList(downloadedFilteredOriginalNormZipFile));

        File downloadedNotFilteredOriginalNormZipFile = getOriginalNormalizationFilesPage().clearFilteredText()
                .downloadFileWithName(expectedZippedOnixFileName);

        compareFileLists(Collections.singletonList(zippedOnix.toFile()), Collections.singletonList(downloadedNotFilteredOriginalNormZipFile));
    }
}
