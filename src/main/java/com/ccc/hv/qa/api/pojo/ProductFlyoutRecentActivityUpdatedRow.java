package com.ccc.hv.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by R0manL on 4/28/22.
 */

@Getter
@Builder
public class ProductFlyoutRecentActivityUpdatedRow {
    @JsonProperty("updated")
    private final int updated;
    @JsonProperty("contentType")
    private final String contentType;
    @JsonProperty("timestamp")
    private final String timeStamp;
}
