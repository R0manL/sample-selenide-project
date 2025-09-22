package com.ccc.hv.qa.api.services;

import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.ZiplineDBService.getZiplineJobMasterIdWhenExistsFor;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class ZiplineAPIService {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @NotNull private static final String ZIPLINE_ROOT_PATH = ENV_CONFIG.ziplineServerUrl();
    private static final String JOB_DIST_PATH_TEMPLATE = "/zipline-web/rest/zipline/job/%s/dist";


    private ZiplineAPIService() {
        // NONE
    }

    public static ZiplineAPIService getZiplineAPIService() {
        return new ZiplineAPIService();
    }

    public void triggerZipFileDistributionFor(@NotNull String productTitle) {
        log.debug("Distribute zip audio file for '" + productTitle + "' product.");
        int jobId = getZiplineJobMasterIdWhenExistsFor(productTitle);
        given()
                .when()
                .request()
                .baseUri(ZIPLINE_ROOT_PATH)
                .basePath(String.format(JOB_DIST_PATH_TEMPLATE, jobId))
                .get()
                .then()
                .statusCode(HTTP_OK);
    }
}