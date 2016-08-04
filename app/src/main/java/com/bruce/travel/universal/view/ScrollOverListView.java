package com.bruce.travel.universal.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.travel.R;
import com.bruce.travel.universal.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 带下拉刷新、自动加载更多的ListView
 */

public class ScrollOverListView extends ListView implements OnScrollListener {

    private static final String TAG = "ScrollOverListView";

    private long startime;
    private static final int START_PULL_DEVIATION = 50; //     移动误差
    /**
     * 刷新成功后平滑收起的动画时间
     */
    private static final int UPDATE_SUCCESS_ANIMATION_DURATION = 500;// 350
    /**
     * 下拉刷新松手后平滑收起一段距离的动画时间
     */
    private static final int AUTO_FLING_ANIMATION_DURATION = 800;
    private int mLastY;
    private int mBottomPosition;
    /**
     * 松开更新
     **/
    public final static int RELEASE_To_REFRESH = 0;
    /**
     * 下拉更新
     **/
    public final static int PULL_To_REFRESH = 1;
    /**
     * 更新中
     **/
    public final static int REFRESHING = 2;
    /**
     * 无
     **/
    public final static int DONE = 3;
    /**
     * 加载中
     **/
    public final static int LOADING = 4;
    /**
     * 更新失败
     */
    public final static int ERROR = 5;
    /**
     * 自动弹出或下拉松开后自动滑动到刷新位置
     */
    public final static int AUTO_FLING = 6;
    /**
     * 实际的padding的距离与界面上偏移距离的比例
     **/
    private final static int RATIO = 2;
    private LayoutInflater inflater;
    /**
     * 头部刷新的布局
     **/
    private ViewGroup headView;
    /**
     * 用于保证startY的值在一个完整的touch事件中只被记录一次
     **/
    private boolean isRecored;
    /**
     * 头部高度
     **/
    private int headContentHeight;
    /**
     * 开始的Y坐标
     **/
    private int startY;
    /**
     * 第一个item
     **/
    public int firstItemIndex;
    /**
     * 状态
     **/
    public volatile int mCurrentState;

    private boolean isBack;
    /**
     * 是否要使用下拉刷新功能
     **/
    public boolean mIsShowRefreshing = true;
    public boolean mCanRefleash = false;

    private boolean mIsOnInterceptTouchEvent;

    private boolean mIsRefreshable = true;

    /**
     * 刷新和更多的事件接口
     **/
    private OnPullDownListener mOnPullDownListener;
    private float mMotionDownLastY; // 按下时候的Y轴坐标
    public boolean mIsFetchMoreing; // 是否获取更多中
    private boolean mIsPullUpDone; // 是否回推完成
    private boolean mEnableAutoFetchMore; // 是否允许自动获取更多
    /**
     * 底部更多的按键
     **/
    private RelativeLayout mFooterView;
    /**
     * 底部更多的按键
     **/
    private TextView mFooterTextView;
    /**
     * 底部跳转的button
     **/
    private Button mFooterButtonView;
    Boolean mIsNoMoreComments = false;
    /**
     * 底部更多的按键
     **/
    private ProgressBar mFooterLoadingView;

    /**
     * 用来加载EmptyView的FooterView
     */
    private ViewGroup mEmptyFooterView;

    private boolean isDisallowFastMoveToTop = false;

    /**
     * 概念版刷新动画Drawable
     */
    // private AnimationDrawable mRefreshAnimation;

    /**
     * 概念版动画View
     */
    // private ImageView mRefreshView;

    // private int mStepHeight;
    /**
     * 刷新动画帧数
     */
    // private int mFrameCount;

    private int mFooterPadding;

    private AnimationDrawable mPulldownDrawable;

    private AnimationDrawable mAutoFlingAnimDrawable;

    private AnimationDrawable mOnceAnimDrawable;

    private AnimationDrawable mRepeatAnimDrawable;

    private AnimationDrawable mFadeOutAnimDrawable;

    private RotateAnimation mRotateAnim;

    private int mAnimStartHeight;

    private float mAnimStepHeight;

    private int mPullDownFrameCount;

    private int mOnceAnimDuration;

    private ImageView mProgressView;

    private TextView mTipsText;

    private boolean mIsSimpleRepeat;

    private SharedPreferences correctThemeInfo;
    private String correctthemepackage;
    /**
     * 底部的加载更多是否使用border,默认是使用
     */
    private boolean loadMoreUseBorder = true;

    public void setRefreshable(boolean flag) {
        mIsRefreshable = flag;
    }

    /**
     * 是否开启滑动到顶部时自动refresh
     */
    private boolean mAutoRefresh = false;

    /**
     * 自定义滑动到顶部自动刷新所用的progressBar
     */
    private ProgressBar autoRefreshProgress;

    private boolean useSimpleProgress = false;

    public boolean refreshExecuted = false;//是否已执行自动refresh,防止多次执行

    /**
     * 刷新和获取更多事件接口
     */
    public interface OnPullDownListener {
        /**
         * 刷新事件接口 这里要注意的是获取更多完 要关闭 刷新的进度条RefreshComplete()
         **/
        void onRefresh();

        /**
         * 刷新事件接口 这里要注意的是获取更多完 要关闭 更多的进度条 notifyDidMore()
         **/
        void onMore();
    }

    private Context mContext; //用于保存主工程的上下文环境。

