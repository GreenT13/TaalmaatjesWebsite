package com.apon.util;

import java.sql.Date;
import java.time.LocalDate;

public class DateTimeUtil {

    public static boolean isBeforeToday(Date date) {
        return date.compareTo(getCurrentDate()) < 0;
    }

    public static Date getCurrentDate() {
        return Date.valueOf(LocalDate.now());
    }
}
