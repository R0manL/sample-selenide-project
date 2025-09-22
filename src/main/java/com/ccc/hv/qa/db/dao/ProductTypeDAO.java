package com.ccc.hv.qa.db.dao;

import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.KeyColumn;
import org.jdbi.v3.sqlobject.config.ValueColumn;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public interface ProductTypeDAO {

    @SqlQuery("SELECT hv_prdtyp_product_type_id FROM hv_product_type_ref WHERE name =:name")
    @SingleValue
    int getProductTypeIdBy(@Bind("name") @NotNull String name);

    @SqlQuery("SELECT name FROM hv_product_type_ref WHERE hv_prdtyp_product_type_id =:id")
    @SingleValue
    String getProductTypeNameBy(@Bind("id") int id);

    @SqlQuery("SELECT hv_prdtyp_product_type_id, name FROM hv_product_type_ref")
    @KeyColumn("hv_prdtyp_product_type_id")
    @ValueColumn("name")
    Map<Integer, String> getAllProductTypes();
}
