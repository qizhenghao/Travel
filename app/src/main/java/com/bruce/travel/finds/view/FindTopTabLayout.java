package com.bruce.travel.finds.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梦亚 on 2016/8/18.
 */
public class FindTopTabLayout extends RelativeLayout implements ViewPager.OnPageChangeListener{

    private final String TAG = "FindFragment";
    private static final int TAB_COUNT = 2;
    private int[] mViewIds;
    private LineViewPagerIndicator mLineIndicator;
    private Activity mParentActivity;
    private List<TextView> mTextItems;

    public interface TabType {
        int LATEST = 0;
        int RECOMMEND = 1;
    }

    public FindTopTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewIds(int[] ids) {
        if (ids != null && ids.length > 1) {
            mViewIds = ids;
            initViews();
        } else {
            Log.e(TAG, "error in setViewIds(): the given \"ids\" is invalid!!!");
        }
    }

    public void setViewPager(ViewPager pager) {
        mLineIndicator.setViewPager(pager);
        mLineIndicator.setOnPageChangeListener(this);
    }

    public void setParentActivty(Activity parentActivity) {
        mParentActivity = parentActivity;
    }

    private void initViews() {
        if (mViewIds != null && mViewIds.length > 1) {
            mTextItems = new ArrayList<>(mViewIds.length - 1);
            mLineIndicator = (LineViewPagerIndicator) findViewById(mViewIds[0]);
            for (int i = 1; i < mViewIds.length; i++) {
                mTextItems.add((TextView) findViewById(mViewIds[i]));
            }
        } else {
            Log.e(TAG, "error in initViews(): mViewIds is invalid!!!");
        }
        bindListener();
    }


    private void bindListener() {
        if (mTextItems != null && mTextItems.size() > 0) {
            for (int i = 0; i < mTextItems.size(); i++) {
                final int currTab = i;
                mTextItems.get(currTab).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View dv) {
                        if (mLineIndicator.getCurrentPage() == currTab) {
                            onTabClicked(currTab);
                        }
                        mLineIndicator.setCurrentItem(currTab);
                    }
                });
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int index) {
        for (int i=0;i<mTextItems.size();i++)
            mTextItems.get(i).setSelected(i==index);
    }

    private void onTabClicked(int index) {

    }

    public void setCurrentItem(int position) {
        mLineIndicator.setCurrentItem(position);
        onPageSelected(position);
    }


}
