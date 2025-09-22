package com.ccc.hv.qa.api.pojo;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 22/02/21.
 */

@Getter
@SuperBuilder
public class ChannelAPI {
    private final GeneralStep generalStep;
    private final ExclusiveStep exclusiveStep;
    private final DistributionStep distributionStep;
    private final ServersStep serversStep;
    private final BlackoutsStep blackoutsStep;
    private final ContactStep contactStep;
    private final CommentsStep commentsStep;
}
