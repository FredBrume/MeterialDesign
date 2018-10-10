package com.example.fredbrume.meterialdesign.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fredbrume.meterialdesign.Data.Local.SchoolUpdateServicerHelper;
import com.example.fredbrume.meterialdesign.Model.Assignment;
import com.example.fredbrume.meterialdesign.R;
import com.example.fredbrume.meterialdesign.util.DateUtils;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Date;
import java.util.List;

/**
 * Created by fredbrume on 12/24/17.
 */

public class CaldroidCalendarFragment extends BaseFragment {

    private static final int LOADER_ID = 1;
    private CaldroidFragment mCaldroidFragment;
    private LoaderManager loaderManager;

    CalendarEventOnClickHandler calendarEventOnClickHandler;

    public interface CalendarEventOnClickHandler {

        void getEventOnClick(String date);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            calendarEventOnClickHandler = (CalendarEventOnClickHandler) context;

        } catch (ClassCastException exception) {

            throw new ClassCastException(context.toString()
                    + " must implement CalendarEventOnClickHandler");
        }
    }


    public static CaldroidCalendarFragment getInstance() {

        CaldroidCalendarFragment fragment = new CaldroidCalendarFragment();

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mCaldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        args.putInt(com.roomorama.caldroid.CaldroidFragment.START_DAY_OF_WEEK, com.roomorama.caldroid.CaldroidFragment.MONDAY);
        args.putBoolean(com.roomorama.caldroid.CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
        mCaldroidFragment.setArguments(args);

        getFragmentManager().beginTransaction().add(R.id.cal_container, mCaldroidFragment).commit();
        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, new FetchEventTask());
        addOnSelectedEventListenerToCal();


        return view;
    }

    private void addOnSelectedEventListenerToCal() {
        CaldroidListener caldroidListener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                calendarEventOnClickHandler.getEventOnClick(DateUtils.formatCalendarDate(date));
            }
        };

        mCaldroidFragment.setCaldroidListener(caldroidListener);
    }

    private class FetchEventTask implements LoaderManager.LoaderCallbacks<List<Assignment>> {

        @SuppressLint("StaticFieldLeak")
        @Override
        public Loader<List<Assignment>> onCreateLoader(int id, Bundle args) {

            return new AsyncTaskLoader<List<Assignment>>(getContext()) {

                List<Assignment> assignmentList = null;

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();

                    if (assignmentList != null) {
                        deliverResult(assignmentList);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public void deliverResult(List<Assignment> data) {
                    super.deliverResult(data);
                    assignmentList = data;
                }

                @Override
                public List<Assignment> loadInBackground() {

                    try {
                        List<Assignment> assignments = SchoolUpdateServicerHelper.assignmentFromDB(getContext());

                        return assignments;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Assignment>> loader, List<Assignment> data) {

            if (data != null) {
                for (Assignment assignment : data) {

                    mCaldroidFragment.setTextColorForDate(R.color.caldroid_light_red, DateUtils.formatCalendarDate(assignment.getDate()));
                    mCaldroidFragment.refreshView();
                }
            }

        }

        @Override
        public void onLoaderReset(Loader<List<Assignment>> loader) {
            mCaldroidFragment.refreshView();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.caldroid_fragment;
    }

}
