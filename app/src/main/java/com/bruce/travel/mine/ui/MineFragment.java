package com.bruce.travel.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.desktop.ui.DesktopActivity;
import com.bruce.travel.universal.utils.ImageUtils;

/**
 * Created by 梦亚 on 2016/8/2.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private static final int RESULT_CANCELED = 0;

    private boolean isLogin;
    private String username = null;
    private String username_unlogin = "用户名";
    private Button login_btn;
    private ImageButton user_head;
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
        user_head = (ImageButton) mContentView.findViewById(R.id.user_head_btn);
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
        user_head.setOnClickListener(this);
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
            case R.id.user_head_btn:
                ImageUtils.showImagePickDialog(getActivity());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ImageUtils.REQUEST_CODE_FROM_ALBUM: {

                if (resultCode == RESULT_CANCELED) {   //取消操作
                    return;
                }

                Uri imageUri = data.getData();
                ImageUtils.copyImageUri(getActivity(),imageUri);
                ImageUtils.cropImageUri(getActivity(), ImageUtils.getCurrentUri(), 200, 200);
                break;
            }
            case ImageUtils.REQUEST_CODE_FROM_CAMERA: {

                if (resultCode == RESULT_CANCELED) {     //取消操作
                    ImageUtils.deleteImageUri(getContext(), ImageUtils.getCurrentUri());   //删除Uri
                }

                ImageUtils.cropImageUri(getActivity(), ImageUtils.getCurrentUri(), 200, 200);
                break;
            }
            case ImageUtils.REQUEST_CODE_CROP: {

                if (resultCode == RESULT_CANCELED) {     //取消操作
                    return;
                }

                Uri imageUri = ImageUtils.getCurrentUri();
                if (imageUri != null) {
                    user_head.setImageURI(imageUri);
                }
                break;
            }
            default:
                break;
        }
    }
}
