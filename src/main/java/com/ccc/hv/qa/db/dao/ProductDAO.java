package com.ccc.hv.qa.db.dao;

import com.ccc.hv.qa.db.pojo.ProductDB;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jetbrains.annotations.NotNull;


public interface ProductDAO {

    @SqlQuery("SELECT hv_prdmas_product_master_id FROM hrv.hv_product_master WHERE title =:productTitle")
    @SingleValue
    int getProductIdBy(@Bind("productTitle") @NotNull String productTitle);

    @SqlQuery("SELECT * FROM hrv.hv_product_master WHERE title =:title")
    @SingleValue
    @RegisterConstructorMapper(ProductDB.class)
    ProductDB getProductBy(@Bind("title") @NotNull String title);

    @SqlQuery("SELECT on_hold FROM hrv.hv_product_master where hv_prdmas_product_master_id =:productId")
    @SingleValue
    boolean isProductOnHold(@Bind("productId") int productId);

    @SqlQuery("SELECT marked_for_delete_yn FROM hrv.hv_product_master where hv_prdmas_product_master_id =:productId")
    @SingleValue
    boolean hasProductMarkedForDelete(@Bind("productId") int productId);

    @SqlQuery("SELECT embargoed_yn FROM hrv.hv_product_master where hv_prdmas_product_master_id =:productId")
    @SingleValue
    boolean isProductLocked(@Bind("productId") int productId);

    @SqlQuery("select COUNT(*) from hv_product_master where title like '%' || :revisionPrefix  || '%'")
    @SingleValue
    int getNumOfTestProducts(@Bind("revisionPrefix") @NotNull String revisionPrefix);

    @SqlUpdate("UPDATE hv_product_master SET marked_for_delete_yn = true WHERE title like '%' || :revisionPrefix  || '%'")
    void markTestProductsForDeletion(@Bind("revisionPrefix") @NotNull String revisionPrefix);
}
