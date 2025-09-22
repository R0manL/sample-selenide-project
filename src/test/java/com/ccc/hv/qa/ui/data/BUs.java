package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.IngestMode;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import com.ccc.hv.qa.utils.StringUtils;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.data.Addresses.*;
import static com.ccc.hv.qa.ui.enums.IngestMode.PREVIOUSLY_DISTRIBUTED;
import static com.ccc.hv.qa.utils.EmailUtils.generateUniqueFakeEmailAddress;
import static com.ccc.hv.qa.utils.StringUtils.*;

/**
 * Created by R0manL on 20/08/20.
 */

public class BUs {

    /* Smoke business units */
    public static final BusinessUnit smokeBU = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Smoke-Test-BU-"))
            .recordSourceName(generateUniqueRecordSourceName())
            .useExpectedShipDate(true)
            .ingestMode(PREVIOUSLY_DISTRIBUTED)
            .distributionErrorNotificationEmail(generateUniqueFakeEmailAddress("buSmokeTest"))
            .profileContactInfo(smokeUserContactAddress)
            .billingContactInfo(smokeBillingAddress)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .publisherId(generate10UniqueNumbersBasedOnDate())
            .publisherPin(generate10UniqueNumbersBasedOnDate())
            .imageThreshold(5)
            .build();
    /* end of Smoke business units */

    /* Regression business units */
    public static final BusinessUnit testBU1 = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Verify creation BU 1"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_1_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit testBU2 = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Verify creation BU 2"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_2_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit testBU3 = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Verify creation BU 3"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_3_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit regressionBUWithParentAccAddr1 = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_BU_with_parent_acc_addr"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_par_acc_1_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .useParentAccountInfo(true)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit regressionBUWithParentAccAddr2 = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_BU_with_parent_acc_addr"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_par_acc_2_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .useParentAccountInfo(true)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit regressionBUWithParentAccAddr3 = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_BU_with_parent_acc_addr"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_par_acc_3_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .useParentAccountInfo(true)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit regressionCancelBUCreation = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Verify canceling BU creation"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_cancel_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit activateDeactivateBU = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Activate Deactivate Account"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_activate_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit allEmptyFieldsBU = BusinessUnit.builder()
            .name("")
            .recordSourceName("")
            .alphaCode("")
            .profileContactInfo(EMPTY_BILLING_INFO)
            .billingContactInfo(EMPTY_BILLING_INFO)
            .userId("")
            .password("")
            .build();

