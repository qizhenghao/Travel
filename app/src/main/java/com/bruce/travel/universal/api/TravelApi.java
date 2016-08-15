package com.bruce.travel.universal.api;

import com.bruce.travel.universal.http.ApiHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by qizhenghao on 16/8/15.
 */
public class TravelApi {

    public static void getSoftwareTagList(int searchTag, int page,
                                          AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("searchTag", searchTag);
        params.put("pageIndex", page);
        params.put("pageSize", 50);
        ApiHttpClient.get("action/api/softwaretag_list", params, handler);
    }
}
