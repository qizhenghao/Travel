package com.bruce.travel.travels;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseFragment;
import com.bruce.travel.travels.activities.MyTravelsDetailActivity;
import com.bruce.travel.travels.been.TravelsBean;
import com.bruce.travel.travels.listener.OnGetDataListener;
import com.bruce.travel.desktop.DesktopActivity;
import com.bruce.travel.travels.adapter.TravelsAdapter;
import com.bruce.travel.travels.been.FilterBean;
import com.bruce.travel.travels.been.FilterTwoBean;
import com.bruce.travel.travels.been.TravelFragmentData;
import com.bruce.travel.travels.model.ITravelModel;
import com.bruce.travel.travels.model.ITravelModelImpl;
import com.bruce.travel.travels.view.FilterView;
import com.bruce.travel.travels.view.HeaderAdViewView;
import com.bruce.travel.travels.view.HeaderChannelViewView;
import com.bruce.travel.travels.view.HeaderFilterViewView;
import com.bruce.travel.universal.utils.ModelUtil;
import com.bruce.travel.universal.view.FloatingActionButton;
import com.bruce.travel.universal.view.ScrollOverListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qizhenghao on 16/8/1.
 */
public class TravelsFragment extends BaseFragment implements ScrollOverListView.OnPullDownListener, OnGetDataListener<TravelFragmentData> {

    private ITravelModel iTravelModel;
    private TravelFragmentData travelFragmentData;

    private ScrollOverListView mListView;
    private HeaderAdViewView headerAdViewView;
    private HeaderChannelViewView  headerChannelViewView;
    private HeaderFilterViewView headerFilterViewView;
    private TravelsAdapter mAdapter;
    private boolean isLogin;
    private TravelsBean mTravelsBean;


    @Bind(R.id.fragment_travels_top_filter_view)
    FilterView fvTopFilter;
    @Bind(R.id.fragment_travels_write_fab)
    FloatingActionButton mWriteFAB;

    private View itemHeaderAdView; // 从ListView获取的广告子View
    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private boolean isScrollIdle = true; // ListView是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private int titleViewHeight = 50; // 标题栏的高度
    private int filterPosition = -1; // 点击FilterView的位置：分类(0)、排序(1)、筛选(2)

    private int adViewHeight = 180; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private int filterViewPosition = 4; // 筛选视图的位置
    private int filterViewTopSpace; // 筛选视图距离顶部的距离

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_travels_layout, null);
        ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    protected void initView() {
//        mListView = (ScrollOverListView) mContentView.findViewById(R.id.fragment_travels_lv);
        mListView = findView(R.id.fragment_travels_lv);
        headerAdViewView = new HeaderAdViewView(mActivity);
        headerChannelViewView = new HeaderChannelViewView(mActivity);
        headerFilterViewView = new HeaderFilterViewView(mActivity);

        mListView.setRefreshable(true);
        mListView.setOnPullDownListener(this);
        fvTopFilter.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        isLogin = DesktopActivity.isLogin;

//        // 设置筛选数据
//        // 筛选数据
//        filterData = new FilterData();
//        filterData.setCategory(ModelUtil.getCategoryData());
//        filterData.setSorts(ModelUtil.getSortData());
//        filterData.setFilters(ModelUtil.getFilterData());
//        fvTopFilter.setFilterData(mActivity, filterData);
//
//        headerAdViewView.fillView(ModelUtil.getAdData(), mListView);
//        headerChannelViewView.fillView(ModelUtil.getChannelData(), mListView);
//        headerFilterViewView.fillView(new Object(), mListView);
//
//        // 设置ListView数据
//        mAdapter = new TravelsAdapter(mActivity, ModelUtil.getTravelingData());
//        mListView.setAdapter(mAdapter);
//
//        filterViewPosition = mListView.getHeaderViewsCount() - 1;

        // 设置ListView数据
        travelFragmentData = new TravelFragmentData();
        mAdapter = new TravelsAdapter(mActivity, travelFragmentData.travelsBeans);
        mListView.setAdapter(mAdapter);

        iTravelModel = new ITravelModelImpl();
        iTravelModel.getTravelData(travelFragmentData, this);
        initProgressBar((ViewGroup) mContentView, true);
        showProgressBar();
    }

    @Override
    protected void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Methods.showToast(mListView.getItemAtPosition(position)+"lala",false);
            }
        });

        // (假的ListView头部展示的)筛选视图点击
        headerFilterViewView.setOnFilterClickListener(new HeaderFilterViewView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooth = true;
                mListView.smoothScrollToPositionFromTop(filterViewPosition, -1);
//                fvTopFilter.setVisibility(View.VISIBLE);
//                fvTopFilter.performOnFilterClick(position);
            }
        });

        // (真正的)筛选视图点击
        fvTopFilter.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
