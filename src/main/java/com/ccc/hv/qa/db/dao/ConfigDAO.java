package com.ccc.hv.qa.db.dao;

import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * Created by R0manL on 24/09/20.
 */

public interface ConfigDAO {

    @SqlQuery("SELECT value FROM hv_config_master WHERE key = 'MAX_PRODUCT_DELETE';")
    @SingleValue
    int getMaxProductDelete();

    @SqlUpdate("UPDATE hv_config_master SET value =:value WHERE key = 'MAX_PRODUCT_DELETE';")
    void setMaxProductDelete(@Bind("value") int value);

}
