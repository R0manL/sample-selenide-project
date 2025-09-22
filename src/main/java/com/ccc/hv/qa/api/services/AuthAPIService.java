package com.ccc.hv.qa.api.services;

import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by R0manL on 11/11/20.
 */

public class AuthAPIService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final String AUTH_SRV_URL = ENV_CONFIG.keycloakAuthUrl();
    private static final String LSCC_TOKEN_PATH = "/auth/realms/LSCC/protocol/openid-connect/token";
    private static final String TOKEN_PATH = "/auth/realms/master/protocol/openid-connect/token";


    private AuthAPIService() {
        // None
    }

    public static AuthAPIService getKeycloakAPIService() {
        return new AuthAPIService();
    }

    @NotNull
    public String getSuperUserAccessToken(){
        log.debug("Getting super user's token.");

        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type","password")
                .formParam("client_id",  ENV_CONFIG.keycloakReaperClientId())
                .formParam("client_secret", ENV_CONFIG.keycloakReaperClientSecret())
                .formParam("username", ENV_CONFIG.keycloakReaperUsername())
                .formParam("password", ENV_CONFIG.keycloakReaperPassword())
                .when()
                .baseUri(AUTH_SRV_URL)
                .basePath(LSCC_TOKEN_PATH)
                .post()
                .then()
                .statusCode(HTTP_OK)
                .and()
                .extract()
                .path("access_token")
                .toString();
    }

    @NotNull
    public String getKeyloakAdminAccessToken(){
        log.debug("Getting keyloak admin's token.");

        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type","password")
                .formParam("client_id",  ENV_CONFIG.keycloakAdminClientId())
                .formParam("username", ENV_CONFIG.keycloakAdminUsername())
                .formParam("password", ENV_CONFIG.keycloakAdminPassword())
                .when()
                .baseUri(AUTH_SRV_URL)
                .basePath(TOKEN_PATH)
                .post()
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .path("access_token")
                .toString();
    }
}