//                if (isStickyTop) {
                filterPosition = position;
                fvTopFilter.showFilterLayout(position);
//                    if (titleViewHeight - 3 > filterViewTopSpace || filterViewTopSpace > titleViewHeight + 3) {
//                    mListView.smoothScrollToPositionFromTop(filterViewPosition, 0);
//                    }
//                }
            }
        });

        // 分类Item点击
        fvTopFilter.setOnItemCategoryClickListener(new FilterView.OnItemCategoryClickListener() {
            @Override
            public void onItemCategoryClick(FilterTwoBean entity) {
                fillAdapter(ModelUtil.getCategoryTravelingData(entity));
            }
        });

        // 排序Item点击
        fvTopFilter.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterBean entity) {
                fillAdapter(ModelUtil.getSortTravelingData(entity));
            }
        });

        // 筛选Item点击
        fvTopFilter.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterBean entity) {
                fillAdapter(ModelUtil.getFilterTravelingData(entity));
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mWriteFAB.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                if (isScrollIdle && adViewTopSpace < 0) return;

                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = mListView.getChildAt(1 - firstVisibleItem);
                }
                if (itemHeaderAdView != null) {
                    adViewTopSpace = itemHeaderAdView.getTop();
                    adViewHeight = itemHeaderAdView.getHeight();
                }

                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = mListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopSpace = itemHeaderFilterView.getTop();
                }

                if (firstVisibleItem >= filterViewPosition) {
                    isStickyTop = true;
                    fvTopFilter.setVisibility(View.VISIBLE);
                } else {
                    isStickyTop = false;
                    fvTopFilter.setVisibility(View.GONE);
                }

                if (isSmooth && isStickyTop) {
                    isSmooth = false;
                    fvTopFilter.showFilterLayout(filterPosition);
                }

                fvTopFilter.setStickyTop(isStickyTop);

            }
        });

        mWriteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(isLogin) {
                Intent intent = new Intent(getActivity(), NewRecordActivity.class);
                startActivity(intent);
//                } else {
//                    Methods.showToast("请先登录", false);
//                }

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTravelsBean = travelFragmentData.travelsBeans.get(position - 4);
                Intent intent = new Intent(getContext(),MyTravelsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("my_detail", mTravelsBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    // 填充数据
    private void fillAdapter(List<TravelsBean> list) {
        if (list == null || list.size() == 0) {
//            int height = mScreenHeight - DensityUtil.dip2px(mContext, 95); // 95 = 标题栏高度 ＋ FilterView的高度
//            mAdapter.setData(ModelUtil.getNoDataEntity(height));
        } else {
//            mListView.setLoadMoreEnable(list.size() > TravelsAdapter.ONE_REQUEST_COUNT);
            mAdapter.setData(list);
        }
    }


    @Override
    public void refresh() {

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.notifyLoadMoreComplete();
            }
        }, 2000);
    }

    @Override
    public void onSuccess(final TravelFragmentData result) {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               dismissProgressBar();
               fvTopFilter.setFilterData(mActivity, result.filterData);
               headerAdViewView.fillView(result.adData, mListView);
               headerChannelViewView.fillView(result.channelBeans, mListView);
               headerFilterViewView.fillView(new Object(), mListView);

               filterViewPosition = mListView.getHeaderViewsCount() - 1;
               mAdapter.notifyDataSetChanged();
           }
       });
    }

    @Override
    public void onError(String error) {

    }
}
