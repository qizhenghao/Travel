package com.bruce.travel.desktop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;
import com.bruce.travel.db.MyDbHelper;

import java.sql.SQLException;


public class DesktopActivity extends BaseActivity {
    public static boolean isLogin = false;
    public static String username = null;

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

        Intent intent = getIntent();
        isLogin = intent.getBooleanExtra("loginState", false);
        username = intent.getStringExtra("username");
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
