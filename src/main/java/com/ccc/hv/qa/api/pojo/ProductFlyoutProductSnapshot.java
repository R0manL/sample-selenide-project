package com.ccc.hv.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Created by R0manL on 4/28/22.
 */

@Getter
@Builder
public class ProductFlyoutProductSnapshot {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("tenantId")
    private final int tenantId;
    @JsonProperty("rows")
    private final List<List<String>> rows;
}
