package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.MetadataDistributionOption;

/**
 * Created by R0manL on 04/08/20.
 */

public class PredMetadataDistributionOptions {

    public static final MetadataDistributionOption PRED_METADATA_DISTR_OPTIONS_WITH_GROUP_BY_PUBLISHER_AND_IMPRINTENABLED = MetadataDistributionOption.builder()
            .groupByPublisherAndImprint(true)
            .build();

    public static final MetadataDistributionOption PRED_METADATA_DISTR_OPTIONS_WITH_SEND_METADATA_WITH_EVERY_ASSETENABLED = MetadataDistributionOption.builder()
            .sendMetadataWithEveryAsset(true)
            .build();


    private PredMetadataDistributionOptions() {
        //NONE
    }
}