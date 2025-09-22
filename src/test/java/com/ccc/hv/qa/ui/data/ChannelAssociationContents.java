package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.ContentType;
import com.ccc.hv.qa.ui.enums.Distribute;
import com.ccc.hv.qa.ui.pojos.ChannelAssociationContent;

/**
 * Created by R0manL on 23/09/20.
 */

public class ChannelAssociationContents {

    /**
     * Smoke test set
     */
    public static final ChannelAssociationContent smokeChannelAssociationContentFTP = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .distribute(Distribute.BEFORE)
            .daysBeforeOnSaleDate(30)
            .destinationFolder("/${ASSETTYPE}")
            .build();

    public static final ChannelAssociationContent smokeChannelAssociationContentSFTP = ChannelAssociationContent.builder()
            .contentType(ContentType.AUDIO)
            .distributeAudioContentInAZipNamed(true)
            .zipName("${ISBN13}")
            .build();

    public static final ChannelAssociationContent smokeChannelAssociationContentFTPS = ChannelAssociationContent.builder()
            .contentType(ContentType.PRINT_REPLICA)
            .watermarkThisAsset(true)
            .build();

    public static final ChannelAssociationContent smokeChannelAssociationContentSFTPEpubWatermarking = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .watermarkThisAsset(true)
            .destinationFolder("/Watermark/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationContent smokeChannelAssociationContentSFTPPdfWatermarking = ChannelAssociationContent.builder()
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .watermarkThisAsset(true)
            .destinationFolder("/Watermark/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationContent smokeChannelAssociationContentSFTPAudioDistribution = ChannelAssociationContent.builder()
            .contentType(ContentType.CD_AUDIO)
            .distributeAudioContentInAZipNamed(true)
            .destinationFolder("/audio/")
            .zipName("${ISBN13}")
            .build();

    public static final ChannelAssociationContent contentIBook = ChannelAssociationContent.builder()
            .contentType(ContentType.IBOOK)
            .build();
    /* end of smoke test set */

    /**
     * Regression test set
     */

    public static final ChannelAssociationContent contentEnhancedEpub = ChannelAssociationContent.builder()
            .contentType(ContentType.ENHANCED_EPUB)
            .build();

    public static final ChannelAssociationContent contentEpub = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .build();

    public static final ChannelAssociationContent contentPrintReplica = ChannelAssociationContent.builder()
            .contentType(ContentType.PRINT_REPLICA)
            .build();

    public static final ChannelAssociationContent contentWebOptimizedPdf = ChannelAssociationContent.builder()
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .build();

    public static final ChannelAssociationContent contentFixedLayoutEpub = ChannelAssociationContent.builder()
            .contentType(ContentType.FIXED_LAYOUT_EPUB)
            .build();

    public static final ChannelAssociationContent regressionCAContentFTPForAutoDistWithOnSaleDateAndOffsetInPastForOnix21 = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .distribute(Distribute.BEFORE)
            .daysBeforeOnSaleDate(9)
            .build();

    public static final ChannelAssociationContent regressionCAContentFTPForAutoDistWithOnSaleDateAndOnsetInPastForOnix21 = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .distribute(Distribute.AFTER)
            .daysBeforeOnSaleDate(10)
            .build();

    public static final ChannelAssociationContent regressionCAContentFTPForAutoDistWithOnSaleDateAndOffsetInPastForOnix30 = ChannelAssociationContent.builder()
            .contentType(ContentType.PRINT_REPLICA)
            .distribute(Distribute.BEFORE)
            .daysBeforeOnSaleDate(8)
            .build();

    public static final ChannelAssociationContent regressionCAContentFTPForAutoDistWithOnSaleDateAndOnsetInPastForOnix30 = ChannelAssociationContent.builder()
            .contentType(ContentType.PRINT_REPLICA)
            .distribute(Distribute.AFTER)
            .daysBeforeOnSaleDate(11)
            .build();

    public static final ChannelAssociationContent regressionCAContentFTPForAutoDistWithOnSaleDateInFutureAndOffsetOnix21 = ChannelAssociationContent.builder()
            .contentType(ContentType.MOBIPOCKET_MOBI)
            .distribute(Distribute.BEFORE)
            .daysBeforeOnSaleDate(3)
            .build();

    public static final ChannelAssociationContent regressionCAContentFTPForAutoDistWithOnSaleDateInFutureAndOnsetOnix21 = ChannelAssociationContent.builder()
            .contentType(ContentType.MOBIPOCKET_KPF)
            .distribute(Distribute.AFTER)
            .daysBeforeOnSaleDate(5)
            .build();

    public static final ChannelAssociationContent regressionCAContentFTPForAutoDistWithOnSaleDateInFutureAndOffsetOnix30 = ChannelAssociationContent.builder()
            .contentType(ContentType.SEARCH_PDF)
            .distribute(Distribute.BEFORE)
            .daysBeforeOnSaleDate(4)
            .build();

    public static final ChannelAssociationContent regressionCAContentFTPForAutoDistWithOnSaleDateInFutureAndOnsetOnix30 = ChannelAssociationContent.builder()
            .contentType(ContentType.SEARCH_PDF)
            .distribute(Distribute.AFTER)
            .daysBeforeOnSaleDate(7)
            .build();

    public static final ChannelAssociationContent regressionChannelAssociationContentFTPForPublisherName = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .destinationFolder("/epub/${PUBLISHER}/${XFER yyyyMMddHHmmssSSS}/")
            .build();

    public static final ChannelAssociationContent regressionChannelAssociationContentFTPForImprintName = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .destinationFolder("/ebooks/${IMPRINTCODE}/${ISBN13}/")
            .build();

    public static final ChannelAssociationContent regressionChannelAssociationContentFTPForSrvSettingsSendTriggerFile = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .destinationFolder("/${RECORDREFERENCE}/")
            .build();

    public static final ChannelAssociationContent regressionCAContentEPUBForSendMetadataWithEveryAsset = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .destinationFolder("/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationContent regressionCAContentSFTPForSendMetadataWithEveryAssetOnixNotSelected = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .destinationFolder("/${ASSETTYPE}/")
            .build();

    public static final ChannelAssociationContent regressionCAContentSFTPSettingsSendTriggerFileFailure = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .destinationFolder("/${RECORDREFERENCE}/")
            .build();

    public static final ChannelAssociationContent regressionCAContentSFTPForTokenReplacementOnDistribution1 = ChannelAssociationContent.builder()
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .destinationFolder("./${ISBN13}")
            .build();

    public static final ChannelAssociationContent regressionCAContentSFTPForTokenReplacementOnDistribution5 = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .destinationFolder("/Test/Auto_Publishing_Epub_${UPD MM.dd.YY-HHmmssSSS}")
            .build();

    public static final ChannelAssociationContent regressionCAContentSFTPForAutoDistrWithPublicationDateAndOffset = ChannelAssociationContent.builder()
            .contentType(ContentType.FIXED_LAYOUT_EPUB)
            .distribute(Distribute.BEFORE)
            .daysBeforeOnSaleDate(10)
            .build();

    public static final ChannelAssociationContent regressionCAContentFTPSForAutoDistrWithPublicationDateAndOnsetDate = ChannelAssociationContent.builder()
            .contentType(ContentType.PRINT_REPLICA)
            .distribute(Distribute.AFTER)
            .daysBeforeOnSaleDate(10)
            .build();

    public static final ChannelAssociationContent contentWebOptimizedPdfWithWatermarking = ChannelAssociationContent.builder()
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .build();

    public static final ChannelAssociationContent contentWebOptimizedPdfAltWithWatermarking = ChannelAssociationContent.builder()
            .contentType(ContentType.WEB_OPT_PDF_ALT)
            .build();

    public static final ChannelAssociationContent contentWebOptimizedPdfEnhWithWatermarking = ChannelAssociationContent.builder()
            .contentType(ContentType.WEB_OPT_PDF_ENH)
            .build();


    public static final ChannelAssociationContent contentKoboVariantEpub = ChannelAssociationContent.builder()
            .contentType(ContentType.KOBO_VARIANT_EPUB)
            .build();

    public static final ChannelAssociationContent contentAppleVariantEpub = ChannelAssociationContent.builder()
            .contentType(ContentType.APPLE_VARIANT_EPUB)
            .build();

    public static final ChannelAssociationContent contentBNVariantEpub = ChannelAssociationContent.builder()
            .contentType(ContentType.B_AND_N_VARIANT_EPUB)
            .build();

    public static final ChannelAssociationContent contentAmazonVariantEpub = ChannelAssociationContent.builder()
            .contentType(ContentType.AMAZON_VARIANT_EPUB)
            .build();

    public static final ChannelAssociationContent contentGoogleVariantEpub = ChannelAssociationContent.builder()
            .contentType(ContentType.GOOGLE_VARIANT_EPUB)
            .build();

    public static final ChannelAssociationContent contentKindleKPF = ChannelAssociationContent.builder()
            .contentType(ContentType.KINDLE_KPF)
            .build();

    public static final ChannelAssociationContent contentMobipocketPRC = ChannelAssociationContent.builder()
            .contentType(ContentType.MOBIPOCKET_PRC)
            .build();

    public static final ChannelAssociationContent contentMobipocketMobi = ChannelAssociationContent.builder()
            .contentType(ContentType.MOBIPOCKET_MOBI)
            .build();

    public static final ChannelAssociationContent contentEnhancedMobipocketMobi = ChannelAssociationContent.builder()
            .contentType(ContentType.ENHANCED_MOBIPOCKET_MOBI)
            .build();

    public static final ChannelAssociationContent contentKindlePackageFormat = ChannelAssociationContent.builder()
            .contentType(ContentType.FIXED_LAYOUT_KINDLE_KPF)
            .build();

    public static final ChannelAssociationContent contentEnhancedMobipocketPRC = ChannelAssociationContent.builder()
            .contentType(ContentType.ENHANCED_MOBIPOCKET_PRC)
            .build();

    public static final ChannelAssociationContent contentFixedLayoutePIB = ChannelAssociationContent.builder()
            .contentType(ContentType.FIXED_LAYOUT_EPIB)
            .build();

    public static final ChannelAssociationContent contentFixedLayoutKindleMobi = ChannelAssociationContent.builder()
            .contentType(ContentType.FIXED_LAYOUT_KINDLE_MOBI)
            .build();

    public static final ChannelAssociationContent contentFixedLayoutKindlePRC = ChannelAssociationContent.builder()
            .contentType(ContentType.FIXED_LAYOUT_KINDLE_PRC)
            .build();

    public static final ChannelAssociationContent contentEnhancedFixedEPUB = ChannelAssociationContent.builder()
            .contentType(ContentType.ENHANCED_FIXED_EPUB)
            .build();

    public static final ChannelAssociationContent contentEnhancedFixedePIB = ChannelAssociationContent.builder()
            .contentType(ContentType.ENHANCED_FIXED_EPIB)
            .build();

    public static final ChannelAssociationContent contentEnhancedEPUB = ChannelAssociationContent.builder()
            .contentType(ContentType.ENHANCED_EPUB)
            .build();

    public static final ChannelAssociationContent contentFixedLayoutEPUB = ChannelAssociationContent.builder()
            .contentType(ContentType.FIXED_LAYOUT_EPUB)
            .build();

    public static final ChannelAssociationContent contentPrintOnDemand = ChannelAssociationContent.builder()
            .contentType(ContentType.PRINT_ON_DEMAND)
            .build();

    public static final ChannelAssociationContent contentJacketPDF = ChannelAssociationContent.builder()
            .contentType(ContentType.JACKET_PDF)
            .build();

    public static final ChannelAssociationContent contentPrintOnDemandCover = ChannelAssociationContent.builder()
            .contentType(ContentType.PRINT_ON_DEMAND_COVER)
            .build();

    public static final ChannelAssociationContent contentPrintCover = ChannelAssociationContent.builder()
            .contentType(ContentType.PRINT_COVER)
            .build();

    public static final ChannelAssociationContent contentPrintOnDemandJacket = ChannelAssociationContent.builder()
            .contentType(ContentType.POD_JACKET)
            .build();

    public static final ChannelAssociationContent contentEPUBAlternate = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB_ALTERNATE)
            .build();

    public static final ChannelAssociationContent contentPubXML = ChannelAssociationContent.builder()
            .contentType(ContentType.PUB_XML)
            .build();

    public static final ChannelAssociationContent contentAudio = ChannelAssociationContent.builder()
            .contentType(ContentType.AUDIO)
            .build();

    public static final ChannelAssociationContent contentAudioCassette = ChannelAssociationContent.builder()
            .contentType(ContentType.AUDIO_CASSETTE)
            .build();

    public static final ChannelAssociationContent contentCDAudio = ChannelAssociationContent.builder()
            .contentType(ContentType.CD_AUDIO)
            .build();

    public static final ChannelAssociationContent contentDownloadableAudioFile = ChannelAssociationContent.builder()
            .contentType(ContentType.DOWNLOADABLE_AUDIO_FILE)
            .build();

    public static final ChannelAssociationContent contentCDROM = ChannelAssociationContent.builder()
            .contentType(ContentType.CD_ROM)
            .build();

    public static final ChannelAssociationContent contentEbook = ChannelAssociationContent.builder()
            .contentType(ContentType.EBOOK)
            .build();

    public static final ChannelAssociationContent contentCalendar = ChannelAssociationContent.builder()
            .contentType(ContentType.CALENDAR)
            .build();

    public static final ChannelAssociationContent contentCards = ChannelAssociationContent.builder()
            .contentType(ContentType.CARDS)
            .build();

    public static final ChannelAssociationContent contentDiary = ChannelAssociationContent.builder()
            .contentType(ContentType.DIARY)
            .build();

    public static final ChannelAssociationContent contentKit = ChannelAssociationContent.builder()
            .contentType(ContentType.KIT)
            .build();

    public static final ChannelAssociationContent contentPostcardBook = ChannelAssociationContent.builder()
            .contentType(ContentType.POSTCARD_BOOK)
            .build();

    public static final ChannelAssociationContent contentWalletOrFolder = ChannelAssociationContent.builder()
            .contentType(ContentType.WALLET_OR_FOLDER)
            .build();

    public static final ChannelAssociationContent contentStickers = ChannelAssociationContent.builder()
            .contentType(ContentType.STICKERS)
            .build();

    public static final ChannelAssociationContent contentOtherPrintedItem = ChannelAssociationContent.builder()
            .contentType(ContentType.OTHER_PRINTED_ITEM)
            .build();

    public static final ChannelAssociationContent contentDVDVideo = ChannelAssociationContent.builder()
            .contentType(ContentType.DVD_VIDEO)
            .build();

    public static final ChannelAssociationContent contentMixedMediaProduct = ChannelAssociationContent.builder()
            .contentType(ContentType.MIXED_MEDIA_PRODUCT)
            .build();

    public static final ChannelAssociationContent contentMultipleCopyPack = ChannelAssociationContent.builder()
            .contentType(ContentType.MULTIPLE_COPY_PACK)
            .build();

    public static final ChannelAssociationContent contentCounterpack = ChannelAssociationContent.builder()
            .contentType(ContentType.COUNTERPACK)
            .build();

    public static final ChannelAssociationContent contentJigsaw = ChannelAssociationContent.builder()
            .contentType(ContentType.JIGSAW)
            .build();

    public static final ChannelAssociationContent contentOtherMerchandise = ChannelAssociationContent.builder()
            .contentType(ContentType.OTHER_MERCHANDISE)
            .build();

    public static final ChannelAssociationContent contentSearchPDF = ChannelAssociationContent.builder()
            .contentType(ContentType.SEARCH_PDF)
            .build();

    public static final ChannelAssociationContent contentNookPagePerfectPDF = ChannelAssociationContent.builder()
            .contentType(ContentType.NOOK_PAGE_PERFECT)
            .build();

    public static final ChannelAssociationContent contentKindleMobi = ChannelAssociationContent.builder()
            .contentType(ContentType.KINDLE_MOBI)
            .build();

    public static final ChannelAssociationContent contentKindlePRC = ChannelAssociationContent.builder()
            .contentType(ContentType.KINDLE_PRC)
            .build();

    public static final ChannelAssociationContent contentGame = ChannelAssociationContent.builder()
            .contentType(ContentType.GAME)
            .build();

    public static final ChannelAssociationContent regressionCAContentAppleMetaEpub = ChannelAssociationContent.builder()
            .contentType(ContentType.EPUB)
            .build();
    /** end of Regression test set */


    private ChannelAssociationContents() {
        //NONE
    }
}