    public ScrollOverListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context.getApplicationContext();
        init(attrs, mContext);
    }

    public ScrollOverListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context.getApplicationContext();
        init(attrs, mContext);
    }

    public ScrollOverListView(Context context) {
        super(context);
        mContext = context.getApplicationContext();
        init(mContext);
    }


    private void initPullDownAnim() {

        try {

            mPulldownDrawable = (AnimationDrawable) getResources()
                    .getDrawable(R.drawable.vc_0_0_1_newsfeed_loading_anim_pull);
            mAutoFlingAnimDrawable = (AnimationDrawable) getResources()
                    .getDrawable(R.drawable.vc_0_0_1_newsfeed_loading_anim_auto_fling);
            mOnceAnimDrawable = (AnimationDrawable) getResources()
                    .getDrawable(R.drawable.vc_0_0_1_newsfeed_loading_anim_auto_fling);
            mRepeatAnimDrawable = (AnimationDrawable) getResources()
                    .getDrawable(R.drawable.vc_0_0_1_newsfeed_loading_anim_repeat);
            mFadeOutAnimDrawable = (AnimationDrawable) getResources()
                    .getDrawable(R.drawable.vc_0_0_1_newsfeed_loading_anim_fade_out);

            headView.setBackgroundColor(getResources().getColor(R.color.vc_0_0_1_newsfeed_pull_down_bg));

            if (mRepeatAnimDrawable.getNumberOfFrames() == 1) {
                mRotateAnim = (RotateAnimation) AnimationUtils.loadAnimation(
                        mContext, R.anim.vc_0_0_1_newsfeed_loading_rotate);
                mIsSimpleRepeat = true;
            }

            mProgressView.setImageDrawable(mPulldownDrawable.getFrame(0));

            mPullDownFrameCount = mPulldownDrawable.getNumberOfFrames();

            //mAnimStartHeight = (int) (headContentHeight * 0.6f + 0.5);
            mAnimStartHeight = headContentHeight;

		/*mAnimStepHeight = 1.0f * (headContentHeight - mAnimStartHeight)
                / (mPullDownFrameCount - 1);*/
            mAnimStepHeight = 1.0f * (headContentHeight * 0.4f)
                    / (mPullDownFrameCount - 1);

            int onceAnimFrameCount = mOnceAnimDrawable.getNumberOfFrames();
            mOnceAnimDuration = 0;
            for (int i = 0; i < onceAnimFrameCount; i++) {
                mOnceAnimDuration += mOnceAnimDrawable.getDuration(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startRepeatAnim(int state) {
        if (state == REFRESHING) {
            if (mIsSimpleRepeat) {
                mProgressView.setImageDrawable(mRepeatAnimDrawable.getFrame(0));
                mProgressView.startAnimation(mRotateAnim);
            } else {
                mProgressView.setImageDrawable(mRepeatAnimDrawable);
                mRepeatAnimDrawable.stop();
                mRepeatAnimDrawable.start();
            }
        }
    }

    /**
     * 重载初始化函数,添加自定义属性
     *
     * @param attrs
     * @param context
     * @author jason
     */
    private void init(AttributeSet attrs, Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollOverListFooter);
        loadMoreUseBorder = a.getBoolean(R.styleable.ScrollOverListFooter_use_border, true);
        a.recycle();
        init(context);
    }

    /**
     * 出事化控件
     **/
    private void init(Context context) {
        mFooterPadding = 0;
        mBottomPosition = 0;
        setCacheColorHint(0);
        // 下面三个属性非常重要，去除后会引起滑动新鲜事列表卡顿
        setFadingEdgeLength(0);
        setVerticalFadingEdgeEnabled(false);
        setHorizontalFadingEdgeEnabled(false);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        /**
         * @author meng
         * @description 注释掉此句，防止滚动条弹
         */
        // setSmoothScrollbarEnabled(false);
        setScrollingCacheEnabled(false);
        inflater = LayoutInflater.from(context);
        headView = (ViewGroup) inflater.inflate(
                R.layout.vc_0_0_1_newsfeed_pull_down_head, null);

        mProgressView = (ImageView) headView
                .findViewById(R.id.vc_0_0_1_newsfeed_refresh_progress);
        mTipsText = (TextView) headView
                .findViewById(R.id.vc_0_0_1_newsfeed_tips);
        autoRefreshProgress = (ProgressBar) headView.findViewById(R.id.auto_refresh_progress);
        headContentHeight = mContext.getResources().getDimensionPixelSize(
                R.dimen.vc_0_0_1_newsfeed_refresh_head_height);// headView.getMeasuredHeight();

        initPullDownAnim();

        // mStepHeight = (int) (headContentHeight / mFrameCount + 0.5);
        headView.setPadding(-100, -1 * headContentHeight, 0, 0);
        headView.invalidate();

        /** 列表添加头部 **/
        addHeaderView(headView, null, false);
        initFooterViewAndListView(context);
        mCurrentState = DONE;

        // 设置滑动暂停加载监听
        pauseOnScrollListener = new PauseOnScrollListener(false, true);
        super.setOnScrollListener(pauseOnScrollListener);

        // tipsTextview.setVisibility(View.GONE);

        mTipsText.setVisibility(View.GONE);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mIsSimpleRepeat)
            startRepeatAnim(mCurrentState);
    }

    /**
     * 触摸事件的处理
     **/
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mIsRefreshable)
            return super.onTouchEvent(ev);

        final int action = ev.getAction();
        final int y = (int) ev.getRawY();
        cancelLongPress();
        switch (action) {
            case MotionEvent.ACTION_DOWN: { // 按下的时候
                if (firstItemIndex == 0 && !isRecored) {
                    isRecored = true;
                    startY = (int) ev.getY(); // 在down时候记录当前位置
                }
                mIsOnInterceptTouchEvent = false;
                mLastY = y;
                break;
            }

            case MotionEvent.ACTION_MOVE: { // 手指正在移动的时候
                int tempY = (int) ev.getY();
                if (mIsShowRefreshing) {
                    if (!isRecored && firstItemIndex == 0) {
                        isRecored = true;
                        startY = tempY; // 在move时候记录下位置
                    }
                    if (mCurrentState != REFRESHING && mCurrentState != ERROR
                            && mCurrentState != AUTO_FLING && isRecored
                            && mCurrentState != LOADING) {
                        // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
                        // 可以松手去刷新了
                        if (mCurrentState == RELEASE_To_REFRESH) {
                            //mod by changxin begin
//						setSelection(0);
                            if (!isDisallowFastMoveToTop) {
                                setSelection(0);
                            }
                            //mod by changxin end
                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((tempY - startY) / RATIO < headContentHeight)
                                    && (tempY - startY) > 0) {
                                changeHeaderViewByState(PULL_To_REFRESH);
                            }
                            // 一下子推到顶了
                            else if (tempY - startY <= 0) {
                                mCurrentState = DONE;
                                changeHeaderViewByState(DONE);
                                // 由松开刷新状态转变到done状态;
                            }
                            // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                            else {
                                // 不用进行特别的操作，只用更新paddingTop的值就行了
                            }
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (mCurrentState == PULL_To_REFRESH) {
                            //del by changxin begin
//						setSelection(0);
                            if (!isDisallowFastMoveToTop) {
                                setSelection(0);
                            }
                            //del by changxin end

                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - startY) / RATIO >= headContentHeight) {
                                isBack = true;
                                changeHeaderViewByState(RELEASE_To_REFRESH);
                                // 由done或者下拉刷新状态转变到松开刷新
                            }
                            // 上推到顶了
                            else if (tempY - startY <= 0) {
                                mCurrentState = DONE;
                                changeHeaderViewByState(DONE);
                                // 由DOne或者下拉刷新状态转变到done状态
                            }
                        }

                        // done状态下
                        if (mCurrentState == DONE) {
                            if (tempY - startY > 0) {
                                changeHeaderViewByState(PULL_To_REFRESH);
                            }
                        }
                        // 更新headView的size
                        if (mCurrentState == PULL_To_REFRESH || mCurrentState == RELEASE_To_REFRESH) {
                            mIsOnInterceptTouchEvent = true;
                            headView.setPadding(0, -1 * headContentHeight
                                    + (tempY - startY) / RATIO, 0, 0);
                            int height = (tempY - startY) / RATIO;

                            // progressView已经完全显示出来，可以开始根据步长获取当前应当显示的帧，并显示出来
                            if (height >= mAnimStartHeight) {
                            /*int frameIndex = (int) ((height - mAnimStartHeight)
									/ mAnimStepHeight + 0.5f) + 1;*/
                                int frameIndex = (int) ((height - mAnimStartHeight)
                                        / mAnimStepHeight) + 1;
                                if (frameIndex >= mPullDownFrameCount)
                                    frameIndex = mPullDownFrameCount - 1;
                                mProgressView.setImageDrawable(mPulldownDrawable
                                        .getFrame(frameIndex));
                                if (frameIndex == mPullDownFrameCount - 1) {
                                    changeHeaderViewByState(RELEASE_To_REFRESH);
                                }
                            }
                            // 背景始终显示pullDownDrawable第一帧，直到progressView完全显示出来
                            else {

                            }
                            return super.onTouchEvent(ev);
                        }
                        // 更新headView的paddingTop
                        if (mCurrentState == RELEASE_To_REFRESH) {
                            mIsOnInterceptTouchEvent = true;
                            headView.setPadding(0, (tempY - startY) / RATIO
                                    - headContentHeight, 0, 0);
                            return true;
                        }

                        if (mIsOnInterceptTouchEvent && mCurrentState == DONE) {
                            return true;
                        }
                    }
                }
                final int childCount = getChildCount();
                if (childCount == 0) {
                    return super.onTouchEvent(ev);
                }

