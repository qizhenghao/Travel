package com.bruce.travel.finds.model;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by 梦亚 on 2016/8/20.
 */
public class FindsInfo {
    private String title;
    private String location;
    private ImageView icon;
    private String account;
    private String date;
    private int seen;
    private int msg;
    private int like;

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

}
