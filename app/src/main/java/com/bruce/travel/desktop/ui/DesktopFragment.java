package com.bruce.travel.desktop.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.desktop.view.DesktopBottomTabLayout;
import com.bruce.travel.desktop.adapter.DesktopFragmentAdapter;
import com.bruce.travel.desktop.TestFragment;
import com.bruce.travel.desktop.view.TabViewPager;
import com.bruce.travel.finds.ui.FindFragment;
import com.bruce.travel.message.ui.DestinationFragment;
import com.bruce.travel.mine.ui.MineFragment;
import com.bruce.travel.travels.TravelsFragment;

import java.util.ArrayList;
import java.util.List;

public class DesktopFragment extends BaseFragment {

    private DesktopFragmentAdapter mAdapter;
    private TabViewPager mViewPager;
    private DesktopBottomTabLayout mTabLayout;
    private List<Fragment> fragments;

    public DesktopFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_desktop_layout, container, false);
        return mContentView;
    }

    @Override
    protected void initView() {
        mViewPager = (TabViewPager) mContentView.findViewById(R.id.desktop_viewpager);
        mViewPager.setCanScroll(true);//设置可滑动，默认为false
        mTabLayout = (DesktopBottomTabLayout) mContentView.findViewById(R.id.desktop_bottom_layout);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new TravelsFragment());
        fragments.add(new FindFragment());
        fragments.add(new DestinationFragment());
        fragments.add(new MineFragment());
        mAdapter = new DesktopFragmentAdapter(getFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {

    }
}