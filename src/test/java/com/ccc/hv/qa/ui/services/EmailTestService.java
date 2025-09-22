package com.ccc.hv.qa.ui.services;

import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.Email;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ccc.hv.qa.utils.EmailUtils.getSingleEmailAndDeleteItFor;

/**
 * Created by R0manL on 11/3/20.
 */

public class EmailTestService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private EmailTestService() {
        // NONE
    }

    /**
     * Method read 'Activation' emails from mail box and parse activation URL for use with {recipientEmailAddr} email.
     *
     * @param recipientEmailAddr - since each user must have unique e-mail address (that used as loginname), we can
     *                           identify user's emails by recipient's e-mail address.
     * @return URL for activation (for entering a new password).
     */
    @NotNull
    public static String getUserActivationUrlFor(@NotNull String recipientEmailAddr) {
        log.info("Getting activation url from e-mail's body.");
        String emailContent = getSingleEmailAndDeleteItFor(recipientEmailAddr).getContent();

        return getRegistrationLinkFromEmailBody(emailContent);
    }

    public static boolean hasNormalizationFailureEmailReseivedFor(@NotNull String zipFileName) {
        log.info("Getting content from normalization failure email.");
        final String NORMA_FAIL_EMAIL_ADDRESS = "roman.liubun@ccc.com";
        final String NORMA_FAIL_SUBJECT_TEXT = "Hrv Metadata Normalization Failure Notification";

        Email normaFailEmail = getSingleEmailAndDeleteItFor(NORMA_FAIL_EMAIL_ADDRESS);
        String subject = normaFailEmail.getSubject();

        return subject.contains(NORMA_FAIL_SUBJECT_TEXT) && subject.contains(zipFileName);
    }

    /**
     * Method parse email's body (as text) and return URL
     *
     * @param body email's body
     * @return URL
     */
    @NotNull("Url was not found in e-mail's body.")
    private static String getRegistrationLinkFromEmailBody(@NotNull String body) {
        ArrayList<String> links = new ArrayList<>();
        Pattern linkPattern = Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>???“”‘’]))", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher pageMatcher = linkPattern.matcher(body);
        while (pageMatcher.find()) {
            links.add(pageMatcher.group(1));
        }

        // Only one link must be present in e-mail's body.
        if (links.size() == 1) {
            return links.get(0);
        } else {
            throw new IllegalStateException(links.size() + " link(s) were found in e-mail's body. Should be only one.");
        }
    }
}
