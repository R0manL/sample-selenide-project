package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.MetadataOptimization;

/**
 * Created by R0manL on 22/09/20.
 */

public class MetadataOptimizations {

    /** Smoke test channels */
    public static final MetadataOptimization SMOKE_NON_APPLE_OPTIMIZATION_RULES_NOT_APPLY = MetadataOptimization.builder()
            .ruleFileRelPath("smoke/verifyNonAppleChannelOptWhenRulesNotApply/simplifiedOnixOptimizationProfile.xml")
            .build();

    public static final MetadataOptimization SMOKE_NON_APPLE_OPTIMIZATION_RULES_APPLY = MetadataOptimization.builder()
            .ruleFileRelPath("smoke/verifyNonAppleChannelOptWhenRulesApply/simplifiedOnixOptimizationProfile.xml")
            .build();

    public static final MetadataOptimization SMOKE_OPTIMIZATION_APPLE = MetadataOptimization.builder()
            .ruleFileRelPath("smoke/verifyAppleChannelOptimizationRules/appleOptimizeProfile.xml")
            .labelAppleXML(true)
            .labelCustom(true)
            .labelCustomText("Test_Label")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_21_SUBSTITUTE_RULE_SEND_SINGLE_ONIX = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationOptimizationRules21WithDistributeAsSingleOnixFileOption/Substitute_Normalization_profile.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_21_SUBSTITUTE_RULE_METADATA_ACTIVITY = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationOptimizationRulesMetadataActivityOnix21/Substitute_Normalization_profile.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_SUBSTITUTE_RULE_METADATA_ACTIVITY = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationOptimizationRulesMetadataActivityOnix30/Substitute_Optimization_profile.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_NORM_FAILED_PATH_SUBSTITUTE_INVALID = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationOptimizationFailedPath/Substitute_Normalization_profile_Invalid.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_SUBSTITUTE_RULE_21_GROUP_BY_PUBLISHER_AND_IMPRINT = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationOptimizationRules21WithGroupByPublisherAndImprint/Substitute_Normalization_profile.txt")
            .build();
    /* end of Smoke test channels */

