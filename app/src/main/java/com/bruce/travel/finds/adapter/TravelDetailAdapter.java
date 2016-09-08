package com.bruce.travel.finds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.finds.model.TravelDetailInfo;
import com.bruce.travel.finds.model.TravelNotesInfo;
import com.bruce.travel.travels.adapter.BaseListAdapter;

import java.util.List;

/**
 * Created by 梦亚 on 2016/9/7.
 */
public class TravelDetailAdapter extends BaseListAdapter<TravelDetailInfo> {

    public TravelDetailAdapter(Context context) {
        super(context);
    }

    public TravelDetailAdapter(Context context, List<TravelDetailInfo> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder  = null;

        TravelDetailInfo infos = getItem(position);
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.travel_detail_llistview_item, null);
            holder.content = (TextView) convertView.findViewById(R.id.detail_content_tv);
//            holder.photo = (ImageView) convertView.findViewById(R.id.record_bg_iv);
//            holder.video = (ImageView) convertView.findViewById(R.id.title_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.content.setText(infos.getContent());
//        holder.photo.setBackgroundResource(infos.getPhoto());
//        holder.video.setBackgroundResource(infos.getVideo());
        return convertView;
    }

    class ViewHolder {
        TextView content;
        ImageView photo;
        ImageView video;

    }
}
