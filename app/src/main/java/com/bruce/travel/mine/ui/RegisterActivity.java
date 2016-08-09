package com.bruce.travel.mine.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bruce.travel.R;
import com.bruce.travel.db.MyDbHelper;
import com.bruce.travel.mine.data.UserInfo;

/**
 * Created by 梦亚 on 2016/8/7.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button return_btn;
    private EditText user_name_et, phone_et, password_et;
    private Button register_btn;
    private UserInfo user;
    private MyDbHelper db;
    private String user_name, phone, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        initView();
    }

    private void initView() {
        return_btn = (Button) findViewById(R.id.return_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
        user_name_et = (EditText) findViewById(R.id.register_name_et);
        phone_et = (EditText) findViewById(R.id.register_phone_et);
        password_et = (EditText) findViewById(R.id.register_password_et);

        return_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);

        user = new UserInfo();
        db = new MyDbHelper(this);

        user_name = user_name_et.getText().toString();
        phone = phone_et.getText().toString();
        password = password_et.getText().toString();
        user.setUsername(user_name);
        user.setPhone(phone);
        user.setPassword(password);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.register_btn:
//                Cursor cursor = db.select();
//                cursor.moveToFirst();
//                while(cursor.moveToNext()) {
//                    if (cursor.getString(cursor.getColumnIndex("username")).equals(user_name)) {
//                        Toast.makeText(getApplicationContext(), getString(R.string.username_has_existed), Toast.LENGTH_LONG).show();
//                    } else {
//                        db.insert(user_name, phone, password);
//                    }
//                }
                db.insert(user_name, phone, password);
                break;
            case R.id.return_btn:
                this.finish();
                break;
        }
    }
}
