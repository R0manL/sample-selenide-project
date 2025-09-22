package com.ccc.hv.qa.api.pojo;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Created by R0manL on 25/08/20.
 * ATTENTION: This pojo does not represent real distribution server, use:
 * DistributionServerFTP, DistributionServerSFTP, DistributionServerFTPS or DistributionServerITMS instead.
 */

@Getter
@SuperBuilder
public class ChannelServerAssociationAPI {
    @NonNull
    private final ServerAssociationProtocolAPI protocol;
    private final List<DeliverableAssetAPI> deliverableAssets;

    private final boolean drmFree;
    private final boolean preorderPreviews;
    private final boolean sendIndividualOnix;
}
