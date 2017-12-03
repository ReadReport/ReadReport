package com.wy.report.base.viewholder;

import android.support.annotation.CallSuper;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wy.report.base.adapter.BaseAdapter;
import com.wy.report.base.model.BaseModel;

import butterknife.ButterKnife;
import butterknife.OnItemClick;

/*
 *
 * @author cantalou
 * @date 2017-12-03 22:38
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private BaseAdapter.OnItemClickListener<T> onItemClickListener;

    private T model;

    public BaseViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(itemView, model, getAdapterPosition());
                }
            }
        });
    }

    @CallSuper
    public void onBindViewHolder(T model) {
        this.model = model;
    }

    public void setOnItemClickListener(BaseAdapter.OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
