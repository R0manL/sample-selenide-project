package com.ccc.hv.qa.db.dao;

import com.ccc.hv.qa.db.pojo.WorkflowAssetStatusDetailsDB;
import com.ccc.hv.qa.db.pojo.WorkflowProductStatusDetailsDB;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface WorkflowDAO {

    @SqlQuery("select hv_tenmas_tenant_master_id, hv_csadet_cha_server_assoc_id, wasdet_current_version from workflow_asset_status_detail where hv_caadet_cha_association_id=:caId and hv_astmas_asset_master_id=:assetId")
    @SingleValue
    @RegisterConstructorMapper(WorkflowAssetStatusDetailsDB.class)
    WorkflowAssetStatusDetailsDB getWorkflowAssetStatusDetailsFor(@Bind("caId") int caId, @Bind("assetId") int assetId);

    @SqlQuery("select hv_tenmas_tenant_master_id, hv_caadet_cha_association_id, wpsdet_filter_all_yn, wpsdet_filter_sales_outlet_yn, wpsdet_filter_bisac_yn, wpsdet_filter_imprint_yn, wpsdet_filter_publisher_name_yn, wpsdet_filter_discount_code_yn, wpsdet_filter_territory_yn, wpsdet_filter_supplier_ident_yn, wpsdet_filter_audience_code_yn, wpsdet_filter_isbn_tag_yn, wpsdet_filter_pub_date_yn from workflow_product_status_detail where hv_caadet_cha_association_id=:chaAssocId and hv_prdmas_product_master_id=:prdmasId")
    @SingleValue
    @RegisterConstructorMapper(WorkflowProductStatusDetailsDB.class)
    WorkflowProductStatusDetailsDB getWorkflowProductStatusDetailsFor(@Bind("chaAssocId") int chaAssocId, @Bind("prdmasId") int productId);
}
