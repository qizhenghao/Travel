package com.bruce.travel.finds.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;
import com.bruce.travel.finds.adapter.TravelDetailAdapter;
import com.bruce.travel.finds.model.TravelDetailInfo;
import com.bruce.travel.finds.model.TravelNotesInfo;
import com.bruce.travel.universal.image.ImageManager;
import com.bruce.travel.universal.view.ScrollOverListView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 梦亚 on 2016/9/5.
 */
public class TravelDetailActivity extends BaseActivity {

    @Bind(R.id.travel_detail_bottom_ll)
    LinearLayout bottom_ll;
    @Bind(R.id.go_top_btn)
    Button go_top_btn;
    private ImageView detail_bg;
    private TextView title_tv;
    private TextView account_tv;
    private TextView seen_tv;
    private  TextView comment_tv;
    private  TextView location_tv;
    private  TextView time_tv;
    private  ImageView icon_iv;

    private TravelNotesInfo travelInfo;
    private static String TAG = "travel_detail";
    private ImageManager mImageManager;
    private List<TravelDetailInfo> list;
    private ListView detailList;
    private TravelDetailAdapter detailAdapter;
    private View headerView;
    private int mCurrentFirstVisibleItem = 0;
    private int height;
    private SparseArray recordSp = new SparseArray(0);
    private int distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail_layout);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void initViews() {

        headerView = getLayoutInflater().inflate(R.layout.travel_detail_header_layout, null);
        detail_bg = (ImageView) headerView.findViewById(R.id.travel_detail_bg);
        title_tv = (TextView) headerView.findViewById(R.id.detail_title_tv);
        account_tv = (TextView) headerView.findViewById(R.id.account_tv);
        location_tv = (TextView) headerView.findViewById(R.id.detail_location_tv);
        time_tv = (TextView) headerView.findViewById(R.id.detail_time_tv);
        icon_iv = (ImageView) headerView.findViewById(R.id.icon_iv);

        detailList = (ListView) findViewById(R.id.detail_list);

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

        detailList.addHeaderView(headerView);
        detailAdapter = new TravelDetailAdapter(this, list);
        detailList.setAdapter(detailAdapter);

        WindowManager wm = this.getWindowManager();
        height = wm.getDefaultDisplay().getHeight();
    }

    @Override
    protected void initListeners() {
        detailList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        bottom_ll.setVisibility(View.VISIBLE);
                        if (distance > 0.6 * height) {
                            go_top_btn.setVisibility(View.VISIBLE);
                        } else {
                            go_top_btn.setVisibility(View.GONE);
                        }
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        bottom_ll.setVisibility(View.GONE);
                        if (distance > 0.6 * height) {
                            go_top_btn.setVisibility(View.VISIBLE);
                        } else {
                            go_top_btn.setVisibility(View.GONE);
                        }
                        break;
                    case SCROLL_STATE_FLING:
                        bottom_ll.setVisibility(View.GONE);
                        if(distance > 0.6 * height) {
                            go_top_btn.setVisibility(View.VISIBLE);
                        } else {
                            go_top_btn.setVisibility(View.GONE);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCurrentFirstVisibleItem = firstVisibleItem;
                View firstView = view.getChildAt(0);
                if (null != firstView) {
                    ItemRecord itemRecord = (ItemRecord) recordSp.get(firstVisibleItem);
                    if (null == itemRecord) {
                        itemRecord = new ItemRecord();
                    }
                    itemRecord.height = firstView.getHeight();
                    itemRecord.top = firstView.getTop();
                    recordSp.append(firstVisibleItem, itemRecord);
                    distance = getScrollY();//滚动距离
                }
            }
        });

        go_top_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListViewPos(0);
            }
        });
    }

    private void setListViewPos(int pos) {
        if (android.os.Build.VERSION.SDK_INT >= 8) {
            detailList.smoothScrollToPosition(pos);
        } else {
            detailList.setSelection(pos);
        }
    }

    private int getScrollY() {
        int height = 0;
        for (int i = 0; i < mCurrentFirstVisibleItem; i++) {
        ItemRecord itemRecod = (ItemRecord) recordSp.get(i);
        height += itemRecod.height;
        }
        ItemRecord itemRecod = (ItemRecord) recordSp.get(mCurrentFirstVisibleItem);
        if (null == itemRecod) {
        itemRecod = new ItemRecord();
        }
        return height - itemRecod.top;
        }

    class ItemRecord {
        int height = 0;
        int top = 0;
    }


}
