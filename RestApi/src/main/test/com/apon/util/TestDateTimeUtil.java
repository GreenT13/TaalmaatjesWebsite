package com.apon.util;

import org.junit.Test;

import java.text.ParseException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Test class for DateTimeUtil. There is no need to extend BaseTest, since DateTimeUtil only contains static functions.
 */
public class TestDateTimeUtil {

    @Test
    public void convertStringToDate() throws ParseException {
        // Some random date.
        assertEquals(DateTimeUtil.convertStringToDate("26-03-1993").toString(), "1993-03-26");
        // Test leap year.
        assertEquals(DateTimeUtil.convertStringToDate("29-02-2016").toString(), "2016-02-29");
        // Test date far back.
        assertEquals(DateTimeUtil.convertStringToDate("01-01-0001").toString(), "0001-01-01");
    }

    @Test
    public void isBeforeToday() throws ParseException {
        // Check for some arbitrary date in the past.
        Date date = DateTimeUtil.convertStringToDate("26-03-1993");
        assertEquals(DateTimeUtil.isBeforeToday(date), true);

        // Check for some arbitrary date in the future.
        date = DateTimeUtil.convertStringToDate("26-03-3000");
        assertEquals(DateTimeUtil.isBeforeToday(date), false);

        // Check for today (dangerous because this code is not tested or whatever but ok).
        date = DateTimeUtil.getCurrentDate();
        assertEquals(DateTimeUtil.isBeforeToday(date), false);

        // Check for yesterday (also dangerous).
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        date = new Date(cal.getTime().getTime()); // Yesterday
        assertEquals(DateTimeUtil.isBeforeToday(date), true);
    }

    @Test
    public void getCurrentDate() {
        // Getting current date can be done in several ways, so I just test a few that I found online.
        // I always assertEquals the toStrings of date, because we create different objects (not equal).
        // I could also use compare, but this is also fine I guess.

        Calendar cal = Calendar.getInstance();
        assertEquals(DateTimeUtil.getCurrentDate().toString(), new Date(cal.getTime().getTime()).toString());
        assertEquals(DateTimeUtil.getCurrentDate().toString(), new Date(new java.util.Date().getTime()).toString());
        assertEquals(DateTimeUtil.getCurrentDate().toString(), Date.valueOf(LocalDate.now()).toString());
    }

    @Test
    public void isBetween() throws ParseException {
        // We assume convertStringToDate() is tested and correct, since this is an easy way to create dates.
        Date d1 = DateTimeUtil.convertStringToDate("31-12-2017");
        Date d2 = DateTimeUtil.convertStringToDate("01-01-2018");
        Date d3 = DateTimeUtil.convertStringToDate("02-01-2018");

        // Happy flows
        assertEquals(DateTimeUtil.isBetween(d1, d2, d3), false);
        assertEquals(DateTimeUtil.isBetween(d2, d1, d3), true);
        assertEquals(DateTimeUtil.isBetween(d3, d1, d2), false);

        // Edge cases
        assertEquals(DateTimeUtil.isBetween(d2, d2, d3), true);
        assertEquals(DateTimeUtil.isBetween(d2, d1, d2), true);
        assertEquals(DateTimeUtil.isBetween(d2, d2, d2), true);
    }

    @Test
    public void isBetweenWithoutEndpoints() throws ParseException {
        // We assume convertStringToDate() is tested and correct, since this is an easy way to create dates.
        Date d1 = DateTimeUtil.convertStringToDate("31-12-2017");
        Date d2 = DateTimeUtil.convertStringToDate("01-01-2018");
        Date d3 = DateTimeUtil.convertStringToDate("02-01-2018");

        // Happy flows
        assertEquals(DateTimeUtil.isBetweenWithoutEndpoints(d1, d2, d3), false);
        assertEquals(DateTimeUtil.isBetweenWithoutEndpoints(d2, d1, d3), true);
        assertEquals(DateTimeUtil.isBetweenWithoutEndpoints(d3, d1, d2), false);

        // Edge cases
        assertEquals(DateTimeUtil.isBetweenWithoutEndpoints(d2, d2, d3), false);
        assertEquals(DateTimeUtil.isBetweenWithoutEndpoints(d2, d1, d2), false);
        assertEquals(DateTimeUtil.isBetweenWithoutEndpoints(d2, d2, d2), false);
    }

