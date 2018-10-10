package com.example.fredbrume.meterialdesign.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;

import com.example.fredbrume.meterialdesign.Activity.ViewPropertyHandler;
import com.example.fredbrume.meterialdesign.Adapters.DocumentAdapter;
import com.example.fredbrume.meterialdesign.R;

import com.example.fredbrume.meterialdesign.Model.Document;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyJsonStringUtil;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyStringRequest;
import butterknife.BindView;

/**
 * Created by fredbrume on 12/7/17.
 */

public class DocumentFragment extends BaseFragment implements DocumentAdapter.DocumentOnClickHandler{

    @BindView(R.id.document_list)
    RecyclerView documentList;

    private DocumentAdapter documentAdapter;

    public DocumentFragment() {

    }

    public static DocumentFragment getInstance() {

        DocumentFragment fragment = new DocumentFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        documentAdapter = new DocumentAdapter(this);
        documentList.setAdapter(documentAdapter);

        getDetailsRequest();

        return view;
    }

    private void getDetailsRequest() {

        final VolleyStringRequest volleyStringRequest = new VolleyStringRequest(getContext(), Config.buildDocumentUrl().toString());
        volleyStringRequest.addHeader("Authorization", "application/json");
        volleyStringRequest.executeRequest(Request.Method.GET, new VolleyStringRequest.VolleyCallback() {

                    @Override
                    public void getResponse(String response) {

                        Log.d(this.getClass().getSimpleName(), response);
                        documentAdapter.setLayoutData(VolleyJsonStringUtil.vJsonDocumentList(response), false);

                    }
                }
        );
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_document;
    }

    @Override
    public void onDocumentItemClick(Document document) {

        viewPropertyHandler.openFragment(DocumentDetailsFragment.getInstance(document),true,null);

    }
}
