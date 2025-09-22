package com.ccc.hv.qa.api.services;

import org.jetbrains.annotations.NotNull;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by R0manL on 30/10/20.
 */

public class NukacolaAPIService {
    private static final String INGEST_PERSISTENCE_ROOT_PATH = ENV_CONFIG.hrvIngestPersistenceApiUrl();
    private static final String NUKOLA_DELETE_PATH = "/nukacola/destroy";


    private NukacolaAPIService() {
        // NONE
    }

    public static NukacolaAPIService getNukacolaAPIService() {
        return new NukacolaAPIService();
    }

    public void destroyEntityBy(@NotNull String accountNameOrAlphaCode) {
        given()
                .when()
                .request()
                .baseUri(INGEST_PERSISTENCE_ROOT_PATH)
                .basePath(NUKOLA_DELETE_PATH)
                .contentType("application/json")
                .body(String.format("{\"alphaCode\": \"%s\"}", accountNameOrAlphaCode))
                .post()
                .then()
                .statusCode(HTTP_OK);
    }
}