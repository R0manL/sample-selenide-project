package com.ccc.hv.qa.ui.pojos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Created by R0manL on 25/08/20.
 */

@Getter
@SuperBuilder
public class ChannelExclusive extends Channel {
    @NonNull
    @Singular private final List<BusinessUnit> businessUnits;
}
