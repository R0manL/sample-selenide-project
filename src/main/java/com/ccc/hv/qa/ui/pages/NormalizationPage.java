package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.NormalizationRule;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Keys;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by R0manL on 22/09/20.
 */

public class NormalizationPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private NormalizationPage() {
        // None
    }

    public static NormalizationPage getNormalizationPage() {
        return new NormalizationPage();
    }

    public NormalizationPage setRuleAndComment(NormalizationRule normalizationRule) {
        log.info("Set and save rules XML.");

        boolean isOnix30Rule = normalizationRule.isOnix30();
        selectOnixVersion(isOnix30Rule);

        final String ONIX_VER_CLASSNAME = isOnix30Rule ? "onix3-control" : "onix2-control";
        SelenideElement ruleSectionRootElm = $(".rules." + ONIX_VER_CLASSNAME);
        setValueIntoTextarea(ruleSectionRootElm.$("textarea"), normalizationRule.getRule());
        clickSaveIfEnabled(ruleSectionRootElm.$("button"));

        String comment = normalizationRule.getComment();
        if (comment != null) {
            log.info("Set and save comment.");
            SelenideElement commentsSectionRootElm = $(".comments." + ONIX_VER_CLASSNAME);
            setValueIntoTextarea(commentsSectionRootElm.$("textarea"), comment);
            clickSaveIfEnabled(commentsSectionRootElm.$("button"));
        }

        return this;
    }

    private NormalizationPage selectOnixVersion(boolean isOnix30) {
        SelenideElement versionLink = isOnix30 ?
                $("#onixVersionToggle").$x(".//*[normalize-space(text())='ONIX 3.0']"):
                $("#onixVersionToggle").$x(".//*[normalize-space(text())='ONIX 2.1']");

        versionLink.click();

        return this;
    }

    private void clickSaveIfEnabled(SelenideElement saveBtn) {
        if(saveBtn.isEnabled()) {
            log.debug("Click save.");
            saveBtn.click();
        } else {
            log.warn("Value is identical to the old one. Skip setting value.");
        }
    }

    private void setValueIntoTextarea(SelenideElement textarea, @NotNull String text) {
        textarea.clear();
        // Save button is disabled after .clear(). Adding and removing space allows to enable Save button.
        textarea.sendKeys(" ");
        textarea.sendKeys(Keys.BACK_SPACE);
        textarea.sendKeys(text);
    }
}
