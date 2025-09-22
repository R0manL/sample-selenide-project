package com.ccc.hv.qa.api.pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Created by R0manL on 11/12/20.
 */

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class KeycloakResult {
    String code;
    boolean error;
    String message;
    String email;
    String id;
    String username;
}
