package com.bruce.travel.message.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.message.adapter.TitleAdapter;
import com.bruce.travel.message.model.DestinationDetailInfo;
import com.bruce.travel.message.model.DestinationInfo;
import com.bruce.travel.universal.utils.Methods;
import com.bruce.travel.universal.utils.ModelUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by qizhenghao on 16/8/11.
 */
public class DestinationFragment extends BaseFragment {

    private ListView mTitleLv;
    private FrameLayout mDetailContainer;
    private TitleAdapter titleAdapter;
    private int titlePosition;
    private List<DestinationInfo> titleList;
    private DestinationDetailFragment dstDetailFragment;
    private DestinationDetailInfo dstInfo;
    private int mPosition;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_destination_layout, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        mTitleLv = (ListView)mContentView.findViewById(R.id.dst_lv);
    }

    @Override
    protected void initData() {
        titleAdapter = new TitleAdapter(getContext(), ModelUtil.getDestinationData());
        mTitleLv.setAdapter(titleAdapter);

        dstDetailFragment = new DestinationDetailFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dst_detail_container, dstDetailFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("selectInfo", titlePosition);
        dstDetailFragment.setArguments(bundle);
        fragmentTransaction.commit();

    }

    @Override
    protected void initListener() {
        mTitleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Methods.showToast("" + position, false);
                titleAdapter.setSelectedItem(position);
                dstDetailFragment = new DestinationDetailFragment();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.dst_detail_container, dstDetailFragment);
                Bundle bundle = new Bundle();
                bundle.putInt("selectInfo", position);
                dstDetailFragment.setArguments(bundle);
                fragmentTransaction.commit();

            }
        });
        mTitleLv.setAdapter(new TitleAdapter(getContext(), ModelUtil.getDestinationData()));
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
