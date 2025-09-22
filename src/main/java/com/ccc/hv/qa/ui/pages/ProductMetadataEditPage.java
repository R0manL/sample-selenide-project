package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.ProductDetailsPage.getProductDetailsPage;

public class ProductMetadataEditPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private ProductMetadataEditPage() {
        // NONE
    }

    public static ProductMetadataEditPage getProductMetadataEditPage() {
        return new ProductMetadataEditPage();
    }

    /**
     * Method update text value in Ace editor.
     * @param oldUniqueValue old text value. WARNING. Value must be unique text.
     * @param newValue WARNING. Value must not contains spaces (use "-" or "_" instead).
     */
    public ProductMetadataEditPage updateValueInEditorBy(@NotNull String oldUniqueValue, @NotNull String newValue) {
        log.info("Update old value: '" + oldUniqueValue + "' with a new value: '" + newValue + "'.");

        if (newValue.contains(".") || newValue.contains(" ")) {
            throw new IllegalArgumentException("New value can't contains dots or spaces. Use '-' or '_' instead.");
        }

        SelenideElement textElm = $x(".//span[@class='ace_text ace_xml' and text()=\"" + oldUniqueValue + "\"]");
        scrollToElement(textElm);
        textElm.shouldBe(visible);

        int attempts = 0;
        while(attempts < 2) {
            try {
                textElm.shouldBe(visible);
                int textWidth = textElm.getSize().getWidth();
                actions()
                        .moveToElement(textElm, -(textWidth / 2), 0)
                        .clickAndHold()
                        .moveToElement(textElm, (textWidth / 2), 0)
                        .release()
                        .sendKeys(newValue)
                        .build()
                        .perform();
                break;
            } catch(StaleElementReferenceException e) {
                log.warn("Can't edit text (getting StaleElementReferenceException) in metadata editor.");
            }
            attempts++;
        }

        return this;
    }

    public ProductMetadataEditPage validateAndSave() {
        log.info("Click validate and save button.");

        SelenideElement validateAndSaveBtn = $("button.save-button");
        validateAndSaveBtn.click();
        validateAndSaveBtn.$(".spinner-wrapper").shouldNotBe(visible);
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 3);

        return this;
    }

    public ProductDetailsPage clickBack() {
        log.info("Click back to product details page.");
        $("a.back").click();

        return getProductDetailsPage();
    }

    /**
     * Method scroll till {elm} will not be visible.
     * @param elm - searched element.
     */
    private void scrollToElement(SelenideElement elm) {
        SelenideElement editor = $(".ace_editor");
        editor.shouldBe(visible);

        SelenideElement endElm = $x(".//*[@class='ace_line' and ./*[contains(@class, 'ace_end-tag-open')] and ./*[text()='Product']]");
        while (!elm.exists() && !endElm.isDisplayed()) {
            actions().sendKeys(editor, Keys.PAGE_DOWN).build().perform();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        }
    }
}
