package com.wy.report.business.upload.fragment;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;

/*
 *
 * @author cantalou
 * @date 2017-12-05 21:20
 */
public class ReportUploadFragment extends ToolbarFragment {

    @Override
    protected void initData() {

    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_report_upload;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_upload);
    }
}
