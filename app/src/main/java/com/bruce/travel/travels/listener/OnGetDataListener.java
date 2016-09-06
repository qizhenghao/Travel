package com.bruce.travel.travels.listener;

/**
 * Created by qizhenghao on 16/9/6.
 */
public interface OnGetDataListener<T> {

    void onSuccess(T result);

    void onError(String error);
}
