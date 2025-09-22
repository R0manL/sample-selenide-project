package com.ccc.hv.qa.db.pojo;

import lombok.Value;

import java.sql.Timestamp;

/**
 * Created by R0manL on 02/11/20.
 */

@Value
public class AssetDistrStatusDetailsDB {
    String distributedPath;
    int hvAtsrefAssetStatusRefId;
    String processingErrors;
    Timestamp timeStamp;
    String sourceName;
}
