package com.example.fredbrume.meterialdesign.util;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.example.fredbrume.meterialdesign.Data.Local.SchoolUpdateServicerHelper;
import com.example.fredbrume.meterialdesign.Fragments.AssignmentFragment;
import com.example.fredbrume.meterialdesign.Model.Assignment;

import java.util.List;

/**
 * Created by fredbrume on 2/1/18.
 */

public class AssignmenBackgroundLoader implements LoaderManager.LoaderCallbacks<List<Assignment>> {

    public AssignmentTaskHandler assignmentTaskHandler;
    private Bundle bundle;
    private Context context;
    private LoaderManager mLoaderManager;

    public AssignmenBackgroundLoader(Context context, LoaderManager loadermanger, Bundle bundle, AssignmentTaskHandler assignmentTaskHandler) {

        this.assignmentTaskHandler = assignmentTaskHandler ;
        this.mLoaderManager = loadermanger;
        this.bundle = bundle;
        this.context = context;

        Loader<String> loader = mLoaderManager.getLoader((Integer) bundle.get(AssignmentFragment.ASSIGNMENT_LOADER_ID));

        if (loader == null) {
            mLoaderManager.initLoader((Integer) bundle.get(AssignmentFragment.ASSIGNMENT_LOADER_ID), bundle, this);
        } else {
            mLoaderManager.restartLoader((Integer) bundle.get(AssignmentFragment.ASSIGNMENT_LOADER_ID), bundle, this);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<List<Assignment>> (context) {

            List<Assignment> assignment = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                assignmentTaskHandler.preExcecuteAssignmentTask();

                if (args == null) {
                    return;
                }

                if (assignment != null) {
                    deliverResult(assignment);
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(List<Assignment> data) {
                super.deliverResult(data);
                assignment =data;
            }


            @Override
            public List<Assignment> loadInBackground() {

                List<Assignment> assignmentList = SchoolUpdateServicerHelper.assignmentFromDBWithDate(
                        context,args.getString(AssignmentFragment.ASSIGNMENT_DATE_BUNDLE));

                return assignmentList;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Assignment>> loader, List<Assignment> data) {

        assignmentTaskHandler.postExecuteAssignmentTask(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public interface AssignmentTaskHandler {

        void postExecuteAssignmentTask(List<Assignment> poster);

        void preExcecuteAssignmentTask();
    }
}
