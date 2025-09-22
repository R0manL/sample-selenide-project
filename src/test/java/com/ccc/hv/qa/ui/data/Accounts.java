package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.Account;
import com.ccc.hv.qa.utils.StringUtils;

import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;

/**
 * Created by R0manL on 20/08/20.
 */

public class Accounts {


    /* Regression */
    public static final Account ACCOUNT_WITH_WATERMARK_DISABLED = Account.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_acc_with_disabled_watermark"))
            .useHrvDistribution(true)
            .useWatermarkAssets(false)
            .useMarketplaceMonitoring(true)
            .useMarketplaceComplianceData(true)
            .useConsumerEngagement(true)
            .useCompetitiveAnalytics(true)
            .competitorProducts(20)
            .useBuyBoxAnalysis(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();

    public static final Account AQA_CREATE_ACCOUNT_WITH_DEFAULT_SETTINGS = Account.builder()
            .name(generateUniqueStringBasedOnDate("Automation-createAccountTest1-"))
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR_TEST1)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR_TEST1)
            .build();

    public static final Account AQA_CREATE_ACCOUNT_WITH_SELECTED_DISRT_OPTIONS = Account.builder()
            .name(generateUniqueStringBasedOnDate("Automation-createAccountTest2-"))
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR_TEST2)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR_TEST2)
            .build();

    public static final Account AQA_CREATE_ACCOUNT_WITH_UNCHECKED_DISTR_OPTIONS = Account.builder()
            .name(generateUniqueStringBasedOnDate("Automation-createAccountTest3-"))
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR_TEST3)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR_TEST3)
            .build();

    public static final Account AQA_EDIT_ACCOUNT_WITH_DEFAULT_SETTINGS = Account.builder()
            .name(generateUniqueStringBasedOnDate("Automation-editAccountTest1-"))
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR_EDIT_ACCOUNT_TEST1)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR_EDIT_ACCOUNT_TEST1)
            .build();

    public static final Account AQA_EDIT_ACCOUNT_WITH_UNCHECKED_DISTR_OPTIONS = Account.builder()
            .name(generateUniqueStringBasedOnDate("Automation-editAccountTest2-"))
            .useHrvDistribution(true)
            .useWatermarkAssets(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR_EDIT_ACCOUNT_TEST2)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR_EDIT_ACCOUNT_TEST2)
            .build();

    public static final Account AQA_ACTIVATED_INACTIVATED_TEST_ACCOUNT = Account.builder()
            .name(generateUniqueStringBasedOnDate("Automation-activateDeactivate-"))
            .contactAddress(PredAddresses.AQA_CONTACT_ACTIVATE_DEACTIVATE_TEST)
            .billingAddress(PredAddresses.AQA_BILLING_ACTIVATE_DEACTIVATE_TEST)
            .build();

    public static final Account ACCOUNT_WITH_WATERMARK_DISABLED_BU_EDIT_TEST = Account.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_acc_with_disabled_watermark_BU_edit_"))
            .useHrvDistribution(true)
            .useWatermarkAssets(false)
            .useMarketplaceMonitoring(true)
            .useMarketplaceComplianceData(true)
            .useConsumerEngagement(true)
            .useCompetitiveAnalytics(true)
            .competitorProducts(20)
            .useBuyBoxAnalysis(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();

    public static final Account ACCOUNT_WITH_WATERMARK_DISABLED_BU_VIEW_TEST = Account.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_acc_with_disabled_watermark_BU_view_"))
            .useHrvDistribution(true)
            .useWatermarkAssets(false)
            .useMarketplaceMonitoring(true)
            .useMarketplaceComplianceData(true)
            .useConsumerEngagement(true)
            .useCompetitiveAnalytics(true)
            .competitorProducts(20)
            .useBuyBoxAnalysis(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();

    public static final Account ACCOUNT_WITHOUT_ANALYTICS_TEST = Account.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_acc_without_analytics_"))
            .useHrvDistribution(true)
            .useWatermarkAssets(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();
    public static final Account ACCOUNT_FOR_ADD_EVENTS_ON_TEST = Account.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_add_acc_wth_evnts_on_"))
            .useMarketplaceMonitoring(true)
            .useEvents(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();

    public static final Account ACCOUNT_FOR_ADD_EVENTS_OFF_TEST = Account.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_add_acc_wth_evnts_off_"))
            .useMarketplaceMonitoring(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();

    public static final Account ACCOUNT_FOR_EDIT_EVENTS_ON_TEST = Account.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_edt_acc_wth_evnts_on_"))
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();

    public static final Account ACCOUNT_FOR_EDIT_EVENTS_OFF_TEST = Account.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_edt_acc_wth_evnts_off_"))
            .useMarketplaceMonitoring(true)
            .useEvents(true)
            .contactAddress(PredAddresses.AQA_CONTACT_ADDR)
            .billingAddress(PredAddresses.AQA_BILLING_ADDR)
            .build();

    /* end of Regression */

    private Accounts() {
        //NONE
    }
}
