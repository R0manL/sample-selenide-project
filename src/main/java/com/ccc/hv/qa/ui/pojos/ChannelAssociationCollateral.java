package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.CollateralType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ChannelAssociationCollateral {
    @NonNull
    private final CollateralType collateralType;
    private final String destinationFolder;
}
