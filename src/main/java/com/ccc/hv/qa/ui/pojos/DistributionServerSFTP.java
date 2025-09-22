package com.ccc.hv.qa.ui.pojos;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 25/08/20.
 */


@Getter
@SuperBuilder
public class DistributionServerSFTP extends DistributionServer {
    private final int port;
    @NonNull
    private final String hostName;
}
