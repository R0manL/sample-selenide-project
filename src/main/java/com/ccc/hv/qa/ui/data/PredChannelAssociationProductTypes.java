package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.ProductType;
import com.ccc.hv.qa.ui.pojos.ChannelAssociationProductType;

import static com.ccc.hv.qa.ui.data.PredChannelAssociationContents.*;

public class PredChannelAssociationProductTypes {

    public static final ChannelAssociationProductType PRED_CHANNEL_ASSOCIATION_PRODUCT_TYPE_FTP = ChannelAssociationProductType.builder()
            .productType(ProductType.EPUB)
            .content(PRED_CHANNEL_ASSOCIATION_CONTENT_FTP)
            .collateral(PredChannelAssociationCollaterals.collateralScreenshot)
            .collateral(PredChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(PredChannelAssociationMetadata.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType PRED_CHANNEL_ASSOCIATION_PRODUCT_TYPE_SFTP = ChannelAssociationProductType.builder()
            .productType(ProductType.AUDIO)
            .content(PRED_CHANNEL_ASSOCIATION_CONTENT_SFTP)
            .collateral(PredChannelAssociationCollaterals.collateralScreenshot)
            .metadata(PredChannelAssociationMetadata.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType PRED_CHANNEL_ASSOCIATION_PRODUCT_TYPE_FTPS = ChannelAssociationProductType.builder()
            .productType(ProductType.PRINT)
            .content(PRED_CHANNEL_ASSOCIATION_CONTENT_FTPS)
            .metadata(PredChannelAssociationMetadata.metadataOnix21)
            .build();

    public static final ChannelAssociationProductType PRED_CHANNEL_ASSOCIATION_PRODUCT_TYPE_ITMS = ChannelAssociationProductType.builder()
            .productType(ProductType.IBOOK)
            .content(PRED_CHANNEL_ASSOCIATION_CONTENT_ITMS)
            .collateral(PredChannelAssociationCollaterals.collateralMarketingImage)
            .metadata(PredChannelAssociationMetadata.metadataOnix21)
            .build();


    private PredChannelAssociationProductTypes() {
        // NONE
    }
}
