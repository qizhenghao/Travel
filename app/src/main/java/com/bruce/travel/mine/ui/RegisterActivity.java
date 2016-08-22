package com.bruce.travel.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bruce.travel.R;
import com.bruce.travel.db.MyDbHelper;
import com.bruce.travel.desktop.ui.DesktopActivity;

/**
 * Created by 梦亚 on 2016/8/7.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button return_btn;
    private EditText user_name_et, phone_et, password_et;
    private Button register_btn;
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



    }

    @Override
    public void onClick(View v) {
        db = new MyDbHelper(this);
        switch(v.getId()) {
            case R.id.register_btn:
                user_name = user_name_et.getText().toString();
                phone = phone_et.getText().toString();
                password = password_et.getText().toString();

                if (TextUtils.isEmpty(user_name)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_user_empty), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_password_empty), Toast.LENGTH_LONG).show();
                    return;
                }
                db.insert(user_name, phone, password);
                Intent intent = new Intent(RegisterActivity.this, DesktopActivity.class);
                intent.putExtra("loginState",true);
                intent.putExtra("username",user_name);
                startActivity(intent);
                finish();
                break;
            case R.id.return_btn:
                this.finish();
                break;
        }
    }
}
