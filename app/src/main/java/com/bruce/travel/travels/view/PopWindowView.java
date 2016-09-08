package com.bruce.travel.travels.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bruce.travel.R;

/**
 * Created by 梦亚 on 2016/9/2.
 */
public class PopWindowView extends PopupWindow {

    public PopWindowView(final Activity context, int resId, final OnItemClickListener onItemClickListener) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(resId, null);

        this.setContentView(contentView);
        this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);

        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
//        ColorDrawable dw = new ColorDrawable(0000000000);
//
//        this.setBackgroundDrawable(dw);

        this.setAnimationStyle(R.style.AnimationPreview);

        RelativeLayout[] relativeLayouts = new RelativeLayout[]{
                (RelativeLayout) contentView.findViewById(R.id.popwindow_item1_rl),
                (RelativeLayout) contentView.findViewById(R.id.popwindow_item2_rl)};

        for (int i = 0; i < relativeLayouts.length; i++) {
            final int finalI = i;
            relativeLayouts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopWindowView.this.dismiss();
                    onItemClickListener.onClick(finalI);
                }
            });
        }
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }
    //点击事件回调
    public interface OnItemClickListener {
        void onClick(int position);
    }
}
