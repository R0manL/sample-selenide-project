package com.ccc.hv.qa.api.services;

import com.ccc.hv.qa.logging.AllureLogger;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class ChannelAssociationAPIService {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String INGEST_PERSISTENCE_ROOT_PATH = ENV_CONFIG.hrvIngestPersistenceApiUrl();
    private static final String UPDATE_ONIX_DISTRIBUTION_INTERVAL = "/association/ca/%s/onixdistributioninterval/%s";
    private static final String UPDATE_NEXT_ONIX_DISTRIBUTION_UTC_TO_NOW = "/association/ca/%s/nextonixdistribution/now";

    private ChannelAssociationAPIService() {
        // NONE
    }

    public static ChannelAssociationAPIService getChannelAssociationAPIService() {
        return new ChannelAssociationAPIService();
    }

    public void setOnixDistributionInterval(int channelAssociationId, int interval) {
        log.debug("Update Onix Distribution Interval to " + interval + " for channel association with id : " + channelAssociationId);
        given()
                .when()
                .request()
                .baseUri(INGEST_PERSISTENCE_ROOT_PATH)
                .basePath(String.format(UPDATE_ONIX_DISTRIBUTION_INTERVAL, channelAssociationId, interval))
                .get()
                .then()
                .statusCode(HTTP_OK);
    }

    public void setNextOnixDistributionUtcToNow(int channelAssociationId) {
        log.debug("Update Next Onix Distribution Utc time to now for channel association with id : " + channelAssociationId);
        given()
                .when()
                .request()
                .baseUri(INGEST_PERSISTENCE_ROOT_PATH)
                .basePath(String.format(UPDATE_NEXT_ONIX_DISTRIBUTION_UTC_TO_NOW, channelAssociationId))
                .get()
                .then()
                .statusCode(HTTP_OK);
    }
}
