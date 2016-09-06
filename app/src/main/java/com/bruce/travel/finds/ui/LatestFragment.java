package com.bruce.travel.finds.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.desktop.DesktopActivity;
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
    private TravelNotesInfo travelInfo;


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
        travelAdapter = new TravelNotesAdapter(mActivity, ModelUtil.getTravelNotesData());
        latestLv.setAdapter(travelAdapter);
        latestLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                travelInfo = ModelUtil.getTravelNotesData().get(position);
                Intent intent = new Intent(getContext(),TravelDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail", travelInfo);
                intent.putExtras(bundle);
                startActivity(intent);
                
            }
        });


    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {

    }
}
