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

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;

/**
 * Created by 梦亚 on 2016/8/2.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    public boolean isLogin = false;
    private Button login_btn;
    private Button msg_remind_btn;
    private LinearLayout my_collection_btn;
    private LinearLayout my_interest_btn;
    private LinearLayout setting_btn;
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
        my_collection_btn = (LinearLayout) mContentView.findViewById(R.id.my_collection);
        my_interest_btn = (LinearLayout) mContentView.findViewById(R.id.my_interest_tab);
        setting_btn = (LinearLayout) mContentView.findViewById(R.id.my_setting);
    }

    @Override
    protected void initData() {

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
        }

    }
}
