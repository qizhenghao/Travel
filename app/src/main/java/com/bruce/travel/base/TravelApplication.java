package com.bruce.travel.base;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by qizhenghao on 16/6/23.
 */
public class TravelApplication extends Application {

    private static TravelApplication mContext;
    private static Handler mApplicationHandler;


    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
    }

    public static TravelApplication getContext() {
        return mContext;
    }

    public static Handler getApplicationHandler() {
        if (mApplicationHandler == null) {
            mApplicationHandler = new Handler(Looper.getMainLooper());
        }
        return mApplicationHandler;
    }
}
