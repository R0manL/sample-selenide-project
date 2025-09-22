package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Created by R0manL on 11/17/20.
 */

public class PublishersHubPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final SelenideElement hrvTileElm = $(".item.hrv a");


    private PublishersHubPage() {
        // None
    }

    public static PublishersHubPage getPublishersHubPage() {
        log.debug("Switch to parent iframe.");
        switchTo().parentFrame();
        return new PublishersHubPage();
    }

    public void openHrv() {
        log.info("Open 'Hrv'.");
        hrvTileElm.click();
    }

    public static void openHrvIfPublisherHubIsDisplayed() {
      if (hrvTileElm.isDisplayed()) {
          getPublishersHubPage().openHrv();
      }
    }
}
