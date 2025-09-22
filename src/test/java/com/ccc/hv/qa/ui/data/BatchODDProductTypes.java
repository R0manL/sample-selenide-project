package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.CollateralType;
import com.ccc.hv.qa.ui.enums.ContentType;
import com.ccc.hv.qa.ui.enums.MetadataType;
import com.ccc.hv.qa.ui.enums.ProductType;
import com.ccc.hv.qa.ui.pojos.BatchODDProductTypesEntity;

public class BatchODDProductTypes {

    /**
     * Smoke test set
     */
    public static final BatchODDProductTypesEntity SMOKE_BATCH_ODD_PRODUCT_TYPE_ENTITY = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity SMOKE_BATCH_ODD_PRODUCT_TYPE_ENTITY_ONIX30 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REGRESSION_BATCH_ODD_PRODUCT_TYPE_ENTITY_GROUP_BY_PUB_IMPRINT = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX21)
            .build();
    /* end of Smoke test set */

    /**
     * Regression test set
     */
    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX_ONIX_30 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ONIX_FEED_DISTR_FAILURE = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ONIX_FEED_DISTR_SUCCESS_PATH = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_ON_HOLD_PRODUCT = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS_EPUB = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS_PRINT = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS_MOBI = BatchODDProductTypesEntity.builder()
            .productType(ProductType.MOBIPOCKET)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS_PDF = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_CONTENT_COLLATERAL_ENTITY_BATCH_ODD_LOCKED_PRODUCT = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX21)
            .contentType(ContentType.EPUB)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .build();

    public static final BatchODDProductTypesEntity REG_ONIX_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_LOCKED_PRODUCT = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_CONTENT_COLLATERAL_PRODUCT_ON_HOLD = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .contentType(ContentType.EPUB)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_3 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_4 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_5 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_6 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_7 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_8 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_9 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_10 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_11 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_12 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_13 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_14 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_15 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_16 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_17 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_18 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_19 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .contentType(ContentType.WEB_OPTIMIZED_PDF)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_NORM_OPT_21_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_NORM_OPT_21_GROUP_BY_PUBLISHER_AND_IMPRINT = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_NORM_OPT_30_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_NORM_OPT_30_GROUP_BY_PUBLISHER_AND_IMPRINT = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ENTITY_TAG_MATCH_FOR_ONIX_21 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ENTITY_TAG_MATCH_FOR_ONIX_30 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ENTITY_TAG_DOES_NOT_MATCH_FOR_ONIX_21 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_OPT_ONIX21 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_OPT_ONIX30 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_OPT_PARTIALLY_FAIL_ONIX30 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_OPT_PARTIALLY_FAIL_ONIX21 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_F1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_F2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_SUCCESS_1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_SUCCESS_2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_RULE_FAIL_PATH_1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.WEB_OPTIMIZED_PDF)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_RULE_FAIL_PATH_2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.PRINT)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2 = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_CONTENT_COLLATERAL_META = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .collateralType(CollateralType.MARKETING_IMAGE)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_NO_FILTER_SETUP = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX21)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_ALL_FILTERS_SETUP_AND_MATCH_NO_SALES_OUTLET = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_ALL_FILTERS_SETUP_AND_SOME_NOT_MATCH_NO_SALES_OUTLET = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX30)
            .build();

    public static final BatchODDProductTypesEntity REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_SALES_OUTLET = BatchODDProductTypesEntity.builder()
            .productType(ProductType.EPUB)
            .metadataType(MetadataType.ONIX30)
            .build();
    /* end of Regression test set */

    private BatchODDProductTypes() {
        // None
    }
}
