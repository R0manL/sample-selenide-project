package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.MetadataType;
import com.ccc.hv.qa.ui.pojos.ChannelAssociationMetadata;

public class ChannelAssociationMetadatas {

    public static final ChannelAssociationMetadata metadataOnix21 = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final ChannelAssociationMetadata metadataOnix30 = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final ChannelAssociationMetadata metadataOnix21PublisherName = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX21)
            .destinationFolder("/onix/${PUBLISHER}/${XFER yyyyMMddHHmmssSSS}/")
            .build();

    public static final ChannelAssociationMetadata metadataOnix21ImprintName = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX21)
            .destinationFolder("/onix/${IMPRINTCODE}/${XFER yyyyMMddHHmmssSSS}/")
            .build();

    public static final ChannelAssociationMetadata tokenReplacementOnDistribution2 = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX21)
            .destinationFolder("/Austin/FromHrv/${RECORDREFERENCE}/AQA_SA_${XFER yyyyMMddHHmmssSSS}_onix")
            .build();

    public static final ChannelAssociationMetadata metadataOnix21tokenReplacementForOnixFeedDistXFER = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX21)
            .destinationFolder("/Automation/${XFER yyyyMMddHHmmssSSS}/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationMetadata metadataOnix21tokenReplacementForOnixFeedDistUPD = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX21)
            .destinationFolder("/Automation/${UPD yyyyMMddHHmmssSSS}/${ASSETTYPE}/")
            .build();
    public static final ChannelAssociationMetadata metadataTokenReplacementIgnoredForOnixFeedDistribution = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX21)
            .destinationFolder("/${RECORDREFERENCE}/")
            .build();

    public static final ChannelAssociationMetadata metadataSendSingleProductsInSingleOnixFileOnix30 = ChannelAssociationMetadata.builder()
            .metadataType(MetadataType.ONIX30)
            .destinationFolder("/${ASSETTYPE}/")
            .build();

    private ChannelAssociationMetadatas() {
        //NONE
    }
}
