package com.ccc.hv.qa.api.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

/**
 * Created by R0manL on 11/12/20.
 */

@Getter
@Builder
public class KeycloakCreateUserRequest {
    @NonNull
    private final String application;
    @Singular
    private final List<KeycloakUser> users;
}
