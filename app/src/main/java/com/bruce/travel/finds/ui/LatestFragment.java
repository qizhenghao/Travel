package com.bruce.travel.finds.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.finds.adapter.TravelNotesAdapter;
import com.bruce.travel.finds.model.TravelNotesInfo;
import com.bruce.travel.universal.utils.ModelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梦亚 on 2016/8/20.
 */
public class LatestFragment extends BaseFragment{

    private ListView latestLv;
    private List<TravelNotesInfo> travelList;
    private TravelNotesAdapter travelAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_latest_layout,null);
        return mContentView ;
    }

    @Override
    protected void initView() {
        latestLv = (ListView) mContentView.findViewById(R.id.latest_listview);
    }

    @Override
    protected void initData() {
        travelList = new ArrayList<>();
        travelAdapter = new TravelNotesAdapter(mActivity, ModelUtil.getTraveNotesData());
        latestLv.setAdapter(travelAdapter);


    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {

    }
}
