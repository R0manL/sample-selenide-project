package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.Account;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;

/**
 * Created by R0manL on 04/08/20.
 */

public class PredAccounts {

    public static final Account AQA_ACCOUNT = Account.builder()
            .name(ENV_CONFIG.accountName())
            .useHrvDistribution(true)
            .useWatermarkAssets(true)
            .useMarketplaceMonitoring(true)
            .useMarketplaceComplianceData(true)
            .useConsumerEngagement(true)
            .useCompetitiveAnalytics(true)
            .competitorProducts(20)
            .useBuyBoxAnalysis(true)
            .userAutoCorrect(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();

    public static final Account AQA_ACCOUNT_2 = Account.builder()
            .name(ENV_CONFIG.accountName2())
            .useHrvDistribution(true)
            .useWatermarkAssets(false)
            .useMarketplaceMonitoring(true)
            .useMarketplaceComplianceData(true)
            .useConsumerEngagement(true)
            .useCompetitiveAnalytics(true)
            .competitorProducts(20)
            .useBuyBoxAnalysis(true)
            .userAutoCorrect(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();


    private PredAccounts() {
        //NONE
    }
}
