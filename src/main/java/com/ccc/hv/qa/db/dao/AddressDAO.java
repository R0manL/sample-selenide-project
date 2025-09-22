package com.ccc.hv.qa.db.dao;

import com.ccc.hv.qa.db.pojo.AddressDB;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;


public interface AddressDAO {

    @SqlQuery("SELECT * FROM hv_address_master where hv_addmas_address_id =:addressId;")
    @RegisterConstructorMapper(AddressDB.class)
    AddressDB getAddressBy(@Bind("addressId") int id);
}
