package com.example.fredbrume.meterialdesign.Fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fredbrume.meterialdesign.Adapters.ClassAdapter;
import com.example.fredbrume.meterialdesign.Data.Local.SchoolUpdateService;
import com.example.fredbrume.meterialdesign.Data.Local.SchoolUpdateServicerHelper;
import com.example.fredbrume.meterialdesign.Model.Subject;
import com.example.fredbrume.meterialdesign.R;

import java.util.List;

import butterknife.BindView;

/**
 * Created by CeeDe on 1/27/2017.
 */
public class ClassFragment extends BaseFragment implements ClassAdapter.ClassOnClickHandler, SchoolUpdateServicerHelper.OnDataListRefreshListener {

    private static final int LOADER_ID = 1;
    private ClassAdapter classAdapter;

    @BindView(R.id.myClassesList)
    RecyclerView classList;

    @BindView(R.id.noDataView)
    CardView noDataView;

    private LoaderManager loaderManager;

    private SchoolUpdateServicerHelper.ServiceBroadcastReceiver srviceBroadcastReceiver;

    public ClassFragment() {

    }

    public static ClassFragment getInstance() {

        ClassFragment fragment = new ClassFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewPropertyHandler.setToolBar(R.menu.my_classes);
        viewPropertyHandler.setBackgroundMainLogo(0.4f);

        classAdapter = new ClassAdapter(this);
        classList.setAdapter(classAdapter);

        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, new FetchClassTask());

        IntentFilter intentFilter = new IntentFilter(SchoolUpdateServicerHelper.ServiceBroadcastReceiver.PROCESS_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        srviceBroadcastReceiver = new SchoolUpdateServicerHelper.ServiceBroadcastReceiver(this);
        getActivity().registerReceiver(srviceBroadcastReceiver, intentFilter);

        refreshClassList();


        return view;
    }

    public void refreshClassList() {

        loaderManager = getLoaderManager();
        loaderManager.restartLoader(LOADER_ID, null, new FetchClassTask());

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClassItemClick(Subject subject) {

        viewPropertyHandler.openFragment(AddOrEditClassFragment.getInstance(subject), true, null);
    }

    @Override
    public void onClassItemDeleteOnLongClick(Subject subject) {

        viewPropertyHandler.openDialog(subject);
    }

    @Override
    public void onRefresh(String status) {

        if (getActivity() != null) {
            refreshClassList();

        }
    }

    private class FetchClassTask implements LoaderManager.LoaderCallbacks<List<Subject>> {

        @SuppressLint("StaticFieldLeak")
        @Override
        public Loader<List<Subject>> onCreateLoader(int id, Bundle args) {

            return new AsyncTaskLoader<List<Subject>>(getActivity()) {

                List<Subject> subjectList = null;

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();

                    if (subjectList != null) {
                        deliverResult(subjectList);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public void deliverResult(List<Subject> data) {
                    super.deliverResult(data);
                    subjectList = data;
                }

                @Override
                public List<Subject> loadInBackground() {

                    try {
                        List<Subject> subjectList1 = SchoolUpdateServicerHelper.classFromDB(getContext());

                        return subjectList1;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Subject>> loader, List<Subject> data) {

            if (data.size() != 0) noDataView.setVisibility(View.GONE);
            classAdapter.setLayoutData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Subject>> loader) {
            classAdapter.setLayoutData(null);
        }
    }

    public void removeClassFromList(Subject subject) {
        SchoolUpdateServicerHelper.deleteClassFromDB(getContext(), subject);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(srviceBroadcastReceiver);

    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_classes;
    }
}


