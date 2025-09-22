package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.ChannelAssociation;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.data.DistributionRules.*;

/**
 * Created by R0manL on 16/09/20.
 */

public class ChannelAssociations {
    private static final String NOTIFICATION_EMAIL = ENV_CONFIG.notificationEmail();

    /**
     * Smoke test channel associations
     */
    public static final ChannelAssociation smokeChannelAssociationFTP = ChannelAssociation.builder()
            .distributionRule(DistributionRules.smokeDistributionRuleFTP)
            .associationServer(AssociationSrvs.smokeAssociationSrvFTP)
            .contactInformation(Addresses.smokeChannelContactInfo)
            .distributionNotificationRecipient(NOTIFICATION_EMAIL)
            .takeDownNotificationRecipient(NOTIFICATION_EMAIL)
            .comment("This is FTP server association comment.")
            .build();

    public static final ChannelAssociation smokeChannelAssociationSFTPWithOptimization = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.smokeAssociationSrvSFTPWithOptimization)
            .build();

    public static final ChannelAssociation smokeChannelAssociationITMSWithOptimization = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.smokeAssociationSrvITMSWithOptimization)
            .build();

    public static final ChannelAssociation smokeChannelAssociationSFTPForODD = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.smokeAssociationSrvSFTPForODD)
            .build();

    public static final ChannelAssociation smokeChannelAssociationSFTPForProductTracking = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.smokeAssociationSrvSFTPForProductTracking)
            .build();

    public static final ChannelAssociation smokeChannelAssociationSFTPForEpubWatermarking = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.smokeAssociationSrvSFTPForEpubWatermarking)
            .build();

    public static final ChannelAssociation smokeChannelAssociationSFTPPdfForWatermarking = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.smokeAssociationSrvSFTPForPdfWatermarking)
            .build();

    public static final ChannelAssociation smokeChannelAssociationFTPSForBatchODD = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.smokeAssociationSrvFTPSForBatchODD)
            .build();

    public static final ChannelAssociation smokeChannelAssociationSFTPForAudioDistribution = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.smokeAssociationSrvSFTPForAudioDistribution)
            .build();

    public static final ChannelAssociation smokeChannelAssociationITMSForDistribution = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.smokeAssociationSrvITMSForDistribution)
            .build();

    public static final ChannelAssociation smokeChannelAssociationSFTPForODDONIX30 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.smokeAssociationSrvSFTPForODDONIX30)
            .build();

    public static final ChannelAssociation smokeChannelAssociationFTPSForBatchODDOnix30 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.smokeAssociationSrvFTPSForBatchODDOnix30)
            .build();

    public static final ChannelAssociation smokeChannelAssociationPTPSForAutoDistr = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.smokeAssociationSrvFTPSForAutoDistr)
            .build();
    /* end of smoke test channel associations */

    /**
     * Regression test channel associations
     */
    public static final ChannelAssociation regressionChannelAssociationFTPSForSingleODDValidationCheck = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForSingleODDValidationCheck)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForAddElementNormRule = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForAddElementNormRule)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForSubstituteElementNormRule = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSubstituteElementNormRule)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForRemoveElementNormRule = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForRemoveElementNormRule)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForNormOptRule21SendSingle = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptNormRule21SendSingleOnix)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForNormOptMetadataActivityOnix21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormOptMetadataActivityOnix21)
            .automatedDistributionRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForNormOptMetadataActivityOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormOptMetadataActivityOnix30)
            .automatedDistributionRules(true)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForNormOptFailOnix21MetadataActivity = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormOptFailOnix21MetadataActivity)
            .automatedDistributionRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForNormOptFailOnix30MetadataActivity = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormOptFailOnix30MetadataActivity)
            .automatedDistributionRules(true)
            .useOnix30ToDetermineDistrRules(true)
            .build();
    public static final ChannelAssociation regressionChannelAssociationSFTPForNormOptFailedPath = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptNormFailedPath)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForMultiplyNormOptRule = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForMultiplyNormOptRule)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForNormOptRule21GroupByPublisherAndImprint = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptNorm21GroupByPublisherAndImprint)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPForSingleODDToMultipleChannelsPrint = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPForSingleODDToMultipleChannelsPrint)
            .build();

    public static final ChannelAssociation regressionSortOrderForAssetStatusOnProductDetailsPage = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionSortOrderForAssetStatusOnProductDetailsPage)
            .build();
    public static final ChannelAssociation regressionChannelAssociationFTPForSingleODDToMultipleChannelsEpub = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPForSingleODDToMultipleChannelsEpub)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistrContentWithOnSaleDateInPastForOnix21 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContentWithOnSaleDateInPastForOnix21)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistrContentWithOnSaleDateInPastForOnix30 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContentWithOnSaleDateInPastForOnix30)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistrContentWithOnSaleDateInFutureForOnix21 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContentWithOnSaleDateInFutureForOnix21)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistrContentWithOnSaleDateInFutureForOnix30 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContentWithOnSaleDateInFutureForOnix30)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPForSingleODDToMixedAssets = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPForSingleODDToMixedAssets)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPForDistributionFailure = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPForDistributionFailure)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForDistributionFailure = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForDistributionFailure)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPSForDistributionFailure = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForDistributionFailure)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistContCollatWithOnSaleDateAndOffsetInPastForOnix21 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateAndOffsetInPastForOnix21)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistContCollatWithOnSaleDateInPastAndOnsetForOnix21 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateAndOnsetInPastForOnix21)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistContCollatWithOnSaleDateInPastAndOffsetForOnix30 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateAndOffsetInPastForOnix30)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistContCollatWithOnSaleDateAndOnsetInPastForOnix30 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateAndOnsetInPastForOnix30)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistContCollatWithOnSaleDateInFutureAndOffsetOnix21 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOffsetOnix21)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistContCollatWithOnSaleDateInFutureAndOnsetOnix21 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOnsetOnix21)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistContCollatWithOnSaleDateInFutureAndOffsetOnix30 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOffsetOnix30)
            .build();

    public static final ChannelAssociation regressionCAFTPForAutoDistContCollatWithOnSaleDateInFutureAndOnsetOnix30 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOnsetOnix30)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForDistributionFailure = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForDistributionFailure)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForPublisherName = ChannelAssociation.builder()
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForPublisherTheNavigators)
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForPublisherTyndaleHousePublishersInc)
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForPublisherFocusOnTheFamily)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForPublisherName)
            .build();

    public static final ChannelAssociation regressionCAFTPForSrvSettingsSendTriggerFile = ChannelAssociation.builder()
            .sendTriggerFile(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPForSrvSettingsSendTriggerFile)
            .build();

    public static final ChannelAssociation regressionCASFTPForSendSingleProductsInSingleOnixFile = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSendSingleProductsInSingleOnixFile)
            .build();

    public static final ChannelAssociation regressionCASFTPForSendSingleProductsInSingleOnixFileOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSendSingleProductsInSingleOnixFileOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionCASFTPForSendMetadataWithEveryAssetContent = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSendMetadataWithEveryAssetContent)
            .build();

    public static final ChannelAssociation regressionCASFTPForSendMetadataWithEveryAssetCollateral = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSendMetadataWithEveryAssetCollateral)
            .build();

    public static final ChannelAssociation regressionCASFTPForSendMetadataWithEveryAssetContentRevisionCheck = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSendMetadataWithEveryAssetContentRevisionCheck)
            .build();

    public static final ChannelAssociation regressionCASFTPForSendMetadataWithEveryAssetCollateralRevisionCheck = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSendMetadataWithEveryAssetCollateralRevisionCheck)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForImprintName = ChannelAssociation.builder()
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForImprintNovellPress)
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForImprintPearsonFTPress)
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForImprintMySQLPress)
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForImprintAddisonWesleyProfessional)
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForImprintPearson)
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForImprintPearsonPrenticeHall)
            .advancedKeywordSetting(AdvancedKeywordSettings.settingsForImprintPrenticeHall)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForImprintName)
            .build();

    public static final ChannelAssociation regressionCASFTPForGroupByPublisherImprint = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForGroupByPublisherImprint)
            .build();

    public static final ChannelAssociation regressionCAFTPForOnixFeedDistrFailurePath = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPForOnixFeedDistrFailurePath)
            .build();

    public static final ChannelAssociation regressionCASFTPForOnixFeedDistributionSuccessPath = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOnixFeedDistributionSuccessPath)
            .build();

    public static final ChannelAssociation regressionCAFTPSForSingleODDDistrOnHold = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForSingleODDDistrOnHold)
            .build();

    public static final ChannelAssociation regressionCASFTPForSendMetadataWithEveryAssetOnixNotSelected = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSendMetadataWithEveryAssetOnixNotSelected)
            .build();

    public static final ChannelAssociation regressionCASFTPForSingleODDDistrOnLocked = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSingleODDDistrLocked)
            .build();

    public static final ChannelAssociation regressionCASFTPForSrvSettingsSendTriggerFileFailure = ChannelAssociation.builder()
            .sendTriggerFile(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSrvSettingsSendTriggerFileFailure)
            .build();

    public static final ChannelAssociation regressionCAFTPSForSelectChannelsPageCancel = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForSelectChannelsPageCancel)
            .build();

    public static final ChannelAssociation regressionCAFTPForForAutoDistrWithOnSaleDateAndPublicationDate = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPForAutoDistrWithOnSaleDateAndPublicationDate)
            .build();

    public static final ChannelAssociation regressionCAFTPSForConfirmDistributionPageCancel = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForConfirmDistributionPageCancel)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementOnDistribution1 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForTokenReplacementOnDistribution1)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementOnDistribution2 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForTokenReplacementOnDistribution2)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementOnDistribution3 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForTokenReplacementOnDistribution3)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementOnDistribution4 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForTokenReplacementOnDistribution4)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementOnDistribution5 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForTokenReplacementOnDistribution5)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementOnDistribution6 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForTokenReplacementOnDistribution7)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementOnDistribution7 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForTokenReplacementOnDistribution8)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementOnDistribution8 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForTokenReplacementOnDistribution9)
            .build();

    public static final ChannelAssociation regressionCASFTPForAutoDistrContentWithPublicationDateAndOffsetDateOnix21 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForAutoDistrContentWithPublicationDateAndOffsetDateOnix21)
            .build();

    public static final ChannelAssociation regressionCAFTPSForAutoDistrContentWithPublicationDateAndOnsetDateOnix30 = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPSForAutoDistrContentWithPublicationDateAndOnsetDateOnix30)
            .build();

    public static final ChannelAssociation regressionCAFTPForFilteringByPubDateDefaultDateAndState = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .distributionRule(regressionFilteringByPubDateDefaultDateAndState)
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPForFilteringByPubDateDefaultDateAndState)
            .build();

    public static final ChannelAssociation regressionCAFTPSForSingleODDSelectAllChannels = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForSingleODDSelectAllChannels)
            .build();

    public static final ChannelAssociation regressionCAFTPSForSingleODDMultipleAssetsDistributionToMultipleChannels = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForSingleODDMultipleAssetsDistributionToMultipleChannels)
            .build();

    public static final ChannelAssociation regressionCASFTPForSingleODDMultipleAssetsDistributionToMultipleChannels = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSingleODDMultipleAssetsDistributionToMultipleChannels)
            .build();

    public static final ChannelAssociation regressionCAFTPSForConfirmDistributionPageBack = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForConfirmDistributionPageBack)
            .build();

    public static final ChannelAssociation regressionCAFTPSForMultipleProductTypesAndAssetsDistributionByPaths = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForMultipleProductTypesAndAssetsDistributionByPaths)
            .build();

    public static final ChannelAssociation regressionCAFTPSForSuccessDistributionBackOption = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForSuccessDistributionBackOption)
            .build();

    public static final ChannelAssociation regressionCAFTPSForBatchODDDistrLocked = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForBatchODDDistrLocked)
            .build();

    public static final ChannelAssociation regressionCAFTPSForAutoDistrLockedProduct = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForAutoDistrLockedProduct)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDContentCollateralDistrOnHold = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDContentCollateralDistrOnHold)
            .build();

    public static final ChannelAssociation caFTPForAutoDistrProductOnHoldOnlyOnixHasDistributed = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrProductOnHoldOnlyOnixHasDistributed)
            .build();

    public static final ChannelAssociation caFTPForAutoDistrEnablingMarkExternalForNewChannelTrue = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .preventOfDistributionOfCurrentAssetVersions(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrEnablingMarkExternalForNewChannelTrue)
            .build();

    public static final ChannelAssociation caFTPForAutoDistrEnablingMarkExternalForNewChannelFalse = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrEnablingMarkExternalForNewChannelFalse)
            .build();

    public static final ChannelAssociation caFTPForAutoDistrEnablingMarkExternalForEditChannel = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .preventOfDistributionOfCurrentAssetVersions(true)
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrEnablingMarkExternalForEditChannel)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementForOnixFeedDistXFER = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForTokenReplacementForOnixFeedDistXFER)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementForOnixFeedDistUPD = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForTokenReplacementForOnixFeedDistUPD)
            .build();

    public static final ChannelAssociation regressionCASFTPForTokenReplacementIgnoredForOnixFeedDistribution = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForTokenReplacementIgnoredForOnixFeedDistribution)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario1 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario1)
            .distributionRule(regressionBatchODDScenario1)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario2 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario2)
            .distributionRule(regressionBatchODDScenario2)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario3 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario3)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario4 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario4)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario5 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario5)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario6 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario6)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario7 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario7)
            .distributionRule(regressionBatchODDScenario7)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario8 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario8)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario9 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario9)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario10 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario10)
            .distributionRule(regressionBatchODDScenario10)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario11 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario11)
            .distributionRule(regressionBatchODDScenario11)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario12 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario12)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario13 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario13)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario14 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario14)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario15 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario15)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario16 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario16)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario17 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario17)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario18 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario18)
            .build();

    public static final ChannelAssociation regressionCASFTPForBatchODDScenario19 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForBatchODDScenario19)
            .build();

    public static final ChannelAssociation regressionCASFTPForNormalizationLongToShort = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormalizationLongToShort)
            .build();

    public static final ChannelAssociation regressionCASFTPForNormalization21To30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormalization21To31)
            .build();

    public static final ChannelAssociation regressionCASFTPForCreateTagFromEditCAPage = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForCreateTagFromEditCAPage)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt21FailedPath = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt21FeedFailedPath)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt21FailedMetadataActivityPath = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt21FeedFailedMetadataActivityPath)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt30FailedMetadataActivityPath = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt30FeedFailedMetadataActivityPath)
            .build();
    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt21FromLong21ToShort30AddSubstitute = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt21FromLong21ToShort30AddSubstitute)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt21Remove = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt21Remove)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOptMultiply = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptMultiply)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOptMetadataActivityOnix21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptMetadataActivityOnix21)
            .automatedDistributionRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOptMetadataActivityOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptMetadataActivityOnix30)
            .automatedDistributionRules(true)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSWithOptimization21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSWithOptimization21)
            .build();

    public static final ChannelAssociation regressionCASFTPForSimpleCACreation = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSimpleCACreation)
            .build();

    public static final ChannelAssociation regressionCASFTPForWatermarkAndPublisherID = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSimpleCACreation)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPSForCAEditModeInitial = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForCAEditModeInitial)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPSForCAEditModeInitialValidation = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForCAEditModeInitialValidation)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPSForCAEditModeUpdated = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .sendTriggerFile(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForCAEditModeUpdated)
            .contactInformation(Addresses.channelAssociationContactInfo)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPForMarkExternalUpdateFalse = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .useOnix30ToDetermineDistrRules(true)
            .preventOfDistributionOfCurrentAssetVersions(false)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPSForCAEditModeUpdatedValidation = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForCAEditModeUpdatedValidation)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPSForCAEditModeWatermarkingIsOff = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForCAEditModeWatermarkOff)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPSForCAEditBUWatermarkSettings = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForCAEditBUWatermarkSettings)
            .build();

    public static final ChannelAssociation regressionCASFTPSForCATagFilteringOptionSuperAdmin = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSimpleCACreation)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForCATagFilteringOptionAndCAId = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSimpleCACreation)
            .build();

    public static final ChannelAssociation regressionChannelAssociationFTPSForCAViewBUWatermarkSettings = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvFTPSForCAEditBUWatermarkSettings)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForCADeactivationAndSorting = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSimpleCACreation)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForCAFiltering = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSimpleCACreation)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForCASorting = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSimpleCACreation)
            .build();

    public static final ChannelAssociation regressionCASFTPForAutoSendSingleProductsInSingleOnixFileOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForAutoSendSingleProductsInSingleOnixFileOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .automatedDistributionRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOnix30WhenBothNormaProfilesAreSetup = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOnix30WhenBothNormaProfilesAreSetup)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForAddElementNormRuleOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForAddElementNormRuleOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForSubstituteElementNormRuleOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForSubstituteElementNormRuleOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForRemoveElementNormRuleOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForRemoveElementNormRuleOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForMultiplyNormOptRuleOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForMultiplyNormOptRuleOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt30Add = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt30Add)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt30Remove = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt30Remove)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOnix21WhenBothNormaProfilesAreSetup = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOnix21WhenBothNormaProfilesAreSetup)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt30Substitute = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt30Substitute)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionCASFTPForNormalizationLongToLongOnix21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormalizationLongToLongOnix21)
            .build();

    public static final ChannelAssociation regressionCASFTPForNormalizationShortToLongOnix21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormalizationShortToLongOnix21)
            .build();

    public static final ChannelAssociation regressionCASFTPForNormalizationLongToLongOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormalizationLongToLongOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionCASFTPForNormalizationShortToLongOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForNormalizationShortToLongOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForNormOptRule30SendSingle = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptNormRule30SendSingleOnix)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForNormOptRule30GroupByPublisherAndImprint = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptNorm30GroupByPublisherAndImprint)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOptNonMatchOnix21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptNonMatchOnix21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOptNonMatchOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptNonMatchOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt30FailedPathSyntaxError = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt30FeedFailedPathSyntaxError)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOpt30FailedPathRemoveRecRef = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOpt30FeedFailedPathRemoveRecRef)
            .build();

    public static final ChannelAssociation regressionCASFTPForOptConversion21To30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptConversion21To30)
            .build();

    public static final ChannelAssociation regressionChnlAssocSFTPForVerifyPrefixAndTitleElement = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForVerifyPrefixAndTitleElement)
            .build();

    public static final ChannelAssociation regressionCASFTPForChangingCompOnixElValOnix21Short = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForChangingCompOnixElValOnix21Short)
            .build();

    public static final ChannelAssociation regressionCASFTPForChangingCompOnixElValOnix21Long = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForChangingCompOnixElValOnix21Long)
            .build();

    public static final ChannelAssociation regressionCASFTPForChangingCompOnixElValOnix30Short = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForChangingCompOnixElValOnix30Short)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionCASFTPForChangingCompOnixElValOnix30Long = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForChangingCompOnixElValOnix30Long)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSWithOptimization30AddReplace = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSWithOptimization30AddReplace)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSWithOptimization30SubsReplace = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSWithOptimization30SubsReplace)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSWithOptimization30RemoveReplace = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSWithOptimization30RemoveReplace)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForRelaxNGValidation21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForRelaxNGValidation21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForRelaxNGValidation30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForRelaxNGValidation30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaOptProcessIssues21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaOptProcessIssues21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaOptProcessIssues30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaOptProcessIssues30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaInvalidOptProfile21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaInvalidOptProfile21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaInvalidOptProfile30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaInvalidOptProfile30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSWithOnix30CAOptionsOn = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSWithOnix30CAOptionsOn)
            .distributionRule(appleDistrRuleOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSWithOnix21CAOptionsOn = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSWithOnix21CAOptionsOn)
            .distributionRule(appleDistrRuleOnix21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleConverterProcessPackagingIssue21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleConverterProcessPackagingIssue21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleConverterProcessPackagingIssue30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleConverterProcessPackagingIssue30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaTransportationDistrIssues21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaTransportationDistrIssues21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaTransportationDistrIssues30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaTransportationDistrIssues30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaBothValidOptProfiles = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaBothValidOptProfiles)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaBothInValidOptProfiles = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaBothInValidOptProfiles)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaLinkWithOnixAndProductType21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaLinkWithOnixAndProductType21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaLinkWithOnixAndProductType30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSForAppleMetaLinkWithOnixAndProductType30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSWithReleaseTypeNewReleaseOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvITMSWithReleaseTypeNewReleaseOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSWithReleaseTypeDigitalOnlyOnix30 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionChannelAssociationITMSWithReleaseTypeDigitalOnlyOnix30)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions1 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionChannelAssociationITMSWithAppleMetaConverterHandlesTwoAdditionalTitleConditions1)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions2 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionChannelAssociationITMSWithAppleMetaConverterHandlesTwoAdditionalTitleConditions2)
            .useOnix30ToDetermineDistrRules(true)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForDistributeWhenTagMatchForOnix21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForDistributeWhenTagMatchForOnix21)
            .distributionRule(tagMatchForOnix21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForDistributeWhenTagMatchForOnix30 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForDistributeWhenTagMatchForOnix30)
            .distributionRule(tagMatchForOnix30)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForDistributeWhenTagDoesNotMatchForOnix21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForDistributeWhenTagDoesNotMatchForOnix21)
            .distributionRule(tagDoesNotMatchForOnix21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByPublisherSuccPath1 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByPublisherSuccPath1)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByPublisher1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByPublisherSuccPath2 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByPublisherSuccPath2)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByPublisher2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByPublisherFailPath1 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByPublisherFailPath)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByPublisher1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByPublisherFailPath2 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByPublisherFailPath)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByPublisher2)
            .build();

    public static final ChannelAssociation regressionCAFTPForChannelFlyoutSection = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssocSrvFTPForAutoDistrContentWithOnSaleDateInPastForOnix21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOptPartiallyFailOnix30 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptPartiallyFailOnix30)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForOptPartiallyFailOnix21 = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForOptPartiallyFailOnix21)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByAudienceCodeSuccPath1 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByAudienceCodeSuccPath1)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByAudienceCode1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByAudienceCodeSuccPath2 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByAudienceCodeSuccPath2)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByAudienceCode2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByAudienceCodeFailPath1 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByAudienceCodeFailPath)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByAudienceCode1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByAudienceCodeFailPath2 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByAudienceCodeFailPath)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByAudienceCode2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByTerritoryRightsSuccPath1 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByTerritoryRightsSuccPath1)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByTerritoryRights1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByTerritoryRightsSuccPath2 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByTerritoryRightsSuccPath2)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByTerritoryRights2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByTerritoryRightsFailPath1 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByPTerritoryRightsFailPath)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByTerritoryRights1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByTerritoryRightsFailPath2 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByPTerritoryRightsFailPath)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByTerritoryRights2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintSuccPath11 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintNoValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintSuccPath12 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintNoValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintSuccPath21 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintSingleValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintSuccPath22 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintSingleValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintSuccPath31 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintTwoValues)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintSuccPath32 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintTwoValues)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintFailPath11 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintSingleValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintFailPath12 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintSingleValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintFailPath21 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintSingleInvalidValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByImprintFailPath22 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByImprintSingleInvalidValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByImprint2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath11 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierNoValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierSucc1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath12 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierNoValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierSucc2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath21 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierSingleValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierSucc1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath22 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierSingleValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierSucc2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath31 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierTwoValues)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierSucc1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierSuccPath32 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierTwoValues)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierSucc2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierFailPath11 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierSingleValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierFail1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierFailPath12 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierSingleValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierFail2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierFailPath21 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierSingleInvalidValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierFail3)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterBySupplyIdentifierFailPath22 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterBySupplyIdentifierSingleInvalidValue)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterBySupplyIdentifierFail4)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByMultipleDistrRulesSuccPath1 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByMultipleDistrRulesSuccPath1)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByMultipleDistrRulesSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByMultipleDistrRulesSuccPath2 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByMultipleDistrRulesSuccPath2)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByMultipleDistrRulesSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByMultipleDistrRulesFailPath1 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByMultipleDistrRulesFail)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForMultipleDistrRulesFail1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByMultipleDistrRulesFailPath2 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByMultipleDistrRulesFail)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForMultipleDistrRulesFail2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath11 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueS11)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath12 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueS12)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath21 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS21)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath22 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS22)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath31 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS31)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath32 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS32)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath41 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS41)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccPath42 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS42)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsPassPath51 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueP51)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccessPath52 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueP52)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccessPath61 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueP61)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsSuccessPath62 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueP62)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath11 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF11)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath12 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF12)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath21 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF21)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath22 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF22)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath31 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF31)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath32 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF32)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath61 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF61)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath62 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF62)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath71 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF71)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath72 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF72)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath81 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF81)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath82 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF82)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath91 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF91)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath92 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF92)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath101 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF101)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath102 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF102)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath111 = ChannelAssociation.builder()
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF111)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath1)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForFilterByBISACSubjectsFailPath112 = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF112)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForFilterByBISACSubjectsSuccPath2)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForProductCardFilteringSomeFiltersMatch = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForProductCardFilteringSomeFiltersMatch)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForProductCardFilteringSomeFiltersMatch)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForProductCardFilteringWithNoFiltersSetup = ChannelAssociation.builder()
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForProductCardFilteringWithNoFiltersSetup)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet)
            .build();

    public static final ChannelAssociation regressionChannelAssociationSFTPForProductCardFilteringWithSalesOutlet = ChannelAssociation.builder()
            .useOnix30ToDetermineDistrRules(true)
            .distributionRule(DistributionRules.regressionDistributionRuleSFTPForProductCardFilteringWithSalesOutlet)
            .associationServer(AssociationSrvs.regressionAssociationSrvSFTPForProductCardFilteringWithSalesOutlet)
            .build();

    public static final ChannelAssociation regressionCASFTPForAutoDistrOnlyContentAndCollateral = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .associationServer(AssociationSrvs.regressionAssocSrvSFTPForAutoDistrOnlyContentAndCollateral)
            .build();
    /**
     * end of regression test channel associations
     */


    private ChannelAssociations() {
        // None
    }
}
