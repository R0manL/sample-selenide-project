package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.UserDAO;
import com.ccc.hv.qa.db.pojo.UserDB;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.User;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;

/**
 * Created by R0manL on 25/09/20.
 */

public class UserDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private UserDBService() {
        //NONE
    }

    @NotNull
    public static UserDB getUserBy(@NotNull String userName) {
        log.debug("Getting '" + userName + "' user.");
        return createConnection()
                .onDemand(UserDAO.class)
                .getUserBy(userName);
    }

    public static boolean isUserExist(@NotNull String userName) {
        log.debug("Check if '" + userName + "' user exist.");
        return createConnection()
                .onDemand(UserDAO.class)
                .isUserExist(userName);
    }

    public static void createUser(@NotNull User user) {
        createUser(user.toUserDB());
    }

    public static void createUser(@NotNull UserDB user) {
        final String USERNAME = user.getUsername();

        if (!isUserExist(user.getUsername())) {
            log.debug("Create '" + USERNAME + "' user in DB");
            createConnection()
                    .onDemand(UserDAO.class)
                    .createUser(user);

            if(!isUserExist(user.getUsername())) {
                throw new IllegalStateException("User '" + USERNAME + "' has not been created.");
            }
        } else {
            log.warn("User '" + USERNAME + "' has already existed in the Hrv DB. Skip creation.");
        }
    }
}
