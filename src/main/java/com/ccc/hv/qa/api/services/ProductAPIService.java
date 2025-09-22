package com.ccc.hv.qa.api.services;

import com.ccc.hv.qa.api.pojo.*;
import com.codeborne.selenide.Selenide;
import com.ccc.hv.qa.api.enums.OnixVersion;
import com.ccc.hv.qa.api.pojo.*;
import com.ccc.hv.qa.core.CustomCollectors;
import com.ccc.hv.qa.db.pojo.PhaseTrackMasterDB;
import com.ccc.hv.qa.db.services.BatchDBService;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.ContentType;
import com.ccc.hv.qa.ui.enums.MetadataActivityPhase;
import io.restassured.RestAssured;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ccc.hv.qa.api.services.AuthAPIService.getKeycloakAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.BatchDBService.getBatchIdListFor;
import static com.ccc.hv.qa.db.services.BatchDBService.getPhaseTrackMasterFor;
import static com.ccc.hv.qa.db.services.ProductDBService.getProductBy;
import static com.ccc.hv.qa.db.services.ProductDBService.getProductIDBy;
import static com.ccc.hv.qa.db.services.TenantDBService.getTenantIDBy;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Created by R0manL on 30/10/20.
 */

public class ProductAPIService {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final String REBUILD_INDEX_SRV_URL_1 = ENV_CONFIG.rebuildSearchIndexSrvUrl01();
    private static final String REBUILD_INDEX_SRV_URL_2 = ENV_CONFIG.rebuildSearchIndexSrvUrl02();
    private static final String REBUILD_INDEX_PATH = "/ingest-persistence-web/rest/search/rebuild";
    private static final String SEARCH_RESULTS_PATH = "/ingest-persistence-web/rest/search/%s";
    private static final long REBUILD_INDEX_TIMEOUT = ENV_CONFIG.webElmLoadTimeout();

    private static final String INGEST_PERSISTENCE_ROOT_PATH = ENV_CONFIG.hrvIngestPersistenceApiUrl();
    private static final String TRIGGER_AUTO_DISTR_PATH = "/precompute/distribute";
    private static final String DELETE_MARKED_PRODUCTS_PATH = "/schedule/job/DELETE_PRODUCTS_JOB_TYPE";

    private static final String API_ROOT_PATH = ENV_CONFIG.hrvApiUrl();
    private static final String PRODUCTS_RECENTLY_ADDED_PATH_TEMPLATE = "/db/%s/ProductsRecentlyAdded/json";
    private static final String PRODUCTS_RECENTLY_UPDATED_PATH_TEMPLATE = "/db/%s/ProductsRecentlyUpdated/json";
    private static final String TOTAL_PRODUCTS_PATH_TEMPLATE = "/db/%s/ProductTotals/json";

    private static final String MAILSTROM_BOX_URL = ENV_CONFIG.mailsrtomBoxUrl();
    private static final String TRIGGER_OPT_FAILED_DIGEST = "/mailstrom-web/rest/notification/optimizationFail/send/";

    private static final String PRODUCTS_RECENT_REFRESH_URL = ENV_CONFIG.hrvTimeBanditApiUrl() + "/timers/products/recents/refresh";

    private static final String TIME_BANDIT_URL = ENV_CONFIG.hrvTimeBanditApiUrl();
    private static final String TIME_BANDIT_SRV_1_URL = ENV_CONFIG.hrvTimeBanditSrvUrl01();
    private static final String TIME_BANDIT_SRV_2_URL = ENV_CONFIG.hrvTimeBanditSrvUrl02();
    private static final String INGEST_ALERT_PATH = "/time-bandit-web/rest/timers/products/ingestedalert";
    private static final String PRODUCTS_NOTIFICATION_PATH = "/timers/products/notifications/";


    private ProductAPIService() {
        // NONE
    }

    public static ProductAPIService getProductAPIService() {
        return new ProductAPIService();
    }

    public void rebuildProductSearchIndex() {
        log.debug("Rebuild products search index.");
        boolean srv1Status = rebuildProductSearchIndexAndGetStatus(REBUILD_INDEX_SRV_URL_1);
        boolean srv2Status = rebuildProductSearchIndexAndGetStatus(REBUILD_INDEX_SRV_URL_2);
        if (!srv1Status && !srv2Status) {
            throw new IllegalStateException("Both servers for rebuild Product Search Index are down");
        }

        Selenide.sleep(REBUILD_INDEX_TIMEOUT);
    }

