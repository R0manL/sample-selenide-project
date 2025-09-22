package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.CollateralType;
import com.ccc.hv.qa.ui.pojos.ChannelAssociationCollateral;

public class PredChannelAssociationCollaterals {

    private PredChannelAssociationCollaterals() {
        //NONE
    }
    public static final ChannelAssociationCollateral collateralScreenshot = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.SCREENSHOT)
            .build();

    public static final ChannelAssociationCollateral collateralMarketingImage = ChannelAssociationCollateral.builder()
            .collateralType(CollateralType.MARKETING_IMAGE)
            .build();
}
