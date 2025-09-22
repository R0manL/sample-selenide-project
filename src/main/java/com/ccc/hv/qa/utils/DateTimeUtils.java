package com.ccc.hv.qa.utils;

import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.PathFormatDateOption;
import com.ccc.hv.qa.ui.enums.PathFormatDateTimeOption;
import com.ccc.hv.qa.ui.enums.PathFormatOption;
import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.utils.enums.DatePattern;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.utils.enums.DateTimePattern.*;

/**
 * Created by R0manL on 12/08/20.
 */

public class DateTimeUtils {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final ZoneId USER_TIMEZONE_ID = TimeZone.getDefault().toZoneId();
    private static final ZoneId SERVER_TIMEZONE_ID = ZoneId.of(ENV_CONFIG.serverTimeZone());


    private DateTimeUtils() {
        // NONE
    }

    @NotNull
    public static LocalDateTime convertFromUIAssetTimeStamp(@NotNull String initialDate) {
        log.debug("Convert '%s' date from UI asset timestamp to LocalDateTime.", initialDate);
        DateTimeFormatter f = DateTimeFormatter.ofPattern(UI_ASSET_TIMESTAMP.toString());
        LocalDateTime time = LocalDateTime.parse(initialDate, f).truncatedTo(ChronoUnit.SECONDS);

        return getLocalDateTimeAtServerZone(time);
    }

    @NotNull
    public static LocalDateTime convertFromDBAssetTimeStamp(@NotNull String initialDate) {
        log.debug("Convert '%s' timestamp from DB to LocalDateTime", initialDate);
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendPattern(DB_ASSET_TIMESTAMP.toString())
                .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 6, true)
                .appendPattern("X")
                .toFormatter();

        LocalDateTime time = LocalDateTime.parse(initialDate, f).truncatedTo(ChronoUnit.SECONDS);

        return getLocalDateTimeAtServerZone(time);
    }

    @NotNull
    public static LocalDateTime convertFromFileModifyDate(@NotNull String initialDate) {
        log.debug("Convert '%s' file modify date to LocalDateTime.", initialDate);
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendPattern(FILE_MODIFY_DATE.toString())
                .toFormatter();

        return LocalDateTime.parse(initialDate, f).truncatedTo(ChronoUnit.SECONDS);
    }

    @NotNull
    public static String changeDateFormat(@NotNull String date, DatePattern oldPattern, DatePattern newPattern) {
        log.debug("Change '" + date + "' date format to a new pattern: '" + newPattern + "'.");

        return LocalDate
                .parse(date, DateTimeFormatter.ofPattern(oldPattern.toString()))
                .format(DateTimeFormatter.ofPattern(newPattern.toString()));
    }

    public static String changeDateFormat(@NotNull LocalDate date, DatePattern newPattern) {
        log.debug("Change '" + date + "' date to a new pattern: '" + newPattern + "'.");
        return date.format(DateTimeFormatter.ofPattern(newPattern.toString()));
    }

    @NotNull
    public static LocalDate convertToLocalDate(PathFormatDateOption format, @NotNull String value) {
        log.debug("Convert '%s' to local date (with pattern: '" + format + "').", value);
        String formatPattern = getDateTimePatternFrom(format);
        return getLocalDateBy(formatPattern, value);
    }

    @NotNull
    public static LocalDate convertToLocalDate(DatePattern datePattern, @NotNull String value) {
        log.debug("Convert '%s' to local date (with pattern: '" + datePattern + "').", value);
        return getLocalDateBy(datePattern.toString(), value);
    }

    @NotNull
    public static LocalDateTime convertToLocalDateTime(@NotNull Timestamp timestamp) {
        log.debug("Convert timestamp to local datetime (truncated to seconds).", "");
        return timestamp.toLocalDateTime().truncatedTo(ChronoUnit.SECONDS);
    }

    @NotNull
    public static LocalDateTime convertToLocalDateTime(PathFormatDateTimeOption format, @NotNull String value) {
        log.debug("Convert '%s' to local datetime (with pattern: '" + format + "').", value);
        String formatPattern = getDateTimePatternFrom(format);
        return getLocalDateTimeBy(formatPattern, value);
    }

    /**
     * Get current date & time on the server (time used to define timestamps in DB)
     *
     * @return current (now) local date and time on the server
     */
    public static LocalDateTime getServerLocalDateTimeNow() {
        log.debug("Get current server's dateTime.");
        ZoneId zoneId = ZoneId.of(ENV_CONFIG.serverTimeZone());

        return LocalDateTime.now(zoneId);
    }

    /**
     * Convert from Batch ODD confirm message format (e.g. 06/14, 4:49 PM) to LocalDateTime
     *
     * @param date from BatchODD confirmation message (e.g. 06/14, 4:49 PM).
     * @return - local dateTime obj.
     */
    public static LocalDateTime convertFromUIBatchODDConfirmMsg(@NotNull String date) {
        log.info("Convert 'BatchODD confirm date': '" + date + "' to LocalDateTime format.");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(BATCH_ODD_CONFIRM_MSG.toString())
                .parseDefaulting(ChronoField.YEAR, Year.now().getValue())
                .toFormatter();

        return LocalDateTime.parse(date, formatter);
    }

    public static LocalDate getServerLocalDateNow() {
        log.debug("Get server's local datetime (now) value.");
        return getServerLocalDateTimeNow().toLocalDate();
    }

    public static LocalDate getLocalDateBy(@NotNull String formatPattern, @NotNull String value) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(formatPattern));
    }

    public static LocalDate convertFromUI(@NotNull String date) {
        return getLocalDateBy(DatePattern.UI_PRODUCT.toString(), date);
    }

    private static LocalDateTime getLocalDateTimeBy(@NotNull String formatPattern, @NotNull String value) {
        return LocalDateTime.parse(value, getDateTimeFormatter(formatPattern));
    }

    private static DateTimeFormatter getDateTimeFormatter(@NotNull String formatPattern) {
        log.debug("Get dateTimeFormatter from formatPattern: '" + formatPattern + "'.");
        formatPattern = formatPattern.replace("SSS", "");

        return new DateTimeFormatterBuilder()
                .appendPattern(formatPattern)
                .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                .toFormatter();
    }

    @NotNull
    private static String getDateTimePatternFrom(@NotNull PathFormatOption pathFormatDateOption) {
        log.debug("Extract dateFormatPattern from pathFormatDateOption: '" + pathFormatDateOption + "'.");

        return pathFormatDateOption
                .toString()
                .replace("${XFER ", "")
                .replace("${UPD ", "")
                .replace("YY", "yy") //'YY' - week-based-year, generate a DateParse exception, should be 'yy' used instead.
                .replace("}", "");
    }

    private static LocalDateTime getLocalDateTimeAtServerZone(LocalDateTime localDateTime) {
        log.debug("Convert '" + localDateTime + "' from: " + USER_TIMEZONE_ID + " to: " + SERVER_TIMEZONE_ID);
        return localDateTime.atZone(USER_TIMEZONE_ID).withZoneSameInstant(SERVER_TIMEZONE_ID).toLocalDateTime();
    }
}