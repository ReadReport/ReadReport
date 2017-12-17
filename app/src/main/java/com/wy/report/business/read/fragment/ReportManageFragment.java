package com.wy.report.business.read.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.business.read.mode.ReportManageViewMode;
import com.wy.report.databinding.FragmentManageBinding;

import butterknife.BindView;


public class ReportManageFragment extends PtrFragment {


    private ReportManageViewMode reportManageViewMode;



    @BindView(R.id.toolbar_pop)
    TextView toolBarPop;

    private Gift



    @Override
    protected void initData(Bundle savedInstanceState) {
        reportManageViewMode = new ReportManageViewMode(this);
        ptrWithoutToolbar=true;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        reportManageViewMode.initData();
        toolBarPop.setText("全部");
        toolBarPop.setClickable(true);
        toolBarPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "ssss", Toast.LENGTH_SHORT).show();
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
}
