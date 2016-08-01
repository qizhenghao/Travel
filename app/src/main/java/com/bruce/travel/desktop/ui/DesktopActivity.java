package com.bruce.travel.desktop.ui;

import android.os.Bundle;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;


public class DesktopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop_layout);
        init();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.desktop_container, new DesktopFragment())
                    .commit();
        }
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }
}
