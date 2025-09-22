package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.CollateralType;
import com.ccc.hv.qa.ui.enums.ContentType;
import com.ccc.hv.qa.ui.enums.MetadataType;
import com.ccc.hv.qa.ui.enums.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Builder
public class BatchODDProductTypesEntity {
    @NotNull
    private final ProductType productType;
    @Singular
    private final Set<ContentType> contentTypes;
    @Singular
    private final Set<CollateralType> collateralTypes;
    @Singular
    private final Set<MetadataType> metadataTypes;
}
