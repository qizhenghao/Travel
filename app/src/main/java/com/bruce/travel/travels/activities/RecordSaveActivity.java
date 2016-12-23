package com.bruce.travel.travels.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;
import com.bruce.travel.universal.utils.Methods;

import butterknife.Bind;

/**
 * Created by 梦亚 on 2016/12/20.
 */
public class RecordSaveActivity extends Activity implements View.OnClickListener{


    @Bind(R.id.record_save_confirm_btn)
    Button confirmBtn;
    @Bind(R.id.record_save_cancel_btn)
    Button cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_save_dialog_layout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.record_save_confirm_btn:
                Methods.showToast("确认", false);
                break;
            case R.id.record_save_cancel_btn:
                this.finish();
                break;

        }

    }





}
