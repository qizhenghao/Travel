package com.bruce.travel.base;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.bruce.travel.universal.http.ApiHttpClient;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

/**
 * Created by qizhenghao on 16/6/23.
 */
public class TravelApplication extends Application {

    private static TravelApplication mContext;
    private static Handler mApplicationHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        init();
    }

    private void init() {
        // 初始化网络请求
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);

        Fresco.initialize(mContext);

        client.setCookieStore(myCookieStore);
        ApiHttpClient.setHttpClient(client);
        ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));

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
