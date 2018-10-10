package com.example.fredbrume.meterialdesign.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import com.example.fredbrume.meterialdesign.Model.Subject;
import com.example.fredbrume.meterialdesign.R;

/**
 * Created by fredbrume on 1/9/18.
 */

public class ClassDialog extends BaseDialogFragment {


    public static ClassDialog newInstance(Subject subject) {

        ClassDialog frag = new ClassDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("subject", subject);
        frag.setArguments(bundle);

        return frag;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreateDialog(savedInstanceState);

        final Subject subject = getArguments().getParcelable("subject");

        return new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogCustom))
                .setTitle("Are you sure ?")
                .setMessage("delete " + subject.getSubject())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.ok_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogDatahandler.sendDialogOption(subject);
                            }
                        }).setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dismiss();
                    }
                })

                .create();
    }

}


