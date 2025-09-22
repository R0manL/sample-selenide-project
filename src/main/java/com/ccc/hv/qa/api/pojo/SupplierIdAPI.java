package com.ccc.hv.qa.api.pojo;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 22/02/21.
 */

@Getter
@SuperBuilder
public class SupplierIdAPI {
    @NonNull
    private final String supplierId;
    @NonNull
    private final String description;
}
