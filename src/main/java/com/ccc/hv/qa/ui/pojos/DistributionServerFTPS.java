package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.DistributionServerMode;
import com.ccc.hv.qa.ui.enums.DistributionServerSecurity;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 25/08/20.
 */


@Getter
@SuperBuilder
public class DistributionServerFTPS extends DistributionServer {
    private final DistributionServerMode mode;
    private final DistributionServerSecurity security;
    private final int port;
    @NonNull
    private final String hostName;
}
