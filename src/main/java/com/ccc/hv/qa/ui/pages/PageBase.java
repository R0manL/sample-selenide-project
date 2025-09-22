package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.impl.WebElementsCollectionWrapper;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;

/**
 * Created by R0manL on 10/08/20.
 */

public class PageBase {
    private static final String ACTIVE_PAGE_CSS_SELECTOR = ".ui-page-active";
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    protected static final String HORIZONTAL_SCROLL_INTO_VIEW_OPTIONS = "{block: 'center', inline: 'start'}";

    protected PageBase() {
        // None
    }

    /**
     * We have 2 identical (first visible, second not visible) elements on the page.
     * This is a root element for visible block.
     */
    protected static final SelenideElement activePageContainer = $(ACTIVE_PAGE_CSS_SELECTOR);

    protected static void waitOnLoadingMsgDisappeared() {
        log.debug("Wait until content will be loaded.");

        final int MAX_NUM_OF_TRIES = 10;
        SelenideElement loadingMsg = $(".ui-loader .loader-contents");

        for(int i = 0; i < MAX_NUM_OF_TRIES; i++) {
            if(loadingMsg.isDisplayed()) {
                log.debug("Wait until 'loader' will disappear.");
                loadingMsg.should(disappear);
                Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
                break;
            }
        }
    }

    protected SelenideElement getFirstVisibleElement(SelenideElement one, SelenideElement two) {
        List<SelenideElement> elementsCombined = new ArrayList<>();
        elementsCombined.add(one);
        elementsCombined.add(two);
        WebElementsCollectionWrapper wrapper = new WebElementsCollectionWrapper(WebDriverRunner.driver(), elementsCombined);
        ElementsCollection selenideCollectionCombined = new ElementsCollection(wrapper);

        return selenideCollectionCombined.filter(visible).first();
    }

    protected void clickWithJsBy(@NotNull String jQueryElmSelector) {
        String jQueryAbsElmSelector = ACTIVE_PAGE_CSS_SELECTOR + " " + jQueryElmSelector;
        log.debug("Click at '" + jQueryAbsElmSelector + "' with javascript.");
        $(jQueryAbsElmSelector).should(exist);
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 2);
        executeJavaScript("$('" + jQueryAbsElmSelector + "').click();");
    }

    protected void clickWithJSIfElmHasNotVisible(SelenideElement elmThatShouldBeVisible, @NotNull String clickAtElmLocator) {
        try {
            log.debug("Check if element is visible.");
            elmThatShouldBeVisible.shouldBe(visible, Duration.ofMillis(ENV_CONFIG.webElmLoadTimeout() * 2));
        } catch (ElementNotFound e) {
            log.warn("Element has not visible, click at '" + clickAtElmLocator + "'.");
            clickWithJsBy(clickAtElmLocator);
        }
    }
}
