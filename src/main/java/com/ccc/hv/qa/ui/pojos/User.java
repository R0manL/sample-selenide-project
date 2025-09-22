package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.api.pojo.KeycloakUser;
import com.ccc.hv.qa.db.pojo.UserDB;
import com.ccc.hv.qa.ui.enums.UserRole;
import lombok.*;

import java.util.List;

/**
 * Created by R0manL on 03/08/20.
 */

@Getter
@Setter
@Builder
public class User {
    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
    @NonNull
    private final String email;
    @NonNull
    private final String password;
    @NonNull
    private final String phoneNumber;
    @NonNull
    private final UserRole role;
    private final boolean enabled;
    @Singular
    private List<BusinessUnit> hasAccessToBusinessUnits;
    private final String accountName;

    public UserDB toUserDB() {
        return new UserDB(
                null,
                this.getRole().getPassHashCode(),
                this.getEmail(),
                this.getFirstName(),
                this.getLastName(),
                this.getPhoneNumber(),
                false,
                false,
                true,
                false,
                null, null, null, null,
                this.getRole().getId());
    }

    public KeycloakUser toUserKeycloak(){
        return new KeycloakUser(null,
                this.getEmail(),
                this.getEmail(),
                this.getFirstName(),
                this.getLastName());
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
