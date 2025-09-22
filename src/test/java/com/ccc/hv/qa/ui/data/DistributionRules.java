package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.AudienceCode;
import com.ccc.hv.qa.ui.enums.BisacSubjectCode;
import com.ccc.hv.qa.ui.enums.Imprint;
import com.ccc.hv.qa.ui.enums.Publisher;
import com.ccc.hv.qa.ui.enums.Territory;
import com.ccc.hv.qa.ui.pojos.DistributionRule;

import static com.ccc.hv.qa.ui.data.PredTagFilters.*;
import static com.ccc.hv.qa.ui.enums.BisacSubjectCode.*;

/**
 * Created by R0manL on 23/09/20.
 */

public class DistributionRules {

    /** Smoke test set */
    public static final DistributionRule smokeDistributionRuleFTP = DistributionRule.builder()
            .bisacSubject(BisacSubjectCode.FIC030000, true)
            .build();

    public static final DistributionRule smokeDistributionRuleSFTP = DistributionRule.builder()
            .audienceCode(AudienceCode.PROFESSIONAL_AND_SCHOLARLY)
            .build();

    public static final DistributionRule smokeDistributionRuleFTPS = DistributionRule.builder()
            .audienceCode(AudienceCode.CHILDREN_JUVENILE)
            .build();

    public static final DistributionRule smokeDistributionRuleITMS = DistributionRule.builder()
            .audienceCode(AudienceCode.GENERAL_TRADE)
            .build();
    /* end of Smoke test set */

    /** Regression test set */
    public static final DistributionRule regressionFilteringByPubDateDefaultDateAndState = DistributionRule.builder()
            .pubDateFilterActive(true)
            .pubDateFilterBefore(true)
            .build();

    public static final DistributionRule regressionBatchODDScenario1 = DistributionRule.builder()
            .discountCode("A1K0C8", "Discount code for scenario1")
            .build();

    public static final DistributionRule regressionBatchODDScenario2 = DistributionRule.builder()
            .discountCode("A0K1C9", "Discount code for scenario2")
            .build();

    public static final DistributionRule regressionBatchODDScenario7 = DistributionRule.builder()
            .discountCode("SCE07", "Discount code for scenario7")
            .build();

    public static final DistributionRule regressionBatchODDScenario10 = DistributionRule.builder()
            .discountCode("A1K0C8", "Discount code for scenario10")
            .build();

    public static final DistributionRule regressionBatchODDScenario11 = DistributionRule.builder()
            .discountCode("A0K1C90", "Discount code for scenario11")
            .build();

    public static final DistributionRule appleDistrRuleOnix30 = DistributionRule.builder()
            .drmFree(true)
            .preOrderPreviews(true)
            .explicitContent(true)
            .useImprintForPublisherName(true)
            .booksAreNewReleaseUntil(true)
            .newReleaseMonths(4)
            .build();

    public static final DistributionRule appleDistrRuleOnix21 = DistributionRule.builder()
            .drmFree(true)
            .preOrderPreviews(true)
            .explicitContent(true)
            .useImprintForPublisherName(true)
            .booksAreNewReleaseUntil(true)
            .newReleaseMonths(4)
            .build();

    public static final DistributionRule tagMatchForOnix21 = DistributionRule.builder()
            .useTagFilters(true)
            .tagFilter(MATCH_ONIX_21_TAG_FILTER)
            .build();

    public static final DistributionRule tagMatchForOnix30 = DistributionRule.builder()
            .useTagFilters(true)
            .tagFilter(MATCH_ONIX_30_TAG_FILTER)
            .build();

