package com.bruce.travel.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by qizhenghao on 16/8/1.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected void init() {
        initViews();
        initData();
        initListeners();
    }

    abstract protected void initViews();

    abstract protected void initData();

    abstract protected void initListeners();


}
