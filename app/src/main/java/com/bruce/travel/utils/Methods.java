package com.bruce.travel.utils;

import android.content.Context;
import android.view.WindowManager;
import android.widget.Toast;

import com.bruce.travel.base.TravelApplication;

/**
 * Created by qizhenghao on 16/7/1.
 */
public class Methods {

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) TravelApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }


    public static int computePixelsWithDensity(int dp) {
        return (int) (dp * Variables.density + 0.5);
    }

    /**
     * 显示Toast
     *
     * @param text
     * @param lengthLong
     */
    public static void showToast(final CharSequence text, final boolean lengthLong) {
        Runnable update = new Runnable() {
            public void run() {
                if (Variables.gToast == null && TravelApplication.getContext() != null) {
                    Variables.gToast = Toast.makeText(TravelApplication.getContext().getApplicationContext(), "",
                            Toast.LENGTH_LONG);
                }
                if (Variables.gToast != null) {
                    if (text == null) {
                        Variables.gToast.cancel();
                    } else {
                        Variables.gToast.setText(text);
                        Variables.gToast.setDuration(lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                        Variables.gToast.show();
                    }
                }
            }
        };
        TravelApplication.getApplicationHandler().post(update);
    }
}
