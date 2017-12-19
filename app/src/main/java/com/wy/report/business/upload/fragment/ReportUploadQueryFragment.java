package com.wy.report.business.upload.fragment;

import android.os.Bundle;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;

/**
 * @author cantalou
 * @date 2017年12月04日 15:57
 */
public class ReportUploadQueryFragment extends ToolbarFragment {

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_upload);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_report_upload_query;
    }
}
