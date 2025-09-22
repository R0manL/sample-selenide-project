package com.ccc.hv.qa.ui.pages;

import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pojos.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pojos.*;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.ChannelAssociatedListPage.getChannelAssociatedListPage;
import static com.ccc.hv.qa.ui.pages.CreateTagPage.getCreateTagPage;
import static com.ccc.hv.qa.utils.DateTimeUtils.convertFromUI;

/**
 * Created by R0manL on 01/09/20.
 */

public class AssociatePage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final BlackoutDatesComponent blackoutDatesComponent = new BlackoutDatesComponent();


    private AssociatePage() {
        // None
    }

    public static AssociatePage getChannelAssociatePage() {
        return new AssociatePage();
    }

    public AssociatePage isSuccessMsgVisible() {
        log.info("Verify if alert success is visible.");
        activePageContainer.$(".alerts.success").shouldBe(visible);
        return this;
    }

    public AssociatePage clickSave() {
        log.info("Click save Channel associations.");
        $("#saveBtn").click();
        return this;
    }

    public CreateTagPage navigateToCreateTagPage() {
        log.debug("Navigate to tag filter section.");
        expandDropdownPanel($("#isbnfilter-collapse a"));

        log.debug("Select tag filter options.");
        $("#useIsbnFilters").setSelected(true);

        log.info("Click on 'Don't see a tag in the list? You can create a new tag here.'");
        $("#isbnFilterOptions")
                .$x(".//a[contains(.,\"Don't see a tag in the list? You can create a new tag here.\")]")
                .shouldBe(visible).click();

        return getCreateTagPage();
    }

    public AssociatePage createChannelAssociation(ChannelAssociation ca) {
        log.info("Create channel associations.");

        setAdvancedKeywordSettings(ca.getAdvancedKeywordSettings());
        enableAutomatedDistributionRules(ca.isAutomatedDistributionRules());
        preventDistributionOfCurrentAssetVersions(ca.isPreventOfDistributionOfCurrentAssetVersions());
        setOfOnix30DetermineDistrRules(ca.isUseOnix30ToDetermineDistrRules());

        if (ca.getDistributionRule() != null) {
            setDistributionRules(ca.getDistributionRule());
        }

        List<AssociationServer> assocSrvs = ca.getAssociationServers();
        if (assocSrvs.stream().noneMatch(AssociationServerITMS.class::isInstance)) {
            setSendTriggerFile(ca.isSendTriggerFile());
        }

        setAssociationServers(assocSrvs);
        setContactInformation(ca.getContactInformation());
        setDistributionNotification(ca.getDistributionNotificationRecipients());
        setTakeDownNotification(ca.getTakeDownNotificationRecipients());
        setBlackoutDates(ca.getBlackoutDates());
        setComments(ca.getComment());

        return this;
    }

    public SelenideElement getCAPageErrorElm() {
        return $("#associate-channel-error");
    }

    public SelenideElement getCASectionErrorElm() {
        return $(".panel-default.has-error").$(".text-danger");
    }

    public SelenideElement getPublicationFilterAfterOption() {
        return $("#pubdateFilterBefore").$x(".//span[normalize-space(text())='after']");
    }

    public boolean isPublicationFilterBeforeOptionSelected() {
        return Objects.requireNonNull(getPublicationFilterBeforeOption().getAttribute("class"),
                        "Can't find Publication Filter Before with class attribute.")
                .contains("active");
    }

    public SelenideElement getPublicationFilterBeforeOption() {
        return $("#pubdateFilterBefore").$x(".//span[normalize-space(text())='before']");
    }

    public SelenideElement getPublicationFilterInstructionText() {
        return $("#pubdateFilterBefore :first-child");
    }

    public LocalDate getSelectedPubDate() {
        String selectedDate = $("#pubdate-picker").getAttribute("value");
        Objects.requireNonNull(selectedDate, "Can't get selected date picker date");
        return convertFromUI(selectedDate);
    }

    public ChannelAssociatedListPage cancelSetup() {
        log.debug("Click 'cancel setup' on UI.");
        $("#cancel").click();

        return getChannelAssociatedListPage();
    }

    public ChannelAssociatedListPage returnToAssociatedChannelsList() {
        log.info("Return to associated channels list.");
        $("#associate-list-action").click();

        return getChannelAssociatedListPage();
    }

    @NotNull("Can't find 'CA creation session key' in DOM.")
    public String getSessionKey() {
        return $("[data-key]").getAttribute("data-key");
    }

    public void setAdvancedKeywordSettings(List<AdvancedKeywordSetting> advancedKeywordSettings) {
        if (!advancedKeywordSettings.isEmpty()) {
            for (AdvancedKeywordSetting ks : advancedKeywordSettings) {
                log.info("Add advanced keyword settings: source field value='" + ks.getSourceFieldValue() + "', " +
                        "replacement value='" + ks.getReplacementValue() + "'");

                if (ks.getKeyword() != null) {
                    $("#keywordSelect").selectOptionByValue(ks.getKeyword().getOptionValue());
                }
                $("#sourceField").val(ks.getSourceFieldValue());
                $("#replacement").val(ks.getReplacementValue());
                if (ks.isMakeDefault() && ks.getKeyword().equals(Keyword.IMPRINT)) {
                    $("#isDefault").setSelected(ks.isMakeDefault());
                }
                $("#addKeyword").click();
            }
        }
    }

    public void enableAutomatedDistributionRules(boolean isAutomatedDistributionRules) {
        log.info("Set 'auto distribution' to: " + isAutomatedDistributionRules);
        $("#automaticDistribution").setSelected(isAutomatedDistributionRules);
    }

    public void preventDistributionOfCurrentAssetVersions(boolean isPreventDistributionOfCurrentAssetVersion) {
        log.info("Set 'prevent distribution of current asset versions' to: " + isPreventDistributionOfCurrentAssetVersion);
        if ($("#snapshotDormantAssetsCheckbox").isDisplayed()) {
            $("#snapshotDormantAssetsCheckbox").setSelected(isPreventDistributionOfCurrentAssetVersion);
        }
    }

    public void setOfOnix30DetermineDistrRules(boolean isOnix30DetermineDistrRules) {
        log.info("Set if onix 3.0 determine distribution rules.");
        if (isOnix30DetermineDistrRules) {
            $("#onixVersionToggle").$("span[data-val='ONIX_3_0']").click();
        }
    }

    public void setDistributionRules(DistributionRule dr) {
        log.info("Set distribution rules.");
        expandDropdownPanel($("#autocorrectToggle"));
        $("#autoCorrectToOnSale").setSelected(dr.isProductNotUpForSaleWhenItShouldBe());
        $("#autoCorrectToNotOnSale").setSelected(dr.isProductUpForSaleWhenTtShouldNotBe());
        $("#autoCorrectPrice").setSelected(dr.isIncorrectPrice());
        $("#autoCorrectCover").setSelected(dr.isCoverDoesNotMatch());

        if (dr.isUseTagFilters()) {
            expandDropdownPanel($("#isbnfilter-collapse a"));
            log.debug("Select tag filter options.");
            $("#useIsbnFilters").setSelected(dr.isUseTagFilters());
            TagFilter tf = dr.getTagFilter();
            SelenideElement tagFilterRootElm = $("#isbnFilterOptions");
            String option = tf.isDistribute() ? IsbnFilterType.INCLUDE.toString() : IsbnFilterType.EXCLUDE.toString();
            tagFilterRootElm.$x(".//*[@data-filter-type='" + option + "']").click();
            if (tf.getTagToUse() != null) {
                tagFilterRootElm.$("#tagSelector").selectOptionByValue(tf.getTagToUse().getOptionValue());
            }
        }

        if (dr.isDrmFree() || dr.isPreOrderPreviews() || dr.isExplicitContent()
                || dr.isUseImprintForPublisherName() || dr.isBooksAreNewReleaseUntil()) {
            expandDropdownPanel($("#appleToggle"));
            setAppleSpecificOptions(dr);
        }

        selectAvailableItemsInPanelFromList($("#publisher-collapse a"), $("#publisher-body"), dr.getPublishers());
        selectAvailableItemsInPanelFromList($("#imprints-collapse a"), $("#imprints-body"), dr.getImprints());

        if (!dr.getSuppliers().isEmpty()) {
            expandDropdownPanel($("#supplierToggle"));
            setSuppliers(dr.getSuppliers());
        }

        if (!dr.getDiscountCodes().isEmpty()) {
            expandDropdownPanel($("#discount-collapse a"));
            setDiscountCodes(dr.getDiscountCodes());
        }

        if (!dr.getBisacSubjects().isEmpty()) {
            expandDropdownPanel($x(".//a[normalize-space(text())='BISAC Subjects']"));
            selectBisacSubjectCodes(dr.getBisacSubjects());
        }

        if (dr.isPubDateFilterActive()) {
            expandDropdownPanel($("#pubdateFilterToggle"));

            log.debug("Select publication filter.");

            if (!$("#usePubdateFilters").isSelected()) {
                $("#usePubdateFilters").click();
            }

            if (!dr.isPubDateFilterBefore()) {
                getPublicationFilterAfterOption().click();
            }

            if (dr.getPubDateFilterValue() != null) {
                selectPublicationDate(dr.getPubDateFilterValue());
            }
        }

        selectAvailableItemsInPanelFromList($("#territories-collapse a"), $("#territories-body"), dr.getTerritories());
        selectAvailableItemsInPanelFromList($("#appleToggle"), $("#audience-body"), dr.getAudienceCodes());
    }

    public void setSuppliers(Map<String, String> suppliers) {
        for (Map.Entry<String, String> supplier : suppliers.entrySet()) {
            String supplierId = supplier.getKey();
            log.debug("Set '" + supplierId + "' supplier.");
            $("#supplier-id").val(supplierId);
            String supplierDescription = supplier.getValue();
            if (supplierDescription != null) {
                $("#supplier-description").val(supplierDescription);
            }
            $("#addSupplierID").click();
        }
    }

    public void setDiscountCodes(Map<String, String> discountCodes) {
        for (Map.Entry<String, String> discountCode : discountCodes.entrySet()) {
            String code = discountCode.getKey();
            log.debug("Set '" + code + "' discount code.");
            $("#discount-code").val(code);
            String discountDescription = discountCode.getValue();
            if (discountDescription != null) {
                $("#discount-description").val(discountDescription);
            }
            $("#addDiscountCode").click();
        }
    }

    public void selectBisacSubjectCodes(Map<BisacSubjectCode, Boolean> bisacSubjects) {
        for (Map.Entry<BisacSubjectCode, Boolean> subject : bisacSubjects.entrySet()) {
            String subjectCode = subject.getKey().toString();
            String subjectSuffix = subjectCode.substring(0, 3);
            $x(".//*[@id='parkedSubjectFilters']//a[contains(text(),'[" + subjectSuffix + "]')]").click();
            $x(".//table[@id='bisac-table']//td[text()='" + subjectCode + "']").click();

            boolean isIncluded = subject.getValue();
            if (isIncluded) {
                log.debug("Select include '" + subjectCode + "' BISAC subject code.");
                $("#includeBtn").click();
            } else {
                log.debug("Select exclude '" + subjectCode + "' BISAC subject code.");
                $("#excludeBtn").click();
            }
        }
    }

    /**
     * Method select available items (select and click add) in Panel with two filters, list of items and two buttons: add, remove.
     *
     * @param sectionExpandLinkElm - link (<a> tag) that expand/collapse section
     * @param sectionRootElm       - root element of the section
     * @param items                - item to choose
     */
    public void selectAvailableItemsInPanelFromList(SelenideElement sectionExpandLinkElm, SelenideElement sectionRootElm, Set<? extends ChannelAssociationItem> items) {
        if (!items.isEmpty()) {
            expandDropdownPanel(sectionExpandLinkElm);
            for (ChannelAssociationItem item : items) {
                Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
                log.info("Select '" + item.getText() + "' on panel.");
                sectionRootElm.$x(".//table[contains(@id,'available-')]//td[text()='" + item.getText() + "']").click();
            }
        }
    }

    public void setSendTriggerFile(boolean isSendTriggerFile) {
        log.info("Set send trigger file.");
        $("#sendTriggerFile").setSelected(isSendTriggerFile);
    }

    public void setAssociationServers(List<AssociationServer> servers) {
        for (AssociationServer srv : servers) {
            ServerSection serverSection = expandServerSection(srv).setSettings();

            for (ChannelAssociationProductType caProductType : srv.getCaProductTypes()) {
                ServerSection.ProductTypeSection productTypeSection = serverSection.select(caProductType);

                for (ChannelAssociationContent content : caProductType.getContents()) {
                    ServerSection.ProductTypeSection.ContentSection contentSection = productTypeSection.select(content);

                    if (content.isWatermarkThisAsset() || (content.getDestinationFolder() != null) || (content.getDistribute() != null)) {
                        ServerSection.ProductTypeSection.ModalWindow modalWindow = contentSection.openModalWindow();

                        modalWindow
                                .setDistribute(content.getDistribute(), content.getDaysBeforeOnSaleDate())
                                .setWatermark(content.isWatermarkThisAsset())
                                .setDestinationFolder(content.getDestinationFolder())
                                .setDistributeAudioContentInAZipNamed(content.isDistributeAudioContentInAZipNamed(), content.getZipName())
                                .clickOk();
                    }
                }

                for (ChannelAssociationCollateral collateral : caProductType.getCollaterals()) {
                    ServerSection.ProductTypeSection.CollateralSection contentSection = productTypeSection.select(collateral);

                    if (collateral.getDestinationFolder() != null) {
                        ServerSection.ProductTypeSection.ModalWindow modalWindow = contentSection.openModalWindow();
                        modalWindow.setDestinationFolder(collateral.getDestinationFolder()).clickOk();
                    }
                }

                for (ChannelAssociationMetadata metadata : caProductType.getMetadatas()) {
                    ServerSection.ProductTypeSection.MetadataSection metadataSection = productTypeSection.select(metadata);

                    if (metadata.getDestinationFolder() != null) {
                        ServerSection.ProductTypeSection.ModalWindow modalWindow = metadataSection.openModalWindow();
                        modalWindow.setDestinationFolder(metadata.getDestinationFolder()).clickOk();
                    }
                }
            }
        }
    }

    public void setDistributionNotification(List<String> recipientEmails) {
        log.info("Set distribution notification");
        String emails = String.join(",", recipientEmails);
        $("#association-notifications").val(emails);
    }

    public void setTakeDownNotification(List<String> recipientEmails) {
        log.info("Set take down notification");
        String emails = String.join(",", recipientEmails);
        $("#take-down-notifications").val(emails);
    }

    public void setBlackoutDates(List<LocalDate> blackoutDates) {
        log.info("Set blackout dates");
        blackoutDatesComponent.selectBlackoutDates(blackoutDates);
    }

    public void setComments(@Nullable String comment) {
        log.info("Set comments");
        if (comment != null) {
            $("#association-comments").val(comment);
        }
    }

    public void setContactInformation(@Nullable Address addr) {
        if (addr != null) {
            log.info("Adding Channel Contact information.");
            $("#association-first-name").val(addr.getFirstName());
            $("#association-last-name").val(addr.getLastName());
            $("#association-phone").val(addr.getPhoneNumber());
            $("#association-email").val(addr.getEmail());
            $("#association-title").val(addr.getContactTitle());
            $("#association-address1").val(addr.getAddressOne());
            if (addr.getAddressTwo() != null) $("#association-address2").val(addr.getAddressTwo());
            $("#association-city").val(addr.getCity());
            $("#association-state").val(addr.getState());
            $("#association-zip").val(addr.getPostalCode());
        }
    }

    public void selectPublicationDate(@NonNull LocalDate date) {
        String dateText = date.toString();
        log.info("Select Publication date: '" + dateText + "'.");
        $("#pubdate-picker").shouldBe(Condition.visible);
        executeJavaScript("$('#pubdate-picker').datepicker('setDate', new Date(arguments[0], arguments[1], arguments[2]));",
                date.getYear(),
                date.getMonthValue() - 1, // month count starts with 0, e.g. 0 - January, 1 - February and so on
                date.getDayOfMonth());
    }

    public void setAppleSpecificOptions(@NotNull DistributionRule dr) {
        $("#apple-body").shouldBe(visible);
        $("input#provider.drmfree").setSelected(dr.isDrmFree());
        $("input#provider.preorderpreviews").setSelected(dr.isPreOrderPreviews());
        $("input#provider.explicitcontent").setSelected(dr.isExplicitContent());
        $("input#provider.useimprintname").setSelected(dr.isUseImprintForPublisherName());
        $("input#provider.release").setSelected(dr.isBooksAreNewReleaseUntil());
        if (dr.getNewReleaseMonths() != 0) {
            executeJavaScript("$('input[type=\"range\"]').val(arguments[0]); " +
                    "$('input[type=\"range\"]').trigger('input');", dr.getNewReleaseMonths());
        }
    }

    public void expandDropdownPanel(@NotNull SelenideElement expandLinkElm) {
        expandLinkElm.shouldBe(visible);
        String expandedAttribute = expandLinkElm.getAttribute("aria-expanded");
        if (expandedAttribute == null || "false".equals(expandedAttribute)) {
            log.info("Expand dropdown '" + expandLinkElm.getText() + "'.");
            expandLinkElm.click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        } else {
            log.warn("Dropdown '" + expandLinkElm.getText() + "' has already expanded. Skip expanding.");
        }
    }

    public SelenideElement getHrvDisableWatermarkingWarningMsgElm() {
        return $x(".//*[@class='warning-message' and normalize-space(text())='Hrv has disabled asset watermarking until the Publisher ID field has been provided in the']");
    }

    public SelenideElement getHrvDisableWatermarkingWarningMsgBULinkElm() {
        return getHrvDisableWatermarkingWarningMsgElm().$x("./a[text()='Business Unit']");
    }

    public ServerSection expandServerSection(AssociationServer server) {
        return new ServerSection(server).expand();
    }


    public static class ServerSection {
        private final AssociationServer server;
        private final SelenideElement serverNameLink;
        protected final SelenideElement serverSectionRootElm;

        private ServerSection(AssociationServer server) {
            this.server = server;
            String serverName = server.getName();
            log.info("Go to association server: '" + serverName + "'.");
            this.serverNameLink = $x(".//a[@data-parent='#associateAccordion'" +
                    " and starts-with(normalize-space(text()),'" + serverName + "')]");

            this.serverSectionRootElm = this.serverNameLink.closest(".panel-server");
        }

        private ServerSection expand() {
            boolean isServerGroupExpanded = Boolean.getBoolean(this.serverNameLink.getAttribute("aria-expanded"));
            if (!isServerGroupExpanded) {
                log.info("Expand server section.");
                this.serverNameLink.click();
            }

            return this;
        }

        public ServerSection setSettings() {
            String username = this.server.getUsername();
            log.info("Set username= '" + username + "'");

            serverSectionRootElm.$("input[data-username]").val(username);
            String password = this.server.getPassword();
            log.info("Set password= '" + password + "'");
            serverSectionRootElm.$("input[data-password]").val(password);

            if ((this.server instanceof AssociationServerFTP) || (this.server instanceof AssociationServerSFTP) || (this.server instanceof AssociationServerFTPS)) {
                MetadataDistributionOption options = ((AssociationServerWithMetadataDistrOpt) this.server).getMetadataDistributionOption();
                if (options != null) {
                    log.info("Set Metadata Distribution Options.");
                    serverSectionRootElm
                            .$("input[data-group-onix]").setSelected(options.isGroupByPublisherAndImprint());
                    serverSectionRootElm
                            .$("input[data-onyxparasite]").setSelected(options.isSendMetadataWithEveryAsset());
                    serverSectionRootElm
                            .$("input[data-singleonyx]").setSelected(options.isSendSingleProductsInSingleOnixFile());
                }
            }

            if (this.server instanceof AssociationServerITMS) {
                serverSectionRootElm.$("input[data-provider]").val(((AssociationServerITMS) this.server).getProvider());
            }

            return this;
        }

        public ProductTypeSection select(ChannelAssociationProductType caProductType) {
            return new ProductTypeSection(caProductType).selectProductType();
        }

        public void selectProductTypes() {
            this.server.getCaProductTypes().forEach(this::select);
        }


        public class ProductTypeSection {
            private final ChannelAssociationProductType caProductType;
            private final SelenideElement productTypeSectionRootElm;
            private static final String LIST_ELEMENT_LOCATOR_TEMPLATE = ".//li[. and .//span[normalize-space(text())='%s']]";

            private ProductTypeSection(ChannelAssociationProductType caProductType) {
                this.caProductType = caProductType;
                this.productTypeSectionRootElm = serverSectionRootElm.$("[data-ptype-id='" + caProductType.getProductType().getId() + "']");
            }

            public ProductTypeSection selectProductType() {
                ProductType productType = this.caProductType.getProductType();
                log.info("Select product type: '" + productType + "'");

                SelenideElement selectBtn = serverSectionRootElm.$x(".//span[text()='Select']");
                selectBtn.scrollIntoView(HORIZONTAL_SCROLL_INTO_VIEW_OPTIONS);
                Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
                selectBtn.click();

                serverSectionRootElm.$("li[data-product-id='" + productType.getId() + "']").click();
                Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

                return this;
            }

            public ContentSection select(ChannelAssociationContent caContent) {
                return new ContentSection(caContent).select();
            }

            public CollateralSection select(ChannelAssociationCollateral caCollateral) {
                return new CollateralSection(caCollateral).select();
            }

            public MetadataSection select(ChannelAssociationMetadata caMetadata) {
                return new MetadataSection(caMetadata).select();
            }

            public void selectContentTypes() {
                this.caProductType.getContents().forEach(this::select);
            }


            public class ContentSection {
                private final ChannelAssociationContent caContent;
                private final SelenideElement contentTypeSectionRootElm;

                private ContentSection(ChannelAssociationContent caContent) {
                    this.caContent = caContent;
                    this.contentTypeSectionRootElm = productTypeSectionRootElm
                            .$x(String.format(LIST_ELEMENT_LOCATOR_TEMPLATE, this.caContent.getContentType().getText()));
                }

                public ContentSection select() {
                    log.info("Select content (content type='" + this.caContent.getContentType().getText() + "').");
                    contentTypeSectionRootElm.scrollIntoView(HORIZONTAL_SCROLL_INTO_VIEW_OPTIONS).click();

                    return this;
                }

                public ModalWindow openModalWindow() {
                    return new ModalWindow().open(contentTypeSectionRootElm);
                }
            }


            public class CollateralSection {
                private final ChannelAssociationCollateral caCollateral;
                private final SelenideElement collateralTypeSectionRootElm;

                private CollateralSection(ChannelAssociationCollateral caCollateral) {
                    this.caCollateral = caCollateral;
                    this.collateralTypeSectionRootElm = productTypeSectionRootElm
                            .$x(String.format(LIST_ELEMENT_LOCATOR_TEMPLATE, this.caCollateral.getCollateralType().getText()));
                }

                public CollateralSection select() {
                    log.info("Select collateral (collateral type='" + this.caCollateral.getCollateralType().getText() + "').");
                    Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
                    collateralTypeSectionRootElm.scrollIntoView(false).click();

                    return this;
                }

                public ModalWindow openModalWindow() {
                    return new ModalWindow().open(collateralTypeSectionRootElm);
                }
            }


            public class MetadataSection {
                private final ChannelAssociationMetadata caMetadata;
                private final SelenideElement metadataTypeSectionRootElm;

                private MetadataSection(ChannelAssociationMetadata caMetadata) {
                    this.caMetadata = caMetadata;
                    this.metadataTypeSectionRootElm = productTypeSectionRootElm
                            .$x(String.format(LIST_ELEMENT_LOCATOR_TEMPLATE, this.caMetadata.getMetadataType().getText()));
                }

                public MetadataSection select() {
                    log.info("Select metadata (metadata type='" + this.caMetadata.getMetadataType().getText() + "').");
                    metadataTypeSectionRootElm.scrollIntoView(false).click();

                    return this;
                }

                public ModalWindow openModalWindow() {
                    return new ModalWindow().open(metadataTypeSectionRootElm);
                }
            }


            public class ModalWindow {
                private final SelenideElement modalWinRootElm = $("#product-modal");

                private ModalWindow() {
                    //None
                }

                public ModalWindow open(SelenideElement sectionRootElm) {
                    log.info("Click open modal window.");
                    sectionRootElm.$(".modal-toggle").scrollIntoView(false).click();

                    modalWinRootElm.shouldBe(visible);
                    Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

                    return this;
                }

                public ModalWindow setDistribute(Distribute distribute, int daysBeforeOnSaleDate) {
                    if (distribute != null) {
                        log.debug("Select distribute this: '" + distribute + "'");
                        modalWinRootElm.$(".slider-controls").$x(".//*[text()='" + distribute + "']").click();

                        int days = daysBeforeOnSaleDate;
                        if (distribute.equals(Distribute.BEFORE)) {
                            days = -days;
                        }
                        log.debug("Set number of days='" + days + "'");
                        executeJavaScript("$('input[type=\"range\"]').val(arguments[0]); " +
                                "$('input[type=\"range\"]').trigger('input');", days);
                    }

                    return this;
                }

                public ModalWindow setWatermark(boolean isWatermarkThisAsset) {
                    if (isWatermarkThisAsset && !getWatermarkCheckboxElm().isSelected()) {
                        log.debug("Set watermark='" + isWatermarkThisAsset + "'");
                        getWatermarkCheckboxElm().setSelected(isWatermarkThisAsset);
                    }

                    return this;
                }

                public ModalWindow setDestinationFolder(@Nullable String destinationFolder) {
                    if (destinationFolder != null) {
                        log.debug("Set destination folder='" + destinationFolder + "'");
                        modalWinRootElm.$(".destination input").val(destinationFolder);
                    }

                    return this;
                }

                public ModalWindow setDistributeAudioContentInAZipNamed(boolean distributeAudioContentInAZipNamed, String zipName) {
                    if (distributeAudioContentInAZipNamed && (zipName != null)) {
                        log.debug("Set distribute audio content in a zip named='" + distributeAudioContentInAZipNamed + "'");
                        modalWinRootElm.$(".zip-audio input[type='checkbox']").setSelected(distributeAudioContentInAZipNamed);

                        log.debug("Set zip name='" + zipName + "'");
                        modalWinRootElm.$(".zip-audio input[type='text']").val(zipName).pressTab();
                    }

                    return this;
                }

                public SelenideElement getWatermarkCheckboxElm() {
                    return modalWinRootElm.$(".enable-watermarking input[type='checkbox']");
                }

                public void clickOk() {
                    log.debug("Click OK button.");
                    modalWinRootElm.$("#okBtn").click();
                }
            }
        }
    }
}