//               可以去掉自动加载的操作，由业务逻辑自行控制
                final int itemCount = getAdapter().getCount() - mBottomPosition;
                final int deltaY = y - mLastY;
                final int lastBottom = getChildAt(childCount - 1).getBottom();
                final int end = getHeight() - getPaddingBottom();
                final int firstVisiblePosition = getFirstVisiblePosition();
                /**滑动到屏幕底部，判断是否需要出发自动加载的操作*/
                if (firstVisiblePosition + childCount >= itemCount && lastBottom
                        <= end && deltaY < 0) {
                    final boolean isHandleListViewBottomAndPullDown =
                            onListViewBottomAndPullUp(deltaY);
                    if (isHandleListViewBottomAndPullDown) {
                        mLastY = y;
                        return true;
                    }
                }

                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: { // 手指抬起来的时候
                if (mCurrentState != REFRESHING && mCurrentState != LOADING) {
                    if (mCurrentState == DONE) {
                        // 什么都不做
                    }
                    if (mCurrentState == PULL_To_REFRESH) {
                        Log.d(TAG, "PULL_To_REFRESH time " + (startime = System.currentTimeMillis()));
                        changeHeaderViewByState(DONE);
                        // 由下拉刷新状态，到done状态
                    }
                    if (mCurrentState == RELEASE_To_REFRESH) {
                        changeHeaderViewByState(AUTO_FLING);
                        mCanRefleash = true;
                        // 由松开刷新状态，到done状态
                    }
                }
                mIsOnInterceptTouchEvent = false;
                isRecored = false;
                isBack = false;
                break;
            }
        }
        mLastY = y;
        return super.onTouchEvent(ev);
    }

    // =============================== public method

    /**
     * 可以自定义其中一个条目为尾部，尾部触发的事件将以这个为准，默认为最后一个
     *
     * @param index 倒数第几个，必须在条目数范围之内
     */
    public void setBottomPosition(int index) {
        if (getAdapter() == null)
            throw new NullPointerException(
                    "You must set adapter before setBottonPosition!");
        if (index < 0)
            throw new IllegalArgumentException("Bottom position must > 0");
        mBottomPosition = index;
    }

    /**
     * 滚动监听接口
     *
     * @see ScrollOverListView#setOnScrollOverListener(OnScrollOverListener)
     */
    public interface OnScrollOverListener {
        /**
         * 到达最顶部触发
         *
         * @param delta 手指点击移动产生的偏移量
         * @return
         */
        boolean onListViewTopAndPullDown(int delta);

        /**
         * 到达最底部触发
         *
         * @param delta 手指点击移动产生的偏移量
         * @return
         */
        boolean onListViewBottomAndPullUp(int delta);

        /**
         * 手指触摸按下触发，相当于{@link MotionEvent#ACTION_DOWN}
         *
         * @return 返回true表示自己处理
         * @see View#onTouchEvent(MotionEvent)
         */
        boolean onMotionDown(MotionEvent ev);

        /**
         * 手指触摸移动触发，相当于{@link MotionEvent#ACTION_MOVE}
         *
         * @return 返回true表示自己处理
         * @see View#onTouchEvent(MotionEvent)
         */
        boolean onMotionMove(MotionEvent ev, int delta);

        /**
         * 手指触摸后提起触发，相当于{@link MotionEvent#ACTION_UP}
         *
         * @return 返回true表示自己处理
         * @see View#onTouchEvent(MotionEvent)
         */
        boolean onMotionUp(MotionEvent ev);

    }

    public void setFirstItemIndex(int index) {
        firstItemIndex = index;
    }

    public int getFirstItemIndex() {
        return firstItemIndex;
    }

    public void setAutoRefresh(boolean flag) {
        mAutoRefresh = flag;
    }

    public void setUseSimpleProgress(boolean flag) {
        useSimpleProgress = flag;
    }

    public boolean isAutoRefresh() {
        return mAutoRefresh;
    }

    public void setHeadBg(int bgResId) {
        headView.setBackgroundColor(mContext.getResources().getColor(bgResId));
    }

    /**
     * 检查是否开启滑到顶部自动刷新
     */
    private void checkAutoRefresh(boolean fromScroll) {
        if (!mAutoRefresh || firstItemIndex != 0) {
            return;
        }
        if (fromScroll) {
            if (mCurrentState == DONE && !refreshExecuted) {
                executeAutoRefresh();
//                Methods.logInfo(TAG, " 来自onScroll 执行自动刷新");
            }
        } else if (!fromScroll && !refreshExecuted && mCurrentState == DONE) {
//             Methods.logInfo(TAG, " 来自动画结束 执行自动刷新");
            executeAutoRefresh();
        }
    }

    private void executeAutoRefresh() {
        headView.setPadding(0, 0, 0, headContentHeight);
        changeHeaderViewByState(AUTO_FLING);
        mCanRefleash = true;
        refreshExecuted = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
//		Methods.logInfo(TAG, "listview onScroll");
        firstItemIndex = firstVisibleItem;
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            // 飞速滑动到达底部执行
            // onListViewBottomAndPullUp(0);
        }
        checkAutoRefresh(true);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//		Methods.log(null, "listview", "onScrollStateChanged");
    }

    private void onRefreshComplete() {
        changeHeaderViewByState(DONE);
    }

    private void onRefreshError(String info) {
        mTipsText.setText(info);
        changeHeaderViewByState(ERROR);
        // mHeadErrorContentTxtView.setText(info);
        // mProgressView.setVisibility(View.INVISIBLE);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                changeHeaderViewByState(DONE);
            }
        }, 2000);
    }

    // 当状态改变时候，调用该方法，以更新界面
    private void changeHeaderViewByState(final int targetState) {
        switch (targetState) {
            case RELEASE_To_REFRESH:
                // Log.d("===REFRESH===", "====RELEASE_To_REFRESH====");
                mTipsText.setVisibility(View.GONE);
                if (useSimpleProgress) {
                    autoRefreshProgress.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                } else {
                    autoRefreshProgress.setVisibility(View.GONE);
                    mProgressView.setVisibility(View.VISIBLE);
                    mProgressView.setImageDrawable(mPulldownDrawable
                            .getFrame(mPullDownFrameCount - 1));
                }
                mCurrentState = targetState;
                // 当前状态，松开刷新
                break;
            case PULL_To_REFRESH:
                // Log.d("===REFRESH===", "====PULL_To_REFRESH====");
                mTipsText.setVisibility(View.GONE);
                if (useSimpleProgress) {
                    autoRefreshProgress.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                } else {
                    mProgressView.setVisibility(View.VISIBLE);
                    autoRefreshProgress.setVisibility(View.GONE);
                }
                mCurrentState = targetState;
                break;
            case REFRESHING:
                // Log.d("===REFRESH===", "====REFRESHING====");
                mTipsText.setVisibility(View.GONE);
                if (useSimpleProgress) {
                    autoRefreshProgress.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                } else {
                    autoRefreshProgress.setVisibility(View.GONE);
                    mProgressView.setVisibility(View.VISIBLE);
                    mProgressView.setImageDrawable(mOnceAnimDrawable);
                    mOnceAnimDrawable.stop();
                    mOnceAnimDrawable.start();
                    mProgressView.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            startRepeatAnim(targetState);
                        }
                    }, mOnceAnimDuration);
                }
                UpdateSuccessAnimationRunnable refreshRunnable = new UpdateSuccessAnimationRunnable();
                refreshRunnable.setAnimationPosition(headView.getPaddingTop(), 0,
                        REFRESHING, UPDATE_SUCCESS_ANIMATION_DURATION);
                refreshRunnable.setFinishAction(new Runnable() {
                    @Override
                    public void run() {
                        headView.setPadding(0, 0, 0, 0);
                        mCurrentState = targetState;
                        if (mCanRefleash) {
                            mCanRefleash = false;
                            if (mOnPullDownListener != null) {
//							ImageLoader.mIsDownLoad = true;
                                mOnPullDownListener.onRefresh();
                            }
                        }
                    }
                });
                headView.post(refreshRunnable);

                // 当前状态,正在刷新...
                break;
            case DONE:
                // Log.d("===REFRESH===", "====DONE====");
                mTipsText.setVisibility(View.GONE);
                if (useSimpleProgress) {
                    autoRefreshProgress.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                } else {
                    mProgressView.setVisibility(View.VISIBLE);
                    autoRefreshProgress.setVisibility(View.GONE);
                }

                if (mIsSimpleRepeat) {
                    mRotateAnim.cancel();
                    mRotateAnim.reset();
                }

                UpdateSuccessAnimationRunnable doneRunnable = new UpdateSuccessAnimationRunnable();
                doneRunnable.setAnimationPosition(headView.getPaddingTop(),
                        -headContentHeight, DONE, UPDATE_SUCCESS_ANIMATION_DURATION);

                doneRunnable.setFinishAction(new Runnable() {
                    @Override
                    public void run() {
                        mCurrentState = targetState;
                        if (mFadeOutAnimDrawable != null) {
                            mProgressView.setImageDrawable(mPulldownDrawable
                                    .getFrame(0));
                        }
                        checkAutoRefresh(false);
                    }
                });

                if (mFadeOutAnimDrawable == null || mCurrentState != REFRESHING) {
                    mProgressView.setImageDrawable(mPulldownDrawable.getFrame(0));
                    headView.post(doneRunnable);
                } else {
                    headView.post(doneRunnable);
                }
                // 当前状态，done
                break;
            case ERROR:
                // Log.d("===REFRESH===", "====ERROR====");
                mProgressView.setVisibility(View.GONE);
                autoRefreshProgress.setVisibility(View.GONE);
                mTipsText.setVisibility(View.VISIBLE);
                if (mIsSimpleRepeat) {
                    mRotateAnim.cancel();
                    mRotateAnim.reset();
                } else {
                    mRepeatAnimDrawable.stop();
                }
                mProgressView.setImageDrawable(null);
                headView.setPadding(0, 0, 0, 0);
                mCurrentState = targetState;
                break;
            case AUTO_FLING:
                // Log.d("===REFRESH===", "====AUTO_FLING====");
                mTipsText.setVisibility(View.GONE);
                if (useSimpleProgress) {
                    autoRefreshProgress.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                } else {
                    mProgressView.setVisibility(View.VISIBLE);
                    autoRefreshProgress.setVisibility(View.GONE);
                }

                mCurrentState = targetState;
                UpdateSuccessAnimationRunnable autoFlingRunnable = new UpdateSuccessAnimationRunnable();
                autoFlingRunnable.setAnimationPosition(headView.getPaddingTop(), 0,
                        AUTO_FLING, AUTO_FLING_ANIMATION_DURATION);
                autoFlingRunnable.setFinishAction(new Runnable() {
                    @Override
                    public void run() {
                        changeHeaderViewByState(REFRESHING);
                    }
                });
                headView.post(autoFlingRunnable);
                break;
        }
    }

    private class UpdateSuccessAnimationRunnable implements Runnable {
        long mStartTime;
        boolean mIsStart;
        int mStartHeight;
        int mEndHeight;
        Runnable mFinishAction;
        int mDuration;
        DecelerateInterpolator mInterpolator = new DecelerateInterpolator(1.0f);
        int mTargetState;

        public void setAnimationPosition(int startHeight, int endHeight,
                                         int targetState, int animationDuration) {
            mStartHeight = startHeight;
            mEndHeight = endHeight;
            mDuration = Math.abs(mStartHeight - mEndHeight)
                    * animationDuration / headContentHeight;
            mDuration = mDuration > animationDuration ? animationDuration
                    : mDuration;
            mTargetState = targetState;
        }

        /**
         * 动画结束后执行的操作
         */
        public void setFinishAction(Runnable finishAction) {
            mFinishAction = finishAction;
        }

        public void run() {
            if (!mIsStart) {
                mStartTime = System.currentTimeMillis();
                mIsStart = true;
            }
            long delta = System.currentTimeMillis() - mStartTime;
            if (delta < mDuration) {
                float interpolatoryDelta = mInterpolator
                        .getInterpolation((float) delta / mDuration);
                if (mTargetState == DONE && mCurrentState != ERROR)
                    setFadeOutDrawable(interpolatoryDelta);
                if (mTargetState == AUTO_FLING && mCurrentState != ERROR) {
                    setAutoFlingDrawable(interpolatoryDelta);
                }
                if (interpolatoryDelta != 0) {
                    headView.setPadding(
                            0,
                            mStartHeight
                                    - (int) ((mStartHeight - mEndHeight) * interpolatoryDelta),
                            0, 0);
                }
                headView.post(this);
            } else {
                headView.setPadding(0, mEndHeight, 0, 0);
                if (mFinishAction != null) {
                    post(mFinishAction);
                }
            }
        }

        private void setFadeOutDrawable(float interpolatoryDelta) {
            if (mFadeOutAnimDrawable == null)
                return;
            int count = mFadeOutAnimDrawable.getNumberOfFrames();
            if (count == 0)
                return;
            int index = (int) (count * interpolatoryDelta * 2) - 1;
            if (index > count - 1)
                index = count - 1;
            if (index < 0)
                index = 0;
            mProgressView
                    .setImageDrawable(mFadeOutAnimDrawable.getFrame(index));
        }

        private void setAutoFlingDrawable(float interpolatoryDelta) {
            if (mAutoFlingAnimDrawable == null)
                return;
            int count = mAutoFlingAnimDrawable.getNumberOfFrames();
            if (count == 0)
                return;
            int index = (int) (count * interpolatoryDelta * 2) - 1;
            if (index > count - 1)
                index = count - 1;
            if (index < 0)
                index = 0;
            mProgressView
                    .setImageDrawable(mAutoFlingAnimDrawable.getFrame(index));
        }
    }

    /**
     * 点击外部的刷新按钮后需要下拉菜单同步显示成刷新状态
     */
    public void update2RefreshStatus() {
        if (/*mCurrentState == REFRESHING || mCurrentState == ERROR*/ mCurrentState != DONE) {
            return;
        }
        setSelection(0);
        mCanRefleash = true;
        changeHeaderViewByState(AUTO_FLING);
    }

    public void resetIsFetchMoreing() {
        mIsFetchMoreing = false;
    }

    /**
     * 通知已经获取完更多了，要放在Adapter.notifyDataSetChanged后面
     * 当你执行完更多任务之后，调用这个notyfyDidMore() 才会隐藏加载圈等操作
     */
    public void notifyLoadMoreComplete() {
        notifyLoadMoreComplete(R.string.load_no_more_comments_item_layout_1, R.string.load_more_item_layout_1);
    }

    public void notifyLoadMoreComplete(final int noMoreResId, final int loadMoreResId) {
        post(new Runnable() {
            @Override
            public void run() {
                mIsFetchMoreing = false;
                mFooterTextView.setVisibility(View.VISIBLE);
                // mFooterTextView.setTextColor(mContext.getResources().getColor(
                // R.color.load_more_button_blue));
                if (mIsNoMoreComments) {
                    mFooterTextView.setText(noMoreResId);
                } else {
                    mFooterTextView.setText(loadMoreResId);
                }
                mFooterLoadingView.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 刷新完毕 关闭头部滚动条
     **/
    public void refreshComplete() {

        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurrentState != ERROR) {
                    onRefreshComplete();
                }
            }
        }, 500);
    }

    /**
     * 刷新完毕 关闭头部滚动条
     **/
    public void refreshError(final String info) {
        post(new Runnable() {
            @Override
            public void run() {
                onRefreshError(info);
            }
        });
    }

    /**
     * 刷新完毕 关闭头部滚动条
     **/
    public void refreshError(final int errCode, final String info) {
        post(new Runnable() {
            @Override
            public void run() {
                onRefreshError(info);
            }
        });
    }

    public void refreshError(final int resID) {
        post(new Runnable() {
            @Override
            public void run() {
                onRefreshError(getResources().getString(resID));
            }
        });
    }

    /**
     * 设置监听器
     *
     * @param listener
     */
    public void setOnPullDownListener(OnPullDownListener listener) {
        mOnPullDownListener = listener;
    }

    /**
     * This Method is useless!!!!--------> 是否开启自动获取更多
     * 自动获取更多，将会隐藏footer，并在到达底部的时候自动刷新
     *
     * @param index 倒数第几个触发
     */
    public void enableAutoFetchMore(boolean enable, int index) {
        if (enable) {
            setBottomPosition(index);
        } else {
            // mFooterTextView.setTextColor(mContext.getResources().getColor(
            // R.color.load_more_button_blue));
            mFooterTextView.setText(mContext.getResources().getString(
                    R.string.load_more_item_layout_1));
            mFooterLoadingView.setVisibility(View.GONE);
        }
        mEnableAutoFetchMore = enable;
    }

	/*
	 * ================================== Private method 具体实现下拉刷新等操作
	 * ==================================
	 */

    /**
     * 初始化界面
     */
    private void initFooterViewAndListView(Context context) {
        mEmptyFooterView = (ViewGroup) LayoutInflater.from(context).inflate(
                R.layout.pulldown_listview_emptyview, this, false);
        // mEmptyFooterView.setLayoutParams(new
        // AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,
        // AbsListView.LayoutParams.FILL_PARENT));
        addFooterView(mEmptyFooterView, null, false);

		/* 自定义底部文件 */
        mFooterView = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.common_pulldown_footer, null);
        mFooterView.setBackgroundColor(getResources().getColor(R.color.news_feed_bg_color));
        mFooterTextView = (TextView) mFooterView
                .findViewById(R.id.pulldown_footer_text);
        mFooterLoadingView = (ProgressBar) mFooterView
                .findViewById(R.id.pulldown_footer_loading);
        mFooterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsNoMoreComments) { //如果当前加载更多布局显示的是“无更多内容”则屏蔽点击事件
                    return;
                }
                if (!mIsFetchMoreing) {
                    mIsFetchMoreing = true;
                    mFooterLoadingView.setVisibility(View.VISIBLE);
                    mFooterTextView.setText(mContext.getResources().getString(
                            R.string.load_more_item_layout_1));
                    if (mOnPullDownListener != null) {
                        mOnPullDownListener.onMore();
                    }
                }
            }
        });
        mFooterButtonView = (Button) mFooterView.findViewById(R.id.pulldown_footer_button);

		/* ScrollOverListView 同样是考虑到都是使用，所以放在这里 同时因为，需要它的监听事件 */
        addFooterView(mFooterView);
        setHideFooter();// 默认关闭底部加载更多按钮
    }

    public void setFooterViewBackground(int color) {
        if (mFooterView != null) {
            mFooterView.setBackgroundColor(color);
        }
    }

    /**
     * 条目是否填满整个屏幕
     */
    public boolean isFillScreenItem() {
        final int firstVisiblePosition = getFirstVisiblePosition();
        final int lastVisiblePostion = getLastVisiblePosition()
                - getFooterViewsCount();
        final int visibleItemCount = lastVisiblePostion - firstVisiblePosition
                + 1;
        final int totalItemCount = getCount() - getFooterViewsCount();
        if (visibleItemCount < totalItemCount)
            return true;
        return false;
    }

	/*
	 * ================================== 实现 OnScrollOverListener接口
	 */

    public boolean onListViewBottomAndPullUp(int delta) {
        if (!mEnableAutoFetchMore || mIsFetchMoreing)
            return false;
        // 数量充满屏幕才触发
        if (isFillScreenItem()) {
            mIsFetchMoreing = true;
            mFooterLoadingView.setVisibility(View.VISIBLE);

            if (mOnPullDownListener != null) {
                mOnPullDownListener.onMore();
            }
            return true;
        }
        return false;
    }

    /**
     * 隐藏头部 禁用下拉更新
     **/
    public void setHideHeader() {
        mIsShowRefreshing = false;
    }

    /**
     * 显示头部 使用下拉更新
     **/
    public void setShowHeader() {
        mIsShowRefreshing = true;
    }

    /**
     * 隐藏底部 禁用上拉更多
     **/
    public void setHideFooter() {
        mFooterView.setVisibility(View.GONE);
        mFooterTextView.setVisibility(View.INVISIBLE);
        mFooterLoadingView.setVisibility(View.GONE);
        enableAutoFetchMore(false, 1);
        mFooterView.setPadding(0, -10000, 0, 0);
    }

    /**
     * 隐藏底部 禁用上拉更多
     **/
    public void setHideFooter_new() {
        mFooterView.setVisibility(View.GONE);
        mFooterTextView.setVisibility(View.INVISIBLE);
        mFooterLoadingView.setVisibility(View.GONE);
        mFooterView.setPadding(0, -10000, 0, 0);
    }

    /**
     * 显示底部 使用上拉更多
     **/
    public void setShowFooter() {
        mFooterView.setVisibility(View.VISIBLE);
        mFooterView.findViewById(R.id.pulldown_footer_layout).setVisibility(View.VISIBLE);
        mFooterButtonView.setVisibility(View.GONE);
        mFooterTextView.setVisibility(View.VISIBLE);
        mFooterTextView.setText(mContext.getResources().getString(
                R.string.load_more_item_layout_1));
        mFooterLoadingView.setVisibility(View.GONE);
        enableAutoFetchMore(true, 1);
        mFooterView.setPadding(mFooterPadding, mFooterPadding, mFooterPadding,
                mFooterPadding);
        mIsNoMoreComments = false;
    }

    /**
     * 显示底部 使用上拉更多，自定义一个footer样式
     **/
    public void setShowFooterWithStyle(int bgColor, int fontColor) {
        mFooterView.setBackgroundColor(bgColor);
        mFooterView.setVisibility(View.VISIBLE);
        mFooterView.findViewById(R.id.pulldown_footer_layout).setVisibility(View.VISIBLE);
        mFooterButtonView.setVisibility(View.GONE);
        mFooterTextView.setVisibility(View.VISIBLE);
        mFooterTextView.setText(mContext.getResources().getString(
                R.string.load_more_item_layout_1));
        mFooterTextView.setTextColor(fontColor);
        mFooterLoadingView.setVisibility(View.GONE);
        enableAutoFetchMore(true, 1);
        mFooterView.setPadding(mFooterPadding, mFooterPadding, mFooterPadding,
                mFooterPadding);
        mIsNoMoreComments = false;
    }

    /**
     * 显示底部 已无更多按钮
     **/
    public void setShowFooterNoMoreComments() {
//        mFooterView.setVisibility(View.VISIBLE);
//        mFooterView.findViewById(R.id.pulldown_footer_layout).setVisibility(View.VISIBLE);
//		mFooterButtonView.setVisibility(View.GONE);
//        mFooterTextView.setText(mContext.getResources().
//                getString(R.string.load_no_more_comments_item_layout_1));
//        mFooterTextView.setVisibility(View.VISIBLE);
//        mFooterLoadingView.setVisibility(View.GONE);
////        enableAutoFetchMore(false, 1);
//        mFooterView.setPadding(mFooterPadding, mFooterPadding, mFooterPadding,
//				mFooterPadding);
//        Log.d("Scrollwht", "no --- width: " + mFooterView.getWidth() + " height: " + mFooterView.getHeight() + " mFooterPadding: " + mFooterPadding);
//        mIsNoMoreComments = true;
        setShowFooterNoMoreComments(R.string.load_no_more_comments_item_layout_1);
    }

    /**
     * 显示底部自定义文案
     **/
    public void setShowFooterNoMoreComments(int resId) {
        mFooterView.setVisibility(View.VISIBLE);
        mFooterView.findViewById(R.id.pulldown_footer_layout).setVisibility(View.VISIBLE);
        mFooterButtonView.setVisibility(View.GONE);
        mFooterTextView.setText(resId);
        mFooterTextView.setVisibility(View.VISIBLE);
        mFooterLoadingView.setVisibility(View.GONE);
//        enableAutoFetchMore(false, 1);
        mFooterView.setPadding(mFooterPadding, mFooterPadding, mFooterPadding,
                mFooterPadding);
        Log.d("Scrollwht", "no --- width: " + mFooterView.getWidth() + " height: " + mFooterView.getHeight() + " mFooterPadding: " + mFooterPadding);
        mIsNoMoreComments = true;
    }

    /**
     * 显示底部button，支持跳转
     *
     * @param str      button显示的文字
     * @param listener button跳转listener
     **/
    public void setShowFooterButton(String str, OnClickListener listener) {
        mFooterView.setVisibility(View.VISIBLE);
        mFooterView.findViewById(R.id.pulldown_footer_layout).setVisibility(View.GONE);
        mFooterButtonView.setVisibility(View.VISIBLE);
        mFooterButtonView.setText(str);
        mFooterButtonView.setOnClickListener(listener);
        enableAutoFetchMore(false, 1);
        //footer高度变小，重新进行设置
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFooterView.setLayoutParams(lp);
        mFooterView.setGravity(Gravity.CENTER);
        mFooterView.setPadding(mFooterPadding, mFooterPadding, mFooterPadding,
                mFooterPadding);
        Log.d("Scrollwht", " button --- width: " + mFooterView.getWidth() + " height: " + mFooterView.getHeight() + " mFooterPadding: " + mFooterPadding);
        mIsNoMoreComments = true;
    }


    public void addEmptyViewToFooterView(View emptyView) {
        View child = mEmptyFooterView.getChildAt(0);
        if (child != null) {
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            if (lp != null) {
                emptyView.setLayoutParams(lp);
            }
        }
        mEmptyFooterView.removeAllViews();
        // mEmptyFooterView
        mEmptyFooterView.addView(emptyView);
        Adapter adpter = getAdapter();
        if (adpter != null && adpter instanceof BaseAdapter) {
            ((BaseAdapter) adpter).notifyDataSetChanged();
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        /**
         * 加上此判断后可能会导致以后支持横竖屏后，当刷新时横竖屏切换会导致reLayoutEmptyView()不被调用，如果有更好的解决
         * 方法请去掉此判断
         * */
        reLayoutEmptyView();
    }

    private List<View> mHeaderViewList = new ArrayList<View>();

    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    public void addHeaderView(View v, Object data, boolean isSelectable) {
        mHeaderViewList.add(v);
        super.addHeaderView(v, data, isSelectable);
    }

    public List<View> getHeaderViewList() {
        return mHeaderViewList;
    }

    public void reLayoutEmptyView() {
        View child = mEmptyFooterView.getChildAt(0);

        if (child != null) {
            mEmptyFooterView.removeAllViews();
            int offset = 0;
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE,
                    MeasureSpec.AT_MOST);
            for (int i = 0; i < mHeaderViewList.size(); i++) {
                View headView = mHeaderViewList.get(i);
                // 头部可见, 并且头部不是刷新控件(刷新控件是临时停留在头部, 所以其高度不计算在内)
                if (headView.getVisibility() != View.GONE && this.headView != headView) {
                    headView.measure(expandSpec, expandSpec);
                    int height = headView.getMeasuredHeight()
                            + headView.getPaddingTop()
                            + headView.getPaddingBottom();
                    offset += height;
                }
            }
            // TODO, 除了这么做，不知道还能怎么办
            // 对head view做的一次校准, 不这么做, offset会是负值，导致footerView高度计算太大
            if (offset < 0)
                offset = 0;
            // --------------------------------------------------------------------------
            int height = getHeight() - mEmptyFooterView.getPaddingTop() - mEmptyFooterView.getPaddingBottom() - offset;
            // 需要计算child所需要的最小高度，并在两者之间取最大值
            expandSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            child.measure(expandSpec, expandSpec);
            int measureHeight = child.getMeasuredHeight();
            height = Math.max(height, measureHeight);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, height);
            lp.gravity = Gravity.BOTTOM;
            child.setLayoutParams(lp);
            mEmptyFooterView.addView(child);
        } else {
            LayoutParams lp = (LayoutParams) mEmptyFooterView
                    .getLayoutParams();
            if (lp == null || lp.width != 0 || lp.height != 0) {
                mEmptyFooterView.setLayoutParams(new LayoutParams(
                        0, 0));
            }
        }
    }

    /**
     * @return 作用说明 ：对外接口，提供当前listView的状态，如下拉、松开刷新等。
     * @author 创建的日期 ：2012-12-26
     */
    public int getCurState() {
        return mCurrentState;
    }

    // public void onConfigurationChanged(Configuration newConfig){
    // super.onConfigurationChanged(newConfig);
    // System.out.println("onConfigurationChanged~");
    // reLayoutEmptyView();
    // }

    private PauseOnScrollListener pauseOnScrollListener;

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if (pauseOnScrollListener == null) {
            pauseOnScrollListener = new PauseOnScrollListener(true, true, l);
            super.setOnScrollListener(pauseOnScrollListener);
        } else {
            pauseOnScrollListener.setCustomListener(l);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mIsRefreshable)
            return super.dispatchTouchEvent(ev);
        /**
         * @author tianlei.gao
         * @version 5.7
         * @date 2012.1.5
         * @describe 当收到MontionEvent.CANCEL事件时，手动触发ListView到IDLE状态
         *           解决该view收到Cancel事件后
         *           ，导致回不到IDLE状态引发的问题（置顶按钮不显示、Imagepool下载开关打不开）
         *
         * */
        if (ev.getAction() == MotionEvent.ACTION_CANCEL
                && (ev.getX() != 0 || ev.getY() != 0)
                && pauseOnScrollListener != null
                && pauseOnScrollListener.getCustomListener() != null) {
            // dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
            // SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            pauseOnScrollListener.getCustomListener().onScrollStateChanged(
                    this, OnScrollListener.SCROLL_STATE_IDLE);
            // Log.v("--------------------event cancel--------------------", ""
            // + System.currentTimeMillis());
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 此方法里面已经修改,没有动画,直接会返回到0 item,参数也已经失效.
     *
     * @param scrollOffsetMost 假滑动最大偏移
     * @param duration         滑动时间
     * @param durationOffset   滑动结束后，检测是否置顶，时间偏移，默认100ms后
     * @return 作用说明 ：对外接口，用于滑动置顶，主要用于排版固定的列表。
     * @author 创建的日期 ：2013.1.10
     */
    public void returnTopByPosition(final int scrollOffsetMost,
                                    final int duration, final int durationOffset) {
        Log.d("returntop", "LastVisiablePosition " + getLastVisiblePosition()
                + " firstVisiablePosition " + getFirstVisiblePosition());
        if (this.getCount() > (this.getLastVisiblePosition() - this
                .getFirstVisiblePosition())
                && this.getFirstVisiblePosition() != 0) {
            this.setSelection(0);
        }
		/*
		 * 
		 * final int headerSize = mHeaderViewList.size(); final int
		 * firstVisiblePosition = getFirstVisiblePosition() - headerSize;
		 * 
		 * if (firstVisiblePosition + 1 < 0) { return ; }
		 * 
		 * int dis = firstVisiblePosition > scrollOffsetMost ?
		 * firstVisiblePosition - scrollOffsetMost : 0;
		 * smoothScrollToPosition(dis); postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { dispatchTouchEvent(MotionEvent.obtain(
		 * SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
		 * MotionEvent.ACTION_CANCEL, 0, 0, 0));
		 * dispatchTouchEvent(MotionEvent.obtain( SystemClock.uptimeMillis(),
		 * SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
		 * setSelection(0); } }, duration);
		 * 
		 * postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { if (getFirstVisiblePosition() -
		 * headerSize > 0) { setSelection(0); } } }, duration + durationOffset);
		 */

    }

    /****
     * 此方法里面已经修改,没有动画,直接会返回到0 item,参数也已经失效.
     *
     * @param distance       假滑动最大距离
     * @param duration       滑动时间
     * @param durationOffset 滑动结束后，检测是否置顶，时间偏移，默认100ms后
     * @return 作用说明 ：对外接口，用于滑动置顶，主要用于排版不固定的列表，需要提前计算出滑动距离。
     * @author 创建的日期 ：2013.1.10
     */
    public void returnTopByDistance(final int distance, final int duration,
                                    final int durationOffset) {
        Log.d("returntop", "LastVisiablePosition " + getLastVisiblePosition()
                + " firstVisiablePosition " + getFirstVisiblePosition());
        if (this.getCount() > (this.getLastVisiblePosition() - this
                .getFirstVisiblePosition())
                && this.getFirstVisiblePosition() != 0) {
            this.setSelection(0);
        }
		/*
		 * 
		 * 
		 * if (distance == 0) { return; }
		 * 
		 * final int headerSize = mHeaderViewList.size();
		 * smoothScrollBy(-distance, duration); postDelayed(new Runnable() {
		 * 
		 * @Override public void run() {
		 * dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
		 * SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
		 * dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
		 * SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
		 * setSelection(0); } }, duration/2);
		 * 
		 * postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { if (getFirstVisiblePosition() -
		 * headerSize > 0) { setSelection(0); } } }, duration/2 +
		 * durationOffset);
		 */

    }

    public void setDisallowFastMoveToTop(boolean isFlag) {
        this.isDisallowFastMoveToTop = isFlag;
    }

    public void setFooterViewText(String text) {
        mFooterTextView.setText(text);
    }

    /**
     * 解决用户关注主播的直播列表无法确定是否有下一页的问题
     */
    public void setFooterViewVisible(int visible) {
        if (mFooterView == null) return;
        if (visible == View.VISIBLE) {
            mFooterView.setVisibility(View.VISIBLE);
        } else if (visible == View.GONE) {
            mFooterView.setVisibility(View.GONE);
        } else if (visible == View.VISIBLE) {
            mFooterView.setVisibility(View.VISIBLE);
        }

    }

}
