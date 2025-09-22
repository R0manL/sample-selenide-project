package com.ccc.hv.qa.db.pojo;

import lombok.Value;

@Value
public class ProductDB {
    int hvPrdmasProductMasterId;
    String isbn;
    String timeStamp;
    boolean onHold;
    int hvTenmasTenantMasterId;
    String releaseDateUtc;
    String title;
    String author;
    Long hvAstmasAssetMasterId;
    String externalId;
    boolean embargoedYn;
    int ingestErrCount;
    int distErrCount;
    String contentLastUpdateUtc;
    String metadataLastUpdateUtc;
    String collateralLastUpdateUtc;
    Integer hvAsstypAssetTypeId;
    String releaseDateType;
    Integer hvPrdtypProductTypeId;
    boolean markedForDeleteYn;
    boolean trackedYn;
}
