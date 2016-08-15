package com.bruce.travel.message.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.message.adapter.DetailAdapter;
import com.bruce.travel.message.adapter.TitleAdapter;
import com.bruce.travel.message.model.DestinationInfo;
import com.bruce.travel.universal.api.TravelApi;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by qizhenghao on 16/8/11.
 */
public class DestinationFragment extends BaseFragment {

    private ListView mTitleLv;
    private ListView mDetailLv;
    private TitleAdapter titleAdapter;
    private DetailAdapter detailAdatper;
    private List<DestinationInfo> titleList;
    private List<DestinationInfo> detailList;
    private int mFirstVisibleDetailItem;
    private boolean mNeedScrollGroupLV;
    private int mSelectedTitleItem;

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
        detailList = new ArrayList<>();
        for(int i=0; i<36; i++) {
            DestinationInfo destinationInfo = new DestinationInfo();
            destinationInfo.name = i + "";
            destinationInfo.content = i + " " + i + "" +i + "" +i + "\n" +i + "" +i + "" +i + "" +i +
                    "" +i + "" +i + "\n" +i + "" +i + "" +i + "" +i + "" +i + "\n" +i + "";
            titleList.add(destinationInfo);
            detailList.add(destinationInfo);
        }
        titleAdapter = new TitleAdapter(mActivity, titleList);
        mTitleLv.setAdapter(titleAdapter);
        detailAdatper = new DetailAdapter(mActivity, detailList);
        mDetailLv.setAdapter(detailAdatper);
//        TravelApi.getSoftwareTagList(342, 0, mResponseHandler);//测试http能ping通
    }

    @Override
    protected void initListener() {
        mDetailLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mNeedScrollGroupLV = true;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mFirstVisibleDetailItem != firstVisibleItem && mNeedScrollGroupLV) {
                    mFirstVisibleDetailItem = firstVisibleItem;
                    mSelectedTitleItem = mFirstVisibleDetailItem;
                    titleAdapter.setSelectedItem(mSelectedTitleItem);
                    titleAdapter.notifyDataSetChanged();
                    mTitleLv.setSelection(mFirstVisibleDetailItem);
                }
            }
        });

        mTitleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mNeedScrollGroupLV = false;
                mSelectedTitleItem = position;
                mDetailLv.setSelection(position);
                titleAdapter.setSelectedItem(mSelectedTitleItem);
                titleAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void refresh() {

    }

    private AsyncHttpResponseHandler mResponseHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            String result = null;
            try {
                result = new String(arg2, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("Bruce", "mResponseHandler: onSuccess: " + result);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            Log.d("Bruce", "mResponseHandler: onFailure: " + Arrays.toString(arg2));
        }

        public void onFinish() {
            Log.d("Bruce", "onFinish");
        }
    };
}