    public static final BusinessUnit buNameValidation = BusinessUnit.builder()
            .name(ENV_CONFIG.testBusinessUnitName())
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_name_val_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .useParentAccountInfo(true)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buDuplicateAlphaCodeValidation = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU_duplicate_code_check"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_dup_alpha_val_"))
            .alphaCode(ENV_CONFIG.alphaCodePrefix())
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .useParentAccountInfo(true)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buInvalidAlphaCodeValidation = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU_invalid_code_check"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE__inval_alpha_val_"))
            .alphaCode("QA")
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .useParentAccountInfo(true)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buWithEmptyBillingContactInfo = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU with empty billing info"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_empty_bill_"))
            .ingestMode(IngestMode.NORMAL)
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(EMPTY_BILLING_INFO)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buWithFulfilledPubPINAndPubID = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU with fulfilled Pub Pin Id"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_pub_pin_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .publisherId(generate10UniqueNumbersBasedOnDate())
            .publisherPin(generate10UniqueNumbersBasedOnDate())
            .imageThreshold(5)
            .build();

    public static final BusinessUnit buWithEmptyPubPINAndPubID = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU with empty Pub Pin Id"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_no_pun_pin_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .imageThreshold(5)
            .build();

    public static final BusinessUnit buWithEmptyPubPINAndPubIDWatermarkDisabled = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU with empty Pub Pin Id wtmrk disabled"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_wtmrk_dis_p_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit editWithEmptyProfileInfo = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Regression-Simple-Test-BU-"))
            .recordSourceName(generateUniqueRecordSourceName())
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit buSimpleEmptyBillingInfo = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Regression-Simple-Test-BU-"))
            .recordSourceName(generateUniqueRecordSourceName())
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit buWithEmptyPubPINAndPubIDWatermarkDisabledViewMode = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU with empty Pub Pin Id wtmrk viewMd "))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE__wtmrk_viewMd_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buCancelEditingOnBillingInfoTab = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Regression-Cancel-Edit-Test-"))
            .recordSourceName(generateUniqueRecordSourceName())
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit buCancelEditingOnProfileTab = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Regression-Cancel-Edit-Test-"))
            .recordSourceName(generateUniqueRecordSourceName())
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit buWithNormaRuleAndComment = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Regression-BU-norma-rule-comment"))
            .recordSourceName(generateUniqueRecordSourceName())
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit simpleBUTest = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Regression-Active-BU-"))
            .recordSourceName(generateUniqueRecordSourceName())
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit createTagFromManageBUsPageTest = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Regression-Create_tag_from-manage-BUs-"))
            .recordSourceName(generateUniqueRecordSourceName())
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit createTagFromCreateCATest = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Regression-Create_tag_from-create-CA-"))
            .recordSourceName(generateUniqueRecordSourceName())
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit createTagFromEditCATest = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("Regression-Create_tag_from-edit-CA-"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_tag-edit-CA"))
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit buWithDWatermarkDisabledCAEditSuperAdmin = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU wtmrk disabled ca edit spr adm"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_wtmrk_spr_adm_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buWithDWatermarkDisabledCAEditAccountAdmin = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU wtmrk disabled ca edit acc adm"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE__wtmrk_acc_adm_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buWithDWatermarkDisabledCAEditMetadataAdmin = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU wtmrk disabled ca edit mtd adm"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE__wtmrk_mtd_adm_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buWithDWatermarkDisabledCAEditSuperAdmin2 = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU wtmrk disabled ca edit spr adm 2 "))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE__wtmrk_spr_adm2_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buWithDWatermarkDisabledCAEditAccountAdmin2 = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU wtmrk disabled ca edit acc adm 2 "))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE__wtmrk_acc_adm2_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buWithDWatermarkDisabledCAEditMetadataAdmin2 = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU wtmrk disabled ca edit mtd adm 2 "))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE__wtmrk_mtd_adm2_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buWithWtrmrkIsNotFulFilledCreateCa = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("BU wtmrk disabled ca creation "))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE__wtmrk_dis_"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    public static final BusinessUnit buForHrvViewManagerTest = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("bu-for-hrvmngr-BU-"))
            .recordSourceName(generateUniqueStringBasedOnDate("REC_SOURCE_bu-mngr_"))
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit buForHrvAccountManagerTest = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("bu-for-accMng-BU-"))
            .recordSourceName(generateUniqueStringBasedOnDate("REC_SOURCE_bu-accMng_"))
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit buForHrvTenantUserTest = BusinessUnit.builder()
            .name(generateUniqueStringBasedOnDate("bu-for-tntUser-BU-"))
            .recordSourceName(generateUniqueStringBasedOnDate("REC_SOURCE_bu-tnt_"))
            .profileContactInfo(simpleContactInfo)
            .billingContactInfo(simpleContactInfo)
            .userId(generate10UniqueNumbersBasedOnDate())
            .password(generate10UniqueNumbersBasedOnDate())
            .build();

    public static final BusinessUnit regressionBUWithoutAnalytics = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Regr_BU_without_analytics"))
            .recordSourceName(StringUtils.generateUniqueStringBasedOnDate("REC_SOURCE_analytics"))
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .useParentAccountInfo(true)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();

    private static final String secondBUForAQAAccountAlphaCode = generateUniqueAlphaCode();
    public static final BusinessUnit secondBUForAQAAccount = BusinessUnit.builder()
            .name(StringUtils.generateUniqueStringBasedOnDate("Second_BU_for_AQA_Acc"))
            .recordSourceName(secondBUForAQAAccountAlphaCode)
            .alphaCode(secondBUForAQAAccountAlphaCode)
            .ingestMode(IngestMode.NORMAL)
            .distributionErrorNotificationEmail(ENV_CONFIG.notificationEmail())
            .profileContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .billingContactInfo(PredAddresses.AQA_BILLING_ADDR)
            .userId(StringUtils.generateUniqueStringBasedOnDate("AQA"))
            .password(StringUtils.generateUniqueStringBasedOnDate("AQA_PASS_"))
            .build();
    /* end of Regression business units */


    private BUs() {
        //NONE
    }
}
