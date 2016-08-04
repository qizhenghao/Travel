package com.bruce.travel.universal.utils;

import android.widget.Toast;

import com.bruce.travel.universal.base.TravelApplication;

/**
 * Created by qizhenghao on 16/7/6.
 */
public class Variables {

    public static final float density = TravelApplication.getContext().getResources().getDisplayMetrics().density;

    public static Toast gToast;
}
