package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.CollateralType;
import com.ccc.hv.qa.ui.pojos.ChannelAssociationCollateral;

public class ChannelAssociationCollaterals {

    public static final ChannelAssociationCollateral collateralScreenshot = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.SCREENSHOT)
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImage = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .build();

    public static final ChannelAssociationCollateral collateralFrontCoverProof = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.FRONT_COVER_PROOF)
            .build();

    public static final ChannelAssociationCollateral collateralOutsideFrontCover = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.OUTSIDE_FRONT_COVER)
            .build();

    public static final ChannelAssociationCollateral collateralOutsideBackCover = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.OUTSIDE_BACK_COVER)
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImageToPublisherNameFolder = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/image/${PUBLISHER}/${XFER yyyyMMddHHmmssSSS}/")
            .build();

    public static final ChannelAssociationCollateral collateralOutsideBackCoverToRecRefFolder = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.OUTSIDE_BACK_COVER)
            .destinationFolder("/${RECORDREFERENCE}/")
            .build();

    public static final ChannelAssociationCollateral collateralScreenshotSendMetadataWithEveryAsset = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.SCREENSHOT)
            .destinationFolder("/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImgSendMetadataWithEveryAssetCollateralOnix30 = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/${RECORDREFERENCE}/")
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImageImprintName = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/ebooks/${IMPRINTCODE}/${ISBN13}/")
            .build();

    public static final ChannelAssociationCollateral collateralScreenshotSendMetadataWithEveryAssetOnixNotSelected = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.SCREENSHOT)
            .destinationFolder("/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImageToRecRefFolder = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/${RECORDREFERENCE}/")
            .build();

    public static final ChannelAssociationCollateral collateralForTokenReplacementOnDistribution0 = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.SCREENSHOT)
            .destinationFolder("./${ISBN13}")
            .build();

    public static final ChannelAssociationCollateral collateralForTokenReplacementOnDistribution3 = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/City_SA_${XFER yyyyMMdd-HHmmssSSS}")
            .build();

    public static final ChannelAssociationCollateral collateralForTokenReplacementOnDistribution4 = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/AQA_${XFER MMddyy}")
            .build();

    public static final ChannelAssociationCollateral collateralForTokenReplacementOnDistribution6 = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.OUTSIDE_SPINE_COVER)
            .destinationFolder("\\IMF\\spineCover\\${1SBN13}")
            .build();

    public static final ChannelAssociationCollateral collateralForTokenReplacementOnDistribution7 = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.OUTSIDE_BACK_COVER)
            .destinationFolder("/AQA-BackCover//${ISBN13}")
            .build();

    public static final ChannelAssociationCollateral collateralForTokenReplacementOnDistribution8 = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.OUTSIDE_FRONT_COVER)
            .destinationFolder("/covers/${ISBN13")
            .build();

    public static final ChannelAssociationCollateral collateralForTokenReplacementOnDistribution9 = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.HIGH_RESOLUTION_FRONT_COVER)
            .destinationFolder("/sftp/${ISBN10}/${FILENAME}")
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImageForMultipleProductTypesAndAssetsDistributionByPathsEPUB = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/epub/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImageForMultipleProductTypesAndAssetsDistributionByPathsPRINT= ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/print/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImageForMultipleProductTypesAndAssetsDistributionByPathsMOBI= ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/mobi/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImageForMultipleProductTypesAndAssetsDistributionByPathsPDF= ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .destinationFolder("/pdf/${ASSETTYPE}/")
            .build();

    private ChannelAssociationCollaterals() {
        //NONE
    }
}
