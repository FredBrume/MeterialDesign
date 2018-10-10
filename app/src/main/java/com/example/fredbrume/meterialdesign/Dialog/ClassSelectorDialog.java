package com.example.fredbrume.meterialdesign.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

/**
 * Created by fredbrume on 1/28/18.
 */

public class ClassSelectorDialog extends BaseDialogFragment implements CustomDialogAdapter.CustomDialogOnClickHandler  {

    @BindView(R.id.datalist)
    RecyclerView classList;

    @BindView(R.id.cancel)
    TextView cancelView;
    @BindView(R.id.searchView)
    EditText searchView;

    private CustomDialogAdapter classAdapter;

    public static ClassSelectorDialog newInstance(String title, String[] data) {

        ClassSelectorDialog frag = new ClassSelectorDialog();
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
        classAdapter = new CustomDialogAdapter(this);
        classList.setAdapter(classAdapter);

        classAdapter.setLayoutData(getArguments().getStringArray("msg"));

        return v;
    }

    @Override
    public void onDialogItemClick(String subject) {

        dialogDatahandler.sendDialogData(getTag(), subject);
        dismiss();
    }
}
