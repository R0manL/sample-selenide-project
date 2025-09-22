package com.ccc.hv.qa.api.pojo;

import com.ccc.hv.qa.api.enums.AssetType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 22/02/21.
 */

@Getter
@SuperBuilder
public class AssetTypeAPI {
    private int id;
    @NonNull
    private final AssetType groupType;
}
