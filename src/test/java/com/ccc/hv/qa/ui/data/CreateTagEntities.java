package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.CreateTagEntity;

import java.nio.file.Paths;

import static com.ccc.hv.qa.ui.enums.Tag.*;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;

public class CreateTagEntities {
    public static final CreateTagEntity TAG_ENTITY_FROM_BU = CreateTagEntity.builder()
            .tagName(generateUniqueStringBasedOnDate("aqa_tag_from_BU_"))
            .csvFilePath(Paths.get("regression/verifyUserCanOpenManageTagsFromManageBUsPage/Upload_isbn_for_tag_BU.csv"))
            .build();

    public static final CreateTagEntity TAG_ENTITY_FROM_CA_ADD_MODE = CreateTagEntity.builder()
            .tagName(generateUniqueStringBasedOnDate("aqa_tag_from_add_CA_"))
            .csvFilePath(Paths.get("regression/verifyUserCanOpenManageTagsFromCreateCAPage/Upload_isbn_for_tag_add_CA.csv"))
            .build();

    public static final CreateTagEntity TAG_ENTITY_FROM_CA_EDIT_MODE = CreateTagEntity.builder()
            .tagName(generateUniqueStringBasedOnDate("aqa_tag_from_edit_CA_"))
            .csvFilePath(Paths.get("regression/verifyUserCanOpenManageTagsFromEditCAPage/Upload_isbn_for_tag_edit_CA.csv"))
            .build();

    public static final CreateTagEntity TAG_ENTITY_TAG_MATCH_FOR_ONIX_21 = CreateTagEntity.builder()
            .tagName(MATCH_ONIX_21_TEST_TAG.getOptionValue())
            .csvFilePath(Paths.get("regression/verifyDistributeWhenTagMatchForOnix21/batch_ODD.csv"))
            .build();

    public static final CreateTagEntity TAG_ENTITY_TAG_MATCH_FOR_ONIX_30 = CreateTagEntity.builder()
            .tagName(MATCH_ONIX_30_TEST_TAG.getOptionValue())
            .csvFilePath(Paths.get("regression/verifyDistributeWhenTagMatchForOnix30/batch_ODD.csv"))
            .build();

    public static final CreateTagEntity TAG_ENTITY_TAG_DOES_NOT_MATCH_FOR_ONIX_21 = CreateTagEntity.builder()
            .tagName(DOES_NOT_MATCH_ONIX_21_TEST_TAG.getOptionValue())
            .csvFilePath(Paths.get("regression/verifyDistributeWhenTagDoesNotMatchForOnix21/batch_ODD.csv"))
            .build();

    public static final CreateTagEntity TAG_ENTITY_FOR_PRODUCT_CARD_FILTERING = CreateTagEntity.builder()
            .tagName(PROD_CARD_FILTERING_TAG.getOptionValue())
            .csvFilePath(Paths.get("regression/verifyProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet/batch_ODD.csv"))
            .build();
}
