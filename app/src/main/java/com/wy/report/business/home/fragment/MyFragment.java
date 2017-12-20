package com.wy.report.business.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.fragment.PtrFragment;

/**
 * 我的
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class MyFragment extends PtrFragment {

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        setTitle("我的");
    }

    @Override
    protected void setTitle(String title) {
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_my;
    }
}
