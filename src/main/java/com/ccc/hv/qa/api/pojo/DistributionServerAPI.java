package com.ccc.hv.qa.api.pojo;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 25/08/20.
 * ATTENTION: This pojo does not represent real distribution server, use:
 * DistributionServerFTP, DistributionServerSFTP, DistributionServerFTPS or DistributionServerITMS instead.
 */

@Getter
@SuperBuilder
public class DistributionServerAPI {
    @NonNull
    private final String name;
    @NonNull
    private final DistributionProtocolAPI protocol;
}
