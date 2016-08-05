package com.bruce.travel.travels.view;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.travels.adapter.HeaderAdAdapter;
import com.bruce.travel.universal.image.ImageManager;
import com.bruce.travel.universal.utils.Methods;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HeaderAdViewView extends HeaderViewInterface<List<String>> {

    @Bind(R.id.vp_ad)
    ViewPager vpAd;
    @Bind(R.id.ll_index_container)
    LinearLayout llIndexContainer;

    private static final int TYPE_CHANGE_AD = 0;
    private Thread mThread;
    private List<ImageView> ivList;
    private boolean isStopThread = false;
    private ImageManager mImageManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TYPE_CHANGE_AD) {
                vpAd.setCurrentItem(vpAd.getCurrentItem() + 1);
            }
        }
    };
    private View rootView;

    public HeaderAdViewView(Activity context) {
        super(context);
        ivList = new ArrayList<>();
        mImageManager = new ImageManager(context);
    }

    private void initViews() {
        ViewGroup.LayoutParams vpParams = vpAd.getLayoutParams();
        vpParams.height = Methods.getScreenWidth()*8/16;
    }

    @Override
    protected void getView(List<String> list, ListView listView) {
        rootView = mInflate.inflate(R.layout.fragment_travels_header_vp_layout, listView, false);
        ButterKnife.bind(this, rootView);
        initViews();
        dealWithTheView(list);
        listView.addHeaderView(rootView);
    }

    private void dealWithTheView(List<String> list) {
        ivList.clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ivList.add(createImageView(list.get(i)));
        }

        HeaderAdAdapter photoAdapter = new HeaderAdAdapter(mContext, ivList);
        vpAd.setAdapter(photoAdapter);

        addIndicatorImageViews(size);
        setViewPagerChangeListener(size);
        startADRotate();
    }

    // 创建要显示的ImageView
    private ImageView createImageView(String url) {
        ImageView imageView = new ImageView(mContext);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageManager.loadUrlImage(url, imageView);
        return imageView;
    }

    // 添加指示图标
    private void addIndicatorImageViews(int size) {
        llIndexContainer.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Methods.computePixelsWithDensity(5), Methods.computePixelsWithDensity(5));
            if (i != 0) {
                lp.leftMargin = Methods.computePixelsWithDensity(7);
            }
            iv.setLayoutParams(lp);
            iv.setBackgroundResource(R.drawable.common_round_blue_grey_selector);
            iv.setEnabled(false);
            if (i == 0) {
                iv.setEnabled(true);
            }
            llIndexContainer.addView(iv);
        }
    }

    // 为ViewPager设置监听器
    private void setViewPagerChangeListener(final int size) {
        vpAd.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (ivList != null && ivList.size() > 0) {
                    int newPosition = position % size;
                    for (int i = 0; i < size; i++) {
                        llIndexContainer.getChildAt(i).setEnabled(false);
                        if (i == newPosition) {
                            llIndexContainer.getChildAt(i).setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    // 启动循环广告的线程
    private void startADRotate() {
        // 一个广告的时候不用转
        if (ivList == null || ivList.size() <= 1) {
            return;
        }
        if (mThread == null) {
            mThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // 当没离开该页面时一直转
                    while (!isStopThread) {
                        // 每隔5秒转一次
                        SystemClock.sleep(5000);
                        // 在主线程更新界面
                        mHandler.sendEmptyMessage(TYPE_CHANGE_AD);
                    }
                }
            });
            mThread.start();
        }
    }

    // 停止循环广告的线程，清空消息队列
    public void stopADRotate() {
        isStopThread = true;
        if (mHandler != null && mHandler.hasMessages(TYPE_CHANGE_AD)) {
            mHandler.removeMessages(TYPE_CHANGE_AD);
        }
    }

}
