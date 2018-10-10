package com.example.fredbrume.meterialdesign.Model;

import java.io.Serializable;

/**
 * Created by CeeDe on 10/4/2016.
 */
public class NavDrawerItem implements Serializable {

    private String titles;
    private String groupTitles;
    private int icon;
    public boolean isGroupHeader=false;


    public NavDrawerItem(String title, int icon) {
        this.titles = title;
        this.icon = icon;
    }

    public NavDrawerItem(String groupTitles)
    {
        this.groupTitles=groupTitles;
        isGroupHeader =true;
    }

    public String getGroupTitles() {
        return groupTitles;
    }

    public void setGroupTitles(String groupTitles) {
        this.groupTitles = groupTitles;
    }

    public int getIcon() {return icon;}

    public void setIcon(int icon) {this.icon = icon;}

    public String getTitle() {return titles;}

    public void setTitle(String title) {this.titles = title;}

}
