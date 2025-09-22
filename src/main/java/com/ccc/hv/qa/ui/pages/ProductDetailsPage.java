package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.*;
import com.ccc.hv.qa.db.enums.AssetDistributionStatus;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.AssetType;
import com.ccc.hv.qa.ui.pojos.Asset;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.core.CustomCollectors.toSingleton;
import static com.ccc.hv.qa.ui.pages.HomePage.getHomePage;
import static com.ccc.hv.qa.ui.pages.ProductAddPage.getProductAddPage;
import static com.ccc.hv.qa.ui.pages.DistributionOnHoldOrLockedModal.getDistributionOnHoldOrLockedModal;
import static com.ccc.hv.qa.ui.pages.ProductMetadataEditPage.getProductMetadataEditPage;
import static com.ccc.hv.qa.ui.pages.PageBase.activePageContainer;
import static com.ccc.hv.qa.ui.pages.PageBase.waitOnLoadingMsgDisappeared;
import static com.ccc.hv.qa.ui.pages.ProductAppleMetadataPage.getProductAppleMetadataPage;
import static com.ccc.hv.qa.ui.pages.ChannelSelectPage.getChannelSelectPage;
import static com.ccc.hv.qa.utils.DateTimeUtils.convertFromUIAssetTimeStamp;
import static com.ccc.hv.qa.utils.StringUtils.remove2LastChar;

