package com.bruce.travel.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bruce.travel.R;

/**
 * Created by 梦亚 on 2016/8/9.
 */
public class CommonListAdapter extends BaseAdapter {

    private Context context;
    private String[] infos;
    public CommonListAdapter(Context context, String[] infos){
        this.context= context;
        this.infos = infos;
    }

    @Override
    public int getCount() {
        return infos.length;
    }

    @Override
    public Object getItem(int position) {
        return infos[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.setting_list_item, null);
        TextView text = (TextView)convertView.findViewById(R.id.setting_list_item_txt);
        text.setText(infos[position]);
        return null;
    }
}
