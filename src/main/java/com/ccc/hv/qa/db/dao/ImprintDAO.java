package com.ccc.hv.qa.db.dao;

import lombok.NonNull;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

/*
 * Created by R0manL on 26/02/21.
 */

public interface ImprintDAO {

    @SqlQuery("SELECT hv_impmas_imprint_master_id FROM hv_imprint_master WHERE name =:name AND hv_tenmas_tenant_master_id =:tenantId")
    @SingleValue
    int getImprintIDBy(@Bind("name") @NonNull String name, @Bind("tenantId") int tenantId);
}
