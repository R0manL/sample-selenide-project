package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.ContentType;
import com.ccc.hv.qa.ui.enums.Distribute;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 25/08/20.
 */

@Getter
@SuperBuilder
public class ChannelAssociationContent {
    @NonNull
    private final ContentType contentType;
    private final Distribute distribute;
    private final int daysBeforeOnSaleDate;
    private final boolean watermarkThisAsset;
    private final String destinationFolder;
    private final boolean distributeAudioContentInAZipNamed;
    private final String zipName;
}
