package com.wy.report.business.upload.fragment;

import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.fragment.ToolbarFragment;

/*
 *
 * @author cantalou
 * @date 2017-12-06 21:24
 */
public class ReportQueryFragment extends ToolbarFragment {

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_query);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_report_query;
    }

    @Override
    protected int toolbarFlag() {
        return super.toolbarFlag() | TOOL_BAR_FLAG_OVERLAY;
    }
}
