package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import com.ccc.hv.qa.ui.pojos.User;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.enums.UserRole.*;
import static com.ccc.hv.qa.utils.EmailUtils.generateRealEmailAddressWith;

/**
 * Created by R0manL on 04/08/20.
 */

public class PredUsers {
    private static final String ACCOUNT = PredAccounts.AQA_ACCOUNT.getName();
    private static final BusinessUnit BU = PredBUs.PRED_AUTOMATION_BU;

    private static final String ADMINISTRATOR = "Administrator";


    public static final User SUPER_ADMIN = User.builder()
            .firstName("Reaper")
            .lastName("Test")
            .email(ENV_CONFIG.superAdminEmail())
            .password(ENV_CONFIG.superAdminPassword())
            .phoneNumber("212-000-0000")
            .role(SUPER_ADMINISTRATOR)
            .accountName(ACCOUNT)
            .build();

    public static final User SYS_ADMIN = User.builder()
            .firstName(ADMINISTRATOR)
            .lastName("System")
            .email(generateRealEmailAddressWith("system.admin"))
            .password("System$Admin$Pass0")
            .accountName(ACCOUNT)
            .role(SYSTEM_ADMINISTRATOR)
            .phoneNumber("212-000-0001")
            .enabled(true)
            .build();

    public static final User ACC_ADMIN = User.builder()
            .firstName(ADMINISTRATOR)
            .lastName("Account")
            .email(generateRealEmailAddressWith("account.admin"))
            .password("Account$Admin$Pass0")
            .accountName(ACCOUNT)
            .role(ACCOUNT_ADMINISTRATOR)
            .phoneNumber("212-000-0002")
            .enabled(true)
            .build();

    public static final User HV_VIEW_MNG = User.builder()
            .firstName("Hrv View")
            .lastName("Manager")
            .email(generateRealEmailAddressWith("hvview.mng"))
            .password("Account$Admin$Pass0")
            .accountName(ACCOUNT)
            .phoneNumber("212-000-0003")
            .role(HARVEST_VIEW_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User METADATA_ADMIN = User.builder()
            .firstName("Test Metadata")
            .lastName(ADMINISTRATOR)
            .email(generateRealEmailAddressWith("metadata.admin"))
            .password("Metadata$Admin$Pass0")
            .accountName(ACCOUNT)
            .phoneNumber("212-000-0004")
            .role(METADATA_ADMINISTRATOR)
            .enabled(true)
            .build();

    public static final User ACCOUNT_MNG = User.builder()
            .firstName("Account")
            .lastName("Manager")
            .email(generateRealEmailAddressWith("account.mng"))
            .password("Account$Manager$Pass0")
            .accountName(ACCOUNT)
            .phoneNumber("212-000-0005")
            .role(ACCOUNT_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User TENANT_USR = User.builder()
            .firstName("Tenant")
            .lastName("User")
            .email(generateRealEmailAddressWith("tenant.user"))
            .password("Tenant$User$Pass0")
            .accountName(ACCOUNT)
            .phoneNumber("212-000-0006")
            .role(TENANT_USER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();


    private PredUsers() {
        //NONE
    }
}