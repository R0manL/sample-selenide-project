package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.ui.pages.BusinessUnitEditPage.getBusinessUnitEditPage;
import static com.ccc.hv.qa.ui.pages.BusinessUnitsManagePage.getBusinessUnitsManagePage;


/**
 * Created by R0manL.
 */

public class BusinessUnitViewPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private BusinessUnitViewPage() {
        // None
    }

    public static BusinessUnitViewPage getBusinessUnitViewPage() {
        return new BusinessUnitViewPage();
    }

    public SelenideElement getPageTitleElm() {
        return $x(".//h1[@id='page-title' and text()='View Business Unit']");
    }

    public BusinessUnitEditPage clickEdit() {
        log.info("Click edit.");
        activePageContainer.$("a#business-unit-edit-action").click();

        return getBusinessUnitEditPage();
    }

    public SelenideElement getWatermarkingSectionElm(){
        return $("div.watermarking");
    }

    public SelenideElement getPublisherIdFieldElm(){
        return $x(".//div[@class='form-group']/child::label[normalize-space(text())='Publisher ID']");
    }

    public SelenideElement getPublisherPinElm(){
        return $x(".//div[@class='form-group']/child::label[normalize-space(text())='Publisher PIN']");
    }

    public SelenideElement getImageThresholdFieldElm(){
        return $x(".//div[@class='form-group']/child::label[normalize-space(text())='Image Threshold']");
    }

    public SelenideElement getBUNameElm(){
        return $("div#bsunit-name-wrapper");
    }

    public BusinessUnitsManagePage clickReturnToAllBusinessUnits() {
        $x(".//a[normalize-space(text())='Return to All Business Units']").click();

        return getBusinessUnitsManagePage();
    }
}
