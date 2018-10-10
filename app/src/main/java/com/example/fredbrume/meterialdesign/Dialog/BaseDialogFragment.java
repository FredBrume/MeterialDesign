package com.example.fredbrume.meterialdesign.Dialog;


import android.app.Activity;

import com.example.fredbrume.meterialdesign.Model.Subject;

/**
 * Created by fredbrume on 1/9/18.
 */

public abstract class BaseDialogFragment extends android.support.v4.app.DialogFragment {

    DialogDataHandler dialogDatahandler;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogDatahandler = (DialogDataHandler) activity;
        } catch (ClassCastException exception) {

            throw new ClassCastException(activity.toString()
                    + " must implement DialogDataHandler");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialogDatahandler = null;
    }

    public interface DialogDataHandler {

        void sendDialogData(String dialogTag, String data);

        void sendDialogOption(Subject subject);
    }
}

