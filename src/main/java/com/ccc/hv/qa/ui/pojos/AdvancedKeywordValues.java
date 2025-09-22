package com.ccc.hv.qa.ui.pojos;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 23/09/20.
 */

@Getter
@SuperBuilder
public class AdvancedKeywordValues {
    private final String transferTimestamp;
    private final String lastModifiedDateTime;
    private final String recordReference;
    private final String assetType;
    private final String fileName;
    private final String isbn13;
    private final String isbn10;
    private final String publisher;
}

