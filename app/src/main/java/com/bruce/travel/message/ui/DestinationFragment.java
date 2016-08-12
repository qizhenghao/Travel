package com.bruce.travel.message.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.message.adapter.TitleAdapter;
import com.bruce.travel.message.model.DestinationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qizhenghao on 16/8/11.
 */
public class DestinationFragment extends BaseFragment {

    private ListView mTitleLv;
    private ListView mDetailLv;
    private TitleAdapter titleAdapter;
    private List<DestinationInfo> titleList;
    private List<DestinationInfo> detailList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_destination_layout, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        mTitleLv = (ListView) mContentView.findViewById(R.id.fragment_destination_title_lv);
        mDetailLv = (ListView) mContentView.findViewById(R.id.fragment_destination_detail_lv);
    }

    @Override
    protected void initData() {
        titleList = new ArrayList<>();
        DestinationInfo destinationInfo1 = new DestinationInfo();
        destinationInfo1.name = "1";
        titleList.add(destinationInfo1);
        titleAdapter = new TitleAdapter(mActivity, titleList);
        mTitleLv.setAdapter(titleAdapter);

        detailList = new ArrayList<>();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {

    }
}
