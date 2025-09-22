package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.EditUser;

import static com.ccc.hv.qa.ui.data.PredChannels.BU;
import static com.ccc.hv.qa.ui.data.Users.*;
import static com.ccc.hv.qa.ui.enums.UserRole.*;
import static com.ccc.hv.qa.utils.EmailUtils.generateUniqueRealEmailAddressWith;

public class EditUsers {
    public static final EditUser TENANT_USR_ROLE_UPDATE_TO_HV_VIEW_MNG_TEST = EditUser.builder()
            .firstName("Updated_FirstName_")
            .lastName("LastName_")
            .email(generateUniqueRealEmailAddressWith(""))
            .phoneNumber("212-000-0006")
            .role(TENANT_USER)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final EditUser METADATA_ADMIN_ROLE_UPDATE_TO_ACCOUNT_MANAGER_TEST = EditUser.builder()
            .firstName("Updated Account")
            .lastName("RoleManager")
            .email(generateUniqueRealEmailAddressWith(""))
            .phoneNumber("212-000-0005")
            .role(ACCOUNT_MANAGER)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final EditUser ACC_ADMIN_USR_ROLE_UPDATE_TO_TENANT_USER_TEST = EditUser.builder()
            .firstName("Updated")
            .lastName("Tenant User")
            .email(generateUniqueRealEmailAddressWith(""))
            .phoneNumber("212-000-0006")
            .role(TENANT_USER)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final EditUser ACC_MANAGER_USR_ROLE_UPDATE_TO_TENANT_USER_TEST = EditUser.builder()
            .firstName("Updated")
            .lastName("Tenant User")
            .email(generateUniqueRealEmailAddressWith(""))
            .phoneNumber("212-000-0006")
            .role(TENANT_USER)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final EditUser HV_VIEW_MNG_BU_UPDATE = EditUser.builder()
            .firstName(HV_VIEW_MNG_WITH_SINGLE_BU.getFirstName())
            .lastName(HV_VIEW_MNG_WITH_SINGLE_BU.getLastName())
            .role(HARVEST_VIEW_MANAGER)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final EditUser ACC_MNG_BU_UPDATE = EditUser.builder()
            .firstName(ACCOUNT_MNG_WITH_SINGLE_BU.getFirstName())
            .lastName(ACCOUNT_MNG_WITH_SINGLE_BU.getLastName())
            .role(ACCOUNT_MANAGER)
            .hasAccessToBusinessUnit(BU)
            .build();

    public static final EditUser TNT_USER_BU_UPDATE = EditUser.builder()
            .firstName(TENANT_USR_WITH_SINGLE_BU.getFirstName())
            .lastName(TENANT_USR_WITH_SINGLE_BU.getLastName())
            .role(TENANT_USER)
            .hasAccessToBusinessUnit(BU)
            .build();
}
