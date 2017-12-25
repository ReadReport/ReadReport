package com.wy.report.business.upload.fragment;

import android.os.Bundle;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.manager.auth.AuthManager;
import com.wy.report.manager.router.AuthRouterManager;

import butterknife.OnClick;

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

    @OnClick(R.id.report_query)
    public void queryReport() {
        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(getActivity(), AuthRouterManager.ROUTER_REPORT_QUERY);
    }

    @OnClick(R.id.report_upload)
    public void uploadReport() {
        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(getActivity(), AuthRouterManager.ROUTER_REPORT_UPLOAD);
    }
}
