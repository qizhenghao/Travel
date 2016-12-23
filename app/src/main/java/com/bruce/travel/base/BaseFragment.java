package com.bruce.travel.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bruce.travel.R;

/**
 * @author qizhenghao
 *         <p/>
 *         data: 2014年12月27日10:55:40
 * @description 基本Fragment，其他Fragment要继承
 */

public abstract class BaseFragment extends Fragment {

    public Context mContext;
    public Activity mActivity;
    public View mContentView;
    private View progressBarLayout;
    private ViewGroup progressContainer;
    private View mEmptyView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 全部初始化，包括view、data、listener
     */
    public void init() {
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化view，activity创建时调用
     */
    abstract protected void initView();

    /**
     * 初始化data，activity创建时调用，在initView方法之后调用
     */
    abstract protected void initData();

    /**
     * 设置监听事件，activity创建时调用，在initView方法之后调用
     */
    abstract protected void initListener();

    public <T> T findView(int id) {
        return (T) mContentView.findViewById(id);
    }

    /**
     * 刷新，在需要刷新fragment的时候自行调用
     */
    public abstract void refresh();

    public void initEmptyView(ViewGroup emptyViewContainer, View emptyView) {
        this.mEmptyView = emptyView;
        if (emptyViewContainer instanceof RelativeLayout) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            emptyViewContainer.addView(mEmptyView, params);
        } else if (emptyViewContainer instanceof FrameLayout) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            emptyViewContainer.addView(mEmptyView, params);
        } else {
            emptyViewContainer.addView(mEmptyView);
        }
        emptyView.setVisibility(View.GONE);
    }

    public void showEmptyView() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mEmptyView != null)
                    mEmptyView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void dismissEmptyView() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mEmptyView != null)
                    mEmptyView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = activity;
    }

    /**
     * 初始化progressBar，可选择是否的阻塞container的点击事件
     *
     * @param container
     * @param catchFocus 是否阻塞container的点击事件
     */
    public void initProgressBar(ViewGroup container, boolean catchFocus) {
        progressBarLayout = LayoutInflater.from(getActivity().getApplication()).
                inflate(R.layout.load_progressbar_layout, container, false);// 得到progressBar的view
        if (catchFocus) {
            progressBarLayout.setFocusable(true);
            progressBarLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            progressBarLayout.requestFocus();
        }
        progressBarLayout.setOnKeyListener(new View.OnKeyListener() { // 给这个view加一个返回键的监听
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i) {
                    case KeyEvent.KEYCODE_BACK:
                        if (isProgressBarShow()) {
                            // 这句话会导致http连接池里所有的连接都被取消
                            //HttpProviderWrapper.getInstance().stop();
                            dismissProgressBar();
                            return true;
                        }
                        return false;
                }
                return false;
            }
        });
        progressBarLayout.setVisibility(View.GONE);
        this.progressContainer = container;
        container.addView(progressBarLayout);
    }

    /**
     * 显示progressBar
     */
    public void showProgressBar() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (progressBarLayout != null)
                    progressBarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 关闭progressBar
     */
    public void dismissProgressBar() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressBarLayout != null) {
                    progressBarLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 判断progressbar是否已经显示，一般在dismissProgressBar的方法前调用 xiaoguang.zhang
     */
    final public boolean isProgressBarShow() {
        boolean tag = false;
        if (isInitProgressBar()) {
            tag = progressBarLayout.getVisibility() == View.VISIBLE;
        }
        return tag;
    }


    /**
     * 判断是否已经初始化了progressBar，一般在showProgressBar前调用
     */
    public boolean isInitProgressBar() {
        return (progressBarLayout != null)
                && progressContainer.findViewById(R.id.load_progressbar) != null;
    }

    /**
     * 移除progressBar，在onDestory中调用
     */
    public void deleteProgressBar() {
        if (isInitProgressBar()) {
            dismissProgressBar();
            progressContainer.removeView(progressBarLayout);
        }
        progressBarLayout = null;
    }

}
