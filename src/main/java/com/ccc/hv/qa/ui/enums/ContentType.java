package com.ccc.hv.qa.ui.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by R0manL on 01/09/20.
 */

public enum ContentType {
    EPUB("EPUB", 13),
    GOOGLE_VARIANT_EPUB("Google Variant EPUB", 28),
    AMAZON_VARIANT_EPUB("Amazon Variant EPUB", 0),
    APPLE_VARIANT_EPUB("Apple Variant EPUB", 1),
    KOBO_VARIANT_EPUB("Kobo Variant EPUB", 35),
    B_AND_N_VARIANT_EPUB("B&N Variant EPUB", 4),
    EPUB_ALTERNATE("EPUB, Alternate", 14),
    IBOOK("iBook", 58),
    FIXED_LAYOUT_EPIB("Fixed Layout ePIB", 26),
    FIXED_LAYOUT_EPUB("Fixed Layout EPUB", 22),
    ENHANCED_FIXED_EPUB("Enhanced Fixed EPUB", 17),
    ENHANCED_FIXED_EPIB("Enhanced Fixed ePIB", 18),
    FIXED_LAYOUT_KINDLE_KPF("Kindle Package Format", 23),
    FIXED_LAYOUT_KINDLE_MOBI("Fixed Layout Kindle.mobi", 24),
    FIXED_LAYOUT_KINDLE_PRC("Fixed Layout Kindle.prc", 25),
    EBOOK("Ebook", 15),
    ENHANCED_EPUB("Enhanced EPUB", 16),
    ENHANCED_MOBIPOCKET_MOBI("Enhanced Mobipocket.mobi", 20),
    NOOK_PAGE_PERFECT("Nook Page Perfect PDF", 41),
    PUB_XML("Pub XML", 50),
    AUDIO("Audio", 2),
    AUDIO_CASSETTE("Audio Cassette", 3),
    CD_AUDIO("CD-Audio", 5),
    DOWNLOADABLE_AUDIO_FILE("Downloadable Audio File", 12),
    CD_ROM("CD-ROM", 6),
    CALENDAR("Calendar", 7),
    CARDS("Cards", 8),
    PRINT_REPLICA("Print Replica", 49),
    PRINT_ON_DEMAND("Print On Demand", 47),
    PRINT_COVER("Print Cover", 46),
    PRINT_ON_DEMAND_COVER("Print On Demand Cover", 48),
    JACKET_PDF("Jacket PDF", 29),
    POD_JACKET("Print On Demand Jacket", 44),
    SEARCH_PDF("Search PDF", 51),
    KINDLE_PRC("Kindle.prc", 33),
    KINDLE_MOBI("Kindle.mobi", 32),
    KINDLE_KPF("Kindle Package Format", 31),
    MOBIPOCKET_PRC("Mobipocket.prc", 39),
    MOBIPOCKET_MOBI("Mobipocket.mobi", 38),
    MOBIPOCKET_KPF("Kindle Package Format", 37),
    WEB_OPT_PDF_ALT("Web PDF, Alternate", 55),
    WEB_OPT_PDF_ENH("Web PDF, Enhanced", 56),
    WEB_OPTIMIZED_PDF("Web Optimized PDF", 57),
    ENHANCED_MOBIPOCKET_PRC("Enhanced Mobipocket.prc", 0),
    DIARY("Diary", 0),
    KIT("Kit", 0),
    POSTCARD_BOOK("Postcard Book", 0),
    WALLET_OR_FOLDER("Wallet Or Folder", 0),
    STICKERS("Stickers", 0),
    OTHER_PRINTED_ITEM("Other Printed Item", 0),
    DVD_VIDEO("DVD Video", 0),
    MIXED_MEDIA_PRODUCT("Mixed Media Product", 0),
    MULTIPLE_COPY_PACK("Multiple Copy Pack", 0),
    COUNTERPACK("Counterpack", 0),
    JIGSAW("Jigsaw", 0),
    OTHER_MERCHANDISE("Other Merchandise", 0),
    GAME("Game", 0),
    UNKNOWN_CONTENT("Unknown Content", 53);

    private final String text;
    private final int assetTypeId;

    ContentType(String text, int assetTypeId) {
        this.text = text;
        this.assetTypeId = assetTypeId;
    }

    public String getText() {
        return this.text;
    }

    public int getAssetTypeId() {
        return this.assetTypeId;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static ContentType fromText(@NotNull String text) {
        return Arrays.stream(values()).sequential()
                .filter(contentType -> contentType.toString().equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Content Type with text '" + text + "' has been found."));
    }
}
