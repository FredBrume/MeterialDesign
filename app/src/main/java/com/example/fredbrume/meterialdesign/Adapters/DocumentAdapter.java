package com.example.fredbrume.meterialdesign.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.R;
import com.squareup.picasso.Picasso;

import com.example.fredbrume.meterialdesign.Model.Document;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by fredbrume on 11/5/17.
 */

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.NewsViewHolder> {

    private Context context;
    private boolean details;


    public DocumentOnClickHandler documentOnClickHandler;
    private Document[] documents;

    public interface DocumentOnClickHandler {

        void onDocumentItemClick(Document document);
    }

    public DocumentAdapter(DocumentOnClickHandler documentOnClickHandler) {
        this.documentOnClickHandler = documentOnClickHandler;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int layoutIdForListItem = (!details) ? R.layout.document_list_listitem : R.layout.document_details_listitem;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new NewsViewHolder(view);

    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        Document document = documents[position];

        if (!details) {
            Picasso.with(context)
                    .load(Config.buildDocumentIconUrl(documents[position].icon)).into(holder.documentIcon);
            holder.documentName.setText(document.type);

        }else{
            holder.documentName.setText(document.name);
        }
    }


    @Override
    public int getItemCount() {
        return documents == null ? 0 : documents.length;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.document_name)
        TextView documentName;

        @BindView(R.id.doc_icon)
        ImageView documentIcon;


        public NewsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Animation animatioPinch = AnimationUtils.loadAnimation(context, R.anim.pinch);
            animatioPinch.setDuration(500);
            view.startAnimation(animatioPinch);

            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    documentOnClickHandler.onDocumentItemClick(documents[getAdapterPosition()]);
                }
            }, 500L);
        }
    }

    public void setLayoutData(Document[] documents, boolean details) {

        this.documents = documents;
        this.details = details;
        notifyDataSetChanged();
    }
}
