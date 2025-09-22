package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.MetadataType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ChannelAssociationMetadata {
    @NonNull
    private final MetadataType metadataType;
    private final String destinationFolder;

}
