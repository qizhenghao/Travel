package com.bruce.travel.universal.photopicker.tools;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleGenericAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> list = new ArrayList<T>();
    private int layoutView;


    public SimpleGenericAdapter(Context context, List<T> list, int layoutView) {
        super();
        this.context = context;
        this.list = list;
        this.layoutView = layoutView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        View view;
        if (convertView == null) {
            view = View.inflate(context, layoutView, null);
        } else {
            view = convertView;
        }

        T t = null;
        try {
            t = list.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = getViewHolder(t);
            holder.getHolder(view);
            view.setTag(holder);
        }
        holder.setItem(t);
        holder.setPositopn(position);
        holder.show();
        return view;
    }

    public abstract class ViewHolder {
        public T item;
        public int positopn;

        public ViewHolder(T t) {
            this.item = t;
        }

        public int getPositopn() {
            return positopn;
        }

        public ViewHolder setPositopn(int positopn) {
            this.positopn = positopn;
            return this;
        }

        public abstract ViewHolder getHolder(View view);

        public abstract void show();

        public T getItem() {
            return item;
        }

        public ViewHolder setItem(T item) {
            this.item = item;
            return this;
        }

    }

    public abstract ViewHolder getViewHolder(T t);

}
