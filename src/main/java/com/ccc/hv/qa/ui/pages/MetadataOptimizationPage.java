package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.MetadataOptimization;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.file.services.XmlFileService.readXmlFileToString;

/**
 * Created by R0manL on 22/09/20.
 */

public class MetadataOptimizationPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private MetadataOptimizationPage() {
        // None
    }

    public static MetadataOptimizationPage getChannelOptimizationPage() {
        return new MetadataOptimizationPage();
    }

    public void setOptimizationRulesAndComments(MetadataOptimization metadataOptimization) {
        log.info("Set and save rules XML.");

        if (metadataOptimization.isOnix30Rule()) {
            log.info("Set rule for onix 3.0.");
            $x(".//*[@id='onixVersionToggle']//*[text()='ONIX 3.0']").click();
        }

        final String ONIX_VER_CLASSNAME = metadataOptimization.isOnix30Rule() ? ".onix3-control" : ".onix2-control";
        String ruleSectionCssSelector = ".rules" + ONIX_VER_CLASSNAME;


        String ruleRelatedPath = metadataOptimization.getRuleFileRelPath();

        setValueInto(ruleSectionCssSelector + " textarea", readXmlFileToString(Paths.get(ruleRelatedPath)));
        clickSaveIfEnabled($(ruleSectionCssSelector).$("button"));

        String comment = metadataOptimization.getComment();
        if (comment != null) {
            log.info("Set and save comments.");
            String commentsSectionCssLocator = ".comments" + ONIX_VER_CLASSNAME;
            setValueInto(commentsSectionCssLocator + " textarea", comment);
            clickSaveIfEnabled($(commentsSectionCssLocator).$("button"));
        }

        log.info("Set labels.");
        $("label[for='label_onix30'] input").setSelected(metadataOptimization.isLabelOnix30());
        $("label[for='label_applexml'] input").setSelected(metadataOptimization.isLabelAppleXML());
        $("label[for='label_custom'] input").setSelected(metadataOptimization.isLabelCustom());
        if (metadataOptimization.getLabelCustomText() != null) {
            $("input.custom-name").val(metadataOptimization.getLabelCustomText());
        }

        SelenideElement saveLabelsBtn = $x(".//*[@for='label_onix30']/../button[text()='Save']");
        clickSaveIfEnabled(saveLabelsBtn);
    }

    private void clickSaveIfEnabled(SelenideElement saveBtn) {
        if(saveBtn.isEnabled()) {
            log.debug("Click save.");
            saveBtn.click();
        } else {
            log.warn("Value is identical to the old one. Skip setting rule.");
        }
    }

    private void setValueInto(@NotNull String cssLocator, @NotNull String text) {
        log.debug("Set text: '" + text + "' into: " + cssLocator);
        SelenideElement textarea = $(cssLocator);
        textarea.shouldBe(Condition.visible);
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

        executeJavaScript(String.format("$('%s').val('%s')", cssLocator, text)); //Note. Look if JS works better then sendKeys.
        textarea.sendKeys(" ");
    }
}
