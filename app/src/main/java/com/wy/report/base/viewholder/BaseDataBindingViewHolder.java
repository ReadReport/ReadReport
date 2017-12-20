package com.wy.report.base.viewholder;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-19 下午4:52
 * @description: ReadReport
 */
public class BaseDataBindingViewHolder<B extends ViewDataBinding> extends BaseViewHolder {

    protected B binding;

    public BaseDataBindingViewHolder(View view) {
        super(view);
    }

    public B getBinding() {
        return binding;
    }

    public void setBinding(B binding) {
        this.binding = binding;
    }
}
