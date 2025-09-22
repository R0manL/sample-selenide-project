package com.ccc.hv.qa.db.pojo;

import lombok.Value;

import java.sql.Timestamp;

/**
 * Created by R0manL on 1/20/21.
 */

@Value
public class AccountDB {
    int hvAccmasAccountMasterId;
    String accountName;
    boolean activeYn;
    Timestamp createDateUtc;
    int hvAccmasBillingAddressId;
    int hvAccmasPhysicalAddressId;
    String code;
    boolean hrvViewEnabledYn;
    boolean hrvViewComplianceYn;
    boolean hrvViewEngagementYn;
    boolean distributionEnabledYn;
    boolean autocorrectEnabledYn;
    boolean syntheticsEnabledYn;
    int syntheticsLimit;
    boolean eventsEnabledYn;
    boolean buyboxEnabledYn;
    String sisenseAccount;
    boolean accmasWatermarkEnabledYn;
}
