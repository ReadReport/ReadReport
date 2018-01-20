package com.wy.report.business.read.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.read.mode.ReportDetailMode;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.util.ToastUtils;
import com.wy.report.widget.ObservableScrollView;
import com.wy.report.widget.view.ColorArcProgressBar;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 报告详细
 */
public class ReportDetailFragment extends PtrFragment {

    @BindView(R.id.report_detail_score_pb)
    ColorArcProgressBar mProgressBar;

    @BindView(R.id.report_detail_sv)
    ObservableScrollView mScrollView;

    private ReadService mReadService;

    /**
     * 是否是男性
     */
    private boolean isMale;

    /**
     * 报告状态
     */
    private int reportStatus;

    /**********************************界面************************************/
    //-----用户信息------
    @BindView(R.id.report_detail_head_name)
    TextView userName;
    @BindView(R.id.report_detail_head_sex)
    TextView userSex;
    @BindView(R.id.report_detail_head_age)
    TextView userAge;
    @BindView(R.id.report_detail_head_type)
    TextView userType;
    @BindView(R.id.report_detail_head_time)
    TextView userCheckTime;
    @BindView(R.id.report_detail_head_hospital)
    TextView userCheckHospital;

    //    -----体检报告------
    //    @BindView(R.id.report_detail_head_name)
    //    TextView     userName;
    @BindView(R.id.report_detail_report_pic_content_ll)
    LinearLayout picContentView;
    //    //-----用户备注------
    //    @BindView(R.id.report_detail_head_name)
    //    TextView userName;
    //    //-----健康得分------
    //    @BindView(R.id.report_detail_head_name)
    //    TextView userName;
    //    //-----身体机能------
    //    @BindView(R.id.report_detail_head_name)
    //    TextView userName;


    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_report_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ptrWithoutToolbar = true;
        mReadService = RetrofitHelper.getInstance().create(ReadService.class);

    }

    @Override
    protected void loadData() {
        super.loadData();
        mReadService.getReportDetail("1", "4").subscribe(new PtrSubscriber<ResponseModel<ReportDetailMode>>(this) {
            @Override
            public void onNext(ResponseModel<ReportDetailMode> reportDetailModeResponseModel) {
                super.onNext(reportDetailModeResponseModel);
                ReportDetailMode            detailMode = reportDetailModeResponseModel.getData();
                ReportDetailMode.ReportInfo reportInfo = detailMode.getReprotInfo();
                ReportDetailMode.UserInfo   userInfo   = detailMode.getUserInfo();
            }
        });
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        //        mProgressBar.setCurrentValues(85);
        //        AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_DOCTOR_DETAIL);
        addPic("1");
        addPic("2");
        addPic("3");
        addPic("4");
        addPic("");
        addPic("");
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler2.checkContentCanBePulledDown(frame, mScrollView, header);
    }

    private void updateUserInfo(ReportDetailMode.UserInfo userInfo) {

    }

    private void updateReportInfo(ReportDetailMode.ReportInfo userInfo) {

    }

    private void updateBodyInfo(ReportDetailMode.ReportInfo userInfo) {

    }


    private void addPic(final String url) {
        getActivity().getLayoutInflater().inflate(R.layout.view_report_detail_report_pic_item, picContentView, true);
        ImageView photo = (ImageView) picContentView.getChildAt(picContentView.getChildCount() - 1);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLong(url);
            }
        });
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) picContentView.getChildAt(0).getLayoutParams();
        layoutParams.setMargins(0,0,0,0);
    }


}
