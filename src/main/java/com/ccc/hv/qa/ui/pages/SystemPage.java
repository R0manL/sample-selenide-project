package com.ccc.hv.qa.ui.pages;

import com.ccc.hv.qa.logging.AllureLogger;
import io.qameta.allure.Step;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.ui.pages.OriginalNormalizationFilesPage.getOriginalNormalizationFilesPage;

/**
 * Created by R0manL on 7/5/22.
 */

public class SystemPage {

    private SystemPage() {
        // None
    }

    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public static SystemPage getSystemPage() {
        return new SystemPage();
    }

    @Step("Navigate to the Original Normalization Files page")
    public OriginalNormalizationFilesPage navigateToOriginalNormalizationFilesPage() {
        $x(".//a[normalize-space(text())='Original Normalization Files']").shouldBe(visible).click();
        return getOriginalNormalizationFilesPage();
    }
}
