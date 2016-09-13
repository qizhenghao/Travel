package com.bruce.travel.finds.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.finds.model.TravelNotesInfo;
import com.bruce.travel.travels.adapter.BaseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梦亚 on 2016/8/25.
 */
public class TravelNotesAdapter extends BaseListAdapter<TravelNotesInfo> {

    private boolean isNoData;
    private int mHeight;

    public static final int ONE_SCREEN_COUNT = 5; // 一屏能显示的个数，这个根据屏幕高度和各自的需求定
    public static final int ONE_REQUEST_COUNT = 10; // 一次请求的个数

    private List<TravelNotesInfo> travelNotesInfo;

    public TravelNotesAdapter(Context context, List<TravelNotesInfo> list) {
        super(context, list);
    }

    // 设置数据
    public void setData(List<TravelNotesInfo> list) {
        clearAll();
        addALL(list);

        isNoData = false;
        if (list.size() == 1 && list.get(0).isNoData()) {
            // 暂无数据布局
            isNoData = list.get(0).isNoData();
            mHeight = list.get(0).getHeight();
        } else {
            // 添加空数据
            if (list.size() < ONE_SCREEN_COUNT) {
                addALL(createEmptyList(ONE_SCREEN_COUNT - list.size()));
            }
        }
        notifyDataSetChanged();
    }

    // 创建不满一屏的空数据
    public List<TravelNotesInfo> createEmptyList(int size) {
        List<TravelNotesInfo> emptyList = new ArrayList<>();
        if (size <= 0) return emptyList;
        for (int i=0; i<size; i++) {
            emptyList.add(new TravelNotesInfo());
        }
        return emptyList;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder  = null;

        TravelNotesInfo infos = getItem(position);
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.finds_llistview_item, null);
            holder.record_bg = (RelativeLayout) convertView.findViewById(R.id.record_bg_rl);
            holder.bgIv = (ImageView) convertView.findViewById(R.id.record_bg_iv);
            holder.title = (TextView) convertView.findViewById(R.id.title_tv);
            holder.location = (TextView) convertView.findViewById(R.id.location_tv);
            holder.icon = (ImageView) convertView.findViewById(R.id.user_icon);
            holder.account = (TextView)convertView.findViewById(R.id.user_name_tv);
            holder.date = (TextView) convertView.findViewById(R.id.share_date_tv) ;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(infos.getTitle());
        holder.location.setText(infos.getLocation());
        holder.date.setText(infos.getDate());
        holder.account.setText(infos.getAccount());
//        holder.record_bg.setBackgroundResource(infos.getBg());

        mImageManager.loadRoundedCornerResImage(infos.getBg(), holder.bgIv);
        mImageManager.loadCircleResImage(infos.getIcon(),holder.icon);
        return convertView;
    }

    class ViewHolder {
        RelativeLayout record_bg;
        ImageView bgIv;
        TextView title;
        TextView location;
        ImageView icon;
        TextView account;
        TextView date;


    }



}
