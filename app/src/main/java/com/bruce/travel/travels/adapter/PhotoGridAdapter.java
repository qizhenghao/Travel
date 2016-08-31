package com.bruce.travel.travels.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bruce.travel.R;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梦亚 on 2016/8/26.
 */
public class PhotoGridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private List<Uri> list;

    public PhotoGridAdapter(Context context) {
        this.mContext = context;
    }

    public PhotoGridAdapter(Context context, List<Uri> list) {
        this.mContext = context;
        this. list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<Uri> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        int total = list.size();
        if (total < 9)
            total++;
        return total;
    }

    @Override
    public Uri getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.photo_select_gridview_item_layout, null);
        SimpleDraweeView sdv_image = (SimpleDraweeView) convertView.findViewById(R.id.sdv_image);
        if (position == list.size() && list.size() < 9) {
            GenericDraweeHierarchy hierarchy = sdv_image.getHierarchy();
            hierarchy.setPlaceholderImage(R.drawable.record_icon_photo_add);
        } else {

            Uri uri_temp = getItem(position);
            sdv_image.setImageURI(uri_temp);
        }
        return convertView;
    }
}
