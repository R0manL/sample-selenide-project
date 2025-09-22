package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import io.qameta.allure.Step;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.AnalyticsPage.getAnalyticsPage;
import static com.ccc.hv.qa.ui.pages.ReportsPage.getReportsPage;
import static com.ccc.hv.qa.ui.pages.SystemPage.getSystemPage;

public class HomePage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private HomePage() {
        // None
    }

    public static HomePage getHomePage() {
        return new HomePage();
    }

    public static HomePage switchToHrvFrame() {
        log.debug("Switch to iframe.");
        switchTo().frame("appframe");
        activePageContainer.$(".nav-bar").shouldBe(visible, Duration.ofMillis(Configuration.pageLoadTimeout));
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

        return new HomePage();
    }

    public SelenideElement getHomePageMarketplaceMonitoringSectionElm() {
        return $("#monitoringBasicsContainer");
    }

    public SelenideElement getAnalyticsLinkElm() {
        log.info("Get Analytics link element");
        return activePageContainer.$x(".//a[child::span[@class='glyphicon glyphicon-stats']]");
    }

    public SelenideElement getReportLinkElm() {
        log.info("Get Report link element");
        return $(".ui-link#gotoReports");
    }

    public ReportsPage clickReports() {
        getReportLinkElm().click();

        return getReportsPage();
    }

    @Step("Click 'System'")
    public SystemPage clickSystem() {
        activePageContainer.$x(".//a[child::span[@class='glyphicon glyphicon-cog']]").click();

        return getSystemPage();
    }

    public SelenideElement getCopyRightElm() {
        log.info("Get CopyRight link element");
        return $("div.col-md-6.credit");
    }

    public SelenideElement getHelpElm() {
        log.info("Get Help link element");
        return $("#footerNav").$x(".//li[not(@class)]").$("a");
    }

    public SelenideElement getFAQElm() {
        log.info("Get FAQ link element");
        return $("#footerNav").$x(".//li[@class]").$("a");
    }

    public AnalyticsPage navigateToBuyBoxAnalysis() {
        log.info("Navigate to Buy Box Analysis Page");
        getAnalyticsLinkElm().click();
        return getAnalyticsPage();
    }

}