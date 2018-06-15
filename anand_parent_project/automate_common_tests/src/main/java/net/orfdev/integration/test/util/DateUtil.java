package net.orfdev.integration.test.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DateUtil {

    private static final Logger log = LogManager.getLogger(null, null);

    public static final LocalDate MIN = LocalDate.of(1970, 1, 1);
    public static final LocalDate MAX = LocalDate.of(2199, 1, 1);
    public static final int MAX_DAYS = 540;

    private static final String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyyMMdd", "yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "dd/MM/yy"};
    private static final Map<String, Formatter> formatters;

    static {
        formatters = new HashMap<String, Formatter>();
        for (String pattern : patterns)
            formatters.put(pattern, new Formatter(pattern));
    }

    public static Date toDate(LocalDate ld) {
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date toDate(LocalDateTime ldt) {
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date toDate(LocalDateTime ldt, ZoneId zoneId) {
        Instant instant = ldt.atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime parse(String dt, String pattern) {
        if (!formatters.containsKey(pattern))
            formatters.put(pattern, new Formatter(pattern));
        return formatters.get(pattern).parse(dt);
    }

    public static LocalDate parseLocalDate(String dt, String pattern) {
        if (!formatters.containsKey(pattern))
            formatters.put(pattern, new Formatter(pattern));
        return formatters.get(pattern).parseLocalDate(dt);
    }

    public static LocalDateTime parse(String dt) {
        for (String pattern : patterns) {
            try {
                LocalDateTime t = parse(dt, pattern);
                return t;
            } catch (DateTimeParseException e) {
                if (log.isDebugEnabled())
                    log.debug(e);
            }
        }
        return null;
    }

    public static String format(LocalDateTime dt, String pattern) {
        if (!formatters.containsKey(pattern))
            formatters.put(pattern, new Formatter(pattern));
        return dt.format(formatters.get(pattern).getFormatter());
    }

    public static String now() {
        return LocalDateTime.now().format(formatters.get("yyyy-MM-dd HH:mm:ss").getFormatter());
    }

    public static LocalDate toLocalDate(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    public static String format(java.sql.Date d, String pattern) {
        return format(d.toLocalDate().atStartOfDay(), pattern);
    }

    public static String format(Timestamp d, String pattern) {
        return format(d.toLocalDateTime(), pattern);
    }

    public static String format(LocalDate d, String pattern) {
        return format(d.atStartOfDay(), pattern);
    }

    /**
     * Formats LocalDateTime by applying a specified pattern.
     * returns '---' if the LocalDateTime is null or outside an acceptable range.
     * TODO review 'check min max' behavior - looks like it should not be checked here at all (or should throw exception instead of formatting the date as '---')
     * LP: as an interim measure I've reverted the old "format" method to ignore the whole '---' issue and created this safeFormat method to encapsulated the
     * weirdness (and added a <null> check too)
     */
    public static String safeFormat(LocalDateTime dt, String pattern) {
        if (null == dt || (dt.isBefore(MIN.atStartOfDay()) || dt.isAfter(MAX.atStartOfDay())))
            return "---";
        return format(dt, pattern);
    }

    public static String safeFormat(LocalDate d, String pattern) {
        if (null == d)
            return "---";
        return safeFormat(d.atStartOfDay(), pattern);
    }

    public static int getAgeAt(LocalDate dob, LocalDate atDate) {
        return (int) dob.until(atDate, ChronoUnit.YEARS);
    }

    public static ZonedDateTime zoned(LocalDateTime localDateTime) {
        // This is a dangerous method. It assumes that the localDateTime was created by Java with LocalDateTime.now();
        // It also assumes that this will be deprecated at some point in the future as we will only persist ZonedDateTime.
        return localDateTime.atZone(ZoneId.of("Australia/Sydney"));
    }
}

class Formatter {

    private DateTimeFormatter formatter;
    private boolean useLocalDate = true;

    Formatter(String pattern) {
        if (pattern.contains("HH") || pattern.contains("mm") || pattern.contains("ss"))
            useLocalDate = false;
        formatter = DateTimeFormatter.ofPattern(pattern);
    }

    DateTimeFormatter getFormatter() {
        return formatter;
    }

    LocalDateTime parse(String dt) {
        if (useLocalDate)
            return LocalDate.parse(dt, formatter).atStartOfDay();
        return LocalDateTime.parse(dt, formatter);
    }

    LocalDate parseLocalDate(String dt) {
        return LocalDate.parse(dt, formatter);
    }
}