package com.bruce.travel.finds.model;

import java.io.Serializable;

/**
 * Created by 梦亚 on 2016/9/7.
 */
public class TravelDetailInfo  implements Serializable, Comparable<TravelDetailInfo>{

    public String content;

    public String getContent() {
        return content;
    }



    @Override
    public int compareTo(TravelDetailInfo another) {
        return 0;
    }
}
