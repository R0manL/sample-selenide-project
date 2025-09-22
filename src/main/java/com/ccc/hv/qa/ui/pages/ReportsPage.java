package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;

/**
 * Created by R0manL on 7/5/22.
 */

public class ReportsPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private ReportsPage() {
        log.debug("Switch to 'dashboardingEmbed' iframe.");
        $("#dashboardingEmbed").shouldBe(visible, Duration.ofMillis(ENV_CONFIG.pageLoadTimeout()));
        switchTo().frame("dashboardingEmbed");
        $(".app-main").shouldBe(visible, Duration.ofMillis(Configuration.pageLoadTimeout));
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
    }

    public static ReportsPage getReportsPage() {
        return new ReportsPage();
    }

    public SelenideElement getToolBarElm() {
        return $("#prism-toolbar");
    }

    public SelenideElement getLeftViewSectionElm() {
        return $("#prism-leftview");
    }

    public SelenideElement getDashboardSectionElm() {
        return $("dashboard.dashboard");
    }
}
