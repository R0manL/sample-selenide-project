package com.ccc.hv.qa.db.pojo;

import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by R0manL on 1/20/21.
 */

@Value
public class AssetPrecompDetailsDB {
    int hvPrecddPrecompddId;
    int hvAstmasAssetMasterId;
    int hvTenmasTenantMasterId;
    int hvAsstypAssetTypeId;
    Timestamp releaseDateUtc;
    int deliveryOffset;
    Timestamp preComputedDateUtc;
    Timestamp inFlightTimeUtc;
    int priority;
    int hvCsadetChaServerAssocId;
    Integer hvUsrmasUserMasterId;
    int distFailureCount;
    String workflowStatus;
    String releaseDateType;
    String distributionType;
    int hvPrdtypProductTypeId;
    Integer workerGroupingId;

    @NotNull
    public LocalDateTime getReleaseDateUtc() {
        return this.releaseDateUtc.toLocalDateTime();
    }

    @NotNull
    public LocalDateTime getPreComputedDateUtc() {
        return this.preComputedDateUtc.toLocalDateTime();
    }
}
