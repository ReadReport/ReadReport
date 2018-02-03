package com.wy.report.business.read.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.read.mode.ReportDetailMode;
import com.wy.report.business.read.mode.ReportItemMode;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.StringUtils;
import com.wy.report.util.TimeUtils;
import com.wy.report.util.ToastUtils;
import com.wy.report.widget.ObservableScrollView;
import com.wy.report.widget.view.BodyItemView;
import com.wy.report.widget.view.ColorArcProgressBar;
import com.wy.report.widget.view.recycleview.NestedGridLayoutManager;
import com.wy.report.widget.view.recycleview.NestedLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 报告详细
 */
public class ReportDetailFragment extends PtrFragment {

    @BindView(R.id.report_detail_sv)
    ObservableScrollView mScrollView;

    private ReadService mReadService;
    private boolean     isMale;
    private int         reportStatus;
    private String      reportId;
    private boolean     fromHome;

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

    //-----体检报告------
    @BindView(R.id.report_detail_report_pic_info_private_ll)
    LinearLayout        picContentPrivate;
    @BindView(R.id.report_detail_report_pic_rv)
    RecyclerView        picRecyleView;
    //-----用户备注------
    @BindView(R.id.report_detail_note_head_icon)
    ImageView           userNoteHeader;
    @BindView(R.id.report_detail_note_content)
    TextView            userNoteContent;
    //-----健康得分------
    @BindView(R.id.report_detail_score_ll)
    LinearLayout        scoreView;
    @BindView(R.id.report_detail_score_pb)
    ColorArcProgressBar score;
    //-----身体机能------
    @BindView(R.id.report_detail_body_ll)
    LinearLayout        bodyView;
    @BindView(R.id.report_detail_body_item_1)
    BodyItemView        body1;
    @BindView(R.id.report_detail_body_item_2)
    BodyItemView        body2;
    @BindView(R.id.report_detail_body_item_3)
    BodyItemView        body3;
    @BindView(R.id.report_detail_body_item_4)
    BodyItemView        body4;
    @BindView(R.id.report_detail_body_item_5)
    BodyItemView        body5;
    @BindView(R.id.report_detail_body_item_6)
    BodyItemView        body6;
    @BindView(R.id.report_detail_body_item_7)
    BodyItemView        body7;
    @BindView(R.id.report_detail_body_item_8)
    BodyItemView        body8Male;
    @BindView(R.id.report_detail_body_item_8_female)
    BodyItemView        body8Female;
    @BindView(R.id.report_detail_body_body)
    ImageView           body;
    //-----医生建议------
    @BindView(R.id.report_detail_suggestion_ll)
    LinearLayout        suggestionView;

    //指标说明
    @BindView(R.id.report_detail_quota_ll)
    LinearLayout quotaView;
    @BindView(R.id.report_detail_quota_private_ll)
    LinearLayout quotaPrivateView;

    @BindView(R.id.report_detail_quota_normal)
    TextView quotaNormal;
    @BindView(R.id.report_detail_quota_un_normal)
    TextView quotaUnNormal;
    @BindView(R.id.report_detail_doctor_check_suggestion)
    TextView checkSuggestion;
    @BindView(R.id.report_detail_doctor_eat_suggestion)
    TextView eatSuggestion;
    @BindView(R.id.report_detail_doctor_sport_suggestion)
    TextView sportSuggestion;

    @BindView(R.id.report_detail_doctor_name)
    TextView doctorName;
    @BindView(R.id.report_detail_doctor_level)
    TextView doctorLevel;
    @BindView(R.id.report_detail_doctor_suggestion_time)
    TextView doctorSuggestTime;
    //-----医生建议------
    @BindView(R.id.report_detail_bottom)
    TextView bottomBtn;


    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_report_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ptrWithoutToolbar = true;
        mReadService = RetrofitHelper.getInstance().create(ReadService.class);

