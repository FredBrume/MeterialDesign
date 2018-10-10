package com.example.fredbrume.meterialdesign.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CeeDe on 12/4/2016.
 */
public class News implements Parcelable {

    @SerializedName("No_Title")
    public String newsTitle;
    @SerializedName("No_Context")
    public String newsContext;
    @SerializedName("No_Time")
    public String newsTime;
    @SerializedName("No_Status")
    public String newsStatus;
    @SerializedName("No_Date")
    public String newsDate;
    @SerializedName("No_Type")
    public String newsType;
    @SerializedName("Event_Date")
    public String eventDate;
    @SerializedName("Start_Time")
    public String eventStartTime;
    @SerializedName("End_Time")
    public String eventEndTime;
    @SerializedName("ImageUrl")
    public String imageUrl;
    @SerializedName("No_ImageUrl")
    public String newsImageUrl;
    @SerializedName("Attach_Url")
    public String attachUrl;
    @SerializedName("NAME")
    public String name;
    @SerializedName("Designation")
    public String designation;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.newsTitle);
        dest.writeString(this.newsContext);
        dest.writeString(this.newsTime);
        dest.writeString(this.newsStatus);
        dest.writeString(this.newsDate);
        dest.writeString(this.newsType);
        dest.writeString(this.eventDate);
        dest.writeString(this.eventStartTime);
        dest.writeString(this.eventEndTime);
        dest.writeString(this.imageUrl);
        dest.writeString(this.newsImageUrl);
        dest.writeString(this.attachUrl);
        dest.writeString(this.name);
        dest.writeString(this.designation);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.newsTitle = in.readString();
        this.newsContext = in.readString();
        this.newsTime = in.readString();
        this.newsStatus = in.readString();
        this.newsDate = in.readString();
        this.newsType = in.readString();
        this.eventDate = in.readString();
        this.eventStartTime = in.readString();
        this.eventEndTime = in.readString();
        this.imageUrl = in.readString();
        this.newsImageUrl = in.readString();
        this.attachUrl = in.readString();
        this.name = in.readString();
        this.designation = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
