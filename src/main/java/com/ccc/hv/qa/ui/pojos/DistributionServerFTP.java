package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.DistributionServerMode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 25/08/20.
 */


@Getter
@SuperBuilder
public class DistributionServerFTP extends DistributionServer {
    private final DistributionServerMode mode;
    private final int port;
    @NonNull
    private final String hostName;
}
