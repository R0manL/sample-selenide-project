package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.ccc.hv.qa.ui.pages.ProductsViewPage.getProductsViewPage;
import static com.codeborne.selenide.Condition.visible;

/**
 * Created by R0manL on 7/12/22.
 */

public class IngestAlertBox extends PageBase {
    private static final SelenideElement ROOT_ELM = activePageContainer.$(".alertboxes");
    private static final SelenideElement TOTAL_PRODUCTS_LINK = ROOT_ELM.$("#total a#plRedAlert");
    private static final SelenideElement NEW_PRODUCTS_TEXT = ROOT_ELM.$("#new");
    private static final SelenideElement NEW_PRODUCTS_LINK = NEW_PRODUCTS_TEXT.$("a#plRedAlert");
    private static final SelenideElement UPDATED_PRODUCTS_LINK = ROOT_ELM.$("#updated a#plRedAlert");
    private static final SelenideElement FAILED_PRODUCTS_COUNT_ELM = ROOT_ELM.$("#onix2FailedCount");


    private IngestAlertBox() {
        // NONE
    }

    public static IngestAlertBox getIngestAlertBox() {
        ROOT_ELM.shouldBe(visible);
        return new IngestAlertBox();
    }

    public ProductsViewPage clickTotalLink() {
        TOTAL_PRODUCTS_LINK.click();

        return getProductsViewPage();
    }

    public ProductsViewPage clickNewLink() {
        NEW_PRODUCTS_LINK.click();

        return getProductsViewPage();
    }

    public ProductsViewPage clickUpdatedLink() {
        UPDATED_PRODUCTS_LINK.click();

        return getProductsViewPage();
    }

    public SelenideElement getNewProductsElm() {
        return NEW_PRODUCTS_TEXT;
    }

    public SelenideElement getNumberOfFailedProductsElm() {
        return FAILED_PRODUCTS_COUNT_ELM;
    }

    public int getNumberOfFailedProducts() {
        final int NO_FAILURES_COUNT = 0;

        if(getNumberOfFailedProductsElm().isDisplayed()) {
            return Integer.parseInt(getNumberOfFailedProductsElm().shouldBe(visible).getText().trim());
        }

        return NO_FAILURES_COUNT;
    }
}
