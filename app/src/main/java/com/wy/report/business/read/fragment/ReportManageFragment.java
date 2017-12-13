package com.wy.report.business.read.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.business.read.mode.ReportItemMode;
import com.wy.report.databinding.ViewReportItemBinding;

public class ReportManageFragment extends ToolbarFragment {

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_manage);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.view_report_item;
    }

    @Override
    protected View bindView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ReportItemMode reportItemMode = new ReportItemMode();
        reportItemMode.setHospital(new ObservableField<String>("西乡"));
        reportItemMode.setName(new ObservableField<String>("试试"));
        ViewReportItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_report_item, container, false);
        binding.setItem(reportItemMode);
        return binding.getRoot();
    }
}
