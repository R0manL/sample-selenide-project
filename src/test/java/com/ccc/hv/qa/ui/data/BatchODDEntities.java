package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.BatchODDEntity;

import java.nio.file.Paths;

import static com.ccc.hv.qa.ui.data.BatchODDProductTypes.*;
import static com.ccc.hv.qa.ui.data.Channels.*;

public class BatchODDEntities {

    /**
     * Smoke test set
     */
    public static final BatchODDEntity SMOKE_BATCH_ODD_ENTITY = BatchODDEntity.builder()
            .channelName(smokePublicChannelFTPSForBatchODD.getName())
            .batchODDProductTypeEntity(SMOKE_BATCH_ODD_PRODUCT_TYPE_ENTITY)
            .pathToXlsxFile(Paths.get("smoke/verifyBatchODD/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity SMOKE_BATCH_ODD_ENTITY_ONIX30 = BatchODDEntity.builder()
            .channelName(smokePublicChannelFTPSForBatchODDOnix30.getName())
            .batchODDProductTypeEntity(SMOKE_BATCH_ODD_PRODUCT_TYPE_ENTITY_ONIX30)
            .pathToXlsxFile(Paths.get("smoke/verifyBatchODDOnix30/batch_ODD_onix30.xlsx"))
            .build();

    public static final BatchODDEntity REGRESSION_BATCH_ODD_ENTITY_GROUP_BY_PUBLISHER = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPForGroupByPublisherImprint.getName())
            .batchODDProductTypeEntity(REGRESSION_BATCH_ODD_PRODUCT_TYPE_ENTITY_GROUP_BY_PUB_IMPRINT)
            .pathToXlsxFile(Paths.get("regression/verifyGroupByPublisherAndImprintOption/batch_ODD.xlsx"))
            .build();
    /* end of Smoke test set */

    /**
     * Regression test set
     */
    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPForSendSingleProductsInSingleOnixFile.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX)
            .pathToXlsxFile(Paths.get("regression/verifySendSingleProductsInSingleOnixFileOption/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX_ONIX30 = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPForSendSingleProductsInSingleOnixFileOnix30.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX_ONIX_30)
            .pathToXlsxFile(Paths.get("regression/verifySendSingleProductsInSingleOnixFileOptionOnix30/batch_odd.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_OXIX_FEED_DISTR_FAILURE = BatchODDEntity.builder()
            .channelName(regressionPublicChannelFTPForOnixFeedDistrFailurePath.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ONIX_FEED_DISTR_FAILURE)
            .pathToXlsxFile(Paths.get("regression/verifyOnixFeedDistributionFailurePath/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_ONIX_FEED_DISTR_SUCCESS_PATH = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPForOnixFeedDistributionSuccessPath.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ONIX_FEED_DISTR_SUCCESS_PATH)
            .pathToXlsxFile(Paths.get("regression/verifyOnixFeedDistributionSuccessPath/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS = BatchODDEntity.builder()
            .channelName(regressionPublicChannelFTPSForMultipleProductTypesAndAssetsDistributionByPaths.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS_EPUB)
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS_PRINT)
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS_MOBI)
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_MULTIPLE_PROD_TYPES_AND_ASSETS_PDF)
            .pathToXlsxFile(Paths.get("regression/verifyMultipleProductTypesAndAssetsDistributionByPaths/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_CONTENT_COLLATERAL_ON_LOCKED_PRODUCT = BatchODDEntity.builder()
            .channelName(regressionPublicChannelFTPSForBatchODDDistributeLocked.getName())
            .isSendLockedProducts(true)
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_CONTENT_COLLATERAL_ENTITY_BATCH_ODD_LOCKED_PRODUCT)
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDWhenProductLockedContentCollateralNotDistr/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_CONTENT_COLLATERAL_PRODUCT_ON_HOLD = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPForBatchODDContentCollateralDistrOnHold.getName())
            .isSendOnHoldProducts(true)
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_CONTENT_COLLATERAL_PRODUCT_ON_HOLD)
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDWhenProductOnHoldContentCollateralNotDistr/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_1 = BatchODDEntity.builder()
            .isApplyRule(true)
            .selectEntireCatalog(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario1.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_2 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario2.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_2)
            .isApplyRule(true)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_3 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario3.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_3)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_4 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario4.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_4)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_5 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario5.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_5)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_6 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .isApplyRule(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario6.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_6)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_7 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .isApplyRule(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario7.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_7)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_8 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .isSendOnHoldProducts(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario8.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_8)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_9 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .isSendLockedProducts(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario9.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_9)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_10 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario10.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario10/batch_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_10)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_11 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario11.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario11/batch_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_11)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_12 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario12.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario12/batch_odd.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_12)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_13 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario13.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario13/batch_odd.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_13)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_14 = BatchODDEntity.builder()
            .selectEntireCatalog(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario14.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario14/batch_odd.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_14)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_15 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario15.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario15/batch_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_15)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_16 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario16.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario16/batch_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_16)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_17 = BatchODDEntity.builder()
            .isSendOnHoldProducts(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario17.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario17/batch_odd.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_17)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_18 = BatchODDEntity.builder()
            .isSendLockedProducts(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario18.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario18/batch_odd.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_18)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_SCENARIO_19 = BatchODDEntity.builder()
            .isSendLockedProducts(true)
            .channelName(regressionPublicChannelSFTPForBatchODDScenario19.getName())
            .pathToXlsxFile(Paths.get("regression/verifyBatchODDScenario19/batch_odd_invalid.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_SCENARIO_19)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_NORM_OPT_21_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX = BatchODDEntity.builder()
            .channelName(regressionPubChannelSFTPNormOptRule21SendSingleOnixFile.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_NORM_OPT_21_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX)
            .pathToXlsxFile(Paths.get("regression/verifyNormalizationOptimizationRules21WithDistributeAsSingleOnixFileOption/batch_odd.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_NORM_OPT_21_GROUP_BY_PUBLISHER_AND_IMPRINT = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPNormOptRule21GroupByPublisherAndImprint.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_NORM_OPT_21_GROUP_BY_PUBLISHER_AND_IMPRINT)
            .pathToXlsxFile(Paths.get("regression/verifyNormalizationOptimizationRules21WithGroupByPublisherAndImprint/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_NORM_OPT_30_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX = BatchODDEntity.builder()
            .channelName(regressionPubChannelSFTPNormOptRule30SendSingleOnixFile.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_NORM_OPT_30_SEND_SINGLE_PRODUCT_IN_SINGLE_ONIX)
            .pathToXlsxFile(Paths.get("regression/verifyNormalizationOptimizationRules30WithDistributeAsSingleOnixFileOption/batch_odd.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_NORM_OPT_30_GROUP_BY_PUBLISHER_AND_IMPRINT = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPNormOptRule30GroupByPublisherAndImprint.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_NORM_OPT_30_GROUP_BY_PUBLISHER_AND_IMPRINT)
            .pathToXlsxFile(Paths.get("regression/verifyNormalizationOptimizationRules30WithGroupByPublisherAndImprint/batch_odd.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_TAG_MATCH_FOR_ONIX_21 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPForDistributeWhenTagMatchForOnix21.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ENTITY_TAG_MATCH_FOR_ONIX_21)
            .pathToXlsxFile(Paths.get("regression/verifyDistributeWhenTagMatchForOnix21/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_TAG_MATCH_FOR_ONIX_30 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPForDistributeWhenTagMatchForOnix30.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ENTITY_TAG_MATCH_FOR_ONIX_30)
            .pathToXlsxFile(Paths.get("regression/verifyDistributeWhenTagMatchForOnix30/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_ENTITY_TAG_DOES_NOT_MATCH_FOR_ONIX_21 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPForDistributeWhenTagDoesNotMatchForOnix21.getName())
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_ENTITY_TAG_DOES_NOT_MATCH_FOR_ONIX_21)
            .pathToXlsxFile(Paths.get("regression/verifyDistributeWhenTagDoesNotMatchForOnix21/batch_ODD.xlsx"))
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_PATH_1 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByPublisherSuccPath1.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByPublisherSuccessPath/batch_ODD_21.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_PATH_2 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByPublisherSuccPath2.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByPublisherSuccessPath/batch_ODD_30.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_PUBLISHER_FAIL_PATH_1 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByPublisherFailPath1.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByPublisherFailPath/batch_onix21_1_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_PUBLISHER_FAIL_PATH_2 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByPublisherFailPath2.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByPublisherFailPath/batch_onix30_1_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_PUBLISHER_FAIL_PATH_3 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByPublisherFailPath3.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByPublisherFailPath/batch_onix21_2_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_PUBLISHER_FAIL_PATH_4 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByPublisherFailPath4.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByPublisherFailPath/batch_onix30_2_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_PUBLISHER_SUCCESS_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_OPT_ONIX21 = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPOpt21Remove.getName())
            .pathToXlsxFile(Paths.get("regression/verifyOptimization21RemoveRule/batch_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_OPT_ONIX21)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_OPT_ONIX30 = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPOpt30Remove.getName())
            .pathToXlsxFile(Paths.get("regression/verifyOptimization30RemoveRule/batch_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_OPT_ONIX30)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_OPT_PARTIALLY_FAIL_ONIX30 = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPPartiallyFailOptOnix30.getName())
            .pathToXlsxFile(Paths.get("regression/verifyPartiallyFailOptimizationOnix30/batch_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_OPT_PARTIALLY_FAIL_ONIX30)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_OPT_PARTIALLY_FAIL_ONIX21 = BatchODDEntity.builder()
            .channelName(regressionPublicChannelSFTPPartiallyFailOptOnix21.getName())
            .pathToXlsxFile(Paths.get("regression/verifyPartiallyFailOptimizationOnix21/batch_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_OPT_PARTIALLY_FAIL_ONIX21)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_PATH_1 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByAudienceCodeSuccPath1.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByAudienceCodeSuccessPath/batch_ODD_21.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_PATH_2 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByAudienceCodeSuccPath2.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByAudienceCodeSuccessPath/batch_ODD_30.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_FAIL_PATH_1 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByAudienceCodeFailPath1.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByAudienceCodeFailPath/batch_onix21_1_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_FAIL_PATH_2 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByAudienceCodeFailPath2.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByAudienceCodeFailPath/batch_onix30_1_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_FAIL_PATH_3 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByAudienceCodeFailPath3.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByAudienceCodeFailPath/batch_onix21_2_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_FAIL_PATH_4 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByAudienceCodeFailPath4.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByAudienceCodeFailPath/batch_onix30_2_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_AUDIENCE_CODE_SUCCESS_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_PATH_1 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByTerritoryRightsSuccPath1.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByTerritoryRightsSuccessPath/batch_onix21_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_PATH_2 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByTerritoryRightsSuccPath2.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByTerritoryRightsSuccessPath/batch_onix30_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_FAIL_PATH_1 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByTerritoryRightsFailPath1.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByTerritoryRightsFailPath/batch_onix21_ODD_9780000001042.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_FAIL_PATH_2 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByTerritoryRightsFailPath2.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByTerritoryRightsFailPath/batch_onix30_ODD_9780000001033.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_TERRITORY_RIGHTS_SUCCESS_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_11 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintSuccPath11.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_pass11.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_12 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintSuccPath12.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_pass12.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_21 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintSuccPath21.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_pass21.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_22 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintSuccPath22.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_pass22.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_31 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintSuccPath31.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_pass31.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_SUCCESS_PATH_32 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintSuccPath32.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_pass32.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_FAIL_PATH_11 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintFailPath11.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_fail11.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_FAIL_PATH_12 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintFailPath12.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_fail12.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_FAIL_PATH_21 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintFailPath21.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_fail21.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_IMPRINT_FAIL_PATH_22 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByImprintFailPath22.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByImprint/batch_ODD_fail22.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_IMPRINT_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_11 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath11.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_pass11.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_12 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath12.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_pass12.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_21 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath21.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_pass21.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_22 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath22.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_pass22.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_31 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath31.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_pass31.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_SUCCESS_PATH_32 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierSuccPath32.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_pass32.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_FAIL_PATH_11 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierFailPath11.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_fail11.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_F1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_FAIL_PATH_12 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierFailPath12.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_fail12.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_F2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_FAIL_PATH_21 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierFailPath21.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_fail21.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_FAIL_PATH_22 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterBySupplyIdentifierFailPath22.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringBySupplyIdentifier/batch_ODD_fail22.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_SUPPLY_IDENTIFIER_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_SUCCESS_PATH_1 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByMultipleDistrRulesSuccPath1.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByMultipleDistrRulesSuccessPath/batch_onix21_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_SUCCESS_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_SUCCESS_PATH_2 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByMultipleDistrRulesSuccPath2.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByMultipleDistrRulesSuccessPath/batch_onix30_ODD.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_SUCCESS_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_RULE_FAIL_PATH_1 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByMultipleDistrRulesFailPath1.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByMultipleDistrFailPath/batch_ODD_9780000001088.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_RULE_FAIL_PATH_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_RULE_FAIL_PATH_2 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByMultipleDistrRulesFailPath2.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByMultipleDistrFailPath/batch_ODD_9780000001089.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_MULTIPLE_DISTR_RULE_FAIL_PATH_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_11 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsSuccPath11.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9782374950823.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_12 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsSuccPath12.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9780807596492.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_21 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsSuccPath21.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9782374950824.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_22 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsSuccPath22.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9780807596493.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_31 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsSuccPath31.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9782374950825.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_32 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsSuccPath32.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9780807596494.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_41 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsSuccPath41.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9782374950826.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCCESS_PATH_42 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsSuccPath42.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9780807596495.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCC_PATH_51 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsPassPath51.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9782374950830.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCC_PATH_52 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsPassPath52.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9780807596499.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCC_PATH_61 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsPassPath61.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9782374950831.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_SUCC_PATH_62 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsPassPath62.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsSuccessPath/batch_ODD_9780807596500.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_11 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath11.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9782374950827.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_12 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath12.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9780807596496.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_21 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath21.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9782374950828.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_22 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath22.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9780807596497.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_31 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath31.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9782374950829.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_32 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath32.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9780807596498.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_61 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath61.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9782374950832.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_62 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath62.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9780807596501.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_71 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath71.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9782374950833.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_72 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath72.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9780807596502.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_81 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath81.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9782374950834.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_82 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath82.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9780807596503.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_91 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath91.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9782374950835.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_92 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath92.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9780807596504.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_101 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath101.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9782374950836.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_102 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath102.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9780807596505.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_111 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath111.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9782374950837.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_1)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_FILTER_BY_BISAC_SBJ_FAIL_PATH_112 = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPFilterByBISACSubjectsFailPath112.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductFilteringByBISACSubjectsFailPath/batch_ODD_9780807596506.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_ENTITY_BATCH_ODD_FILTER_BY_BISAC_SBJ_2)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_CARD_FILTERING_WITH_CONTENT_COLLATERAL_META = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPForProductCardFilteringSomeFiltersMatch.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductCardFilteringWithContentAndCollateralAndMetadata/batch_ODD_9780807505855.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_CONTENT_COLLATERAL_META)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_CARD_FILTERING_WITH_NO_FILTERS_SETUP = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPForProductCardFilteringWithNoFiltersSetup.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductCardFilteringWithNoFiltersSetup/batch_ODD_9780807505856.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_NO_FILTER_SETUP)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_CARD_FILTERING_WITH_ALL_FILTERS_SETUP_AND_MATCH_NO_SALES_OUTLET = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet/batch_ODD_9780807505857.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_ALL_FILTERS_SETUP_AND_MATCH_NO_SALES_OUTLET)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_CARD_FILTERING_WITH_ALL_FILTERS_SETUP_AND_SOME_NOT_MATCH_NO_SALES_OUTLET = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductCardFilteringWithAllFiltersSetupSomeDoNotMatchWithoutSalesOutlet/batch_ODD_9780807505858.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_ALL_FILTERS_SETUP_AND_SOME_NOT_MATCH_NO_SALES_OUTLET)
            .build();

    public static final BatchODDEntity REG_BATCH_ODD_CARD_FILTERING_WITH_SALES_OUTLET = BatchODDEntity.builder()
            .isApplyRule(true)
            .channelName(regressionPubChannelSFTPForProductCardFilteringWithSalesOutlet.getName())
            .pathToXlsxFile(Paths.get("regression/verifyProductCardFilteringWithSalesOutlet/batch_ODD_9780807505859.xlsx"))
            .batchODDProductTypeEntity(REG_BATCH_ODD_PRODUCT_TYPE_CARD_FILTERING_WITH_SALES_OUTLET)
            .build();
    /**
     * end of Regression test set
     */

    private BatchODDEntities() {
        // None
    }
}
