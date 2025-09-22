package com.ccc.hv.qa.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.testng.ScreenShooter;
import com.ccc.hv.qa.logging.AllureLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import org.awaitility.Awaitility;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.utils.EmailUtils.cleanupTestInbox;
import static java.util.concurrent.TimeUnit.SECONDS;


/**
 * Created by R0manL on 31/07/20.
 */
@Listeners({ScreenShooter.class})
public class UITestBase {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String ALLURE_LISTENER_NAME = "allure";
    private static final String ALLURE_REPORTS_FOLDER = "./target/allure-results";
    private static final String SELENOID_HOST_NAME = ENV_CONFIG.selenoidHostName();

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        Configuration.reportsFolder = ALLURE_REPORTS_FOLDER;

        // Cleanup test gMail box.
        cleanupTestInbox();

        log.debug("Use properties", ENV_CONFIG.toString());
        Configuration.baseUrl = ENV_CONFIG.uiUrl();
        log.debug("Execute tests on: " + Configuration.baseUrl);

        final String SERVER_TZ = ENV_CONFIG.serverTimeZone();
        log.debug("Set JVM timezone to: " + SERVER_TZ);
        System.setProperty("user.timezone", SERVER_TZ);
        TimeZone.setDefault(null);

        // Setup selenoid options.
        if (SELENOID_HOST_NAME != null) {
            log.info("Execute tests with Selenoid remotely on: '" + SELENOID_HOST_NAME + "'.");
            Configuration.remote = SELENOID_HOST_NAME + ":4444/wd/hub";

            Map<String, Object> options = new HashMap<>();
            options.put("enableVNC", true);
            options.put("enableVideo", true);
            options.put("enableLog", true);
            options.put("timeZone", SERVER_TZ);
            options.put("screenResolution", Configuration.browserSize);
            options.put("sessionTimeout", (ENV_CONFIG.pageLoadTimeout() * 8 / 1000) + "s");

            Configuration.browserCapabilities = new ChromeOptions();
            Configuration.browserCapabilities.setCapability("selenoid:options", options);

            log.info("Remote chrome capabilities: " + options.toString());
        } else {
            ChromeOptions ops = new ChromeOptions();
            ops.addArguments("--remote-allow-origins=*");
            Configuration.browserCapabilities = ops;
        }

        //Setup rest-assured
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", ENV_CONFIG.restassuredHttpConnectionTimeout())
                .setParam("http.socket.timeout", ENV_CONFIG.restassuredHttpSocketTimeout())
                .setParam("http.connection-manager.timeout", ENV_CONFIG.restassuredHttpConnectionManagerTimeout()));

        // Set timeout values
        Configuration.timeout = ENV_CONFIG.webElmLoadTimeout();
        Configuration.pageLoadTimeout = ENV_CONFIG.pageLoadTimeout();

        Awaitility.setDefaultTimeout(ENV_CONFIG.awaitilityTimeout(), SECONDS);
        Awaitility.setDefaultPollInterval(ENV_CONFIG.awaitilityPollInterval(), SECONDS);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethodSetup() {
        //Setup allure reporting
        Configuration.reportsFolder = ALLURE_REPORTS_FOLDER;
        SelenideLogger.addListener(ALLURE_LISTENER_NAME, new AllureSelenide()
                .includeSelenideSteps(false)
                .screenshots(true)
                .savePageSource(true));
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestTearDown() {
        if ((SELENOID_HOST_NAME != null) && ENV_CONFIG.recordRemoteVideo()) {
            attachSelenoidVideo(getSessionID(), SELENOID_HOST_NAME);
        }

        SelenideLogger.removeListener(ALLURE_LISTENER_NAME);
        closeWebDriver();
    }


    private void attachSelenoidVideo(@NotNull String sessionId, @NotNull String selenoidHostName) {
        final String VIDEO_URL_TEMPLATE = selenoidHostName + ":4444/video/%s.mp4";

        Allure.addAttachment("Link to video", "text/html",
                "<html><body><a href='" + String.format(VIDEO_URL_TEMPLATE, sessionId) + "' type='video/mp4'>" +
                        "Link to video</a></body></html>", ".html");
    }

    private String getSessionID() {
        log.debug("Get current session id.");

        if (WebDriverRunner.driver().hasWebDriverStarted()) {
            return ((RemoteWebDriver) WebDriverRunner.getWebDriver()).getSessionId().toString();
        } else {
            log.warn("Can't get session id. You need to call open() first.");
            return "[undefined]";
        }
    }
}
