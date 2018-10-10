package com.example.fredbrume.meterialdesign.Activity;

import android.support.v4.app.Fragment;

import com.example.fredbrume.meterialdesign.Fragments.AddOrEditClassFragment;
import com.example.fredbrume.meterialdesign.Model.Subject;

import java.util.List;

/**
 * Created by fredbrume on 12/24/17.
 */

public interface ViewPropertyHandler {

    void openFragment(Fragment fragment);
    void openFragment(Fragment fragment, boolean withBackStack, String backStackId);
    void openDialog(String[] teachers, String tag);
    void openDialog(Subject subject);
    void setBackgroundMainLogo(float alpha);
    void setToolBar(int layout);
    void setToolbarItemVisibility(int layout, boolean visibility);
}
