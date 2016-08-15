package com.bruce.travel.universal.http;


import com.bruce.travel.universal.utils.AppUtil;
import com.bruce.travel.universal.utils.Methods;

public class ApiClientHelper {
	
	/**
	 * 获得请求的服务端数据的userAgent
	 * @return
	 */
	public static String getUserAgent() {
		StringBuilder ua = new StringBuilder("OSChina.NET");
		ua.append('/' + AppUtil.getPackageInfo().versionName + '_'
				+  AppUtil.getPackageInfo().versionCode);// app版本信息
		ua.append("/Android");// 手机系统平台
		ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
		ua.append("/" + android.os.Build.MODEL); // 手机型号
		ua.append("/" + AppUtil.getAppId());// 客户端唯一标识
		return ua.toString();
	}
}
