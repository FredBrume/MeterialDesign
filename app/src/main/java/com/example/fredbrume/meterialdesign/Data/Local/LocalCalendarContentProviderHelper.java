package com.example.fredbrume.meterialdesign.Data.Local;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;

import com.example.fredbrume.meterialdesign.Model.Assignment;

import org.joda.time.Minutes;

import java.util.TimeZone;

/**
 * Created by fredbrume on 2/8/18.
 */

public class LocalCalendarContentProviderHelper {

    public static void addReminder(Activity activity, Assignment assignment, org.joda.time.DateTime dateTime) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CALENDAR}, 0);
        }
        //create content that will go into the calendar
        ContentValues calEvent = new ContentValues();
        //create ability to insert into the calendar
        ContentResolver cr = activity.getContentResolver();
        //where/when/id_for_insert/start_time/end_time/time_zone
        //need address/description

        calEvent.put(CalendarContract.Events.CALENDAR_ID, 1); // XXX pick)
        calEvent.put(CalendarContract.Events.DTSTART, dateTime.getMillis());
        calEvent.put(CalendarContract.Events.DTEND, dateTime.getMillis());
        calEvent.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        calEvent.put(CalendarContract.Events.TITLE, assignment.getAssignmentName());
        calEvent.put(CalendarContract.Events.DESCRIPTION, assignment.getNote());

        Uri EVENTS_URI = Uri.parse(CalendarContract.Events.CONTENT_URI.toString());
        long eventID = Long.parseLong(cr.insert(EVENTS_URI, calEvent).getLastPathSegment());

        ContentValues reminders = new ContentValues();
        reminders.put(CalendarContract.Reminders.EVENT_ID, Long.valueOf(eventID));
        reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        reminders.put(CalendarContract.Reminders.MINUTES, Integer.valueOf(15));

        activity.getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, reminders);
        //get id for reminders

    }


}
