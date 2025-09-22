package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.AccountDAO;
import com.ccc.hv.qa.db.pojo.AccountDB;
import com.ccc.hv.qa.db.pojo.AddressDB;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.ccc.hv.qa.db.services.AddressDBService.getAddressBy;
import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;

/**
 * Created by R0manL on 24/09/20.
 */

public class AccountDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private AccountDBService() {
        //NONE
    }

    @NotNull("Can't find account in DB.")
    public static String getAccountIDBy(@NotNull String name) {
        log.debug("Getting '" + name + "' account ID.");
        return createConnection()
                .onDemand(AccountDAO.class)
                .getAccountIDBy(name);
    }

    public static void setSSOAccountFor(@NotNull String accountName, @NotNull String ssoAccountName) {
        log.debug("Updating '" + accountName + "' account with a new SSO account name:'" + ssoAccountName + "'.");
        createConnection()
                .onDemand(AccountDAO.class)
                .setSSOAccountName(accountName, ssoAccountName);
    }

    @NotNull
    public static AccountDB getAccountBy(@NotNull String accountName) {
        log.debug("Getting '" + accountName + "' account.");
        return createConnection()
                .onDemand(AccountDAO.class)
                .getAllAccounts(accountName);
    }

    public static AddressDB getAccountPhysicalAddress(@NotNull String accountName) {
        log.debug("Getting physical address for '" + accountName + "' account.");
        int addressId =  getAccountBy(accountName).getHvAccmasPhysicalAddressId();

        return getAddressBy(addressId);
    }

    @NotNull
    public static List<AccountDB> getAllAccounts() {
        log.debug("Getting all accounts.");
        return createConnection()
                .onDemand(AccountDAO.class)
                .getAllAccounts();
    }
}
