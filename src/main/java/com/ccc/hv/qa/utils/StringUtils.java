package com.ccc.hv.qa.utils;

import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.utils.enums.DateTimePattern;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.TenantDBService.getAllAlphaCodes;
import static com.ccc.hv.qa.utils.FileOpsUtils.getRevisionDelimiter;

/**
 * Created by R0manL on 12/08/20.
 */

public class StringUtils {
    public static final String REVISION_PREFIX = "_rev_";

    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final Random random = new Random();
    private static final int ALPHACODE_MAX_LENGTH = 50;
    private static final int DEFAULT_THREAD_NUM = 0;
    private static final int MAX_NUMBER_OF_THREADS = 25;


    private StringUtils() {
        // NONE
    }

    @NotNull
    public static String generateUniqueStringBasedOnDate() {
        return generateUniqueStringBasedOnDate("");
    }

    /**
     * Method generate unique string: [prefix] + [year + month + day + hours + minutes + seconds] + [random number 0 - 999, depends on number of current thread (if tests are running in parallel)].
     * @param prefix - will be added at the beginning of generated unique string.
     *               If you do not need any prefix just pass empty string.
     * @return [prefix] + [unique string]
     */
    public static String generateUniqueStringBasedOnDate(@NotNull String prefix) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat(DateTimePattern.YYMMDDHHMMSS.toString());
        int threadNum = getThreadNumFrom(Thread.currentThread().getName());
        int n = random.nextInt(999 - threadNum) + threadNum;

        return prefix + ft.format(dNow) + n;
    }

    @NotNull
    public static String generateUniqueFakePhoneNumber() {
        return "+1" + generateUniqueStringBasedOnDate();
    }

    @NotNull
    public static String generate10UniqueNumbersBasedOnDate() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("MMddhhmm");
        int n = random.nextInt(99);

        return ft.format(dNow) + (n + "");
    }

    @NotNull
    public static String generateUniqueIsbn13BasedOnDate() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("MMddhhmmss");
        int n = random.nextInt(999);

        return ft.format(dNow) + (n + "");
    }

    @NotNull
    public static String generateUniqueIsbn10BasedOnDate() {
        return generate10UniqueNumbersBasedOnDate();
    }

    @NotNull
    public static String updateTextRevisionNumber(@NotNull String textWithOldRevisionNumber) {
        String[] subStrings = textWithOldRevisionNumber.split(REVISION_PREFIX);
        return subStrings[0] + REVISION_PREFIX + generateUniqueStringBasedOnDate();
    }

    /**
     * Method generate unique Record Source Name based on date.
     * @return - unique generated record source name.
     */
    @NotNull
    public static String generateUniqueRecordSourceName() {
        return generateUniqueStringBasedOnDate("record source name ");
    }

    /**
     * Generate unique Alphacode and check if it has already exist in DB.
     * Alpha code must be in range 3-7 of uppercase characters.
     * @return - Alphacode
     */
    @NotNull
    public static String generateUniqueAlphaCode() {
        log.debug("Generating unique alpha code...");
        final String ALPHACODE_PREFIX = ENV_CONFIG.alphaCodePrefix();
        String result = ALPHACODE_PREFIX + RandomStringUtils.randomAlphabetic(ALPHACODE_MAX_LENGTH - ALPHACODE_PREFIX.length()).toUpperCase();

        SortedSet<String> existingAlphaCodes = getAllAlphaCodes();
        if (existingAlphaCodes.contains(result)) {
            throw new IndexOutOfBoundsException("Generated alphacode: '" + result + "' has already exist in DB.");
        }

        return result;
    }

    private static int getThreadNumFrom(@NotNull String threadName) {
        log.debug("Extract thread number from: '" + threadName + "' thread name.");
        int index = threadName.lastIndexOf("-");
        int result = index > 0 ? Integer.parseInt(threadName.substring(index + 1)) : DEFAULT_THREAD_NUM;

        if (result < 0 || result > MAX_NUMBER_OF_THREADS) {
            throw new IllegalArgumentException("Thread number must be in a range: " + DEFAULT_THREAD_NUM + "-"
                    + MAX_NUMBER_OF_THREADS + ", current='" + result + "'.");
        }

        return result;
    }

    public static String removeSpaces(String str){
        return str.replace(" ","");
    }

    public static String remove2LastChar(String str){
        return removeLastChars(str, 2);
    }

    public static String removeLastChars(String str, int chars){
        return str.substring(0, str.length()-chars);
    }

    public static String getIsbnFrom(@NotNull String recordReference){
        return recordReference.split(getRevisionDelimiter())[0];
    }
}
