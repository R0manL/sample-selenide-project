package com.ccc.hv.qa.ui.pages;

import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.User;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by R0manL on 18/08/20.
 */

public class NewUserPasswordPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private NewUserPasswordPage() {
        // NONE
    }

    public static void openRegistrationUrlFromEmailAndSetNewPasswordFor(User user, @NotNull String activationUrl) {
        log.info("Open 'update password' page.");
        open(activationUrl);
        $("#kc-info-message a").click();

        log.info("Submit a new password.");
        String newPassword = user.getPassword();
        $("#password-new").val(newPassword);
        $("#password-confirm").val(newPassword);
        $x("//input[@type='submit']").click();
    }
}
