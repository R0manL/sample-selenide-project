package com.ccc.hv.qa.utils;

import com.codeborne.selenide.Configuration;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.pojos.Email;
import org.jetbrains.annotations.NotNull;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.sleep;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;

/**
 * Created by R0manL on 18/08/20.
 */


public class EmailUtils {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final String ccc_EMAIL_SUFFIX = "@ccc.com";
    private static final String EMAIL_DELIMITER = "@";
    private static final String ALIAS_DELIMITER = "+";
    private static final String SINGLE_EMAIL_ERROR_TEMPLATE = "Expect '1', but '%s' e-mail(s) have been received.";


    private EmailUtils() {
        //NONE
    }

    @NotNull
    public static Email getSingleEmailAndDeleteItFor(@NotNull String recipientEmailAddr) {
        List<Email> emails;
        Email result = null;
        // Try 3 times to find e-mail in inbox, since some e-mails come with delay.
        for (int i = 0; i < 5; i++) {
            emails = readEmailsAndRemoveAfterFor(recipientEmailAddr, false);
            int numOfEmails = emails.size();
            switch (numOfEmails) {
                case 0:
                    sleep(Configuration.timeout / 8);
                    break;
                case 1:
                    result = emails.get(0);
                    break;
                default:
                    throw new IllegalStateException(String.format(SINGLE_EMAIL_ERROR_TEMPLATE, numOfEmails));
            }

            if (result != null) {
                return result;
            }
        }

        throw new IllegalStateException("No email(s) have been found.");
    }

    /**
     * Method generate real (used for our test gmail box) e-mail address with defined {alias} and unique generated string.
     *
     * @param alias that will be added at the beginning of email address.
     * @return e-mail address with unique alias (that we can identify whom addressed test e-mail)
     */
    @NotNull
    public static String generateUniqueRealEmailAddressWith(@NotNull String alias) {
        final String uniqueNum = generateUniqueStringBasedOnDate();
        final String testEmail = ENV_CONFIG.testGmailAddress();

        return addAliasToEmail(testEmail, alias + uniqueNum);
    }

    /**
     * Method generate real (used for our test gmail box) e-mail address with defined {alias}.
     *
     * @param alias that will be added at the beginning of email address.
     * @return e-mail address with unique alias (that we can identify whom addressed test e-mail)
     */
    @NotNull
    public static String generateRealEmailAddressWith(@NotNull String alias) {
        return addAliasToEmail(ENV_CONFIG.testGmailAddress(), alias);
    }

    /**
     * Method generate fake (not real) e-mail address.
     *
     * @return fake e-mail address.
     */
    @NotNull
    public static String generateUniqueFakeEmailAddress(@NotNull String prefix) {
        final String uniqueNum = generateUniqueStringBasedOnDate();
        return prefix + uniqueNum + ccc_EMAIL_SUFFIX;
    }

    public static String getEmailPrefix(@NotNull String email) {
        log.debug("Get '" + email + "' email's prefix.");
        String[] parts = email.split(EMAIL_DELIMITER);

        return parts[0];
    }

    /**
     * Method adding alias to e-mail address.
     *
     * @param emailAddress e-mail address
     * @param alias        alias
     * @return new e-mail address with the alias.
     */
    private static String addAliasToEmail(@NotNull String emailAddress, @NotNull String alias) {
        log.debug("Add '" + alias + "' alias to '" + emailAddress + "' emailAddress address.");
        String[] parts = emailAddress.split(EMAIL_DELIMITER);
        return parts[0] + ALIAS_DELIMITER + alias + EMAIL_DELIMITER + parts[1];
    }

    /**
     * Method read gmail inbox via SMTP, return matched emails.
     * Method can be used as cleanup method.
     *
     * @param deleteNotMatchedEmails - remove emails even if recipient's filterByEmail do not match.
     * @return List of emails with subject, content, list of senders, list of recipients.
     * @partam filterByEmail - recipient's email to match, if empty - all emails will be returned
     */
    @NotNull
    private static List<Email> readEmailsAndRemoveAfterFor(@NotNull String filterByEmail, boolean deleteNotMatchedEmails) {
        final String host = "imap.gmail.com";
        final String username = ENV_CONFIG.testGmailAddress();
        final String password = ENV_CONFIG.testGmailPassword();

        log.debug("Filter by '" + filterByEmail + "' emails (if empty all email will be returned).");
        Store store;
        Folder inbox;
        List<Email> result = new LinkedList<>();
        try {
            // create properties
            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");
            properties.put("mail.imap.ssl.trust", host);

            Session emailSession = Session.getDefaultInstance(properties);
            store = emailSession.getStore("imaps");
            store.connect(host, username, password);
            inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);

            // retrieve the allMsgs from the folder in an array.
            Message[] allMsgs = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
            log.debug("Inbox have '" + allMsgs.length + "' filterByEmail(s).");
            for (Message message : allMsgs) {
                Address[] toRecipientAddrs = message.getRecipients(Message.RecipientType.TO);
                boolean isRecipientPresent = Arrays.stream(toRecipientAddrs)
                        .map(Address::toString)
                        .anyMatch(filterByEmail::equalsIgnoreCase);

                if (isRecipientPresent) {
                    final Email resultEmail = Email.builder()
                            .senders(Arrays.stream(message.getFrom()).collect(Collectors.toList()))
                            .recipients(Arrays.stream(toRecipientAddrs).collect(Collectors.toList()))
                            .subject(message.getSubject())
                            .content(getTextFromMessageBody(message))
                            .build();

                    result.add(resultEmail);
                }

                if (isRecipientPresent || deleteNotMatchedEmails) {
                    log.debug("Delete email: '" + message.getSubject() + "'.");
                    message.setFlag(Flag.DELETED, true);
                }
            }

            inbox.close(false);
            store.close();
        } catch (MessagingException e) {
            throw new IllegalStateException("Can't read messages.\nError: " + e.getMessage());
        }

        return result;
    }

    public static void cleanupTestInbox() {
        log.info("Cleanup test inbox.");
        readEmailsAndRemoveAfterFor("*", true);
    }

    /**
     * Method convert e-mail's body into text. Some email's body may contains from "html" + "text".
     *
     * @param message where we getting body.
     * @return body as a text
     */
    @NotNull
    private static String getTextFromMessageBody(@NotNull Message message) {
        String result = "";
        try {
            if (message.isMimeType("text/plain") || message.isMimeType("TEXT/HTML; charset=UTF-8")) {
                result = message.getContent().toString();
            } else if (message.isMimeType("multipart/*")) {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                result = getTextFromMimeMultipart(mimeMultipart);
            }
        } catch (MessagingException | IOException e) {
            throw new IllegalStateException("Can't get body from the message, details: " + e.toString());
        }

        return result;
    }

    /**
     * Method convert multipart email body into text.
     *
     * @param mimeMultipart part of email body with multipart
     * @return multipart body as text
     */
    @NotNull
    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result.append("\n").append(org.jsoup.Jsoup.parse(html).text());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }

        return result.toString();
    }
}
