package com.wy.report.business.read.fragment;

import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.manager.router.AuthRouterManager;

/**
 * 报告详细
 */
public class ReportDetailFragment extends PtrFragment {

//    @BindView(R.id.report_detail_score_pb)
//    ColorArcProgressBar mProgressBar;


    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_report_detail;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
//        mProgressBar.setCurrentValues(85);
        AuthRouterManager.getInstance().getRouter().open(getActivity(),AuthRouterManager.ROUTER_DOCTOR_DETAIL);
    }
}
