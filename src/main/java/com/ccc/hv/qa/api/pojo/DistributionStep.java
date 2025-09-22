package com.ccc.hv.qa.api.pojo;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 22/02/21.
 */

@Getter
@SuperBuilder
public class DistributionStep {
    @NonNull
    private final String supportAdvancedKeywords;
    private final String salesOutlet;
    private final String propSalesOutlet;
    private final String propSalesOutletType;
}
