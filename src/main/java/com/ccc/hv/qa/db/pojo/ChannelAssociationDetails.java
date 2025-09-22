package com.ccc.hv.qa.db.pojo;

import lombok.Value;

import java.sql.Timestamp;
import java.time.LocalDate;

@Value
public class ChannelAssociationDetails {
    int hvCaadetChaAssociationId;
    String notificationEmail;
    boolean activeYn;
    int hvChamasChannelMasterId;
    int hvAddmasAddressId;
    int hvTenmasTenantMasterId;
    boolean sysInactivated;
    int precompOperationCount;
    Integer hvUsrmasUserMasterId;
    Timestamp userLockUtc;
    boolean useAutomaticDistributionYn;
    Timestamp createDateUtc;
    boolean appleSendExplicitYn;
    int onixDistributionInterval;
    Timestamp nextOnixDistributionUtc;
    boolean useImprintNameYn;
    String optimizeProfileOnix21;
    String optimizeProfileOnix21Desc;
    boolean sendTriggerfileYn;
    boolean trackedYn;
    boolean autocorrectCoverYn;
    boolean autocorrectPriceYn;
    boolean autocorrectToonsaleYn;
    boolean autocorrectTonotonsaleYn;
    String takeDownEmail;
    boolean appleUseNewReleaseYn;
    int appleNewReleaseMonths;
    String distributionErrorEmail;
    Timestamp inactiveEmailSentUtc;
    String isbnFilterType;
    String isbnFilterTag;
    String onixVersion;
    String optimizeProfileOnix30;
    String optimizeProfileOnix30Desc;
    Timestamp publicationDateFilterValue;
    boolean publicationDateFilterActiveYn;
    boolean publicationDateFilterBeforeYn;

    public LocalDate getPublicationDateFilterValue(){
        return this.publicationDateFilterValue.toLocalDateTime().toLocalDate();
    }
}
