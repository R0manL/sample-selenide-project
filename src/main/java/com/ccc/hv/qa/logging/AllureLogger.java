package com.ccc.hv.qa.logging;

import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by R0manL on 5/21/21.
 */

public class AllureLogger {
    private final Logger logger;


    public AllureLogger(@NotNull String className) {
        this.logger = LoggerFactory.getLogger(className);
    }

    @Step("{0}")
    public void info(String message) {
        this.logger.info(message);
    }

    @Step("WARN: {0}")
    public void warn(String message) {
        this.logger.warn(message);
    }

    @Step("WARN: {0}")
    public void warn(String message, String details) {
        this.logger.warn("{}\nDETAILS: {}", message, details);
    }

    @Step("DEBUG: {0}")
    public void debug(String message) {
        this.logger.debug(message);
    }

    @Step("DEBUG: {0}")
    public void debug(String message, String details) {
        this.logger.debug("{}\nDETAILS: {}", message, details);
    }
}