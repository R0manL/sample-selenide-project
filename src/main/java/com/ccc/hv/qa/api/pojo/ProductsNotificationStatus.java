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
public class ProductsNotificationStatus {
    @JsonProperty("pia")
    private final List<ProductsIngestBUStatus> pia;
    @JsonProperty("foa")
    private final List<ProductIngestFailedBUStatus> foa;
    @JsonProperty("oa")
    private final List<Object> oa;
}
