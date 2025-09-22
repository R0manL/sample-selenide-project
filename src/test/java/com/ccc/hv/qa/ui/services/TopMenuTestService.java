package com.ccc.hv.qa.ui.services;

import com.ccc.hv.qa.ui.data.PredAccounts;
import com.ccc.hv.qa.ui.data.PredBUs;
import com.ccc.hv.qa.ui.pages.TopMenu;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;

/**
 * Created by R0manL on 11/18/20.
 */

public class TopMenuTestService {

    private TopMenuTestService() {
        // None
    }

    public static TopMenuTestService getTopMenuTestService() {
        return new TopMenuTestService();
    }


    public TopMenu selectDefaultAccount() {
        return getTopMenu().selectAccountBy(PredAccounts.AQA_ACCOUNT.getName());
    }

    public TopMenu selectDefaultBU() {
        String buName = PredBUs.PRED_AUTOMATION_BU.getName();
        return getTopMenu().selectBusinessUnitWithJSBy(buName);
    }

    public TopMenu selectDefaultTestAccountAndBu() {
        selectDefaultAccount();
        return selectDefaultBU();
    }

    public TopMenu selectSecondTestAccountAndBu() {
        return getTopMenu()
                .selectAccountBy(ENV_CONFIG.accountName2())
                .selectBusinessUnitWithJSBy(ENV_CONFIG.testBusinessUnitName2());
    }

    public TopMenu selectSecondTestBU() {
        return getTopMenu().selectBusinessUnitWithJSBy(ENV_CONFIG.testBusinessUnitName2());
    }
}
