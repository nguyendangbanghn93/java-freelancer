package com.example.asmAuth.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static Date addDaysToCurrentTime(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

}
