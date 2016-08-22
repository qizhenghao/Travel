package com.bruce.travel.finds.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.desktop.TestFragment;
import com.bruce.travel.desktop.adapter.DesktopFragmentAdapter;
import com.bruce.travel.desktop.view.TabViewPager;
import com.bruce.travel.finds.adapter.FindFragmentPagerAdapter;
import com.bruce.travel.finds.view.FindTopTabLayout;
import com.bruce.travel.travels.TravelsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梦亚 on 2016/8/18.
 */
public class FindFragment extends BaseFragment {

    private ViewPager mViewPager;
    private FindTopTabLayout mTabLayout;
    private FindTopTabLayout findTopTabLayout;
    private List<Fragment> fragments;
    private FindFragmentPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_find_layout,null);
        initView();
        initData();
        return mContentView;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) mContentView.findViewById(R.id.find_viewpager);
        mTabLayout = (FindTopTabLayout) mContentView.findViewById(R.id.find_top_layout);
        mTabLayout.setViewIds(new int[]{R.id.tab_line_layout, R.id.tab_latest, R.id.tab_recommend});
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new LatestFragment());
        fragments.add(TestFragment.newInstance("6"));
        mAdapter = new FindFragmentPagerAdapter(getChildFragmentManager(),fragments);
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
