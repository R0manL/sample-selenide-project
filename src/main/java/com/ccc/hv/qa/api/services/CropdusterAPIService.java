package com.ccc.hv.qa.api.services;

import com.ccc.hv.qa.api.pojo.WatermarkTransactionResponseAPI;
import com.ccc.hv.qa.logging.AllureLogger;
import io.restassured.RestAssured;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.api.services.AuthAPIService.getKeycloakAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by R0manL on 11/12/20.
 */

public class CropdusterAPIService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String CROPDUSTER_ROOT_URL = ENV_CONFIG.hrvCropdusterApiUrl();
    private static final String TRANSACTION_ENDPOINT = "/secure/watermark/transaction/";

    private CropdusterAPIService() {
        //NONE
    }

    public static CropdusterAPIService getCropdusterAPIService() {
        return new CropdusterAPIService();
    }

    public WatermarkTransactionResponseAPI getWatermarkTransaction(int id) {
        log.debug("Read watermark transaction with id: '" + id + "'");

        return given()
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .auth()
                .oauth2(getKeycloakAPIService().getSuperUserAccessToken())
                .when()
                .get(CROPDUSTER_ROOT_URL + TRANSACTION_ENDPOINT + id)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .body()
                .as(WatermarkTransactionResponseAPI.class);
    }
}
