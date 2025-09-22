package com.ccc.hv.qa.api.services;

import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class EventAPIService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String RECORD_SOURCE_NAME = ENV_CONFIG.recordSourceName();
    private static final String RETRIEVE_THE_LIST_OF_CURRENT_EVENTS = "/hrv-view-web/rest/analyticsevent/businessunit/%s/current";
    private static final String CURRENT_EVENT_SRV_URL_1 = ENV_CONFIG.currentEventSrvUrl01();
    private static final String CURRENT_EVENT_SRV_URL_2 = ENV_CONFIG.currentEventSrvUrl02();


    private EventAPIService() {
        // NONE
    }

    public static EventAPIService getEventAPIService() {
        return new EventAPIService();
    }

    public void setRetrieveTheListOfCurrentEvents() {
        boolean srv1Status = setRetrieveTheListOfCurrentEvents(CURRENT_EVENT_SRV_URL_1);
        boolean srv2Status = setRetrieveTheListOfCurrentEvents(CURRENT_EVENT_SRV_URL_2);
        if (!srv1Status && !srv2Status) {
            throw new IllegalStateException("Both servers for 'Retrieve the list of current events from hrv view' are down");
        }
    }

    private boolean setRetrieveTheListOfCurrentEvents(@NotNull String baseUrl) {
        log.debug("Retrieve the list of current events from hrv view for server " + baseUrl);
        try {
            return given()
                    .when()
                    .baseUri(baseUrl)
                    .get(String.format(RETRIEVE_THE_LIST_OF_CURRENT_EVENTS, RECORD_SOURCE_NAME))
                    .then()
                    .extract()
                    .statusCode() == HTTP_OK;
        } catch (Exception e) {
            throw new IllegalStateException("Retrieve the list of current events is unsuccessful for the server '" + baseUrl + "'; \nError message: '" + e.getMessage() + "'.");
        }
    }
}