    public void verifyCreatedProductIsInSearchServiceResults(@NotNull String title) {
        log.debug("Find the created product in the API search results");
        int productId = getProductIDBy(title);
        int tenantId = Objects.requireNonNull(getProductBy(title)).getHvTenmasTenantMasterId();
        await().atMost(ENV_CONFIG.awaitilityTimeout() * 10, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(searchServiceResultsBy(title, REBUILD_INDEX_SRV_URL_1, tenantId))
                        .as("Product '" + title + "' has not appeared in API search results.")
                        .contains(String.valueOf(productId))
                );
    }

    public void triggerAutoDistribution() {
        log.debug("Trigger auto distribution.");
        given()
                .when()
                .request()
                .baseUri(INGEST_PERSISTENCE_ROOT_PATH)
                .basePath(TRIGGER_AUTO_DISTR_PATH)
                .get()
                .then()
                .statusCode(HTTP_OK);
    }

    public void deleteMarkedProducts() {
        log.debug("Delete marked for deletion products.");
        given()
                .when()
                .request()
                .baseUri(INGEST_PERSISTENCE_ROOT_PATH)
                .basePath(DELETE_MARKED_PRODUCTS_PATH)
                .get()
                .then()
                .statusCode(HTTP_OK);
    }

    public void waitOnNumAddedProductsWithinPast24HoursIsGreaterThan(int oldValue, ContentType contentType, @NotNull String buName) {
        final int buId = getTenantIDBy(buName);

        await().atMost(ENV_CONFIG.awaitilityTimeout() * 20, TimeUnit.SECONDS).untilAsserted(() -> {
            int currentValue = getNumberOfRecentlyAddedProductsFor(buId, contentType);
            assertThat(currentValue)
                    .as("'Added Products within Past 24 Hours' is not as expected..")
                    .isGreaterThan(oldValue);
        });
    }

    public void waitOnNumOfUpdatedProductsWithinPast24HoursIsGreaterThan(int oldValue, ContentType contentType, @NotNull String buName) {
        final int buId = getTenantIDBy(buName);

        await().atMost(ENV_CONFIG.awaitilityTimeout() * 10, TimeUnit.SECONDS).untilAsserted(() -> {
            int currentValue = getNumberOfRecentlyUpdatedProductsFor(buId, contentType);
            assertThat(currentValue)
                    .as("'Updated Products within Past 24 Hours' is not as expected..")
                    .isGreaterThan(oldValue);
        });
    }

    public void waitOnNumOfAddedProductsIsGreaterThan(int oldValue, ContentType contentType, @NotNull String buName) {
        final int buId = getTenantIDBy(buName);

        await().atMost(ENV_CONFIG.awaitilityTimeout() * 10, TimeUnit.SECONDS).untilAsserted(() -> {
            int currentValue = getNumberOfProductsInProductSnapshotFor(buId, contentType);
            assertThat(currentValue)
                    .as("Invalid 'Number of added Products'.")
                    .isGreaterThan(oldValue);
        });
    }

    public void waitOnNumOfAddedProductsWithAssetIsGreaterThan(int oldValue, ContentType contentType, @NotNull String buName) {
        final int buId = getTenantIDBy(buName);

        await().atMost(ENV_CONFIG.awaitilityTimeout() * 10, TimeUnit.SECONDS).untilAsserted(() -> {
            int currentValue = getNumberOfProductsWithAssetInProductSnapshotFor(buId, contentType);
            assertThat(currentValue)
                    .as("Invalid 'Number of added Products with asset'.")
                    .isGreaterThan(oldValue);
        });
    }

    public int getNumberOfRecentlyAddedProductsFor(@NotNull String buName, ContentType contentType) {
        final int buId = getTenantIDBy(buName);
        return getNumberOfRecentlyAddedProductsFor(buId, contentType);
    }

    public int getNumberOfRecentlyUpdatedProductsFor(@NotNull String buName, ContentType contentType) {
        final int buId = getTenantIDBy(buName);
        return getNumberOfRecentlyUpdatedProductsFor(buId, contentType);
    }

    public int getNumberOfProductsInProductSnapshotFor(@NotNull String buName, ContentType contentType) {
        final int buId = getTenantIDBy(buName);
        return getNumberOfProductsInProductSnapshotFor(buId, contentType);
    }

