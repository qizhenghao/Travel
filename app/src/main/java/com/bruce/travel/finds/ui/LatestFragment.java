package com.bruce.travel.finds.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.finds.adapter.FindsListAdapter;
import com.bruce.travel.finds.model.FindsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梦亚 on 2016/8/20.
 */
public class LatestFragment extends BaseFragment{

    private ListView latestLv;
    private List<FindsInfo> findsList;
    private FindsListAdapter findsAdapter;


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
        findsList = new ArrayList<>();
        for(int i = 0;i < 10 ;i++) {
            FindsInfo findsInfo = new FindsInfo();
            findsInfo.setTitle(i+"");
            findsInfo.setLocation("beijingshi");
            findsList.add(findsInfo);
        }

        findsAdapter = new FindsListAdapter(mActivity,findsList);
        latestLv.setAdapter(findsAdapter);


    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {

    }
}
