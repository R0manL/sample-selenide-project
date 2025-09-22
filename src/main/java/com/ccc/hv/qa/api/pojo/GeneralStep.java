package com.ccc.hv.qa.api.pojo;

import com.ccc.hv.qa.ui.enums.HrvViewMarketMapping;
import com.ccc.hv.qa.ui.enums.Headquarter;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Created by R0manL on 22/02/21.
 */

@Getter
@SuperBuilder
public class GeneralStep {
    @NonNull
    private final String name;
    private final HrvViewMarketMapping market;
    @NonNull
    private final Headquarter headquarters;
    private final List<Headquarter> footprint;
    @NonNull
    private final String timezone;
    @NonNull
    private final String retryInterval;
    @NonNull
    private final String threshold;
}
