package com.ccc.hv.qa.ui.services;

import com.ccc.hv.qa.db.services.UserDBService;
import com.ccc.hv.qa.ui.pojos.User;
import org.jetbrains.annotations.NotNull;

import static com.ccc.hv.qa.api.services.UserAPIService.getUserAPIService;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.NavigationBar.getNavigationBar;
import static com.ccc.hv.qa.ui.pages.NewUserPasswordPage.openRegistrationUrlFromEmailAndSetNewPasswordFor;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.EmailTestService.getUserActivationUrlFor;

/**
 * Created by R0manL on 11/4/20.
 */

public class UserTestService {

    private UserTestService() {
        // None
    }

    public static UserTestService getUserTestService() {
        return new UserTestService();
    }

    public void createUserViaUI(User user) {
        getTopMenu()
                .clickAddUserWithJS()
                .createUserWithValidation(user);

        String activationURL = getUserActivationUrlFor(user.getEmail());
        openRegistrationUrlFromEmailAndSetNewPasswordFor(user, activationURL);
        getLoginPage().loginAs(user);
        getNavigationBar().clickLogoutLink();
    }

    public void createUserViaAPI(@NotNull User user) {
        getUserAPIService().createUser(user.toUserKeycloak());
        UserDBService.createUser(user);

        String activationURL = getUserActivationUrlFor(user.getEmail());
        openRegistrationUrlFromEmailAndSetNewPasswordFor(user, activationURL);
        getLoginPage().loginAs(user);
        getNavigationBar().clickLogoutLink();
    }
}
