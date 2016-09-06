package com.bruce.travel.travels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.travels.been.FilterTwoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterLeftAdapter extends BaseListAdapter<FilterTwoBean> {

    private FilterTwoBean selectedEntity;

    public FilterLeftAdapter(Context context) {
        super(context);
    }

    public FilterLeftAdapter(Context context, List<FilterTwoBean> list) {
        super(context, list);
    }

    public void setSelectedEntity(FilterTwoBean filterEntity) {
        this.selectedEntity = filterEntity;
        for (FilterTwoBean entity : getData()) {
            entity.setSelected(entity.getType().equals(selectedEntity.getType()));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_travels_header_filter_item_left, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FilterTwoBean entity = getItem(position);

        holder.tvTitle.setText(entity.getType());
        if (entity.isSelected()) {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.blue_light));
            holder.llRootView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_28));
            holder.llRootView.setBackgroundColor(mContext.getResources().getColor(R.color.font_black_6));
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ll_root_view)
        LinearLayout llRootView;
        @Bind(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
