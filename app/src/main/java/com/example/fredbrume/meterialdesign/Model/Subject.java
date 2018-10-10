package com.example.fredbrume.meterialdesign.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fredbrume on 1/12/18.
 */

public class Subject implements Parcelable {

    private String id, teacher, subject, period, days, time;

    public Subject(String id, String teacher, String subject, String period, String days, String time) {

        this.id = id;
        this.teacher = teacher;
        this.subject = subject;
        this.period = period;
        this.days = days;
        this.time = time;
    }

    protected Subject(Parcel in) {
        id = in.readString();
        teacher = in.readString();
        subject = in.readString();
        period = in.readString();
        days = in.readString();
        time = in.readString();
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSubject() {
        return subject;
    }

    public String getPeriod() {
        return period;
    }

    public String getDays() {
        return days;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(teacher);
        dest.writeString(subject);
        dest.writeString(period);
        dest.writeString(days);
        dest.writeString(time);
    }
}
