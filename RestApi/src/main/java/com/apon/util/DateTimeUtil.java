package com.apon.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@SuppressWarnings({"RedundantIfStatement", "unused"})
public class DateTimeUtil {

    /**
     * Convert String to Date.
     * @param date String with format dd-MM-yyyy
     * @return java.sql.Date
     */
    public static Date convertStringToDate(String date) throws ParseException {
        return new Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());
    }

    /**
     * Helper function that converts Date to LocalDate (which is used to compare dates).
     * @param date Date to convert.
     * @return LocalDate
     */
    private static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }

        return date.toLocalDate();
    }

    /**
     * Check whether given date is before today.
     * @param date Given date to check.
     * @return If date < currentDate, then return {@code true}. Else return {@code false}
     */
    public static boolean isBeforeToday(Date date) {
        return isBeforeToday(toLocalDate(date));
    }
    private static boolean isBeforeToday(LocalDate date) {
        return date.compareTo(toLocalDate(getCurrentDate())) < 0;
    }

    /**
     * Get current date.
     * @return java.sql.Date
     */
    public static Date getCurrentDate() {
        return Date.valueOf(LocalDate.now());
    }

    /**
     * Determine whether date d lies inside the range [r1,r2].
     * @param d Date
     * @param r1 Start date of the range.
     * @param r2 End date of the range.
     * @return boolean
     */
    public static boolean isBetween(Date d, Date r1, Date r2) {
        return isBetween(toLocalDate(d), toLocalDate(r1), toLocalDate(r2));
    }
    private static boolean isBetween(LocalDate d, LocalDate r1, LocalDate r2) {
        if (d == null) {
            return (r2 == null);
        }

        if (r1 != null && r1.compareTo(d) > 0) {
            return false;
        }

        if (r2 != null && r2.compareTo(d) < 0) {
            return false;
        }

        return true;
    }

    /**
     * Determine whether the date lies inside the range (r1, r2).
     * @param d Date
     * @param r1 Start date of the range.
     * @param r2 End date of the range.
     * @return boolean
     */
    public static boolean isBetweenWithoutEndpoints(Date d, Date r1, Date r2) {
        return isBetweenWithoutEndpoints(toLocalDate(d), toLocalDate(r1), toLocalDate(r2));
    }
    private static boolean isBetweenWithoutEndpoints(LocalDate d, LocalDate r1, LocalDate r2) {
        if (d == null) {
            return (r2 == null);
        }

        if (r1 != null && r1.compareTo(d) >= 0) {
            return false;
        }

        if (r2 != null && r2.compareTo(d) <= 0) {
            return false;
        }

        return true;
    }

    /**
     * Return whether the days [d1, d2] has at least one day overlap with [e1, e2].
     * @param d1 Start range 1.
     * @param d2 End range 1.
     * @param e1 Start range 1.
     * @param e2 End range 1.
     * @return boolean
     */
    public static boolean hasOverlap(Date d1, Date d2, Date e1, Date e2) {
        return hasOverlap(toLocalDate(d1), toLocalDate(d2), toLocalDate(e1), toLocalDate(e2));
    }
    private static boolean hasOverlap(LocalDate d1, LocalDate d2, LocalDate e1, LocalDate e2) {
        if (isBetween(e1, d1, d2)) {
            return true;
        }

        if (isBetween(e2, d1, d2)) {
            return true;
        }

        if (isBetween(d1, e1, e2)) {
            return true;
        }

        if (isBetween(d2, e1, e2)) {
            return true;
        }

        return false;
    }

    /**
     * Determine whether holds [d1, d2] \subset [e1, e2]. This is equivalent to
     * d1 \in [e1, e2] and d2 \in [e1,e2].
     * @param d1 Start range 1.
     * @param d2 End range 1.
     * @param e1 Start range 1.
     * @param e2 End range 1.
     * @return boolean
     */
    public static boolean isContained(Date d1, Date d2, Date e1, Date e2) {
        return isBetween(d1, e1, e2) && isBetween(d2, e1, e2);
    }

    /**
     * Count the number of days between two dates.
     * @param d1 First date
     * @param d2 Second date
     * @return long
     */
    public static long nrOfDaysInBetween(Date d1, Date d2) {
        return nrOfDaysInBetween(toLocalDate(d1), toLocalDate(d2));
    }
    private static long nrOfDaysInBetween(LocalDate d1, LocalDate d2) {
        if (d2 == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(d1, d2);
    }


}
