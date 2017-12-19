package com.wy.report.base.viewholder;

import android.support.annotation.CallSuper;
import android.view.View;

import butterknife.ButterKnife;
/*
 *
 * @author cantalou
 * @date 2017-12-03 22:38
 */
public abstract class BaseViewHolder<T> extends com.chad.library.adapter.base.BaseViewHolder {

    protected T model;

    public BaseViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @CallSuper
    public void onBindViewHolder(T model) {
        this.model = model;
    }
}