    public int getNumberOfProductsWithAssetInProductSnapshotFor(@NotNull String buName, ContentType contentType) {
        final int buId = getTenantIDBy(buName);
        return getNumberOfProductsWithAssetInProductSnapshotFor(buId, contentType);
    }

    public void refreshProductRecentsInProductFlyoutSection() {
        log.info("Refresh numbers in products flyout section.");

        given()
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .auth()
                .oauth2(getKeycloakAPIService().getSuperUserAccessToken())
                .when()
                .request()
                .baseUri(PRODUCTS_RECENT_REFRESH_URL)
                .get()
                .then()
                .statusCode(HTTP_OK);
    }

    public void triggerIngestAlert() {
        log.debug("Trigger ingested alert.");

        boolean srv1Status = updateIngestedAlertValues(TIME_BANDIT_SRV_1_URL);
        boolean srv2Status = updateIngestedAlertValues(TIME_BANDIT_SRV_2_URL);
        if (!srv1Status && !srv2Status) {
            throw new IllegalStateException("Both (time bandit) servers for 'ingested alert' are down");
        }
    }

    public void waitOnCompleteStatusForFile(@NotNull String filename, MetadataActivityPhase metadataActivityPhase) {
        String expectedStatus = "COMPLETE";
        log.debug("Wait on '" + filename + "' track master phase '" + metadataActivityPhase + "' have status '" + expectedStatus + "'.");

        await().atMost(ENV_CONFIG.awaitilityTimeout() * 10, TimeUnit.SECONDS).untilAsserted(() -> {
            PhaseTrackMasterDB phaseTrackMasterDB = BatchDBService.getPhaseTrackMasterFor(filename, metadataActivityPhase);
            assertThat(phaseTrackMasterDB.getStatus())
                    .as("Completed status is not displayed for phase '" + metadataActivityPhase + "'.")
                    .isEqualTo(expectedStatus);
        });
    }

    public void waitOnCompleteStatusForFile(int batchId, MetadataActivityPhase metadataActivityPhase) {
        String expectedStatus = "COMPLETE";
        log.debug("Wait on '" + batchId + "' track master phase '" + metadataActivityPhase + "' have status '" + expectedStatus + "'.");

        await().atMost(ENV_CONFIG.awaitilityTimeout() * 10, TimeUnit.SECONDS).untilAsserted(() -> {
            PhaseTrackMasterDB phaseTrackMasterDB = getPhaseTrackMasterFor(batchId, metadataActivityPhase);
            assertThat(phaseTrackMasterDB.getStatus())
                    .as("Completed status is not displayed for phase '{}'.", metadataActivityPhase)
                    .isEqualTo(expectedStatus);
        });
    }

    public void waitOnNumberOfBatchIdRecordsForFile(@NotNull String filename, int expectedNumberIfBatchIdRecords) {
        log.debug("Wait on '" + filename + "' to  have second batch id record after second ingestion.");

        await().atMost(ENV_CONFIG.awaitilityTimeout() * 10, TimeUnit.SECONDS).untilAsserted(() -> {
            List<Integer> batchIdList = getBatchIdListFor(filename);
            assertThat(batchIdList.size())
                    .as("Expected to have '{}' batch id records.", expectedNumberIfBatchIdRecords)
                    .isEqualTo(expectedNumberIfBatchIdRecords);
        });
    }

    private int getNumberOfProductsInProductSnapshotFor(int buId, ContentType contentType) {
        final int contentTIndex = 0;
        final int numOfProductsIndex = 1;

        return Integer.parseInt(getProductSnapshotFor(buId)
                .stream()
                .filter(entity -> contentType.getText().equals(entity.get(contentTIndex)))
                .collect(CustomCollectors.toSingleton())
                .get(numOfProductsIndex));
    }

    private int getNumberOfProductsWithAssetInProductSnapshotFor(int buId, ContentType contentType) {
        final int contentIndex = 0;
        final int numOfProductsWithAssetIndex = 2;

        return Integer.parseInt(getProductSnapshotFor(buId)
                .stream()
                .filter(entity -> contentType.getText().equals(entity.get(contentIndex)))
                .collect(CustomCollectors.toSingleton())
                .get(numOfProductsWithAssetIndex));
    }

