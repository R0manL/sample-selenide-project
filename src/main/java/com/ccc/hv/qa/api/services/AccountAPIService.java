package com.ccc.hv.qa.api.services;

import com.ccc.hv.qa.logging.AllureLogger;
import io.restassured.RestAssured;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.api.services.AuthAPIService.getKeycloakAPIService;
import static com.ccc.hv.qa.api.services.NukacolaAPIService.getNukacolaAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.AccountDBService.getAccountIDBy;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by R0manL on 24/09/20.
 */

public class AccountAPIService {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final String API_ROOT_PATH = ENV_CONFIG.hrvApiUrl();
    private static final String ACTIVATE_USR_TEMPLATE_PATH = "/account/%s/active/true/json";


    private AccountAPIService() {
        // None
    }

    public static AccountAPIService getAccountAPIService() {
        return new AccountAPIService();
    }

    public void activateAccount(@NotNull String accountName) {
        log.debug("Activate '" + accountName + "' account.");
        String accountId = getAccountIDBy(accountName);
        given()
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .auth()
                .oauth2(getKeycloakAPIService().getSuperUserAccessToken())
                .baseUri(API_ROOT_PATH)
                .basePath(String.format(ACTIVATE_USR_TEMPLATE_PATH, accountId))
                .when()
                .get()
                .then()
                .statusCode(HTTP_OK);
    }

    public void deleteAccountBy(@NotNull String name) {
        log.debug("Delete account: " + name);

        getNukacolaAPIService().destroyEntityBy(name);
    }
}
