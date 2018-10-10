package com.example.fredbrume.meterialdesign.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CeeDe on 10/26/2016.
 */
public class Assignment implements Parcelable{

    private String id, type, assignmentName, note, subject, date, reminder;

    public Assignment(String id, String type, String assignmentName, String note, String subject, String date, String reminder) {
        this.id = id;
        this.type = type;
        this.assignmentName = assignmentName;
        this.note = note;
        this.subject = subject;
        this.date = date;
        this.reminder = reminder;
    }

    public String getReminder() {
        return reminder;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getNote() {
        return note;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public static Creator<Assignment> getCREATOR() {
        return CREATOR;
    }

    protected Assignment(Parcel in) {
        id = in.readString();
        type = in.readString();
        assignmentName = in.readString();
        note = in.readString();
        subject = in.readString();
        date = in.readString();
        reminder=in.readString();
    }

    public static final Creator<Assignment> CREATOR = new Creator<Assignment>() {
        @Override
        public Assignment createFromParcel(Parcel in) {
            return new Assignment(in);
        }

        @Override
        public Assignment[] newArray(int size) {
            return new Assignment[size];
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
        dest.writeString(assignmentName);
        dest.writeString(note);
        dest.writeString(subject);
        dest.writeString(date);
        dest.writeString(reminder);
    }
}