    private boolean rebuildProductSearchIndexAndGetStatus(@NotNull String srvUrl) {
        log.info("Rebuild product search index for '" + srvUrl + "' server.");
        try {
            return given()
                    .when()
                    .request()
                    .baseUri(srvUrl)
                    .basePath(REBUILD_INDEX_PATH)
                    .get()
                    .then()
                    .extract()
                    .statusCode() == HTTP_OK;
        } catch (Exception e) {
            log.warn("Rebuild search call is unsuccessful for the server '" + srvUrl + "'; \nError message: '" + e.getMessage() + "'.");
            return false;
        }
    }

    public String searchServiceResultsBy(@NotNull String title, @NotNull String srvUrl, int productTenantId) {
        log.info("API '" + title + "' product search for '" + srvUrl + "' server.");
        return given()
                .when()
                .request()
                .contentType("text/plain")
                .baseUri(srvUrl)
                .basePath(String.format(SEARCH_RESULTS_PATH, productTenantId))
                .body(title)
                .post()
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .response()
                .asString();
    }

    public void triggerOptimizationFailureDigest() {
        log.debug("Trigger Optimization Failure Digest distribution.");
        given()
                .when()
                .request()
                .baseUri(MAILSTROM_BOX_URL)
                .basePath(TRIGGER_OPT_FAILED_DIGEST)
                .get()
                .then()
                .statusCode(HTTP_OK);
    }

    public int getNumberOfAddedProductsTodayFor(@NotNull String buName) {
        log.info("Get number of added products for today for BU: " + buName);
        List<ProductsIngestBUStatus> statuses = getProductsNotificationStatus(buName).getPia();

        int numOfStatuses = statuses.size();
        assertThat(numOfStatuses).as("Expect single product ingest status record, but got: " + numOfStatuses).isEqualTo(1);

        return statuses.get(0).addedToday;
    }

    public int getNumberOfUpdatedProductsTodayFor(@NotNull String buName) {
        log.info("Get number of updated products for today for BU: " + buName);
        List<ProductsIngestBUStatus> statuses = getProductsNotificationStatus(buName).getPia();

        int numOfStatuses = statuses.size();
        assertThat(numOfStatuses).as("Expect single product ingest status record, but got: " + numOfStatuses).isEqualTo(1);

        return statuses.get(0).updatedToday;
    }

    public int getNumberOfIngestFailedOnix21TodayFor(@NotNull String buName) {
        log.info("Get number of failed products (onix 2.1) for today for BU: " + buName);
        List<ProductIngestFailedBUStatus> statuses = getProductsNotificationStatus(buName).getFoa();

        int numOfStatuses = statuses.size();
        assertThat(numOfStatuses).as("Expect single product ingest fail status record, but got: " + numOfStatuses).isEqualTo(1);

        return statuses.get(0).onix2Failed;
    }

    public int getNumberOfIngestFailedOnix30TodayFor(@NotNull String buName) {
        log.info("Get number of failed products (onix 3.0) for today for BU: " + buName);
        List<ProductIngestFailedBUStatus> statuses = getProductsNotificationStatus(buName).getFoa();

        int numOfStatuses = statuses.size();
        assertThat(numOfStatuses).as("Expect single product ingest fail status record, but got: " + numOfStatuses).isEqualTo(1);

        return statuses.get(0).onix3Failed;
    }

    public void waitWhenNumberOfIngestFailuresIsGreaterThen(int expectedNum, @NotNull String buName, OnixVersion onixVersion) {
        log.debug("Wait when ingest failed number of onixes will be greater then :" + expectedNum);

        await().atMost(ENV_CONFIG.awaitilityTimeout() * 10, TimeUnit.SECONDS).untilAsserted(() -> {
            int actualNumOfIngestFailedOnixes;
            if(OnixVersion.ONIX_2_1.equals(onixVersion)) {
                actualNumOfIngestFailedOnixes = getNumberOfIngestFailedOnix21TodayFor(buName);
            } else {
                actualNumOfIngestFailedOnixes = getNumberOfIngestFailedOnix30TodayFor(buName);
            }

            assertThat(actualNumOfIngestFailedOnixes)
                    .as("Invelid number of ingest failed onixes.")
                    .isGreaterThan(expectedNum);
        });
    }

