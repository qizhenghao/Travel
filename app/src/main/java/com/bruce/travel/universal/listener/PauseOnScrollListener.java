package com.bruce.travel.universal.listener;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ListView;

import com.bruce.travel.universal.view.ScrollOverListView;

//import com.renren.mobile.android.img.ImageLoaderManager;
//import com.renren.mobile.android.img.recycling.RecyclingLoadImageEngine;
//import com.renren.mobile.android.view.ScrollOverListView;


/**
 * Listener-helper for {@linkplain AbsListView list views} ({@link ListView}, {@link GridView}) which can
 * {@linkplain ImageLoader#pause() pause ImageLoader's tasks} while list view is scrolling (touch scrolling and/or
 * fling). It prevents redundant loadings.<br />
 * Set it to your list view's {@link AbsListView#setOnScrollListener(OnScrollListener) setOnScrollListener(...)}.<br />
 * This listener can wrap your custom {@linkplain OnScrollListener listener}.
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.7.0
 */
public class PauseOnScrollListener implements OnScrollListener {

	private final boolean pauseOnScroll;
	private final boolean pauseOnFling;
	private OnScrollListener externalListener;

	/**
	 * Constructor
	 * 
	 * @param pauseOnScroll Whether {@linkplain ImageLoader#pause() pause ImageLoader} during touch scrolling
	 * @param pauseOnFling Whether {@linkplain ImageLoader#pause() pause ImageLoader} during fling
	 */
	public PauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
		this(pauseOnScroll, pauseOnFling, null);
	}

	/**
	 * Constructor
	 * 
	 * @param pauseOnScroll Whether {@linkplain ImageLoader#pause() pause ImageLoader} during touch scrolling
	 * @param pauseOnFling Whether {@linkplain ImageLoader#pause() pause ImageLoader} during fling
	 * @param customListener Your custom {@link OnScrollListener} for {@linkplain AbsListView list view} which also will
	 *            be get scroll events
	 */
	public PauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling, OnScrollListener customListener) {
		this.pauseOnScroll = pauseOnScroll;
		this.pauseOnFling = pauseOnFling;
		externalListener = customListener;
	}

	/**
	 * set custom listener
	 */
	public void setCustomListener(OnScrollListener customListener) {
		externalListener = customListener;
	}

	/**
	 * get custom listener
	 * @return
	 */
	public OnScrollListener getCustomListener() {
		return externalListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_IDLE:
//				ImageLoaderManager.setPauseState(false);
//				RecyclingLoadImageEngine.resume();
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				if (pauseOnScroll) {
//					ImageLoaderManager.setPauseState(true);
//					RecyclingLoadImageEngine.pause();
				}
				break;
			case OnScrollListener.SCROLL_STATE_FLING:
				if (pauseOnFling) {
//					ImageLoaderManager.setPauseState(true);
//					RecyclingLoadImageEngine.pause();
				}
				break;
		}
		if (externalListener != null) {
			externalListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (externalListener != null) {
			externalListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
        if(view instanceof ScrollOverListView){
           ((ScrollOverListView) view).onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
	}
}
