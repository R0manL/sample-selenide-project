package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.HrvViewMarketMapping;
import com.ccc.hv.qa.ui.enums.OnixSalesOutletIdCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Created by R0manL on 25/08/20.
 */

@Getter
@SuperBuilder
public class ChannelPublic extends Channel {
    private final HrvViewMarketMapping hrvViewMarketMapping;
    private final boolean makeExclusive;
    @Singular private final List<BusinessUnit> exclusiveBusinessUnits;
    private final boolean distributeByAdvancedKeyword;
    private final OnixSalesOutletIdCode onixSalesOutletIDCode;
}
