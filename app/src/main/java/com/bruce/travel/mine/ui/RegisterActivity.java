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
import com.bruce.travel.desktop.DesktopActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by 梦亚 on 2016/8/7.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button return_btn;
    private EditText user_name_et, phone_et, password_et;
    private Button register_btn;
    private MyDbHelper db;
    private String user_name, phone, password;

    public static HttpClient httpClient = new DefaultHttpClient();
    private String url = "http://10.109.20.201:8080/TravelServer/register.do";

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
//        db = new MyDbHelper(this);
        switch(v.getId()) {
            case R.id.register_btn:

                String user_name = user_name_et.getText().toString();
                String password = password_et.getText().toString();

                if (TextUtils.isEmpty(user_name)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_user_empty), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_password_empty), Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    if (registerPro()) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "该用户名已经存在", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                db.insert(user_name, phone, password);
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

    private boolean registerPro() throws Exception{
        user_name = user_name_et.getText().toString();
        phone = phone_et.getText().toString();
        password = password_et.getText().toString();
        JSONObject json;

        try{
            json = query(user_name, password, phone);
            if(json.getInt("flag") == 1) {
                return true;
            } else if (json.getInt("flag") == -1) {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"服务器异常",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private JSONObject query(String username, String password, String phonenum) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("user", username);
        map.put("pass", password);
        map.put("phone", phonenum);
        return new JSONObject(postRequest(url, map));
    }

    public static String postRequest(final String url, final Map<String ,String> rawParams)throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        // 创建HttpPost对象。
                        HttpPost post = new HttpPost(url);
                        // 如果传递参数个数比较多的话可以对传递的参数进行封装
                        List<NameValuePair> params = new ArrayList<>();
                        for(String key : rawParams.keySet())
                        {
                            //封装请求参数
                            params.add(new BasicNameValuePair(key, rawParams.get(key)));
                        }
                        // 设置请求参数
                        post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                        // 发送POST请求
                        HttpResponse httpResponse = httpClient.execute(post);
                        // 如果服务器成功地返回响应
                        if (httpResponse.getStatusLine().getStatusCode() == 200)
                        {
                            // 获取服务器响应字符串
                            String result = EntityUtils.toString(httpResponse.getEntity());
                            return result;
                        }
                        return null;
                    }
                });
        new Thread(task).start();
        return task.get();
    }
}
