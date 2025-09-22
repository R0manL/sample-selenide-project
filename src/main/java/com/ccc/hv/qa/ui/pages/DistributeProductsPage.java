package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.*;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.AssetType;
import com.ccc.hv.qa.ui.enums.BatchODDWarningMsg;
import com.ccc.hv.qa.ui.enums.ProductType;
import com.ccc.hv.qa.ui.pojos.BatchODDEntity;
import com.ccc.hv.qa.ui.pojos.BatchODDProductTypesEntity;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.utils.DateTimeUtils.convertFromUIBatchODDConfirmMsg;
import static com.ccc.hv.qa.utils.DateTimeUtils.getServerLocalDateTimeNow;
import static com.ccc.hv.qa.utils.FileOpsUtils.getAbsoluteResourceFilePath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;

public class DistributeProductsPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final SelenideElement WIZARD_STEP_CONTAINER_ELM = $("*:not([style*='display: none']).step");
    private static final String NO_ISBNS_IN_UPLOADED_FILE = "The file uploaded does not contain any valid ISBNs. Please correct and resubmit.";

    private DistributeProductsPage() {
        // none
    }

    public static DistributeProductsPage getDistributeProductsPage() {
        return new DistributeProductsPage();
    }

    public DistributeProductsPage setupBatchODDWithConfirmation(BatchODDEntity batchODDEntity) {
        setupBatchODD(batchODDEntity);
        log.info("Verify confirmation message.");
        verifyConfirmationMessage(batchODDEntity.getChannelName());

        return this;
    }

    public DistributeProductsPage setupBatchODD(BatchODDEntity batchODDEntity) {
        log.info("Set up batch ODD.");
        selectEntireCatalogOrProducts(batchODDEntity.isSelectEntireCatalog())
                .selectChannel(batchODDEntity.getChannelName())
                .clickContinue()
                .selectProductTypes(batchODDEntity)
                .clickContinue()
                .selectApplyRule(batchODDEntity.isApplyRule())
                .clickContinue()
                .selectSendOnHoldProducts(batchODDEntity.isSendOnHoldProducts())
                .selectSendLockedProducts(batchODDEntity.isSendLockedProducts())
                .clickContinue();

        if (batchODDEntity.isSelectEntireCatalog()) {
            clickContinue();
        } else {
            uploadFileWithProductISBNs(batchODDEntity.getPathToXlsxFile());
        }

        return this;
    }

    public SelenideElement getNoIsbnsInUploadedFileErrorMsgElm() {
        log.info("Get 'The file uploaded does not contain any valid ISBNs' error message");

        return $("div.error").$x("./*[normalize-space(text())='" + NO_ISBNS_IN_UPLOADED_FILE + "']");
    }

    public SelenideElement getScheduledConfirmationMsgElm() {
        log.info("Get 'No data available in table' message");

        return WIZARD_STEP_CONTAINER_ELM.$x(".//*[@class='job']/../*[normalize-space(text())='has been scheduled']");
    }

    public SelenideElement getWarningMessageElmFor(@NotNull String channelName, ProductType productType,
                                                   AssetType assetType, @NotNull String isbn, BatchODDWarningMsg warningMsg) {

        String warningMsgText = warningMsg.getMsgText();

        log.info("Get warning message element for channel: " + channelName + " , product type: " + productType + " , " +
                "asset type: " + assetType + " , isbn: " + isbn + " , expected warning message: " + warningMsgText + ".");

        ElementsCollection elementsCollection = $$x(".//td[normalize-space()='" + channelName + "']" +
                "/following-sibling::td[@class='  sorting_1' and text()='" + productType.toString() + "']" +
                "/following-sibling::td[text()= '" + assetType.getDistributionPathValue() + "']" +
                "/following-sibling::td[@class='  sorting_2' and text()='" + isbn + "']" +
                "/following-sibling::td[text()='" + warningMsgText + "']");

        Objects.requireNonNull(elementsCollection, "Warning message element not found");
        elementsCollection.shouldHave(CollectionCondition.size(1));

        return elementsCollection.first();
    }

    private DistributeProductsPage selectEntireCatalogOrProducts(boolean selectEntireCatalog) {
        if (selectEntireCatalog) {
            log.info("Click 'Entire Catalog'.");
            SelenideElement catalogLink = WIZARD_STEP_CONTAINER_ELM.$x(".//*[@class='text' and text()='Entire Catalog']");

            //Sometimes we click at link but UI do not load next page. Add small delay to solve this issue.
            catalogLink.shouldBe(Condition.visible);
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
            catalogLink.click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        } else {
            log.info("Click 'Select Products'.");
            SelenideElement productLink = WIZARD_STEP_CONTAINER_ELM.$x(".//*[@class='text' and text()='Select Products']");

            //Sometimes we click at link but UI do not load next page. Add small delay to solve this issue.
            productLink.shouldBe(Condition.visible);
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
            productLink.click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        }

        return this;
    }

    private DistributeProductsPage selectProductTypes(BatchODDEntity batchODDEntity) {
        batchODDEntity.getBatchODDProductTypeEntities().forEach(this::selectProductType);

        return this;
    }

    private DistributeProductsPage selectChannel(@NotNull String channelName) {
        log.info("Select '" + channelName + "' channel.");
        WIZARD_STEP_CONTAINER_ELM.$("select.tron").selectOptionContainingText(channelName);
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

        return this;
    }

    private DistributeProductsPage selectProductType(BatchODDProductTypesEntity batchODDProductTypes) {
        ProductType productType = batchODDProductTypes.getProductType();

        log.info("Select product type: '" + productType + "'");
        final SelenideElement productContainer = WIZARD_STEP_CONTAINER_ELM.$x(".//*[@class='product-group' and ./*[@class='heading' " +
                "and normalize-space(text())='" + productType + "']]");

        batchODDProductTypes.getContentTypes().forEach(contentType -> {
            log.info("Select content type: '" + contentType + "'");
            productContainer.$x(".//*[@class='group' and ./*[normalize-space(text())='Content']]" +
                    String.format("//li[normalize-space(text())='%s']", contentType)).click();
        });

        batchODDProductTypes.getCollateralTypes().forEach(collateralType -> {
            log.info("Select collateral type: '" + collateralType + "'");
            productContainer.$x(".//*[@class='group' and ./*[normalize-space(text())='Collateral']]" +
                    String.format("//li[normalize-space(text())='%s']", collateralType)).click();
        });

        batchODDProductTypes.getMetadataTypes().forEach(metadataType -> {
            log.info("Select metadata type: '" + metadataType + "'");
            productContainer.$x(".//*[@class='group' and ./*[normalize-space(text())='Metadata']]" +
                    "//li[normalize-space(text())='" + metadataType + "']").click();
        });

        return this;
    }

    private DistributeProductsPage selectApplyRule(boolean isSelected) {
        if (isSelected) {
            log.info("Select: Apply rule.");
            WIZARD_STEP_CONTAINER_ELM.$x(".//*[normalize-space(text())='Apply']").click();
        } else {
            log.info("Select: Do not Apply rule.");
            WIZARD_STEP_CONTAINER_ELM.$x(".//*[normalize-space(text())='Do not apply']").click();
        }

        return this;
    }

    private DistributeProductsPage selectSendOnHoldProducts(boolean isEnabled) {
        String optionLocatorTemplate = ".//*[normalize-space(text())='on hold products']/..//*[contains(@class,'option') and normalize-space(text())='%s']";
        if (isEnabled) {
            log.info("Select send on hold products.");
            WIZARD_STEP_CONTAINER_ELM.$x(String.format(optionLocatorTemplate, "Send")).click();
        } else {
            log.info("Select do not send on hold products.");
            WIZARD_STEP_CONTAINER_ELM.$x(String.format(optionLocatorTemplate, "Do not send")).click();
        }

        return this;
    }

    private DistributeProductsPage selectSendLockedProducts(boolean isEnabled) {
        String optionLocatorTemplate = ".//*[normalize-space(text())='locked products']/..//*[contains(@class,'option') and normalize-space(text())='%s']";
        if (isEnabled) {
            log.info("Select send locked products.");
            WIZARD_STEP_CONTAINER_ELM.$x(String.format(optionLocatorTemplate, "Send")).click();
        } else {
            log.info("Select do not send locked products.");
            WIZARD_STEP_CONTAINER_ELM.$x(String.format(optionLocatorTemplate, "Do not send")).click();
        }

        return this;
    }

    private void uploadFileWithProductISBNs(@NotNull Path xlsxFilePath) {
        xlsxFilePath = getAbsoluteResourceFilePath(xlsxFilePath);
        log.info("Upload file '" + xlsxFilePath.getFileName() + "' with product ISBNs");

        WIZARD_STEP_CONTAINER_ELM.$("input[type='file']").uploadFile(xlsxFilePath.toFile());
        $("#checkingMissingProducts")
                .shouldBe(Condition.disappear, Duration.ofMillis(Configuration.timeout * 3));

        log.info("File with product ISBNs has been successfully uploaded");
    }

    private DistributeProductsPage clickContinue() {
        log.debug("Click continue.");
        WIZARD_STEP_CONTAINER_ELM.$(".btn.btn-primary.continue").click();

        return this;
    }

    private DistributeProductsPage verifyConfirmationMessage(@NotNull String channelName) {
        final SelenideElement confirmationMsgElm = WIZARD_STEP_CONTAINER_ELM.$(".job");

        confirmationMsgElm.should(Condition.text(channelName));

        String confirmationMsg = confirmationMsgElm.getOwnText();
        String scheduledDate = confirmationMsg
                .split(channelName)[1]
                .replace("(^\\h*)|(\\h*$)", "")
                .replace("\u00A0", " ")
                .replace("\u2007", " ")
                .replace("\u202F", " ")
                .replace("in the morning", "AM")
                .replace("in the afternoon", "PM")
                .replace("in the evening", "PM")
                .replace("at night", "AM")
                .replace("noon", "PM")
                .trim();

        LocalDateTime actualScheduledDate = convertFromUIBatchODDConfirmMsg(scheduledDate);
        assertThat(actualScheduledDate)
                .as("Scheduled date is not close to current.")
                .isCloseTo(getServerLocalDateTimeNow(), byLessThan(ENV_CONFIG.pageLoadTimeout(), ChronoUnit.MINUTES));

        return this;
    }
}
