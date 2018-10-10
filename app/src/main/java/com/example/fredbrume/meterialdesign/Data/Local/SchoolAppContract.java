package com.example.fredbrume.meterialdesign.Data.Local;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by fredbrume on 10/11/17.
 */

public class SchoolAppContract {

    public static final String AUTHORITY = "com.example.fredbrume.meterialdesign";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_ASSIGNMENT = "assignment"; //the folder containing the database
    public static final String PATH_CLASS = "subject";


    public static final class AssignmentEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ASSIGNMENT).build();
        public static final String TABLE_NAME = "assignment";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_REMINDER = "reminder";

    }

    public static final class ClassEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLASS).build();
        public static final String TABLE_NAME = "Class";
        public static final String COLUMN_TEACHER = "teacher";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_PERIOD = "period";
        public static final String COLUMN_DAYS = "days";
        public static final String COLUMN_TIME = "time";

    }
}
