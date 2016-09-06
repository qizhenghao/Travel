package com.bruce.travel.travels.model;


import com.bruce.travel.travels.been.TravelFragmentData;
import com.bruce.travel.travels.listener.OnGetDataListener;

/**
 * Created by qizhenghao on 16/9/6.
 */
public interface ITravelModel {

    void getTravelData(TravelFragmentData travelFragmentData, OnGetDataListener<TravelFragmentData> onGetDataListener);
}
