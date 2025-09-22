package com.ccc.hv.qa.db.dao;

import com.ccc.hv.qa.db.pojo.TenantDB;
import lombok.NonNull;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.SortedSet;

/*
 * Created by R0manL on 21/08/20.
 */

/**
 * Describes methods to work with HV_TENANT_MASTER table.
 */
public interface TenantDAO {
    /**
     * Method getting all Alpha Codes from V_TENANT_MASTER table.
     * @return list of Alpha Codes ordered by alpha code.
     */
    @SqlQuery("select alpha_code from hv_tenant_master order by alpha_code")
    SortedSet<String> getAllAlphaCodes();

    /**
     * Getting BU name by it's ID.
     * @param tenantName - BU's name.
     * @return - BU's ID.
     */
    @SqlQuery("select hv_tenmas_tenant_master_id from hv_tenant_master where name=:name")
    @SingleValue
    int getBusinessUnitIDBy(@Bind("name") @NonNull String tenantName);

    @SqlQuery("SELECT CASE WHEN EXISTS (SELECT * FROM hv_tenant_master WHERE name=:name) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    @SingleValue
    boolean isBusinessUnitExist(@Bind("name") @NonNull String tenantName);

    /**
     * Check if Record Source Name has already existed in DB.
     * @param recordSourceName value
     * @return true - if exist, false - if not.
     */
    @SqlQuery("SELECT CASE WHEN EXISTS (SELECT * FROM hv_tenant_master WHERE record_source_name=:name) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    boolean isRecordSourceNameExist(@Bind("name") @NotNull String recordSourceName);

    @SqlQuery("SELECT name FROM hv_tenant_master where alpha_code =:alphacode")
    @SingleValue
    String getTenantNameBy(@Bind("alphacode") @NonNull String alphacode);

    @SqlQuery("SELECT * FROM hv_tenant_master where name =:name")
    @RegisterConstructorMapper(TenantDB.class)
    TenantDB getTenantBy(@Bind("name") String tenantName);

    @SqlQuery("SELECT name FROM hv_tenant_master where hv_accmas_account_master_id =:accountId")
    List<String> getListOfTenantNamesFor(@Bind("accountId") int accountId);

    @SqlQuery("SELECT * FROM hv_tenant_master where hv_accmas_account_master_id =:accountId")
    @RegisterConstructorMapper(TenantDB.class)
    List<TenantDB> getListOfTenantsFor(@Bind("accountId") int accountId);

    @SqlQuery("select * from hv_tenant_master")
    @RegisterConstructorMapper(TenantDB.class)
    List<TenantDB> getAllTenants();
}
