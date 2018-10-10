package com.example.fredbrume.meterialdesign.Data.Local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by fredbrume on 10/11/17.
 */

public class SchoolAppContentProvider extends ContentProvider {

    private static final int CLASS = 100;
    private static final int CLASS_WITH_ID = 101;
    private static final int ASSIGNMENT = 200;
    private static final int ASSIGNMENT_WITH_ID = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    SchoolAppDbHelper schoolAppDbHelper;


    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(SchoolAppContract.AUTHORITY, SchoolAppContract.PATH_CLASS, CLASS);
        uriMatcher.addURI(SchoolAppContract.AUTHORITY, SchoolAppContract.PATH_CLASS + "/#", CLASS_WITH_ID);

        uriMatcher.addURI(SchoolAppContract.AUTHORITY, SchoolAppContract.PATH_ASSIGNMENT, ASSIGNMENT);
        uriMatcher.addURI(SchoolAppContract.AUTHORITY, SchoolAppContract.PATH_ASSIGNMENT + "/#", ASSIGNMENT_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        schoolAppDbHelper = new SchoolAppDbHelper(context);

        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase sqLiteDatabase = schoolAppDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        String date = uri.getLastPathSegment();

        Cursor cursor;

        switch (match) {

            case CLASS:

                String classQuery = "SELECT * FROM " + SchoolAppContract.ClassEntry.TABLE_NAME + ";";
                cursor = sqLiteDatabase.rawQuery(classQuery, null);

                break;

            case ASSIGNMENT:

                String assignmentQuery = "SELECT * FROM " + SchoolAppContract.AssignmentEntry.TABLE_NAME + ";";
                cursor = sqLiteDatabase.rawQuery(assignmentQuery, null);

                break;


            case ASSIGNMENT_WITH_ID:

                String ingredientQuery = "SELECT * FROM " + SchoolAppContract.AssignmentEntry.TABLE_NAME
                        + " WHERE " + SchoolAppContract.AssignmentEntry.COLUMN_DATE + " = " + date + ";";

                cursor = sqLiteDatabase.rawQuery(ingredientQuery, null);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Uri returnUri;
        final SQLiteDatabase sqLiteDatabase = schoolAppDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match) {

            case CLASS:

                long classCount = sqLiteDatabase.insert(SchoolAppContract.ClassEntry.TABLE_NAME, null, values);

                if (classCount > 0) {
                    returnUri = ContentUris.withAppendedId(SchoolAppContract.ClassEntry.CONTENT_URI, classCount);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            case ASSIGNMENT:
                long assignmentCount = sqLiteDatabase.insert(SchoolAppContract.AssignmentEntry.TABLE_NAME, null, values);

                if (assignmentCount > 0) {
                    returnUri = ContentUris.withAppendedId(SchoolAppContract.ClassEntry.CONTENT_URI, assignmentCount);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = schoolAppDbHelper.getWritableDatabase();

        int deletedRow=0;
        String id = uri.getLastPathSegment();
        int match = sUriMatcher.match(uri);

        switch (match) {

            case CLASS_WITH_ID:

                deletedRow = db.delete(SchoolAppContract.ClassEntry.TABLE_NAME,
                        SchoolAppContract.ClassEntry._ID + "=?", new String[]{String.valueOf(id)});

            case ASSIGNMENT_WITH_ID:
                deletedRow = db.delete(SchoolAppContract.AssignmentEntry.TABLE_NAME,
                        SchoolAppContract.AssignmentEntry._ID + "=?", new String[]{String.valueOf(id)});

                break;

            default:
                throw new android.database.SQLException("Failed to delete row into " + uri);
        }

        if (deletedRow != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }


        return deletedRow;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
