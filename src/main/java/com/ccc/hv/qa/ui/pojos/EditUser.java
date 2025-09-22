package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

/**
 * Created by R0manL on 03/08/20.
 */

@Getter
@Builder
public class EditUser {
    private final UserRole role;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    @Singular
    private final List<BusinessUnit> hasAccessToBusinessUnits;
}
