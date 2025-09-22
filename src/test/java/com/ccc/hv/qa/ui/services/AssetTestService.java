package com.ccc.hv.qa.ui.services;

import com.ccc.hv.qa.db.dao.AssetDAO;
import com.ccc.hv.qa.db.enums.AssetDistributionStatus;
import com.ccc.hv.qa.db.enums.AssetWatermarkPhase;
import com.ccc.hv.qa.db.enums.AssetWatermarkStatus;
import com.ccc.hv.qa.db.pojo.AssetDistrStatusDetailsDB;
import com.ccc.hv.qa.db.pojo.AssetPrecompDetailsDB;
import com.ccc.hv.qa.db.services.ChannelAssociationDBService;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.file.pojo.ServerCredentials;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ccc.hv.qa.api.services.ChannelAssociationAPIService.getChannelAssociationAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.AssetDBService.*;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAServerIDBy;
import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;
import static com.ccc.hv.qa.ui.services.FileTestService.getFileTestService;
import static com.ccc.hv.qa.utils.FileOpsUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Created by R0manL on 11/3/20.
 */

public class AssetTestService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final long SCHEDULED_DISTRIBUTION_POLL_DELAY = 60;
    private static final long SCHEDULED_DISTRIBUTION_TIMEOUT = 70;
    //Note. Max value = 40second, with bigger value tests may fail with TIMEOUT exception when execute on Bamboo.
    private static final long DISTRIBUTION_MAX_TIMEOUT = ENV_CONFIG.awaitilityTimeout() * 20;

    protected Path filePath;


    protected AssetTestService() {
        // NONE
    }

    protected AssetTestService(@NotNull String relFilePath) {
        this.filePath = getAbsoluteResourceFilePath(Paths.get(relFilePath));
    }

    public AssetTestService cloneFileWith(@NotNull String newRecordReference) {
        String newFileName = generateFileNameWith(newRecordReference, this.filePath);
        this.filePath = cloneFile(newFileName, this.filePath.toString());

        return this;
    }

    public AssetTestService uploadToCrushFTP(ServerCredentials creds) {
        getFileTestService().uploadFileToCrushFTPRootFolder(this.filePath, creds);

        return this;
    }

    public Path getFilePath() {
        return this.filePath;
    }

    @NotNull
    public String getFileName() {
        return getFilePath().getFileName().toString();
    }

    @NotNull
    public AssetDistrStatusDetailsDB getDistrStatusDetailsWhenExistsTo(@NotNull String channelName) {
        final String assetFileName = this.getFileName();
        log.debug("Getting '" + assetFileName + "' asset distribution to '" + channelName + "' channel status details when exists.");
        AssetDistrStatusDetailsDB[] result = new AssetDistrStatusDetailsDB[1];

        await().atMost(DISTRIBUTION_MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<AssetDistrStatusDetailsDB> statuses = createConnection()
                            .onDemand(AssetDAO.class)
                            .getAssetDistributionStatusDetails(channelName, assetFileName);

                    if (statuses.size() == 1) {
                        result[0] = statuses.get(0);
                    }
                    if (statuses.size() > 1) {
                        throw new IllegalArgumentException("Expect single row, but have received more. " +
                                "Check if your product has unique title, record reference number and asset name. " +
                                "Results: " + Arrays.stream(result).toString());
                    }

                    assertThat(statuses)
                            .as("No asset's distribution status details for " +
                                    "channel: '" + channelName + "', asset: '" + assetFileName + "'." +
                                    "In case the expected result is Distribution Failure - " +
                                    "maybe you have too many products that are scheduled for distribution.")
                            .isNotEmpty();
                });

        return result[0];
    }

    public void waitOnSuccessfullyMultipleDistrStatusDetailsTo(@NotNull String channelName) {
        final String assetFileName = this.getFileName();
        log.debug("Getting '" + assetFileName + "' asset distribution to '" + channelName + "' channel status details when exists.");
        AssetDistrStatusDetailsDB[] result = new AssetDistrStatusDetailsDB[1];

        await().atMost(DISTRIBUTION_MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<AssetDistrStatusDetailsDB> statuses = createConnection()
                            .onDemand(AssetDAO.class)
                            .getAssetDistributionStatusDetails(channelName, assetFileName);

                    assertThat(statuses)
                            .as("No asset's distribution status details for " +
                                    "channel: '" + channelName + "', asset: '" + assetFileName + "'." +
                                    "In case the expected result is Distribution Failure - " +
                                    "maybe you have too many products that are scheduled for distribution.")
                            .isNotEmpty();
                });

    }

    public boolean isDistrStatusDetailsExist(@NotNull String channelName) {
        final String assetFileName = this.getFileName();
        log.debug("Is '" + assetFileName + "' asset distributed to '" + channelName);

        List<AssetDistrStatusDetailsDB> statuses = createConnection()
                .onDemand(AssetDAO.class)
                .getAssetDistributionStatusDetails(channelName, assetFileName);

        return !statuses.isEmpty();
    }

    public void checkIsNotDistributedTo(@NotNull String channelName) {
        log.info("Check is '" + this.getFileName() + "' asset not scheduled for distribution to '" + channelName);
        await().pollDelay(SCHEDULED_DISTRIBUTION_POLL_DELAY, TimeUnit.SECONDS).timeout(SCHEDULED_DISTRIBUTION_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() ->
                        assertThat(this.isDistributionScheduledFor(channelName))
                                .as("The record appears in precompdd table is unexpected.")
                                .isFalse());

        assertThat(isDistrStatusDetailsExist(channelName))
                .as("Asset " + this.getFileName() +
                        " distribution scheduling to channel " + channelName + " is unexpected")
                .isFalse();
    }

    public AssetDistrStatusDetailsDB getSuccessDistrStatusDetailsTo(@NotNull String channelName) {
        AssetDistrStatusDetailsDB result = getDistrStatusDetailsWhenExistsTo(channelName);

        assertThat(getDistributionStatusFrom(result))
                .as("'" + result.getSourceName() + "' asset has not successfully distributed to '" + channelName
                        + "'\nErrors:." + result.getProcessingErrors())
                .isEqualTo(AssetDistributionStatus.DISTRIB_SUCCESS);

        return result;
    }

    public AssetDistrStatusDetailsDB getSuccessExternalDistrStatusDetailsTo(@NotNull String channelName) {
        AssetDistrStatusDetailsDB result = getExternalDistrStatusDetailsWhenExistsTo(channelName);

        assertThat(getDistributionStatusFrom(result))
                .as("'" + result.getSourceName() + "' asset has not successfully externally distributed to '" + channelName
                        + "'\nErrors:." + result.getProcessingErrors())
                .isEqualTo(AssetDistributionStatus.DISTRIB_SUCCESS);

        return result;
    }

    public AssetDistrStatusDetailsDB getSuccessDistrStatusDetailsOfSecondVersionTo(@NotNull String channelName) {
        final int secondVersionNum = 2;
        AssetDistrStatusDetailsDB result = getDistrStatusDetailsWhenExistsTo(channelName, secondVersionNum);

        assertThat(getDistributionStatusFrom(result))
                .as("'" + result.getSourceName() + "' asset has not successfully distributed to '" + channelName
                        + "'\nErrors:." + result.getProcessingErrors())
                .isEqualTo(AssetDistributionStatus.DISTRIB_SUCCESS);

        return result;
    }

    public void waitOnSuccessfulDistributionTo(@NotNull String channelName) {
        getSuccessDistrStatusDetailsTo(channelName);
    }

    public void waitOnSuccessfulExternalDistributionTo(@NotNull String channelName) {
        getSuccessExternalDistrStatusDetailsTo(channelName);
    }

    public void waitOnSuccessfulDistributionOfSecondVersionTo(@NotNull String channelName) {
        getSuccessDistrStatusDetailsOfSecondVersionTo(channelName);
    }

    public AssetDistributionStatus getDistrStatusWhenExistsFor(@NotNull String channelName) {
        log.debug("Get asset distribution status to '" + channelName + "' channel.");
        AssetDistrStatusDetailsDB assetDistributionStatusDetails = getDistrStatusDetailsWhenExistsTo(channelName);

        return getDistributionStatusFrom(assetDistributionStatusDetails);
    }

    public AssetDistributionStatus getDistrStatusFrom(@NotNull AssetDistrStatusDetailsDB assetDistrStatusDetails) {
        log.debug("Get asset distribution status value from asset distribution status details.");

        return AssetDistributionStatus.getValueBy(assetDistrStatusDetails.getHvAtsrefAssetStatusRefId());
    }

    @NotNull
    public String getDistrPathFor(@NotNull String channelName) {
        return getSuccessDistrStatusDetailsTo(channelName).getDistributedPath();
    }

    @NotNull
    public AssetPrecompDetailsDB getPrecompDetailsWhenExistFor(@NotNull String channelName) {
        final String assetFileName = getFileName();
        log.debug("Getting '" + channelName + "' asset precomp details when exists for '" + assetFileName + "' channel.");
        AssetPrecompDetailsDB[] result = new AssetPrecompDetailsDB[1];

        await().untilAsserted(() -> {
            AssetPrecompDetailsDB details = getAssetPrecompDetailsFor(channelName, assetFileName);
            if (details != null) {
                result[0] = details;
            }
            assertThat(details).as("No asset precomp details record has been found for " +
                    "asset: '" + assetFileName + "' and channel: '" + channelName + "'.").isNotNull();
        });

        return result[0];
    }

    public void waitOnWatermarkingCompletionFor(@NotNull String channelName) {
        AssetWatermarkStatus expectedStatus = AssetWatermarkStatus.WATERMARKING;
        String assetFileName = getFileName();
        log.debug("Wait on '" + assetFileName + "' asset's prepackage status: '" + expectedStatus + "'.");

        int assetId = getAssetIdWhenExistsBy();
        int channelServerAssocId = getCAServerIDBy(channelName);
        await().atMost(ENV_CONFIG.awaitilityTimeout() * 3, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    boolean status = createConnection()
                            .onDemand(AssetDAO.class)
                            .hasAssetPrePackageStatus(channelServerAssocId, assetId, AssetWatermarkStatus.WATERMARKING.toString());

                    assertThat(status)
                            .as("No asset pre package status has been found.")
                            .isTrue();
                });

        log.debug("Wait on watermarking completion.");
        int assetDetailId = getChannelServerAssocIDsBy(channelName, assetId);
        waitOnWatermarkJobHistoryTableRecordFor(assetDetailId, AssetWatermarkStatus.READY, AssetWatermarkPhase.CREATE);
        waitOnWatermarkJobHistoryTableRecordFor(assetDetailId, AssetWatermarkStatus.READY, AssetWatermarkPhase.UPLOAD);
        waitOnWatermarkJobHistoryTableRecordFor(assetDetailId, AssetWatermarkStatus.READY, AssetWatermarkPhase.EMBED);
        waitOnWatermarkJobHistoryTableRecordFor(assetDetailId, AssetWatermarkStatus.SUCCESS, AssetWatermarkPhase.DOWNLOAD);
        waitOnWatermarkJobHistoryTableRecordFor(assetDetailId, AssetWatermarkStatus.SUCCESS, AssetWatermarkPhase.DELETE);
    }

    public boolean isDistributionScheduledFor(@NotNull String channelName) {
        String assetFileName = this.getFileName();
        log.debug("Check if distribution scheduled '" + assetFileName + "' asset to channel '" + channelName + "'.");

        return getAssetPrecompDetailsFor(channelName, assetFileName) != null;
    }

    private AssetDistributionStatus getDistributionStatusFrom(@NotNull AssetDistrStatusDetailsDB assetDistrStatusDetailsDB) {
        return AssetDistributionStatus.getValueBy(assetDistrStatusDetailsDB.getHvAtsrefAssetStatusRefId());
    }

    private int getAssetIdWhenExistsBy() {
        String assetFileName = getFileName();
        int[] result = new int[1];
        await().untilAsserted(() -> {
            Integer id = getAssetIdBy(assetFileName);
            if (id != null) {
                result[0] = id;
            }

            assertThat(id).as("No id for '" + assetFileName + "' has been found.").isNotNull();
        });

        return result[0];
    }

    private void waitOnWatermarkJobHistoryTableRecordFor(int assetDetailId,
                                                         AssetWatermarkStatus status,
                                                         AssetWatermarkPhase phase) {
        log.debug("Wait on watermark job history table has a record with assetDetailId: '" + assetDetailId + "', " +
                "status: '" + status + "', " +
                "phase: '" + phase + "'");
        await().atMost(ENV_CONFIG.awaitilityTimeout() * 3, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(isRecordWithStatusAndPhaseExistInWatermarkJobHistoryTable(assetDetailId, status, phase))
                        .as("No records found in watermark job history table.")
                        .isTrue());
    }

    /**
     * Getting asset's distribution status details when it appears on DB.
     *
     * @param channelName - unique channel's name
     * @param version     - version (revision) of the asset
     * @return - asset's status details information
     */
    @NotNull
    private AssetDistrStatusDetailsDB getDistrStatusDetailsWhenExistsTo(@NotNull String channelName, int version) {
        final String assetFileName = this.getFileName();
        log.debug("Getting '" + assetFileName + "' asset distribution to '" + channelName + "' channel status details when exists.");

        AssetDistrStatusDetailsDB[] result = new AssetDistrStatusDetailsDB[1];
        await().atMost(DISTRIBUTION_MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    result[0] = createConnection()
                            .onDemand(AssetDAO.class)
                            .getAssetDistributionStatusDetails(channelName, assetFileName, version);

                    assertThat(result[0]).isNotNull();
                });

        return result[0];
    }

    @NotNull
    public AssetDistrStatusDetailsDB getDistrStatusDetailsForPackingWhenExistsTo(@NotNull String channelName) {
        final String assetFileName = this.getFileName();
        log.debug("Getting '" + assetFileName + "' asset distribution to '" + channelName + "' channel status details when exists.");
        AssetDistrStatusDetailsDB[] result = new AssetDistrStatusDetailsDB[1];

        await().atMost(DISTRIBUTION_MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<AssetDistrStatusDetailsDB> statuses = createConnection()
                            .onDemand(AssetDAO.class)
                            .getAssetDistributionStatusDetailsForPacking(channelName, assetFileName);

                    if (statuses.size() == 1) {
                        result[0] = statuses.get(0);
                    }
                    if (statuses.size() > 1) {
                        throw new IllegalArgumentException("Expect single row, but have received more. " +
                                "Check if your product has unique title, record reference number and asset name. " +
                                "Results: " + Arrays.stream(result).toString());
                    }

                    assertThat(statuses)
                            .as("No asset's distribution status details for " +
                                    "channel: '" + channelName + "', asset: '" + assetFileName + "'." +
                                    "In case the expected result is Distribution Failure - " +
                                    "maybe you have too many products that are scheduled for distribution.")
                            .isNotEmpty();
                });

        return result[0];
    }

    @NotNull
    public AssetDistrStatusDetailsDB getExternalDistrStatusDetailsWhenExistsTo(@NotNull String channelName) {
        final String assetFileName = this.getFileName();
        log.debug("Getting '" + assetFileName + "' asset external distribution to '" + channelName + "' channel status details when exists.");
        AssetDistrStatusDetailsDB[] result = new AssetDistrStatusDetailsDB[1];

        await().atMost(DISTRIBUTION_MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<AssetDistrStatusDetailsDB> statuses = createConnection()
                            .onDemand(AssetDAO.class)
                            .getAssetExternalDistributionStatusDetails(channelName, assetFileName);

                    if (statuses.size() == 1) {
                        result[0] = statuses.get(0);
                    }
                    if (statuses.size() > 1) {
                        throw new IllegalArgumentException("Expect single row, but have received more. " +
                                "Check if your product has unique title, record reference number and asset name. " +
                                "Results: " + Arrays.stream(result).toString());
                    }

                    assertThat(statuses)
                            .as("No asset's external distribution status details for " +
                                    "channel: '" + channelName + "', asset: '" + assetFileName + "'." +
                                    "In case the expected result is Distribution Failure - " +
                                    "maybe you have too many products that are scheduled for distribution.")
                            .isNotEmpty();
                });

        return result[0];
    }

    public void updateOnixDistributionDateAndIntervalForCA() {
        List<Integer> channelAssocIds = getChannelServerAssocIDsBy(getAssetIdBy(this.getFileName())).stream()
                .map(ChannelAssociationDBService::getChannelAssociationIDByServerId)
                .collect(Collectors.toList());

        if (!channelAssocIds.isEmpty()) {
            channelAssocIds.forEach(channelAssocId -> {
                final int onixDistributionInterval = 0;
                getChannelAssociationAPIService().setOnixDistributionInterval(channelAssocId, onixDistributionInterval);
                getChannelAssociationAPIService().setNextOnixDistributionUtcToNow(channelAssocId);
            });
        } else {
            log.info("No scheduled distributions in precompdd table for asset " + this.getFileName());
        }
    }
}
