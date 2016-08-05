package com.bruce.travel.travels.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.bruce.travel.universal.view.ScrollOverListView;

/**
 * Created by qizhenghao on 16/8/4.
 */
public class PinnedHeaderListView extends ScrollOverListView {

    private String TAG = "PinnedHeaderListView";
    private View headerTabView;
    private int headerTabIndex = -1;
    private int mWidthMode;
    private float mHeaderOffset = 0.0f;

    public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinnedHeaderListView(Context context) {
        super(context);
    }

    public void addHeaderTabView(View v) {
        headerTabView = v;
        addHeaderView(v, null, true);
        headerTabIndex = getHeaderViewsCount() - 1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
//        if (firstVisibleItem > headerTabIndex) {
//            pinnedHeaderTabView();
//        }
    }

    private void pinnedHeaderTabView() {
        headerTabView.setVisibility(VISIBLE);
        ensurePinnedHeaderLayout(headerTabView);
        invalidate();
    }

    private void ensurePinnedHeaderLayout(View header) {
        if (header != null) {
            int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), mWidthMode);

            int heightSpec;
            ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
            if (layoutParams != null && layoutParams.height > 0) {
                heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
            } else {
                heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            }
            header.measure(widthSpec, heightSpec);
            Log.d(TAG, "ensurePinnedHeaderLayout");
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
        }
    }

//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        if (getAdapter() == null || headerTabView == null || getFirstItemIndex() < headerTabIndex)
//            return;
//        int saveCount = canvas.save();
//        canvas.translate(0, mHeaderOffset);
//        canvas.clipRect(0, 0, getWidth(), headerTabView.getMeasuredHeight());
//        headerTabView.draw(canvas);
//        canvas.restoreToCount(saveCount);
//    }


}
