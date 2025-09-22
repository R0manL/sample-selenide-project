package com.ccc.hv.qa.api.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by R0manL on 22/02/21.
 */

@Data
@Builder
public class DeliverableAssetAPI {
    @NonNull
    private final AssetTypeAPI assetType;
    @NonNull
    private final String path;
    @NonNull
    private final ProductTypeAPI productType;
    private final int offset;
    private final boolean watermark;
    private final boolean bundleDistribution;
    private final String bundleName;
}
