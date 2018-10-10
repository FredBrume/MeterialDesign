package com.example.fredbrume.meterialdesign.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fredbrume on 1/31/18.
 */

public class DateUtils {

    public static Date formatCalendarDate(String date) {

        Date formattedDate = null;
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            formattedDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static String formatCalendarDate(Date date) {

        String formattedDate = null;
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }
}
