package com.ccc.hv.qa.api.pojo;

import com.ccc.hv.qa.api.enums.BisacCodeFilterType;
import com.ccc.hv.qa.ui.enums.BisacSubjectCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 22/02/21.
 */

@Getter
@SuperBuilder
public class BisacCodeAPI {
    @NonNull
    private final BisacCodeFilterType filterType;
    @NonNull
    private final BisacSubjectCode code;
}
