package com.example.fredbrume.meterialdesign.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fredbrume.meterialdesign.Activity.ViewPropertyHandler;

import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fredbrume on 12/5/17.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    public ViewPropertyHandler viewPropertyHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            viewPropertyHandler = (ViewPropertyHandler) context;

        } catch (ClassCastException exception) {

            throw new ClassCastException(context.toString()
                    + " must implement viewPropertyHandler");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewPropertyHandler = null;
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    protected abstract int getLayoutResId();

}
