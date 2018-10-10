package com.example.fredbrume.meterialdesign.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.Adapters.CustomDialogAdapter;
import com.example.fredbrume.meterialdesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fredbrume on 1/9/18.
 */

public class TeacherSelectorDialog extends BaseDialogFragment implements CustomDialogAdapter.CustomDialogOnClickHandler {

    @BindView(R.id.datalist)
    RecyclerView teacherList;

    @BindView(R.id.cancel)
    TextView cancelView;
    @BindView(R.id.searchView)
    EditText searchView;

    private CustomDialogAdapter customDialogAdapter;


    @Override
    public void onDialogItemClick(String teacher) {

        dialogDatahandler.sendDialogData(getTag(), teacher);
        dismiss();
    }

    @OnClick(R.id.cancel)
    public void cancelViewClicked() {
        dismiss();
    }

    public static TeacherSelectorDialog newInstance(String title, String[] data) {

        TeacherSelectorDialog frag = new TeacherSelectorDialog();
        frag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArray("msg", (data));
        frag.setArguments(args);

        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dialog_custom_layout, container, false);
        ButterKnife.bind(this, v);
        customDialogAdapter = new CustomDialogAdapter(this);
        teacherList.setAdapter(customDialogAdapter);

        customDialogAdapter.setLayoutData(getArguments().getStringArray("msg"));

        return v;
    }
}
