package com.example.fredbrume.meterialdesign.Data.Remote.Api;

import com.example.fredbrume.meterialdesign.Model.Teacher;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.example.fredbrume.meterialdesign.Model.Banner;
import com.example.fredbrume.meterialdesign.Model.Document;
import com.example.fredbrume.meterialdesign.Model.Gallery;
import com.example.fredbrume.meterialdesign.Model.Location;
import com.example.fredbrume.meterialdesign.Model.News;

/**
 * Created by fredbrume on 10/2/17.
 */

public class VolleyJsonStringUtil {


    public static LinkedList<News> vJsonStringToList(String jsonString) {

        Gson gson = new Gson();
        News news[] = gson.fromJson(jsonString, News[].class);

        LinkedList<News> linkedListNews = new LinkedList<>();

        for (int i = 0; i < news.length; i++) {
            linkedListNews.add(news[i]);
        }
        return linkedListNews;
    }

    public static String[] vJsonTeacherList(String jsonString) {

        Gson gson = new Gson();
        Teacher[] teachers = gson.fromJson(jsonString, Teacher[].class);
        String[] teaStrings = new String[teachers.length];

        for (int i = 0; i < teachers.length; i++) {
            teaStrings[i] = teachers[i].name;
        }

        return teaStrings;
    }

    public static Gallery[] vJsonPicturesList(String jsonString) {

        Gson gson = new Gson();
        Gallery pictures[] = gson.fromJson(jsonString, Gallery[].class);

        return pictures;
    }

    public static Location[] vJsonLocationList(String jsonString) {

        Gson gson = new Gson();
        Location locations[] = gson.fromJson(jsonString, Location[].class);

        return locations;
    }

    public static Document[] vJsonDocumentList(String jsonString) {

        Gson gson = new Gson();
        Document[] documents = gson.fromJson(jsonString, Document[].class);

        return documents;
    }
}
