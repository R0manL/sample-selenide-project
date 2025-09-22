package com.ccc.hv.qa.db.pojo;

import lombok.Value;

/**
 * Created by R0manL on 6/7/22.
 */

@Value
public class WorkflowProductStatusDetailsDB {
    int hvTenmasTenantMasterId;
    int hvCaadetChaAssociationId;
    Boolean wpsdetFilterAllYn;
    Boolean wpsdetFilterSalesOutletYn;
    Boolean wpsdetFilterBisacYn;
    Boolean wpsdetFilterImprintYn;
    Boolean wpsdetFilterPublisherNameYn;
    Boolean wpsdetFilterDiscountCodeYn;
    Boolean wpsdetFilterTerritoryYn;
    Boolean wpsdetFilterSupplierIdentYn;
    Boolean wpsdetFilterAudienceCodeYn;
    Boolean wpsdetFilterIsbnTagYn;
    Boolean wpsdetFilterPubDateYn;
}
