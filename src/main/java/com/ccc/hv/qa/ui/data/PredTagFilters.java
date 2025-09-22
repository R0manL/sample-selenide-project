package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.TagFilter;

import static com.ccc.hv.qa.ui.enums.Tag.*;

/**
 * Created by R0manL on 17/09/20.
 */

public class PredTagFilters {

    public static final TagFilter MATCH_ONIX_21_TAG_FILTER = TagFilter.builder()
            .distribute(true)
            .tagToUse(MATCH_ONIX_21_TEST_TAG)
            .build();

    public static final TagFilter MATCH_ONIX_30_TAG_FILTER = TagFilter.builder()
            .distribute(true)
            .tagToUse(MATCH_ONIX_30_TEST_TAG)
            .build();

    public static final TagFilter DOE_NOT_MATCH_ONIX_21_TAG_FILTER = TagFilter.builder()
            .distribute(true)
            .tagToUse(DOES_NOT_MATCH_ONIX_21_TEST_TAG)
            .build();

    public static final TagFilter PRODUCT_CARD_FILTERING_TAG_FILTER = TagFilter.builder()
            .distribute(true)
            .tagToUse(PROD_CARD_FILTERING_TAG)
            .build();


    private PredTagFilters() {
        // NONE
    }
}
