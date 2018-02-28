package com.wy.report.business.read.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.read.mode.DoctorMode;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-16 下午6:36
 * @description: ReadReport
 */
public class DoctorDetailFragment extends PtrFragment {

    private ReadService readService;
    private String      docId;

    @BindView(R.id.doctor_detail_name)
    TextView         name;
    @BindView(R.id.doctor_detail_level)
    TextView         level;
    @BindView(R.id.doctor_detail_head_icon)
    RoundedImageView headIcon;

    @BindView(R.id.doctor_detail_server_num)
    TextView serviceNum;
    @BindView(R.id.doctor_detail_good_at_num)
    TextView goodNum;

    @BindView(R.id.doctor_detail_good_at)
    TextView goodAt;
    @BindView(R.id.doctor_detail_summary)
    TextView summary;


    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_doctor_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ptrWithoutToolbar = true;
        readService = RetrofitHelper.getInstance().create(ReadService.class);

        docId = getArguments().getString(BundleKey.BUNDLE_KEY_DOCTOR_ID);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        setTitle(getString(R.string.doctor_detail_title));
        ptrFrameLayout.autoRefresh();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        Drawable toolbarBackground = toolbar.getBackground();
        toolbarBackground.setAlpha(0);
        toolbarBack.setBackgroundResource(R.drawable.selector_toolbar_back_white);
        toolbarTitle.setTextColor(getColor(R.color.bai_ffffff));
    }

    @Override
    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW | TOOL_BAR_FLAG_OVERLAY;
    }

    @Override
    protected void loadData() {
        super.loadData();
        readService.getDoctorInfo(docId).subscribe(new PtrSubscriber<ResponseModel<DoctorMode>>(this) {
            @Override
            public void onNext(ResponseModel<DoctorMode> doctorModeResponseModel) {
                super.onNext(doctorModeResponseModel);
                updateInfo(doctorModeResponseModel.getData());
            }
        });
    }

    private void updateInfo(DoctorMode doctorMode) {
        if (StringUtils.isNotBlank(doctorMode.getHeadIcon())) {
            Glide.with(getActivity())
                 .load(doctorMode.getHeadIcon())
                 .into(headIcon);
        } else {
            Glide.with(getActivity())
                 .load(R.drawable.btn_login_no)
                 .into(headIcon);
        }


        name.setText(doctorMode.getName());
        level.setText(doctorMode.getAppellation());

        serviceNum.setText(String.format(getString(R.string.doctor_detail_server), doctorMode.getServerNum()));
        goodNum.setText(String.format(getString(R.string.doctor_detail_good), doctorMode.getGoodAt()));

        goodAt.setText(doctorMode.getGoodAt());
        summary.setText(doctorMode.getSummary());
    }


    @OnClick(R.id.doctor_detail_upload_report_2_doctor)
    public void upload() {
        AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_REPORT_UPLOAD_QUERY);
    }
}
