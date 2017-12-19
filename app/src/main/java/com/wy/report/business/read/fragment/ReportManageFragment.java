package com.wy.report.business.read.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.business.read.mode.ReportManageViewMode;
import com.wy.report.business.read.view.ManagePopMenu;
import com.wy.report.databinding.FragmentManageBinding;
import com.zyyoona7.lib.EasyPopup;
import com.zyyoona7.lib.HorizontalGravity;
import com.zyyoona7.lib.VerticalGravity;

import butterknife.BindView;


public class ReportManageFragment extends PtrFragment {


    private ReportManageViewMode reportManageViewMode;


    private ManagePopMenu mPopMenu;

    private boolean isPop;

    @BindView(R.id.toolbar_pop)
    TextView toolBarPop;


    @Override
    protected void initData(Bundle savedInstanceState) {
        reportManageViewMode = new ReportManageViewMode(this);
        ptrWithoutToolbar = true;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        reportManageViewMode.initData();
        initPopMenu();
        toolBarPop.setText("全部");
        toolBarPop.setClickable(true);
        toolBarPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPop) {
                    mPopMenu.dismiss();
                } else {
                    mPopMenu.showAtAnchorView(toolbar, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT,0,0);
                }
            }
        });
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_manage);
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_read_manage_toolbar;
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_manage;
    }

    @Override
    protected View bindView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentManageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage, container, false);
        binding.setData(reportManageViewMode.getData());
        return binding.getRoot();
    }

    @Override
    protected void loadData() {
        reportManageViewMode.getDataFromNet();
    }

    private void initPopMenu() {
        mPopMenu = new ManagePopMenu(getActivity());
        mPopMenu.createPopup();
        mPopMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toolBarPop.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.selector_read_manage_nav_up, 0);
                isPop = false;
            }
        });
        mPopMenu.setOnAttachedWindowListener(new EasyPopup.OnAttachedWindowListener() {
            @Override
            public void onAttachedWindow(int i, int i1, EasyPopup easyPopup) {
                toolBarPop.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.selector_read_manage_nav_down, 0);
                isPop = true;
            }
        });
    }
}
