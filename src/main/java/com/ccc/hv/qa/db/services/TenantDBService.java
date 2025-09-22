package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.TenantDAO;
import com.ccc.hv.qa.db.pojo.AddressDB;
import com.ccc.hv.qa.db.pojo.TenantDB;
import com.ccc.hv.qa.logging.AllureLogger;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.SortedSet;

import static com.ccc.hv.qa.db.services.AccountDBService.getAccountIDBy;
import static com.ccc.hv.qa.db.services.AddressDBService.getAddressBy;
import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;


/**
 * Created by R0manL on 21/08/20.
 */

public class TenantDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private TenantDBService() {
        //NONE
    }

    public static SortedSet<String> getAllAlphaCodes() {
        log.debug("Getting all alphacodes.");
        return createConnection()
                .onDemand(TenantDAO.class)
                .getAllAlphaCodes();
    }

    public static int getTenantIDBy(@NotNull String name) {
        log.debug("Getting business unit's ID by name: " + name);
        return createConnection()
                .onDemand(TenantDAO.class)
                .getBusinessUnitIDBy(name);
    }

    public static boolean isTenantExist(@NotNull String name) {
        log.debug("Checking if BU: '" + name + "' exist.");
        return createConnection()
                .onDemand(TenantDAO.class)
                .isBusinessUnitExist(name);
    }

    public static boolean isRecordSourceNameExist(@NotNull String name) {
        log.debug("Check if '" + name + "' record source name exists.");
        return createConnection()
                .onDemand(TenantDAO.class)
                .isRecordSourceNameExist(name);
    }

    @NonNull
    public static String getTenantNameBy(@NotNull String alphacode) {
        log.debug("Getting tenant name by '" + alphacode + "' alphacode.");
        return createConnection()
                .onDemand(TenantDAO.class)
                .getTenantNameBy(alphacode);
    }

    @NonNull
    public static TenantDB getTenantBy(@NotNull String name) {
        log.debug("Getting tenant by '" + name + "' name.");
        return createConnection()
                .onDemand(TenantDAO.class)
                .getTenantBy(name);
    }

    @NonNull
    public static AddressDB getTenantPhysicalAddressBy(@NotNull String tenantName) {
        log.debug("Getting physical address of '" + tenantName + "' tenant.");
        int addressId = getTenantBy(tenantName).getHvTenmasPhysicalAddressId();

        return getAddressBy(addressId);
    }

    @NonNull
    public static String getTenantNormalizationProfileOnix21By(@NotNull String name) {
        log.debug("Getting tenant's normalization profile for onix 2.1 by name: " + name);
        return getTenantBy(name).getNormProfileOnix21();
    }

    @NonNull
    public static String getTenantNormalizationProfileOnix30By(@NotNull String name) {
        log.debug("Getting tenant's normalization profile for onix 3.0 by name: " + name);
        return getTenantBy(name).getNormProfileOnix30();
    }

    @NonNull
    public static List<String> getListOfTenantNamesFor(@NotNull String accountName) {
        log.debug("Getting all tenants' names for '" + accountName + "' account.");
        int accountId = Integer.parseInt(getAccountIDBy(accountName));

        return createConnection()
                .onDemand(TenantDAO.class)
                .getListOfTenantNamesFor(accountId);
    }

    public static List<TenantDB> getListOfAllTenants() {
        log.debug("Getting all tenants.");

        return createConnection()
                .onDemand(TenantDAO.class)
                .getAllTenants();
    }
}
