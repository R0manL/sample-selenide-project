package com.ccc.hv.qa.ui.pojos;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by R0manL on 22/09/20.
 */

@Getter
@SuperBuilder
public class NormalizationRule {
    private final boolean isOnix30;
    @NotNull
    private final String rule;
    private final String comment;
}
