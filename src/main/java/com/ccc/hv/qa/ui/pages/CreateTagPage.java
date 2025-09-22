package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.CreateTagEntity;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.utils.FileOpsUtils.getAbsoluteResourceFilePath;

public class CreateTagPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private CreateTagPage() {
        // none
    }

    public static CreateTagPage getCreateTagPage() {
        return new CreateTagPage();
    }

    public CreateTagPage createProductTag(CreateTagEntity createTagEntity) {
        setTagName(createTagEntity.getTagName())
                .uploadFileWithProductISBNs(createTagEntity.getCsvFilePath());

        return this;
    }

    private CreateTagPage setTagName(@NotNull String tagName) {
        SelenideElement fieldNameElm = $x(".//span[normalize-space(text())='Create a new tag.']");
        fieldNameElm.$x("./following-sibling::input").val(tagName);
        fieldNameElm.$x("./following-sibling::button").click();

        return this;
    }

    private CreateTagPage uploadFileWithProductISBNs(@NotNull Path xlsxFilePath) {
        xlsxFilePath = getAbsoluteResourceFilePath(xlsxFilePath);
        log.info("Upload file '" + xlsxFilePath.getFileName() + "' with product ISBNs");

        $("input[type='file']").uploadFile(xlsxFilePath.toFile());

        $(".message.success-message")
                .$x("./span[normalize-space(text())='Your tag was created.']")
                .shouldBe(Condition.visible);

        log.info("File with product ISBNs has been successfully uploaded");

        return this;
    }

    public SelenideElement getUploadedTagName(@NotNull String tagName) {
        return $x(".//div[@class='tag-list-item']" +
                "/div[@class='name' and normalize-space(text())='" + tagName + "']");
    }
}