        Bundle argument = getArguments();
        if (argument != null) {
            reportId = argument.getString(BundleKey.BUNDLE_KEY_REPORT_ID);
            fromHome = argument.getBoolean(BundleKey.BUNDLE_KEY_REPORT_FROM_HOME);
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        mReadService.getReportDetail(reportId, "4").subscribe(new PtrSubscriber<ResponseModel<ReportDetailMode>>(this) {
            @Override
            public void onNext(ResponseModel<ReportDetailMode> reportDetailModeResponseModel) {
                super.onNext(reportDetailModeResponseModel);
                ReportDetailMode detailMode = reportDetailModeResponseModel.getData();
                update(detailMode);
            }
        });
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        setTitle(R.string.report_detail_title);
        ptrFrameLayout.autoRefresh();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler2.checkContentCanBePulledDown(frame, mScrollView, header);
    }


    /**
     * 更新信息
     *
     * @param detailMode
     */
    private void update(ReportDetailMode detailMode) {

        updateUserInfo(detailMode);
        updateReportInfo(detailMode);
        updateBodyInfo(detailMode);

        updateUserPrivate(fromHome);
    }

    private void updateUserPrivate(boolean isPrivate) {
        if (isPrivate) {
            userName.setText("******");
            picContentPrivate.setVisibility(VISIBLE);
            picRecyleView.setVisibility(GONE);
            quotaView.setVisibility(GONE);
            quotaPrivateView.setVisibility(VISIBLE);
        }
    }

    /**
     * 更新用户信息
     *
     * @param detailMode
     */
    private void updateUserInfo(ReportDetailMode detailMode) {
        ReportDetailMode.UserInfo userInfo = detailMode.getUserInfo();
        if (userInfo != null) {
            userName.setText(userInfo.getName());
            userSex.setText(StringUtils.getSex2Show(userInfo.getSex()));
            userAge.setText(String.valueOf(userInfo.getAge()));
            userType.setText(userInfo.getType());
            userCheckTime.setText(TimeUtils.millis2StringWithoutTime(userInfo.getDate()));
            userCheckHospital.setText(userInfo.getHospital());
            isMale = userInfo.getSex().equals("1");
        }
        checkMale(isMale);
    }

