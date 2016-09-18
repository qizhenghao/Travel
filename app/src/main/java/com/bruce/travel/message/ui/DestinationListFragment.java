package com.bruce.travel.message.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bruce.travel.R;
import com.bruce.travel.message.adapter.TitleAdapter;
import com.bruce.travel.message.model.DestinationInfo;
import com.bruce.travel.universal.utils.Methods;
import com.bruce.travel.universal.utils.ModelUtil;

import java.util.List;

/**
 * Created by 梦亚 on 2016/9/9.
 */
public class DestinationListFragment extends ListFragment {

    private View mContentView;
    private TitleAdapter titleAdapter;
    public static int mTitlePosition;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleAdapter = new TitleAdapter(getContext(), ModelUtil.getDestinationData());
        this.setListAdapter(titleAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_dst_list_layout, container, false);
        return mContentView;
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
//        Methods.showToast("" + position, false);
//        titleAdapter.setSelectedItem(position);
//        mTitlePosition = position;
//        titleAdapter.notifyDataSetChanged();

    }

}
