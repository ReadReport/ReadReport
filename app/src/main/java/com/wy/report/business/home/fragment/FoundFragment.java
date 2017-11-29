package com.wy.report.business.home.fragment;

import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.fragment.PtrFragment;

/*
 * @author cantalou
 * @date 2017-11-26 23:03
 */
public class FoundFragment extends PtrFragment {

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_find;
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_home_toolbar;
    }

    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW;
    }
}
