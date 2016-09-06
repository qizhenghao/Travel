package com.bruce.travel.travels.been;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qizhenghao on 16/9/6.
 */
public class TravelFragmentData {

    public TravelFragmentData() {
        init();
    }

    private void init() {
        travelsBeans = new ArrayList<>();
        filterData = new FilterData();
        adData = new ArrayList<>();
        channelBeans = new ArrayList<>();
    }

    public List<TravelsBean> travelsBeans;
    public FilterData filterData;
    public List<String> adData;
    public List<ChannelBean> channelBeans;

}