    /** Regression test channels */
    public static final MetadataOptimization REGRESSION_SUBSTITUTE_OPTIMIZATION_RULES = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationRulesMultiply/simpleNormalizeProfile.xml")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_21_LONG_ONIX21_TO_SHORT_30_ADD_SUBSTITUTE_RULE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization21FromLongOnix21ToShort30AddSubstitute/Add-Sub-3.0-config.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_21_FAILED_PATH_SUBSTITUTE_INVALID = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization21FailedPath/Substitute_Opt_profile_Invalid.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_21_FAILED_METADATA_ACTIVITY_PATH_SUBSTITUTE_INVALID = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization21FailedPathMetadataActivity/Substitute_Opt_profile_Invalid.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_FAILED_METADATA_ACTIVITY_PATH_SUBSTITUTE_INVALID = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization30FailedMetadataActivity/Substitute_Opt_profile_Invalid_Syntax.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_NORM_21_FAILED_METADATA_ACTIVITY_PATH_SUBSTITUTE_INVALID = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationOptimization21FailedMetadataActivity/Substitute_Opt_profile_Invalid.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_NORM_30_FAILED_METADATA_ACTIVITY_PATH_SUBSTITUTE_INVALID = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationOptimization30FailedMetadataActivity/Substitute_Opt_profile_Invalid_Syntax.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_21_REMOVE_RULE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization21RemoveRule/Remove-config.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_21_MULTIPLY_RULE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimizationMultiplyRule/Multiply_Opt_profile.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_METADATA_ACTIVITY = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimizationMetadataActivityOnix21/Multiply_Opt_profile.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPTIM_21_ADD_REPL_REMOVE_APPLE_META_PROFILE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization21AddRemoveSubstituteTransformationAppleMetadata/Add_remove_replace_profile.txt")
            .labelAppleXML(true)
            .labelCustom(true)
            .labelCustomText("Test_Label_regression_replace")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_ADD_RULE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization30AddRule/Add-config.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_METADATA_ACTIVITY_30_ADD_RULE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimizationMetadataActivityOnix30/Add-config.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_REMOVE_RULE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization30RemoveRule/Remove-config.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_SUBSTITUTE_RULE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization30SubstituteRule/Sub-3.0-config.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_RULE_LONG_TO_LONG_ONIX30 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationLongOrShortToLongFormatOnix30/to_long_onix_30.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_RULE_LONG_TO_LONG_ONIX21 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationLongToLongFormatOnix21/to_long_onix_21.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_SUBSTITUTE_RULE_SEND_SINGLE_ONIX = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationOptimizationRules30WithDistributeAsSingleOnixFileOption/Substitute_Optimization_profile.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_SUBSTITUTE_RULE_30_GROUP_BY_PUBLISHER_AND_IMPRINT = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyNormalizationOptimizationRules30WithGroupByPublisherAndImprint/Substitute_Optimization_profile.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization OPT_REMOVE_RULE_21_FOR_ONIX_21 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimizationRulesWhenTheyNotMatchOnixVersion21/Remove_Opt_rule.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization OPT_SUBSTITUTE_RULE_30_FOR_ONIX_30 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimizationRulesWhenTheyNotMatchOnixVersion30/Substitute_Optimization_profile.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_FAILED_PATH_SUBSTITUTE_INVALID_SYNTAX_ERROR = MetadataOptimization.builder()
            .isOnix30Rule(true)
            .ruleFileRelPath("regression/verifyOptimization30FailedPath/Substitute_Opt_profile_Invalid_Syntax.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_FAILED_PATH_SUBSTITUTE_INVALID_REMOVE_REC_REF = MetadataOptimization.builder()
            .isOnix30Rule(true)
            .ruleFileRelPath("regression/verifyOptimization30FailedPath/Substitute_Opt_profile_Invalid_Remove_rec_ref.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_CONVERSION_SUBSTITUTE_RULE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimizationWithConversionOnixFrom21To30/conversion_opt_profile.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_30_ADD_PUBLISHING_STATUS_NOTE = MetadataOptimization.builder()
            .isOnix30Rule(true)
            .ruleFileRelPath("regression/verifyPrefixAndTitleElementWereNotRemovedOnAddElementOperation/opt_profile.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPTIM_30_ADD_REPL_APPLE_META_PROFILE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimizationOnix30AddReplaceTransformationAppleMetadata/AUT-Apple-Add-Replace-opt-profile.txt")
            .labelAppleXML(true)
            .labelCustom(true)
            .isOnix30Rule(true)
            .labelOnix30(true)
            .labelCustomText("Test_Label_regression_replace")
            .build();

    public static final MetadataOptimization REGRESSION_OPTIM_30_SUBS_REPL_APPLE_META_PROFILE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimizationOnix30SubstituteReplaceTransformationAppleMetadata/AUT-Apple-Replace-opt-profile.txt")
            .labelAppleXML(true)
            .labelCustom(true)
            .isOnix30Rule(true)
            .labelOnix30(true)
            .labelCustomText("Test_Label_regression_replace")
            .build();

    public static final MetadataOptimization REGRESSION_OPTIM_30_REMOVE_REPL_APPLE_META_PROFILE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimizationOnix30RemoveReplaceTransformationAppleMetadata/AUT-Apple-Add-Replace-Remove-opt-profile.txt")
            .labelAppleXML(true)
            .labelCustom(true)
            .isOnix30Rule(true)
            .labelOnix30(true)
            .labelCustomText("Test_Label_regression_replace")
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_OPT_ISSUES_21 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaOptProcessIssues/PearsonUK_Apple_Optimization-Fails_Optimization.txt")
            .labelAppleXML(true)
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_OPT_ISSUES_30 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaOptProcessIssues/Test_Apple_30_Optimization-Fails_Optimization.txt")
            .labelAppleXML(true)
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_INVALID_OPT_PROFILE_21 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaInvalidSingleOptimizationProfile/PearsonUK_Apple_Optimization_invalid.txt")
            .labelAppleXML(true)
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_INVALID_OPT_PROFILE_30 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaInvalidSingleOptimizationProfile/Test_Apple_30_Optimization_invalid.txt")
            .labelAppleXML(true)
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPTIM_21_CA_OPTIONS_ON_APPLE_META_PROFILE = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOnix21CAOptionsOn/Add_profile.txt")
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_OPT_PROCESS_ISSUES_21 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaAppleConverterProcessPackagingIssues/PearsonUK_Apple_Optimization-Fails_Conversion.txt")
            .labelAppleXML(true)
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_OPT_PROCESS_ISSUES_30 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaAppleConverterProcessPackagingIssues/Test_Apple_30_Optimization-Fails_Validation-20220302.txt")
            .labelAppleXML(true)
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_TRANSPORTATION_DISTR_ISSUES_21 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaTransportationDistrIssuesOnix21/PearsonUK_Apple_Optimization-Fails_Transporter-Distribution.txt")
            .labelAppleXML(true)
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_TRANSPORTATION_DISTR_ISSUES_30 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaTransportationDistrIssuesOnix30/Test_Apple_30_Optimization-Fails_Transporter-Distribution.txt")
            .labelAppleXML(true)
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_BOTH_VALID_OPT_PROFILES_FOR_ONIX_21 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaWhenBothOptimizationProfilesAreValid/PearsonUK_Apple_Optimization.txt")
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_BOTH_VALID_OPT_PROFILES_FOR_ONIX_30 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaWhenBothOptimizationProfilesAreValid/Test_Apple_30_Optimization.txt")
            .labelAppleXML(true)
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_BOTH_INVALID_OPT_PROFILES_FOR_ONIX_21 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaWhenBothOptimizationProfilesAreInValid/PearsonUK_Apple_Optimization-Fails_Optimization.txt")
            .build();

    public static final MetadataOptimization REGRESSION_APPLE_META_BOTH_INVALID_OPT_PROFILES_FOR_ONIX_30 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyAppleMetaWhenBothOptimizationProfilesAreInValid/Test_Apple_30_Optimization-Fails_Optimization.txt")
            .labelAppleXML(true)
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_PARTIALLY_FAIL_ONIX30 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyPartiallyFailOptimizationOnix30/Optimization_Remove_USD_Test_Profile.txt")
            .isOnix30Rule(true)
            .build();

    public static final MetadataOptimization REGRESSION_OPT_PARTIALLY_FAIL_ONIX21 = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyPartiallyFailOptimizationOnix21/Optimization_Remove_USD_Test_Profile.txt")
            .build();

    public static final MetadataOptimization REGRESSION_OPT_21_FAILED_PATH_SUBSTITUTE_INVALID_SORT_ORDER_FOR_ASSET = MetadataOptimization.builder()
            .ruleFileRelPath("regression/verifyOptimization21FailedPath/Substitute_Opt_profile_Invalid.txt")
            .build();
    /** end of Regression test channels */


    private MetadataOptimizations() {
        //NONE
    }
}
