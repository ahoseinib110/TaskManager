package com.example.taskmanager.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    public static Date getRandomDate(int startYear, int endYear) {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(startYear, endYear);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        int hour = randBetween(0,gc.getActualMaximum(gc.HOUR_OF_DAY));
        int min = randBetween(0,gc.getActualMaximum(gc.MINUTE));
        gc.set(gc.YEAR, year);
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        gc.set(gc.HOUR_OF_DAY, hour);
        gc.set(gc.MINUTE, min);
        gc.set(gc.SECOND, 0);
        return gc.getTime();
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static String getDateWithoutTime(Date date)  {
        String strDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        strDate =year+"/"+month+"/"+day;
        return  strDate;
    }

    public static String getTimeWithoutDate(Date date)  {
        String strTime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        strTime =hour+":"+min+":"+sec;
        return  strTime;
    }
}
