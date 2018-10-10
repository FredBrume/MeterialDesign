package com.example.fredbrume.meterialdesign.Fragments;

import android.annotation.SuppressLint;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.fredbrume.meterialdesign.Activity.ViewPropertyHandler;
import com.example.fredbrume.meterialdesign.Adapters.AssignmentAdapter;
import com.example.fredbrume.meterialdesign.Model.Assignment;
import com.example.fredbrume.meterialdesign.R;
import com.example.fredbrume.meterialdesign.util.AssignmenBackgroundLoader;

import java.util.List;

import butterknife.BindView;

/**
 * Created by CeeDe on 1/27/2017.
 */
public class AssignmentFragment extends BaseFragment implements AssignmentAdapter.AssignmentOnClickHandler{

    public static final String ASSIGNMENT_DATE_BUNDLE ="assignment_date" ;
    public static final String ASSIGNMENT_LOADER_ID ="loader_Id";
    public static final int ASSIGNMENT_LOADER_VALUE =1 ;
    private LoaderManager loaderManager;
    private AssignmentAdapter assignmentAdapter;

    @BindView(R.id.assignmentList)
    RecyclerView assignmentList;

    GetAssignmentOnClickHandler getAssignmentOnClickHandler;

    public AssignmentFragment() {

    }

    public interface GetAssignmentOnClickHandler {

        void getAssignmentOnClick(Assignment assignment);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            getAssignmentOnClickHandler = (GetAssignmentOnClickHandler) context;

        } catch (ClassCastException exception) {

            throw new ClassCastException(context.toString()
                    + " must implement GetAssignmentOnClickHandler");
        }
    }

    public static AssignmentFragment getInstance() {
        AssignmentFragment fragment = new AssignmentFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        openChildFragment(CaldroidCalendarFragment.getInstance(), R.id.caldroid_container, null);

        viewPropertyHandler.setToolBar(R.menu.menu_assignment);
        viewPropertyHandler.setBackgroundMainLogo(0.4f);

        assignmentAdapter = new AssignmentAdapter(this);
        assignmentList.setAdapter(assignmentAdapter);

        return view;
    }

    public void displayAssignments(final String assignmentDate)
    {
        Bundle assignmentDateBundle = new Bundle();

        assignmentDateBundle.putString(ASSIGNMENT_DATE_BUNDLE, assignmentDate);
        assignmentDateBundle.putInt(ASSIGNMENT_LOADER_ID, ASSIGNMENT_LOADER_VALUE);

        loaderManager = getActivity().getLoaderManager();
        new AssignmenBackgroundLoader(getContext(), loaderManager, assignmentDateBundle, new AssignmenBackgroundLoader.AssignmentTaskHandler() {
            @Override
            public void postExecuteAssignmentTask(List<Assignment> assignments) {

                System.out.println("Calendar List: " + assignments);
                assignmentAdapter.setLayoutData(assignments);

            }

            @Override
            public void preExcecuteAssignmentTask() {

            }
        });

    }

    private void openChildFragment(Fragment fragment, int fragmentLayout, String stackTag) {
        if (fragment != null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(fragmentLayout, fragment, stackTag).commit();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_assignment;
    }

    @Override
    public void onAssignmentItemClick(Assignment assignment) {

        getAssignmentOnClickHandler.getAssignmentOnClick(assignment);
    }
}
