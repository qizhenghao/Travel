package com.bruce.travel.travels.been;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class TravelsBean implements Serializable, Comparable<TravelsBean> {

    private String title;
    private String date;
    private String image_url; // 图片地址

    // 暂无数据属性
    private boolean isNoData = false;
    private int height;


    public TravelsBean() {

    }

    public TravelsBean(String title, String date, String image_url) {
        this.title = title;
        this.date = date;
        this.image_url = image_url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isNoData() {
        return isNoData;
    }

    public void setNoData(boolean noData) {
        isNoData = noData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public int compareTo(TravelsBean another) {
        return 0;
    }

//    private String type; // 风景、动物、植物、建筑
//    private String title; // 标题
//    private String from; // 来源
//    private int rank; // 排名
//    private String image_url; // 图片地址
//    private String date;
//
//    // 暂无数据属性
//    private boolean isNoData = false;
//    private int height;
//
//    public TravelsBean() {
//    }
//
//    public TravelsBean(String type, String title, String from, String date, String image_url) {
//        this.type = type;
//        this.title = title;
//        this.from = from;
//        this.date = date;
//        this.image_url = image_url;
//    }
//
//    public int getHeight() {
//        return height;
//    }
//
//    public void setHeight(int height) {
//        this.height = height;
//    }
//
//    public boolean isNoData() {
//        return isNoData;
//    }
//
//    public void setNoData(boolean noData) {
//        isNoData = noData;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getFrom() {
//        return from;
//    }
//
//    public void setFrom(String from) {
//        this.from = from;
//    }
//
//    public int getRank() {
//        return rank;
//    }
//
//    public void setRank(int rank) {
//        this.rank = rank;
//    }
//
//    public String getImage_url() {
//        return image_url;
//    }
//
//    public void setImage_url(String image_url) {
//        this.image_url = image_url;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    @Override
//    public int compareTo(TravelsBean another) {
//        return this.getRank() - another.getRank();
//    }
}
