package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Created by R0manL on 7/5/22.
 */

public class OriginalNormalizationFilesPage {

    private OriginalNormalizationFilesPage() {
        // None
    }

    public static OriginalNormalizationFilesPage getOriginalNormalizationFilesPage() {
        return new OriginalNormalizationFilesPage();

    }

    @Step("Enter alpha code '{}'")
    public OriginalNormalizationFilesPage enterAlphaCode(@NotNull String alphaCode) {
        $("#alphaCode").val(alphaCode);
        return this;
    }

    public SelenideElement getNumberOfFoundFilesElm() {
        return $(".found");
    }

    @Step("Click Load")
    public OriginalNormalizationFilesPage clickLoad() {
        $(".form-inline button").click();
        return this;
    }

    @Step("Enter text '{}' to the filter input")
    public OriginalNormalizationFilesPage enterTextToFilterInput(@NotNull String filterText) {
        $(".filter-input").val(filterText);
        return this;
    }

    @Step("Clear filter text")
    public OriginalNormalizationFilesPage clearFilteredText() {
        $(".btn-clear").click();
        return this;
    }

    @Step("Download file with name '{}'")
    public File downloadFileWithName(@NotNull String fileName) {
        File file;
        try {
            file = $x(".//a[normalize-space(text())='" + fileName + "']").download();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not provide download functionality for the filename " + fileName);
        }
        return file;
    }
}
