package com.ccc.hv.qa.api.pojo;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 22/02/21.
 */

@Getter
@SuperBuilder
public class KeywordAPI {
    @NonNull
    private final String defaultValue;
    @NonNull
    private final String type;
    @NonNull
    private final String replacement;
    @NonNull
    private final String value;
}
