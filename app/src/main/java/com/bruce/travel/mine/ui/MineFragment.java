package com.bruce.travel.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.desktop.ui.DesktopActivity;

/**
 * Created by 梦亚 on 2016/8/2.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private boolean isLogin;
    private String username = null;
    private String username_unlogin = "用户名";
    private Button login_btn, user_head;
    private Button msg_remind_btn;
    private LinearLayout my_collection_btn;
    private LinearLayout my_interest_btn;
    private LinearLayout setting_btn;
    private LinearLayout share_concern_info;
    private Button user_name;
    private View login_divider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContentView = inflater.inflate(R.layout.fragment_main_mine_layout, container, false);
        return mContentView;
    }

    @Override
    protected void initView() {
        login_btn = (Button) mContentView.findViewById(R.id.register_login_btn);
        msg_remind_btn = (Button) mContentView.findViewById(R.id.main_fragment_msg_icon);
        login_divider = (View) mContentView.findViewById(R.id.login_divider);
        share_concern_info = (LinearLayout) mContentView.findViewById(R.id.share_concern_info_ll);
        setting_btn = (LinearLayout) mContentView.findViewById(R.id.my_setting);
        my_collection_btn = (LinearLayout) mContentView.findViewById(R.id.my_collection);


        my_interest_btn = (LinearLayout) mContentView.findViewById(R.id.my_interest_tab);
        user_head = (Button) mContentView.findViewById(R.id.user_head_btn);
        user_name = (Button) mContentView.findViewById(R.id.user_name_btn);

    }

    @Override
    protected void initData() {
        isLogin = DesktopActivity.isLogin;
        username = DesktopActivity.username;
        if(isLogin) {
            login_btn.setVisibility(View.GONE);
            user_head.setVisibility(View.VISIBLE);
            user_name.setVisibility(View.VISIBLE);
            login_divider.setVisibility(View.VISIBLE);
            share_concern_info.setVisibility(View.VISIBLE);

            user_name.setText(username);
        } else {
            login_btn.setVisibility(View.VISIBLE);
            user_head.setVisibility(View.GONE);
            user_name.setVisibility(View.GONE);
            login_divider.setVisibility(View.GONE);
            share_concern_info.setVisibility(View.GONE);

            user_name.setText(username_unlogin);

        }
    }

    @Override
    protected void initListener() {
        login_btn.setOnClickListener(this);
        msg_remind_btn.setOnClickListener(this);
        my_collection_btn.setOnClickListener(this);
        my_interest_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.register_login_btn:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.my_setting:
                Intent intent1 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent1);
                break;
        }

    }
}
