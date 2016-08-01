package com.bruce.travel.desktop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by qizhengaho on 2016-08-01 16:50:03.
 */
public class DesktopFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public DesktopFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
