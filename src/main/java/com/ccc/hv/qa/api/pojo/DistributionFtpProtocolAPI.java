package com.ccc.hv.qa.api.pojo;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 25/08/20.
 */


@Getter
@SuperBuilder
public class DistributionFtpProtocolAPI extends DistributionProtocolAPI {
    @NonNull
    private final String mode;
    @NonNull
    private final String host;
    @NonNull
    private final String portString;
}
