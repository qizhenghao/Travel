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
    private int mSelectedItem = 0;

    public TitleAdapter(Context context, List<DestinationInfo> infos) {
        this.mContext = context;
        this.destinationInfos = infos;
    }

    @Override
    public int getCount() {
        return destinationInfos.size();
    }

    @Override
    public Object getItem(int position) {
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
            convertView.setTag(holder);
        } else {
            holder = (GroupItemHolder) convertView.getTag();
        }
        if (position == mSelectedItem) {
            holder.tv.setTextColor(Color.BLACK);
            holder.tv.setBackgroundResource(R.color.white);
        } else {
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.font_black_38));
            holder.tv.setBackgroundResource(0);
        }
        holder.tv.setText(info.name);
        return convertView;
    }

    public void setSelectedItem(int selectedItem) {
        this.mSelectedItem = selectedItem;
    }
    private static class GroupItemHolder {
        TextView tv;
    }
}
