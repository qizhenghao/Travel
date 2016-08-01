package com.bruce.travel.travels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;

/**
 * Created by qizhenghao on 16/8/1.
 */
public class TravelsFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_travels_layout, null);
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
}
