package com.example.fredbrume.meterialdesign.Data.Local;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import com.example.fredbrume.meterialdesign.Model.Assignment;
import com.example.fredbrume.meterialdesign.Model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * Created by fredbrume on 1/12/18.
 */

public class SchoolUpdateServicerHelper {

    public static void insertClassDetails(Context context, Subject subject) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(SchoolAppContract.ClassEntry.COLUMN_TEACHER, subject.getTeacher());
        contentValues.put(SchoolAppContract.ClassEntry.COLUMN_SUBJECT, subject.getSubject());
        contentValues.put(SchoolAppContract.ClassEntry.COLUMN_PERIOD, subject.getPeriod());
        contentValues.put(SchoolAppContract.ClassEntry.COLUMN_DAYS, subject.getDays());
        contentValues.put(SchoolAppContract.ClassEntry.COLUMN_TIME, subject.getTime());

        SchoolUpdateService.insertClass(context, contentValues);
    }

    public static void insertAssignmentDetails(Context context, Assignment assignment, boolean exist) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(SchoolAppContract.AssignmentEntry.COLUMN_TYPE, assignment.getType());
        contentValues.put(SchoolAppContract.AssignmentEntry.COLUMN_TITLE, assignment.getAssignmentName());
        contentValues.put(SchoolAppContract.AssignmentEntry.COLUMN_DATE, assignment.getDate());
        contentValues.put(SchoolAppContract.AssignmentEntry.COLUMN_DESCRIPTION, assignment.getNote());
        contentValues.put(SchoolAppContract.AssignmentEntry.COLUMN_SUBJECT, assignment.getSubject());
        contentValues.put(SchoolAppContract.AssignmentEntry.COLUMN_REMINDER, assignment.getReminder());

        if (exist) {
            deleteAssignmentFromDB(context, assignment);
        }
        SchoolUpdateService.insertAssignment(context, contentValues);
    }

    public static List<Subject> classFromDB(Context context) {

        Uri contentUri = SchoolAppContract.ClassEntry.CONTENT_URI;

        List<Subject> subjectList = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);

        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex(SchoolAppContract.ClassEntry._ID));
                String teacher = cursor.getString(cursor.getColumnIndex(SchoolAppContract.ClassEntry.COLUMN_TEACHER));
                String subjectTitle = cursor.getString(cursor.getColumnIndex(SchoolAppContract.ClassEntry.COLUMN_SUBJECT));
                String period = cursor.getString(cursor.getColumnIndex(SchoolAppContract.ClassEntry.COLUMN_PERIOD));
                String days = cursor.getString(cursor.getColumnIndex(SchoolAppContract.ClassEntry.COLUMN_DAYS));
                String time = cursor.getString(cursor.getColumnIndex(SchoolAppContract.ClassEntry.COLUMN_TIME));

                Subject subject = new Subject(id, teacher, subjectTitle, period, days, time);

                subjectList.add(subject);

            } while (cursor.moveToNext());
        }

        return subjectList;
    }

    public static List<Assignment> assignmentFromDB(Context context) {

        Uri contentUri = SchoolAppContract.AssignmentEntry.CONTENT_URI;

        List<Assignment> assignmentList = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);

        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex(SchoolAppContract.AssignmentEntry._ID));
                String type = cursor.getString(cursor.getColumnIndex(SchoolAppContract.AssignmentEntry.COLUMN_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(SchoolAppContract.AssignmentEntry.COLUMN_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(SchoolAppContract.AssignmentEntry.COLUMN_DATE));
                String description = cursor.getString(cursor.getColumnIndex(SchoolAppContract.AssignmentEntry.COLUMN_DESCRIPTION));
                String subject = cursor.getString(cursor.getColumnIndex(SchoolAppContract.AssignmentEntry.COLUMN_SUBJECT));
                String reminder = cursor.getString(cursor.getColumnIndex(SchoolAppContract.AssignmentEntry.COLUMN_REMINDER));

                Assignment assignment = new Assignment(id, type, title, description, subject, date, reminder);

                assignmentList.add(assignment);

            } while (cursor.moveToNext());
        }


        return assignmentList;
    }

    public static List<Assignment> assignmentFromDBWithDate(Context context, String date) {

        List<Assignment> tempAssignments = assignmentFromDB(context);
        List<Assignment> assignments = new ArrayList<>();

        for (int i = 0; i < tempAssignments.size(); ++i) {
            if (tempAssignments != null && tempAssignments.get(i).getDate().equals(date)) {
                assignments.add(tempAssignments.get(i));

            }
        }

        return assignments;
    }


    public static String[] classSubjectFromDB(Context context) {

        if (classFromDB(context) != null) {

            List<Subject> subjectList = classFromDB(context);
            String[] classSubject = new String[subjectList.size()];

            for (int position = 0; position < subjectList.size(); ++position) {

                classSubject[position] = subjectList.get(position).getSubject();
            }

            return classSubject;
        }

        return null;
    }


    public static boolean deleteClassFromDB(Context context, Subject subject) {

        try {

            SchoolUpdateService.deleteClass(context, ContentUris.withAppendedId(
                    SchoolAppContract.ClassEntry.CONTENT_URI, Long.parseLong(subject.getId())));

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteAssignmentFromDB(Context context, Assignment assignment) {

        try {
            SchoolUpdateService.deleteAssignment(context,ContentUris.withAppendedId(
                    SchoolAppContract.ClassEntry.CONTENT_URI,Long.parseLong(assignment.getId())));

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface OnDataListRefreshListener {
        void onRefresh(String status);
    }

    public static class ServiceBroadcastReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "ServiceBroadcastReceiver.PROCESS_RESPONSE";
        private SchoolUpdateServicerHelper.OnDataListRefreshListener mDataUpdateListener = null;

        public ServiceBroadcastReceiver(SchoolUpdateServicerHelper.OnDataListRefreshListener dataUpdateListener) {
            mDataUpdateListener = dataUpdateListener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            if (null != mDataUpdateListener) {
                mDataUpdateListener.onRefresh(intent.getStringExtra(SchoolUpdateService.RESPONSE_MESSAGE));
            }
        }
    }
}
