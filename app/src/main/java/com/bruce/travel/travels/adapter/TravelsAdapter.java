package com.bruce.travel.travels.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bruce.travel.R;
import com.bruce.travel.travels.been.TravelsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class TravelsAdapter extends BaseListAdapter<TravelsBean> {

    private boolean isNoData;
    private int mHeight;
    public static final int ONE_SCREEN_COUNT = 7; // 一屏能显示的个数，这个根据屏幕高度和各自的需求定
    public static final int ONE_REQUEST_COUNT = 10; // 一次请求的个数

    public TravelsAdapter(Context context) {
        super(context);
    }

    public TravelsAdapter(Context context, List<TravelsBean> list)
    {
        super(context, list);
    }

    // 设置数据
    public void setData(List<TravelsBean> list) {
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
    public List<TravelsBean> createEmptyList(int size) {
        List<TravelsBean> emptyList = new ArrayList<>();
        if (size <= 0) return emptyList;
        for (int i=0; i<size; i++) {
            emptyList.add(new TravelsBean());
        }
        return emptyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 暂无数据
        if (isNoData) {
            convertView = mInflater.inflate(R.layout.fragment_travels_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
            RelativeLayout rootView = ButterKnife.findById(convertView, R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }

        // 正常数据
        final ViewHolder holder;
        if (convertView != null && convertView instanceof LinearLayout) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.fragment_travels_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        TravelsBean entity = getItem(position);

        holder.tvTitle.setText(entity.getTitle());
        holder.tvDate.setText(entity.getDate());
        mImageManager.loadUrlImage(entity.getImage_url(), holder.ivImage);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ll_root_view)
        LinearLayout llRootView;
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_date)
        TextView tvDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
