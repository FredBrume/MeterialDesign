package com.example.fredbrume.meterialdesign.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.example.fredbrume.meterialdesign.Data.Local.SchoolUpdateServicerHelper;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyJsonStringUtil;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyStringRequest;
import com.example.fredbrume.meterialdesign.Model.Subject;
import com.example.fredbrume.meterialdesign.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by CeeDe on 1/27/2017.
 */
public class AddOrEditClassFragment extends BaseFragment {

    @BindView(R.id.selectTeacher)
    TextView selectTeacher;

    @BindView(R.id.classname_text)
    EditText classNameTxt;

    @BindView(R.id.period_text)
    EditText periodTxt;

    @BindView(R.id.timePicker)
    TimePicker subjectTime;

    @BindView(R.id.classMondaySwitch)
    SwitchCompat switchMondayClass;

    @BindView(R.id.classTuesdaySwitch)
    SwitchCompat switchTuesdayClass;

    @BindView(R.id.classWednesdaySwitch)
    SwitchCompat switchWednesdayClass;

    @BindView(R.id.classThursdaySwitch)
    SwitchCompat switchThursdayClass;

    @BindView(R.id.classFirdaySwitch)
    SwitchCompat switchFridayClass;

    private String mondayTxt, tuesdayTxt, wednesdayTxt, thursdayTxt, fridayTxt;

    private Subject subject = null;

    public AddOrEditClassFragment() {

    }


    public static AddOrEditClassFragment getInstance(Subject subject) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("subject", subject);
        AddOrEditClassFragment fragment = new AddOrEditClassFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public static AddOrEditClassFragment getInstance() {

        AddOrEditClassFragment fragment = new AddOrEditClassFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        viewPropertyHandler.setBackgroundMainLogo(0.7f);
        viewPropertyHandler.setToolBar(R.menu.add_or_edit_class);

        bindCurrentClassToView(getArguments());


        return view;
    }

    @OnClick(R.id.selectTeacher)
    public void getTeachersRequest() {

        final VolleyStringRequest volleyStringRequest = new VolleyStringRequest(getContext(), Config.buildTeachersUrl().toString());
        volleyStringRequest.addHeader("Authorization", "application/json");
        volleyStringRequest.executeRequest(Request.Method.GET, new VolleyStringRequest.VolleyCallback() {

                    @Override
                    public void getResponse(String response) {

                        Log.d(this.getClass().getSimpleName(), response);
                        viewPropertyHandler.openDialog(VolleyJsonStringUtil.vJsonTeacherList(response), getTag());

                    }
                }
        );

    }

    public void doneMenuItemSelected() {

        SchoolUpdateServicerHelper.insertClassDetails(getContext(), getSubjectFromViews());
        getActivity().getSupportFragmentManager().popBackStack();

        ClassFragment classFragment = (ClassFragment) getFragmentManager()
                .findFragmentByTag(ClassFragment.class.getSimpleName());
        classFragment.refreshClassList();

    }

    private Subject getSubjectFromViews() {

        Subject subject = this.subject != null ? this.subject : new Subject(

                null,
                selectTeacher.getText().toString(),
                classNameTxt.getText().toString(),
                periodTxt.getText().toString(),
                getDaysValuesFromSwitchComponents(),
                subjectTime.getCurrentHour() + ":" + subjectTime.getCurrentMinute());

        return subject;

    }

    private String getDaysValuesFromSwitchComponents() {

        final List<String> days = new ArrayList<>();

        if (switchMondayClass.isChecked()) days.add(mondayTxt = "MON");
        if (switchTuesdayClass.isChecked()) days.add(tuesdayTxt = "TUE");
        if (switchWednesdayClass.isChecked()) days.add(wednesdayTxt = "WED");
        if (switchThursdayClass.isChecked()) days.add(thursdayTxt = "THU");
        if (switchFridayClass.isChecked()) days.add(fridayTxt = "FRI");

        final StringBuilder sb = new StringBuilder();
        final Iterator<String> iterator = days.iterator();

        if (iterator.hasNext()) {
            sb.append(iterator.next());

            while (iterator.hasNext()) {
                sb.append("|");
                sb.append(iterator.next());
            }
        }

        return sb.toString();
    }

    private void bindCurrentClassToView(Bundle bundle) {

        if (bundle != null) {

            System.out.println("============== CLASS CLICKED +++++");
            Subject subject = bundle.getParcelable("subject");

            selectTeacher.setText(subject.getTeacher());
            periodTxt.setText(subject.getPeriod());

            final ArrayList<String> daysArry = new ArrayList<>();
            StringTokenizer tokenDays = new StringTokenizer(subject.getDays(), "|");
            StringTokenizer tokenTime = new StringTokenizer(subject.getTime(), ":");

            while (tokenDays.hasMoreElements()) {
                daysArry.add(tokenDays.nextElement().toString());
            }

            switchMondayClass.setChecked(daysArry.contains("MON"));
            switchTuesdayClass.setChecked(daysArry.contains("TUE"));
            switchWednesdayClass.setChecked(daysArry.contains("WED"));
            switchThursdayClass.setChecked(daysArry.contains("THU"));
            switchFridayClass.setChecked(daysArry.contains("FRI"));

            while (tokenTime.hasMoreElements()) {

            }
        }
    }

    public void teacherViewSelected(String teacher) {
        selectTeacher.setText(teacher);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_add_or_edit_class;
    }

}
