package com.bruce.travel.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.message.model.DestinationDetailInfo;

import java.util.List;

public class DetailAdapter extends BaseAdapter {

    private Context mContext;

    private List<DestinationDetailInfo> detailInfos;
//    private int mSelectedItem = 0;

    public DetailAdapter(Context context, List<DestinationDetailInfo> infos) {
        this.mContext = context;
        this.detailInfos = infos;
    }

    @Override
    public int getCount() {
        return detailInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return detailInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupItemHolder holder;
        DestinationDetailInfo info = detailInfos.get(position);
        if (convertView == null) {
            holder = new GroupItemHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_destination_detail_list_item, null);
            holder.nameTv = (TextView) convertView.findViewById(R.id.detail_list_item_name_tv);
            convertView.setTag(holder);
        } else {
            holder = (GroupItemHolder) convertView.getTag();
        }

        holder.nameTv.setText(info.getTitle());
        return convertView;
    }


//    public void setSelectedItem(int selectedItem) {
//        this.mSelectedItem = selectedItem;
//    }

    private static class GroupItemHolder {
        TextView nameTv;
    }
}
