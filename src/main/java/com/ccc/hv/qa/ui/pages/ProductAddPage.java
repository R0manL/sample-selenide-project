package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.ui.pages.PageBase.activePageContainer;
import static com.ccc.hv.qa.utils.FileOpsUtils.getAbsoluteResourceFilePath;


public class ProductAddPage {
    public static final String UPLOADED = "Uploaded";
    public static final String VALID = "Valid";
    public static final String PROBLEM_WITH_INVALID_ONIX_UPLOADING_MSG = "There's a problem contacting the upload server. Please contact customer support.";

    private ProductAddPage() {
        // none
    }

    public static ProductAddPage getProductAddPage() {
        return new ProductAddPage();
    }

    public void uploadProductsOrAssets(@NotNull Path resourceFilePath) {
        uploadFile(resourceFilePath);
        $("tbody.files tr").$("td.start").click();
        $("tr.template-download").shouldBe(visible)
                .$("#uploadStatus-0").shouldHave(text(UPLOADED));
        $("tr.template-download")
                .$("#ingestStatus-0").shouldBe(text(VALID), Duration.ofMillis(Configuration.timeout * 4));
    }

    public ProductAddPage uploadFile(@NotNull Path resourceFilePath) {
        File file = getAbsoluteResourceFilePath(resourceFilePath).toFile();
        activePageContainer.$("#fileupload-input").shouldBe(enabled).uploadFile(file);
        $("tbody.files tr").$("td.name span").shouldHave(text(file.getName()));

        return this;
    }

    public SelenideElement getErrorStatusElm() {
        return $("tr.template-upload").shouldBe(visible).$("td.error");
    }

    public SelenideElement getErrorMsgElm() {
        return getErrorStatusElm().$("span");
    }
}
