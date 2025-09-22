package com.ccc.hv.qa.db.dao;

import lombok.NonNull;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

/*
 * Created by R0manL on 26/02/21.
 */

public interface PublisherDAO {

    @SqlQuery("SELECT hv_pubmas_pub_name_master_id FROM hv_publisher_name_master WHERE name =:name and hv_tenmas_tenant_master_id =:tenantId")
    @SingleValue
    int getPublisherIDBy(@Bind("name") @NonNull String name, @Bind("tenantId") int tenantId);
}
