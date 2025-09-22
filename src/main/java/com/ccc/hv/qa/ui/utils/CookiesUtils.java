package com.ccc.hv.qa.ui.utils;

import com.ccc.hv.qa.logging.AllureLogger;
import io.restassured.http.Cookies;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Cookie;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by R0manL on 12/08/20.
 */

public class CookiesUtils {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private CookiesUtils() {
        // NONE
    }

    /**
     * Convert browser cookies into rest-assured cookies.
     * @param browserCookies from browser.
     * @return rest-assured cookies
     */
    @NotNull
    public static Cookies convertToRestAssuredCookies(Set<Cookie> browserCookies) {
        log.debug("Convert browser's coookies to rest-assured ones.");

        List<io.restassured.http.Cookie> restAssuredCookies = new ArrayList<>();
        for (Cookie cookie : browserCookies) {
            restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }

        return new Cookies(restAssuredCookies);
    }
}
