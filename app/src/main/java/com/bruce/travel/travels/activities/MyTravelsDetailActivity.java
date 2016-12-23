package com.bruce.travel.travels.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;
import com.bruce.travel.finds.adapter.TravelDetailAdapter;
import com.bruce.travel.finds.model.TravelDetailInfo;
import com.bruce.travel.finds.model.TravelNotesInfo;
import com.bruce.travel.travels.been.TravelsBean;
import com.bruce.travel.universal.image.ImageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梦亚 on 2016/9/8.
 */
public class MyTravelsDetailActivity extends BaseActivity {

    private TravelsBean mTravelsBean;
    private View mHeaderView;
    private ImageView detail_bg;
    private TextView title_tv;
    private  TextView time_tv;
    private ListView detailList;
    private List<TravelDetailInfo> list;
    private TravelDetailAdapter detailAdapter;

    private static String TAG = "my travel detail";
    protected ImageManager mImageManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_travel_detail_layout);
        init();
    }

    @Override
    protected void initViews() {
        mHeaderView = getLayoutInflater().inflate(R.layout.my_travel_detail_header_layout, null);
        detail_bg = (ImageView) mHeaderView.findViewById(R.id.my_travel_detail_bg);
        title_tv = (TextView) mHeaderView.findViewById(R.id.my_detail_title_tv);
        detailList = (ListView) findViewById(R.id.my_detail_list);

    }

    @Override
    protected void initData() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        mTravelsBean = (TravelsBean) bundle.getSerializable("my_detail");
        if(mTravelsBean != null) {
            title_tv.setText(mTravelsBean.getTitle());
        } else {
            Log.d(TAG, "travelInfo is null");
        }
        list = new ArrayList<>();
        for(int i = 0;i < 8;i ++) {
            TravelDetailInfo details = new TravelDetailInfo();
            details.content = i + "云南是个少数民族众多的省份，去云南旅游的朋友" +
                    "可以充分感受下那边的风情，另外那边的自然景观就不用我多说了，" +
                    "下面开始介绍我本人一路从云南走来的经历。因为我今年大学即将毕业，" +
                    "也就是人们所说的毕业季，而云南一直是我最向往的地方，所以选择云南" +
                    "作为我毕业旅行的目的地，为期半个月。";
            list.add(details);
        }

        detailList.addHeaderView(mHeaderView);
        detailAdapter = new TravelDetailAdapter(this, list);
        detailList.setAdapter(detailAdapter);
    }

    @Override
    protected void initListeners() {

    }
}
