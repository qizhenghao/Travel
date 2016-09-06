package com.bruce.travel.travels.model;

import com.bruce.travel.travels.been.TravelFragmentData;
import com.bruce.travel.travels.listener.OnGetDataListener;
import com.bruce.travel.universal.utils.ModelUtil;

/**
 * Created by qizhenghao on 16/9/6.
 */
public class ITravelModelImpl implements ITravelModel {

    @Override
    public void getTravelData(final TravelFragmentData travelFragmentData, final OnGetDataListener<TravelFragmentData> onGetDataListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                travelFragmentData.channelBeans.addAll(ModelUtil.getChannelData());
                travelFragmentData.adData.addAll(ModelUtil.getAdData());
                travelFragmentData.filterData.setCategory(ModelUtil.getCategoryData());
                travelFragmentData.filterData.setFilters(ModelUtil.getFilterData());
                travelFragmentData.filterData.setSorts(ModelUtil.getSortData());
                travelFragmentData.travelsBeans.addAll(ModelUtil.getTravelingData());
                onGetDataListener.onSuccess(travelFragmentData);
            }
        }).start();
    }
}
