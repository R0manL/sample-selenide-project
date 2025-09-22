package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.MetadataDistributionOption;

/**
 * Created by R0manL on 23/09/20.
 */

public class MetadataDistributionOptions {

    public static final MetadataDistributionOption metadataDistrOptionsWithAllDisabledValues = MetadataDistributionOption.builder()
            .build();

    public static final MetadataDistributionOption sendSingleProductsInSingleOnixFile = MetadataDistributionOption.builder()
            .sendSingleProductsInSingleOnixFile(true)
            .build();

    public static final MetadataDistributionOption sendMetadataWithEveryAsset = MetadataDistributionOption.builder()
            .sendMetadataWithEveryAsset(true)
            .build();

    public static final MetadataDistributionOption groupByPublisherImprint = MetadataDistributionOption.builder()
            .groupByPublisherAndImprint(true)
            .build();


    private MetadataDistributionOptions() {
        //NONE
    }
}
