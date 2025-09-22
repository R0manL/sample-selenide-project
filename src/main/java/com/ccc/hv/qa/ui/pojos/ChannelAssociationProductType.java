package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.ProductType;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import lombok.NonNull;

import java.util.Set;

@Getter
@SuperBuilder
public class ChannelAssociationProductType {
    @NonNull
    private final ProductType productType;
    @Singular
    private final Set<ChannelAssociationContent> contents;
    @Singular
    private final Set<ChannelAssociationCollateral> collaterals;
    @Singular
    private final Set<ChannelAssociationMetadata> metadatas;
}