public class ProductDetailsPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private ProductDetailsPage() {
        $("#assetSection").shouldBe(visible);
    }

    public static ProductDetailsPage getProductDetailsPage() {
        return new ProductDetailsPage();
    }

    public HomePage navigateToHomePage() {
        $x(".//a[@class='ui-link' and normalize-space(text())='Home']").click();
        return getHomePage();
    }

    public String getProductTitle() {
        return activePageContainer.$("#title").getText();
    }

    public String getAuthorName() {
        return activePageContainer.$("#author").getAttribute("data-author");
    }

    public String getIsbn() {
        return activePageContainer.$("#isbn").getAttribute("data-isbn");
    }

    public String getPublisherName() {
        return activePageContainer.$("#publisher").getAttribute("data-publisher");
    }

    public String getProductType() {
        return activePageContainer.$("#productType").getAttribute("data-producttype");
    }

    public String getOnSaleDate() {
        return activePageContainer.$("#onsaledate").getAttribute("data-onsaledate");
    }

    public String getRecordReference() {
        return activePageContainer.$("#recordReference").getAttribute("data-recordreference");
    }

    public String getProductSubject() {
        return activePageContainer.$("#subject").getAttribute("data-subject");
    }

    public List<String> getProductImprints() {
        return activePageContainer.$$("#imprints ul li").texts();
    }

    public ProductAddPage addAssets() {
        activePageContainer.$("#quickIngest").click();
        waitOnLoadingMsgDisappeared();
        return getProductAddPage();
    }

    public ElementsCollection getAllAssetElements() {
        return activePageContainer.$$(".product-assets-3-0").filterBy(visible);
    }

    public List<File> downloadAssets() {
        return getAllAssetElements().asDynamicIterable().stream()
                .map(element -> element.$("div.download a"))
                .map(this::downloadByElementLink)
                .collect(Collectors.toList());
    }

    public File downloadAssetBy(AssetType type) {
        SelenideElement downloadLink = getAssetBy(type).$(".download a");

        return downloadByElementLink(downloadLink);
    }

    public ProductDetailsPage selectAssetsBy(AssetType... types) {
        for (AssetType type : types) {
            log.info("Select asset with '" + type + "' type.");
            getAssetBy(type).$("input.tab-bulk-select").setSelected(true);
        }

        return this;
    }

    private SelenideElement getAssetBy(AssetType type) {
        return getAllAssetElements().asDynamicIterable()
                .stream()
                .filter(elm -> elm.$x(".//*[starts-with(normalize-space(text()),'" + type.getText() + "')]").isDisplayed())
                .collect(toSingleton());
    }

    public ProductDetailsPage selectAssetsBy(@NotNull String... fileNamesWithExt) {
        for (String fileName : fileNamesWithExt) {
            log.info("Select asset by '" + fileName + "' fileName.");
            $x(".//*[@class='product-assets-3-0' " +
                    "and .//*[@class='asset-value' and text()='" + fileName + "']]//input[@type='checkbox']")
                    .setSelected(true);
        }

        return this;
    }

    @NotNull
    public Asset getAssetDetailsBy(@NotNull String assetFileName) {
        List<Asset> filteredAssets = getAllAssets().stream()
                .filter(a -> a.getFileName().equals(assetFileName))
                .collect(Collectors.toList());

        if (filteredAssets.size() != 1) {
            throw new IllegalStateException(filteredAssets.size() +
                    " assets have been found (expect one) by file name: '" + assetFileName + "'.");
        }

        return filteredAssets.stream()
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Filtered Assets list is empty."));
    }

    public ProductMetadataEditPage clickEditProductMetadata() {
        $(".onix-edit-link").click();

        return getProductMetadataEditPage();
    }

    public SelenideElement getDeleteButton() {
        return $("#deleteBtn");
    }

    public void deleteSelectedAsset() {
        getDeleteButton().click();
        $("#assetDeleteModalAction").click();
    }

    public List<Asset> getAllAssets() {
        List<Asset> assetList = new ArrayList<>();
        getAllAssetElements().asDynamicIterable().forEach(asset -> {
            List<String> assetLabels = asset.$$("span.asset-label").texts();
            List<String> assetValues = asset.$$("span.asset-value").texts();
            AssetType assetType = AssetType.from(remove2LastChar(asset.$("div.type-name div").getText()));

            assetList.add(Asset.builder()
                    .type(assetType)
                    .fileName(assetValues.get(assetLabels.indexOf("Filename:")))
                    .lastRevisioned(convertFromUIAssetTimeStamp(assetValues.get(assetLabels.indexOf("Last revisioned:"))))
                    .firstAdded(convertFromUIAssetTimeStamp(assetValues.get(assetLabels.indexOf("First added:"))))
                    .fileSize(assetValues.get(assetLabels.indexOf("File size:")))
                    .build());
        });

        return assetList;
    }

    public SelenideElement getAssetDistributionStatusElmFor(@NotNull String channelName, AssetType assetType,
                                                            AssetDistributionStatus withStatus) {
        log.debug("Get distribution status element for asset type: '" + assetType.getText() + "', " +
                "channel: '" + channelName + "' " +
                "with status:'" + withStatus + "'.");

        return getAssetBy(assetType).$x(".//tr[@class='distribution-history-item' and @data-status='" + withStatus + "' " +
                "and @data-channel='" + channelName + "']");
    }

    public ElementsCollection getAssetDistributionChannelsElmsFor(AssetType assetType) {
        log.debug("Get distribution channel element list for asset type: '" + assetType.getText() + "' ");

        return getAssetBy(assetType).$$("tr.distribution-history-item .channel");
    }

    @NotNull
    private File downloadByElementLink(SelenideElement element) {
        File file;
        try {
            file = element.download();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not provide download functionality for the element" + element);
        }
        return file;
    }

    public boolean isOnHoldActive() {
        return $("div.hold").has(not(cssClass("disabled")));
    }

    public boolean isProductLocked() {
        return $("div.lock").has(not(cssClass("disabled")));
    }

    public ProductDetailsPage putOnHold() {
        $("#removeOnHold").scrollIntoView(false).click();
        $("#hold-modal-button").click();
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        $("div.modal-dialog").should(disappear);

        return this;
    }

    public ProductDetailsPage removeOnHold() {
        $("#putOnHold").click();
        $("#hold-modal-button").click();
        $("div.modal-dialog").should(disappear);

        return this;
    }

    public void deleteProduct() {
        $("#deleteProductGroup button").click();
        $("#productDeleteModalAction").click();
    }

    public ChannelSelectPage clickDistribute() {
        $("#distributeBtn").click();

        return getChannelSelectPage();
    }

    public DistributionOnHoldOrLockedModal clickDistributeOnHoldOrLocked() {
        $("#distributeBtn").click();
        return getDistributionOnHoldOrLockedModal();
    }

    public ProductAppleMetadataPage clickAppleMetadataLink() {
        getAppleMetadataLink().click();

        return getProductAppleMetadataPage();
    }

    public SelenideElement getAppleMetadataLink() {
        return $$("#appleMeta").shouldHave(CollectionCondition.size(1))
                .first();
    }

    public SelenideElement getAppleMetadataLinkFor(AssetType type) {
        return getAssetBy(type).$("#appleMeta");
    }
}
