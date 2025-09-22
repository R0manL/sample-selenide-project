package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.MetadataType;
import com.ccc.hv.qa.ui.pojos.ChannelAssociationMetadata;

public class PredChannelAssociationMetadata {

    private PredChannelAssociationMetadata() {
        //NONE
    }

    public static final ChannelAssociationMetadata metadataOnix21 = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX21)
            .build();
}
