package com.ccc.hv.qa.ui.pages;

import com.ccc.hv.qa.ui.enums.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pojos.Address;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAIDBy;
import static com.ccc.hv.qa.ui.pages.BusinessUnitEditPage.getBusinessUnitEditPage;
import static com.ccc.hv.qa.ui.pages.ChannelAssociationEditPage.getChannelAssociationEditPage;
import static com.ccc.hv.qa.utils.enums.DatePattern.UI_BLACKOUT;
import static com.ccc.hv.qa.utils.DateTimeUtils.convertToLocalDate;

/**
 * Created by R0manL on 5/12/21.
 */

public class ChannelAssociationViewPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final SelenideElement PUB_DATE_TEXT_ELM = $x(".//*[text()='Distribute assets that have a publication date']");
    private static final String SERVER_ASSOCIATION_SECTION_LOCATOR = ".//*[@id='associateAccordion' and .//a[normalize-space(text())='%s']]";


    private ChannelAssociationViewPage() {
        // None
    }

    public static ChannelAssociationViewPage getChannelAssociationViewPage() {
        return new ChannelAssociationViewPage();
    }

    public SelenideElement getPageTitleElm() {
        return $x(".//h1[@id='page-title' and contains(.,'View')]");
    }

    public ChannelAssociationEditPage clickEditChannelAssociation() {
        log.info("Click on 'edit channel association' button.");
        $("#channel-association-edit-action").click();

        return getChannelAssociationEditPage();
    }

    public boolean isAutomatedDistributionRulesSelected() {
        log.info("Getting if automated distribution rules selected.");
        return isOptionSelected("Use Automated Distribution Rules");
    }

    public boolean isSendTriggerFileSelected() {
        log.info("Getting if automated distribution rules selected.");
        return isOptionSelected("Send Trigger File");
    }

    public Map<BisacSubjectCode, Boolean> getSelectedBisacSubjectCodes() {
        log.info("Getting selected bisac subject codes.");

        SelenideElement subjectSectionElm = $("#subjectSelector");
        subjectSectionElm.shouldBe(Condition.visible);
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 6);

        Map<BisacSubjectCode, Boolean> result = new EnumMap<>(BisacSubjectCode.class);

        subjectSectionElm.$$x(".//*[@id='inclusions-table']//td[3]/../td[1]")
                .forEach(elm -> result.put(BisacSubjectCode.fromText(elm.innerText()), true));

        subjectSectionElm.$$x(".//*[@id='exclusions-table']//td[3]/../td[1]")
                .forEach(elm -> result.put(BisacSubjectCode.fromText(elm.innerText()), false));

        return result;
    }

    public boolean isPublicationDateFilterActive() {
        return PUB_DATE_TEXT_ELM.isDisplayed();
    }

    public boolean isPublicationDateFilterBefore() {
        if (isPublicationDateFilterActive()) {
            if ("before".equals(PUB_DATE_TEXT_ELM.sibling(0).text())) {
                return true;
            }
            if ("after".equals(PUB_DATE_TEXT_ELM.sibling(0).text())) {
                return false;
            }
            throw new IllegalStateException("Can't identify if publication date is set as 'before' or 'after'.");
        }

        return false;
    }

    @NotNull
    public LocalDate getPublicationDateValue() {
        LocalDate result = null;

        if (isPublicationDateFilterActive()) {
            @NotNull String pubDateValue = PUB_DATE_TEXT_ELM.sibling(1).text();
            result = convertToLocalDate(UI_BLACKOUT, pubDateValue);
        }
        Objects.requireNonNull(result, "Can't get publication date.");

        return result;
    }

    public List<ProductType> getSelectedProductTypesFor(@NotNull String srvAssociationName) {
        log.info("Getting selected product types.");

        return $x(String.format(SERVER_ASSOCIATION_SECTION_LOCATOR, srvAssociationName))
                .$$(".product-types li")
                .stream()
                .map(productTypeElm -> ProductType.fromText(productTypeElm.innerText().trim()))
                .collect(Collectors.toList());
    }

    public ProductType getSelectedProductTypeFor(@NotNull String srvAssociationName) {
        return getSingleElmFrom(getSelectedProductTypesFor(srvAssociationName));
    }

    public List<ContentType> getSelectedContentTypesFor(@NotNull String srvAssociationName) {
        log.info("Getting selected content types.");

        return $x(String.format(SERVER_ASSOCIATION_SECTION_LOCATOR, srvAssociationName))
                .$$("[data-asset-type='content'] ul li")
                .stream()
                .map(contentTypeElm -> ContentType.fromText(contentTypeElm.getOwnText().trim()))
                .collect(Collectors.toList());
    }

    public ContentType getSelectedContentTypeFor(@NotNull String srvAssociationName) {
        return getSingleElmFrom(getSelectedContentTypesFor(srvAssociationName));
    }

    public List<CollateralType> getSelectedCollateralTypesFor(@NotNull String srvAssociationName) {
        log.info("Getting selected collateral types.");

        return $x(String.format(SERVER_ASSOCIATION_SECTION_LOCATOR, srvAssociationName))
                .$$("[data-asset-type='collateral'] ul li")
                .stream()
                .map(collateralTypeElm -> CollateralType.fromText(collateralTypeElm.getOwnText().trim()))
                .collect(Collectors.toList());
    }

    public CollateralType getSelectedCollateralTypeFor(@NotNull String srvAssociationName) {
        return getSingleElmFrom(getSelectedCollateralTypesFor(srvAssociationName));
    }

    public List<MetadataType> getSelectedMetadataTypesFor(@NotNull String srvAssociationName) {
        log.info("Getting selected metadata types.");

        return $x(String.format(SERVER_ASSOCIATION_SECTION_LOCATOR, srvAssociationName))
                .$$("[data-asset-type='metadata'] ul li")
                .stream()
                .map(metadataTypeElm -> MetadataType.fromText(metadataTypeElm.getOwnText().trim()))
                .collect(Collectors.toList());
    }

    public MetadataType getSelectedMetadataTypeFor(@NotNull String srvAssociationName) {
        return getSingleElmFrom(getSelectedMetadataTypesFor(srvAssociationName));
    }

    public SelenideElement getHrvDisableWatermarkingWarningMsgElm() {
        return $x(".//*[@class='warning-message' and normalize-space(text())='Hrv has disabled asset watermarking until the Publisher ID field has been provided in the']");
    }

    public SelenideElement getHrvDisableWatermarkingWarningMsgBULinkElm() {
        return getHrvDisableWatermarkingWarningMsgElm().$x("./a[text()='Business Unit']");
    }

    public Address getAddress() {
        return Address.builder()
                .firstName(getValueElm(DropDown.FIRST_NAME).getText())
                .lastName(getValueElm(DropDown.LAST_NAME).getText())
                .phoneNumber(getValueElm(DropDown.PHONE_NUMBER).getText())
                .email(getValueElm(DropDown.EMAIL_ADDRESS).getText())
                .contactTitle(getValueElm(DropDown.TITLE).getText())
                .addressOne(getValueElm(DropDown.ADDRESS1).getText())
                .addressTwo(getValueElm(DropDown.ADDRESS2).getText())
                .city(getValueElm(DropDown.CITY).getText())
                .state(getValueElm(DropDown.STATE).getText())
                .postalCode(getValueElm(DropDown.ZIP).getText())
                .build();
    }

    public SelenideElement getChannelAssociationIDElm(@NotNull String channelName) {
        int caID = getCAIDBy(channelName);
        return $x(".//h1[@id='page-title' and ./*[normalize-space(text())='ID - " + caID + "']]");
    }

    public SelenideElement getPublisherPinIsNotProvidedWarningMsgElm() {
        return $(".warning-message");
    }

    public BusinessUnitEditPage navigateToTheBUFromWarningMsg() {
        getPublisherPinIsNotProvidedWarningMsgElm().$("a").click();

        return getBusinessUnitEditPage();
    }

    public SelenideElement getValueElm(ChannelAssociationViewPage.DropDown dropDown) {
        return $x(".//label[@label-default='label-default' " +
                "and normalize-space(text())='" + dropDown.getId() + "']/following-sibling::div");
    }

    private boolean isOptionSelected(@NotNull String optionTitle) {
        SelenideElement rowElm = $x(".//*[@class='column-data' and .//label[normalize-space(text())='" + optionTitle + "']]");
        rowElm.shouldBe(Condition.visible);
        if (rowElm.$x(".//*[text()='yes']").isDisplayed()) {
            return true;
        }
        if (rowElm.$x(".//*[text()='no']").isDisplayed()) {
            return false;
        }

        throw new IllegalStateException("Can't identify '" + optionTitle + "' state.");
    }

    @NotNull
    private <T> T getSingleElmFrom(List<T> list) {
        if (list.size() == 1) {
            return list.get(0);
        }

        throw new IllegalArgumentException("Expect single value in the list, but '" + list.size() + "' found.");
    }


    public enum DropDown {
        FIRST_NAME("First Name"),
        LAST_NAME("Last Name"),
        PHONE_NUMBER("Phone Number"),
        EMAIL_ADDRESS("Email Address"),
        TITLE("Title"),
        ADDRESS1("Address 1"),
        ADDRESS2("Address 2"),
        CITY("City"),
        STATE("State / Province / Region"),
        ZIP("Zip / Postal");


        private final String id;

        DropDown(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        @Override
        public String toString() {
            return name();
        }
    }
}
