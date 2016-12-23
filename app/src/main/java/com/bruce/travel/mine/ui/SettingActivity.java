package com.bruce.travel.mine.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.desktop.DesktopActivity;
import com.bruce.travel.mine.adapter.CommonListAdapter;

/**
 * Created by 梦亚 on 2016/8/9.
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    private ListView listView;
    private CommonListAdapter mAdapter;
    private Context mContext;
    private boolean isLogin;
    private LinearLayout quit_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        initView();
        initListener();

    }

    private void initView() {
        quit_ll = (LinearLayout)findViewById(R.id.quit_ll);
    }
    private void initListener() {
        quit_ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        isLogin = DesktopActivity.isLogin;
        switch(v.getId()) {
            case R.id.quit_ll:

                new AlertDialog.Builder(this).setTitle(R.string.point)
                        .setMessage(getString(R.string.exit_message))
                        .setPositiveButton(getString(R.string.exit_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isLogin = false;
                                Intent intent = new Intent(SettingActivity.this, DesktopActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton(getString(R.string.exit_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                break;
        }

    }
}
