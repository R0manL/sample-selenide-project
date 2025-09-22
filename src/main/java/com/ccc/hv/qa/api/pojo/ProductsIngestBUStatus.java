package com.ccc.hv.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by R0manL on 4/28/22.
 */

@Getter
@Builder
public class ProductsIngestBUStatus {
    @JsonProperty("addedToday")
    public int addedToday;
    @JsonProperty("updatedToday")
    public int updatedToday;
    @JsonProperty("type")
    public String type;
    @JsonProperty("creationTime")
    public String creationTime;
    @JsonProperty("expirationTime")
    public String expirationTime;
    @JsonProperty("expired")
    public boolean expired;
    @JsonProperty("lastUpdatedTime")
    public String lastUpdatedTime;
    @JsonProperty("offset")
    public String offset;
    @JsonProperty("selfExpiring")
    public boolean selfExpiring;
    @JsonProperty("severity")
    public String severity;
}
