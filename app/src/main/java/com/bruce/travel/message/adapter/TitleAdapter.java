package com.bruce.travel.message.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.message.model.DestinationInfo;
import com.bruce.travel.universal.utils.Methods;

import java.util.List;

public class TitleAdapter extends BaseAdapter {

    private Context mContext;

    private List<DestinationInfo> destinationInfos;
    private int mSelectedGroupItem = 0;

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
            holder.tv = new TextView(mContext);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Methods.computePixelsWithDensity(36));
            holder.tv.setLayoutParams(params);
            holder.tv.setSingleLine(true);
            holder.tv.setEllipsize(TextUtils.TruncateAt.END);
            holder.tv.setGravity(Gravity.CENTER);
            holder.tv.setTextSize(R.dimen.fontsize_14px);
            convertView = holder.tv;
            convertView.setTag(holder);
        } else {
            holder = (GroupItemHolder) convertView.getTag();
        }
        if (position == mSelectedGroupItem) {
            holder.tv.setTextColor(Color.WHITE);
            holder.tv.setBackgroundResource(R.color.blue_light);
        } else {
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.font_black_28));
            holder.tv.setBackgroundResource(0);
        }
        holder.tv.setText(info.name);
        return convertView;
    }

    private static class GroupItemHolder {
        TextView tv;
    }
}
