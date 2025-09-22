package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.Keyword;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by R0manL on 23/09/20.
 */

@Getter
@SuperBuilder
public class AdvancedKeywordSetting {
    private final Keyword keyword;
    @NotNull
    private final String sourceFieldValue;
    @NotNull
    private final String replacementValue;
    private final boolean makeDefault;
}

