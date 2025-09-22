package com.ccc.hv.qa.db.dao;

import com.ccc.hv.qa.db.pojo.AccountDB;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * Created by R0manL on 24/09/20.
 */

public interface AccountDAO {

    @SqlQuery("select hv_accmas_account_master_id from hrv.hv_account_master where account_name=:name")
    @SingleValue
    String getAccountIDBy(@Bind("name") String accountName);

    @SqlUpdate("update hv_account_master set sisense_account =:sisense where account_name =:name")
    void setSSOAccountName(@Bind("name") String accountName, @Bind("sisense") String sisenseAccount);

    @SqlQuery("select * from hv_account_master where account_name =:name")
    @RegisterConstructorMapper(AccountDB.class)
    AccountDB getAllAccounts(@Bind("name") String accountName);

    @SqlQuery("select * from hv_account_master")
    @RegisterConstructorMapper(AccountDB.class)
    List<AccountDB> getAllAccounts();
}
