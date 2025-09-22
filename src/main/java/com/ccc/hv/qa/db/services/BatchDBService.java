package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.BatchDAO;
import com.ccc.hv.qa.db.enums.BatchStatus;
import com.ccc.hv.qa.db.pojo.PhaseTrackMasterDB;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.MetadataActivityPhase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAServerIDBy;
import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;
import static com.ccc.hv.qa.db.services.TenantDBService.getTenantIDBy;


public class BatchDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private BatchDBService() {
        //NONE
    }

    public static boolean isAnyBatchRecordExistFor(@NotNull String originalFileName) {
        log.debug("Check if any (success/fail) batch record exists for original file name: '" + originalFileName + "'.");
        return createConnection()
                .onDemand(BatchDAO.class)
                .isAnyBatchRecordExistFor(originalFileName);
    }

    public static boolean isBatchMasterIdExistsFor(@NotNull String originalFileName) {
        log.debug("Check if batch record (with ID) exists for original file name: '" + originalFileName + "'.");
        return createConnection()
                .onDemand(BatchDAO.class)
                .isBatchMasterIdExistsFor(originalFileName);
    }

    public static int getBatchIdFor(@NotNull String originalFileName) {
        log.debug("Getting batch master id by original file name: '" + originalFileName);
        return createConnection()
                .onDemand(BatchDAO.class)
                .getBatchMasterIdBy(originalFileName);
    }

    public static BatchStatus getBatchStatusFor(@NotNull String originalFileName) {
        log.debug("Getting batch status by original file name: '" + originalFileName);
        int batchStatusID = createConnection()
                .onDemand(BatchDAO.class)
                .getBatchStatusIdBy(originalFileName);

        return BatchStatus.getValueBy(batchStatusID);
    }

    @Nullable
    public static String getBatchValidationErrorFor(@NotNull String originalFileName) {
        log.debug("Getting batch validation errors by original file name: '" + originalFileName);
        int batchId = getBatchIdFor(originalFileName);

        return createConnection()
                .onDemand(BatchDAO.class)
                .getValidationErrorBy(batchId);
    }

    public static int getNormalizeBatchIdFor(@NotNull String originalFileName) {
        log.debug("Getting normalize batch id by original file name: '" + originalFileName);
        int batchId = getBatchIdFor(originalFileName);

        return createConnection()
                .onDemand(BatchDAO.class)
                .getNormalizeBatchIdBy(batchId);
    }
    public static List<Integer> getBatchIdListFor(@NotNull String originalFileName) {
        log.debug("Getting all batch master id by original file name: '" + originalFileName);
        return createConnection()
                .onDemand(BatchDAO.class)
                .getBatchMasterIdListBy(originalFileName);
    }

    public static int getBatchTrackMasterIdFor(@NotNull String originalFileName) {
        log.debug("Getting batch track master id by original file name: '" + originalFileName);
        int batchId = getBatchIdFor(originalFileName);

        return createConnection()
                .onDemand(BatchDAO.class)
                .getBatchTrackMasterIdBy(batchId);
    }

    public static int getBatchTrackMasterIdFor(int batchId) {
        log.debug("Getting batch track master id by '" + batchId + "' batch id.");

        return createConnection()
                .onDemand(BatchDAO.class)
                .getBatchTrackMasterIdBy(batchId);
    }

    public static PhaseTrackMasterDB getPhaseTrackMasterFor(@NotNull String originalFileName, @NotNull MetadataActivityPhase phase) {
        log.debug("Getting track master phase by '" + originalFileName + "' original file name.");
        int batchTrackMasterId = getBatchTrackMasterIdFor(originalFileName);

        return createConnection()
                .onDemand(BatchDAO.class)
                .getPhaseTrackMasterIdBy(batchTrackMasterId, phase.getText());
    }

    public static PhaseTrackMasterDB getPhaseTrackMasterFor(int batchId, @NotNull MetadataActivityPhase phase) {
        log.debug("Getting track master phase by '" + batchId + "' batch id.");
        int batchTrackMasterId = getBatchTrackMasterIdFor(batchId);

        return createConnection()
                .onDemand(BatchDAO.class)
                .getPhaseTrackMasterIdBy(batchTrackMasterId, phase.getText());
    }

    @Nullable
    public static String getBatchOddValidationMsgFor(@NotNull String channelName) {
        log.debug("Getting batch odd validation message asset distributed to '" + channelName + "' channel.");
        int chaSrvAssocId = getCAServerIDBy(channelName);

        return createConnection()
                .onDemand(BatchDAO.class)
                .getBatchOddValidationMsgBy(chaSrvAssocId);
    }

    public static int getBatchMasterIDBy(@NotNull String tenantName, @NotNull String originalFileName) {
        log.debug("Getting batch id for tenant: '" + tenantName + "', onix: '" + originalFileName + "'.");
        int tenantID = getTenantIDBy(tenantName);

        return createConnection()
                .onDemand(BatchDAO.class)
                .getBatchMasterIDBy(tenantID, originalFileName);
    }
}