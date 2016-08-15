package com.bruce.travel.universal.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.bruce.travel.base.TravelApplication;

import java.util.UUID;

/**
 * Created by qizhenghao on 16/8/15.
 */
public class AppUtil {

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public static String getAppId() {
        if (TextUtils.isEmpty(Variables.uniqueID)) {
            Variables.uniqueID = UUID.randomUUID().toString();
        }
        return Variables.uniqueID;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public static PackageInfo getPackageInfo() {
        if (Variables.packageInfo == null) {
            try {
                Variables.packageInfo = TravelApplication.getContext().getPackageManager().getPackageInfo(TravelApplication.getContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace(System.err);
            }
            if (Variables.packageInfo == null)
                Variables.packageInfo = new PackageInfo();
        }
        return Variables.packageInfo;
    }
}
