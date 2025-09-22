package com.ccc.hv.qa.ui.data;


import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pojos.ChannelExclusive;
import com.ccc.hv.qa.ui.pojos.ChannelPublic;

import java.util.Arrays;

import static com.ccc.hv.qa.ui.data.DistributionSrvs.*;
import static com.ccc.hv.qa.ui.data.PredAddresses.AQA_CONTACT_ADDR;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;
import static java.time.LocalDate.now;

/**
 * Created by R0manL on 25/08/20.
 */

public class Channels {

    /**
     * Smoke test channels
     */
    private static final String UNIQUE_POSTFIX = generateUniqueStringBasedOnDate();

    public static final ChannelPublic smokePublicChannelFTP = ChannelPublic.builder()
            .name("Smoke_public_FTP_channel_" + UNIQUE_POSTFIX)
            .headquarter(Headquarter.US)
            .timeZone(TimeZone.CST)
            .retryInterval(RetryInterval.FOUR)
            .threshold(ChannelThreshold.ONE_HUNDRED)
            .distributeByAdvancedKeyword(true)
            .onixSalesOutletIDCode(OnixSalesOutletIdCode.AMZ)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelExclusive smokeExclusiveChannel = ChannelExclusive.builder()
            .name("Smoke_exclusive_channel_" + UNIQUE_POSTFIX)
            .timeZone(TimeZone.CST)
            .businessUnit(PredBUs.PRED_AUTOMATION_BU)
            .proprietarySchemeName(generateUniqueStringBasedOnDate("Test_SchemeName"))
            .proprietarySalesOutletID("Internal AMZ")
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive smokeExclusiveChannelSFTPWithNotAppliedOptimization = ChannelExclusive.builder()
            .name("Smoke_excl_sftp_channel_not_applied_opt_" + UNIQUE_POSTFIX)
            .businessUnit(PredBUs.PRED_AUTOMATION_BU)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive smokeExclusiveChannelSFTPWithAppliedOptimization = ChannelExclusive.builder()
            .name("Smoke_excl_sftp_channel_with_applied_opt_" + UNIQUE_POSTFIX)
            .businessUnit(PredBUs.PRED_AUTOMATION_BU)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic smokePublicChannelITMSWithOptimization = ChannelPublic.builder()
            .name("Smoke_public_ITMS_channel_with_optimization_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic smokePublicChannelSFTPForODD = ChannelPublic.builder()
            .name("Smoke_public_SFTP_channel_for_ODD_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic smokePublicChannelSFTPForODD2 = ChannelPublic.builder()
            .name("Smoke_public_SFTP_channel_for_ODD_2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic smokePublicChannelSFTPForProductTracking = ChannelPublic.builder()
            .name("Smoke_pub_SFTP_channel_for_product_tracking_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic smokePublicChannelSFTPForEpubWatermarking = ChannelPublic.builder()
            .name("Smoke_pub_SFTP_channel_for_epub_wtrmrk_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic smokePublicChannelSFTPForPdfWatermarking = ChannelPublic.builder()
            .name("Smoke_pub_SFTP_channel_for_pdf_wtrmrk_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic smokePublicChannelFTPSForBatchODD = ChannelPublic.builder()
            .name("Smoke_public_FTPS_channel_for_batch_ODD_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic smokePublicChannelSFTPForAudioDistribution = ChannelPublic.builder()
            .name("Smoke_public_SFTP_channel_for_audio_distribution_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic smokePublicChannelITMSForDistribution = ChannelPublic.builder()
            .name("Smoke_public_ITMS_channel_for_distribution_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic smokePublicChannelSFTPForODDONIX30 = ChannelPublic.builder()
            .name("Smoke_public_SFTP_channel_for_ODD_ONIX30" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic smokePublicChannelFTPSForBatchODDOnix30 = ChannelPublic.builder()
            .name("Smoke_public_FTPS_channel_for_batch_ODD_ONIX30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic smokePublicChannelFTPSForAutoDistr = ChannelPublic.builder()
            .name("Smoke_public_FTPS_channel_for_AutoDistr_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();
    /* end of smoke test channels */

    /**
     * Regression test channels
     */
    public static final ChannelPublic regressionPublicChannelFTPSForSingleODDValidationCheck = ChannelPublic.builder()
            .name("Regression_pub_FTPS_channel_single_ODD_check_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPAddElementNormRule = ChannelPublic.builder()
            .name("Regression_pub_SFTP_channel_add_el_norm_rule_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPSubstituteElementNormRule = ChannelPublic.builder()
            .name("Regression_pub_SFTP_channel_subs_el_norm_rule_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPRemoveElementNormRule = ChannelPublic.builder()
            .name("Regression_pub_SFTP_channel_rmv_el_norm_rule_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPNormOptRule21SendSingleOnixFile = ChannelPublic.builder()
            .name("Regression_pub_SFTP_cnl_norm_opt21_send_singl_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPNormOptMetadataActivityOnix21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_cnl_nor_opt_met_act_21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPNormOptMetadataActivityOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_cnl_nor_opt_met_act_30" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPNormOptFailOnix21MetadataActivity = ChannelPublic.builder()
            .name("Regression_pub_SFTP_cnl_norm_opt_fail_21_met_act_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPNormOptFailOnix30MetadataActivity = ChannelPublic.builder()
            .name("Regression_pub_SFTP_cnl_norm_opt_fail_30_met_act_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPNormOptFailedPath = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_norm_opt_fail_path_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPMultiplyNormOptRule = ChannelPublic.builder()
            .name("Regression_pub_SFTP_channel_mult_norm_rule_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPNormOptRule21GroupByPublisherAndImprint = ChannelPublic.builder()
            .name("Regression_pub_SFTP_norm_opt_21_grp_by_publ_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForSingleODDToMultipleChannelsPrint = ChannelPublic.builder()
            .name("Regression_pub_FTP_chnl_singODD_mult_chnl_Print_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionSortOrderForAssetStatusOnProductDetailsPageWithR = ChannelPublic.builder()
            .name("Regression_sort_order_asset_status_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionSortOrderForAssetStatusOnProductDetailsPageWithr = ChannelPublic.builder()
            .name("regression_sort_order_asset_status_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionSortOrderForAssetStatusOnProductDetailsPageWithA = ChannelPublic.builder()
            .name("Aregression_sort_order_asset_status_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionSortOrderForAssetStatusOnProductDetailsPageWitha = ChannelPublic.builder()
            .name("aregression_sort_order_asset_status_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionSortOrderForAssetStatusOnProductDetailsPageFailedDistrWithR = ChannelPublic.builder()
            .name("Regression_sort_order_asset_status_fd_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionSortOrderForAssetStatusOnProductDetailsPageFailedDistrWithr = ChannelPublic.builder()
            .name("regression_sort_order_asset_status_fd_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionSortOrderForAssetStatusOnProductDetailsPageFailedDistrWithA = ChannelPublic.builder()
            .name("Aregression_sort_order_asset_status_fd_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionSortOrderForAssetStatusOnProductDetailsPageFailedDistrWitha = ChannelPublic.builder()
            .name("aregression_sort_order_asset_status_fd_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForSingleODDToMultipleChannelsEpub = ChannelPublic.builder()
            .name("Regression_pub_FTP_chnl_singODD_mult_chnl_Epub_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistrContentWithOnSaleDateInPastForOnix21 = ChannelPublic.builder()
            .name("Regression_FTP_auto_distr_onsaledate_past_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistrContentWithOnSaleDateInPastForOnix30 = ChannelPublic.builder()
            .name("Regression_FTP_auto_distr_onsaledate_past_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistrContentWithOnSaleDateInFutureForOnix21 = ChannelPublic.builder()
            .name("Regression_FTP_auto_distr_onsaledate_future_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistrContentWithOnSaleDateInFutureForOnix30 = ChannelPublic.builder()
            .name("Regression_FTP_auto_distr_onsaledate_future_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForSingleODDToMixedAssets = ChannelPublic.builder()
            .name("Regression_pub_FTP_chnl_singODD_mix_assets_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForDistributionFailure = ChannelPublic.builder()
            .name("Regression_pub_FTP_chnl_Distr_failure_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelITMSForDistributionFailure = ChannelPublic.builder()
            .name("Regression_pub_ITMS_chnl_Distr_failure_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForDistributionFailure = ChannelPublic.builder()
            .retryInterval(RetryInterval.TWO)
            .name("Regression_pub_FTPS_chnl_Distr_failure_" + UNIQUE_POSTFIX)
            .distributionServer(regressionInvalidDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInPastAndOffsetOnix21 = ChannelPublic.builder()
            .name("Regression_FTP_autoDistr_onsale_offset_past_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInPastAndOnsetOnix21 = ChannelPublic.builder()
            .name("Regression_FTP_autoDistr_onsale_onset_past_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInPastAndOffsetOnix30 = ChannelPublic.builder()
            .name("Regression_FTP_autoDistr_onsale_offset_past_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInPastAndOnsetOnix30 = ChannelPublic.builder()
            .name("Regression_FTP_autoDistr_onsale_onset_past_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInFutureAndOffsetOnix21 = ChannelPublic.builder()
            .name("Regression_FTP_autoDistr_onsale_future_offset_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInFutureAndOnsetOnix21 = ChannelPublic.builder()
            .name("Regression_FTP_autoDistr_onsale_future_onset_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInFutureAndOffsetOnix30 = ChannelPublic.builder()
            .name("Regression_FTP_autoDistr_onsale_future_offset_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForAutoDistContCollatWithOnSaleDateInFutureAndOnsetOnix30 = ChannelPublic.builder()
            .name("Regression_FTP_autoDistr_onsale_future_onset_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForDistributionFailure = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_distr_failure_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForPublisherName = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_publisher_name_" + UNIQUE_POSTFIX)
            .distributeByAdvancedKeyword(true)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForImprintName = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_imprint_name_" + UNIQUE_POSTFIX)
            .distributeByAdvancedKeyword(true)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForSrvSettingsSendTriggerFile = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_send_trigger_file_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForSendSingleProductsInSingleOnixFile = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_send_single_onix_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForSendSingleProductsInSingleOnixFileOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_send_sngl_onx_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForSendMetadataWithEveryAssetContent = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_send_mtdt_with_ast_cnt_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForSendMetadataWithEveryAssetCollateral = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_send_mtdt_with_ast_clt_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForSendMetadataWithEveryAssetContentRevisionCheck = ChannelPublic.builder()
            .name("Regression_pub_SFTP_send_mtdt_with_ast_cnt_rev_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForSendMetadataWithEveryAssetCollateralRevisionCheck = ChannelPublic.builder()
            .name("Regression_pub_SFTP_send_mtdt_with_ast_clt_rev_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForGroupByPublisherImprint = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_group_by_pub_impt_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForOnixFeedDistrFailurePath = ChannelPublic.builder()
            .name("Regression_pub_FTP_chnl_for_onix_fd_distr_fail_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForOnixFeedDistributionSuccessPath = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_onix_feed_distr_scs_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForSingleODDDistributeOnHold = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_for_ODD_distr_on_hold_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionSendMetadataWithEveryAssetOnixNotSelected = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_onix_not_selected_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForSingleODDDistributeLocked = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_ODD_distr_on_locked_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();


    public static final ChannelPublic regressionPublicChannelSFTPForSrvSettingsSendTriggerFileFailure = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_send_trigger_file_fail_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForSelectChannelsPageCancel = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_select_chnl_cancel_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForAutoDistrWithOnSaleDateAndPublicationDate = ChannelPublic.builder()
            .name("Regression_pub_FTP_chnl_onsale_pub_date" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForConfirmDistributionPageCancel = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_confrm_distr_cancel_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForTokenReplacementOnDistribution1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_token_replacement_on_distr_1_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForTokenReplacementOnDistribution2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_token_replacement_on_distr_2_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForTokenReplacementOnDistribution3 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_token_replacement_on_distr_3_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForTokenReplacementOnDistribution4 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_token_replacement_on_distr_4_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForTokenReplacementOnDistribution5 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_token_replacement_on_distr_5_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForTokenReplacementOnDistribution6 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_token_replacement_on_distr_7_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForTokenReplacementOnDistribution7 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_token_replacement_on_distr_8_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForTokenReplacementOnDistribution8 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_token_replacement_on_distr_9_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForAutoDistrContentWithPublicationDateAndOffsetDateOnix21 = ChannelPublic.builder()
            .name("Regression_SFTP_auto_distr_pub_date_offset_onix21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForAutoDistrContentWithPublicationDateAndOnsetDateOnix30 = ChannelPublic.builder()
            .name("Regression_FTPS_auto_distr_pub_date_onset_onix30" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForFilteringByPubDateDefaultDateAndState = ChannelPublic.builder()
            .name("Regression_pub_FTP_chnl_fltr_pubDate_default_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForFilteringByPubDateDefaultDateAndAfterState = ChannelPublic.builder()
            .name("Regression_pub_FTP_chnl_fltr_pubDate_today_aftr_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForFilteringByPubDateBeforeInOnix = ChannelPublic.builder()
            .name("Regression_pub_FTP_fltr_pubDate_before_in_onix_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForFilteringByPubDateEqualsOnixPubDate = ChannelPublic.builder()
            .name("Regression_pub_FTP_fltr_pubDate_equal_onx_date_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForPublicationDateFilterBeforeWhenNoPubDateInOnix = ChannelPublic.builder()
            .name("Regression_pub_FTP_fltr_pubDate_bfr_no_pbDt_onx_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForPublicationDateFilterAfterWhenNoPubDateInOnix = ChannelPublic.builder()
            .name("Regression_pub_FTP_fltr_pubDate_aftr_no_pbDt_onx_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForFilteringByPubDateEqualsOnixPubDateAfterOption = ChannelPublic.builder()
            .name("Regression_pub_FTP_fltr_pubDate_eql_onx_dt_aftr_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForFilteringByPubDateIsAfterOnixPubDate = ChannelPublic.builder()
            .name("Regression_pub_FTP_fltr_pubDate_is_aftr_onx_date_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_pubDt_aftr_onx_dt_distr_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForFilteringByPubDateIsBeforeOnixPubDate = ChannelPublic.builder()
            .name("Regression_pub_FTP_fltr_pubDate_is_bftr_onx_date_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPSForSingleODDSelectAllChannels = ChannelPublic.builder()
            .name("Regression_pub_FTPS_single_ODD_select_all_chnls_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPubChannelFTPSForSingleODDMultipleAssetsDistributionToMultipleChannels = ChannelPublic.builder()
            .name("Regression_pub_FTPS_snglODD_distr_to_multi_chnls_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForSingleODDMultipleAssetsDistributionToMultipleChannels = ChannelPublic.builder()
            .name("Regression_pub_SFTP_snglODD_distr_to_multi_chnls_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForConfirmDistributionPageBack = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_for_confrm_distr_back_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForMultipleProductTypesAndAssetsDistributionByPaths = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_mult_product_asset_dist_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForSuccessDistributionBackOption = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_for_success_dist_back_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForBatchODDDistributeLocked = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_batch_ODD_distr_locked_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForAutoDistrLockedProduct = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_auto_distr_locked_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDContentCollateralDistrOnHold = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_cont_coll_pr_onhold_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic publicChannelFTPForAutoDistrProductOnHoldOnlyOnixHasDistributed = ChannelPublic.builder()
            .name("Regression_pub_FTP_auto_dstr_cont_coll_pr_onhold_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic publicChannelFTPForAutoDistrEnablingMarkExternalForNewChannelTrue = ChannelPublic.builder()
            .name("Regression_pub_FTP_auto_dstr_mark_ext_new_ch_tr_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic publicChannelFTPForAutoDistrEnablingMarkExternalForNewChannelFalse = ChannelPublic.builder()
            .name("Regression_pub_FTP_auto_dstr_mark_ext_new_ch_fls_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic publicChannelFTPForAutoDistrEnablingMarkExternalForEditedChannel = ChannelPublic.builder()
            .name("Regression_pub_FTP_auto_dstr_mark_ext_edt_ch_tr_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForTokenReplacementForOnixFeedDistXFER = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_tkn_replace_dstr_xfer_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForFilteringByPubDateSuccODDWithOptionAfterOneDayDiffInFuture = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_pub_dt_aftr_with_odd_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_pub_date_bfr_with_odd_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForTokenReplacementForOnixFeedDistUPD = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_tkn_replace_dstr_upd_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPForChannelUnlockAfterCAEditing = ChannelPublic.builder()
            .name("Regression_pub_FTP_chnl_unlock_after_ca_editing_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForTokenReplacementIgnoredForOnixFeedDistribution = ChannelPublic.builder()
            .name("Regression_pub_SFTP_token_replacement_ignored_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChaSFTPForPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode = ChannelPublic.builder()
            .name("Regression_pub_SFTP_pub_fltr_succ_disc_code_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicationDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode = ChannelPublic.builder()
            .name("Regression_pub_SFTP_pub_fltr_fail_discCode_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubDateFilterFailODDWithValidFilterAndInvalidDiscountCode = ChannelPublic.builder()
            .name("Regression_pub_SFTP_pub_fltr_invalid_discCode_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubDateFilterFailODDWithValidFilterAndMissedDiscountCode = ChannelPublic.builder()
            .name("Regression_pub_SFTP_pub_fltr_missed_discCode_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario1_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario2_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario3 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario3_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario4 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario4_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario5 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario5_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario6 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario6_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario7 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario7_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario8 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario8_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario9 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario9_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario10 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario10_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario11 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario11_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario12 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario12_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario13 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario13_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario14 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario14_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario15 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario15_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario16 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario16_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario17 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario17_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario18 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario18_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForBatchODDScenario19 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_batchODD_scenario19_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForNormalizationCSVFeed = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_norma_csv_feed_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForNormalizationLongToShort = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_norma_long_to_short_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForNormalization21To30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_norma_2_1_to_3_1_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionPublicChannelWithExistingChannelName = ChannelExclusive.builder()
            .businessUnit(PredBUs.PRED_AUTOMATION_BU)
            .name("Regression_pub_chnl_with_existing_name_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionPublicChannelWithEmptyBuSection = ChannelExclusive.builder()
            .name("Regression_pub_chnl_with_empty_bu_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForActivationDeactivationBySuperAdmin = ChannelPublic.builder()
            .name("Regression public super adm creation " + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionExclusiveChnlSFTPForActivationDeactivationByAccAdmin = ChannelExclusive.builder()
            .name("Regression exclusive acc admin creation " + UNIQUE_POSTFIX)
            .businessUnit(PredBUs.PRED_AUTOMATION_BU)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionExclusiveChnlSFTPForActivationDeactivationByMetaAdmin = ChannelExclusive.builder()
            .name("Regression exclusive meta admin creation " + UNIQUE_POSTFIX)
            .businessUnit(PredBUs.PRED_AUTOMATION_BU)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionPublicChannelCreateTagFromCreateCAPage = ChannelExclusive.builder()
            .name("Regression_pub_chnl_crt_tag_CA_pg_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelCreateTagFromEditCAPage = ChannelPublic.builder()
            .name("Regression_pub_chnl_crt_tag_edt_CA_pg_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForChannelErrorMsgCheck = ChannelPublic.builder()
            .name("Regression public channel error msg check 1 " + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForChannelErrorMsgCheck2 = ChannelPublic.builder()
            .name("Regression public channel error msg check 2 " + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionExclChannelSFTPForChannelCancel = ChannelExclusive.builder()
            .name("Regression excl channel cancel " + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionExclusiveChannelSFTPForChannelViewPageBySysAdmin = ChannelExclusive.builder()
            .name("Regression excl channel view page by Sys Admin" + UNIQUE_POSTFIX)
            .timeZone(TimeZone.UTC)
            .retryInterval(RetryInterval.EIGHT)
            .threshold(ChannelThreshold.TWO_HUNDRED)
            .headquarter(Headquarter.AD)
            .channelMarkets(Arrays.asList(Headquarter.AR, Headquarter.CA))
            .businessUnit(PredBUs.PRED_AUTOMATION_BU)
            .proprietarySchemeName(generateUniqueStringBasedOnDate("Test_AQA_"))
            .proprietarySalesOutletID("Internal AMZ")
            .distributionServer(smokeDistributionSrvSFTP)
            .distributionServer(smokeDistributionSrvFTP)
            .distributionServer(smokeDistributionSrvFTPS)
            .addContactInformation(true)
            .contactInformation(AQA_CONTACT_ADDR)
            .build();

    public static final ChannelExclusive regressionExclusiveChannelSFTPForChannelViewPageByAccAndMetadataAdmin = ChannelExclusive.builder()
            .name("Regression excl chnnl view page Met Acc Admin" + UNIQUE_POSTFIX)
            .timeZone(TimeZone.UTC)
            .retryInterval(RetryInterval.EIGHT)
            .threshold(ChannelThreshold.TWO_HUNDRED)
            .headquarter(Headquarter.AD)
            .channelMarkets(Arrays.asList(Headquarter.AR, Headquarter.CA))
            .businessUnit(PredBUs.PRED_AUTOMATION_BU)
            .proprietarySchemeName(generateUniqueStringBasedOnDate("Test_AQA_"))
            .proprietarySalesOutletID("Internal AMZ")
            .distributionServer(smokeDistributionSrvSFTP)
            .distributionServer(smokeDistributionSrvFTP)
            .distributionServer(smokeDistributionSrvFTPS)
            .addContactInformation(true)
            .contactInformation(AQA_CONTACT_ADDR)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt21FailedPath = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_21_fail_path_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt21FailedMetadataActivityPath = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_fl_met_act_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt30FailedMetadataActivityPath = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_fl_met_act_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt21FromLong21ToShort30AddSubstitute = ChannelPublic.builder()
            .name("Regression_pub_SFTP_opt21_lng_to_shrt_path_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt21Remove = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_21_remove_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOptMultiply = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_multiply_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOptMetadataActivityOnix21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_meta_act_21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOptMetadataActivityOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_meta_act_30" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithOptimization21 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_with_optimization_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForCASimpleCreationSuperAdmin = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_simple_CA_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionExclusiveChannelSFTPForCASimpleCreationAccAdm = ChannelExclusive.builder()
            .name("Regression excl SFTP CA creation by AccAdm" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionExclusiveChannelSFTPForCASimpleCreationMetaAdm = ChannelExclusive.builder()
            .name("Regression excl SFTP CA creation by MetaAdm" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForWatermarkingEnabledSuperAdmin = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_watermarking_en" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionExclusiveChannelSFTPForWatermarkingEnabledAccAdm = ChannelExclusive.builder()
            .name("Regression excl chnl watermark en (Acc Adm)" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelExclusive regressionExclusiveChannelSFTPForWatermarkingEnabledMetaAdm = ChannelExclusive.builder()
            .name("Regression excl chnl watermark en (Meta Adm)" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForWatermarkingDisabledSuperAdmin = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_watermarking_di" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForWatermarkingNonAllowedProductTypes = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_for_watermarking_non" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForWatermarkingAndEmptyPublisherID = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_watermarking_empt_pubID" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeSysAdmin = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_CA_edit_mode_sup_admn" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeAccountAdmin = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_CA_edit_mode_acc_admn" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeMetadataAdmin = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_CA_edit_mode_met_admn" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeSysAdminWarningMsg = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnlCA_edit_mode_sa_war_msg" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeAccountAdminWarningMsg = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnlCA_edit_mode_aa_war_msg" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeMetadataAdminWarningMsg = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnlCA_edit_mode_ma_war_msg" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeValidation = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_CA_edit_mode_valid" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeWatermarkingIsOff = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_CA_edit_mode_wtmk_off" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPubChnlSFTPForCATagFilteringOptionsAndCAIdWithWatermarkEnabled = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_tag_filtr_ca_id_enbl" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChnlSFTPForCATagFilteringOptionsAndCAIdWithWatermarkDisabled = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_tag_filtr_ca_id_disbl" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeSysAdmin2 = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_CA_edit_mode_sup_admn2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeAccountAdmin2 = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_CA_edit_mode_acc_admn2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPublicChannelFTPSForCAEditModeMetadataAdmin2 = ChannelPublic.builder()
            .name("Regression_pub_FTPS_chnl_CA_edit_mode_met_admn2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTPS)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForCAActivationDeactivation = ChannelPublic.builder()
            .name("Regression public verify CA activation " + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForCAFiltering = ChannelPublic.builder()
            .name("Regression public verify CA filtering " + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForCASearch = ChannelPublic.builder()
            .name("Regression public verify CA search" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForCASorting = ChannelPublic.builder()
            .name("Regression public verify CA sorting " + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForAutoDistrSendSingleProductsInSingleOnixFileOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_auto_send_sngl_onx_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForOnix30WhenBothNormaProfilesAreSetup = ChannelPublic.builder()
            .name("Regression_pub_SFTP_norm_rules_30_21_setup_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPAddElementNormRuleOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_channel_add_elem_onx30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPSubstituteElementNormRuleOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_channel_subst_elem_onx30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPRemoveElementNormRuleOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_channel_remove_elem_onx30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPMultiplyNormOptRuleOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_channel_mult_norm_onx30" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt30Add = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt30_add_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt30Remove = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt30_remove_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForOnix21WhenBothNormaProfilesAreSetup = ChannelPublic.builder()
            .name("Regression_pub_SFTP_norm_rules_30_21_setup2_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt30Substitute = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt30_subs_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForNormalizationLongToLongOnix21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_norma_lng_to_lng_21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForNormalizationShortToLongOnix21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_norma_shrt_to_lng_21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForNormalizationLongToLongOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_norma_lng_to_lng_30" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForNormalizationShortToLongOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_norma_shrt_to_lng_30" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPNormOptRule30SendSingleOnixFile = ChannelPublic.builder()
            .name("Regression_pub_SFTP_cnl_norm_opt30_send_singl_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPNormOptRule30GroupByPublisherAndImprint = ChannelPublic.builder()
            .name("Regression_pub_SFTP_norm_opt_30_grp_by_publ_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChnlSFTPOptNonMatchOnix21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_non_mtch_onx21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChnlSFTPOptNonMatchOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_non_mtch_onx30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt30FailedPathSyntaxError = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_30_fail_stx_er_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPOpt30FailedPathRemoveRecRef = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_30_fail_rmv_rr_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForOptConversion21To30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_2_1_to_3_1_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChnlSFTPForVerifyPrefixAndTitleElement = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_verify_title_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForChangingCompOnixElValOnix21Short = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_chng_comp_val_21_srt" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForChangingCompOnixElValOnix21Long = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_chng_comp_val_21_lng" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForChangingCompOnixElValOnix30Short = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_chng_comp_val_30_srt" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPForChangingCompOnixElValOnix30Long = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_chng_comp_val_30_lng" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithOptimization30AddReplace = ChannelPublic.builder()
            .name("Regression_pub_ITMS_with_opt_30_add_repl_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithOptimization30SubsReplace = ChannelPublic.builder()
            .name("Regression_pub_ITMS_with_opt_30_subs_repl_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithOptimization30RemoveReplace = ChannelPublic.builder()
            .name("Regression_pub_ITMS_with_opt_30_rmv_repl_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaRelaxNGValidation21 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_relaxng_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForRelaxNGValidation30 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_relaxng_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaOptProcessIssues21 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_opt_fail_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaOptProcessIssues30 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_opt_fail_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaInvalidOptProfile21 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_opt_issue_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaInvalidOptProfile30 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_opt_issue_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithOnix30CAOptionsOn = ChannelPublic.builder()
            .name("Regression_pub_ITMS_with_30_rules_on_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithOnix21CAOptionsOn = ChannelPublic.builder()
            .name("Regression_pub_ITMS_with_21_rules_on_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleConverterProcessPackagingIssue21 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_pkg_issue_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleConverterProcessPackagingIssue30 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_pkg_issue_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaTransportationDistrIssues21 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_distr_issue_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaTransportationDistrIssues30 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_distr_issue_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithAppleMetaBothValidOptProfiles = ChannelPublic.builder()
            .name("Regression_pub_ITMS_aplmeta_2_valid_opt_prof_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithAppleMetaBothInValidOptProfiles = ChannelPublic.builder()
            .name("Regression_pub_ITMS_aplmeta_2invalid_opt_prof_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaLinkWithOnixAndProductType21 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_onix21_mark_img_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaLinkWithOnixAndProductType30 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_onix30_epub_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithReleaseTypeNewReleaseOnix30 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_with_rel_t_new_release_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSWithReleaseTypeDigitalOnlyOnix30 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_with_rel_t_digital_only_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions1 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_conv_title_1_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChnlITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions2 = ChannelPublic.builder()
            .name("Regression_pub_ITMS_apl_meta_conv_title_2_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvITMS)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForDistributeWhenTagMatchForOnix21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_tag_match_onx_21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForDistributeWhenTagMatchForOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_tag_match_onx_30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForDistributeWhenTagDoesNotMatchForOnix21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_tag_not_match_onx_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByPublisherSuccPath1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_pub_onix21_s1" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByPublisherSuccPath2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_pub_onix30_s2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByPublisherFailPath1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_pub_onix21_f1" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByPublisherFailPath2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_pub_onix30_f2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByPublisherFailPath3 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_pub_onix21_f3" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByPublisherFailPath4 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_pub_onix30_f4" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelFTPForChannelFlyoutSection = ChannelPublic.builder()
            .name("Regression_FTP_channel_flyout_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPPartiallyFailOptOnix30 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_prt_fl_onx30_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPublicChannelSFTPPartiallyFailOptOnix21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_chnl_opt_prt_fl_onx21_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByAudienceCodeSuccPath1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_audcode_onx21_s1" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByAudienceCodeSuccPath2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_audcode_onx30_s2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByAudienceCodeFailPath1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_audcode_onix21_f1" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByAudienceCodeFailPath2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_audcode_onix30_f2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByAudienceCodeFailPath3 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_audcode_onix21_f3" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByAudienceCodeFailPath4 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_audcode_onix30_f4" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByTerritoryRightsSuccPath1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_ter_rghts_on21_s1" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByTerritoryRightsSuccPath2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_ter_rghts_onx30_s2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByTerritoryRightsFailPath1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_ter_rghts_onx21_f1" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByTerritoryRightsFailPath2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_ter_rghts_onx30_f2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintSuccPath11 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_onix21_11" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintSuccPath12 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_onix30_12" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintSuccPath21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_onix21_21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintSuccPath22 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_onix30_22" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintSuccPath31 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_onix21_31" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintSuccPath32 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_onix30_32" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintFailPath11 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_fail_11" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintFailPath12 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_fail_12" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintFailPath21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_fail_21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByImprintFailPath22 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_impr_fail_22" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath11 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_onix21_11" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath12 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_onix30_12" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_onix21_21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath22 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_onix30_22" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath31 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_onix21_31" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath32 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_onix30_32" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierFailPath11 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_fail_11" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierFailPath12 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_fail_12" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierFailPath21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_fail_21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterBySupplyIdentifierFailPath22 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_spID_fail_22" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByMultipleDistrRulesSuccPath1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_mult_dstr_on21_s1" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByMultipleDistrRulesSuccPath2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_mult_dstr_on30_s2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByMultipleDistrRulesFailPath1 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_mult_dst_rl_onx21_f1" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByMultipleDistrRulesFailPath2 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_by_mult_dst_rl_onx30_f2" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsSuccPath11 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p11" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsSuccPath12 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p12" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsSuccPath21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsSuccPath22 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_flt_bisac_sbj_p22" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsSuccPath31 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p31" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsSuccPath32 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p32" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsSuccPath41 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p41" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsSuccPath42 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p42" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsPassPath51 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p51" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsPassPath52 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p52" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsPassPath61 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p61" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsPassPath62 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_p62" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath11 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f11" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath12 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f12" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath21 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f21" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath22 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbjf22" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath31 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f31" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath32 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f32" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath61 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f61" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath62 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f62" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath71 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f71" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath72 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f72" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath81 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f81" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath82 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f82" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath91 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f91" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath92 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f92" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath101 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f101" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath102 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f102" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath111 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f111" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPFilterByBISACSubjectsFailPath112 = ChannelPublic.builder()
            .name("Regression_pub_SFTP_fltr_bisac_sbj_f112" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();
    public static final ChannelPublic regressionPubChannelSFTPForProductCardFilteringSomeFiltersMatch = ChannelPublic.builder()
            .name("Regression_pub_SFTP_pr_card_fltr_some_mtch" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForProductCardFilteringWithNoFiltersSetup = ChannelPublic.builder()
            .name("Regression_pub_SFTP_pr_card_no_fltr_set" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet = ChannelPublic.builder()
            .name("Regression_pub_SFTP_pr_card_all_match_p" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet = ChannelPublic.builder()
            .name("Regression_pub_SFTP_pr_card_all_match_f" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForProductCardFilteringWithSalesOutlet = ChannelPublic.builder()
            .name("Regression_pub_SFTP_pr_card_sales_outlet" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .onixSalesOutletIDCode(OnixSalesOutletIdCode.AMZ)
            .build();

    public static final ChannelPublic regressionPubChannelSFTPForAutoDistrOnlyContentAndCollateral = ChannelPublic.builder()
            .name("Regression_SFTP_auto_distr_only_con_coll_" + UNIQUE_POSTFIX)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();
    /* end of regression test channels */

    private Channels() {
        //NONE
    }
}
