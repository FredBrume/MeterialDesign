package com.example.fredbrume.meterialdesign.Data.Local;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.fredbrume.meterialdesign.Fragments.ClassFragment;
import com.example.fredbrume.meterialdesign.Model.Assignment;
import com.example.fredbrume.meterialdesign.Model.Subject;

/**
 * Created by fredbrume on 2/21/18.
 */

public class SchoolUpdateService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String TAG = SchoolUpdateService.class.getSimpleName();
    public static final String ACTION_INSERT_CLASS = ".INSERT CLASS " + TAG;
    public static final String ACTION_INSERT_ASSIGNMENT = ".INSERT ASSIGNMENT " + TAG;
    public static final String ACTION_UPDATE_CLASS = ".UPDATE CLASS " + TAG;
    public static final String ACTION_UPDATE_ASSIGNMENT = ".UPDATE ASSIGNMENT " + TAG;
    public static final String ACTION_DELETE_CLASS = ".DELETE CLASS " + TAG;
    public static final String ACTION_DELETE_ASSIGNMENT = ".DELETE ASSIGNMENT" + TAG;
    public static final String EXTRA_VALUES = ".EXTRA " + TAG;
    public static final String RESPONSE_STRING = "myResponse";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";


    public SchoolUpdateService() {
        super(TAG);
    }

    public static void insertClass(Context context, ContentValues contentValues) {
        Intent intent = new Intent(context, SchoolUpdateService.class);
        intent.putExtra(EXTRA_VALUES, contentValues);
        intent.setAction(ACTION_INSERT_CLASS);
        context.startService(intent);

    }

    public static void insertAssignment(Context context, ContentValues contentValues) {
        Intent intent = new Intent(context, SchoolUpdateService.class);
        intent.putExtra(EXTRA_VALUES, contentValues);
        intent.setAction(ACTION_INSERT_ASSIGNMENT);
        context.startService(intent);
    }

    public static void updateClass(Context context, Uri uri, ContentValues contentValues) {
        Intent intent = new Intent(context, SchoolUpdateService.class);
        intent.putExtra(EXTRA_VALUES, contentValues);
        intent.setAction(ACTION_UPDATE_CLASS);
        intent.setData(uri);
        context.startService(intent);
    }

    public static void updateAssignment(Context context, Uri uri, ContentValues contentValues) {
        Intent intent = new Intent(context, SchoolUpdateService.class);
        intent.putExtra(EXTRA_VALUES, contentValues);
        intent.setAction(ACTION_UPDATE_ASSIGNMENT);
        intent.setData(uri);
        context.startService(intent);
    }

    public static void deleteClass(Context context, Uri uri) {
        Intent intent = new Intent(context, SchoolUpdateService.class);
        intent.setAction(ACTION_DELETE_CLASS);
        intent.setData(uri);
        context.startService(intent);
    }

    public static void deleteAssignment(Context context, Uri uri) {
        Intent intent = new Intent(context, SchoolUpdateService.class);
        intent.setAction(ACTION_DELETE_ASSIGNMENT);
        intent.setData(uri);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (ACTION_INSERT_CLASS.equals(intent.getAction())) {
            ContentValues contentValues = intent.getParcelableExtra(EXTRA_VALUES);
            performInsertClass(contentValues);

        } else if (ACTION_INSERT_ASSIGNMENT.equals(intent.getAction())) {
            ContentValues contentValues = intent.getParcelableExtra(EXTRA_VALUES);
            performInsertAssignment(contentValues);

        } else if (ACTION_UPDATE_CLASS.equals(intent.getAction())) {
            ContentValues contentValues = intent.getParcelableExtra(EXTRA_VALUES);
            Uri uri = intent.getData();
            performUpdateClass(contentValues, uri);

        } else if (ACTION_UPDATE_ASSIGNMENT.equals(intent.getAction())) {
            ContentValues contentValues = intent.getParcelableExtra(EXTRA_VALUES);
            Uri uri = intent.getData();
            performUpdateAssignment(contentValues, uri);
        } else if (ACTION_DELETE_CLASS.equals(intent.getAction())) {
            Uri uri = intent.getData();
            performDeleteClass(uri);

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(SchoolUpdateServicerHelper.ServiceBroadcastReceiver.PROCESS_RESPONSE);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(RESPONSE_MESSAGE, "Success");
            sendBroadcast(broadcastIntent);

        } else if (ACTION_DELETE_ASSIGNMENT.equals(intent.getAction())) {
            Uri uri = intent.getData();
            performDeleteAssignment(uri);
        }
    }


    private void performInsertClass(ContentValues contentValues) {
        if (getContentResolver().insert(SchoolAppContract.ClassEntry.CONTENT_URI, contentValues) != null) {

        } else {

        }
    }

    private void performInsertAssignment(ContentValues contentValues) {
        if (getContentResolver().insert(SchoolAppContract.AssignmentEntry.CONTENT_URI, contentValues) != null) {

        } else {

        }
    }

    private void performUpdateClass(ContentValues contentValues, Uri uri) {
        int count = getContentResolver().update(uri, contentValues, null, null);
    }

    private void performUpdateAssignment(ContentValues contentValues, Uri uri) {
        int count = getContentResolver().update(uri, contentValues, null, null);
    }

    private void performDeleteClass(Uri uri) {
        getContentResolver().delete(uri, null, null);
    }

    private void performDeleteAssignment(Uri uri) {
        getContentResolver().delete(uri, null, null);
    }

}
