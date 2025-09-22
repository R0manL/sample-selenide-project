package com.ccc.hv.qa.db.pojo;

import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

/**
 * Created by R0manL on 25/09/20.
 */

@Value
public class UserDB {
    Integer hvUsrmasUserMasterId;
    @NotNull
    String passwordHash;
    @NotNull
    String username;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String phoneNumber;
    boolean accountExpiredYn;
    boolean accountLockedYn;
    boolean activeYn;
    boolean passwordExpiredYn;
    String emailSentUtc;
    ZonedDateTime createDateUtc;
    ZonedDateTime updateDateUtc;
    Integer hvAccmasAccountMasterId;
    @NotNull
    Integer hvRolmasRoleMasterId;
}
