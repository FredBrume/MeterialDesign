package com.example.fredbrume.meterialdesign.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;

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

public class DocumentDetailsFragment extends BaseFragment implements DocumentAdapter.DocumentOnClickHandler {

    @BindView(R.id.document_list)
    RecyclerView documentList;

    private DocumentAdapter documentAdapter;
    private static Document document;

    public DocumentDetailsFragment() {

    }

    public static DocumentDetailsFragment getInstance(Document document) {

        DocumentDetailsFragment.document = document;
        DocumentDetailsFragment fragment = new DocumentDetailsFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        documentAdapter = new DocumentAdapter(this);
        documentList.setAdapter(documentAdapter);

        getDocumentDetailsRequest();

        return view;
    }

    private void getDocumentDetailsRequest() {

        final VolleyStringRequest volleyStringRequest = new VolleyStringRequest(getContext(), Config.buildDocumentDetailsUrl().toString());
        volleyStringRequest.addHeader("Content-Type", "application/form-data; charset=utf-8");
        volleyStringRequest.addParams(Config.DOCUMENT_TYPE_QUERY, document.type);
        volleyStringRequest.executeRequest(Request.Method.POST, new VolleyStringRequest.VolleyCallback() {

                    @Override
                    public void getResponse(String response) {

                        Log.d(this.getClass().getSimpleName(), response);

                        Document[] documents = VolleyJsonStringUtil.vJsonDocumentList(response);
                        documentAdapter.setLayoutData(documents, true);

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

        String googleDocsUrl = "http://docs.google.com/viewer?url=" + Config.buildDocumentItemUrl(document.document);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(googleDocsUrl), "text/html");
        startActivity(intent);

    }
}
