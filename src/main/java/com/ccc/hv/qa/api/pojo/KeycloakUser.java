package com.ccc.hv.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

/**
 * Created by R0manL on 11/12/20.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class KeycloakUser {
    @JsonProperty("id")
    String id;
    @NonNull
    @JsonProperty("email")
    String email;
    @NonNull
    @JsonProperty("username")
    String username;
    @JsonProperty("firstName")
    String firstName;
    @JsonProperty("lastName")
    String lastName;
}
