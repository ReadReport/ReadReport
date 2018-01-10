package com.wy.report.base.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

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

    protected TextView toolbarTitle;

    protected TextView toolbarMenu;

    protected View toolbarContentView;

    protected View toolbarBack;

    protected ViewGroup toolbarContainer;

    @Nullable
    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if ((toolbarFlag() & TOOL_BAR_FLAG_SHOW) == TOOL_BAR_FLAG_SHOW) {
            toolbarContainer = (ViewGroup) inflater.inflate(toolbarContainerLayoutID(), container, false);
            toolbarContentView = super.createView(inflater, toolbarContainer, savedInstanceState);
            View toolbar = inflater.inflate(toolbarLayoutID(), toolbarContainer, false);

            if ((TOOL_BAR_FLAG_OVERLAY & toolbarFlag()) == TOOL_BAR_FLAG_OVERLAY) {
                toolbarContainer.addView(toolbarContentView);
                toolbarContainer.addView(toolbar);
            } else {
                toolbarContainer.addView(toolbar);
                toolbarContainer.addView(toolbarContentView);
            }
            return toolbarContainer;
        } else {
            return super.createView(inflater, container, savedInstanceState);
        }
    }

    @Override
    @CallSuper
    protected void initView(View contentView) {
        initToolbar();
    }

    @CallSuper
    protected void initToolbar() {
        toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }

        BaseActivity activity = (BaseActivity) getActivity();
        int toolbarHeight = activity.getResources()
                                    .getDimensionPixelOffset(R.dimen.toolbar_height);
        int statusBarHeight = DeviceUtils.getStatusBarHeight(activity);

        ImageView statusBarBg = new ImageView(getActivity());
        statusBarBg.setImageResource(R.color.translucent_15);
        statusBarBg.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, statusBarHeight));

        if (activity != null && activity.isTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if ((TOOL_BAR_FLAG_OVERLAY & toolbarFlag()) == TOOL_BAR_FLAG_OVERLAY) {
                toolbar.setPadding(toolbar.getPaddingLeft(), statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
                toolbar.getLayoutParams().height = toolbarHeight + statusBarHeight;
                toolbarContainer.addView(statusBarBg, 0);
            } else {
                statusBarBg.setBackgroundDrawable(toolbar.getBackground());
                toolbarContainer.addView(statusBarBg, 0);
            }
        }

        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarMenu = (TextView) toolbar.findViewById(R.id.toolbar_menu);
        toolbarBack = toolbar.findViewById(R.id.toolbar_back);
        if (toolbarBack != null) {
            toolbarBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!onBackPressed()) {
                        getActivity().finish();
                    }
                }
            });
        }

        if (getMenuLayoutId() > 0) {
            toolbar.inflateMenu(getMenuLayoutId());
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

    public void setTitle(String title) {
        toolbarTitle.setText(title);
    }

    public void setTitle(int titleResID) {
        toolbarTitle.setText(titleResID);
    }

    protected int getMenuLayoutId() {
        return 0;
    }

}
