package com.bruce.travel.finds.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;
import com.bruce.travel.finds.model.TravelNotesInfo;
import com.bruce.travel.universal.image.ImageManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 梦亚 on 2016/9/5.
 */
public class TravelDetailActivity extends BaseActivity {

    @Bind(R.id.travel_detail_bg)
    ImageView detail_bg;
    @Bind(R.id.detail_title_tv)
    TextView title_tv;
    @Bind(R.id.account_tv)
    TextView account_tv;
    @Bind(R.id.detail_seen_tv)
    TextView seen_tv;
    @Bind(R.id.detail_comment_tv)
    TextView comment_tv;
    @Bind(R.id.detail_location_tv)
    TextView location_tv;
    @Bind(R.id.detail_time_tv)
    TextView time_tv;
    @Bind(R.id.icon_iv)
    ImageView icon_iv;

    private TravelNotesInfo travelInfo;
    private static String TAG = "travel_detail";
    private ImageManager mImageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail_layout);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        travelInfo = (TravelNotesInfo) bundle.getSerializable("detail");
        if( travelInfo != null) {
            title_tv.setText(travelInfo.getTitle());
            location_tv.setText(travelInfo.getLocation());
            account_tv.setText(travelInfo.getAccount());
            time_tv.setText(travelInfo.getDate());
            icon_iv.setBackgroundResource(travelInfo.getIcon());
            detail_bg.setBackgroundResource(travelInfo.getBg());
        } else {
            Log.d(TAG, "travelInfo is null");
        }
    }

    @Override
    protected void initListeners() {

    }
}
