package com.example.fredbrume.meterialdesign.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fredbrume on 5/24/17.
 */

public class Location implements Parcelable {

    @SerializedName("location_Id")
    public String id;
    @SerializedName("Name")
    public String name;
    @SerializedName("Address")
    public String address;

    protected Location(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
    }
}