    @Test
    public void hasOverlap() throws ParseException {
        // We assume convertStringToDate() is tested and correct, since this is an easy way to create dates.
        Date d1 = DateTimeUtil.convertStringToDate("31-12-2017");
        Date d2 = DateTimeUtil.convertStringToDate("01-01-2018");
        Date d3 = DateTimeUtil.convertStringToDate("02-01-2018");
        Date d4 = DateTimeUtil.convertStringToDate("03-01-2018");

        // Happy flow
        assertEquals(DateTimeUtil.hasOverlap(d1, d3, d2, d4), true); // [1-3],[2-4]
        assertEquals(DateTimeUtil.hasOverlap(d1, d4, d2, d3), true); // [1-4],[2-3]
        assertEquals(DateTimeUtil.hasOverlap(d1, d2, d3, d4), false); // [1-2],[3-4]
        assertEquals(DateTimeUtil.hasOverlap(d1, d2, d1, d2), true); // [1-2],[1-2]

        // Edge cases
        assertEquals(DateTimeUtil.hasOverlap(d1, d2, d2, d3), true); // [1-2],[2-3]
        assertEquals(DateTimeUtil.hasOverlap(d1, d2, d2, d2), true); // [1-2],[2]
    }

    @Test
    public void isContained() throws ParseException {
        // We assume convertStringToDate() is tested and correct, since this is an easy way to create dates.
        Date d1 = DateTimeUtil.convertStringToDate("31-12-2017");
        Date d2 = DateTimeUtil.convertStringToDate("01-01-2018");
        Date d3 = DateTimeUtil.convertStringToDate("02-01-2018");
        Date d4 = DateTimeUtil.convertStringToDate("03-01-2018");

        // Happy flow
        assertEquals(DateTimeUtil.isContained(d1, d3, d2, d4), false); // [1-3],[2-4]
        assertEquals(DateTimeUtil.isContained(d1, d4, d2, d3), false); // [1-4],[2-3]
        assertEquals(DateTimeUtil.isContained(d1, d2, d3, d4), false); // [1-2],[3-4]
        assertEquals(DateTimeUtil.isContained(d1, d2, d1, d2), true); // [1-2],[1-2]

        // Edge cases
        assertEquals(DateTimeUtil.isContained(d1, d2, d2, d3), false); // [1-2],[2-3]
        assertEquals(DateTimeUtil.isContained(d1, d2, d2, d2), false); // [1-2],[2]
        assertEquals(DateTimeUtil.isContained(d2, d2, d1, d2), true); // [2],[1-2]
        assertEquals(DateTimeUtil.isContained(d2, d2, d1, d3), true); // [2],[1-3]
    }

    @Test
    public void nrOfDaysInBetween() throws ParseException {
        // We assume convertStringToDate() is tested and correct, since this is an easy way to create dates.
        Date d1 = DateTimeUtil.convertStringToDate("31-12-2017");
        Date d2 = DateTimeUtil.convertStringToDate("01-01-2018");
        Date d3 = DateTimeUtil.convertStringToDate("02-01-2018");

        // Happy flow
        assertEquals(DateTimeUtil.nrOfDaysInBetween(d1, d3), 2);
        assertEquals(DateTimeUtil.nrOfDaysInBetween(d1, d2), 1);
        assertEquals(DateTimeUtil.nrOfDaysInBetween(d1, d1), 0);
        assertEquals(DateTimeUtil.nrOfDaysInBetween(d2, d1), -1);
        assertEquals(DateTimeUtil.nrOfDaysInBetween(d3, d1), -2);
    }
}
