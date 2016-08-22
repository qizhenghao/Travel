package com.bruce.travel.finds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.finds.model.FindsInfo;

import java.util.List;

/**
 * Created by 梦亚 on 2016/8/20.
 */
public class FindsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<FindsInfo> findsInfo;

    public FindsListAdapter(Context context, List<FindsInfo> findsInfo) {
        this.mContext = context;
        this.findsInfo = findsInfo;
    }
    @Override
    public int getCount() {
        return findsInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return findsInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder  = null;
        FindsInfo infos = findsInfo.get(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.finds_llistview_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title_tv);
            holder.location = (TextView) convertView.findViewById(R.id.location_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(infos.getTitle());
        holder.location.setText(infos.getLocation());
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView location;
        ImageView icon;

    }
}
