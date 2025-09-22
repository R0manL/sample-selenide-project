package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.$;

public class AnalyticsPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private AnalyticsPage() {
        // none
    }

    public static AnalyticsPage getAnalyticsPage() {
        return new AnalyticsPage();
    }

    public SelenideElement getBuyBoxAnalyticsTitle() {
        log.info("Get Buy Box Analytics title element");
        return $(".title");
    }
}
