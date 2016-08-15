package com.bruce.travel.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.message.model.DestinationInfo;

import java.util.List;

public class DetailAdapter extends BaseAdapter {

    private Context mContext;

    private List<DestinationInfo> destinationInfos;
    private int mSelectedItem = 0;

    public DetailAdapter(Context context, List<DestinationInfo> infos) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_destination_detail_list_item, null);
            holder.nameTv = (TextView) convertView.findViewById(R.id.detail_list_item_name_tv);
            holder.contentTv = (TextView) convertView.findViewById(R.id.detail_list_item_content_tv);
            convertView.setTag(holder);
        } else {
            holder = (GroupItemHolder) convertView.getTag();
        }
//        if (position == mSelectedItem) {
//            holder.contentTv.setTextColor(Color.WHITE);
//            holder.contentTv.setBackgroundResource(R.color.blue_light);
//        } else {
//            holder.contentTv.setTextColor(mContext.getResources().getColor(R.color.font_black_28));
//            holder.contentTv.setBackgroundResource(0);
//        }
        holder.nameTv.setText(info.name);
        holder.contentTv.setText(info.content);
        return convertView;
    }

    public void setSelectedItem(int selectedItem) {
        this.mSelectedItem = selectedItem;
    }

    private static class GroupItemHolder {
        TextView nameTv;
        TextView contentTv;
    }
}
