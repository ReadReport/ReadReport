package com.wy.report.business.upload.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.family.model.FamilyMemberModel;
import com.wy.report.business.upload.model.UnitModel;
import com.wy.report.business.upload.model.UploadModel;
import com.wy.report.business.upload.service.ReportService;
import com.wy.report.helper.dialog.DialogHelper;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *
 * @author cantalou
 * @date 2017-12-06 21:24
 */
public class ReportQueryFragment extends NetworkFragment {

    @BindView(R.id.report_query_medical_examiner_name)
    TextView name;

    @BindView(R.id.report_query_hospital_name)
    TextView hospital;

    @BindView(R.id.report_query_account_name)
    EditText account;

    @BindView(R.id.report_query_password_name)
    EditText password;

    private ReportService reportService;

    private FamilyMemberModel familyMemberModel;

    private UnitModel unitModel;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        reportService = RetrofitHelper.getInstance()
                                      .create(ReportService.class);
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

    @OnClick({R.id.report_query_medical_examiner})
    public void nameClick() {
        router.open(getActivity(), AuthRouterManager.ROUTER_FAMILY_MEMBER_SELECT);
    }

    @OnClick({R.id.report_query_hospital})
    public void hospitalClick() {
        router.open(getActivity(), AuthRouterManager.ROUTER_REPORT_HOSPITAL_LIST);
    }


    @Subscribe(tags = {@Tag(RxKey.RX_FAMILY_MEMBER_SELECT)})
    public void familyMemberSelected(FamilyMemberModel model) {
        familyMemberModel = model;
        name.setText(model.getName());
    }

    @Subscribe(tags = {@Tag(RxKey.RX_HOSPITAL_UNIT_SELECT)})
    public void hospitalUnitSelected(UnitModel model) {
        unitModel = model;
        hospital.setText(model.getTitle());
    }

    @OnClick(R.id.report_query_not_found)
    public void reportNotFoundClick() {
        authRouterManager.openWebView(getActivity(), "http://dbg.vip120.com/Report/thirdParty_search_fail", "");
    }

    @OnClick({R.id.report_query_submit})
    public void submit() {
        reportService.queryReport(familyMemberModel.getId(), unitModel.getId(), unitModel.getTitle(), ViewUtils.getText(account),
                ViewUtils.getText(password), unitModel.getHospitalType())
                     .subscribe(new NetworkSubscriber<ResponseModel<UploadModel>>(this) {

                         @Override
                         public void handleSuccess(ResponseModel<UploadModel> uploadModelResponseModel) {
                             super.handleSuccess(uploadModelResponseModel);
                             DialogHelper.showReportQueryConfirmDialog(getActivity(), new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     router.open(getActivity(), AuthRouterManager.ROUTER_REPORT_QUERY_SUCCESS);
                                 }
                             });
                         }
                     });
    }


}
