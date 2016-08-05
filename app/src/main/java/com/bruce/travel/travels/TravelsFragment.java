package com.bruce.travel.travels;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.travels.adapter.TravelsAdapter;
import com.bruce.travel.travels.view.HeaderAdViewView;
import com.bruce.travel.travels.view.PinnedHeaderListView;
import com.bruce.travel.universal.utils.Methods;
import com.bruce.travel.universal.utils.ModelUtil;
import com.bruce.travel.universal.view.ScrollOverListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qizhenghao on 16/8/1.
 */
public class TravelsFragment extends BaseFragment implements ScrollOverListView.OnPullDownListener{

    private PinnedHeaderListView mListView;
    private HeaderAdViewView headerVp;
    private View headerTab;
    private TravelsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_travels_layout, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        mListView = (PinnedHeaderListView) mContentView.findViewById(R.id.fragment_travels_lv);
        headerVp = new HeaderAdViewView(mActivity);
    }

    @Override
    protected void initData() {
        List<String> travelsList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            travelsList.add(i + "");
        }
        mAdapter = new TravelsAdapter(mActivity, travelsList);
        mListView.setAdapter(mAdapter);
        mListView.setRefreshable(true);
        mListView.setOnPullDownListener(this);

        headerVp.fillView(ModelUtil.getAdData(), mListView);
        headerTab = LayoutInflater.from(mActivity).inflate(R.layout.fragment_travels_header_tab_layout, null);
        mListView.addHeaderTabView(headerTab);
    }

    @Override
    protected void initListener() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void refresh() {

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.notifyLoadMoreComplete();
            }
        }, 2000);
    }
}
