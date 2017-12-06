package com.wy.report.base.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cantalou.android.util.DeviceUtils;
import com.wy.report.R;
import com.wy.report.base.activity.BaseActivity;

import butterknife.BindView;

/**
 * @author cantalou
 * @date 2017年11月29日 11:18
 */
public abstract class ToolbarFragment extends BaseFragment {

    public static final int TOOL_BAR_FLAG_SHOW = 0x1;

    public static final int TOOL_BAR_FLAG_OVERLAY = 0x10;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    protected TextView toolbarTitle;

    protected View toolbarBack;

    @Nullable
    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ViewGroup toolbarContainer = (ViewGroup) inflater.inflate(toolbarContainerLayoutID(), container, false);

        View contentView = super.createView(inflater, toolbarContainer, savedInstanceState);
        View toolbar = inflater.inflate(toolbarLayoutID(), toolbarContainer, false);

        if ((TOOL_BAR_FLAG_OVERLAY & toolbarFlag()) == TOOL_BAR_FLAG_OVERLAY) {
            toolbarContainer.addView(contentView);
            toolbarContainer.addView(toolbar);
        } else {
            toolbarContainer.addView(toolbar);
            toolbarContainer.addView(contentView);
        }
        return toolbarContainer;
    }

    @Override
    @CallSuper
    protected void initView(View contentView) {
        initToolbar();
    }

    @CallSuper
    protected void initToolbar() {
        toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);

        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null && activity.isTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int toolbarHeight = activity.getResources()
                                        .getDimensionPixelOffset(R.dimen.toolbar_height);
            int statusBarHeight = DeviceUtils.getStatusBarHeight(activity);
            toolbar.setPadding(toolbar.getPaddingLeft(), statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            toolbar.getLayoutParams().height = toolbarHeight + statusBarHeight;
        }

        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarBack = toolbar.findViewById(R.id.toolbar_back);
        if (toolbarBack != null) {
            toolbarBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
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

    protected void setTitle(String title) {
        toolbarTitle.setText(title);
    }

    protected void setTitle(int titleResID) {
        toolbarTitle.setText(titleResID);
    }

}
