package com.ccc.hv.qa.ui.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by R0manL on 01/09/20.
 */

public enum ProductType {
    WEB_OPTIMIZED_PDF("Web Optimized PDF", 3),
    EPUB("EPUB", 4),
    MOBIPOCKET("Mobipocket", 5),
    IBOOK("iBook", 6),
    ENHANCED_MOBIPOCKET("Enhanced Mobipocket", 7),
    FIXED_LAYOUT_EPIB("Fixed Layout ePIB", 8),
    FIXED_LAYOUT_KINDLE("Fixed Layout Kindle", 9),
    ENHANCED_FIXED_EPUB("Enhanced Fixed EPUB", 10),
    ENHANCED_FIXED_EPIB("Enhanced Fixed ePIB", 11),
    ENHANCED_EPUB("Enhanced EPUB", 12),
    FIXED_LAYOUT_EPUB("Fixed Layout EPUB", 13),
    PRINT("Print", 14),
    WEB_OPTIMIZED_PDF_ALTERNATE("Web Optimized PDF, Alternate", 15),
    WEB_OPTIMIZED_PDF_ENHANCED("Web Optimized PDF, Enhanced", 16),
    EPUB_ALTERNATE("EPUB, Alternate", 17),
    PUB_XML("Pub XML", 18),
    AUDIO("Audio", 19),
    AUDIO_CASSETTE("Audio Cassette", 20),
    CD_AUDIO("CD-Audio", 21),
    DOWNLOADABLE_AUDIO_FILE("Downloadable Audio File", 22),
    CD_ROM("CD-ROM", 23),
    EBOOK("Ebook", 24),
    CALENDAR("Calendar", 25),
    CARDS("Cards", 26),
    DIARY("Diary", 27),
    KIT("Kit", 28),
    POSTCARD_BOOK("Postcard Book", 29),
    WALLET_OR_FOLDER("Wallet Or Folder", 30),
    STICKERS("Stickers", 31),
    OTHER_PRINTED_ITEM("Other Printed Item", 32),
    DVD_VIDEO("DVD Video", 33),
    MIXED_MEDIA_PRODUCT("Mixed Media Product", 34),
    MULTIPLE_COPY_PACK("Multiple Copy Pack", 35),
    COUNTERPACK("Counterpack", 36),
    JIGSAW("Jigsaw", 37),
    OTHER_MERCHANDISE("Other Merchandise", 38),
    SEARCH_PDF("Search PDF", 39),
    NOOK_PAGE_PERFECT_PDF("Nook Page Perfect PDF", 40),
    KINDLE("Kindle", 41),
    GAME("Game", 42);


    private final String text;
    private final int id;

    ProductType(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static ProductType fromText(@NotNull String text) {
        return Arrays.stream(values()).sequential()
                .filter(productType -> productType.toString().equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Product Type with text " + text + " has been found"));
    }
}
