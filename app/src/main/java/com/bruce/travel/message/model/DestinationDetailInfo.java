package com.bruce.travel.message.model;

import java.io.Serializable;

/**
 * Created by 梦亚 on 2016/9/9.
 */
public class DestinationDetailInfo implements Serializable, Comparable<DestinationDetailInfo> {

    public Integer id;
    private String Slogan;
    private String title;
    private String title1;
    private String title2;
    private String title3;
    private String title4;
    private String describtion;

    public DestinationDetailInfo(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(DestinationDetailInfo another) {
        return 0;
    }
}
