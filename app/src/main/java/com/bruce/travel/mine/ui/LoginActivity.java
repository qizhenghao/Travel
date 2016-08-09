package com.bruce.travel.mine.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bruce.travel.R;
import com.bruce.travel.db.MyDbHelper;
import com.bruce.travel.mine.data.UserInfo;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.*;

/**
 * Created by 梦亚 on 2016/8/4.
 */
public class LoginActivity extends Activity implements OnClickListener {

    private RadioButton account_login_btn, dynamic_login_btn;
    private EditText account_login_name_et, account_lognin_password_et;
    private EditText dynamic_login_number_et, dynamic_login_password_et;
    private Button send_dynamic_password_btn;
    private Button register_new_account_btn, find_password_btn;
    private Button close_btn, login_btn;
    private LinearLayout account_login_ll, dynamic_login_ll;
    private RadioGroup login_rg;

    public static MyDbHelper db;
    SharedPreferences sp;
    private boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        initViews();
        initListeners();

    }

    private void initViews() {
        login_rg = (RadioGroup) findViewById(R.id.login_rg);
        close_btn = (Button) findViewById(R.id.close_btn);
        account_login_btn = (RadioButton) findViewById(R.id.account_login_radio_btn);
        dynamic_login_btn = (RadioButton) findViewById(R.id.dynamic_login_radio_btn);

        account_login_name_et = (EditText) findViewById(R.id.account_login_name_et);
        account_lognin_password_et = (EditText) findViewById(R.id.account_login_password_et);
        dynamic_login_number_et = (EditText) findViewById(R.id.dynamic_login_phone_number_et);
        dynamic_login_password_et = (EditText) findViewById(R.id.dynamic_login_password_et);
        send_dynamic_password_btn = (Button) findViewById(R.id.send_dynamic_password_btn);

        register_new_account_btn = (Button) findViewById(R.id.register_new_account_btn);
        find_password_btn = (Button) findViewById(R.id.find_password_btn);
        login_btn = (Button) findViewById(R.id.login_btn);

        account_login_ll = (LinearLayout) findViewById(R.id.account_login_layout);
        dynamic_login_ll = (LinearLayout) findViewById(R.id.dynamic_login_layout);


    }

    private void initListeners() {
        account_login_ll.setOnClickListener(this);
        dynamic_login_ll.setOnClickListener(this);

        account_login_btn.setOnClickListener(this);
        dynamic_login_ll.setOnClickListener(this);
        send_dynamic_password_btn.setOnClickListener(this);
        register_new_account_btn.setOnClickListener(this);
        find_password_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);

        login_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.account_login_radio_btn:
                        account_login_ll.setVisibility(View.VISIBLE);
                        dynamic_login_ll.setVisibility(View.GONE);
                        break;
                    case R.id.dynamic_login_radio_btn:
                        account_login_ll.setVisibility(View.GONE);
                        dynamic_login_ll.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        db = new MyDbHelper(this);
//        sp = this.getSharedPreferences("userInfo", LoginActivity.MODE_WORLD_READABLE);


        switch(v.getId()) {
            case R.id.close_btn:
                this.finish();
                break;
            case R.id.login_btn:

                String account_login_name = account_login_name_et.getText().toString();
                String account_login_password = account_lognin_password_et.getText().toString();
                String dynamic_login_number = dynamic_login_number_et.getText().toString();
                String dynamic_login_password = dynamic_login_number_et.getText().toString();

                if(TextUtils.isEmpty(account_login_name)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_user_empty), Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(account_login_password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_password_empty), Toast.LENGTH_LONG).show();
                    return;
                }


                Cursor cursor = db.select();
                cursor.moveToFirst();
                while(cursor.moveToNext()) {
                    if(cursor.getString(cursor.getColumnIndex("username")).equals(account_login_name)){
                        if(cursor.getString(cursor.getColumnIndex("password")).equals(account_login_password)){
//                            记住用户名、密码
//                            SharedPreferences.Editor editor = sp.edit();
//                            editor.putString("USER_NAME", account_login_name);
//                            editor.putString("PASSWORD", account_login_password);
//                            editor.commit();
                            flag = true;

                        }
                    }
                }
                if(!flag){
                    Toast.makeText(getApplicationContext(), "用户名密码不正确", Toast.LENGTH_SHORT).show();
                }else{
//                    Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
//                    startActivity(intent1);
                    Toast.makeText(getApplicationContext(), "登陆成功,正在获取用户数据...", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.send_dynamic_password_btn:
                break;
            case R.id.register_new_account_btn:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }



}
