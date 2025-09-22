package com.ccc.hv.qa.ui.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by R0manL on 04/08/20.
 */

@Getter
@Builder
public class EventView {
    @NonNull
    private final String startDate;
    private final String endDate;
    @NonNull
    private final String title;
}
