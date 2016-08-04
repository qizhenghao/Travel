package com.bruce.travel.travels.adapter;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bruce.travel.base.BaseActivity;
import com.bruce.travel.universal.utils.Methods;

import java.util.List;

/**
 * Created by qizhenghao on 16/8/4.
 */
public class TravelsAdapter extends BaseAdapter {

    private Activity mContext;
    private List<String> list;

    public TravelsAdapter(Activity activity, List<String> list) {
        this.mContext = activity;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            holder.textView = new TextView(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Methods.computePixelsWithDensity(30));
            holder.textView.setLayoutParams(params);
            holder.textView.setGravity(Gravity.CENTER);
            convertView = holder.textView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText("第" + position + "条");
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
