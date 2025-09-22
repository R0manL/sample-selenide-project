package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.AssociationServerFTP;
import com.ccc.hv.qa.ui.pojos.AssociationServerFTPS;
import com.ccc.hv.qa.ui.pojos.AssociationServerITMS;
import com.ccc.hv.qa.ui.pojos.AssociationServerSFTP;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.data.ChannelAssociationProductTypes.*;
import static com.ccc.hv.qa.ui.data.MetadataDistributionOptions.*;

/**
 * Created by R0manL on 16/09/20.
 * {name} must be taken from appropriate Distribution server.
 */

public class AssociationSrvs {
    private static final String FTP_SRV_USERNAME = ENV_CONFIG.associationServerFTPUsername();
    private static final String FTP_SRV_PASSWORD = ENV_CONFIG.associationServerFTPPassword();

    private static final String SFTP_SRV_USERNAME = ENV_CONFIG.associationServerSFTPUsername();
    private static final String SFTP_SRV_PASSWORD = ENV_CONFIG.associationServerSFTPPassword();

    private static final String FTPS_SRV_USERNAME = ENV_CONFIG.associationServerFTPSUsername();
    private static final String FTPS_SRV_PASSWORD = ENV_CONFIG.associationServerFTPSPassword();

    private static final String ITMS_USERNAME = ENV_CONFIG.associationServerITMSUsername();
    private static final String ITMS_PASSWORD = ENV_CONFIG.associationServerITMSPassword();
    private static final String ITMS_PROVIDER = ENV_CONFIG.associationServerITMSProvider();


    /**
     * Smoke test set
     */
    public static final AssociationServerFTP smokeAssociationSrvFTP = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(smokeChannelAssociationProductTypeSrvFTP)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP smokeAssociationSrvSFTP = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(smokeChannelAssociationProductTypeSrvSFTP)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS smokeAssociationSrvFTPS = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(smokeChannelAssociationProductTypeSrvFTPS)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerITMS smokeAssociationSrvITMS = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(smokeChannelAssociationProductTypeSrvITMS)
            .build();

