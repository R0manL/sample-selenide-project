package com.ccc.hv.qa.ui;

import com.ccc.hv.qa.api.pojo.KeycloakUser;
import com.ccc.hv.qa.db.pojo.AccountDB;
import com.ccc.hv.qa.db.pojo.TenantDB;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.TestPrefix;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.ccc.hv.qa.api.services.AccountAPIService.getAccountAPIService;
import static com.ccc.hv.qa.api.services.ProductAPIService.getProductAPIService;
import static com.ccc.hv.qa.api.services.TenantAPIService.getTenantAPIService;
import static com.ccc.hv.qa.api.services.UserAPIService.getUserAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.AccountDBService.getAllAccounts;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAIDBy;
import static com.ccc.hv.qa.db.services.ChannelDBService.getIDsOfChannelsWith;
import static com.ccc.hv.qa.db.services.ChannelDBService.hasChannelExistWith;
import static com.ccc.hv.qa.db.services.ConfigDBService.getMaxProductDelete;
import static com.ccc.hv.qa.db.services.ConfigDBService.setMaxProductDelete;
import static com.ccc.hv.qa.db.services.ProductDBService.getNumOfTestProducts;
import static com.ccc.hv.qa.db.services.ProductDBService.markTestProductsForDeletion;
import static com.ccc.hv.qa.db.services.TenantDBService.getListOfTenantNamesFor;
import static com.ccc.hv.qa.db.services.TenantDBService.getListOfAllTenants;
import static com.ccc.hv.qa.ui.data.PredUsers.*;
import static com.ccc.hv.qa.utils.EmailUtils.getEmailPrefix;
import static com.ccc.hv.qa.utils.SSHUtils.executeCommandViaSSH;

/**
 * Created by R0manL on 1/13/21.
 */

public class CleanupScripts {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    public void removeLegacyChannelsAndCAs() {
        final String SSH_HOST = "amlnx-ldx-ans02.digital.prscoad.com";
        final String SSH_USERNAME = "[your username]";
        final String SSH_PASSWORD = "[your password]";

        final String REMOVE_CHANNEL_COMMAND_TEMPLATE = "PGPASSWORD=%s psql -U %s -d hrv -h %s -p %d -v channelId=%d -q -f ./cleanup_scripts/dbscripts/pg/utility/killAChannel.sql";
        final String REMOVE_CHANNEL_ASSOCIATION_COMMAND_TEMPLATE = "PGPASSWORD=%s psql -U %s -d hrv -h %s -p %d -v channelAssocId=%d -q -f ./cleanup_scripts/dbscripts/pg/utility/killAChannelAssociation.sql";
        final String HARVEST_DB_ADMIN_USERNAME = "hrv";
        final String HARVEST_DB_ADMIN_PASSWORD = "7599oP0Y0Z4Z7I3L";
        final String HARVEST_DB_HOSTNAME = "amrds-ldq-hrv01.cv3cojqhrp0o.us-east-1.rds.amazonaws.com";
        final int HARVEST_DB_PORT = 5432;

        //Get list of channels to delete
        List<Integer> channelIds = getIDsOfChannelsWith(TestPrefix.SMOKE.toString());
        channelIds.addAll(getIDsOfChannelsWith(TestPrefix.REGRESSION.toString()));

        int counter = 0;
        int total = channelIds.size();
        log.info("Deleting test channels and CAs, found: '" + total + " '");
        for (int channelId : channelIds) {
            log.info("-------- Deleting channel: '"+ channelId + "' (" + ++counter + " of " + total + ") --------");

            //Delete CA first.
            Integer caId = getCAIDBy(channelId);
            if (caId != null) {
                String rmCACommand = String.format(REMOVE_CHANNEL_ASSOCIATION_COMMAND_TEMPLATE,
                        HARVEST_DB_ADMIN_PASSWORD,
                        HARVEST_DB_ADMIN_USERNAME,
                        HARVEST_DB_HOSTNAME,
                        HARVEST_DB_PORT,
                        caId);

                log.info("Deleting '" + caId + "' CA...");
                executeCommandViaSSH(SSH_HOST, SSH_USERNAME, SSH_PASSWORD, rmCACommand);

                //Verify if CA was deleted.
                caId = getCAIDBy(channelId);
                if (caId != null) {
                    log.warn("Can't delete channel association for channel: '" + channelId + "'.");
                } else {
                    log.info("Channel association has been successfully deleted.");
                }
            } else {
                log.info("No channel association to delete for channel: '" + channelId + "'.");

            }

            //Delete channel.
            String rmChannelCommand = String.format(REMOVE_CHANNEL_COMMAND_TEMPLATE,
                    HARVEST_DB_ADMIN_PASSWORD,
                    HARVEST_DB_ADMIN_USERNAME,
                    HARVEST_DB_HOSTNAME,
                    HARVEST_DB_PORT,
                    channelId);

            executeCommandViaSSH(SSH_HOST, SSH_USERNAME, SSH_PASSWORD, rmChannelCommand);

            if (hasChannelExistWith(channelId)) {
                log.warn("Can't delete '" + channelId + "' channel.");
            } else {
                log.info("Channel '" + channelId + "' has been successfully deleted.");
            }
        }
    }

