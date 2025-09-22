package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.BusinessUnit;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.data.PredAddresses.AQA_BILLING_ADDR;
import static com.ccc.hv.qa.ui.data.PredAddresses.AQA_CONTACT_ADDR;
import static com.ccc.hv.qa.ui.enums.IngestMode.NORMAL;

/**
 * Created by R0manL on 04/08/20.
 */

public class PredBUs {
    private static final String PUBLISHER_ID = ENV_CONFIG.buPublisherId();
    private static final String PUBLISHER_PIN = ENV_CONFIG.buPublisherPin();
    private static final String USER_ID = ENV_CONFIG.buUserId();
    private static final String USER_PASS = ENV_CONFIG.buUserPass();

    private static final String TEST_BUSINESS_UNIT_NAME   = ENV_CONFIG.testBusinessUnitName();
    private static final String RECORD_SOURCE_NAME        = ENV_CONFIG.recordSourceName();
    private static final String ALPHA_CODE                = ENV_CONFIG.alphaCodePrefix();
    private static final String NOTIFICATION_EMAIL        = ENV_CONFIG.notificationEmail();

    private static final String TEST_BUSINESS_UNIT_NAME_2 = ENV_CONFIG.testBusinessUnitName2();
    private static final String RECORD_SOURCE_NAME_2      = ENV_CONFIG.recordSourceName2();
    private static final String ALPHA_CODE_2              = ENV_CONFIG.alphaCodePrefix2();


    public static final BusinessUnit PRED_AUTOMATION_BU = BusinessUnit.builder()
            .name(TEST_BUSINESS_UNIT_NAME)
            .recordSourceName(RECORD_SOURCE_NAME)
            .alphaCode(ALPHA_CODE)
            .useExpectedShipDate(false)
            .ingestMode(NORMAL)
            .distributionErrorNotificationEmail(NOTIFICATION_EMAIL)
            .profileContactInfo(AQA_CONTACT_ADDR)
            .billingContactInfo(AQA_BILLING_ADDR)
            .userId(USER_ID)
            .password(USER_PASS)
            .publisherId(PUBLISHER_ID)
            .publisherPin(PUBLISHER_PIN)
            .imageThreshold(7)
            .build();

    public static final BusinessUnit PRED_AUTOMATION_BU_2 = BusinessUnit.builder()
            .name(TEST_BUSINESS_UNIT_NAME_2)
            .recordSourceName(RECORD_SOURCE_NAME_2)
            .alphaCode(ALPHA_CODE_2)
            .useExpectedShipDate(false)
            .ingestMode(NORMAL)
            .distributionErrorNotificationEmail(NOTIFICATION_EMAIL)
            .profileContactInfo(AQA_CONTACT_ADDR)
            .billingContactInfo(AQA_BILLING_ADDR)
            .userId(USER_ID)
            .password(USER_PASS)
            .publisherId(PUBLISHER_ID)
            .publisherPin(PUBLISHER_PIN)
            .imageThreshold(7)
            .build();


    private PredBUs() {
        //NONE
    }
}
