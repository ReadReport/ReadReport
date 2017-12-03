package com.wy.report.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wy.report.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-03 23:07
 */
public abstract class BaseAdapter<VH extends BaseViewHolder<T>, T> extends RecyclerView.Adapter<VH> {

    public interface OnItemClickListener<T>{
        void onItemClick(View itemView, T model, int position);
    }

    private final List<T> data = new ArrayList<>();

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.onBindViewHolder(data.get(position));
    }

    public void add(List<T> newData) {
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public void replace(List<T> newData) {
        data.clear();
        add(newData);
    }

    public List<T> getData() {
        return data;
    }
}
