package com.bruce.travel.message.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.message.model.DestinationInfo;

import java.util.List;

public class TitleAdapter extends BaseAdapter {

    private Context mContext;

    private List<DestinationInfo> destinationInfos;
    private int mSelectedItem;

    public TitleAdapter(Context context, List<DestinationInfo> infos) {
        this.mContext = context;
        this.destinationInfos = infos;
    }

    public void changeSelected(int position){ //刷新方法
        if(position != mSelectedItem){
            mSelectedItem = position;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return destinationInfos.size();
    }

    @Override
    public DestinationInfo getItem(int position) {
        return destinationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupItemHolder holder;
        DestinationInfo info = destinationInfos.get(position);
        if (convertView == null) {
            holder = new GroupItemHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_destination_title_list_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.title_list_item_content_tv);
            holder.indicator = convertView.findViewById(R.id.selected_indicator);
            convertView.setTag(holder);
        } else {
            holder = (GroupItemHolder) convertView.getTag();
        }

        if (mSelectedItem == position) {
            holder.tv.setTextColor(Color.BLACK);
            holder.tv.setBackgroundResource(R.color.white);
            holder.indicator.setBackgroundResource(R.color.blue_light);
        } else {
            holder.tv.setTextColor(Color.GRAY);
            holder.tv.setBackgroundResource(R.color.grey_f2);
            holder.indicator.setBackgroundResource(R.color.grey_f2);
        }

        holder.tv.setText(info.getTitle());
        return convertView;
    }

    private static class GroupItemHolder {
        TextView tv;
        View indicator;
    }
}
