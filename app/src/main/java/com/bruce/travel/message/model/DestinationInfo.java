package com.bruce.travel.message.model;


import com.bruce.travel.universal.utils.ModelUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qizhenghao on 16/8/11.
 */
public class DestinationInfo {

    private String title;

    public DestinationInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

//    public static List<DestinationDetailInfo> items = new ArrayList<>();
//
//    public static Map<Integer, List<DestinationDetailInfo>> item_map = new HashMap<>();
//
//    static {
//        addItem(ModelUtil.getDestinationDetailData());
//    }
//
//    public static void addItem(List<DestinationDetailInfo> detailInfo) {
//        items = detailInfo;
//        item_map.put(1, detailInfo);
//    }
}
