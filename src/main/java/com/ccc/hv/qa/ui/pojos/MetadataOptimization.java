package com.ccc.hv.qa.ui.pojos;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by R0manL on 22/09/20.
 */

@Getter
@SuperBuilder
public class MetadataOptimization {
    @NotNull
    private final String ruleFileRelPath;
    private final boolean isOnix30Rule;
    private final String comment;
    private final boolean labelOnix30;
    private final boolean labelAppleXML;
    private final boolean labelCustom;
    private final String labelCustomText;
}
