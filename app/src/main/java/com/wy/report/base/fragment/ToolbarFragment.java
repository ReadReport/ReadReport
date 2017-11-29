package com.wy.report.base.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cantalou.android.util.DeviceUtils;
import com.wy.report.R;
import com.wy.report.base.activity.BaseActivity;

/**
 * @author cantalou
 * @date 2017年11月29日 11:18
 */
public abstract class ToolbarFragment extends BaseFragment {

    public static final int TOOL_BAR_FLAG_SHOW = 0x1;

    public static final int TOOL_BAR_FLAG_OVERLAY = 0x10;

    protected Toolbar toolbar;

    @Nullable
    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ViewGroup toolbarContainer = (ViewGroup) inflater.inflate(toolbarContainerLayoutID(), container, false);

        View contentView = super.createView(inflater, toolbarContainer, savedInstanceState);
        View toolbar = inflater.inflate(toolbarLayoutID(), toolbarContainer, false);

        if ((TOOL_BAR_FLAG_OVERLAY & toolbarFlag()) == TOOL_BAR_FLAG_OVERLAY) {
            toolbarContainer.addView(toolbar);
            toolbarContainer.addView(contentView);
        } else {
            toolbarContainer.addView(contentView);
            toolbarContainer.addView(toolbar);
        }
        return toolbarContainer;
    }

    @Override
    @CallSuper
    protected void initView(View contentView) {
        toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null && activity.isWindowTranslucentStatus()) {
            toolbar.setPadding(toolbar.getPaddingLeft(), DeviceUtils.getStatusBarHeight(activity), toolbar.getPaddingRight(), toolbar.getPaddingBottom());
        }
    }

    protected int toolbarContainerLayoutID() {
        return (TOOL_BAR_FLAG_OVERLAY & toolbarFlag()) == TOOL_BAR_FLAG_OVERLAY ? R.layout.view_toolbar_wrapper_overlap : R.layout.view_toolbar_wrapper;
    }

    protected int toolbarLayoutID() {
        return R.layout.view_toolbar;
    }

    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW;
    }
}
