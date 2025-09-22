package com.ccc.hv.qa.ui;

import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.data.PredAccounts;
import com.ccc.hv.qa.ui.data.PredBUs;
import com.ccc.hv.qa.ui.pojos.Account;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import com.ccc.hv.qa.ui.pojos.ChannelPublic;
import com.ccc.hv.qa.ui.pojos.User;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.api.services.AccountAPIService.getAccountAPIService;
import static com.ccc.hv.qa.ui.data.PredChannelAssociations.*;
import static com.ccc.hv.qa.ui.data.PredChannels.*;
import static com.ccc.hv.qa.ui.data.PredUsers.*;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static com.ccc.hv.qa.ui.services.UserTestService.getUserTestService;

/**
 * Created by R0manL on 31/08/20.
 */

public class NewEnvironmentSetupScript extends UITestBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final Account ACCOUNT   = PredAccounts.AQA_ACCOUNT;
    private static final Account ACCOUNT_2 = PredAccounts.AQA_ACCOUNT_2;
    private static final BusinessUnit BU   = PredBUs.PRED_AUTOMATION_BU;
    private static final BusinessUnit BU_2 = PredBUs.PRED_AUTOMATION_BU_2;
    private static final User[] USERS = {ACC_ADMIN, HV_VIEW_MNG, ACCOUNT_MNG, METADATA_ADMIN, TENANT_USR};
    private static final ChannelPublic[] CHANNELS = {PRED_CHANNEL_FTP, PRED_CHANNEL_FTPS, PRED_CHANNEL_SFTP, PRED_CHANNEL_ITMS};


    @Test
    public void envPredefinedDataSetup() {
        log.info("Setup a new Super Duper Admin User...");
        getUserTestService().createUserViaAPI(SUPER_ADMIN);

        log.info("Setup a new test environment...");
        getLoginPage().loginAs(SUPER_ADMIN);


        log.info("1. Create automation test accounts.");
        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT);

        getTopMenu()
                .addAccountWithJS()
                .createAccount(ACCOUNT_2);


        log.info("2. Create automation business unit.");
        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(BU);

        getTopMenuTestService()
                .selectSecondTestAccountAndBu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(BU_2);


        log.info("3. Activate account.");
        getAccountAPIService().activateAccount(ACCOUNT.getName());
        getAccountAPIService().activateAccount(ACCOUNT_2.getName());


        log.info("4. Create automation test users.");
        for (User user : USERS) {
            getTopMenuTestService().selectDefaultTestAccountAndBu();
            getUserTestService().createUserViaUI(user);
            getLoginPage().loginAs(SUPER_ADMIN);
        }


        log.info("5. Create automation test channels.");
        getTopMenuTestService().selectDefaultTestAccountAndBu();
        for (ChannelPublic channel : CHANNELS) {
            getTopMenu()
                    .clickAddChannel()
                    .addPublicChannel(channel);
        }


        log.info("6. Create channel associations.");
        getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .clickManageChannelWithJS()
                .openAddChannelAssociationsPageFor(PRED_CHANNEL_FTP.getName())
                .createChannelAssociation(PRED_CHANNEL_ASSOCIATIONS_FTP)
                .clickSave()
                .isSuccessMsgVisible()
                .returnToAssociatedChannelsList()
                .openAddChannelAssociationsPageFor(PRED_CHANNEL_SFTP.getName())
                .createChannelAssociation(PRED_CHANNEL_ASSOCIATIONS_SFTP)
                .clickSave()
                .isSuccessMsgVisible()
                .returnToAssociatedChannelsList()
                .openAddChannelAssociationsPageFor(PRED_CHANNEL_FTPS.getName())
                .createChannelAssociation(PRED_CHANNEL_ASSOCIATIONS_FTPS)
                .clickSave()
                .isSuccessMsgVisible()
                .returnToAssociatedChannelsList()
                .openAddChannelAssociationsPageFor(PRED_CHANNEL_ITMS.getName())
                .createChannelAssociation(PRED_CHANNEL_ASSOCIATIONS_ITMS)
                .clickSave()
                .isSuccessMsgVisible();

        log.info("New test environment has successfully setup.");
    }
}