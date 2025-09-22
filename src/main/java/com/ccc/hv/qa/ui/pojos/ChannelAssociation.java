package com.ccc.hv.qa.ui.pojos;

import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by R0manL on 25/08/20.
 */

@Getter
@SuperBuilder
public class ChannelAssociation {
    @Singular private final List<AdvancedKeywordSetting> advancedKeywordSettings;
    private final boolean automatedDistributionRules;
    private final boolean preventOfDistributionOfCurrentAssetVersions;
    private final boolean useOnix30ToDetermineDistrRules;
    private final DistributionRule distributionRule;
    private final boolean sendTriggerFile;
    @Singular private final List<AssociationServer> associationServers;
    private final Address contactInformation;
    @Singular private final List<String> distributionNotificationRecipients;
    @Singular private final List<String> takeDownNotificationRecipients;
    @Singular private final List<LocalDate> blackoutDates;
    private final String comment;
}
