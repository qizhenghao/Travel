package com.bruce.travel.travels;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 梦亚 on 2016/8/22.
 */
public class NewRecordActivity extends BaseActivity implements View.OnClickListener {

//    @Bind(R.id.local_time_btn)
//    Button local_time_btn;
//    @Bind(R.id.location_btn)
//    Button location_btn;
//    @Bind(R.id.voice_record_btn)
//    Button voice_record_btn;
//    @Bind(R.id.photo_select_btn)
//    Button photo_select_btn;
//    @Bind(R.id.return_desktop_btn)
//    Button return_btn;
//    @Bind(R.id.save_record_btn)
//    Button save_btn;

    private Button aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record_layout);
//        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        aa = (Button)findViewById(R.id.btn);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListeners() {
//        local_time_btn.setOnClickListener(this);
//        location_btn.setOnClickListener(this);
//        voice_record_btn.setOnClickListener(this);
//        photo_select_btn.setOnClickListener(this);
//        return_btn.setOnClickListener(this);
//        save_btn.setOnClickListener(this);
        aa.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn:
                Toast.makeText(getApplicationContext(), "lala", Toast.LENGTH_LONG).show();
                break;
        }
    }


}
