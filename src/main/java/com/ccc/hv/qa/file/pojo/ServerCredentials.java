package com.ccc.hv.qa.file.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by R0manL on 27/08/20.
 */

@Getter
@Builder
public class ServerCredentials {
    @NonNull
    private final String username;
    @NonNull
    private final String password;
}
