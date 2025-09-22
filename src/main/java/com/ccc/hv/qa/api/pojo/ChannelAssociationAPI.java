package com.ccc.hv.qa.api.pojo;

import com.ccc.hv.qa.api.enums.OnixVersion;
import com.ccc.hv.qa.ui.enums.IsbnFilterType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 * Created by R0manL on 22/02/21.
 */

@Getter
@SuperBuilder
public class ChannelAssociationAPI {
    private final boolean automaticDistribution;
    @NotNull
    private final OnixVersion onixVersion;
    @NonNull
    private final IsbnFilterType isbnFilterType;
    private final String isbnFilterTag;
    private final boolean pubdateFilterActive;
    private final boolean pubdateFilterBefore;
    private final long pubdateFilterValue;
    private final Set<ImprintAPI> imprints;
    private final Set<DiscountCodesAPI> discountCodes;
    private final Set<SupplierIdAPI> supplierIds;
    private final Set<TerritoryAPI> territories;
    private final Set<AudienceCodeAPI> audienceCodes;
    private final Set<PublisherAPI> publishers;
    private final Set<KeywordAPI> keywords;
    //Apple-specific options
    private final boolean sendExplicit;
    private final boolean useImprintName;
    private final boolean useNewRelease;
    private final String newReleaseMonths;
    private final boolean sendTriggerFile;
    private final Set<BisacCodeAPI> bisacCodes;
    @NonNull
    private final Set<ChannelServerAssociationAPI> channelServerAssociations;
    private final AddressAPI address;
    private final String notificationEmails;
    private final String takeDownEmails;
    private final List<BlackoutDateAPI> blackoutDates;
    private final boolean autoCorrectToNotOnSale;
    private final boolean autoCorrectToOnSale;
    private final boolean autoCorrectPrice;
    private final boolean autoCorrectCover;
    private final List<CommentAPI> comments;
    private final List<String> footprint;
}
