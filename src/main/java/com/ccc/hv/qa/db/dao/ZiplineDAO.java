package com.ccc.hv.qa.db.dao;

import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;


public interface ZiplineDAO {
    @SqlQuery("SELECT jobmas_id FROM job_master where jobmas_product_id =:productId")
    @SingleValue
    int getZiplineJobMasterId(@Bind("productId") int productId);

    @SqlQuery("SELECT CASE WHEN EXISTS (SELECT jobmas_id FROM job_master WHERE jobmas_product_id =:productId) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    @SingleValue
    boolean isZiplineJobExistFor(@Bind("productId") int productId);
}
