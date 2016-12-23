package com.bruce.travel.finds.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 梦亚 on 2016/9/21.
 */
public class SearchTravelActivity extends BaseActivity {

    @Bind(R.id.dst_search_et)
    EditText search_et;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_travel_layout);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        search_et.setFocusable(true);
    }

    @Override
    protected void initListeners() {

    }
}
