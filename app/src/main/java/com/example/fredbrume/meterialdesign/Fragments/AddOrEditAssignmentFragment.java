package com.example.fredbrume.meterialdesign.Fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.Data.Local.LocalCalendarContentProviderHelper;
import com.example.fredbrume.meterialdesign.Data.Local.SchoolUpdateServicerHelper;
import com.example.fredbrume.meterialdesign.Model.Assignment;
import com.example.fredbrume.meterialdesign.R;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import org.joda.time.DateTime;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by CeeDe on 1/27/2017.
 */
public class AddOrEditAssignmentFragment extends BaseFragment {

    @BindView(R.id.selectClass)
    TextView selectClass;

    @BindView(R.id.assignmentDate)
    DatePicker datePicker;

    @BindView(R.id.assignment)
    EditText assignmenTitle;

    @BindView(R.id.notes)
    EditText assignmentDiscription;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.switchReminder)
    SwitchCompat switchReminder;

    private Assignment assignment;

    private SwitchDateTimeDialogFragment dateTimePickerDialog;
    private Assignment assign;
    private String id;


    public AddOrEditAssignmentFragment() {

    }

    public static AddOrEditAssignmentFragment getInstance(Assignment assignment) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("assignment", assignment);

        AddOrEditAssignmentFragment fragment = new AddOrEditAssignmentFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public static AddOrEditAssignmentFragment getInstance() {

        AddOrEditAssignmentFragment fragment = new AddOrEditAssignmentFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupTypeSpinner();
        setToolbar();

        bindAssignmentToViews(getArguments());

        return view;
    }

    @OnClick(R.id.selectClass)
    public void selectClassClick() {

        viewPropertyHandler.openDialog(SchoolUpdateServicerHelper.classSubjectFromDB(getContext()), getTag());

    }

    public void setToolbar() {

        viewPropertyHandler.setToolBar(R.menu.add_or_edit_assignment);
        viewPropertyHandler.setBackgroundMainLogo(0.7f);

        if (getArguments() != null) {
            viewPropertyHandler.setToolbarItemVisibility(R.id.update, true);
        }
    }

    public void classViewSelected(String subject) {
        selectClass.setText(subject);
    }

    private void setupTypeSpinner() {

        Resources res = getActivity().getResources();
        String[] spinner_items = res.getStringArray(R.array.spinner_items);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, spinner_items);

        spinner.setAdapter(spinnerAdapter);
    }

    private void addReminder(Assignment assignment, org.joda.time.DateTime dateTime) {

        LocalCalendarContentProviderHelper.addReminder(getActivity(), assignment, dateTime);

    }

    public void shareFile() {

        this.assignment = getAssignmentFromViews();

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, assignment.getSubject());
        intent.putExtra(Intent.EXTRA_TEXT, assignment.getNote());
        intent.setType("text/plain");

        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_using)));
    }

    public void doneMenuItemSelected() {

        this.assignment = getAssignmentFromViews();

        if (switchReminder.isChecked()) {
            dateTimePickerDialog = SwitchDateTimeDialogFragment.newInstance(
                    "Set Reminder",
                    "OK",
                    "Cancel");

            dateTimePickerDialog.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
                @Override
                public void onPositiveButtonClick(Date date) {

                    addReminder(assignment, new DateTime(date));
                    saveAssignmentAndGoBack(assignment,  id != null? true : false);
                }

                @Override
                public void onNegativeButtonClick(Date date) {
                }

                @Override
                public void onNeutralButtonClick(Date date) {
                }
            });

            dateTimePickerDialog.startAtCalendarView();
            dateTimePickerDialog.show(getActivity().getSupportFragmentManager(), "dialog_time");

            return;
        }
        saveAssignmentAndGoBack(assignment, id != null? true : false);
    }

    private void saveAssignmentAndGoBack(Assignment assignment, boolean exist) {

        SchoolUpdateServicerHelper.insertAssignmentDetails(getContext(), assignment, exist);
        getActivity().getSupportFragmentManager().popBackStack();

    }

    public void bindAssignmentToViews(Bundle bundle) {

        if (bundle != null) {

            assign = bundle.getParcelable("assignment");
            int typeSelection = 0;
            id = assign.getId();

            switch (assign.getType()) {
                case "Homework":
                    typeSelection = 0;
                    break;
                case "Project":
                    typeSelection = 1;
                    break;
                case "Test":
                    typeSelection = 2;
                    break;
                case "Exam":
                    typeSelection = 3;
                    break;
                case "Other":
                    typeSelection = 4;
                    break;
            }
            spinner.setSelection(typeSelection);
            assignmenTitle.setText(assign.getAssignmentName());
            assignmentDiscription.setText(assign.getNote());
            selectClass.setText(assign.getSubject());

        }
    }

    private Assignment getAssignmentFromViews() {

        Assignment assignment = this.assignment != null ? this.assignment : new Assignment(
                id,
                spinner.getSelectedItem().toString(),
                assignmenTitle.getText().toString(),
                assignmentDiscription.getText().toString(),
                selectClass.getText().toString(),
                checkDigit(datePicker.getDayOfMonth()) + "-" + checkDigit(datePicker.getMonth() + 1) + "-" + datePicker.getYear(),
                switchReminder.isChecked() ? "true" : "false");

        return assignment;

    }

    private String checkDigit(int number) {
        return number >= 10 ? "" + number : "0" + number;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_add_or_edit_assignment;
    }

}
