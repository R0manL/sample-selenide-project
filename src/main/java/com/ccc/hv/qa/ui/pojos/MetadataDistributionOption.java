package com.ccc.hv.qa.ui.pojos;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 25/08/20.
 *  ATTENTION: This pojo does not represent real distribution server, use:
 *  DistributionServerFTP, DistributionServerSFTP, DistributionServerFTPS or DistributionServerITMS instead.
 */

@Getter
@SuperBuilder
public class MetadataDistributionOption {
    private final boolean groupByPublisherAndImprint;
    private final boolean sendMetadataWithEveryAsset;
    private final boolean sendSingleProductsInSingleOnixFile;
}
