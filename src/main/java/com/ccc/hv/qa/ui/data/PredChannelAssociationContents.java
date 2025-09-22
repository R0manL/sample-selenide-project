package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.ContentType;
import com.ccc.hv.qa.ui.enums.Distribute;
import com.ccc.hv.qa.ui.pojos.ChannelAssociationContent;

/**
 * Created by R0manL on 04/08/20.
 */

public class PredChannelAssociationContents {

    public static final ChannelAssociationContent PRED_CHANNEL_ASSOCIATION_CONTENT_FTP = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .distribute(Distribute.BEFORE)
            .daysBeforeOnSaleDate(30)
            .destinationFolder("/${ASSETTYPE}")
            .build();

    public static final ChannelAssociationContent PRED_CHANNEL_ASSOCIATION_CONTENT_SFTP = ChannelAssociationContent.builder()
            .contentType(ContentType.AUDIO)
            .distributeAudioContentInAZipNamed(true)
            .zipName("${ISBN13}")
            .build();

    public static final ChannelAssociationContent PRED_CHANNEL_ASSOCIATION_CONTENT_FTPS = ChannelAssociationContent.builder()
            .contentType(ContentType.PRINT_REPLICA)
            .watermarkThisAsset(true)
            .build();

    public static final ChannelAssociationContent PRED_CHANNEL_ASSOCIATION_CONTENT_ITMS = ChannelAssociationContent.builder()
            .contentType(ContentType.IBOOK)
            .build();


    private PredChannelAssociationContents() {
        //NONE
    }
}