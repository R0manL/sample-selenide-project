package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.IngestMode;
import com.ccc.hv.qa.ui.pojos.Address;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.db.services.TenantDBService.isRecordSourceNameExist;
import static com.ccc.hv.qa.ui.enums.IngestMode.NORMAL;
import static com.ccc.hv.qa.ui.enums.IngestMode.PREVIOUSLY_DISTRIBUTED;
import static com.ccc.hv.qa.ui.pages.BusinessUnitViewPage.getBusinessUnitViewPage;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueAlphaCode;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;


/**
 * Created by R0manL.
 */

public class BusinessUnitEditPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private BusinessUnitEditPage() {
        // None
    }

    public static BusinessUnitEditPage getBusinessUnitEditPage() {
        return new BusinessUnitEditPage();
    }

    public SelenideElement getPageTitle() {
        return $x(".//h1[text()='Edit Business Unit']");
    }

    public BusinessUnitEditPage updateBusinessUnitWith(BusinessUnit newBU) {
        String buName = newBU.getName();
        log.info("Update '" + buName + "' business unit.");
        updateBusinessUnitProfileInformation(newBU);
        clickContinueBtn();
        updateBusinessUnitBillingContactInformationWith(newBU);
        clickSaveBtn();
        log.info("'" + buName + "' business unit has successfully created.");

        return this;
    }

    public BusinessUnitEditPage updateBusinessUnitProfileInformation(BusinessUnit newBU) {
        String buName = newBU.getName();
        log.info("Update profile information for business unit: '" + buName + "'.");
        updateBusinessUnitName(buName);

        if (isRecordSourceNameExist(newBU.getRecordSourceName())) {
            throw new IllegalArgumentException("Business unit's 'record source name' must be unique." +
                    " '" + newBU.getRecordSourceName() + "' has already exist in DB.");
        }
        activePageContainer.$("#bsunit-record-source").val(newBU.getRecordSourceName());

        String alphacode = newBU.getAlphaCode();
        if (alphacode == null) { alphacode = generateUniqueAlphaCode(); }
        activePageContainer.$("#bsunit-code").val(alphacode);

        IngestMode ingestMode = newBU.getIngestMode();
        if (PREVIOUSLY_DISTRIBUTED.equals(ingestMode)) { activePageContainer.$("#yesOnboarding").click(); }
        if (NORMAL.equals(ingestMode)) { activePageContainer.$("#noOnboarding").click(); }

        if (newBU.getDistributionErrorNotificationEmail() != null) {
            activePageContainer.$("#bsunit-email").val(newBU.getDistributionErrorNotificationEmail());
        }

        if (newBU.isUseParentAccountInfo()) {
            log.info("Business unit contact information will be used from parent account.");
            $x(".//*[@for='infoCheckbox']").click();
            activePageContainer.$("#bsunit-physical-first-name").shouldNotBe(empty);
        } else {
            log.info("Update business unit contact information.");
            Address contAddr = newBU.getProfileContactInfo();
            activePageContainer.$("#bsunit-physical-first-name").val(contAddr.getFirstName());
            activePageContainer.$("#bsunit-physical-last-name").val(contAddr.getLastName());
            activePageContainer.$("#bsunit-physical-title").val(contAddr.getContactTitle());
            activePageContainer.$("#bsunit-physical-phone").val(contAddr.getPhoneNumber());
            activePageContainer.$("#bsunit-physical-email").val(contAddr.getEmail());
            activePageContainer.$("#bsunit-physical-address1").val(contAddr.getAddressOne());
            if (contAddr.getAddressTwo() != null) {
                activePageContainer.$("#bsunit-physical-address2").val(contAddr.getAddressTwo());
            }
            activePageContainer.$("#bsunit-physical-city").val(contAddr.getCity());
            activePageContainer.$("#bsunit-physical-state").val(contAddr.getState());
            activePageContainer.$("#bsunit-physical-zip").val(contAddr.getPostalCode());
            activePageContainer.$("#bsunit-physical-country").val(contAddr.getCountry());
        }

        log.info("Update business unit file intake information.");
        activePageContainer.$("#bsunit-uploadUsername").val(newBU.getUserId());
        activePageContainer.$("#bsunit-uploadPassword").val(newBU.getPassword());

        log.info("Update business unit watermarking information.");
        if (newBU.getPublisherId() != null) {
            getPublisherIdElm().val(newBU.getPublisherId());
        }
        if (newBU.getPublisherPin() != null) {
            activePageContainer.$(By.name("businessUnit.digimarcPublisherPin")).val(newBU.getPublisherPin());
        }
        if (newBU.getImageThreshold() != 0) {
            executeJavaScript("$('[name=\"businessUnit.digimarcImageThreshold\"]').val(arguments[0]);", newBU.getImageThreshold());
        }

        log.info("Profile information has been updated.");

        return this;
    }

    public BusinessUnitEditPage updateBusinessUnitName(@NotNull String newName) {
        activePageContainer.$("#bsunit-name").val(newName);

        return this;
    }

    public BusinessUnitViewPage clickCancel() {
        $$(".form-cancel").filter(visible).first().click();

        return getBusinessUnitViewPage();
    }

    public BusinessUnitEditPage updateBusinessUnitBillingContactInformationWith(BusinessUnit newBU) {
        log.info("Update business unit billing contact information.");
        Address blAddr = newBU.getBillingContactInfo();
        activePageContainer.$("#bsunit-billing-first-name").val(blAddr.getFirstName());
        activePageContainer.$("#bsunit-billing-last-name").val(blAddr.getLastName());
        activePageContainer.$("#bsunit-billing-title").val(blAddr.getContactTitle());
        activePageContainer.$("#bsunit-billing-phone").val(blAddr.getPhoneNumber());
        activePageContainer.$("#bsunit-billing-email").val(blAddr.getEmail());
        activePageContainer.$("#bsunit-billing-address1").val(blAddr.getAddressOne());
        if (blAddr.getAddressTwo() != null) {
            activePageContainer.$("#bsunit-billing-address2").val(blAddr.getAddressTwo());
        }
        activePageContainer.$("#bsunit-billing-city").val(blAddr.getCity());
        activePageContainer.$("#bsunit-billing-state").val(blAddr.getState());
        activePageContainer.$("#bsunit-billing-zip").val(blAddr.getPostalCode());
        activePageContainer.$("#bsunit-billing-country").val(blAddr.getCountry());

        return this;
    }

    public BusinessUnitEditPage clickContinueBtn() {
        log.info("Click continue business unit updating.");
        $x("//*[@id='bsunitdata']//*[normalize-space(text())='Continue']").click();
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

        return this;
    }

    public SelenideElement getPublisherIdElm() {
        return activePageContainer.$(By.name("businessUnit.digimarcPublisherId"));
    }

    public SelenideElement getPublisherPINElm() {
        return activePageContainer.$(By.name("businessUnit.digimarcPublisherPin"));
    }

    public SelenideElement getImageThresholdElm() {
        return activePageContainer.$("[name='businessUnit.digimarcImageThreshold']");
    }

    public BusinessUnitEditPage clickSaveBtn() {
        log.info("Save business unit.");
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        $x("//*[@id='bsunitbilling']//*[normalize-space(text())='Save']").click();

        return this;
    }

    public SelenideElement getContinueBtn(){
        return $x("//*[@id='bsunitdata']//*[normalize-space(text())='Continue']");
    }

    public SelenideElement getBUUpdatedSuccessMessageElm() {
        return $x("//*[@id='page-title' and text()='Business Unit Updated']/..//*[@id='bsunitName']");
    }

    public BusinessUnitEditPage setPublisherID(@NotNull String publisherID) {
        getPublisherIdElm().val(publisherID);
        return this;
    }

    public BusinessUnitEditPage setPublisherPin(@NotNull String publisherPin) {
        getPublisherPINElm().val(publisherPin);
        return this;
    }

    public BusinessUnitEditPage setImageThreshold(@NotNull String imageThreshold) {
        executeJavaScript("$('[name=\"businessUnit.digimarcImageThreshold\"]').val(arguments[0]);", imageThreshold);
        return this;
    }

    @NotNull
    public SelenideElement getBuNameErrorElm() {
        return $("#bsunit-name-help");
    }

    @NotNull
    public SelenideElement getRecordSourceErrorElm() {
        return $("#bsunit-record-source-help");
    }

    @NotNull
    public SelenideElement getAlphaCodeErrorElm() {
        return $("#bsunit-code-help");
    }

    @NotNull
    public SelenideElement getPhysicalFirstNameErrorElm() {
        return $("#bsunit-physical-first-name-help");
    }

    @NotNull
    public SelenideElement getPhysicalLastNameErrorElm() {
        return $("#bsunit-physical-last-name-help");
    }

    @NotNull
    public SelenideElement getPhysicalContactTitleErrorElm() {
        return $("#bsunit-physical-title-help");
    }

    @NotNull
    public SelenideElement getPhysicalPhoneErrorElm() {
        return $("#bsunit-physical-phone-help");
    }

    @NotNull
    public SelenideElement getPhysicalEmailErrorElm() {
        return $("#bsunit-physical-email-help");
    }

    @NotNull
    public SelenideElement getPhysicalAddr1ErrorElm() {
        return $("#bsunit-physical-address1-help");
    }

    @NotNull
    public SelenideElement getPhysicalCityErrorElm() {
        return $("#bsunit-physical-city-help");
    }

    @NotNull
    public SelenideElement getPhysicalStateErrorElm() {
        return $("#bsunit-physical-state-help");
    }

    @NotNull
    public SelenideElement getPhysicalZipErrorElm() {
        return $("#bsunit-physical-zip-help");
    }

    @NotNull
    public SelenideElement getPhysicalCountryErrorElm() {
        return $("#bsunit-physical-country-help");
    }

    @NotNull
    public SelenideElement getUserIdErrorElm() {
        return $("#bsunit-uploadUsername-help");
    }

    @NotNull
    public SelenideElement getPasswordErrorElm() {
        return $("#bsunit-uploadPassword-help");
    }

    @NotNull
    public SelenideElement getBillingFirstNameErrorElm() {
        return $("#bsunit-billing-first-name-help");
    }

    @NotNull
    public SelenideElement  getBillingLastNameErrorElm() {
        return $("#bsunit-billing-last-name-help");
    }

    @NotNull
    public SelenideElement  getBillingTitleErrorElm() {
        return $("#bsunit-billing-title-help");
    }

    @NotNull
    public SelenideElement  getBillingPhoneErrorElm() {
        return $("#bsunit-billing-phone-help");
    }

    @NotNull
    public SelenideElement  getBillingEmailErrorElm() {
        return $("#bsunit-billing-email-help");
    }

    @NotNull
    public SelenideElement  getBillingAddress1ErrorElm() {
        return $("#bsunit-billing-address1-help");
    }

    @NotNull
    public SelenideElement  getBillingCityErrorElm() {
        return $("#bsunit-billing-city-help");
    }

    @NotNull
    public SelenideElement  getBillingStateErrorElm() {
        return $("#bsunit-billing-state-help");
    }

    @NotNull
    public SelenideElement  getBillingZipErrorElm() {
        return $("#bsunit-billing-zip-help");
    }

    @NotNull
    public SelenideElement  getBillingCountryErrorElm() {
        return $("#bsunit-billing-country-help");
    }
}
