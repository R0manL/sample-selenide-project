package com.ccc.hv.qa.db.pojo;

import lombok.Value;

@Value
public class PhaseTrackMasterDB {
    int hvPhaseTrackMasterId;
    int hvBatchTrackMasterId;
    String phase;
    int targetCount;
    int successCount;
    int failCount;
    int skipCount;
    String status;
    String startUtc;
    String endUtc;
}
