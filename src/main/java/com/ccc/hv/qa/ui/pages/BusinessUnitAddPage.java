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

import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.TenantDBService.isRecordSourceNameExist;
import static com.ccc.hv.qa.ui.enums.IngestMode.NORMAL;
import static com.ccc.hv.qa.ui.enums.IngestMode.PREVIOUSLY_DISTRIBUTED;
import static com.ccc.hv.qa.ui.pages.BusinessUnitsManagePage.getBusinessUnitsManagePage;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueAlphaCode;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.empty;

/**
 * Created by R0manL on 11/08/20.
 */

public class BusinessUnitAddPage extends PageBase {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private BusinessUnitAddPage() {
        // None
    }

    public static BusinessUnitAddPage getBusinessUnitAddPage() {
        return new BusinessUnitAddPage();
    }

    /**
     * Method create Business Unit based on {bu} bean.
     * Requirements:
     * * AlphaCode - must be unique across all BUs.
     * * Record source name - must be unique across all BUs. Currently UI does not alert about this issue.
     * @param bu - bean that describes BU's data.
     * @return - same BU page.
     */
    public BusinessUnitAddPage createBusinessUnit(BusinessUnit bu) {
        String buName = bu.getName();
        log.info("Creating '" + buName + "' business unit.");
        setBusinessUnitProfileInformation(bu);
        clickContinueBtn();
        setBusinessUnitBillingContactInformation(bu);
        clickSaveBtn();
        log.info("'" + buName + "' business unit has successfully created.");

        return this;
    }

    public SelenideElement getBUCreatedSuccessMessageElm() {
        return $x("//*[@id='page-title' and text()='New Business Unit Added']/..//*[@id='bsunitName']");
    }

    public BusinessUnitAddPage setBusinessUnitProfileInformation(BusinessUnit bu) {
        String buName = bu.getName();
        log.info("Set profile information for business unit '" + buName + "'.");
        activePageContainer.$("#bsunit-name").val(buName);

        if (isRecordSourceNameExist(bu.getRecordSourceName())) {
            throw new IllegalArgumentException("Business unit's 'record source name' must be unique." +
                    " '" + bu.getRecordSourceName() + "' has already exist in DB.");
        }
        activePageContainer.$("#bsunit-record-source").val(bu.getRecordSourceName());

        String alphacode = bu.getAlphaCode();
        if (alphacode == null) { alphacode = generateUniqueAlphaCode(); }
        activePageContainer.$("#bsunit-code").val(alphacode);

        if (bu.isUseExpectedShipDate()) {
            activePageContainer.$("#yesShipDate").click();
        } else {
            activePageContainer.$("#noShipDate").click();
        }

        IngestMode ingestMode = bu.getIngestMode();
        if (PREVIOUSLY_DISTRIBUTED.equals(ingestMode)) { activePageContainer.$("#yesOnboarding").click(); }
        if (NORMAL.equals(ingestMode)) { activePageContainer.$("#noOnboarding").click(); }

        if (bu.getDistributionErrorNotificationEmail() != null) {
            activePageContainer.$("#bsunit-email").val(bu.getDistributionErrorNotificationEmail());
        }

        if (bu.isUseParentAccountInfo()) {
            log.info("Business unit contact information will be used from parent account.");
            $x(".//*[@for='infoCheckbox']").scrollIntoView(false);
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
            $x(".//*[@for='infoCheckbox']").click();
            activePageContainer.$("#bsunit-physical-first-name").shouldNotBe(empty);
        } else {
            log.info("Set business unit contact information.");
            Address contAddr = bu.getProfileContactInfo();
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

        log.info("Set business unit file intake information.");
        activePageContainer.$("#bsunit-uploadUsername").val(bu.getUserId());
        activePageContainer.$("#bsunit-uploadPassword").val(bu.getPassword());

        log.info("Set business unit watermarking information.");
        if (bu.getPublisherId() != null) {
            getPublisherIdElm().val(bu.getPublisherId());
        }
        if (bu.getPublisherPin() != null) {
            activePageContainer.$(By.name("businessUnit.digimarcPublisherPin")).val(bu.getPublisherPin());
        }
        if (bu.getImageThreshold() != 0) {
            executeJavaScript("$('[name=\"businessUnit.digimarcImageThreshold\"]').val(arguments[0]);", bu.getImageThreshold());
        }

        log.info("Profile information has been set.");
        return this;
    }

    public BusinessUnitsManagePage clickCancel() {
        $$(".form-cancel").filter(visible).first().click();

        return getBusinessUnitsManagePage();
    }

    public BusinessUnitAddPage setBusinessUnitBillingContactInformation(BusinessUnit bsUnit) {
        log.info("Set business unit billing contact information.");
        Address blAddr = bsUnit.getBillingContactInfo();
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

    public BusinessUnitAddPage clickContinueBtn() {
        log.info("Click continue business unit creation.");
        $x("//*[@id='bsunitdata']//*[normalize-space(text())='Continue']").click();

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

    public void clickSaveBtn() {
        log.info("Save business unit.");
        $x("//*[@id='bsunitbilling']//*[normalize-space(text())='Save']").click();
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