    public static final DistributionRule tagDoesNotMatchForOnix21 = DistributionRule.builder()
            .useTagFilters(true)
            .tagFilter(DOE_NOT_MATCH_ONIX_21_TAG_FILTER)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByPublisherSuccPath1 = DistributionRule.builder()
            .publisher(Publisher.TEST_PUBLISHER)
            .publisher(Publisher.ALBERT_WHITMAN_COMPANY)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByPublisherSuccPath2 = DistributionRule.builder()
            .publisher(Publisher.TEST_PUBLISHER)
            .publisher(Publisher.ALBERT_WHITMAN_COMPANY)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByPublisherFailPath = DistributionRule.builder()
            .publisher(Publisher.HMH_BOOKS)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByAudienceCodeSuccPath1 = DistributionRule.builder()
            .audienceCode(AudienceCode.CHILDREN_JUVENILE)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByAudienceCodeSuccPath2 = DistributionRule.builder()
            .audienceCode(AudienceCode.GENERAL_TRADE)
            .audienceCode(AudienceCode.CHILDREN_JUVENILE)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByAudienceCodeFailPath = DistributionRule.builder()
            .audienceCode(AudienceCode.CHILDREN_JUVENILE)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByTerritoryRightsSuccPath1 = DistributionRule.builder()
            .territory(Territory.US)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByTerritoryRightsSuccPath2 = DistributionRule.builder()
            .territory(Territory.US)
            .territory(Territory.AE)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByPTerritoryRightsFailPath = DistributionRule.builder()
            .territory(Territory.AD)
            .territory(Territory.AE)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByImprintNoValue = DistributionRule.builder()
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByImprintSingleValue = DistributionRule.builder()
            .imprint(Imprint.VENDOME_PRESS)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByImprintTwoValues = DistributionRule.builder()
            .imprint(Imprint.VENDOME_PRESS)
            .imprint(Imprint.ALBERT_WHITMAN_COMPANY)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByImprintSingleInvalidValue = DistributionRule.builder()
            .imprint(Imprint.ALBERT_WHITMAN_COMPANY)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterBySupplyIdentifierNoValue = DistributionRule.builder()
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterBySupplyIdentifierSingleValue = DistributionRule.builder()
            .supplier("7P", "7P")
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterBySupplyIdentifierTwoValues = DistributionRule.builder()
            .supplier("7P", "7P")
            .supplier("Invalid", "Invalid")
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterBySupplyIdentifierSingleInvalidValue = DistributionRule.builder()
            .supplier("Invalid", "Invalid")
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByMultipleDistrRulesSuccPath1 = DistributionRule.builder()
            .territory(Territory.US)
            .publisher(Publisher.TEST_PUBLISHER)
            .publisher(Publisher.ALBERT_WHITMAN_COMPANY)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByMultipleDistrRulesSuccPath2 = DistributionRule.builder()
            .territory(Territory.US)
            .territory(Territory.AE)
            .publisher(Publisher.TEST_PUBLISHER)
            .publisher(Publisher.ALBERT_WHITMAN_COMPANY)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByMultipleDistrRulesFail = DistributionRule.builder()
            .territory(Territory.CA)
            .publisher(Publisher.TEST_PUBLISHER)
            .publisher(Publisher.ALBERT_WHITMAN_COMPANY)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueS11 = DistributionRule.builder()
            .bisacSubject(ART023000, true)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueS12 = DistributionRule.builder()
            .bisacSubject(JUV001000, true)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS21 = DistributionRule.builder()
            .bisacSubject(ANT045000, true)
            .bisacSubject(ART023000, true)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS22 = DistributionRule.builder()
            .bisacSubject(JUV001000, true)
            .bisacSubject(JUV013050, true)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS31 = DistributionRule.builder()
            .bisacSubject(ANT045000, true)
            .bisacSubject(CGN000000, false)
            .bisacSubject(CGN001000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS32 = DistributionRule.builder()
            .bisacSubject(JUV001000, true)
            .bisacSubject(CGN000000, false)
            .bisacSubject(CGN001000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS41 = DistributionRule.builder()
            .bisacSubject(ANT045000, true)
            .bisacSubject(ART023000, true)
            .bisacSubject(CGN001000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeMultpValueS42 = DistributionRule.builder()
            .bisacSubject(JUV001000, true)
            .bisacSubject(JUV013050, true)
            .bisacSubject(CGN001000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueP51 = DistributionRule.builder()
            .bisacSubject(BUS043000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueP52 = DistributionRule.builder()
            .bisacSubject(BUS043000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF11 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF12 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF21 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(FIC030000, true)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF22 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(FIC030000, true)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF31 = DistributionRule.builder()
            .bisacSubject(ART023000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF32 = DistributionRule.builder()
            .bisacSubject(JUV001000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueP61 = DistributionRule.builder()
            .bisacSubject(ART023000, true)
            .bisacSubject(BUS043000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueP62 = DistributionRule.builder()
            .bisacSubject(JUV001000, true)
            .bisacSubject(BUS043000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF61 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(ART023000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF62 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(JUV001000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF71 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(FIC030000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF72 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(FIC030000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF81 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(ART003000, false)
            .bisacSubject(FIC030000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF82 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(ART003000, false)
            .bisacSubject(FIC030000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF91 = DistributionRule.builder()
            .bisacSubject(ART023000, false)
            .bisacSubject(ANT045000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF92 = DistributionRule.builder()
            .bisacSubject(JUV001000, false)
            .bisacSubject(FIC030000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF101 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(CGN000000, false)
            .bisacSubject(FIC030000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF102 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(CGN000000, false)
            .bisacSubject(FIC030000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF111 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(CGN000000, true)
            .bisacSubject(FIC030000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForFilterByByBISACSubjectsIncludeSingleValueF112 = DistributionRule.builder()
            .bisacSubject(BUS043000, true)
            .bisacSubject(CGN000000, true)
            .bisacSubject(FIC030000, false)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForProductCardFilteringSomeFiltersMatch = DistributionRule.builder()
            .territory(Territory.US)
            .publisher(Publisher.HMH_BOOKS)
            .bisacSubject(FIC000000, true)
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForProductCardFilteringWithAllFiltersSetupThatMatchWithoutSalesOutlet = DistributionRule.builder()
            .supplier("7P", "7P")
            .imprint(Imprint.VENDOME_PRESS)
            .territory(Territory.US)
            .publisher(Publisher.HMH_BOOKS)
            .bisacSubject(CKB088000, true)
            .useTagFilters(true)
            .tagFilter(PRODUCT_CARD_FILTERING_TAG_FILTER)
            .audienceCode(AudienceCode.GENERAL_TRADE)
            .discountCode("AWH", "Valid discount code for automation testing")
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForProductCardFilteringWithAllFiltersSetupSomeNotMatchWithoutSalesOutlet = DistributionRule.builder()
            .supplier("7P", "7P")
            .imprint(Imprint.ALBERT_WHITMAN_COMPANY)
            .territory(Territory.AD)
            .publisher(Publisher.HMH_BOOKS)
            .bisacSubject(CKB088000, true)
            .audienceCode(AudienceCode.GENERAL_TRADE)
            .discountCode("AWH", "Valid discount code for automation testing")
            .build();

    public static final DistributionRule regressionDistributionRuleSFTPForProductCardFilteringWithSalesOutlet = DistributionRule.builder()
            .supplier("7P", "7P")
            .imprint(Imprint.ALBERT_WHITMAN_COMPANY)
            .publisher(Publisher.HMH_BOOKS)
            .bisacSubject(CKB088000, true)
            .audienceCode(AudienceCode.GENERAL_TRADE)
            .discountCode("AWH", "Valid discount code for automation testing")
            .territory(Territory.AD)
            .build();
    /** end of regression test set */


    private DistributionRules() {
        // None
    }
}
