package com.ccc.hv.qa.api.pojo;

import lombok.*;

import java.util.List;

/**
 * Created by R0manL on 11/12/20.
 */

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class KeycloakResponse {
    boolean error;
    @Singular
    List<KeycloakResult> results;
}
