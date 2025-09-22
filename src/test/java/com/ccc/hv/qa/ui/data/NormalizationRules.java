package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.NormalizationRule;

import java.nio.file.Paths;

import static com.ccc.hv.qa.file.services.XmlFileService.readXmlFileToString;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;

/**
 * Created by R0manL on 22/09/20.
 */

public class NormalizationRules {

    /* Smoke tests */
    public static final NormalizationRule SMOKE_SIMPLE_NORMALIZATION_RULE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("smoke/verifyNormalizationRules/simpleNormalizeProfile.xml")))
            .build();
    /* end of Smoke tests */

    /* Regression tests */

    public static final NormalizationRule ADD_ELEMENT_NORMALIZATION_RULE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesAddElement/AddElement_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule SUBSTITUTE_ELEMENT_NORMALIZATION_RULE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesSubstituteElement/Substitute_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORMALIZATION_RULE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesRemoveElement/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMA_LONG_TO_SHORT = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationLongToShortFormat/simpleNormalizeProfile.xml")))
            .build();

    public static final NormalizationRule REGRESSION_NORMA_SHORT_21_TO_30 = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptWithConversionOnixFrom21To30/simpleNormalizeProfile.xml")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_SEND_ONIX_21_SINGLE_FILE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRules21WithDistributeAsSingleOnixFileOption/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_SEND_ONIX_SINGLE_FILE_EMPTY_30_RULE = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRules21WithDistributeAsSingleOnixFileOption/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRulesMetadataActivityOnix21/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY_30_RULE = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRulesMetadataActivityOnix21/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_ONIX_30_METADATA_ACTIVITY = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRulesMetadataActivityOnix30/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_ONIX_30_METADATA_ACTIVITY_30_RULE = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRulesMetadataActivityOnix30/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_ONIX_30_SKIP_COUNT_METADATA_ACTIVITY = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyMultipleIngestionSkipCountOfZIPOnix30File/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_ONIX_30_SKIP_COUNT_METADATA_ACTIVITY_30_RULE = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyMultipleIngestionSkipCountOfZIPOnix30File/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_OPT_NORM_FAIL_METADATA_ACTIVITY = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimization21FailedMetadataActivity/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_OPT_NORM_FAIL_METADATA_ACTIVITY_30_RULE = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimization21FailedMetadataActivity/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_ONIX_30_OPT_NORM_FAIL_METADATA_ACTIVITY = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimization30FailedMetadataActivity/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_ONIX_30_OPT_NORM_FAIL_METADATA_ACTIVITY_30_RULE = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimization30FailedMetadataActivity/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY_SKIP_COUNT = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyMultipleIngestionSkipCountOfZIPOnix21File/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORM_RULE_METADATA_ACTIVITY_SKIP_COUNT_30_RULE = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyMultipleIngestionSkipCountOfZIPOnix21File/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule NORMALIZATION_OPTIMIZATION_FAILED_PATH_REMOVE_RULE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationFailedPath/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMA_MULTIPLY = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesMultiply/Multiply_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule NORMA_REMOVE_RULE_21_GROUP_BY_PUBLISHER_AND_IMPRINT = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRules21WithGroupByPublisherAndImprint/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule NORMA_EMPTY_RULE_30_GROUP_BY_PUBLISHER_AND_IMPRINT = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRules21WithGroupByPublisherAndImprint/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMALIZATION_FAIL_WITH_INVALID_ZIP_FILE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationFailWhenInvalidZipFile/Substitute_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMALIZATION_FAIL_WITH_INVALID_NORMA_RULE_UNREC_REC_SOURCE_NAME = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationFailWhenInvalidNormaRuleWithUnrecognizableRecordSourceName/Substitute_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMALIZATION_FAIL_WITH_INVALID_NORMA_RULE_SYNTAX_ERROR = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationFailWhenInvalidNormaRuleWithSyntaxError/Substitute_Normalization_profile-Invalid_syntax.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMA_RULE_WITH_COMMENT = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyBuEditNormalizationRuleWithComment/simple_Normalization_profile.txt")))
            .comment(generateUniqueStringBasedOnDate("Regression-BU-norma-rule-comment"))
            .build();

    public static final NormalizationRule REGRESSION_NORMALIZATION_EMPTY_RULE_OPT21_APPLE_METADATA = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyOptimization21AddRemoveSubstituteTransformationAppleMetadata/Empty_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMA_ONIX30_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOnix30WhenBoth30And21NormaProfilesAreSetup/Add_3.0-config.txt")))
            .isOnix30(true)
            .build();

    public static final NormalizationRule REGRESSION_NORMA_ONIX21_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOnix30WhenBoth30And21NormaProfilesAreSetup/AddElement_21_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule EMPTY_ADD_NORMALIZATION_RULE_ONIX_21 = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesAddElementOnix30/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule ADD_NORMALIZATION_RULE_ONIX_30 = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesAddElementOnix30/Add_config_3.0.txt")))
            .build();

    public static final NormalizationRule EMPTY_SUBSTITUTE_ELEMENT_NORMALIZATION_RULE_ONIX21 = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesSubstituteElementOnix30/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule SUBSTITUTE_ELEMENT_NORMALIZATION_RULE_ONIX30 = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesSubstituteElementOnix30/Substitute_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_REMOVE_INVALID_NORMALIZATION_30_RULE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOnix30WhenInvalid30AndValid21NormaProfilesAreSetup/Add_3.0-invalid.txt")))
            .isOnix30(true)
            .build();

    public static final NormalizationRule REGRESSION_REMOVE_VALID_NORMALIZATION_21_RULE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOnix30WhenInvalid30AndValid21NormaProfilesAreSetup/AddElement_21_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REMOVE_ELEMENT_NORMALIZATION_RULE_ONIX_30 = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesRemoveElementOnix30/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule EMPTY_REMOVE_NORMALIZATION_RULE_ONIX_21 = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesRemoveElementOnix30/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMA30_INVALID_NORMALIZATION_30_RULE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalization30WhenNormaRule30IsInvalid/Add_3.0-invalid.txt")))
            .isOnix30(true)
            .build();

    public static final NormalizationRule REGRESSION_NORMA30_EMPTY_RULE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalization30WhenNormaRule30IsInvalid/Empty_Normalization_profile.txt")))
            .build();


    public static final NormalizationRule EMPTY_MULTIPLY_NORMALIZATION_RULE_ONIX_21 = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesMultiplyOnix30/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule MULTIPLY_NORMALIZATION_RULE_ONIX_30 = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationRulesMultiplyOnix30/Multiply_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMA_ONIX30_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES_2 = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOnix21WhenBoth30And21NormaProfilesAreSetup/Add_3.0-config.txt")))
            .isOnix30(true)
            .build();

    public static final NormalizationRule REGRESSION_NORMA_ONIX21_RULE_FOR_TEST_THAT_CHECK_BOTH_30_21_PROFILES_2 = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOnix21WhenBoth30And21NormaProfilesAreSetup/AddElement_21_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule REGRESSION_NORMA_LONG_TO_LONG_ONIX_21 = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationLongToLongFormatOnix21/simpleNormalizeProfile.txt")))
            .build();

    public static final NormalizationRule EMPTY_NORMA_LONG_TO_LONG_ONIX_ONIX30_FOR_ONIX21_TEST = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationLongToLongFormatOnix21/Empty_Normalization_profile.txt")))
            .isOnix30(true)
            .build();

    public static final NormalizationRule REGRESSION_NORMA_LONG_TO_LONG_ONIX_30 = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationLongOrShortToLongFormatOnix30/simpleNormalizeProfile.txt")))
            .isOnix30(true)
            .build();

    public static final NormalizationRule EMPTY_NORMA_LONG_TO_LONG_ONIX_ONIX21_FOR_ONIX30_TEST = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationLongOrShortToLongFormatOnix30/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule NORM_REMOVE_RULE_30_SEND_SINGLE_ONIX_IN_SINGLE_FILE = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRules30WithDistributeAsSingleOnixFileOption/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule NORM_EMPTY_RULE_21_SEND_SINGLE_ONIX_IN_SINGLE_FILE = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRules30WithDistributeAsSingleOnixFileOption/Empty_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule NORM_REMOVE_RULE_30_GROUP_BY_PUB_AND_IMPRINT = NormalizationRule.builder()
            .isOnix30(true)
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRules30WithGroupByPublisherAndImprint/Remove_Normalization_profile.txt")))
            .build();

    public static final NormalizationRule NORM_EMPTY_RULE_21_GROUP_BY_PUB_AND_IMPRINT = NormalizationRule.builder()
            .rule(readXmlFileToString(Paths.get("regression/verifyNormalizationOptimizationRules30WithGroupByPublisherAndImprint/Empty_Normalization_profile.txt")))
            .build();
    /** end of Regression tests */


    private NormalizationRules() {
        //NONE
    }
}