    private int getNumberOfRecentlyAddedProductsFor(int buId, ContentType contentType) {
        List<ProductFlyoutRecentActivityAddedRow> addedProducts = getRecentlyAddedProductsFor(buId);

        List<ProductFlyoutRecentActivityAddedRow> addedProductsFilteredByContentType = addedProducts
                .stream()
                .filter(addedProduct -> contentType.toString().equals(addedProduct.getContentType()))
                .collect(Collectors.toList());

        int numOfAddedProductsFilteredByContentType = addedProductsFilteredByContentType.size();
        if (numOfAddedProductsFilteredByContentType == 0) {
            return 0;
        }

        if (numOfAddedProductsFilteredByContentType > 1) {
            throw new IllegalArgumentException("Expect single or zero value with '" + contentType + "', " +
                    "but got: " + numOfAddedProductsFilteredByContentType);
        }

        return addedProductsFilteredByContentType.get(0).getAdded();
    }

    private int getNumberOfRecentlyUpdatedProductsFor(int buId, ContentType contentType) {
        List<ProductFlyoutRecentActivityUpdatedRow> updatedProducts = getRecentlyUpdatedProductsFor(buId);

        List<ProductFlyoutRecentActivityUpdatedRow> updatedProductsFilteredByContentType = updatedProducts
                .stream()
                .filter(addedProduct -> contentType.toString().equals(addedProduct.getContentType()))
                .collect(Collectors.toList());

        int numOfUpdatedProductsFilteredByContentType = updatedProductsFilteredByContentType.size();
        if (numOfUpdatedProductsFilteredByContentType == 0) {
            return 0;
        }

        if (numOfUpdatedProductsFilteredByContentType > 1) {
            throw new IllegalArgumentException("Expect single or zero value with '" + contentType + "', " +
                    "but got: " + numOfUpdatedProductsFilteredByContentType);
        }

        return updatedProductsFilteredByContentType.get(0).getUpdated();
    }

    private List<ProductFlyoutRecentActivityAddedRow> getRecentlyAddedProductsFor(int buId) {
        log.info("Get recently added products for '" + buId + "' business unit.");

        return given()
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .auth()
                .oauth2(getKeycloakAPIService().getSuperUserAccessToken())
                .when()
                .request()
                .baseUri(API_ROOT_PATH)
                .basePath(String.format(PRODUCTS_RECENTLY_ADDED_PATH_TEMPLATE, buId))
                .get()
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .body()
                .as(ProductFlyoutRecentActivityAddedResponse.class)
                .getRows();
    }

    private List<ProductFlyoutRecentActivityUpdatedRow> getRecentlyUpdatedProductsFor(int buId) {
        log.info("Get recently added products for '" + buId + "' business unit.");

        return given()
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .auth()
                .oauth2(getKeycloakAPIService().getSuperUserAccessToken())
                .when()
                .request()
                .baseUri(API_ROOT_PATH)
                .basePath(String.format(PRODUCTS_RECENTLY_UPDATED_PATH_TEMPLATE, buId))
                .get()
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .body()
                .as(ProductFlyoutRecentActivityUpdatedResponse.class)
                .getRows();
    }

    private List<List<String>> getProductSnapshotFor(int buId) {
        log.info("Get total number of products for '" + buId + "' business unit.");

        return given()
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .auth()
                .oauth2(getKeycloakAPIService().getSuperUserAccessToken())
                .when()
                .request()
                .baseUri(API_ROOT_PATH)
                .basePath(String.format(TOTAL_PRODUCTS_PATH_TEMPLATE, buId))
                .get()
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .body()
                .as(ProductFlyoutProductSnapshot.class)
                .getRows();
    }

    private boolean updateIngestedAlertValues(@NotNull String srvUrl) {
        log.info("Update ingested alert values for '" + srvUrl + "' server.");
        try {
            return given()
                    .when()
                    .request()
                    .baseUri(srvUrl)
                    .basePath(INGEST_ALERT_PATH)
                    .get()
                    .then()
                    .extract()
                    .statusCode() == HTTP_OK;
        } catch (Exception e) {
            log.warn("Ingested alert refresh call is unsuccessful for the server '" + srvUrl + "'; \nError message: '" + e.getMessage() + "'.");
            return false;
        }
    }

    private ProductsNotificationStatus getProductsNotificationStatus(@NotNull String buName) {
        log.info("Getting products notification status for BU: '" + buName + "'.");

        int buId = getTenantIDBy(buName);

        return given()
                .when()
                .request()
                .baseUri(TIME_BANDIT_URL)
                .basePath(PRODUCTS_NOTIFICATION_PATH + buId)
                .get()
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .as(ProductsNotificationStatus.class);
    }
}