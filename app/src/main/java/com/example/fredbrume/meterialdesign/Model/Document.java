package com.example.fredbrume.meterialdesign.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fredbrume on 12/17/17.
 */

public class Document  implements Parcelable{

    @SerializedName("doc_Id")
    public String id;

    @SerializedName("Type")
    public String type;

    @SerializedName("name")
    public String name;

    @SerializedName("document")
    public String document;

    @SerializedName("Icon")
    public String icon;

    protected Document(Parcel in) {
        id = in.readString();
        type = in.readString();
        name = in.readString();
        document = in.readString();
        icon = in.readString();
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(document);
        dest.writeString(icon);
    }
}
