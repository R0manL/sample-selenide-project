package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.enums.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * Created by R0manL on 25/08/20.
 * {bisacSubjects} - if keyValue = true - subjectCode will be included, = false - will be excluded
 */

@Getter
@SuperBuilder
public class DistributionRule {
    //Auto-Correct
    private final boolean productNotUpForSaleWhenItShouldBe;
    private final boolean productUpForSaleWhenTtShouldNotBe;
    private final boolean incorrectPrice;
    private final boolean coverDoesNotMatch;
    //Apple-specific options
    private final boolean drmFree;
    private final boolean preOrderPreviews;
    private final boolean explicitContent;
    private final boolean useImprintForPublisherName;
    private final boolean booksAreNewReleaseUntil;
    private final int newReleaseMonths;
    //Tags
    private final boolean useTagFilters;
    private final TagFilter tagFilter;
    @Singular private final Set<Publisher> publishers;
    @Singular private final Set<Imprint> imprints;
    @Singular private final Map<@NonNull String, String> suppliers;
    @Singular private final Map<@NonNull String, String> discountCodes;
    @Singular private final Map<@NonNull BisacSubjectCode, @NonNull Boolean> bisacSubjects;
    private final boolean pubDateFilterActive;
    @Builder.Default
    private final boolean pubDateFilterBefore = true;
    private final LocalDate pubDateFilterValue;
    @Singular private final Set<Territory> territories;
    @Singular private final Set<AudienceCode> audienceCodes;
}
