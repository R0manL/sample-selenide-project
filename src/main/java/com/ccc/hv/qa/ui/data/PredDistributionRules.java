package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.AudienceCode;
import com.ccc.hv.qa.ui.enums.BisacSubjectCode;
import com.ccc.hv.qa.ui.pojos.DistributionRule;

/**
 * Created by R0manL on 16/09/20.
 */

public class PredDistributionRules {

    public static final DistributionRule PRED_DISTRIBUTION_RULES_1 = DistributionRule.builder()
            .bisacSubject(BisacSubjectCode.FIC030000, true)
            .build();

    public static final DistributionRule PRED_DISTRIBUTION_RULES_2 = DistributionRule.builder()
            .audienceCode(AudienceCode.PROFESSIONAL_AND_SCHOLARLY)
            .build();


    private PredDistributionRules() {
        // NONE
    }
}
