package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.ProductType;
import com.ccc.hv.qa.ui.pojos.ChannelAssociationProductType;

import static com.ccc.hv.qa.ui.data.ChannelAssociationContents.*;

public class ChannelAssociationProductTypes {

    /**
     * Smoke test set
     */

    public static final ChannelAssociationProductType smokeChannelAssociationProductTypeSrvFTP = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.smokeChannelAssociationContentFTP)
            .collateral(ChannelAssociationCollaterals.collateralScreenshot)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeChannelAssociationProductTypeSrvSFTP = ChannelAssociationProductType.builder()
            .productType(ProductType.AUDIO)
            .content(ChannelAssociationContents.smokeChannelAssociationContentSFTP)
            .collateral(ChannelAssociationCollaterals.collateralScreenshot)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeChannelAssociationProductTypeSrvFTPS = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .content(ChannelAssociationContents.smokeChannelAssociationContentFTPS)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeChannelAssociationProductTypeSrvITMS = ChannelAssociationProductType.builder()
            .productType(ProductType.IBOOK)
            .content(ChannelAssociationContents.contentIBook)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvSFTPWithOptimization = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvITMSWithOptimization = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvSFTPForODD = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvSFTPForProductTracking = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvSFTPForEpubWatermarking = ChannelAssociationProductType.builder()
            .content(ChannelAssociationContents.smokeChannelAssociationContentSFTPEpubWatermarking)
            .productType(ProductType.EPUB)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvSFTPForPdfWatermarking = ChannelAssociationProductType.builder()
            .content(ChannelAssociationContents.smokeChannelAssociationContentSFTPPdfWatermarking)
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvFTPSForBatchODD = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvSFTPForAudioDistribution = ChannelAssociationProductType.builder()
            .productType(ProductType.CD_AUDIO)
            .content(ChannelAssociationContents.smokeChannelAssociationContentSFTPAudioDistribution)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvITMSForDistribution = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvSFTPForODDONIX30 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvFTPSForBatchODDOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType smokeAssociationProductTypeSrvFTPSForAutoDistr = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();
    /* end of smoke test set */

    /**
     * Regression test set
     */
    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForSingleODDValidationCheck = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContentWithOnSaleDateInPastForOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContentWithOnSaleDateInPastForOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.ENHANCED_EPUB)
            .content(ChannelAssociationContents.contentEnhancedEpub)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContentWithOnSaleDateInFutureForOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContentWithOnSaleDateInFutureForOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .content(ChannelAssociationContents.contentPrintReplica)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForSingleODDToMultipleChannelsPrint = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .collateral(ChannelAssociationCollaterals.collateralFrontCoverProof)
            .content(ChannelAssociationContents.contentPrintReplica)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSortOrderForAssetStatusOnProductDetailsPage = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForSingleODDToMultipleChannelsEpub = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .content(ChannelAssociationContents.contentEpub)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForSingleODDToMixedAssets = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForDistributionFailure = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .content(ChannelAssociationContents.contentPrintReplica)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForDistributionFailure = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralOutsideBackCover)
            .content(ChannelAssociationContents.contentEpub)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForDistributionFailure = ChannelAssociationProductType.builder()
            .productType(ProductType.FIXED_LAYOUT_EPIB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralOutsideFrontCover)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateAndOffsetInPastForOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.regressionCAContentFTPForAutoDistWithOnSaleDateAndOffsetInPastForOnix21)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateAndOnsetInPastForOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.regressionCAContentFTPForAutoDistWithOnSaleDateAndOnsetInPastForOnix21)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateAndOffsetInPastForOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .content(ChannelAssociationContents.regressionCAContentFTPForAutoDistWithOnSaleDateAndOffsetInPastForOnix30)
            .collateral(ChannelAssociationCollaterals.collateralScreenshot)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateAndOnsetInPastForOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .content(ChannelAssociationContents.regressionCAContentFTPForAutoDistWithOnSaleDateAndOnsetInPastForOnix30)
            .collateral(ChannelAssociationCollaterals.collateralScreenshot)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssocProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOffsetOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.MOBIPOCKET)
            .content(ChannelAssociationContents.regressionCAContentFTPForAutoDistWithOnSaleDateInFutureAndOffsetOnix21)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOnsetOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.MOBIPOCKET)
            .content(ChannelAssociationContents.regressionCAContentFTPForAutoDistWithOnSaleDateInFutureAndOnsetOnix21)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOffsetOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.SEARCH_PDF)
            .content(ChannelAssociationContents.regressionCAContentFTPForAutoDistWithOnSaleDateInFutureAndOffsetOnix30)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrContCollatWithOnSaleDateInFutureAndOnsetOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.SEARCH_PDF)
            .content(ChannelAssociationContents.regressionCAContentFTPForAutoDistWithOnSaleDateInFutureAndOnsetOnix30)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForDistributionFailure = ChannelAssociationProductType.builder()
            .productType(ProductType.FIXED_LAYOUT_EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .content(ChannelAssociationContents.contentFixedLayoutEpub)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForPublisherName = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21PublisherName)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImageToPublisherNameFolder)
            .content(ChannelAssociationContents.regressionChannelAssociationContentFTPForPublisherName)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForSrvSettingsSendTriggerFile = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.regressionChannelAssociationContentFTPForSrvSettingsSendTriggerFile)
            .collateral(ChannelAssociationCollaterals.collateralOutsideBackCoverToRecRefFolder)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetContent = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.regressionCAContentEPUBForSendMetadataWithEveryAsset)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetCollateral = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralScreenshotSendMetadataWithEveryAsset)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetContentRevisionCheck = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.regressionCAContentEPUBForSendMetadataWithEveryAsset)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetCollateralRevisionCheck = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralScreenshotSendMetadataWithEveryAsset)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSendMetadataWithEveryAssetCollateralOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImgSendMetadataWithEveryAssetCollateralOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSendSingleProductsInSingleOnixFile = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSendSingleProductsInSingleOnixFileOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataSendSingleProductsInSingleOnixFileOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForImprintName = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21ImprintName)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImageImprintName)
            .content(ChannelAssociationContents.regressionChannelAssociationContentFTPForImprintName)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForGroupByPublisherImprint = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeFTPForOnixFeedDistrFailurePath = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForOnixFeedDistributionSuccessPath = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForSingleODDDistrOnHold = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSSendMetadataWithEveryAssetOnixNotSelected = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.regressionCAContentSFTPForSendMetadataWithEveryAssetOnixNotSelected)
            .collateral(ChannelAssociationCollaterals.collateralScreenshotSendMetadataWithEveryAssetOnixNotSelected)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSingleODDDistrLocked = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPSettingsSendTriggerFileFailure = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.regressionCAContentSFTPSettingsSendTriggerFileFailure)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImageToRecRefFolder)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForSelectChannelsPageCancel = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForAutoDistrWithOnSaleDateAndPublicationDate = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.contentPrintReplica)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForConfirmDistributionPageCancel = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();
    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution0 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .collateral(ChannelAssociationCollaterals.collateralForTokenReplacementOnDistribution0)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution1 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.regressionCAContentSFTPForTokenReplacementOnDistribution1)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution2 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.tokenReplacementOnDistribution2)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution3 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralForTokenReplacementOnDistribution3)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution4 = ChannelAssociationProductType.builder()
            .productType(ProductType.FIXED_LAYOUT_EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .collateral(ChannelAssociationCollaterals.collateralForTokenReplacementOnDistribution4)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution5 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.regressionCAContentSFTPForTokenReplacementOnDistribution5)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution6 = ChannelAssociationProductType.builder()
            .productType(ProductType.FIXED_LAYOUT_EPIB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralForTokenReplacementOnDistribution6)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution7 = ChannelAssociationProductType.builder()
            .productType(ProductType.KINDLE)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralForTokenReplacementOnDistribution7)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution8 = ChannelAssociationProductType.builder()
            .productType(ProductType.MOBIPOCKET)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralForTokenReplacementOnDistribution8)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementOnDistribution9 = ChannelAssociationProductType.builder()
            .productType(ProductType.JIGSAW)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .collateral(ChannelAssociationCollaterals.collateralForTokenReplacementOnDistribution9)
            .build();

    public static final ChannelAssociationProductType regressionAssocProductTypeSrvSFTPForAutoDistrContentWithPublicationDateAndOffsetDateOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.FIXED_LAYOUT_EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.regressionCAContentSFTPForAutoDistrWithPublicationDateAndOffset)
            .build();

    public static final ChannelAssociationProductType regressionAssocProductTypeSrvFTPSForAutoDistrContentWithPublicationDateAndOnsetDateOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .content(ChannelAssociationContents.regressionCAContentFTPSForAutoDistrWithPublicationDateAndOnsetDate)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForFilteringByPubDateDefaultDateAndState = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForFilteringByPubDateBeforeInOnix = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForFilteringByPubDateEqualsOnixDate = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForFilteringByPubDateBeforeNoPubDateInOnix = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForFilteringByPubDateAfterNoPubDateInOnix = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForFilteringByPubDateEqualsOnixDateAfterOption = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForFilteringByPubDateIsAfterOnixPubDate = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilteringByPubDateIsAfterOnixPubDateAssetsAreDistr = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForFilteringByPubDateIsBeforeOnixPubDate = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForSingleODDSelectAllChannels = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvForSingleODDMultipleAssetsDistrToMultiChnls = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForConfirmDistributionPageBack = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForMultipleProductTypesAndAssetsDistributionByPathsEPUB = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImageForMultipleProductTypesAndAssetsDistributionByPathsEPUB)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForMultipleProductTypesAndAssetsDistributionByPathsPRINT = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImageForMultipleProductTypesAndAssetsDistributionByPathsPRINT)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForMultipleProductTypesAndAssetsDistributionByPathsPDF = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImageForMultipleProductTypesAndAssetsDistributionByPathsPDF)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForMultipleProductTypesAndAssetsDistributionByPathsMOBI = ChannelAssociationProductType.builder()
            .productType(ProductType.MOBIPOCKET)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImageForMultipleProductTypesAndAssetsDistributionByPathsMOBI)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForSuccessDistributionBackOption = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForBatchODDDistrLocked = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.contentEpub)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPSForAutoDistrLockedProduct = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .content(ChannelAssociationContents.contentPrintReplica)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDContentCollateralDistrOnHold = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvFTPForAutoDistrProductOnHoldOnlyOnixHasDistributed = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .content(ChannelAssociationContents.contentPrintReplica)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvFTPForAutoDistrEnablingMarkExternalForNewChannelTrue = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(contentEpub)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvFTPForAutoDistrEnablingMarkExternalForNewChannelFalse = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(contentEpub)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvFTPForAutoDistrEnablingMarkExternalForEditChannel = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(contentEpub)
            .build();
    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForTokenReplacementForOnixFeedDistXFER = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21tokenReplacementForOnixFeedDistXFER)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForFilteringByPubDateSuccODDWithOptionAftereOneDayDiffInFuture = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForFilteringByPubDateSuccODDWithOptionBeforeOneDayDiffInFuture = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForTokenReplacementForOnixFeedDistUPD = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21tokenReplacementForOnixFeedDistUPD)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForChannelUnlockAfterCAEditing = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForTokenReplacementIgnoredForOnixFeedDistribution = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataTokenReplacementIgnoredForOnixFeedDistribution)
            .collateral(ChannelAssociationCollaterals.collateralForTokenReplacementOnDistribution3)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForPubFilterSuccODDWithAfterOptionOneDayDiffInFutureAndDiscountCode = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForPubDateFilterFailODDWithAfterOptionOneDayDiffInPastAndDiscountCode = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForPubDateFilterFailODDWithValidFilterAndInvalidDiscountCode = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForPubDateFilterFailODDWithValidFilterAndMissedDiscountCode = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario1 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario2 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario3 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario4 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario5 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario6 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario7 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario8 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario9 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario10 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario11 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario12 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario13 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario14 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario15 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario16 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario17 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario18 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForBatchODDScenario19 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForAddElementNormRule = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSubstituteElementNormRule = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForRemoveElementNormRule = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptNormRule21SendSingleOnix = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForNormOptMetadataActivityOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForNormOptMetadataActivityOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForNormOptFailOnix21MetadataActivity = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForNormOptFailOnix30MetadataActivity = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptNormFailedPath = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForMultiplyNormOptRule = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptNormRule21GroupByPublisherAndImprint = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForForNormalizationCSVFeed = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForForNormalizationLongToShort = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForForNormalization21To31 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForCreateTagFromEditCAPage = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt21FailedPath = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt21FailedMetadataActivityPath = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt30FailedMetadataActivityPath = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt21FromLong21ToShort30AddSubstitute = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt21Remove = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptMultiply = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptMetadataActivityOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptMetadataActivityOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForEditCAInitial = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForEditCAWatermarkOffPdf = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdf)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForEditCAWatermarkOffEpub = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .content(contentAmazonVariantEpub)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSWithOptimization21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarking = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .content(ChannelAssociationContents.contentWebOptimizedPdfWithWatermarking)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarking2 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF_ALTERNATE)
            .content(ChannelAssociationContents.contentWebOptimizedPdfAltWithWatermarking)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarking3 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF_ENHANCED)
            .content(ChannelAssociationContents.contentWebOptimizedPdfEnhWithWatermarking)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingEPUB = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(contentKoboVariantEpub)
            .content(contentEpub)
            .content(contentAppleVariantEpub)
            .content(contentBNVariantEpub)
            .content(contentAmazonVariantEpub)
            .content(contentGoogleVariantEpub)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingMobipocket = ChannelAssociationProductType.builder()
            .productType(ProductType.MOBIPOCKET)
            .content(contentKindleKPF)
            .content(contentMobipocketPRC)
            .content(contentMobipocketMobi)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingIBook = ChannelAssociationProductType.builder()
            .productType(ProductType.IBOOK)
            .content(contentIBook)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingEnhancedMobipocket = ChannelAssociationProductType.builder()
            .productType(ProductType.ENHANCED_MOBIPOCKET)
            .content(contentEnhancedMobipocketMobi)
            .content(contentKindlePackageFormat)
            .content(contentEnhancedMobipocketPRC)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingFixedLayoutePIB = ChannelAssociationProductType.builder()
            .productType(ProductType.FIXED_LAYOUT_EPIB)
            .content(contentFixedLayoutePIB)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingFixedLayoutKindle = ChannelAssociationProductType.builder()
            .productType(ProductType.FIXED_LAYOUT_KINDLE)
            .content(contentFixedLayoutKindleMobi)
            .content(contentFixedLayoutKindlePRC)
            .content(contentKindlePackageFormat)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingEnhancedFixedEPUB = ChannelAssociationProductType.builder()
            .productType(ProductType.ENHANCED_FIXED_EPUB)
            .content(contentEnhancedFixedEPUB)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingEnhancedFixedePIB = ChannelAssociationProductType.builder()
            .productType(ProductType.ENHANCED_FIXED_EPIB)
            .content(contentEnhancedFixedePIB)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingEnhancedEPUB = ChannelAssociationProductType.builder()
            .productType(ProductType.ENHANCED_EPUB)
            .content(contentEnhancedEPUB)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingFixedLayoutEPUB = ChannelAssociationProductType.builder()
            .productType(ProductType.FIXED_LAYOUT_EPUB)
            .content(contentFixedLayoutEPUB)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingPrint = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .content(contentPrintOnDemand)
            .content(contentJacketPDF)
            .content(contentPrintReplica)
            .content(contentPrintOnDemandCover)
            .content(contentPrintCover)
            .content(contentPrintOnDemandJacket)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingEPUBAlternate = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB_ALTERNATE)
            .content(contentEPUBAlternate)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingPubXML = ChannelAssociationProductType.builder()
            .productType(ProductType.PUB_XML)
            .content(contentPubXML)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingAudio = ChannelAssociationProductType.builder()
            .productType(ProductType.AUDIO)
            .content(contentAudio)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingAudioCassette = ChannelAssociationProductType.builder()
            .productType(ProductType.AUDIO_CASSETTE)
            .content(contentAudioCassette)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingCDAudio = ChannelAssociationProductType.builder()
            .productType(ProductType.CD_AUDIO)
            .content(contentCDAudio)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingDownloadableAudioFile = ChannelAssociationProductType.builder()
            .productType(ProductType.DOWNLOADABLE_AUDIO_FILE)
            .content(contentDownloadableAudioFile)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingCDROM = ChannelAssociationProductType.builder()
            .productType(ProductType.CD_ROM)
            .content(contentCDROM)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingEbook = ChannelAssociationProductType.builder()
            .productType(ProductType.EBOOK)
            .content(contentEbook)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingCalendar = ChannelAssociationProductType.builder()
            .productType(ProductType.CALENDAR)
            .content(contentCalendar)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingCards = ChannelAssociationProductType.builder()
            .productType(ProductType.CARDS)
            .content(contentCards)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingDiary = ChannelAssociationProductType.builder()
            .productType(ProductType.DIARY)
            .content(contentDiary)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingKit = ChannelAssociationProductType.builder()
            .productType(ProductType.KIT)
            .content(contentKit)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingPostcardBook = ChannelAssociationProductType.builder()
            .productType(ProductType.POSTCARD_BOOK)
            .content(contentPostcardBook)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingWalletOrFolder = ChannelAssociationProductType.builder()
            .productType(ProductType.WALLET_OR_FOLDER)
            .content(contentWalletOrFolder)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingStickers = ChannelAssociationProductType.builder()
            .productType(ProductType.STICKERS)
            .content(contentStickers)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingOtherPrintedItem = ChannelAssociationProductType.builder()
            .productType(ProductType.OTHER_PRINTED_ITEM)
            .content(contentOtherPrintedItem)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingDVDVideo = ChannelAssociationProductType.builder()
            .productType(ProductType.DVD_VIDEO)
            .content(contentDVDVideo)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingMixedMediaProduct = ChannelAssociationProductType.builder()
            .productType(ProductType.MIXED_MEDIA_PRODUCT)
            .content(contentMixedMediaProduct)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingMultipleCopyPack = ChannelAssociationProductType.builder()
            .productType(ProductType.MULTIPLE_COPY_PACK)
            .content(contentMultipleCopyPack)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingCounterpack = ChannelAssociationProductType.builder()
            .productType(ProductType.COUNTERPACK)
            .content(contentCounterpack)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingJigsaw = ChannelAssociationProductType.builder()
            .productType(ProductType.JIGSAW)
            .content(contentJigsaw)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingOtherMerchandise = ChannelAssociationProductType.builder()
            .productType(ProductType.OTHER_MERCHANDISE)
            .content(contentOtherMerchandise)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingSearchPDF = ChannelAssociationProductType.builder()
            .productType(ProductType.SEARCH_PDF)
            .content(contentSearchPDF)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingNookPagePerfectPDF = ChannelAssociationProductType.builder()
            .productType(ProductType.NOOK_PAGE_PERFECT_PDF)
            .content(contentNookPagePerfectPDF)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingKindle = ChannelAssociationProductType.builder()
            .productType(ProductType.KINDLE)
            .content(contentKindleMobi)
            .content(contentKindleKPF)
            .content(contentKindlePRC)
            .build();

    public static final ChannelAssociationProductType regressionAssocProdTypeSrvSFTPForWatermarkingGame = ChannelAssociationProductType.builder()
            .productType(ProductType.GAME)
            .content(contentGame)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForAutoSendSingleProductsInSingleOnixFileOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataSendSingleProductsInSingleOnixFileOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOnix30WhenBothNormaProfilesAreSetup = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForAddElementNormRuleOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForSubstituteElementNormRuleOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForRemoveElementNormRuleOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForMultiplyElementNormRuleOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt30Add = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt30Remove = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOnix21WhenBothNormaProfilesAreSetup = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt30Substitute = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForForNormalizationLongToLongOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForForNormalizationShortToLongOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForForNormalizationLongToLongOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForForNormalizationShortToLongOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptNormRule30SendSingleOnix = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptNormRule30GroupByPublisherAndImprint = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptNonMatchOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptNonMatchOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt30FeedFailedPathSyntaxError = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOpt30FeedFailedRemoveRecRef = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForForOptConversion21To30 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssocProductTypeSrvSFTPForForVerifyPrefixAndTitleElement = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForChangingCompOnixElValOnix21Short = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForChangingCompOnixElValOnix21Long = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForChangingCompOnixElValOnix30Short = ChannelAssociationProductType.builder()
            .productType(ProductType.ENHANCED_EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .content(contentEnhancedEPUB)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForChangingCompOnixElValOnix30Long = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSWithOptimization30AddReplace = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSWithOptimization30SubsReplace = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSWithOptimization30RemoveReplace = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForRelaxNGValidation21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForRelaxNGValidation30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaOptProcessIssues21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaOptProcessIssues30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaInvalidOptProfile21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaInvalidOptProfile30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSWithOnix30CAOptionsOn = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSWithOnix21CAOptionsOn = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleConverterProcessPackagingIssue21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleConverterProcessPackagingIssue30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaTransportationDistrIssues21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaTransportationDistrIssues30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaBothValidOptProfiles = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaBothInValidOptProfiles = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaLinkWithOnixAndProductType21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaLinkWithOnixAndProductType30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.regressionCAContentAppleMetaEpub)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSReleaseTypeNewReleaseOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSReleaseTypeDigitalOnlyOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions1 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvITMSForAppleMetaConverterHandlesTwoAdditionalTitleConditions2 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForDistributeWhenTagMatchForOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForDistributeWhenTagMatchForOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForDistributeWhenTagDoesNotMatchForOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByPublisherSuccPath1 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByPublisherSuccPath2 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvFTPForProductFlyoutSection = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptPartiallyFailOnix30 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForOptPartiallyFailOnix21 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByAudienceCodeSuccPath1 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByAudienceCodeSuccPath2 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByTerritoryRightsSuccPath1 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByTerritoryRightsSuccPath2 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();


    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByImprintSuccPath1 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByImprintSuccPath2 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierSuccPath1 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierSuccPath2 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierFailPath1 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierFailPath2 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierFailPath3 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterBySupplyIdentifierFailPath4 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByMultipleDistrRulesSuccPath1 = ChannelAssociationProductType.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForFilterByMultipleDistrRulesSuccPath2 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForFilterByBISACSubjectsSuccPath1 = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForFilterByBISACSubjectsSuccPath2 = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForProductCardFilteringSomeFiltersMatch = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForProductCardFilteringWithNoFiltersSetup = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationSrvProductTypeSFTPForProductCardFilteringWithSalesOutlet = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .metadata(ChannelAssociationMetadatas.metadataOnix30)
            .build();

    public static final ChannelAssociationProductType regressionAssociationProductTypeSrvSFTPForAutoDistrOnlyContentAndCollateral = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(ChannelAssociationContents.contentEpub)
            .collateral(ChannelAssociationCollaterals.collateralMarketingImage)
            .build();
    /** end of regression test set */


    private ChannelAssociationProductTypes() {
        //NONE
    }
}
