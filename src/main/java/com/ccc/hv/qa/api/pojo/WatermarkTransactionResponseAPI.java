package com.ccc.hv.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by R0manL on 04/08/20.
 */

@Getter
@Builder
public class WatermarkTransactionResponseAPI {
    @JsonProperty("buName")
    private final String buName;
    @JsonProperty("channelName")
    private final String channelName;
}
