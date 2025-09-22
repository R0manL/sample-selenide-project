package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.ui.pages.ProductDetailsPage.getProductDetailsPage;

public class DistributionSuccessPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private DistributionSuccessPage() {
        // None
    }

    public static DistributionSuccessPage getDistributionSuccessPage() {
        return new DistributionSuccessPage();
    }

    public SelenideElement distributionSuccessMsgElm(){
        return $(".alerts.success");
    }

    public ProductDetailsPage clickReturn(){
        log.info("Return to product details page");
        $("#return").click();

        return getProductDetailsPage();

    }


}
