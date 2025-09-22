package com.ccc.hv.qa.ui.enums;

import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;

/**
 * Created by R0manL on 17/09/20.
 */

public enum Tag {
    CORPORATE_IDENTITY_CONSULTANT("Corporate Identity Consultant"),
    DIRECT_FACTORS_TECHNICIAN("Direct Factors Technician"),
    FORWARD_DATA_MANAGER("Forward Data Manager"),
    GLOBAL_DIVISION_ORCHESTRATOR("Global Division Orchestrator"),
    REGIONAL_INTEGRATION_LIAISON("Regional Integration Liaison"),
    MATCH_ONIX_21_TEST_TAG(generateUniqueStringBasedOnDate("aqa_tag_match_onix_21_")),
    MATCH_ONIX_30_TEST_TAG(generateUniqueStringBasedOnDate("aqa_tag_match_onix_30_")),
    PROD_CARD_FILTERING_TAG(generateUniqueStringBasedOnDate("aqa_tag_prod_card_fltr_")),
    DOES_NOT_MATCH_ONIX_21_TEST_TAG(generateUniqueStringBasedOnDate("aqa_tag_do_not_match_onix_21_"));

    private final String optionValue;

    Tag(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionValue() {
        return this.optionValue;
    }

}
