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
    private List<DestinationInfo> titleList;
    private DestinationDetailFragment destinationDetailFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_destination_layout, null);
        return mContentView;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

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