    public static final AssociationServerSFTP smokeAssociationSrvSFTPWithOptimization = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvSFTPWithOptimization)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerITMS smokeAssociationSrvITMSWithOptimization = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(smokeAssociationProductTypeSrvITMSWithOptimization)
            .build();

    public static final AssociationServerSFTP smokeAssociationSrvSFTPForODD = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvSFTPForODD)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP smokeAssociationSrvSFTPForProductTracking = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvSFTPForProductTracking)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP smokeAssociationSrvSFTPForEpubWatermarking = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvSFTPForEpubWatermarking)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP smokeAssociationSrvSFTPForPdfWatermarking = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvSFTPForPdfWatermarking)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS smokeAssociationSrvFTPSForBatchODD = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvFTPSForBatchODD)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP smokeAssociationSrvSFTPForAudioDistribution = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvSFTPForAudioDistribution)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerITMS smokeAssociationSrvITMSForDistribution = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(smokeAssociationProductTypeSrvITMSForDistribution)
            .build();

    public static final AssociationServerSFTP smokeAssociationSrvSFTPForODDONIX30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvSFTPForODDONIX30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS smokeAssociationSrvFTPSForBatchODDOnix30 = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvFTPSForBatchODDOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS smokeAssociationSrvFTPSForAutoDistr = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(smokeAssociationProductTypeSrvFTPSForAutoDistr)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();
    /* end of smoke test set */

    /**
     * Regression test set
     */
    public static final AssociationServerFTPS regressionAssociationSrvFTPSForSingleODDValidationCheck = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForSingleODDValidationCheck)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForAddElementNormRule = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForAddElementNormRule)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSubstituteElementNormRule = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSubstituteElementNormRule)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForRemoveElementNormRule = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForRemoveElementNormRule)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptNormRule21SendSingleOnix = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptNormRule21SendSingleOnix)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormOptMetadataActivityOnix21 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForNormOptMetadataActivityOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormOptMetadataActivityOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForNormOptMetadataActivityOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormOptFailOnix21MetadataActivity = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForNormOptFailOnix21MetadataActivity)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormOptFailOnix30MetadataActivity = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForNormOptFailOnix30MetadataActivity)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptNormFailedPath = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptNormFailedPath)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForMultiplyNormOptRule = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForMultiplyNormOptRule)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptNorm21GroupByPublisherAndImprint = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptNormRule21GroupByPublisherAndImprint)
            .metadataDistributionOption(groupByPublisherImprint)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContentWithOnSaleDateInPastForOnix21 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContentWithOnSaleDateInPastForOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContentWithOnSaleDateInPastForOnix30 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContentWithOnSaleDateInPastForOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContentWithOnSaleDateInFutureForOnix21 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContentWithOnSaleDateInFutureForOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContentWithOnSaleDateInFutureForOnix30 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContentWithOnSaleDateInFutureForOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForSingleODDToMultipleChannelsPrint = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForSingleODDToMultipleChannelsPrint)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionSortOrderForAssetStatusOnProductDetailsPage = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationSortOrderForAssetStatusOnProductDetailsPage)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();
    public static final AssociationServerFTP regressionAssociationSrvFTPForSingleODDToMultipleChannelsEpub = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForSingleODDToMultipleChannelsEpub)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForSingleODDToMixedAssets = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForSingleODDToMixedAssets)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForDistributionFailure = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username("Invalid")
            .password("Invalid")
            .caProductType(regressionAssociationProductTypeSrvFTPForDistributionFailure)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForDistributionFailure = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider("Invalid")
            .caProductType(regressionAssociationProductTypeSrvITMSForDistributionFailure)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPSForDistributionFailure = AssociationServerFTP.builder()
            .name(DistributionSrvs.regressionInvalidDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForDistributionFailure)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateAndOffsetInPastForOnix21 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateAndOffsetInPastForOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateAndOnsetInPastForOnix21 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateAndOnsetInPastForOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateAndOffsetInPastForOnix30 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateAndOffsetInPastForOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateAndOnsetInPastForOnix30 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateAndOnsetInPastForOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOffsetOnix21 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssocProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOffsetOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOnsetOnix21 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOnsetOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOffsetOnix30 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOffsetOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOnsetOnix30 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOnsetOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForDistributionFailure = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username("Invalid")
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForDistributionFailure)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForPublisherName = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForPublisherName)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvFTPForSrvSettingsSendTriggerFile = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForSrvSettingsSendTriggerFile)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSendMetadataWithEveryAssetContent = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetContent)
            .metadataDistributionOption(sendMetadataWithEveryAsset)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSendMetadataWithEveryAssetCollateral = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetCollateral)
            .metadataDistributionOption(sendMetadataWithEveryAsset)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSendMetadataWithEveryAssetContentRevisionCheck = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetContentRevisionCheck)
            .metadataDistributionOption(sendMetadataWithEveryAsset)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSendMetadataWithEveryAssetCollateralRevisionCheck = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetCollateralRevisionCheck)
            .metadataDistributionOption(sendMetadataWithEveryAsset)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSendMetadataWithEveryAssetCollateralOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetCollateralOnix30)
            .metadataDistributionOption(sendMetadataWithEveryAsset)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSendSingleProductsInSingleOnixFile = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSendSingleProductsInSingleOnixFile)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSendSingleProductsInSingleOnixFileOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSendSingleProductsInSingleOnixFileOnix30)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForImprintName = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForImprintName)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForGroupByPublisherImprint = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForGroupByPublisherImprint)
            .metadataDistributionOption(groupByPublisherImprint)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForSingleODDDistrOnHold = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForSingleODDDistrOnHold)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForOnixFeedDistrFailurePath = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username("Invalid")
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeFTPForOnixFeedDistrFailurePath)
            .metadataDistributionOption(groupByPublisherImprint)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOnixFeedDistributionSuccessPath = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForOnixFeedDistributionSuccessPath)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSendMetadataWithEveryAssetOnixNotSelected = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSSendMetadataWithEveryAssetOnixNotSelected)
            .metadataDistributionOption(sendMetadataWithEveryAsset)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSingleODDDistrLocked = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSingleODDDistrLocked)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSrvSettingsSendTriggerFileFailure = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password("Invalid")
            .caProductType(regressionAssociationProductTypeSrvSFTPSettingsSendTriggerFileFailure)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForSelectChannelsPageCancel = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForSelectChannelsPageCancel)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForAutoDistrWithOnSaleDateAndPublicationDate = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForAutoDistrWithOnSaleDateAndPublicationDate)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForConfirmDistributionPageCancel = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForConfirmDistributionPageCancel)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution0 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution0)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution2)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution3 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution3)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution4 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution4)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution5 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution5)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution6 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution6)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution7 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution7)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution8 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution8)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementOnDistribution9 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution9)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvSFTPForAutoDistrContentWithPublicationDateAndOffsetDateOnix21 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProductTypeSrvSFTPForAutoDistrContentWithPublicationDateAndOffsetDateOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPSForAutoDistrContentWithPublicationDateAndOnsetDateOnix30 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssocProductTypeSrvFTPSForAutoDistrContentWithPublicationDateAndOnsetDateOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForFilteringByPubDateDefaultDateAndState = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForFilteringByPubDateDefaultDateAndState)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForFilteringByPubDateDefaultDateAndAfterState = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForFilteringByPubDateBeforeInOnix = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForFilteringByPubDateBeforeInOnix)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForFilteringByPubDateEqualsOnixDate = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForFilteringByPubDateEqualsOnixDate)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForFilteringByPubDateBeforeNoPubDateInOnix = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForFilteringByPubDateBeforeNoPubDateInOnix)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForFilteringByPubDateAfterNoPubDateInOnix = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForFilteringByPubDateAfterNoPubDateInOnix)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForFilteringByPubDateEqualsOnixDateAfterOption = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForFilteringByPubDateEqualsOnixDateAfterOption)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForFilteringByPubDateIsAfterOnixPubDate = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForFilteringByPubDateIsAfterOnixPubDate)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForFilteringByPubDateIsBeforeOnixPubDate = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForFilteringByPubDateIsBeforeOnixPubDate)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForSingleODDSelectAllChannels = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForSingleODDSelectAllChannels)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForSingleODDMultipleAssetsDistributionToMultipleChannels = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvForSingleODDMultipleAssetsDistrToMultiChnls)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSingleODDMultipleAssetsDistributionToMultipleChannels = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvForSingleODDMultipleAssetsDistrToMultiChnls)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForConfirmDistributionPageBack = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForConfirmDistributionPageBack)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForMultipleProductTypesAndAssetsDistributionByPaths = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForMultipleProductTypesAndAssetsDistributionByPathsEPUB)
            .caProductType(regressionAssociationProductTypeSrvFTPSForMultipleProductTypesAndAssetsDistributionByPathsPRINT)
            .caProductType(regressionAssociationProductTypeSrvFTPSForMultipleProductTypesAndAssetsDistributionByPathsPDF)
            .caProductType(regressionAssociationProductTypeSrvFTPSForMultipleProductTypesAndAssetsDistributionByPathsMOBI)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForSuccessDistributionBackOption = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForSuccessDistributionBackOption)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForBatchODDDistrLocked = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForBatchODDDistrLocked)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForAutoDistrLockedProduct = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPSForAutoDistrLockedProduct)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDContentCollateralDistrOnHold = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDContentCollateralDistrOnHold)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrProductOnHoldOnlyOnixHasDistributed = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvFTPForAutoDistrProductOnHoldOnlyOnixHasDistributed)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrEnablingMarkExternalForNewChannelTrue = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvFTPForAutoDistrEnablingMarkExternalForNewChannelTrue)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrEnablingMarkExternalForNewChannelFalse = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvFTPForAutoDistrEnablingMarkExternalForNewChannelFalse)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForAutoDistrEnablingMarkExternalForEditChannel = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvFTPForAutoDistrEnablingMarkExternalForEditChannel)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();
    public static final AssociationServerSFTP regressionAssociationSrvSFTPForTokenReplacementForOnixFeedDistXFER = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForTokenReplacementForOnixFeedDistXFER)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilteringByPubDateSuccODDWithOptionAfterOneDayDiffInFuture = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForFilteringByPubDateSuccODDWithOptionAftereOneDayDiffInFuture)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForTokenReplacementForOnixFeedDistUPD = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForTokenReplacementForOnixFeedDistUPD)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForChannelUnlockAfterCAEditing = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvFTPForChannelUnlockAfterCAEditingUpdate = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForChannelUnlockAfterCAEditing)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForTokenReplacementIgnoredForOnixFeedDistribution = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForTokenReplacementIgnoredForOnixFeedDistribution)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForPubDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForPubDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForPubDateFilterFailODDWithValidFilterAndInvalidDiscountCode = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForPubDateFilterFailODDWithValidFilterAndInvalidDiscountCode)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForPubDateFilterFailODDWithValidFilterAndMissedDiscountCode = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForPubDateFilterFailODDWithValidFilterAndMissedDiscountCode)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario3 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario3)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario4 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario4)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario5 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario5)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario6 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario6)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario7 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario7)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario8 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario8)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario9 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario9)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario10 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario10)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario11 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario11)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario12 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario12)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario13 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario13)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario14 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario14)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario15 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario15)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario16 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario16)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario17 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario17)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario18 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario18)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForBatchODDScenario19 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForBatchODDScenario19)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormalizationCSVFeed = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForForNormalizationCSVFeed)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormalizationLongToShort = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForForNormalizationLongToShort)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormalization21To31 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForForNormalization21To31)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForActivationDeactivation = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForCreateTagFromEditCAPage = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForCreateTagFromEditCAPage)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt21FeedFailedPath = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt21FailedPath)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt21FeedFailedMetadataActivityPath = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt21FailedMetadataActivityPath)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt30FeedFailedMetadataActivityPath = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt30FailedMetadataActivityPath)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt21FromLong21ToShort30AddSubstitute = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt21FromLong21ToShort30AddSubstitute)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt21Remove = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt21Remove)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptMultiply = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptMultiply)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptMetadataActivityOnix21 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptMetadataActivityOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptMetadataActivityOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptMetadataActivityOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSWithOptimization21 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSWithOptimization21)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSimpleCACreation = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForWatermarkingOption = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarking)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarking2)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarking3)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingEPUB)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingEnhancedEPUB)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingEnhancedFixedEPUB)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingFixedLayoutEPUB)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingEPUBAlternate)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingSearchPDF)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingNookPagePerfectPDF)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingPrint)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForWatermarkingNonAllowedProductTypes = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingMobipocket)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingFixedLayoutKindle)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingIBook)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingEnhancedMobipocket)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingFixedLayoutePIB)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingEnhancedFixedePIB)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingPubXML)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingAudio)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingAudioCassette)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingCDAudio)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingDownloadableAudioFile)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingCDROM)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingEbook)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingCalendar)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingCards)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingDiary)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingKit)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingPostcardBook)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingWalletOrFolder)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingStickers)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingOtherPrintedItem)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingDVDVideo)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingMixedMediaProduct)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingMultipleCopyPack)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingCounterpack)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingJigsaw)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingOtherMerchandise)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingKindle)
            .caProductType(regressionAssocProdTypeSrvSFTPForWatermarkingGame)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForWatermarkAndPublisherID = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .build();
    /** end of regression test set */

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForCAEditModeInitial = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForEditCAInitial)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForCAEditModeUpdated = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username("Updated")
            .password("Updated")
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForCAEditModeUpdatedValidation = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username("")
            .password("")
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForCAEditModeInitialValidation = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForCAEditModeWatermarkOff = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForEditCAWatermarkOffPdf)
            .caProductType(regressionAssociationProductTypeSrvSFTPForEditCAWatermarkOffEpub)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTPS regressionAssociationSrvFTPSForCAEditBUWatermarkSettings = AssociationServerFTPS.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTPS.getName())
            .username(FTPS_SRV_USERNAME)
            .password(FTPS_SRV_PASSWORD)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForAutoSendSingleProductsInSingleOnixFileOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForAutoSendSingleProductsInSingleOnixFileOnix30)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOnix30WhenBothNormaProfilesAreSetup = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOnix30WhenBothNormaProfilesAreSetup)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForAddElementNormRuleOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForAddElementNormRuleOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForSubstituteElementNormRuleOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForSubstituteElementNormRuleOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForRemoveElementNormRuleOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForRemoveElementNormRuleOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForMultiplyNormOptRuleOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForMultiplyElementNormRuleOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt30Add = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt30Add)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt30Remove = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt30Remove)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOnix21WhenBothNormaProfilesAreSetup = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOnix21WhenBothNormaProfilesAreSetup)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt30Substitute = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt30Substitute)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormalizationLongToLongOnix21 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForForNormalizationLongToLongOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormalizationShortToLongOnix21 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForForNormalizationShortToLongOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormalizationLongToLongOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForForNormalizationLongToLongOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForNormalizationShortToLongOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForForNormalizationShortToLongOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptNormRule30SendSingleOnix = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptNormRule30SendSingleOnix)
            .metadataDistributionOption(sendSingleProductsInSingleOnixFile)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptNorm30GroupByPublisherAndImprint = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptNormRule30GroupByPublisherAndImprint)
            .metadataDistributionOption(groupByPublisherImprint)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptNonMatchOnix21 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptNonMatchOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptNonMatchOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptNonMatchOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt30FeedFailedPathSyntaxError = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt30FeedFailedPathSyntaxError)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOpt30FeedFailedPathRemoveRecRef = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOpt30FeedFailedRemoveRecRef)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptConversion21To30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForForOptConversion21To30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssocSrvSFTPForVerifyPrefixAndTitleElement = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssocProductTypeSrvSFTPForForVerifyPrefixAndTitleElement)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();
    public static final AssociationServerSFTP regressionAssociationSrvSFTPForChangingCompOnixElValOnix21Short = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForChangingCompOnixElValOnix21Short)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForChangingCompOnixElValOnix21Long = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForChangingCompOnixElValOnix21Long)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForChangingCompOnixElValOnix30Short = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForChangingCompOnixElValOnix30Short)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForChangingCompOnixElValOnix30Long = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForChangingCompOnixElValOnix30Long)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSWithOptimization30AddReplace = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSWithOptimization30AddReplace)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSWithOptimization30SubsReplace = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSWithOptimization30SubsReplace)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSWithOptimization30RemoveReplace = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSWithOptimization30RemoveReplace)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForRelaxNGValidation21 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForRelaxNGValidation21)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForRelaxNGValidation30 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForRelaxNGValidation30)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaOptProcessIssues21 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaOptProcessIssues21)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaOptProcessIssues30 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaOptProcessIssues30)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaInvalidOptProfile21 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaInvalidOptProfile21)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaInvalidOptProfile30 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaInvalidOptProfile30)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSWithOnix30CAOptionsOn = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSWithOnix30CAOptionsOn)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSWithOnix21CAOptionsOn = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSWithOnix21CAOptionsOn)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleConverterProcessPackagingIssue21 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleConverterProcessPackagingIssue21)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleConverterProcessPackagingIssue30 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleConverterProcessPackagingIssue30)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaTransportationDistrIssues21 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaTransportationDistrIssues21)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaTransportationDistrIssues30 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaTransportationDistrIssues30)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaBothValidOptProfiles = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaBothValidOptProfiles)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaBothInValidOptProfiles = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaBothInValidOptProfiles)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaLinkWithOnixAndProductType21 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaLinkWithOnixAndProductType21)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSForAppleMetaLinkWithOnixAndProductType30 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaLinkWithOnixAndProductType30)
            .build();

    public static final AssociationServerITMS regressionAssociationSrvITMSWithReleaseTypeNewReleaseOnix30 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSReleaseTypeNewReleaseOnix30)
            .build();

    public static final AssociationServerITMS regressionChannelAssociationITMSWithReleaseTypeDigitalOnlyOnix30 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSReleaseTypeDigitalOnlyOnix30)
            .build();

    public static final AssociationServerITMS regressionChannelAssociationITMSWithAppleMetaConverterHandlesTwoAdditionalTitleConditions1 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions1)
            .build();

    public static final AssociationServerITMS regressionChannelAssociationITMSWithAppleMetaConverterHandlesTwoAdditionalTitleConditions2 = AssociationServerITMS.builder()
            .name(DistributionSrvs.smokeDistributionSrvITMS.getName())
            .username(ITMS_USERNAME)
            .password(ITMS_PASSWORD)
            .provider(ITMS_PROVIDER)
            .caProductType(regressionAssociationProductTypeSrvITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions2)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForDistributeWhenTagMatchForOnix21 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForDistributeWhenTagMatchForOnix21)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForDistributeWhenTagMatchForOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForDistributeWhenTagMatchForOnix30)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForDistributeWhenTagDoesNotMatchForOnix21 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForDistributeWhenTagDoesNotMatchForOnix21)
            .build();


    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByPublisher1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByPublisherSuccPath1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByPublisher2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByPublisherSuccPath2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvFTPForProductFlyoutSection = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvFTPForProductFlyoutSection)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptPartiallyFailOnix30 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptPartiallyFailOnix30)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForOptPartiallyFailOnix21 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForOptPartiallyFailOnix21)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByAudienceCode1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByAudienceCodeSuccPath1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByAudienceCode2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByAudienceCodeSuccPath2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByTerritoryRights1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByTerritoryRightsSuccPath1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByTerritoryRights2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByTerritoryRightsSuccPath2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByImprint1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByImprintSuccPath1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByImprint2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByImprintSuccPath2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterBySupplyIdentifierSucc1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierSuccPath1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterBySupplyIdentifierSucc2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierSuccPath2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterBySupplyIdentifierFail1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierFailPath1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterBySupplyIdentifierFail2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierFailPath2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterBySupplyIdentifierFail3 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierFailPath3)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterBySupplyIdentifierFail4 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierFailPath4)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByMultipleDistrRulesSuccPath1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByMultipleDistrRulesSuccPath1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForFilterByMultipleDistrRulesSuccPath2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByMultipleDistrRulesSuccPath2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForMultipleDistrRulesFail1 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByTerritoryRightsSuccPath1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForMultipleDistrRulesFail2 = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForFilterByTerritoryRightsSuccPath2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForFilterByBISACSubjectsSuccPath1)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2 = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForFilterByBISACSubjectsSuccPath2)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForProductCardFilteringSomeFiltersMatch = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForProductCardFilteringSomeFiltersMatch)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForProductCardFilteringWithNoFiltersSetup = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForProductCardFilteringWithNoFiltersSetup)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerSFTP regressionAssociationSrvSFTPForProductCardFilteringWithSalesOutlet = AssociationServerSFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(SFTP_SRV_USERNAME)
            .password(SFTP_SRV_PASSWORD)
            .caProductType(regressionAssociationSrvProductTypeSFTPForProductCardFilteringWithSalesOutlet)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();

    public static final AssociationServerFTP regressionAssocSrvSFTPForAutoDistrOnlyContentAndCollateral = AssociationServerFTP.builder()
            .name(DistributionSrvs.smokeDistributionSrvSFTP.getName())
            .username(FTP_SRV_USERNAME)
            .password(FTP_SRV_PASSWORD)
            .caProductType(regressionAssociationProductTypeSrvSFTPForAutoDistrOnlyContentAndCollateral)
            .metadataDistributionOption(metadataDistrOptionsWithAllDisabledValues)
            .build();
    /**
     * end of regression test set
     */

    private AssociationSrvs() {
        // None
    }
}
