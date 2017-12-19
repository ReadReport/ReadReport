package com.wy.report.base.adapter;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wy.report.base.viewholder.BaseDataBindingViewHolder;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-19 下午4:58
 * @description: databinding adapter
 */
public abstract class QuickDataBindingAdapter<T,B extends ViewDataBinding> extends BaseQuickAdapter<T,BaseDataBindingViewHolder<B>> {

    public QuickDataBindingAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected BaseDataBindingViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        ViewDataBinding           binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        View                      item    = binding.getRoot();
        BaseDataBindingViewHolder holder  = new BaseDataBindingViewHolder(item);
        holder.setBinding(binding);
        return holder;
    }


}
