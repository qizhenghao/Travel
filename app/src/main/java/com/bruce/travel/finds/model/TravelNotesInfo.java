package com.bruce.travel.finds.model;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by 梦亚 on 2016/8/25.
 */
public class TravelNotesInfo implements Serializable, Comparable<TravelNotesInfo> {
    private String title;
    private String location;
    private int icon;
    private String account;
    private String date;
    private String bg_url;
    private int bg;
    private String seen;
    private int msg;
    private int like;

    private boolean isNoData = false;
    private int height;
    public TravelNotesInfo(){

    }

    public TravelNotesInfo(String title, String location, int icon, String account, String date, int bg) {
        this.title = title;
        this.location = location;
        this.icon = icon;
        this.account =account;
        this.date = date;
        this.bg = bg;
    }

    public boolean isNoData() {
        return isNoData;
    }

    public int getHeight() {
        return height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getUrlBg() {
        return bg_url;
    }

    public int getBg() {
        return bg;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setDate (String date) {
        this.date  = date;
    }

    public String getDate() {
        return date;
    }

    public String getSeen() {
        return seen;
    }



    @Override
    public int compareTo(TravelNotesInfo another) {
        return 0;
    }
}