    /**
     * Delete all test (with appropriate prefix) users from keycloak. Exclude predefined users e.g. SYS_ADMIN, ACC_ADMIN.
     */
    public void cleanupAllTestUsersFromKeycloak() {
        final String TEST_EMAIL_PREFIX = getEmailPrefix(ENV_CONFIG.testGmailAddress());
        log.info("Delete all test users with '" + TEST_EMAIL_PREFIX + "' prefix (except predefined).");

        KeycloakUser[] allUsers = getUserAPIService()
                .getUsersWith(TEST_EMAIL_PREFIX);
        log.debug("Found '" + allUsers.length + "' test users.");

        List<KeycloakUser> testUsers = Arrays.stream(allUsers)
                .filter(u -> !u.getEmail().equals(ACC_ADMIN.getEmail()))
                .filter(u -> !u.getEmail().equals(HV_VIEW_MNG.getEmail()))
                .filter(u -> !u.getEmail().equals(ACCOUNT_MNG.getEmail()))
                .filter(u -> !u.getEmail().equals(METADATA_ADMIN.getEmail()))
                .filter(u -> !u.getEmail().equals(TENANT_USR.getEmail()))
                .collect(Collectors.toList());

        int counter = 0;
        int total = testUsers.size();
        for(KeycloakUser user : testUsers) {
            log.info("Delete '" + user.getUsername() + "' from keycloak (" + ++counter + " of " + total + ").");
            getUserAPIService().deleteUser(user.getId());
        }
    }

    /**
     * Delete all test (with REVISION_PREFIX prefix) products.
     */
    public void cleanupAllTestProducts() {
        log.info("Deleting test products.");
        final int TOTAL_PRODUCTS = getNumOfTestProducts();
        log.info("Found '" + TOTAL_PRODUCTS + "' test products.");

        if(TOTAL_PRODUCTS > 0) {
            markTestProductsForDeletion();

            //Increase number of products to delete in one cycle.
            final int OLD_MAX_PRODUCT_DELETE = getMaxProductDelete();
            final int NEW_MAX_PRODUCT_DELETE = 100;
            setMaxProductDelete(NEW_MAX_PRODUCT_DELETE);

            int remains = TOTAL_PRODUCTS;
            int previous_remains = 0;
            while(remains > 0 && previous_remains != remains) {
                previous_remains = remains;
                getProductAPIService().deleteMarkedProducts();

                remains = getNumOfTestProducts();
                log.debug("Remains: " + remains + " of " + TOTAL_PRODUCTS + ".");
            }

            //Recover previous value
            setMaxProductDelete(OLD_MAX_PRODUCT_DELETE);
        }
    }

    public void cleanupAllTestTenants() {
        log.info("Delete all test tenants...");

        final String TEST_BU_ALPHA_CODE = ENV_CONFIG.alphaCodePrefix();

        Pattern pattern = Pattern.compile("^" + TEST_BU_ALPHA_CODE + "[A-Z]+");
        List<String> tenants = getListOfAllTenants().stream()
                .map(TenantDB::getAlphaCode)
                .collect(Collectors.toList())
                .stream()
                .filter(pattern.asPredicate())
                .collect(Collectors.toList());

        int numOfDeletedTenants = tenants.size();
        tenants.forEach(alphacode -> getTenantAPIService().deleteTenantBy(alphacode));

        log.info(numOfDeletedTenants + " test tenants have been marked for deletion.");
    }

    public void cleanupAllTestAccounts() {
        log.info("Delete all test accounts...");

        List<String> accountsForDeletion = getAllAccounts().stream()
                .filter(acc -> (acc.getAccountName().startsWith("Regr_") || acc.getAccountName().contains("Automation-")))
                .map(AccountDB::getAccountName)
        .collect(Collectors.toList());

        log.info("Found '" + accountsForDeletion.size() + "' test accounts. Deleting...");

        AtomicInteger numOfDeletedAccounts = new AtomicInteger();
        accountsForDeletion
                .forEach(accName -> {
                    int numOfTenants = getListOfTenantNamesFor(accName).size();
                    if(numOfTenants != 0) {
                        log.warn("Can't delete '" + accName + "' account, since it has '" + numOfTenants + "' tenant(s). " +
                                "Delete tenants first. Skip deletion of the account.");
                    } else {
                        numOfDeletedAccounts.getAndIncrement();
                        log.debug("Delete account: '" + accName + "' (" + numOfDeletedAccounts + " of " + accountsForDeletion.size() + ").");
                        getAccountAPIService().deleteAccountBy(accName);
                    }
                } );

        log.info("Summary: " + numOfDeletedAccounts + " of " + accountsForDeletion.size() + " test account have been deleted.");
    }
}
