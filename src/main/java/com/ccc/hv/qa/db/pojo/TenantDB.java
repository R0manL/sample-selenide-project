package com.ccc.hv.qa.db.pojo;

import lombok.Value;

/**
 * Created by R0manL on 1/20/21.
 */

@Value
public class TenantDB {
    int hvTenmasTenantMasterId;
    int hvTntrefTenantTypeRefId;
    String name;
    String notificationEmail;
    String alphaCode;
    String recordSourceName;
    String releaseDateOffset;
    String uploadUsername;
    String uploadPasswordHash;
    boolean activeYn;
    Integer hvTenmasBillingAddressId;
    Integer hvTenmasPhysicalAddressId;
    Integer hvAccmasAccountMasterId;
    boolean useExpectedShipDateYn;
    boolean onboardYn;
    String normProfileOnix21;
    String normProfileOnix21Desc;
    String sisenseAccount;
    String tenmasDigimarcPublisherId;
    String tenmasDigimarcPublisherPin;
    Integer tenmasDigimarcImageThreshold;
    String normProfileOnix30;
    String normProfileOnix30Desc;
}
