package com.bruce.travel.message.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.message.adapter.DetailAdapter;
import com.bruce.travel.message.model.DestinationDetailInfo;
import com.bruce.travel.universal.utils.ModelUtil;

import java.util.List;

/**
 * Created by 梦亚 on 2016/9/9.
 */
public class DestinationDetailFragment extends BaseFragment {

    private ListView detailList;
    private List<DestinationDetailInfo> detailInfoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_destination_detail_layout, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        detailList = (ListView) mContentView.findViewById(R.id.destination_detail_lv);
    }

    @Override
    protected void initData() {

        int selectNum = getArguments().getInt("selectInfo", 0);
        DetailAdapter detailAdapter;
        if (selectNum == 0) {
            detailAdapter = new DetailAdapter(getContext(), ModelUtil.getDestinationDetailData());
        } else if (selectNum == 1){
            detailAdapter = new DetailAdapter(getContext(), ModelUtil.getDestinationDetailData1());
        } else {
            detailAdapter = new DetailAdapter(getContext(), ModelUtil.getDestinationDetailData2());
        }
        detailList.setAdapter(detailAdapter);


    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {

    }
}
