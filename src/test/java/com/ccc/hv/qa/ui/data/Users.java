package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.User;
import com.ccc.hv.qa.utils.StringUtils;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.data.PredBUs.PRED_AUTOMATION_BU;
import static com.ccc.hv.qa.ui.data.PredChannels.BU;
import static com.ccc.hv.qa.ui.enums.UserRole.*;
import static com.ccc.hv.qa.utils.EmailUtils.generateUniqueRealEmailAddressWith;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueFakePhoneNumber;

/**
 * Created by R0manL on 20/08/20.
 */

public class Users {
    private static final String UNIQUE_NUMBER = StringUtils.generateUniqueStringBasedOnDate();

    /**
     * Smoke test set users
     */
    public static final User smokeAccountAdmin = User.builder()
            .firstName("AccAdmin_FirstName_" + UNIQUE_NUMBER)
            .lastName("AccAdmin_LastName_" + UNIQUE_NUMBER)
            .phoneNumber(generateUniqueFakePhoneNumber())
            .role(ACCOUNT_ADMINISTRATOR)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("AccAdmin_" + UNIQUE_NUMBER)
            .build();

    public static final User smokeHVViewMng = User.builder()
            .firstName("HrvViewMng_FirstName_" + UNIQUE_NUMBER)
            .lastName("HrvViewMng_LastName_" + UNIQUE_NUMBER)
            .phoneNumber(generateUniqueFakePhoneNumber())
            .role(HARVEST_VIEW_MANAGER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("HrvViewMng_" + UNIQUE_NUMBER)
            .hasAccessToBusinessUnit(PRED_AUTOMATION_BU)
            .build();

    public static final User smokeMetadataAdmin = User.builder()
            .firstName("MetadataAdministrator_FirstName_" + UNIQUE_NUMBER)
            .lastName("MetadataAdministrator_LastName_" + UNIQUE_NUMBER)
            .phoneNumber(generateUniqueFakePhoneNumber())
            .role(METADATA_ADMINISTRATOR)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("MetadataAdmin_" + UNIQUE_NUMBER)
            .build();

    public static final User smokeAccMng = User.builder()
            .firstName("AccMng_FirstName_" + UNIQUE_NUMBER)
            .lastName("AccMng_LastName_" + UNIQUE_NUMBER)
            .phoneNumber(generateUniqueFakePhoneNumber())
            .role(ACCOUNT_MANAGER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("AccMng_" + UNIQUE_NUMBER)
            .hasAccessToBusinessUnit(PRED_AUTOMATION_BU)
            .build();

    public static final User smokeTenantUser = User.builder()
            .firstName("TenantUser_FirstName_" + UNIQUE_NUMBER)
            .lastName("TenantUser_LastName_" + UNIQUE_NUMBER)
            .phoneNumber(generateUniqueFakePhoneNumber())
            .role(TENANT_USER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("TenantUser_" + UNIQUE_NUMBER)
            .hasAccessToBusinessUnit(PRED_AUTOMATION_BU)
            .build();

    public static final User validationUser = User.builder()
            .firstName("Validation")
            .lastName("Validation")
            .phoneNumber(generateUniqueFakePhoneNumber())
            .role(TENANT_USER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("TenantUser_" + UNIQUE_NUMBER)
            .hasAccessToBusinessUnit(PRED_AUTOMATION_BU)
            .build();

    public static final User notWellFormattedEmailValidationUser = User.builder()
            .firstName("Validation")
            .lastName("Validation")
            .phoneNumber(generateUniqueFakePhoneNumber())
            .role(TENANT_USER)
            .email("Validation")
            .password("TenantUser_" + UNIQUE_NUMBER)
            .hasAccessToBusinessUnit(PRED_AUTOMATION_BU)
            .build();

    public static final User HV_VIEW_MNG_ROLE_TEST = User.builder()
            .firstName("Hrv View")
            .lastName("RoleManager")
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Account$Admin$Pass0")
            .phoneNumber("212-000-0003")
            .role(HARVEST_VIEW_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User METADATA_ADMIN_ROLE_TEST = User.builder()
            .firstName("Test Metadata")
            .lastName("RoleAdministrator")
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Metadata$Admin$Pass0")
            .phoneNumber("212-000-0004")
            .role(METADATA_ADMINISTRATOR)
            .enabled(true)
            .build();

    public static final User ACCOUNT_MNG_ROLE_TEST = User.builder()
            .firstName("Account")
            .lastName("RoleManager")
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Account$Manager$Pass0")
            .phoneNumber("212-000-0005")
            .role(ACCOUNT_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User TENANT_USR_ROLE_HV_VIEW_MNG_TEST = User.builder()
            .firstName("Tenant")
            .lastName("RoleUser")
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Tenant$User$Pass0")
            .phoneNumber("212-000-0006")
            .role(TENANT_USER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User TENANT_USR_ROLE_METADATA_ADMIN_TEST = User.builder()
            .firstName("Tenant")
            .lastName("RoleUser")
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Tenant$User$Pass0")
            .phoneNumber("212-000-0006")
            .role(TENANT_USER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User HV_VIEW_MNG_ROLE_UPDATE_TEST = User.builder()
            .firstName("Hrv View")
            .lastName("RoleManager")
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Account$Admin$Pass0")
            .phoneNumber("212-000-0003")
            .role(HARVEST_VIEW_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User METADATA_ADMIN_ROLE_UPDATE_TEST = User.builder()
            .firstName("Test Metadata")
            .lastName("RoleAdministrator")
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Metadata$Admin$Pass0")
            .phoneNumber("212-000-0004")
            .role(METADATA_ADMINISTRATOR)
            .enabled(true)
            .build();

    public static final User ACCOUNT_ADMIN_USR_ROLE_UPDATE_TEST = User.builder()
            .firstName("Tenant")
            .lastName("RoleAdministrator")
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Tenant$User$Pass0")
            .phoneNumber("212-000-0006")
            .role(ACCOUNT_ADMINISTRATOR)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User ACCOUNT_MNG_ROLE_UPDATE_TEST = User.builder()
            .firstName("Account")
            .lastName("RoleManager")
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Account$Manager$Pass0")
            .phoneNumber("212-000-0005")
            .role(ACCOUNT_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User SUPER_ADMIN_UPDATE = User.builder()
            .firstName("Super Admin")
            .lastName("Test")
            .email(generateUniqueRealEmailAddressWith(""))
            .password(ENV_CONFIG.superAdminPassword())
            .phoneNumber("212-000-0000")
            .role(SUPER_ADMINISTRATOR)
            .build();

    public static final User HV_VIEW_MNG_WITH_MULTIPLE_BUs = User.builder()
            .firstName("Multiple BU HV Mng" + UNIQUE_NUMBER)
            .lastName("Multiple BUs" + UNIQUE_NUMBER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Account$Admin$Pass0")
            .phoneNumber("212-000-0003")
            .role(HARVEST_VIEW_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User HV_VIEW_MNG_WITH_SINGLE_BU = User.builder()
            .firstName("Single BU HV Mng" + UNIQUE_NUMBER)
            .lastName("Single BU" + UNIQUE_NUMBER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Account$Admin$Pass0")
            .phoneNumber("212-000-0003")
            .role(HARVEST_VIEW_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User ACCOUNT_MNG_WITH_MULTIPLE_BUs = User.builder()
            .firstName("Multiple BU Acc Mng" + UNIQUE_NUMBER)
            .lastName("Multiple BUs" + UNIQUE_NUMBER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Account$Manager$Pass0")
            .phoneNumber("212-000-0005")
            .role(ACCOUNT_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User ACCOUNT_MNG_WITH_SINGLE_BU = User.builder()
            .firstName("Single BU Acc Mng" + UNIQUE_NUMBER)
            .lastName("Single BU" + UNIQUE_NUMBER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Account$Manager$Pass0")
            .phoneNumber("212-000-0005")
            .role(ACCOUNT_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User ACCOUNT_MNG_FOR_TENANT_USER_WITH_MULTIPLE_BUs = User.builder()
            .firstName("Mult BU Acc Mng for Tnt " + UNIQUE_NUMBER)
            .lastName("Mult BUs" + UNIQUE_NUMBER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Account$Manager$Pass0")
            .phoneNumber("212-000-0005")
            .role(ACCOUNT_MANAGER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final User TENANT_USR_WITH_SINGLE_BU = User.builder()
            .firstName("Single Tnt User BU" + UNIQUE_NUMBER)
            .lastName("Single BUs" + UNIQUE_NUMBER)
            .email(generateUniqueRealEmailAddressWith(""))
            .password("Tenant$User$Pass0")
            .phoneNumber("212-000-0006")
            .role(TENANT_USER)
            .enabled(true)
            .hasAccessToBusinessUnit(BU)
            .build();
    /* end of Smoke test set users */

    private Users() {
        //None
    }
}
