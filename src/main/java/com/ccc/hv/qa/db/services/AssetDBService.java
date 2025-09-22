package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.AssetDAO;
import com.ccc.hv.qa.db.enums.AssetAction;
import com.ccc.hv.qa.db.enums.AssetWatermarkPhase;
import com.ccc.hv.qa.db.enums.AssetWatermarkStatus;
import com.ccc.hv.qa.db.pojo.AssetPrecompDetailsDB;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAServerIDBy;
import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;
import static com.ccc.hv.qa.db.services.ProductDBService.getProductIDBy;

public class AssetDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private AssetDBService() {
        //NONE
    }

    @NotNull
    public static AssetAction getAssetActionStatus(@NotNull String assetFileName, @NotNull String productTitle) {
        log.debug("Get action status for asset with '" + assetFileName + "' filename related to '" + productTitle + "' product.");
        int productId = getProductIDBy(productTitle);

        String actionValue = createConnection()
                .onDemand(AssetDAO.class)
                .getAssetActionStatus(productId, assetFileName);

        return AssetAction.getActionBy(actionValue);
    }

    @NotNull
    public static String getFirstAddedTimeStamp(@NotNull String assetFileName, int productId) {
        log.debug("Get first added timestamp for '" + assetFileName + "' asset related to product (ID='" + productId + "').");

        return createConnection()
                .onDemand(AssetDAO.class)
                .getFirstAddedTimeStamp(productId, assetFileName);
    }

    @NotNull
    public static String getLastRevTimeStamp(@NotNull String assetFileName,  int productId) {
        log.debug("Get Last Revision timestamp for '" + assetFileName + "' asset.");

        return createConnection()
                .onDemand(AssetDAO.class)
                .getLastRevisedTimeStamp(productId, assetFileName);
    }

    public static int getAssetTypeIdBy(@NotNull String assetType) {
        log.debug("Get id of assetType '" + assetType + "' ");
        return createConnection()
                .onDemand(AssetDAO.class)
                .getAssetTypeIdBy(assetType);
    }

    @Nullable
    public static AssetPrecompDetailsDB getAssetPrecompDetailsFor(@NotNull String channelName, @NotNull String assetFileName) {
        log.debug("Get '" + assetFileName + "' asset precomp details for '" + channelName + "' channel.");

        return createConnection()
                .onDemand(AssetDAO.class)
                .getAssetPrecompDetailsFor(channelName, assetFileName);
    }

    @Nullable
    public static Integer getAssetIdBy(@NotNull String assetFileName) {
        log.debug("Get asset id by '" + assetFileName + "' asset name.");
        return createConnection()
                .onDemand(AssetDAO.class)
                .getAssetIdBy(assetFileName);
    }

    public static boolean isRecordWithStatusAndPhaseExistInWatermarkJobHistoryTable(int assetDetailId,
                                                                                     @NotNull AssetWatermarkStatus status,
                                                                                     @NotNull AssetWatermarkPhase phase) {
        log.debug("Check if asset with assetDetailId: '" + assetDetailId + "', status: '" + status + "'" +
                " and phase: '" + phase + "' exists.");
        return createConnection()
                .onDemand(AssetDAO.class)
                .isRecordWithStatusAndPhaseExistInWatermarkJobHistoryTable(assetDetailId, status.toString(), phase.toString());
    }

    public static int getChannelServerAssocIDsBy(@NotNull String channelName, int assetId) {
        log.debug("Getting asset detail id for '" + channelName + "' channel and asset with ID: '" + assetId + "'.");
        int channelServerAssocId = getCAServerIDBy(channelName);

        return createConnection()
                .onDemand(AssetDAO.class)
                .getAssetDetailId(channelServerAssocId, assetId);
    }

    public static List<Integer> getChannelServerAssocIDsBy(int assetId) {
        log.debug("Getting channel association servers ids of scheduled asset with ID: '" + assetId + "'.");

        return createConnection()
                .onDemand(AssetDAO.class)
                .getChannelServerAssocIDsBy(assetId);
    }
}