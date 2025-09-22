package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.Tag;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 17/09/20.
 */
@Getter
@SuperBuilder
public class TagFilter {
    private final boolean distribute;
    private final Tag tagToUse;
}
