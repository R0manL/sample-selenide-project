package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.WorkflowDAO;
import com.ccc.hv.qa.db.pojo.WorkflowAssetStatusDetailsDB;
import com.ccc.hv.qa.db.pojo.WorkflowProductStatusDetailsDB;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import static com.ccc.hv.qa.db.services.AssetDBService.getAssetIdBy;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAIDBy;
import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;
import static com.ccc.hv.qa.db.services.ProductDBService.getProductIDBy;

/**
 * Created by R0manL on 6/7/22.
 */

public class WorkflowDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private WorkflowDBService() {
        // NONE
    }

    public static WorkflowAssetStatusDetailsDB getWorkflowAssetStatusDetailFor(@NotNull String assetFileName, @NotNull String channelName) {
        log.debug("Getting workflow asset status details for: '" + assetFileName + "' asset and '" + channelName + "' channel.");

        Integer assetId = getAssetIdBy(assetFileName);
        Objects.requireNonNull(assetId, "Can't find asset by file name: '" + assetFileName + "'.");

        int caId = getCAIDBy(channelName);

        return createConnection()
                .onDemand(WorkflowDAO.class)
                .getWorkflowAssetStatusDetailsFor(caId, assetId);
    }

    public static WorkflowProductStatusDetailsDB getWorkflowProductStatusDetailsFor(@NotNull String productTitle, @NotNull String channelName) {
        log.debug("Getting workflow product status details for: '" + productTitle + "' product and '" + channelName + "' channel.");
        int productId = getProductIDBy(productTitle);
        int caId = getCAIDBy(channelName);

        return createConnection()
                .onDemand(WorkflowDAO.class)
                .getWorkflowProductStatusDetailsFor(caId, productId);
    }
}
