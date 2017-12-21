package com.wy.report.business.home.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.manager.router.AuthRouterManager;

import butterknife.OnClick;

/**
 * 我的
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class MyFragment extends PtrFragment {

    @Override
    protected void initData(Bundle savedInstanceState) {
        ptrWithoutToolbar = true;
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        setTitle("我的");
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        Drawable toolbarBackground = toolbar.getBackground();
        toolbarBackground.setAlpha(0);
    }

    @Override
    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW | TOOL_BAR_FLAG_OVERLAY;
    }


    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_my;
    }

    @OnClick(R.id.home_my_header_layout)
    public void onReportQueryClick() {
        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(getActivity(), AuthRouterManager.ROUTER_LOGIN);
    }
}