    /**
     * 更新报告相关信息
     *
     * @param detailMode
     */
    private void updateReportInfo(ReportDetailMode detailMode) {
        ReportDetailMode.ReportInfo reportInfo = detailMode.getReportInfo();
        if (reportInfo == null) {
            return;
        }
        reportId = reportInfo.getId();
        //更新报告状态
        int state = reportInfo.getReportStatus();
        checkReportState(state);

        //更新报告图片信息
        List<ReportDetailMode.ImgInfo> imgs = detailMode.getImgs();
        final ArrayList<String>        urls = new ArrayList<>();
        for (ReportDetailMode.ImgInfo imgInfo : imgs) {
            urls.add(imgInfo.getUrl());
        }
        ReportPicAdapter reportPicAdapter = new ReportPicAdapter(urls);
        picRecyleView.setLayoutManager(new NestedGridLayoutManager(getActivity(), 3));
        picRecyleView.setAdapter(reportPicAdapter);
        reportPicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST, urls);
                bundle.putInt(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST_INDEX, position);
                AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_OTHER_PICTURE_PREVIEW, bundle);
            }
        });


        //更新用户备注
        userNoteContent.setText(reportInfo.getRemark());
        //更新得分
        score.setCurrentValues(reportInfo.getScore());
        //更新医生信息
        doctorName.setText(reportInfo.getDoctorName());
        doctorLevel.setText(reportInfo.getDoctorName());
        doctorSuggestTime.setText(TimeUtils.millis2String(reportInfo.getReadDate()));
        //        AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_DOCTOR_DETAIL);


        if (reportInfo.getDoctorSuggestion() != null) {
            ReportDetailMode.ReportInfo.DoctorSuggestion suggestion = detailMode.getReportInfo().getDoctorSuggestion();
            //更新医生建议
            quotaNormal.setText(suggestion.getNormalQuota());
            quotaUnNormal.setText(suggestion.getIllQuota());
            checkSuggestion.setText(suggestion.getItemSuggestion());
            eatSuggestion.setText(suggestion.getFoodSuggestion());
            sportSuggestion.setText(suggestion.getSportSuggestion());
        }


    }

    /**
     * 更新异常项目
     *
     * @param detailMode
     */
    private void updateBodyInfo(ReportDetailMode detailMode) {
        List<ReportDetailMode.BodySystem> bodySystems = detailMode.getBodySystems();

        final List<BodyItemView>                illItems   = new ArrayList<>();
        final List<ReportDetailMode.BodySystem> illSystems = new ArrayList<>();

        for (final ReportDetailMode.BodySystem system : bodySystems) {
            switch (system.getSystemId()) {
                case ReportDetailMode.BodySystem.BODY_1:
                    illItems.add(body1);
                    illSystems.add(system);
                    break;
                case ReportDetailMode.BodySystem.BODY_2:
                    illItems.add(body2);
                    illSystems.add(system);
                    break;
                case ReportDetailMode.BodySystem.BODY_3:
                    illItems.add(body3);
                    illSystems.add(system);
                    break;
                case ReportDetailMode.BodySystem.BODY_4:
                    illItems.add(body4);
                    illSystems.add(system);
                    break;
                case ReportDetailMode.BodySystem.BODY_5:
                    illItems.add(body5);
                    illSystems.add(system);
                    break;
                case ReportDetailMode.BodySystem.BODY_6:
                    illItems.add(body6);
                    illSystems.add(system);
                    break;
                case ReportDetailMode.BodySystem.BODY_7:
                    illItems.add(body7);
                    illSystems.add(system);
                    break;
                case ReportDetailMode.BodySystem.BODY_8:
                    illItems.add(isMale ? body8Male : body8Female);
                    illSystems.add(system);
                    break;
                default:
                    break;
            }
        }

        //设置异常项目
        for (int i = 0; i < illItems.size(); i++) {
            final ReportDetailMode.BodySystem bodySystem = illSystems.get(i);
            illItems.get(i).setNormal(false);
            illItems.get(i).addUnNormalClickListener(new BodyItemView.UnNormalClickListener() {
                @Override
                public void onUnNormalClick() {
                    createPopDialog(bodySystem.getIllItems());
                }
            });
        }

    }

    /**
     * 根据性别更新view显示
     *
     * @param isMale
     */
    private void checkMale(boolean isMale) {
        if (isMale) {
            userNoteHeader.setImageResource(R.drawable.img_details_man);
            body.setImageResource(R.drawable.yc_st);
            body8Male.setVisibility(VISIBLE);
            body8Female.setVisibility(GONE);
        } else {
            userNoteHeader.setImageResource(R.drawable.img_details_female);
            body.setImageResource(R.drawable.female_st);
            body8Male.setVisibility(GONE);
            body8Female.setVisibility(VISIBLE);
        }

    }

    /**
     * 根据报告状态更新view显示
     *
     * @param reportStatus
     */
    private void checkReportState(int reportStatus) {
        this.reportStatus = reportStatus;
        switch (reportStatus) {
            case ReportItemMode.READ_STATE_UNSUBMIT:
                scoreView.setVisibility(GONE);
                bodyView.setVisibility(GONE);
                suggestionView.setVisibility(GONE);
                bottomBtn.setText(R.string.report_detail_bottom_btn_un_submit);
                break;
            case ReportItemMode.READ_STATE_UNREAD:
                scoreView.setVisibility(GONE);
                bodyView.setVisibility(GONE);
                suggestionView.setVisibility(GONE);
                bottomBtn.setText(R.string.report_detail_bottom_btn_unread);
                break;
            case ReportItemMode.READ_STATE_UNGTE:
            case ReportItemMode.READ_STATE_GETFAILED:
                scoreView.setVisibility(GONE);
                bodyView.setVisibility(GONE);
                suggestionView.setVisibility(GONE);
                bottomBtn.setText(R.string.report_detail_bottom_btn_readed);
                break;
            case ReportItemMode.READ_STATE_READED:
                scoreView.setVisibility(VISIBLE);
                bodyView.setVisibility(VISIBLE);
                suggestionView.setVisibility(VISIBLE);
                bottomBtn.setText(R.string.report_detail_bottom_btn_readed);
                break;
            default:
                break;
        }

    }

    /**
     * @param illItems
     */
    private void createPopDialog(List<ReportDetailMode.BodySystem.IllItem> illItems) {
        final Dialog dialog = new Dialog(getActivity());
        View         view   = getActivity().getLayoutInflater().inflate(R.layout.view_report_detail_bottom_dialog, null);
        view.findViewById(R.id.report_detail_bottom_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //异常项目
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.report_detail_bottom_dialog_recyclerview);
        recyclerView.setLayoutManager(new NestedLinearLayoutManager(getActivity()));
        BaseQuickAdapter<ReportDetailMode.BodySystem.IllItem, BaseViewHolder> quickAdapter = new BaseQuickAdapter<ReportDetailMode.BodySystem.IllItem, BaseViewHolder>(R.layout.view_report_detail_bottom_dialog_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, ReportDetailMode.BodySystem.IllItem illItem) {
                int    position = baseViewHolder.getAdapterPosition() + 1;
                String title    = String.format(getString(R.string.report_detail_bottom_dialog_item_title), position, illItem.getItemName());
                String refer    = String.format(getString(R.string.report_detail_bottom_dialog_item_refer), illItem.getReferenceRange());
                String actual   = String.format(getString(R.string.report_detail_bottom_dialog_item_actual), illItem.getResZb());
                baseViewHolder.setText(R.id.report_detail_bottom_dialog_item_title, title);
                baseViewHolder.setText(R.id.report_detail_bottom_dialog_item_content1, refer);
                baseViewHolder.setText(R.id.report_detail_bottom_dialog_item_content2, actual);
            }
        };
        recyclerView.setAdapter(quickAdapter);
        quickAdapter.setNewData(illItems);

        //设置位置
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.DialogBottomPop);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 宽度
        lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        view.measure(0, 0);
        lp.height = view.getMeasuredHeight();
        // 透明度
        lp.alpha = 9f;
        window.setAttributes(lp);
        dialog.show();

    }

    @OnClick(R.id.report_detail_bottom)
    public void onBottomBtnClick() {
        switch (reportStatus) {
            case ReportItemMode.READ_STATE_UNSUBMIT:
                //未提交
                mReadService.sumbitReport2Doctor(reportId).subscribe(new PtrSubscriber<ResponseModel>(this) {
                    @Override
                    public void onNext(ResponseModel responseModel) {
                        super.onNext(responseModel);
                        bottomBtn.setText(R.string.report_detail_bottom_btn_submited);
                    }
                });
                break;
            case ReportItemMode.READ_STATE_UNREAD:
                ToastUtils.showLong(R.string.report_detail_bottom_btn_unread);
                //未解读
                break;
            case ReportItemMode.READ_STATE_READED:
                //已解读
                Bundle bundle = new Bundle();
                bundle.putString(BundleKey.BUNDLE_KEY_REPORT_ID, reportId);
                AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_ASK, bundle);
                break;
            default:
                break;
        }
    }

    private class ReportPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ReportPicAdapter(@Nullable List<String> data) {
            super(R.layout.view_report_detail_report_pic_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final String item) {
            ImageView pic = helper.getView(R.id.view_report_detail_report_pic_img);
            Glide.with(getActivity()).load(item).into(pic);
        }
    }


}
