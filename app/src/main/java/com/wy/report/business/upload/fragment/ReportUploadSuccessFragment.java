package com.wy.report.business.upload.fragment;

import android.os.Bundle;

import com.wy.report.R;
import com.wy.report.base.constant.Constants;
import com.wy.report.base.fragment.ToolbarFragment;

import butterknife.OnClick;

/**
 * @author cantalou
 * @date 2018年01月23日 18:37
 * <p>
 */
public class ReportUploadSuccessFragment extends ToolbarFragment {

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_report_upload_success;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_upload_success_title);
    }

    @OnClick(R.id.fragment_report_upload_success_start)
    public void startSurvey() {
        authRouterManager.openWebView(getActivity(), Constants.HEALTHY_STATUS_RESEARCH, "");
    }
}
