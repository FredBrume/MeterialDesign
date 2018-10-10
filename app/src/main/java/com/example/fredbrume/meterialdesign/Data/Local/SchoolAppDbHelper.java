package com.example.fredbrume.meterialdesign.Data.Local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fredbrume on 10/11/17.
 */

public class SchoolAppDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "schoolAppDb.db";
    private static final int DATABASE_VERSION = 3;

    public SchoolAppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE_CLASS = "CREATE TABLE " + SchoolAppContract.ClassEntry.TABLE_NAME + "(" +

                SchoolAppContract.ClassEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SchoolAppContract.ClassEntry.COLUMN_TEACHER + " TEXT NOT NULL, " +
                SchoolAppContract.ClassEntry.COLUMN_SUBJECT + " TEXT NOT NULL, " +
                SchoolAppContract.ClassEntry.COLUMN_PERIOD + " TEXT NOT NULL, " +
                SchoolAppContract.ClassEntry.COLUMN_DAYS + " TEXT NOT NULL, " +
                SchoolAppContract.ClassEntry.COLUMN_TIME + " TEXT NOT NULL ); ";


        final String CREATE_TABLE_ASSIGNMENT = "CREATE TABLE " + SchoolAppContract.AssignmentEntry.TABLE_NAME + "(" +

                SchoolAppContract.AssignmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SchoolAppContract.AssignmentEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                SchoolAppContract.AssignmentEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                SchoolAppContract.AssignmentEntry.COLUMN_DATE + " TEXT NOT NULL," +
                SchoolAppContract.AssignmentEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                SchoolAppContract.AssignmentEntry.COLUMN_SUBJECT + " TEXT NOT NULL," +
                SchoolAppContract.AssignmentEntry.COLUMN_REMINDER + " TEXT NOT NULL ); ";


        db.execSQL(CREATE_TABLE_CLASS);
        db.execSQL(CREATE_TABLE_ASSIGNMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS " + SchoolAppContract.ClassEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SchoolAppContract.AssignmentEntry.TABLE_NAME);

            onCreate(db);
        }
    }
}
