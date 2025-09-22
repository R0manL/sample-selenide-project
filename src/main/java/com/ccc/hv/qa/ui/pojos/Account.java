package com.ccc.hv.qa.ui.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by R0manL on 04/08/20.
 */

@Getter
@Builder
public class Account {
    @NonNull
    private final String name;
    private final boolean useHrvDistribution;
    private final boolean useWatermarkAssets;
    private final boolean useMarketplaceMonitoring;
    private final boolean useMarketplaceComplianceData;
    private final boolean useConsumerEngagement;
    private final boolean useCompetitiveAnalytics;
    private final int competitorProducts;
    private final boolean useBuyBoxAnalysis;
    private final boolean useEvents;
    private final boolean userAutoCorrect;
    @NonNull
    private final Address contactAddress;
    @NonNull
    private final Address billingAddress;
}
